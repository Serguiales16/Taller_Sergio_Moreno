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

                System.out.println(
                        """
                                Comandos disponibles:
                                ADDCOCHE - Agregar Coche
                                REMOVECOCHE - Borrar Coche
                                GETCOCHE - Ver coche por ID
                                LISTCOCHES - Listar coches
                                ADDREPARACION - Añadir reparacion
                                EXIT"""
                );

                System.out.println(usuario + "-console: ");
                comandoActual = sc.nextLine();
                pw.println(comandoActual);


                if (comandoActual.equalsIgnoreCase("LISTCOCHES")) {

                    String lineaRecibida;

                    while ((lineaRecibida = br.readLine()) != null) {
                        if (lineaRecibida.equals("!")) {
                            break;
                        }
                        System.out.println(lineaRecibida);
                    }

                } else {
                    String respuesta = br.readLine();
                    if (respuesta != null && !respuesta.isEmpty()) {
                        System.out.println(respuesta);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error al conectar con el servidor " + e.getMessage());
        }
    }
}
