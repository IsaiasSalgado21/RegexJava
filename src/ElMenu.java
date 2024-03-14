import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

public class ElMenu extends JFrame {

    public static String Text = null;

    public ElMenu() {
        setTitle("Escáner DML");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JButton sendButton = new JButton("Analizar");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Text = textArea.getText();
                processAnalysis(Text);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sendButton);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }

    private static void processAnalysis(String inputText) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No. Línea");
        model.addColumn("TOKEN");
        model.addColumn("Tipo");
        model.addColumn("Código");

        String[] lines = inputText.split("\n");
        int lineCount = 1;

        for (String line : lines) {
            line = line.trim();
            if (!line.isEmpty()) {
                String[] tokens = line.split("\\s+");
                for (String token : tokens) {
                    String[] info = getTokenInfo(token);
                    model.addRow(new Object[]{lineCount, token, info[0], info[1]});
                }
                lineCount++;
            }
        }

        JTable table = new JTable(model);
        JOptionPane.showMessageDialog(null, new JScrollPane(table));
    }

    private static String[] getTokenInfo(String token) {
        switch (token) {
            case "SELECT":
            case "FROM":
            case "WHERE":
            case "AND":
                return new String[]{"Palabras Reservadas", "1"};
            case "ANOMBRE":
            case "CALIFICACION":
            case "TURNO":
            case "ALUMNOS":
            case "INSCRITOS":
            case "MATERIAS":
            case "CARRERAS":
            case "MNOMBRE":
            case "CNOMBRE":
            case "SEMESTRE":
                return new String[]{"Identificador", "4"};
            case ",":
            case "=":
                return new String[]{"Operador", "5"};
            case "'":
                return new String[]{"Delimitadores", "54"};
            case ">=":
            case "<=":
                return new String[]{"Relacionales", "84"};
            case "70":
                return new String[]{"Constante", "604"};
            default:
                if (token.matches("\\b\\w+\\b")) {
                    return new String[]{"Palabra", "99"}; // Assuming code 99 for undefined words
                } else {
                    return new String[]{"Inválido", "-1"}; // Assuming code -1 for invalid tokens
                }
        }
    }
    public static String isType(String num) {
        if (isReal(num)) {
            return "Real";
        } else if (isNatural(num)) {
            return "Natural";
        } else if (isExponential(num)) {
            return "Exponential";
        } else if (isPercentage(num)) {
            return "Porcentaje";
        } else if (isWord(num)) {
            return "palabra";
        }
        return "Inválido";
    }

    public static boolean isReal(String num) {
        return num.matches("\\b\\d+[,.]\\d+\\b");
    }
    public static boolean isWord(String num) {
        return num.matches("([a-z])");
    }

    public static boolean isNatural(String num) {
        return num.matches("\\b\\d+\\b");
    }

    public static boolean isExponential(String num) {
        return num.matches("\\b\\d+[,.]\\d+E\\d+\\b");
    }

    public static boolean isPercentage(String num) {
        return num.matches("\\b\\d+\\b%");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ElMenu main = new ElMenu();
            }
        });
    }
}
