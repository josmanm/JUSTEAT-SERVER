package co.unicauca.justeat.server.infra;

import co.unicauca.justEat.server.access.Factory;
import co.unicauca.justeat.commons.domain.Restaurant;
import co.unicauca.justeat.commons.infra.JsonError;
import co.unicauca.justeat.commons.infra.Protocol;
import co.unicauca.justeat.commons.infra.Utilities;
import co.unicauca.justeat.server.access.IRestauranRepository;
import co.unicauca.justeat.server.domain.servicies.RestaurantService;
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
public class RestaurantServerSocket implements Runnable {

    /**
     * private fin Servicio de clientes
     */
    private final RestaurantService service;
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

    public RestaurantServerSocket() {
        // Se hace la inyección de dependencia
        IRestauranRepository repository = Factory.getInstance().getRepository();
        service = new RestaurantService(repository);
        
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
        new Thread(new RestaurantServerSocket()).start();
    }

    /**
     * Instancia el server socket y abre el puerto respectivo
     */
    private static void openPort() {
        try {
            ssock = new ServerSocket(PORT);
            Logger.getLogger("Server").log(Level.INFO, "Servidor iniciado, escuchando por el puerto {0}", PORT);
        } catch (IOException ex) {
            Logger.getLogger(RestaurantServerSocket.class.getName()).log(Level.SEVERE, "Error del server socket al abrir el puerto", ex);
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
            Logger.getLogger(RestaurantServerSocket.class.getName()).log(Level.SEVERE, "Error al abrir un socket", ex);
        }
    }
    /**
     * Cuerpo del hilo
     */
    
    @Override
    public void run() {
        try {
            createStreams();
            readStream();
            closeStream();

        } catch (IOException ex) {
            Logger.getLogger(RestaurantServerSocket.class.getName()).log(Level.SEVERE, "Error al leer el flujo", ex);
        } 
    }
    
    /**
     * Crea los flujos con el socket
     *
     * @throws IOException
     */
    private void createStreams() throws IOException {
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
            case "Restaurante":
                
                if (protocolRequest.getAction().equals("get")) {
                    // Consultar un restaurant
                    processGetRestaurant(protocolRequest);
                }
                
                if (protocolRequest.getAction().equals("post")) {
                    // Agregar un restaurant    
                    processPostRestaurant(protocolRequest);
                }
                break;
        }
    }
    
    /**
     * Procesa la solicitud de consultar un customer
     *
     * @param protocolRequest Protocolo de la solicitud
     */
    private void processGetRestaurant(Protocol protocolRequest) {
        // Extraer la cedula del primer parámetro
        String id = protocolRequest.getParameters().get(0).getValue();
        Restaurant customer = service.findRestaurant((id));
        if (customer == null) {
            String errorJson = generateNotFoundErrorJson();
            output.println(errorJson);
        } else {
            output.println(objectToJSON(customer));
        }
    }
    /**
     * Procesa la solicitud de agregar un Restaurante
     *
     * @param protocolRequest Protocolo de la solicitud
     */
    private void processPostRestaurant(Protocol protocolRequest) {
        
        Restaurant varRestaurant = new Restaurant();
        // Reconstruir el customer a partid de lo que viene en los parámetros
        varRestaurant.setResId(((protocolRequest.getParameters().get(0).getValue())));
        varRestaurant.setUserName((protocolRequest.getParameters().get(1).getValue()));
        varRestaurant.setResNom(protocolRequest.getParameters().get(2).getValue());
        varRestaurant.setResDireccion(protocolRequest.getParameters().get(3).getValue());
        varRestaurant.setResCiudad(protocolRequest.getParameters().get(4).getValue());
        varRestaurant.setResTematicaComida(protocolRequest.getParameters().get(5).getValue());
        String response = service.CreateRestaurant(varRestaurant);
        output.println(response);
    }
    

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
    
       /**
     * Convierte el objeto Restaurant a json para que el servidor lo envie como
     * respuesta por el socket
     *
     * @param parRest cliente
     * @return customer en formato json
     */
    private String objectToJSON(Restaurant parRest) {
        Gson gson = new Gson();
        String strObject = gson.toJson(parRest);
        return strObject;
    }
   
}
