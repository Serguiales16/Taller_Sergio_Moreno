# 🛠️ Gestión de Taller de Coches

## Descripción
Aplicación cliente/servidor en Java (Maven) que gestiona coches y reparaciones mediante sockets TCP.  
El servidor mantiene listas en memoria de `Coche` y `Reparacion`. El cliente envía comandos en texto plano y muestra respuestas. Comunicación simple por líneas de texto; puerto por defecto `5050`.

## Tecnologías
- Java SE
- Sockets TCP: `ServerSocket`, `Socket`
- I/O de texto: `BufferedReader`, `PrintWriter`
- Colecciones: `ArrayList`, `List`
- Build: Maven

## Estructura de ficheros clave
- `src/taller/Server.java`
- `src/taller/Cliente.java`
- `src/taller/Coche.java`
- `src/taller/Reparacion.java`
- `README.md`

## Cómo ejecutar
1. Compilar con Maven:
   - `mvn package`
2. Desde IDE (IntelliJ): ejecutar primero `src/taller/Server.java`, después `src/taller/Cliente.java`.
3. Desde línea de comandos (tras `mvn package`):
   - Iniciar servidor:
     - `java -cp target/classes taller.Server`
   - Iniciar cliente (en otra terminal):
     - `java -cp target/classes taller.Cliente`
4. Puerto por defecto: `5050`. Ejecutar `Server` antes que `Cliente`.

## Formato de comandos (entrada del cliente)
El servidor interpreta líneas CSV separadas por comas. Formatos esperados:
- `ADDCOCHE,ID,Marca,Modelo,Año`
- `GETCOCHE,ID`
- `LISTCOCHES`
- `ADDREPARACION,ID_COCHE,Descripcion,Costo`
- `REMOVECOCHE,ID`
- `EXIT`

Nota: Para `LISTCOCHES` el servidor envía varias líneas y finaliza la lista con una línea que contiene exactamente `!`. El cliente lee hasta esa línea.

## Flujo general
1. El cliente pide un nombre de usuario al iniciar y lo envía al servidor como primera línea.
2. El servidor acepta la conexión, lee ese nombre y lo muestra por consola.
3. El cliente envía comandos (líneas CSV). El servidor procesa y responde por línea(s).
4. `LISTCOCHES` devuelve varias líneas y termina con `!`. Otros comandos devuelven una sola línea de respuesta.

## Resumen funcional de clases y métodos

### `src/taller/Server.java`
- `public static final int PUERTO`
  - Puerto del servidor (`5050`).
- `public static void iniciar() throws IOException`
  - Crea `ServerSocket`, acepta la primera conexión entrante, lee la primera línea como nombre de usuario y la almacena en `usuario`.
- `public static String leerComando(Socket s, BufferedReader br) throws IOException`
  - Lee una línea desde el `BufferedReader`. Devuelve la línea o `null` si la entrada se cerró.
- `public static Coche añadirCoche(String[] comando)`
  - Crea (sin añadir automáticamente) un `Coche` a partir de `comando[1]..comando[4]`. Si ya existe un coche con ese ID devuelve `null`.
- `public static String listarCoches()`
  - Recorre la lista `coches` y construye una cadena con una línea por coche. Añade `\n!` al final para indicar fin de lista.
- `public static String verCoche(String[] comando)`
  - Busca un coche por `id` igual a `comando[1]` y devuelve su representación en texto (cadena vacía si no existe).
- `public static Reparacion añadirReparacion(String[] comando)`
  - Crea y devuelve un objeto `Reparacion` usando `comando[1]..comando[3]`.
- `public static void eliminarCoche(String[] comando)`
  - Elimina el primer coche que coincida con `comando[1]` de la lista `coches` (imprime mensajes en consola).
- `public static void consola(String[] comandoActual, PrintWriter pw)`
  - Interpreta el comando (`comandoActual[0]`) y realiza acciones: `ADDCOCHE`, `REMOVECOCHE`, `GETCOCHE`, `LISTCOCHES`, `ADDREPARACION` o `default`. Envía respuestas al cliente vía `pw`.
- `public static void main(String[] args) throws IOException`
  - Inicializa servidor con `iniciar()`, crea `BufferedReader`/`PrintWriter` sobre el socket aceptado y entra en bucle: lee comando con `leerComando`, divide por `","` y llama a `consola`. Termina cuando recibe `EXIT`.

### `src/taller/Cliente.java`
- `public static void main(String[] args)`
  - Solicita nombre de usuario por consola y se conecta a `localhost:5050`.
  - Envía el nombre y entra en bucle de lectura de comandos hasta `exit`.
  - Envía cada línea al servidor.
  - Para `LISTCOCHES`: lee líneas hasta `!` e imprime cada una.
  - Para otros comandos: lee una sola línea de respuesta y la imprime si no está vacía.
  - Maneja excepciones de conexión mostrando mensajes simples.

### `src/taller/Coche.java`
- POJO con campos `id`, `marca`, `modelo`, `año`. Incluye getters, setters y `toString()`.

### `src/taller/Reparacion.java`
- POJO con campos `idCoche`, `descripcion`, `costo`. Incluye getters y setters.

## Comportamiento actual y errores conocidos
- El servidor acepta una sola conexión (no hay concurrencia ni gestión de múltiples clientes).
- `REMOVECOCHE` depende de la bandera `cocheExistente` y su lógica actual puede impedir eliminar coches correctamente (la bandera no refleja si existe el coche antes del intento).
- `ADDCOCHE` crea el objeto de comprobación y luego vuelve a llamar a la función que crea otro objeto al añadirlo a la lista (se crea dos veces en código, aunque solo se añade uno); riesgo de inconsistencias si se modifica la lógica.
- Falta validación robusta de parámetros (posibles `ArrayIndexOutOfBounds` al parsear comandos CSV).
- No hay persistencia: `coches` y `reparaciones` solo existen en memoria.
- Manejo de errores y mensajes al cliente muy básico; logging limitado a consola.
- Cliente muestra un menú con comandos sin parámetros; el usuario debe enviar la línea en formato CSV cuando corresponda.

## Mejoras recomendadas
- Corregir la lógica de `REMOVECOCHE` para buscar y eliminar por ID sin depender de una bandera global.
- Validar el número de parámetros y el formato antes de acceder a índices del array.
- Soporte para múltiples clientes (threads para cada conexión).
- Persistencia (archivo plano, JSON o base de datos).
- Mejorar protocolización: usar prefijos de longitud, JSON o códigos de respuesta para reducir ambigüedad.
- Mejor manejo de errores y mensajes de retorno consistentes desde el servidor.

## Ejemplo rápido de uso
1. Ejecutar servidor.
2. Ejecutar cliente y enviar usuario.
3. En el cliente enviar:
   - `ADDCOCHE,1,Toyota,Corolla,2020`
   - `LISTCOCHES` (el cliente imprimirá cada línea hasta `!`)
   - `GETCOCHE,1`
   - `REMOVECOCHE,1`
   - `EXIT`