package co.unicauca.justeat.server.infra;

import co.unicauca.justEat.server.access.Factory;
import co.unicauca.justeat.commons.domain.Dish;
import co.unicauca.justeat.commons.domain.Restaurant;
import co.unicauca.justeat.commons.domain.User;
import co.unicauca.justeat.commons.infra.JsonError;
import co.unicauca.justeat.commons.infra.Protocol;
import co.unicauca.justeat.commons.infra.Utilities;
import co.unicauca.justeat.server.access.IDishRepository;
import co.unicauca.justeat.server.access.IRestauranRepository;
import co.unicauca.justeat.server.access.IUserRepository;
import co.unicauca.justeat.server.domain.servicies.DishService;
import co.unicauca.justeat.server.domain.servicies.RestaurantService;
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
 * @author SANTIAGO MUÑOZ, KEVIN ALARCON, JUAN LOPEZ, SANTIAGO CORDOBA, DANIEL
 * MUÑOZ
 */
public class JustEatServerSocket implements Runnable {

    /**
     * Objeto de tipo RestaurantService.
     */
    private final RestaurantService service;

    private final DishService serviceDish;

    /**
     * Objeto de tipo UserService.
     */
    private final UserService service2;

    /**
     * Objeto de tipo ServerSocket.
     */
    private static ServerSocket ssock;

    /**
     * Objeto de tipo Socket por donde se hace la petición/respuesta.
     */
    private static Socket socket;

    /**
     * Objeto de tipo Scanner que permite leer un flujo de datos del socket.
     */
    private Scanner input;

    /**
     * Objeto de tipo PrintStream que permite escribir un flujo de datos del
     * socket.
     */
    private PrintStream output;

    /**
     * Puerto por donde escucha el server socket, la configuracion que tomara
     * sera de 'server.port'.
     */
    private static final int PORT = Integer.parseInt(Utilities.loadProperty("server.port"));

    /**
     * Constructor parametrizado, se encarga de inyectar las dependencias a las
     * variables service y service2.
     */
    public JustEatServerSocket() {
        // Se hace la inyección de dependencia
        IRestauranRepository repository = Factory.getInstance().getRepository();
        service = new RestaurantService(repository);

        IUserRepository repository1 = Factory.getInstance().getRepositoryUser();
        service2 = new UserService(repository1);

        IDishRepository repositoryDish = Factory.getInstance().getRepositoryDish();
        serviceDish = new DishService(repositoryDish);
    }

    /**
     * Metodo que permitira iniciar el servidor.
     */
    public void start() {
        openPort();
        while (true) {

            waitToClient();
            throwThread();
        }
    }

    /**
     * Metodo encargado de arrojar un hilo.
     */
    private static void throwThread() {
        new Thread(new JustEatServerSocket()).start();
    }

    /**
     * Metodo que se encarga de Instanciar el server socket y abre el puerto
     * respectivo.
     */
    private static void openPort() {
        try {
            ssock = new ServerSocket(PORT);
            Logger.getLogger("Server").log(Level.INFO, "Servidor iniciado, escuchando por el puerto {0}", PORT);
        } catch (IOException ex) {
            Logger.getLogger(JustEatServerSocket.class.getName()).log(Level.SEVERE, "Error del server socket al abrir el puerto", ex);
        }
    }

    /**
     * Metodo que se encarga de esperar a que el cliente se conecte y le
     * devuelve un socket.
     */
    private static void waitToClient() {
        try {
            socket = ssock.accept();
            Logger.getLogger("Socket").log(Level.INFO, "Socket conectado");
        } catch (IOException ex) {
            Logger.getLogger(JustEatServerSocket.class.getName()).log(Level.SEVERE, "Error al abrir un socket", ex);
        }
    }

    /**
     * Metodo encargado de darle cuerpo a un hilo.
     */
    @Override
    public void run() {
        try {
            createStreams();
            readStream();
            closeStream();

        } catch (IOException ex) {
            Logger.getLogger(JustEatServerSocket.class.getName()).log(Level.SEVERE, "Error al leer el flujo", ex);
        }
    }

    /**
     * Crea los flujos con el socket.
     *
     * @throws IOException
     */
    private void createStreams() throws IOException {
        output = new PrintStream(socket.getOutputStream());
        input = new Scanner(socket.getInputStream());
    }

