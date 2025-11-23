package kanban.database;

import kanban.models.Task;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    // 1. INSERTAR NUEVA TAREA
    public void addTask(Task task) {
        String sql = "INSERT INTO tasks(title, description, priority, column_id) VALUES(?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setInt(3, task.getPriority());
            pstmt.setInt(4, task.getColumnId());
            pstmt.executeUpdate();

            System.out.println("✅ Tarea agregada: " + task.getTitle());

        } catch (SQLException e) {
            System.err.println("❌ Error agregando tarea: " + e.getMessage());
        }
    }

    // 2. OBTENER TAREAS POR COLUMNA
    public List<Task> getTasksByColumn(int columnId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE column_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, columnId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setPriority(rs.getInt("priority"));
                task.setColumnId(rs.getInt("column_id"));
                task.setCreatedAt(rs.getString("created_at"));

                tasks.add(task);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error obteniendo tareas: " + e.getMessage());
        }

        return tasks;
    }

    // 3. MOVER TAREA ENTRE COLUMNAS
    public void updateTaskColumn(int taskId, int newColumnId) {
        String sql = "UPDATE tasks SET column_id = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newColumnId);
            pstmt.setInt(2, taskId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("✅ Tarea " + taskId + " movida a columna " + newColumnId);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error moviendo tarea: " + e.getMessage());
        }
    }

    // 4. ELIMINAR TAREA
    public void deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, taskId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("✅ Tarea " + taskId + " eliminada de la BD");
            } else {
                System.out.println("❌ Tarea " + taskId + " no encontrada");
            }

        } catch (SQLException e) {
            System.err.println("❌ Error eliminando tarea: " + e.getMessage());
            throw new RuntimeException("Error al eliminar tarea de la BD");
        }
    }

    // 5. OBTENER TODAS LAS TAREAS (para algoritmos)
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection conn = DatabaseManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setPriority(rs.getInt("priority"));
                task.setColumnId(rs.getInt("column_id"));
                task.setCreatedAt(rs.getString("created_at"));

                tasks.add(task);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error obteniendo todas las tareas: " + e.getMessage());
        }

        return tasks;
    }
}