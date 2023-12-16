package GUI;

import Physic.Canvas.SpacePanel;
import Physic.GameEngine;
import Physic.Scenes;

import javax.swing.*;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.BiConsumer;

/**
 * The main frame of the application containing buttons to run different scenes.
 */
public class MainFrame extends JFrame implements ActionListener {

    private JPanel mainBox = new JPanel();
    private GridBagConstraints mainBoxGB = new GridBagConstraints();
    private int width = 400;
    private int height = 400;
    private BiConsumer<SpacePanel, GameEngine> scenes[] = Scenes.getScenes();
    private JButton scenesButt[] = new JButton[scenes.length];

    /**
     * Constructs the main frame and sets up the UI components.
     */
    public MainFrame() {
        this.setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        mainBox.setBackground(new Color(0, 0, 0));

        // Create buttons for each scene
        for (int i = 0; i < scenes.length; i++) {
            scenesButt[i] = new JButton();
            mainBox.add(scenesButt[i]);

            scenesButt[i].setText(String.format("Run scene %d", i + 1));
            setUpButton(scenesButt[i]);
        }

        this.add(mainBox);
    }

    /**
     * Sets up the appearance and behavior of a button.
     *
     * @param jb The JButton to set up.
     */
    private void setUpButton(JButton jb) {
        jb.setPreferredSize(new Dimension(300, 50));
        jb.setHorizontalAlignment(SwingConstants.CENTER);
        jb.setVerticalAlignment(SwingConstants.TOP);
        jb.setFocusPainted(false);
        jb.setFont(new Font("Serif", 1, 30));
        jb.setBackground(new Color(145, 145, 145));
        jb.setForeground(new Color(255, 255, 255));
        jb.addActionListener(this);
    }

    /**
     * Handles button click events and opens a new scene frame when a scene button is clicked.
     *
     * @param e The ActionEvent representing the button click event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < scenes.length; i++) {
            if (e.getSource().equals(scenesButt[i])) {
                JFrame nf = new SceneFrame(scenes[i]);
                nf.setVisible(true);
            }
        }
    }
}
