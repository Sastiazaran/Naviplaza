# Naviplaza

Multi-agent Christmas mall simulation for a parallel programming course. Shopkeepers (`Vendedora`), clients, and Santas run as threads, coordinated with semaphores. A Swing control panel shows live state, lets you kill an agent (everyone else panics), and opens simple animation windows.

Works on **Windows, macOS, and Linux**. Images are loaded from the classpath (not `./src/...` files), so the app runs from any working directory or from the packaged JAR.

## Requirements

- Java 17 or newer (`java -version`)
- No Maven install needed — the Maven Wrapper is included

## Build and run

From the repository root:

```bash
./mvnw test
./mvnw package
java -jar target/naviplaza.jar
```

Windows:

```bat
mvnw.cmd test
mvnw.cmd package
java -jar target\naviplaza.jar
```

IDE: open the folder in VS Code / IntelliJ and run `App`. The project follows the standard Maven layout (`src/main/java`, `src/main/resources`).

## How to use

1. Set how many shopkeepers, clients, and Santas to spawn, plus the event delay in milliseconds (defaults: 5 / 10 / 2 / 500).
2. Click the Naviplaza start button.
3. Click an agent icon to open that group's status table and animation.
4. Use **Kill agent** with a 0-based index to stop one agent; the others enter `PANICO`.

Closing the credits window or a table does not quit the whole app. Close the main **Naviplaza** window to exit.
