/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gre.comp1549.dashboard.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Elwyne
 */
public class CircleDisplay extends JPanel implements Indicator {

    private JLabel lblName;
    private JPanel panel;
    private int value;
    private int threshold;
    private String name;
    private Color colour;
    private boolean warning;

    public CircleDisplay() {

        warning = false;
        
        lblName = new JLabel("");
        value = 1;
        threshold = 5;

        this.setLayout(new BorderLayout());
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
//        
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(lblName);
        this.add(panel, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(75, 105));

        colour = Color.WHITE;
    }

    //
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // get a Graphics2D object to draw with
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (value >= threshold) {
            colour = Color.RED;
        }
        
        //  get URL references
        g.setColor(colour);

        g.drawOval(10, 10, 50, 50);

        if (value >= threshold) {
            g.fillOval(10, 10, 50, 50);
        }
    }

    public void setLabel(String name) {
        this.name = name;
        lblName.setText(name);
        repaint();
    }

    public void setValue(int value) {
        this.value = value;

        if (value >= threshold) {
            setState(true);
        } else if (value < threshold && warning) {
            setState(false);
        }
        
        doAction();

        repaint();
    }


    public void setState(boolean state) {
        warning = state;
    }


    public void doAction() {
        if(warning) {
            lblName.setText("<html><center>Turbulence <br />Warning!</center></html>");
        } else if (!warning) {
            setLabel(name + ": " + value);
        }
    }

    public boolean getState() {
        return warning;
    }

    @Override
    public int getValue() {
         return value;
    } 

    @Override
    public int getThreshold() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
