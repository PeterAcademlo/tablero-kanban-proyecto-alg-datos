package kanban;

import com.sun.net.httpserver.HttpServer;

import kanban.database.DatabaseManager;
import kanban.handlers.TaskHandler;
import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleKanbanServer {
    public static void main(String[] args) throws IOException {
        DatabaseManager.initializeDatabase();

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/api/tasks", new TaskHandler());

        server.start();
        System.out.println("🚀 Servidor Kanban iniciado en http://localhost:8080");
        System.out.println("📡 Endpoints disponibles:");
        System.out.println("   GET  /api/tasks - Obtener todas las tareas");
        System.out.println("   POST /api/tasks - Agregar nueva tarea");
        System.out.println("   PUT  /api/tasks/move - Mover tarea entre columnas");
        System.out.println("   DELETE /api/tasks/{id} - Eliminar tarea");
        System.out.println("   GET  /api/tasks/buscar?q=texto - Buscar tareas por título");
    }
}
