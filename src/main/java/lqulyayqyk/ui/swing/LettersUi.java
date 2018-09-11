package lqulyayqyk.ui.swing;

import static java.util.Objects.requireNonNull;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.google.common.base.Strings;

import lqulyayqyk.model.FrequencyCount;
import lqulyayqyk.model.Model;
import lqulyayqyk.util.Util;

final class LettersUi {
    private Model model;
    private final List<Letter> letters = new ArrayList<>();
    
    public LettersUi(Model model) {
        for (char c = 'A'; c <= 'Z'; ++c) {
            letters.add(new Letter(c));
        }
        setModel(model);
    }

    public void setModel(Model model) {
        this.model = requireNonNull(model);
        FrequencyCount fc = this.model.getFrequencyCount();
        for (char c = 'A'; c <= 'Z'; ++c) {
            letters.get(c - 'A').setCount(fc.getCount(c));
        }
    }

    public JPanel getLayout() {
        GridLayout layout = new GridLayout(0, 3, 8, 8);
        JPanel panel = new JPanel(layout);
        letters.forEach(l -> l.layoutInGrid(panel));
        return panel;
    }
    
    private class Letter {
        private final JLabel label;
        private final JLabel count;
        private final JTextField substitutionField = new JTextField(new OneLetterDocument(), "", 2);
        
        public Letter(char c) {
            this.label = new JLabel(String.valueOf(c));
            this.count = new JLabel("0");
            this.substitutionField.getDocument().addDocumentListener(new DocumentListener() {
                
                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateModel();
                }
                
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateModel();
                }
                
                private void updateModel() {
                    EventQueue.invokeLater(() -> {
                        String text = substitutionField.getText().trim();
                        if (text.isEmpty()) {
                            model.remove(letter);
                        }
                    });
                }
                
                @Override
                public void changedUpdate(DocumentEvent e) {/**/}
            });
        }
        
        public void setCount(int count) {
            this.count.setText(String.valueOf(count));
        }
        
        public void layoutInGrid(JPanel parent) {
            parent.add(label);
            parent.add(count);
            parent.add(substitutionField);
        }
    }
    
    
    private static class OneLetterDocument extends PlainDocument {

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (Strings.isNullOrEmpty(str)) {
                return;
            }
            if (getLength() >= 1) {
                return;
            }
            if (str.length() != 1) {
                return;
            }
            if (Util.isLetter(str.charAt(0))) {
                super.insertString(offs, str.toUpperCase(), a);
            }
        }
    }
}
