import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

public class ElMenu extends JFrame {

    public static String patternNum = "([a-zA-Z_0-9])";
    public static String Text = null;

    public ElMenu() {
        setTitle("Regex");
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
        model.addColumn("No.Línea");
        model.addColumn("Cadena");
        model.addColumn("Tipo");

        Pattern pattern = Pattern.compile(patternNum);
        int i = 1;
        int line;
        Matcher matcher = pattern.matcher(inputText);
        while (matcher.find()) {
            String number = matcher.group();
            line = getLineParagraph(inputText, matcher.start());
            String type = isType(number);
            model.addRow(new Object[]{i, line, number, type});
            i++;
        }

        JTable table = new JTable(model);
        JOptionPane.showMessageDialog(null, new JScrollPane(table));
    }

    private static int getLineParagraph(String text, int position) {
        int lineNumber = 1;
        for (int i = 0; i < position; i++) {
            if (text.charAt(i) == '\n') {
                lineNumber++;
            }
        }
        return lineNumber;
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

