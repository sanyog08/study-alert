package com.studyalert;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

public class FullScreenNag {
    public static final String ESCAPE_PHRASE = "I will study later";
    private static JFrame window = null;

    public static void show(Runnable onStartStudying, Runnable onEscape) {
        if (window != null) {
            return;
        }
        window = new JFrame();
        window.setUndecorated(true);
        window.setAlwaysOnTop(true);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        window.setBounds(0, 0, screen.width, screen.height);
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        JLabel title = new JLabel("GO STUDY.");
        title.setFont(new Font("SansSerif", Font.BOLD, 72));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton startButton = new JButton("Start studying");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 28));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> onStartStudying.run());
        JLabel hint = new JLabel("Emergency? Type \"" + ESCAPE_PHRASE + "\" and press Enter:");
        hint.setForeground(Color.WHITE);
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField phraseField = new JTextField(20);
        phraseField.setMaximumSize(phraseField.getPreferredSize());
        phraseField.setAlignmentX(Component.CENTER_ALIGNMENT);
        phraseField.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        phraseField.addActionListener(e -> {
            if (phraseField.getText().trim().equalsIgnoreCase(ESCAPE_PHRASE)) {
                onEscape.run();
            } else {
                phraseField.setText("");
            }
        });
        content.add(title);
        content.add(Box.createVerticalStrut(40));
        content.add(startButton);
        content.add(Box.createVerticalStrut(60));
        content.add(hint);
        content.add(Box.createVerticalStrut(10));
        content.add(phraseField);
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(new Color(140, 0, 0));
        background.add(content);
        window.setContentPane(background);
        window.setVisible(true);
        window.toFront();
    }
    public static void hide() {
        if (window != null) {
            window.dispose();
            window = null;
        }
    }
}