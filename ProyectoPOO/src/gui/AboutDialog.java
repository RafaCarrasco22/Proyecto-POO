/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Rafael
 */
public class AboutDialog extends JDialog {

    private BufferedImage image;

    public AboutDialog(JFrame parent) {
        
        super(parent, true);
        setTitle("About");
        setSize(505, 530);
        setLayout(new BorderLayout());
        setResizable(false);        
        add(createImagePanel(), BorderLayout.CENTER);
        setLocationRelativeTo(parent);
    }

    public JPanel createImagePanel() {
        try {
            image = ImageIO.read(new File("src/img/RCV.png"));
        } catch (IOException ex) {
        }         
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(image, 0, 0, null);
            }
        };
        panel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        return panel;
    }

}
