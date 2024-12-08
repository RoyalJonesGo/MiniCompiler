import java.util.*;

public class LexicalAnalyzer {
    private static final Set<String> keywords = Set.of("var");
    private static final Set<String> dataTypes = Set.of("int", "float", "string", "bool", "char");
    private static final Set<String> symbols = Set.of(":", ";", "=");
    private static final Set<String> booleanValues = Set.of("true", "false");

    public String analyze(String code) throws Exception {
        String[] lines = code.split("\\n");
        StringBuilder result = new StringBuilder();
        int lineNumber = 1;

        for (String line : lines) {
            String[] tokens = tokenize(line);
            for (String token : tokens) {
                if (!isValidToken(token)) {
                    throw new Exception("Lexical Error: Invalid token '" + token + "' on line " + lineNumber);
                }
                result.append("TOKEN: ").append(token).append("\n");
            }
            lineNumber++;
        }
        return result.toString();
    }

    private String[] tokenize(String line) {
        // add spaces around symbols to make sure they are separated as tokens
        line = line.replaceAll("([:;=])", " $1 ");
        return line.trim().split("\\s+");
    }

    private boolean isValidToken(String token) {
        return keywords.contains(token) || dataTypes.contains(token) || symbols.contains(token)
                || booleanValues.contains(token) || isValidIdentifier(token) || isValidNumber(token)
                || isValidString(token) || isValidChar(token);
    }

    private boolean isValidIdentifier(String token) {
        // variables names must start with a letter or underscore, then followed by alphanumerics or underscores
        return token.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }

    private boolean isValidNumber(String token) {
        // match both integer and floats
        return token.matches("\\d+(\\.\\d+)?");
    }

    private boolean isValidString(String token) {
        // double quotes for strings
        return token.matches("\".*\"");
    }

    private boolean isValidChar(String token) {
        // single character only with single quotes for char
        return token.matches("'.'");
    }
}
