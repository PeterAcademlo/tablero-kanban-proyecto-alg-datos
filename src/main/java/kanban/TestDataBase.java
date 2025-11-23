package kanban;

import kanban.database.DatabaseManager;

public class TestDataBase {
     public static void main(String[] args) {
        System.out.println("🧪 Probando base de datos Kanban...");
        DatabaseManager.initializeDatabase();
        System.out.println("✅ Tablas creadas exitosamente!");
    }
}
