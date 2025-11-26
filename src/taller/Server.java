package taller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static final int PUERTO = 5050;

    public static String usuario = "";

   private static Socket cliente = null;



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


    public static void consola(String[] comandoActual) {

        try {

                switch (comandoActual[0].toUpperCase().trim()) {
                    case "ADDCOCHE":
                        System.out.println("Ha seleccionado el coche");
                        // crear coche
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
                        // Listar coches
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



    public static void main(String[] args) throws IOException {

        List<Coche> coches = new ArrayList<>();
        List<Reparacion> reparaciones = new ArrayList<>();

        String[] comandoActual = null;

        iniciar();
        BufferedReader br = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            do {

                comandoActual = leerComando(cliente, br).split(" ");

                consola(comandoActual);

            } while (!comandoActual[0].equalsIgnoreCase("EXIT"));






        System.out.println("Usuario " + usuario + " se ha desconectado");



    }
}
