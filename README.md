# 🛠️ Gestión de Taller de Coches – Cliente/Servidor con Sockets

## 📌 Descripción General
Proyecto desarrollado en **Java** que implementa un sistema **cliente-servidor** mediante **sockets TCP**, permitiendo gestionar coches y sus reparaciones en un taller mecánico.

---

## 🎯 Objetivos del Sistema
- Gestionar coches en un taller mecánico.
- Permitir comunicación cliente-servidor mediante texto plano.
- Aplicar programación orientada a objetos.
- Trabajar con sockets TCP en Java.

---

## ⚙️ Tecnologías Utilizadas
- **Java SE**
- **Sockets TCP** (`ServerSocket`, `Socket`)
- **Entrada/Salida de texto** (`BufferedReader`, `PrintWriter`)
- **Colecciones** (`ArrayList`, `List`)
- **Programación Orientada a Objetos (POO)**

---

## ▶️ Instrucciones de Ejecución
1. Abrir el proyecto en **IntelliJ IDEA** o **Eclipse**.
2. Compilar todas las clases.
3. Ejecutar primero la clase:
   - `Server.java`
4. Ejecutar después la clase:
   - `Cliente.java`
5. Introducir el nombre de usuario solicitado.
6. Enviar comandos en el formato indicado.

---

## 🧾 Formato de Comandos
```text
ADDCOCHE,ID,Marca,Modelo,Año
GETCOCHE,ID
LISTCOCHES
EXIT
