package uk.ac.gre.comp1549.dashboard.controls;

public interface StateDesignPattern {
// http://www.javacodegeeks.com/2013/08/state-design-pattern-in-java-example-tutorial.html
    public void setState(boolean state);
    public void doAction();
    public boolean getState();
}
