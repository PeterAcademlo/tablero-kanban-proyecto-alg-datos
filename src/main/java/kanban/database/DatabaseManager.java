package kanban.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import kanban.models.Column;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:kanban.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {

            // Crear tabla de columnas
            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS columns (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            name TEXT NOT NULL,
                            position INTEGER
                        )
                    """);

            // Crear tabla de tareas
            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS tasks (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            title TEXT NOT NULL,
                            description TEXT,
                            priority INTEGER DEFAULT 1,
                            column_id INTEGER,
                            created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (column_id) REFERENCES columns(id)
                        )
                    """);

            // Insertar columnas por defecto
            stmt.execute("INSERT OR IGNORE INTO columns (id, name, position) VALUES (1, 'Pendiente', 1)");
            stmt.execute("INSERT OR IGNORE INTO columns (id, name, position) VALUES (2, 'En Proceso', 2)");
            stmt.execute("INSERT OR IGNORE INTO columns (id, name, position) VALUES (3, 'Terminado', 3)");
            stmt.execute("INSERT OR IGNORE INTO columns (id, name, position) VALUES (4, 'Producción', 4)");

            System.out.println(" Base de datos inicializada correctamente");
            System.out.println(" Columnas creadas: Pendiente, En Proceso, Terminado, Producción");

        } catch (SQLException e) {
            System.err.println(" Error inicializando BD: " + e.getMessage());
        }
    }

    // ✅ AGREGA ESTE NUEVO MÉTODO - Obtener todas las columnas
    public static List<Column> getColumns() {
        List<Column> columns = new ArrayList<>();

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM columns ORDER BY position")) {

            while (rs.next()) {
                Column column = new Column();
                column.setId(rs.getInt("id"));
                column.setName(rs.getString("name"));
                column.setPosition(rs.getInt("position"));
                columns.add(column);
            }

        } catch (SQLException e) {
            System.err.println("Error obteniendo columnas: " + e.getMessage());
        }

        return columns;
    }
}