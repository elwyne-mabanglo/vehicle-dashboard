package uk.ac.gre.comp1549.dashboard.threads;

import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.gre.comp1549.dashboard.DashboardDemoMain;
import static uk.ac.gre.comp1549.dashboard.DashboardDemoMain.XML_SCRIPT;
import uk.ac.gre.comp1549.dashboard.events.DashBoardEvent;
import uk.ac.gre.comp1549.dashboard.events.DashBoardEventListener;
import uk.ac.gre.comp1549.dashboard.scriptreader.DashboardEventGeneratorFromXML;

public class SpeedThread extends AbstrackMultiThread {

    private SharedDashboard dashboard;
    
    public SpeedThread(SharedDashboard s) {
        super(s);
        
        dashboard = super.getDashboard();
    }

    @Override
    public void run() {
        // parse xml
        
        try {
            DashboardEventGeneratorFromXML dbegXML = new DashboardEventGeneratorFromXML();

            // Register for petrol events from the XML script file
            DashBoardEventListener dbelSpeed = new DashBoardEventListener() {
                @Override
                public void processDashBoardEvent(Object originator, DashBoardEvent dbe) {
                    dashboard.getSpeed().setValue(Integer.parseInt(dbe.getValue()));
                }
            };
            dbegXML.registerDashBoardEventListener("speed", dbelSpeed);

            // Process the script file - it willgenerate events as it runs
            dbegXML.processScriptFile(XML_SCRIPT);

        } catch (Exception ex) {
            Logger.getLogger(DashboardDemoMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setValue(int value) {
        dashboard.getSpeed().setValue(value);
    }

    @Override
    public int getValue() {
        return dashboard.getSpeed().getValue();
    }
    
}