    /**
     * Lee el flujo del socket, para ello extrae el flujo que envio el cliente.
     */
    private void readStream() {
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
     * Metodo encargado de procesar una peticion proveniente del cliente.
     *
     * @param requestJson Peticion que proviene del socket del cliente en
     * formato json.
     */
    private void processRequest(String requestJson) {
        // Convertir la solicitud a objeto Protocol para poderlo procesar
        Gson gson = new Gson();
        Protocol protocolRequest = gson.fromJson(requestJson, Protocol.class);

        switch (protocolRequest.getResource()) {
            case "Restaurante":

                if (protocolRequest.getAction().equals("get")) {
                    // Consultar un customer
                    processGetRestaurant(protocolRequest);
                }

                if (protocolRequest.getAction().equals("post")) {
                    // Agregar un customer    
                    processPostRestaurant(protocolRequest);
                }
                if (protocolRequest.getAction().equals("gets")) {
                    //(consutlar todos los restaurantes
                    processGetListRestaurant();
                }
                break;
            case "Usuario":

                if (protocolRequest.getAction().equals("get")) {
                    // Consultar un customer
                    processGetUser(protocolRequest);
                    // processGetListRestaurant(protocolRequest);
                }
                if (protocolRequest.getAction().equals("post")) {
                    // Agregar un customer    
                    processPostUser(protocolRequest);
                }
                break;
            case "Dish":
                if (protocolRequest.getAction().equals("post")) {
                    processPostDish(protocolRequest);
                }
        }

    }

    /**
     * Procesa la solicitud de consultar un restaurante en especifico.
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
            output.println(objectToJSONRestaurant(customer));
        }
    }

    /**
     * Procesa la solicitud de consultar un usuario.
     *
     * @param protocolRequest Protocolo de la solicitud
     */
    public void processGetUser(Protocol protocolRequest) {
        // Extraer la cedula del primer parámetro
        String userName = protocolRequest.getParameters().get(0).getValue();
        User user = service2.findUser((userName));
        if (user == null) {
            String errorJson = generateNotFoundErrorJson();
            output.println(errorJson);
        } else {
            output.println(objectToJSONUser(user));
        }
    }

    /**
     * Procesa la solicitud para consultar todos los restaurantes.
     *
     * @param protocolRequest
     */
    private void processGetListRestaurant() {
        List<Restaurant> listaRestaurant = service.ListRestaurant();
        if (!listaRestaurant.isEmpty()) {
            output.println(ArrayToJSON(listaRestaurant));
        } else {
            String errorJson = generateNotFoundErrorJson();
            output.println(errorJson);
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

    /**
     * Procesa la solicitud de agregar un plato.
     */
    private void processPostDish(Protocol protocolRequest) {
        Dish objDish = new Dish();

        objDish.setPlatoId(protocolRequest.getParameters().get(0).getValue());
        objDish.setPlanNom(protocolRequest.getParameters().get(1).getValue());
        objDish.setPlacDesc(protocolRequest.getParameters().get(2).getValue());
        objDish.setPlaPrecio(Double.parseDouble(protocolRequest.getParameters().get(3).getValue()));
        String response = serviceDish.CreateDish(objDish);
        output.println(response);
    }

    /**
     * Metodo encargado de procesar la solicitud de crear un usuario.
     *
     * @param protocolRequest
     */
    public void processPostUser(Protocol protocolRequest) {
        User varUser = new User();
        varUser.setUserName(protocolRequest.getParameters().get(0).getValue());
        varUser.setUserContrasena(protocolRequest.getParameters().get(1).getValue());
        varUser.setUserNombre(protocolRequest.getParameters().get(2).getValue());
        varUser.setUserApellido(protocolRequest.getParameters().get(3).getValue());
        varUser.setUserCedula(protocolRequest.getParameters().get(4).getValue());
        varUser.setUserCiudad(protocolRequest.getParameters().get(5).getValue());
        varUser.setUserDireccion(protocolRequest.getParameters().get(6).getValue());
        varUser.setUserCelular(protocolRequest.getParameters().get(7).getValue());
        String response = service2.CreateUser(varUser);
        output.print(response);
    }

    /**
     * Genera un ErrorJson de cliente cuando este no se encuentra.
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
     * Genera un ErrorJson genérico en caso de fallar alguna solicitud no
     * controlada.
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
     * @param parRest tipo Restaurant.
     * @return Restaurante en formato json(String).
     */
    private String objectToJSONRestaurant(Restaurant parRest) {
        Gson gson = new Gson();
        String strObject = gson.toJson(parRest);
        return strObject;
    }

    /**
     * Convierte un objeto de tipo User a json para que el servidor lo envie
     * como respuesta por el socket.
     *
     * @param parUser tipo Restaurant.
     * @return User en formato json(String).
     */
    private String objectToJSONUser(User parUser) {
        Gson gson = new Gson();
        String strObject = gson.toJson(parUser);
        return strObject;
    }

    /**
     * Convierte Una lista de Restaurante a json para que el servidor lo envie
     * como respuesta al socket.
     *
     * @param parLista Lista de tipo Restaurant.
     * @return Lista de restaurant en formato json (String).
     */
    private String ArrayToJSON(List<Restaurant> parLista) {
        Gson gson = new Gson();
        String strObject = gson.toJson(parLista);
        return strObject;
    }

}
