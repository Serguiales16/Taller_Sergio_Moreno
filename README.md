# 🛠️ Gestión de Taller de Coches

## Descripción breve
Aplicación cliente/servidor en Java (Maven) que gestiona coches y reparaciones mediante comunicación por sockets TCP. El servidor mantiene listas en memoria de `Coche` y `Reparacion`. El cliente envía comandos en texto plano y muestra respuestas.

## Tecnologías
- Java SE
- Sockets TCP: `ServerSocket`, `Socket`
- I/O de texto: `BufferedReader`, `PrintWriter`
- Colecciones: `ArrayList`, `List`
- Build: Maven

## Estructura de archivos clave
- `src/taller/Server.java`
- `src/taller/Cliente.java`
- `src/taller/Coche.java`
- `src/taller/Reparacion.java`
- `README.md`

## Cómo ejecutar en cmd
1. Compilar con Maven:
   - `mvn package`
2. Desde IDE (IntelliJ): ejecutar primero `src/taller/Server.java`, luego `src/taller/Cliente.java`.
3. Desde línea de comandos (tras `mvn package`):
   - Iniciar servidor:
     - `java -cp target/classes taller.Server`
   - Iniciar cliente (en otra terminal):
     - `java -cp target/classes taller.Cliente`
4. Puerto por defecto: `5050`. Ejecutar `Server` antes que `Cliente`.

## Formato de comandos (entrada del cliente)
- `ADDCOCHE,ID,Marca,Modelo,Año`
- `GETCOCHE,ID`
- `LISTCOCHES`
- `ADDREPARACION,ID_COCHE,Descripcion,Costo` (pendiente en servidor)
- `REMOVECOCHE,ID` (pendiente en servidor)
- `EXIT`

Nota: Para `LISTCOCHES` el servidor envía varias líneas y finaliza la lista con una línea que contiene exactamente `!`.

## Flujo general
1. Cliente solicita nombre de usuario al iniciar y lo envía al servidor.
2. Servidor acepta la conexión y almacena el nombre de usuario.
3. Cliente envía comandos (líneas CSV); servidor procesa y responde.
4. `LISTCOCHES` se lee línea por línea en el cliente hasta recibir `!`. Otros comandos devuelven una sola línea.

## Documentación de métodos (resumen funcional)

### `src/taller/Server.java`
- `public static final int PUERTO`
  - Puerto del servidor (5050).
- `public static void iniciar() throws IOException`
  - Crea `ServerSocket`, acepta una conexión, lee la primera línea como nombre de usuario y la almacena en `usuario`.
- `public static String leerComando(Socket s, BufferedReader br) throws IOException`
  - Lee una línea del `BufferedReader`. Devuelve la línea leída o `null` si se cerró la entrada.
- `public static Coche añadirCoche(String[] comando)`
  - Construye y devuelve un `Coche` usando `comando[1]..comando[4]`.
- `public static String listarCoches()`
  - Recorre `Server.coches`, construye una cadena con una línea por coche y agrega `\n!` al final para indicar el fin.
- `public static String verCoche(String[] comando)`
  - Busca un coche por `id` igual a `comando[1]` y devuelve su representación en texto (cadena vacía si no existe).
- `public static void consola(String[] comandoActual, PrintWriter pw)`
  - Interpreta `comandoActual[0]` y ejecuta acciones:
    - `ADDCOCHE`: añade coche con `añadirCoche` y envía confirmación.
    - `REMOVECOCHE`: pendiente (debe borrar coche de la lista).
    - `GETCOCHE`: envía resultado de `verCoche`.
    - `LISTCOCHES`: envía resultado de `listarCoches()` (incluye `!`).
    - `ADDREPARACION`: pendiente (registrar reparación).
    - Comando desconocido: registro de error.
- `public static void main(String[] args) throws IOException`
  - Llama a `iniciar()`, crea `BufferedReader` y `PrintWriter` sobre el socket aceptado, y entra en bucle:
    - Lee comandos con `leerComando`, los divide por `","` y llama a `consola`.
    - Sale cuando el comando es `EXIT`.

### `src/taller/Cliente.java`
- `public static void main(String[] args)`
  - Pide nombre de usuario por consola y se conecta a `localhost:5050`.
  - Envía el nombre y entra en bucle de entrada de usuario hasta `exit`.
  - Envía cada comando al servidor.
  - Para `LISTCOCHES`: lee líneas del servidor hasta encontrar `!` y las imprime.
  - Para otros comandos: lee una sola línea de respuesta y la muestra si no está vacía.
  - Maneja excepciones de conexión y muestra errores breves.

### `src/taller/Coche.java`
- POJO con campos `id`, `marca`, `modelo`, `año`.
- Getters/setters y `toString()` para representar el coche.

### `src/taller/Reparacion.java`
- POJO con campos `idCoche`, `descripcion`, `costo`.
- Getters/setters; lista `Server.reparaciones` preparada para uso futuro.

## Notas y mejoras pendientes
- Implementar `REMOVECOCHE` en `Server.consola`.
- Implementar `ADDREPARACION` y listar/consultar reparaciones.
- Soporte para múltiples clientes y concurrencia (actualmente acepta una sola conexión).
- Persistencia de datos (archivo o base de datos).
- Validaciones y manejo de errores más robusto al parsear comandos.
- Validar índices y evitar `ArrayIndexOutOfBounds` al dividir comandos CSV.

## Comportamiento esperado y errores conocidos
- Si el cliente o servidor envían líneas nulas la comunicación puede terminar; `leerComando` devuelve `null`.
- `listarCoches()` añade `!` en una nueva línea para marcar fin; el cliente espera exactamente `!`.