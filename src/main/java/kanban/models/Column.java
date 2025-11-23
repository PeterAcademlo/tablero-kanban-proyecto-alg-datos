// src/main/java/kanban/models/Column.java
package kanban.models;

public class Column {
    private int id;
    private String name;
    private int position;
    
    // Constructores
    public Column() {}
    
    public Column(String name, int position) {
        this.name = name;
        this.position = position;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
    
    @Override
    public String toString() {
        return name + " (Posición: " + position + ")";
    }
}