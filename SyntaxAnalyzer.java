public class SyntaxAnalyzer {
    public String analyze(String code) throws Exception {
        String[] lines = code.split("\\n");
        for (String line : lines) {
            if (!line.matches("^var\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*:\\s*(int|float|string|bool|char)(\\s*=\\s*.+)?;$")) {
                throw new Exception("Syntax Error in line: " + line);
            }
        }
        return "Syntax Analysis Passed!";
    }
}
