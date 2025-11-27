package taller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static final int PUERTO = 5050;

    public static String usuario = "";

    private static Socket cliente = null;



    public static List<Coche> coches = new ArrayList<>();
    public static List<Reparacion> reparaciones = new ArrayList<>();

    public static void iniciar() throws IOException {

        try  {

            ServerSocket server = new ServerSocket(PUERTO);

            System.out.println("Servidor iniciado en el puerto: " + PUERTO);

            Server.cliente = server.accept();

            BufferedReader br = new BufferedReader(new InputStreamReader(Server.cliente.getInputStream()));

            String linea = br.readLine();

            if ((linea !=  null)) {

                usuario = linea;

                System.out.println(linea + " bienvenido a SerguialesAuto");
            }



        } catch (Exception e) {
            e.getMessage();
        }

    }




    public static String leerComando(Socket s, BufferedReader br) throws IOException {

        String linea = "";

        try  {

            linea = br.readLine();

            if (linea == null) {

                System.err.println("Comando nulo");

            }

        } catch (Exception e) {
            System.out.println("Error, no se pudo leer el comando " + e.getMessage());
        } finally {

            return linea;

        }


    }

    public static Coche añadirCoche(String[] comando) {

        String idCoche = comando[1];
        String marcaCoche = comando[2];
        String modeloCoche = comando[3];
        String añoCoche = comando[4];



        return new Coche(idCoche, marcaCoche, modeloCoche, añoCoche);
    }

    public static String listarCoches() {

        StringBuilder cochesLista = new StringBuilder();

        for (Coche coche : coches) {

            cochesLista.append("ID: ").append(coche.getId()).append(" Marca: ").append(coche.getMarca()).append(" Modelo: ").append(coche.getModelo()).append(" Año: ").append(coche.getAño()).append("\n");
           // System.out.println("ID: " + coche.getId() + " Marca: " + coche.getMarca() + " Modelo: " + coche.getModelo() + " Año: " + coche.getAño());
        }
        return cochesLista.toString();
    }



    public static void consola(String[] comandoActual, PrintWriter pw) {

        try {

            switch (comandoActual[0].toUpperCase().trim()) {
                case "ADDCOCHE":
                    System.out.println("Ha seleccionado crear un coche");
                    coches.add(añadirCoche(comandoActual));
                    System.out.println("Coche " + comandoActual[2] + " añadido correctamente!!");
                    pw.println("Coche " + comandoActual[2] + " añadido correctamente!!");
                    break;
                case "REMOVECOCHE":
                    System.out.println("Remover");
                    // Borrar coche
                    break;
                case "GETCOCHE":
                    System.out.println("Get");
                    // Ver coche
                    break;
                case "LISTCOCHES":
                    System.out.println("Lista de coches");
                    pw.println(listarCoches());
                    System.out.println("El usuario " + usuario + " ha hecho LISTCOCHES");
                    break;
                case "ADDREPARACION":
                    System.out.println("Ha seleccionado el reparacion");
                    // Registrar reparacion
                    break;
                default:
                    System.out.println("Comando invalido");
                    return;


            }

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
        }

    }

    public static void main(String[] args) throws IOException {



        String[] comandoActual = null;

        iniciar();
        BufferedReader br = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        PrintWriter pw = new PrintWriter(cliente.getOutputStream(), true);

            do {

                comandoActual = leerComando(cliente, br).split(",");

                consola(comandoActual, pw);

            } while (!comandoActual[0].equalsIgnoreCase("EXIT"));






        System.out.println("Usuario " + usuario + " se ha desconectado");



    }
}
