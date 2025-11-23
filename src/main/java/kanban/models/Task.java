package kanban.models;

// import java.time.LocalDateTime; // ← ELIMINA ESTA LÍNEA

public class Task {
    private int id;
    private String title;
    private String description;
    private int priority; // 1: Baja, 2: Media, 3: Alta
    private int columnId; // Referencia a columns.id
    private String createdAt;

    // Constructores
    public Task() {
        this.createdAt = java.time.LocalDateTime.now().toString();
    }

    public Task(String title, String description, int priority, int columnId) {
        this();
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.columnId = columnId;
    }

    // ✅ GETTERS Y SETTERS CORREGIDOS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getColumnId() {
        return columnId;
    }

    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }

    // ✅ CORREGIDO - deben usar String, no LocalDateTime
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return title + " (Prioridad: " + priority + ")";
    }
}