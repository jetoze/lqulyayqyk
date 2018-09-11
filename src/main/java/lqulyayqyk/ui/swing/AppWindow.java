package lqulyayqyk.ui.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;

import lqulyayqyk.model.Model;

public final class AppWindow {

    public AppWindow() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Substitution Cipher");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Canvas canvas = new Canvas();
            canvas.setModel(new Model("lqulyayqyk"));
            frame.getContentPane().add(canvas.getLayout(), BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
        });
    }
    
    
}
