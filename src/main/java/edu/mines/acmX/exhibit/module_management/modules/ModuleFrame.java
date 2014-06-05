package edu.mines.acmX.exhibit.module_management.modules;

import processing.core.PApplet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class ModuleFrame extends JFrame {
    private volatile PApplet resident;

    public ModuleFrame(PApplet resident){
        super();
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        setUndecorated(true);

        //resident.init();
        resident.frame = this;
        this.resident = resident;
        setVisible(true);

        setLayout(new BorderLayout());
        add(resident, BorderLayout.CENTER);

        Insets insets = getInsets();
        //setSize(env.getMaximumWindowBounds().getSize());
        setExtendedState(Frame.MAXIMIZED_BOTH);
        resident.setBounds(insets.left, insets.top, getWidth(), getHeight());
    }
}
