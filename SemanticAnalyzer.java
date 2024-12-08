import java.util.*;

public class SemanticAnalyzer {
    public String analyze(String code) throws Exception {
        String[] lines = code.split("\\n");
        Set<String> declaredVariables = new HashSet<>();
        for (String line : lines) {
            String[] parts = line.split("\\s+");
            String variable = parts[1]; // extract variable name
            String type = line.split(":")[1].split("=")[0].trim(); // extract type
            if (declaredVariables.contains(variable)) {
                throw new Exception("Semantic Error: Variable " + variable + " redeclared.");
            }
            if (line.contains("=")) {
                String value = line.split("=")[1].trim().replace(";", "");
                if (!isTypeCompatible(type, value)) {
                    throw new Exception("Semantic Error: Type mismatch in line: " + line);
                }
            }
            declaredVariables.add(variable);
        }
        return "Semantic Analysis Passed!";
    }

    private boolean isTypeCompatible(String type, String value) {
        switch (type) {
            case "int":
                return value.matches("\\d+");
            case "float":
                return value.matches("\\d+\\.\\d+");
            case "string":
                return value.matches("\".*\"");
            case "bool":
                return value.matches("true|false");
            case "char":
                return value.matches("'.'");
            default:
                return false;
        }
    }
}
