package kanban.algorithms;

import java.util.List;
import java.util.stream.Collectors;
import kanban.models.Task;

public class TaskSearch {
    
    // ✅ BUSCAR SOLO POR TÍTULO - MUY SIMPLE
    public static List<Task> buscarPorTitulo(String texto, List<Task> todasLasTareas) {
        // Si no escribió nada, mostrar todas
        if (texto == null || texto.trim().isEmpty()) {
            return todasLasTareas;
        }
        
        String textoMinusculas = texto.toLowerCase().trim();
        
        // Filtrar tareas que contengan el texto en el TÍTULO solamente
        return todasLasTareas.stream()
            .filter(tarea -> 
                tarea.getTitle().toLowerCase().contains(textoMinusculas)
            )
            .collect(Collectors.toList());
    }
}