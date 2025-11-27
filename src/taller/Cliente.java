package taller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {

        String comandoActual = "";

        Scanner sc = new Scanner(System.in);

        System.out.println("Nombre de usuario: ");
        String usuario = sc.nextLine();

        try (Socket s = new Socket("localhost", Server.PUERTO)) {

            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            pw.println(usuario);
            System.out.println("Conexion exitosa al servidor");
            while (!comandoActual.equalsIgnoreCase("exit")) {

            //    System.out.println("Respuesta:\n " + br.readLine());

                System.out.println("Comandos disponibles\nADDCOCHE: Añade un coche al taller\nREMOVECOCHE: Elimina un coche del taller" +
                        "\nGETCOCHE: Obtiene información de un coche\nLISTCOCHES: Lista todos los coches en el taller\nADDREPARACION: Registra una reparación para un coche" +
                        "\nEXIT: Cierra conexion");

                System.out.println(usuario + "-console: ");
                comandoActual = sc.nextLine();
                pw.println(comandoActual);

                String respuesta = br.readLine();
                System.out.println(respuesta);
            }

        } catch (Exception e) {

            System.out.println("Error al conectar con el servidor " + e.getMessage());

        }



    }
}
