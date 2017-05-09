package uk.ac.gre.comp1549.dashboard.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.border.BevelBorder;

/**
 * A JavaBean for displaying dates. At the moment it is not very useful. See the
 * lectures exercises for instructions about what you are to do with it.
 *
 * Give particular thought to how to store the property values. Is it most
 * helpful to make them ints or Strings or something elses?
 */
public class PressureDisplay extends JPanel implements Indicator{

    private JLabel lblValue,
            lblUnits,
            lblName;

    private int value;
    private JPanel panel;

    /**
     * Default constructor (which all beans must have) which sets up the default
     * appearance of the bean.
     */
    public PressureDisplay() {
        panel = new JPanel();

        lblName = new JLabel("");
        lblValue = new JLabel("");
        lblUnits = new JLabel("psi");

        //this.setBackground(Color.GREY);
        this.setLayout(new BorderLayout());
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));

        panel.add(lblValue);
        panel.add(lblUnits);
        
        this.add(lblName, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

//        lblName.setForeground(Color.WHITE);
//        lblValue.setForeground(Color.WHITE);
//        lblUnits.setForeground(Color.WHITE);
//        panel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        Font f = new Font("SansSerif", Font.PLAIN, 12);

//        lblName.setFont(f);
//        lblValue.setFont(f);
//        lblUnits.setFont(f);
        value = 0;
    }

    /**
     * @param pressure the pressure to set
     */
    @Override
    public void setValue(int pressure) {
        this.value = pressure;
        lblValue.setText(pressure + "");
    }

    public void setLabel(String name) {
        lblName.setText(name);
    }

    public void setUnits(String unit) {
        lblUnits.setText(unit);
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
