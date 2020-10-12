package co.unicauca.justeat.server.infra;

import co.unicauca.justEat.server.access.Factory;
import co.unicauca.justeat.commons.domain.User;
import co.unicauca.justeat.commons.infra.JsonError;
import co.unicauca.justeat.commons.infra.Protocol;
import co.unicauca.justeat.commons.infra.Utilities;
import co.unicauca.justeat.server.access.IUserRepository;
import co.unicauca.justeat.server.domain.servicies.UserService;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SANTIAGO MUÑOZ
 *         KEVIN ALARCON
 *         JUAN JOSE LOPEZ
 *         SANTIAGO CORDOBA
 *         DANIEL MUÑOZ
 */
public class UserServerSocket implements Runnable {
    /**
     * private fin Servicio de clientes
     */
 
    private final UserService service;
    /**
     * Server Socket, la orejita
     */
    private static ServerSocket ssock;
    /**
     * Socket por donde se hace la petición/respuesta
     */
    private static Socket socket;
    /**
     * Permite leer un flujo de datos del socket
     */
    private Scanner input;
    /**
     * Permite escribir un flujo de datos del scoket
     */
    private PrintStream output;
    /**
     * Puerto por donde escucha el server socket
     */
    private static final int PORT = Integer.parseInt(Utilities.loadProperty("server.port"));

    public UserServerSocket() {
       
        
        IUserRepository repositoryUser = Factory.getInstance().getRepositoryUser();
        service = new UserService(repositoryUser);
        
    }
    /**
     * Arranca el servidor y hace la estructura completa
     */
    public void start() {
        openPort();

        while (true) {
            waitToClient();
            throwThread();
        }
    }
    
    /**
     * Lanza el hilo
     */
    private static void throwThread() {
        new Thread(new UserServerSocket()).start();
    }

    /**
     * Instancia el server socket y abre el puerto respectivo
     */
    private static void openPort() {
        try {
            ssock = new ServerSocket(PORT);
            Logger.getLogger("Server").log(Level.INFO, "Servidor iniciado, escuchando por el puerto {0}", PORT);
        } catch (IOException ex) {
            Logger.getLogger(UserServerSocket.class.getName()).log(Level.SEVERE, "Error del server socket al abrir el puerto", ex);
        }
    }

    /**
     * Espera que el cliente se conecta y le devuelve un socket
     */
    private static void waitToClient() {
        try {
            socket = ssock.accept();
            Logger.getLogger("Socket").log(Level.INFO, "Socket conectado");
        } catch (IOException ex) {
            Logger.getLogger(UserServerSocket.class.getName()).log(Level.SEVERE, "Error al abrir un socket", ex);
        }
    }
    /**
     * Cuerpo del hilo
     */
    
    @Override
    public void run(){
        try {
            createStreams();
            readStream();
            closeStream();

        } catch (IOException ex) {
            Logger.getLogger(UserServerSocket.class.getName()).log(Level.SEVERE, "Error al leer el flujo", ex);
        
    }}
    
    /**
     * Crea los flujos con el socket
     *
     * @throws IOException
     */
    private void createStreams() throws IOException{
        output = new PrintStream(socket.getOutputStream());
        input = new Scanner(socket.getInputStream());
    }
      /**
     * Lee el flujo del socket
     */
    private void readStream(){
        if (input.hasNextLine()) {
            // Extrae el flujo que envió la aplicación cliente
            String request = input.nextLine();
            processRequest(request);

        } else {
            output.flush();
            String errorJson = generateErrorJson();
            output.println(errorJson);
        }
    }
    
    /**
     * Procesar la solicitud que proviene de la aplicación cliente
     *
     * @param requestJson petición que proviene del cliente socket en formato
     * json que viene de esta manera:
     * "{"resource":"customer","action":"get","parameters":[{"name":"id","value":"1"}]}"
     *
     */
    private void processRequest(String requestJson){
        // Convertir la solicitud a objeto Protocol para poderlo procesar
        Gson gson = new Gson();
        Protocol protocolRequest = gson.fromJson(requestJson, Protocol.class);

        switch (protocolRequest.getResource()) {
            
            case "Usuario":
                if (protocolRequest.getAction().equals("get")) {
                    // Consultar un user
                    processGetUser(protocolRequest);
                }
                
                if (protocolRequest.getAction().equals("post")) {
                    // Agregar un restaurant    
                    processPostUser(protocolRequest);
                }
                break;
                
        }
    }
    
    /**
     * Procesa la solicitud de consultar un user
     *
     * @param protocolRequest Protocolo de la solicitud
     */
    public void processGetUser(Protocol protocolRequest){
        // Extraer la cedula del primer parámetro
        String userName = protocolRequest.getParameters().get(0).getValue();
        User user = service.findUser((userName));
        if (user == null) {
            String errorJson = generateNotFoundErrorJson();
            output.println(errorJson);
        } else {
            output.println(objectToJson(user));
        }
    }
    
  
    /**
     * 
     * @param protocolRequest 
     */
    public void processPostUser(Protocol protocolRequest){
        User varUser = new User();
        varUser.setUserName(protocolRequest.getParameters().get(0).getValue());
        varUser.setUserContrasena(protocolRequest.getParameters().get(1).getValue());
        varUser.setUserNombre(protocolRequest.getParameters().get(2).getValue());
        varUser.setUserApellido(protocolRequest.getParameters().get(3).getValue());
        varUser.setUserCedula(protocolRequest.getParameters().get(4).getValue());
        varUser.setUserCiudad(protocolRequest.getParameters().get(5).getValue());
        varUser.setUserDireccion(protocolRequest.getParameters().get(6).getValue());
        varUser.setUserCelular(protocolRequest.getParameters().get(7).getValue());
        
        String response = service.CreateUser(varUser);
        output.print(response);
    }
        
    /**
     * Genera un ErrorJson de cliente no encontrado
     *
     * @return error en formato json
     */
    private String generateNotFoundErrorJson() {
        List<JsonError> errors = new ArrayList<>();
        JsonError error = new JsonError();
        error.setCode("404");
        error.setError("NOT_FOUND");
        error.setMessage("Cliente no encontrado. Cédula no existe");
        errors.add(error);

        Gson gson = new Gson();
        String errorsJson = gson.toJson(errors);

        return errorsJson;
    }

    /**
     * Genera un ErrorJson genérico
     *
     * @return error en formato json
     */
    private String generateErrorJson() {
        List<JsonError> errors = new ArrayList<>();
        JsonError error = new JsonError();
        error.setCode("400");
        error.setError("BAD_REQUEST");
        error.setMessage("Error en la solicitud");
        errors.add(error);

        Gson gson = new Gson();
        String errorJson = gson.toJson(errors);

        return errorJson;
    }

    /**
     * Cierra los flujos de entrada y salida
     *
     * @throws IOException
     */
    private void closeStream() throws IOException {
        output.close();
        input.close();
        socket.close();
    }
   
    private String objectToJson(User parUser){
        Gson gson = new Gson();
        String strObject = gson.toJson(parUser);
        return strObject;
    }
}
