// src/main/java/kanban/TestAlgorithms.java
package kanban;

import java.util.List;

import kanban.algorithms.TaskSorter;
import kanban.database.DatabaseManager;
import kanban.database.TaskDAO;
import kanban.models.Column;
import kanban.models.Task;

public class TestAlgorithms {
    public static void main(String[] args) {
        System.out.println("🧪 Probando Algoritmos de Ordenamiento...");

        // Inicializar BD
        DatabaseManager.initializeDatabase();

        TaskDAO taskDAO = new TaskDAO();

        // 1. Agregar tareas de prueba con diferentes prioridades
        taskDAO.addTask(new Task("Estudiar algoritmos", "Repasar sorts", 3, 1)); // Alta
        taskDAO.addTask(new Task("Hacer frontend", "Diseñar interfaz", 2, 1)); // Media
        taskDAO.addTask(new Task("Documentación", "Escribir readme", 1, 2)); // Baja
        taskDAO.addTask(new Task("Presentación PPT", "Preparar slides", 3, 1)); // Alta
        taskDAO.addTask(new Task("Revisar código", "Buscar bugs", 2, 3)); // Media

        // Al final del main() en TestAlgorithms.java
        System.out.println("\n📊 PROBANDO COLUMNAS:");
        var columnas = DatabaseManager.getColumns();
        for (Column columna : columnas) {
            System.out.println("📍 " + columna.getName() + " - Posición: " + columna.getPosition());
        }

        // 2. Obtener todas las tareas
        List<Task> todasTareas = taskDAO.getAllTasks();

        // Mostrar desordenadas
        TaskSorter.printTasks(todasTareas, "TAREAS DESORDENADAS");

        // 3. Aplicar Bubble Sort por prioridad
        TaskSorter.bubbleSortByPriority(todasTareas);
        TaskSorter.printTasks(todasTareas, "TAREAS ORDENADAS POR PRIORIDAD (Bubble Sort)");

        // 4. Aplicar Selection Sort por fecha
        TaskSorter.selectionSortByDate(todasTareas);
        TaskSorter.printTasks(todasTareas, "TAREAS ORDENADAS POR FECHA (Selection Sort)");

        System.out.println("✅ Algoritmos probados exitosamente!");
    }
}