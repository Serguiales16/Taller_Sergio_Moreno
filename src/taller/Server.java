package taller;

// Importaciones necesarias para trabajar con sockets, lectura/escritura y listas
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

// Clase Server: gestiona toda la lógica del servidor del taller
public class Server {

    // Puerto donde escucha el servidor
    public static final int PUERTO = 5050;

    // Variable donde se guarda el nombre del usuario conectado
    public static String usuario = "";

    // Socket que representa al cliente conectado
    private static Socket cliente = null;

    // Variable usada para controlar si un coche existe al eliminar
    public static boolean cocheExistente = false;

    // Lista que almacena los coches del taller
    public static List<Coche> coches = new ArrayList<>();

    // Lista que almacena las reparaciones del taller
    public static List<Reparacion> reparaciones = new ArrayList<>();

    // Método que inicia el servidor y espera a un cliente
    public static void iniciar() throws IOException {

        try {

            // Se crea el servidor en el puerto definido
            ServerSocket server = new ServerSocket(PUERTO);

            System.out.println("Servidor iniciado en el puerto: " + PUERTO);

            // El servidor queda esperando hasta que un cliente se conecte
            Server.cliente = server.accept();

            // Prepara la lectura de datos enviados por el cliente
            BufferedReader br = new BufferedReader(new InputStreamReader(Server.cliente.getInputStream()));

            // Lee la primera línea enviada por el cliente (nombre de usuario)
            String linea = br.readLine();

            // Si el cliente ha enviado un nombre, se guarda y se muestra
            if ((linea !=  null)) {

                usuario = linea;

                System.out.println(linea + " bienvenido a SerguialesAuto");
            }

        } catch (Exception e) {
            e.getMessage();
        }

    }

    // Método que lee un comando enviado por el cliente
    public static String leerComando(Socket s, BufferedReader br) throws IOException {

        String linea = "";

        try {

            // Lee una línea completa enviada por el cliente
            linea = br.readLine();

            // Si la línea es null, el cliente no ha enviado datos
            if (linea == null) {

                System.err.println("Comando nulo");
            }

        } catch (Exception e) {
            System.out.println("Error, no se pudo leer el comando " + e.getMessage());
        } finally {

            // Devuelve la línea leída
            return linea;
        }
    }

    // Método que crea un coche comprobando que el ID no esté repetido
    public static Coche añadirCoche(String[] comando) {

        boolean existe = false;

        // Recorre la lista de coches para comprobar si el ID ya existe
        for (Coche coche : coches) {

            if (coche.getId().equalsIgnoreCase(comando[1].trim())) {

                existe = true;
                System.out.println("Error: Ya existe un coche con ese ID");
                break;
            }
        }

        // Se extraen los datos del coche desde el comando
        String idCoche = comando[1];
        String marcaCoche = comando[2];
        String modeloCoche = comando[3];
        String añoCoche = comando[4];

        // Se crea el objeto Coche
        Coche c1 = new Coche(idCoche, marcaCoche, modeloCoche, añoCoche);

        // Si el ID existe se devuelve null, si no se devuelve el coche
        if (existe) {
            return null;
        } else {

            return c1;
        }
    }

    // Método que devuelve todos los coches en formato texto
    public static String listarCoches() {

        StringBuilder cochesLista = new StringBuilder();

        // Recorre la lista de coches y añade cada uno al StringBuilder
        for (Coche coche : coches) {
            cochesLista.append("ID: ")
                    .append(coche.getId())
                    .append(" Marca: ")
                    .append(coche.getMarca())
                    .append(" Modelo: ")
                    .append(coche.getModelo())
                    .append(" Año: ")
                    .append(coche.getAño())
                    .append("\n");
        }

        // Símbolo que indica el final del listado para el cliente
        cochesLista.append("\n!");
        return cochesLista.toString();
    }

    // Método que devuelve la información de un coche concreto por ID
    public static String verCoche(String[] comando) {

        StringBuilder cocheVer = new StringBuilder();

        // Busca el coche cuyo ID coincida con el recibido
        for (Coche coche : coches) {

            if (coche.getId().equalsIgnoreCase(comando[1].trim())) {

                cocheVer.append("ID: ")
                        .append(coche.getId())
                        .append(" Marca: ")
                        .append(coche.getMarca())
                        .append(" Modelo: ")
                        .append(coche.getModelo())
                        .append(" Año: ")
                        .append(coche.getAño());
            }
        }

        // Devuelve la información del coche encontrado
        return cocheVer.toString();
    }

