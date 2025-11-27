package taller;

// Importaciones necesarias para lectura/escritura, sockets y entrada por teclado
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

// Clase Cliente: permite al usuario comunicarse con el servidor
public class Cliente {

    // Método principal del cliente
    public static void main(String[] args) {

        // Variable que almacena el comando introducido por el usuario
        String comandoActual = "";

        // Scanner para leer datos desde el teclado
        Scanner sc = new Scanner(System.in);

        // Se solicita el nombre de usuario
        System.out.println("Nombre de usuario: ");
        String usuario = sc.nextLine();

        try (
                // Se crea el socket y se conecta al servidor usando el puerto definido
                Socket s = new Socket("localhost", Server.PUERTO)
        ) {

            // PrintWriter para enviar datos al servidor
            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);

            // BufferedReader para leer datos enviados por el servidor
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

            // Se envía el nombre de usuario al servidor
            pw.println(usuario);

            System.out.println("Conexion exitosa al servidor");

            // Bucle principal que se ejecuta hasta que el usuario escriba EXIT
            while (!comandoActual.equalsIgnoreCase("exit")) {

                // Muestra el menú de comandos disponibles
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

                // Pide al usuario que introduzca un comando
                System.out.println(usuario + "-console: ");
                comandoActual = sc.nextLine();

                // Envía el comando al servidor
                pw.println(comandoActual);

                // Si el comando es LISTCOCHES, el servidor enviará varias líneas
                if (comandoActual.equalsIgnoreCase("LISTCOCHES")) {

                    String lineaRecibida;

                    // Se leen todas las líneas hasta encontrar el símbolo "!"
                    while ((lineaRecibida = br.readLine()) != null) {
                        if (lineaRecibida.equals("!")) {
                            break;
                        }
                        System.out.println(lineaRecibida);
                    }

                } else {
                    // Para el resto de comandos, solo se lee una línea de respuesta
                    String respuesta = br.readLine();
                    if (respuesta != null && !respuesta.isEmpty()) {
                        System.out.println(respuesta);
                    }
                }
            }

        } catch (Exception e) {
            // Captura errores de conexión o comunicación
            System.out.println("Error al conectar con el servidor " + e.getMessage());
        }
    }
}
