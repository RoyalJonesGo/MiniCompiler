import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MiniCompiler {
    private JFrame frame;
    private JTextArea codeTextArea;
    private JTextArea resultTextArea;
    private JButton openFileBtn, lexBtn, syntaxBtn, semanticBtn, clearBtn;
    private File currentFile;

    public MiniCompiler() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Mini Compiler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Code and Result Text Areas
        codeTextArea = new JTextArea();
        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);

        // Buttons
        openFileBtn = new JButton("Open File");
        lexBtn = new JButton("Lexical Analysis");
        syntaxBtn = new JButton("Syntax Analysis");
        semanticBtn = new JButton("Semantic Analysis");
        clearBtn = new JButton("Clear");

        // Disable buttons initially
        lexBtn.setEnabled(false);
        syntaxBtn.setEnabled(false);
        semanticBtn.setEnabled(false);

        // Vertical button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 rows, equal spacing
        buttonPanel.setPreferredSize(new Dimension(200, 0)); // Fixed width for left panel
        buttonPanel.add(openFileBtn);
        buttonPanel.add(lexBtn);
        buttonPanel.add(syntaxBtn);
        buttonPanel.add(semanticBtn);
        buttonPanel.add(clearBtn);

        // Add labels for clarity
        JPanel codePanel = new JPanel(new BorderLayout());
        codePanel.add(new JLabel("Code Text Area:"), BorderLayout.NORTH);
        codePanel.add(new JScrollPane(codeTextArea), BorderLayout.CENTER);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.add(new JLabel("Result:"), BorderLayout.NORTH);
        resultPanel.add(new JScrollPane(resultTextArea), BorderLayout.CENTER);

        // Layout of the main frame
        frame.setLayout(new BorderLayout());
        frame.add(buttonPanel, BorderLayout.WEST); // Buttons on the left
        frame.add(codePanel, BorderLayout.CENTER); // Code text area in the center
        frame.add(resultPanel, BorderLayout.SOUTH); // Result at the bottom

        // Add action listeners for buttons
        addEventHandlers();

        frame.setVisible(true);
    }

    private void addEventHandlers() {
        openFileBtn.addActionListener(e -> openFile());
        lexBtn.addActionListener(e -> performLexicalAnalysis());
        syntaxBtn.addActionListener(e -> performSyntaxAnalysis());
        semanticBtn.addActionListener(e -> performSemanticAnalysis());
        clearBtn.addActionListener(e -> clearAll());
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
                codeTextArea.setText("");
                String line;
                while ((line = reader.readLine()) != null) {
                    codeTextArea.append(line + "\n");
                }
                resultTextArea.setText("File loaded successfully.");
                lexBtn.setEnabled(true);
            } catch (IOException ex) {
                resultTextArea.setText("Error reading file: " + ex.getMessage());
            }
        }
    }

    private void performLexicalAnalysis() {
        String code = codeTextArea.getText();
        try {
            LexicalAnalyzer lexer = new LexicalAnalyzer();
            String tokens = lexer.analyze(code);
            resultTextArea.setText(tokens);
            lexBtn.setEnabled(false);
            syntaxBtn.setEnabled(true);
        } catch (Exception ex) {
            resultTextArea.setText("Lexical Analysis failed: " + ex.getMessage());
        }
    }

    private void performSyntaxAnalysis() {
        String code = codeTextArea.getText();
        try {
            SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer();
            String result = syntaxAnalyzer.analyze(code);
            resultTextArea.setText(result);
            syntaxBtn.setEnabled(false);
            semanticBtn.setEnabled(true);
        } catch (Exception ex) {
            resultTextArea.setText("Syntax Analysis failed: " + ex.getMessage());
        }
    }

    private void performSemanticAnalysis() {
        String code = codeTextArea.getText();
        try {
            SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
            String result = semanticAnalyzer.analyze(code);
            resultTextArea.setText(result);
            semanticBtn.setEnabled(false);
        } catch (Exception ex) {
            resultTextArea.setText("Semantic Analysis failed: " + ex.getMessage());
        }
    }

    private void clearAll() {
        codeTextArea.setText("");
        resultTextArea.setText("");
        currentFile = null;
        lexBtn.setEnabled(false);
        syntaxBtn.setEnabled(false);
        semanticBtn.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MiniCompiler::new);
    }
}