    // Método que crea una reparación a partir del comando recibido
    public static Reparacion añadirReparacion(String[] comando) {

        // Se extraen los datos de la reparación
        String idCoche = comando[1];
        String descripcion = comando[2];
        float costo = Float.parseFloat(comando[3]);

        // Se crea y devuelve el objeto Reparacion
        return new Reparacion(idCoche, descripcion, costo);
    }

    // Método que elimina un coche según su ID
    public static void eliminarCoche(String[] comando) {

        cocheExistente = false;
        boolean eliminado = false;

        // Busca el coche en la lista y lo elimina
        for (Coche coche : coches) {

            if (coche.getId().equalsIgnoreCase(comando[1].trim())) {

                coches.remove(coche);
                System.out.println("Coche con ID " + comando[1] + " eliminado correctamente");
                eliminado = true;
                cocheExistente = true;
                break;
            }
        }

        // Si no se ha eliminado ningún coche
        if (!eliminado) {
            System.out.println("No se encontró un coche con ID " + comando[1]);
        }
    }

    // Método que interpreta y ejecuta los comandos del cliente
    public static void consola(String[] comandoActual, PrintWriter pw) {

        try {

            // Se analiza el comando según la primera palabra
            switch (comandoActual[0].toUpperCase().trim()) {

                case "ADDCOCHE":

                    try {
                        System.out.println("Ha seleccionado crear un coche");
                        Coche nuevo = añadirCoche(comandoActual);
                        if (nuevo == null) {
                            pw.println("Error al añadir coche: Probable ID repetido");
                            System.out.println("Error al añadir coche: Probable ID repetido");
                            break;
                        } else {
                            coches.add(añadirCoche(comandoActual));
                            System.out.println("Coche " + comandoActual[2] + " añadido correctamente!!");
                            pw.println("Coche " + comandoActual[2] + " añadido correctamente!!");
                        }

                    } catch (Exception e) {
                        System.out.println("Error al añadir coche: Probable ID repetido" + e.getMessage());
                        pw.println("Error, algo has hecho mal.RECUERDA(ADDCOCHE,ID,MARCA,MODELO,AÑO." + e.getMessage());
                    }
                    break;

                case "REMOVECOCHE":
                    System.out.println("Remover");

                    if (!cocheExistente) {
                        pw.println("Error al eliminar coche: No existe un coche con ese ID");
                        System.out.println("Error al eliminar coche: No existe un coche con ese ID");
                        break;
                    } else {

                        eliminarCoche(comandoActual);
                        pw.println("Coche con ID " + comandoActual[1] + " eliminado correctamente!!");
                        System.out.println("El usuario " + usuario + " ha hecho REMOVECOCHE");
                    }
                    break;

                case "GETCOCHE":
                    System.out.println("Get");
                    pw.println(verCoche(comandoActual));
                    System.out.println("El usuario " + usuario + " ha hecho GETCOCHE");
                    break;

                case "LISTCOCHES":
                    System.out.println("Lista de coches");
                    pw.println(listarCoches());
                    System.out.println("El usuario " + usuario + " ha hecho LISTCOCHES");
                    break;

                case "ADDREPARACION":
                    System.out.println("Ha seleccionado el reparacion");
                    reparaciones.add(añadirReparacion(comandoActual));
                    pw.println("Reparacion para coche ID " + comandoActual[1] + " añadida correctamente!!");
                    System.out.println("Reparacion para coche ID " + comandoActual[1] + " añadida correctamente!!");
                    break;

                default:
                    // Comando no reconocido
                    System.out.println("Comando invalido");
                    pw.println("Comando invalido");
                    return;
            }

        } catch (Exception e) {

            // Captura cualquier error inesperado
            System.out.println("Error: " + e.getMessage());
            pw.println("Error, algo has hecho mal.RECUERDA(ADDCOCHE,ID,MARCA,MODELO,AÑO. Y los demas COMANDO,ID)" + e.getMessage());
            return;
        }
    }

    // Método principal del servidor
    public static void main(String[] args) throws IOException {

        String[] comandoActual = null;

        // Arranca el servidor
        iniciar();

        // Prepara streams para leer y escribir con el cliente
        BufferedReader br = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        PrintWriter pw = new PrintWriter(cliente.getOutputStream(), true);

        // Bucle principal que procesa comandos hasta EXIT
        do {

            comandoActual = leerComando(cliente, br).split(",");

            consola(comandoActual, pw);

        } while (!comandoActual[0].equalsIgnoreCase("EXIT"));

        // Mensaje cuando el usuario se desconecta
        System.out.println("Usuario " + usuario + " se ha desconectado");
    }
}
