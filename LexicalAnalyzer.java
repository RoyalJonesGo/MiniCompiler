import java.util.*;

public class LexicalAnalyzer {
    public String analyze(String code) throws Exception {
        String[] lines = code.split("\\n");
        StringBuilder result = new StringBuilder();
        for (String line : lines) {
            String[] tokens = line.trim().split("\\s+");
            for (String token : tokens) {
                result.append("TOKEN: ").append(token).append("\n");
            }
        }
        return result.toString();
    }
}
