package kanban.algorithms;

import java.util.List;

import kanban.models.Task;

public class TaskSorter {

    // 1. BUBBLE SORT - Ordenar por prioridad (Mayor a Menor)
    public static void bubbleSortByPriority(List<Task> tasks) {
        System.out.println("🔄 Aplicando Bubble Sort por prioridad...");

        int n = tasks.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (tasks.get(j).getPriority() < tasks.get(j + 1).getPriority()) {
                    // Intercambiar tareas
                    Task temp = tasks.get(j);
                    tasks.set(j, tasks.get(j + 1));
                    tasks.set(j + 1, temp);
                }
            }
        }
    }

    // 2. SELECTION SORT - Ordenar por fecha (Más reciente primero) - CORREGIDO
    public static void selectionSortByDate(List<Task> tasks) {
        System.out.println("🔄 Aplicando Selection Sort por fecha...");

        int n = tasks.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                // Buscar la tarea más reciente - COMPARAR STRINGS
                if (tasks.get(j).getCreatedAt().compareTo(tasks.get(minIndex).getCreatedAt()) > 0) {
                    minIndex = j;
                }
            }
            // Intercambiar
            Task temp = tasks.get(minIndex);
            tasks.set(minIndex, tasks.get(i));
            tasks.set(i, temp);
        }
    }

    // 3. MÉTODO PARA MOSTRAR TAREAS ORDENADAS
    public static void printTasks(List<Task> tasks, String message) {
        System.out.println("\n📋 " + message);
        System.out.println("┌───┬──────────────────────────┬──────────┬──────────┐");
        System.out.println("│ # │ Título                   │ Prioridad│ Columna  │");
        System.out.println("├───┼──────────────────────────┼──────────┼──────────┤");

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String priorityStr = switch (task.getPriority()) {
                case 1 -> "Baja";
                case 2 -> "Media";
                case 3 -> "Alta";
                default -> "Desconocida";
            };

            String columnStr = switch (task.getColumnId()) {
                case 1 -> "Pendiente";
                case 2 -> "En Proceso";
                case 3 -> "Terminado";
                case 4 -> "Producción";
                default -> "Desconocida";
            };

            System.out.printf("│ %d │ %-24s │ %-8s │ %-8s │\n",
                    i + 1,
                    task.getTitle().length() > 24 ? task.getTitle().substring(0, 21) + "..." : task.getTitle(),
                    priorityStr,
                    columnStr);
        }
        System.out.println("└───┴──────────────────────────┴──────────┴──────────┘");
    }
}