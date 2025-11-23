// src/main/java/kanban/handlers/TaskHandler.java
package kanban.handlers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import kanban.database.TaskDAO;
import kanban.models.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class TaskHandler implements HttpHandler {
    private TaskDAO taskDAO = new TaskDAO();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Configurar CORS headers
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath(); // ← AGREGAR ESTA LÍNEA

        try {
            // 🔥 CAMBIA EL SWITCH POR IF-ELSE para manejar rutas
            if (path.equals("/api/tasks") && method.equals("GET")) {
                handleGetTasks(exchange);
            } else if (path.equals("/api/tasks") && method.equals("POST")) {
                handlePostTask(exchange);
            } else if (path.equals("/api/tasks/move") && method.equals("PUT")) {
                handleMoveTask(exchange);
            } else if (path.startsWith("/api/tasks/") && method.equals("DELETE")) {
                handleDeleteTask(exchange);
            } else if (path.equals("/api/tasks/buscar") && method.equals("GET")) { // ← NUEVA RUTA
                handleBuscarTareas(exchange);
            } else if (method.equals("OPTIONS")) {
                handleOptions(exchange);
            } else {
                sendResponse(exchange, 404, "{\"error\": \"Endpoint no encontrado\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"error\": \"Error interno del servidor\"}");
        }
    }

    private void handleGetTasks(HttpExchange exchange) throws IOException {
        List<Task> tasks = taskDAO.getAllTasks();
        String response = objectMapper.writeValueAsString(tasks);
        sendResponse(exchange, 200, response);
        System.out.println("📨 GET /api/tasks - " + tasks.size() + " tareas enviadas");
    }

    private void handlePostTask(HttpExchange exchange) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        Task newTask = objectMapper.readValue(requestBody, Task.class);

        taskDAO.addTask(newTask);

        sendResponse(exchange, 201, "{\"message\": \"Tarea creada exitosamente\"}");
        System.out.println("📨 POST /api/tasks - Nueva tarea: " + newTask.getTitle());
    }

    private void handleMoveTask(HttpExchange exchange) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        String body = new String(requestBody.readAllBytes());

        // Parsear manualmente el JSON simple
        int taskId = Integer.parseInt(body.split("\"taskId\":")[1].split(",")[0]);
        int newColumnId = Integer.parseInt(body.split("\"newColumnId\":")[1].split("}")[0]);

        taskDAO.updateTaskColumn(taskId, newColumnId);

        sendResponse(exchange, 200, "{\"message\": \"Tarea movida exitosamente\"}");
        System.out.println("📨 PUT /api/tasks/move - Tarea " + taskId + " movida a columna " + newColumnId);
    }

    private void handleDeleteTask(HttpExchange exchange) throws IOException {
        // Extraer el ID de la URL (ej: /api/tasks/5)
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");

        if (pathParts.length == 4) {
            try {
                int taskId = Integer.parseInt(pathParts[3]);
                taskDAO.deleteTask(taskId);
                sendResponse(exchange, 200, "{\"message\": \"Tarea eliminada exitosamente\"}");
                System.out.println("🗑️ DELETE /api/tasks/" + taskId + " - Tarea eliminada");
            } catch (NumberFormatException e) {
                sendResponse(exchange, 400, "{\"error\": \"ID de tarea inválido\"}");
            }
        } else {
            sendResponse(exchange, 400, "{\"error\": \"URL inválida\"}");
        }
    }

    private void handleOptions(HttpExchange exchange) throws IOException {
        sendResponse(exchange, 200, "");
    }

    private void handleBuscarTareas(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        String textoBusqueda = null;

        // Extraer parámetro 'q' de la URL
        if (query != null && query.contains("q=")) {
            String[] partes = query.split("q=");
            if (partes.length > 1) {
                textoBusqueda = partes[1];
                // Decodificar espacios y caracteres especiales
                textoBusqueda = java.net.URLDecoder.decode(textoBusqueda, "UTF-8");
            }
        }

        List<Task> todasTareas = taskDAO.getAllTasks();
        List<Task> resultados = kanban.algorithms.TaskSearch.buscarPorTitulo(textoBusqueda, todasTareas);

        String response = objectMapper.writeValueAsString(resultados);
        sendResponse(exchange, 200, response);

        System.out.println(
                "🔍 GET /api/tasks/buscar - Búsqueda: '" + textoBusqueda + "' → " + resultados.size() + " resultados");
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}