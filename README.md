🛠️ Gestión de Taller de Coches – Cliente/Servidor con Sockets

Proyecto desarrollado en Java que implementa un sistema cliente-servidor mediante sockets TCP, permitiendo gestionar coches y sus reparaciones en un taller mecánico.

📌 Descripción General

Este sistema permite a un cliente conectarse a un servidor para:

Añadir coches al taller.

Consultar un coche por su ID.

Listar todos los coches registrados.

Gestionar reparaciones (estructura preparada).

Mantener comunicación en tiempo real mediante texto plano.

El servidor gestiona los datos en memoria y responde a los comandos enviados por el cliente.

⚙️ Tecnologías Utilizadas

Java SE

Sockets TCP (ServerSocket, Socket)

Entrada/Salida de texto (BufferedReader, PrintWriter)

Colecciones (ArrayList, List)

Programación orientada a objetos (POO)

Protocolo texto plano cliente-servidor

▶️ Instrucciones de Ejecución

Clonar o descargar el proyecto.

Abrir el proyecto en un IDE compatible (IntelliJ IDEA / Eclipse).

Compilar todas las clases.

Ejecutar primero:

Server.java

Ejecutar después:

Cliente.java

Introducir el nombre de usuario cuando lo solicite el cliente.

Introducir comandos según el formato indicado.

🧾 Formato de Comandos
ADDCOCHE,ID,Marca,Modelo,Año
GETCOCHE,ID
LISTCOCHES
EXIT


Ejemplo:

ADDCOCHE,C123,Ford,Focus,2018

🖥️ Funcionamiento del Servidor

El servidor:

Escucha conexiones en un puerto fijo.

Acepta un único cliente (monocliente).

Lee comandos en formato texto.

Procesa los datos en memoria.

Envía respuestas al cliente según el comando recibido.

📂 Clase Server
iniciar()

Inicia el ServerSocket.

Espera la conexión de un cliente.

Lee el nombre de usuario enviado por el cliente.

Deja el socket listo para la comunicación.

leerComando(Socket, BufferedReader)

Lee una línea enviada por el cliente.

Devuelve el comando completo en formato texto.

Controla desconexiones mediante valores null.

añadirCoche(String[])

Recibe el comando dividido.

Extrae los campos correspondientes al coche.

Crea y devuelve un objeto Coche.

listarCoches()

Recorre la lista de coches almacenados.

Genera una cadena con todos los coches.

Envía cada coche en una línea.

Finaliza el listado con el símbolo ! para marcar el fin.

verCoche(String[])

Busca un coche por su ID.

Devuelve la información del coche si existe.

Permite consultar datos concretos sin listar todos.

consola(String[], PrintWriter)

Interpreta el comando recibido.

Ejecuta la acción correspondiente.

Envía siempre una respuesta al cliente.

Centraliza la lógica del servidor.

main(String[])

Arranca el servidor.

Inicializa los streams de entrada/salida.

Entra en un bucle de lectura de comandos.

Finaliza la conexión cuando recibe EXIT.

💻 Funcionamiento del Cliente

El cliente:

Se conecta al servidor mediante sockets.

Envía el nombre de usuario al conectarse.

Presenta un menú de comandos.

Envía comandos al servidor.

Lee y muestra las respuestas del servidor.

📂 Clase Cliente
main(String[])

Solicita el nombre del usuario.

Establece conexión con el servidor.

Muestra el menú de comandos.

Envía los comandos escritos por el usuario.

Lee respuestas del servidor:

Una sola línea para comandos normales.

Varias líneas hasta ! para LISTCOCHES.

Finaliza la conexión con EXIT.

🔄 Protocolo de Comunicación

El cliente envía un comando.

El servidor procesa un comando.

El servidor responde:

Con una línea para respuestas simples.

Con varias líneas + ! para listas.

El cliente lee según el tipo de respuesta.

La conexión permanece abierta hasta EXIT.
