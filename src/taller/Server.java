package taller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PUERTO = 5050;

   private static Socket cliente = null;

    public static void iniciar() {

        try (ServerSocket server = new ServerSocket(PUERTO)) {

            System.out.println("Servidor iniciado en el puerto: " + PUERTO);

            cliente = server.accept();

            BufferedReader br = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            String linea = br.readLine();

            if ((linea !=  null)) {



                System.out.println(linea + " bienvenido a SerguialesAuto");
            }



        } catch (Exception e) {
            e.getMessage();
        }

    }



    public static void main(String[] args) {

        String comandoActual = "";

        iniciar();

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            while (!comandoActual.equalsIgnoreCase("exit")) {
                String linea = br.readLine();

                if ((linea != null)) {

                    comandoActual = linea.trim();
                    System.out.println("El comando es: " + comandoActual);

                }

            }



        }catch(Exception e) {


        }



    }
}
