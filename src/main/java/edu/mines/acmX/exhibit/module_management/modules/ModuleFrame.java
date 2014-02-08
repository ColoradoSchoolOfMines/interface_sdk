package edu.mines.acmX.exhibit.module_management.modules;

import processing.core.PApplet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class ModuleFrame extends JFrame {
    PApplet resident;

    public ModuleFrame(PApplet resident){
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();

        resident.init();
        resident.frame = this;
        this.resident = resident;
        setVisible(true);

        setLayout(new BorderLayout());
        add(resident, BorderLayout.CENTER);

        Insets insets = getInsets();
        setSize(env.getMaximumWindowBounds().getSize());
        resident.setBounds(insets.left, insets.top, getWidth(), getHeight());

        setLocation(500, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
