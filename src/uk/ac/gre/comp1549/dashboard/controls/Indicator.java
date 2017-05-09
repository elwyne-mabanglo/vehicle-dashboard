package uk.ac.gre.comp1549.dashboard.controls;


public interface Indicator {
 
    public int getValue();

    public void setValue(int value);
    
    public int getThreshold();
    
}
