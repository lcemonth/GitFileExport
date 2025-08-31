import java.io.*;
import java.nio.file.*;
import java.util.*;

public class EntityGenerator {

    public static void main(String[] args) throws IOException {
        String filePath = System.getProperty("user.home") + "/Desktop/sqlName.txt";
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        if (lines.size() < 2) {
            throw new IllegalArgumentException("檔案格式不正確，請確認至少有資料表名稱與欄位資料");
        }

        String tableName = lines.get(0).trim();
        String className = toCamelCase(tableName, true);

        StringBuilder sb = new StringBuilder();
        sb.append("import jakarta.persistence.*;\n");
        sb.append("import java.time.*;\n\n");
        sb.append("@Entity\n");
        sb.append("@Table(name = \"" + tableName + "\")\n");
        sb.append("public class " + className + " {\n\n");

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            if (parts.length < 2) continue;

            String sqlType = parts[0];
            String columnName = parts[1];

            String javaType = mapSqlTypeToJava(sqlType);
            String fieldName = toCamelCase(columnName, false);

            sb.append("    @Column(name = \"" + columnName + "\")\n");
            sb.append("    private " + javaType + " " + fieldName + ";\n\n");
        }

        sb.append("}\n");

        // 輸出檔案
        String outputPath = System.getProperty("user.home") + "/Desktop/" + className + ".java";
        Files.write(Paths.get(outputPath), sb.toString().getBytes());

        System.out.println("Entity 已產生：" + outputPath);
    }

    private static String mapSqlTypeToJava(String sqlType) {
        sqlType = sqlType.toLowerCase();
        if (sqlType.startsWith("varchar")) return "String";
        if (sqlType.equals("data")) return "LocalDate";
        if (sqlType.equals("timestamp")) return "LocalDateTime";
        return "String"; // 預設
    }

    private static String toCamelCase(String input, boolean capitalizeFirst) {
        input = input.toLowerCase();
        String[] parts = input.split("_");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (i == 0 && !capitalizeFirst) {
                sb.append(part);
            } else {
                sb.append(part.substring(0, 1).toUpperCase()).append(part.substring(1));
            }
        }
        return sb.toString();
    }
}
