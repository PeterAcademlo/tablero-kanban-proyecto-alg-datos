FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiar todo
COPY . .

# Compilar y ejecutar directamente SIN crear JAR
RUN find src -name "*.java" > sources.txt
RUN javac -cp "lib/*" -d target @sources.txt

EXPOSE 8080

# Ejecutar directamente las clases compiladas
CMD ["java", "-cp", "target:lib/*", "kanban.SimpleKanbanServer"]