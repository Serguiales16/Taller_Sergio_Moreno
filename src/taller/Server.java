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

    public static boolean cocheExistente = false;

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

        boolean existe = false;

        for (Coche coche : coches) {

            if (coche.getId().equalsIgnoreCase(comando[1].trim())) {

                existe = true;
                System.out.println("Error: Ya existe un coche con ese ID");
                break;
            }

        }
        String idCoche = comando[1];
        String marcaCoche = comando[2];
        String modeloCoche = comando[3];
        String añoCoche = comando[4];

        Coche c1 = new Coche(idCoche, marcaCoche, modeloCoche, añoCoche);

        if (existe) {
            return null;
        } else {

            return c1;
        }


    }

    public static String listarCoches() {

        StringBuilder cochesLista = new StringBuilder();

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

        cochesLista.append("\n!");
        return cochesLista.toString();
    }


    public static String verCoche(String[] comando) {

        StringBuilder cocheVer = new StringBuilder();

        for (Coche coche : coches) {

            if (coche.getId().equalsIgnoreCase(comando[1].trim())) {

                cocheVer.append("ID: ").append(coche.getId()).append(" Marca: ").append(coche.getMarca()).append(" Modelo: ").append(coche.getModelo()).append(" Año: ").append(coche.getAño());

            }

        }

        return cocheVer.toString();
    }

    public static Reparacion añadirReparacion(String[] comando) {

        String idCoche = comando[1];
        String descripcion = comando[2];
        float costo = Float.parseFloat(comando[3]);



        return new Reparacion(idCoche, descripcion, costo);
    }

    public static void eliminarCoche(String[] comando) {
        cocheExistente = false;
        boolean eliminado = false;

        for (Coche coche : coches) {

            if (coche.getId().equalsIgnoreCase(comando[1].trim())) {

                coches.remove(coche);
                System.out.println("Coche con ID " + comando[1] + " eliminado correctamente");
                eliminado = true;
                cocheExistente = true;
                break;

            }
        }

        if (!eliminado) {
            System.out.println("No se encontró un coche con ID " + comando[1]);

        }
    }

    public static void consola(String[] comandoActual, PrintWriter pw) {

        try {

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


                    }catch (Exception e) {
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
                    System.out.println("Comando invalido");
                    pw.println("Comando invalido");
                    return;


            }

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
            pw.println("Error, algo has hecho mal.RECUERDA(ADDCOCHE,ID,MARCA,MODELO,AÑO. Y los demas COMANDO,ID)" + e.getMessage());
            return;
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
