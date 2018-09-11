package lqulyayqyk.ui.swing;

import static java.util.Objects.requireNonNull;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import lqulyayqyk.model.Model;

public final class Canvas {
    private Model model = new Model("");
    
    private final JTextArea encryptedTextArea = createTextArea();
    private final JTextArea decryptedTextArea = createTextArea();
    private final LettersUi lettersUi = new LettersUi(new Model(""));

    public Canvas() {
        encryptedTextArea.setEditable(false);
        decryptedTextArea.setEditable(false);
        setModel(model);
    }

    private static JTextArea createTextArea() {
        JTextArea ta = new JTextArea(8,  64);
        ta.setEditable(false);
        return ta;
    }
    
    public void setModel(Model model) {
        this.model = requireNonNull(model);
        encryptedTextArea.setText(model.getEncryptedText());
        decryptedTextArea.setText(model.getClearText());
        lettersUi.setModel(model);
    }
    
    public JPanel getLayout() {
        JPanel left = new JPanel(new GridLayout(2, 1, 0, 32));
        left.add(new JScrollPane(encryptedTextArea));
        left.add(new JScrollPane(decryptedTextArea));
        JPanel right = lettersUi.getLayout();
        JPanel ui = new JPanel(new GridLayout(1, 2, 32, 0));
        ui.add(left);
        ui.add(right);
        return ui;
    }
}
