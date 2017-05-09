package uk.ac.gre.comp1549.dashboard.threads;

public abstract class AbstrackMultiThread extends Thread {

    private SharedDashboard dashboard;

    
    @Override
    public abstract void run(); 
        //parse xml file


    public AbstrackMultiThread(SharedDashboard s) {
        dashboard = s;
    }
    
    public SharedDashboard getDashboard() {
        return dashboard;
    }
    
    public abstract void setValue(int value);
    
    public abstract int getValue();
}
