package taller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Server {

    public static final int PUERTO = 5050;

    public static String usuario = "";

   private static Socket cliente = null;

    public static void iniciar() {

        try (ServerSocket server = new ServerSocket(PUERTO)) {

            System.out.println("Servidor iniciado en el puerto: " + PUERTO);

            cliente = server.accept();

            BufferedReader br = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            String linea = br.readLine();

            if ((linea !=  null)) {

                usuario = linea;

                System.out.println(linea + " bienvenido a SerguialesAuto");
            }



        } catch (Exception e) {
            e.getMessage();
        }

    }



    public static void main(String[] args) {


        List<Coche> coches = new ArrayList<>();
        List<Reparacion> reparaciones = new ArrayList<>();

        String[] comandoActual = null;

        iniciar();

        try {



            do {
                BufferedReader br = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                String linea = br.readLine();

                if ((linea != null)) {

                    comandoActual = linea.split("");
                } else {

                    System.out.println("Comando no registrado");

                }
                    switch (comandoActual[0].toUpperCase()) {
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

            } while (!comandoActual[0].equalsIgnoreCase("exit"));







        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Usuario " + usuario + " se ha desconectado");



    }
}
