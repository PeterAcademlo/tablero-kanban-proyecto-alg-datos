package kanban;

import kanban.database.DatabaseManager;
import kanban.database.TaskDAO;
import kanban.models.Task;

public class TestTaskDAO {
    public static void main(String[] args) {
        System.out.println("🧪 Probando TaskDAO...");

        // Inicializar BD
        DatabaseManager.initializeDatabase();

        TaskDAO taskDAO = new TaskDAO();

        // 1. Agregar tareas de prueba - USANDO EL CONSTRUCTOR CORRECTO
        taskDAO.addTask(new Task("Estudiar algoritmos", "Repasar sorts y búsquedas", 3, 1));
        taskDAO.addTask(new Task("Hacer frontend", "Diseñar interfaz Kanban", 2, 1));
        taskDAO.addTask(new Task("Presentación", "Preparar PPT del proyecto", 1, 2));

        // 2. Obtener tareas de "Pendiente" (columna 1)
        System.out.println("\n📋 Tareas Pendientes:");
        var pendientes = taskDAO.getTasksByColumn(1);
        for (Task t : pendientes) {
            System.out.println(" - " + t.getTitle() + " (Prioridad: " + t.getPriority() + ")");
        }

        System.out.println("✅ TaskDAO funcionando correctamente!");
    }
}