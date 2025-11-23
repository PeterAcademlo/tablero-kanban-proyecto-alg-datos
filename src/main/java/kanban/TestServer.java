
package kanban;

public class TestServer {
    public static void main(String[] args) {
        try {
            System.out.println("🧪 Iniciando servidor de prueba...");
            SimpleKanbanServer.main(args);

            System.out.println("\n✅ Servidor funcionando correctamente!");
            System.out.println("🌐 Abre tu navegador en: http://localhost:8080/api/tasks");
            System.out.println("💡 Para detener el servidor: Ctrl + C");

        } catch (Exception e) {
            System.err.println("❌ Error iniciando servidor: " + e.getMessage());
        }
    }
}