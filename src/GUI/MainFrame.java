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

public class MainFrame extends JFrame implements ActionListener {

    private JPanel mainBox = new JPanel();
    private GridBagConstraints mainBoxGB = new GridBagConstraints();
    private int width = 400;
    private int height = 400;
    private BiConsumer<SpacePanel, GameEngine> scenes[] = Scenes.getScenes();
    private JButton scenesButt[] = new JButton[scenes.length];

    public MainFrame(){
        this.setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        mainBox.setBackground(new Color(0,0,0));

        for(int i = 0; i < scenes.length; i++){
            scenesButt[i] = new JButton();
            mainBox.add(scenesButt[i]);

            scenesButt[i].setText(String.format("Run scene %d",i+1));
            setUpButton(scenesButt[i]);
        }


        this.add(mainBox);
    }

    private void setUpButton(JButton jb){
        jb.setPreferredSize(new Dimension(300,50));
        jb.setHorizontalAlignment(SwingConstants.CENTER);
        jb.setVerticalAlignment(SwingConstants.TOP);
        jb.setFocusPainted(false);
        jb.setFont(new Font("Serif", 1, 30));
        jb.setBackground(new Color(145, 145, 145));
        jb.setForeground(new Color(255, 255, 255));
        jb.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < scenes.length; i++){
            if(e.getSource().equals(scenesButt[i])){
                JFrame nf = new SceneFrame(scenes[i]);
                nf.setVisible(true);
            }
        }
    }
}
