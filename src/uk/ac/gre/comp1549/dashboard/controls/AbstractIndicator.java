package uk.ac.gre.comp1549.dashboard.controls;

import javax.swing.JPanel;

public class AbstractIndicator extends JPanel  {
    
    private int value;
    private int threshold;
    
    public AbstractIndicator() {
        value = 0;
        threshold = 100;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return the threshold
     */
    public int getThreshold() {
        return threshold;
    }

    /**
     * @param threshold the threshold to set
     */
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
    
    
}
