package uk.ac.gre.comp1549.dashboard;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import uk.ac.gre.comp1549.dashboard.threads.AbstrackMultiThread;
import uk.ac.gre.comp1549.dashboard.controls.BarPanel;
import uk.ac.gre.comp1549.dashboard.controls.CircleDisplay;
import uk.ac.gre.comp1549.dashboard.controls.DialPanel;
import uk.ac.gre.comp1549.dashboard.controls.PressureDisplay;
import uk.ac.gre.comp1549.dashboard.events.*;
import uk.ac.gre.comp1549.dashboard.scriptreader.DashboardEventGeneratorFromXML;

import uk.ac.gre.comp1549.dashboard.controls.IndicatorFactory.INDICATOR_TYPE;
import static uk.ac.gre.comp1549.dashboard.controls.IndicatorFactory.createIndicator;
import uk.ac.gre.comp1549.dashboard.threads.AltitudeThread;
import uk.ac.gre.comp1549.dashboard.threads.FuelThread;
import uk.ac.gre.comp1549.dashboard.threads.PressureThread;
import uk.ac.gre.comp1549.dashboard.threads.SharedDashboard;
import uk.ac.gre.comp1549.dashboard.threads.SpeedThread;

/**
 * DashboardDemoMain.java Contains the main method for the Dashboard demo
 * application. It: a) provides the controller screen which allows user input
 * which is passed to the display indicators, b) allows the user to run the XML
 * script which changes indicator values, c) creates the dashboard JFrame and
 * adds display indicators to it.
 *
 * @author Gill Windall
 * @version 2.0
 */
public class DashboardDemoMain extends JFrame {

    /**
     * Name of the XML script file - change here if you want to use a different
     * filename
     */
//    public static final String XML_SCRIPT = "dashboard_script.xml";
    public static final String XML_SCRIPT = "dashboard_script2.xml";

    // fields that appear on the control panel
    private JTextField txtSpeedValueInput;
    private JTextField txtPetrolValueInput;
    private JTextField txtAltitudeInput;
    private JTextField txtAirPressureInput;

    private JButton btnScript;

    // fields that appear on the dashboard itself
    private DialPanel speedDial;
    private DialPanel altimeterDial;
    private BarPanel fuelBar;
    private PressureDisplay pressureDisplay;
    private CircleDisplay harzardDisplay;

    private static SharedDashboard dbInstance = null;

    /**
     * Constructor. Does maybe more work than is good for a constructor.
     */
    public DashboardDemoMain() {
        // Set up the frame for the controller
        setTitle("Dashboard demonstration controller");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        /*
         ***************************************************************
         */
        panel.add(new JLabel("Speed Value:"));
        txtSpeedValueInput = new JTextField("0", 3);
        panel.add(txtSpeedValueInput);
        DocumentListener speedListener = new SpeedValueListener();
        txtSpeedValueInput.getDocument().addDocumentListener(speedListener);

        /*
         ***************************************************************
         */
        panel.add(new JLabel("Altitude Value:"));
        txtAltitudeInput = new JTextField("0", 3);
        panel.add(txtAltitudeInput);
        DocumentListener altitudeListener = new AltitudeValueListener();
        txtAltitudeInput.getDocument().addDocumentListener(altitudeListener);

        /*
         ***************************************************************
         */
        panel.add(new JLabel("Petrol Value:"));
        txtPetrolValueInput = new JTextField("0", 3);
        panel.add(txtPetrolValueInput);
        DocumentListener petrolListener = new PetrolValueListener();
        txtPetrolValueInput.getDocument().addDocumentListener(petrolListener);

        /*
         ***************************************************************
         */
        panel.add(new JLabel("Pressure Value:"));
        txtAirPressureInput = new JTextField("0", 3);
        panel.add(txtAirPressureInput);
        DocumentListener pressureListener = new PressureValueListener();
        txtAirPressureInput.getDocument().addDocumentListener(pressureListener);

        /*
         ***************************************************************
         */
        btnScript = new JButton("Run XML Script");
        // When the button is read the XML script will be run
        btnScript.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                new Thread() {
//                    public void run() {
//                        runXMLScript();
//                    }
//                }.start();

                FuelThread fuelThread = new FuelThread(SharedDashboard.getInstance());
                fuelThread.start();

                SpeedThread speedThread = new SpeedThread(SharedDashboard.getInstance());
                speedThread.start();

                AltitudeThread altitudeThread = new AltitudeThread(SharedDashboard.getInstance());
                altitudeThread.start();

                PressureThread pressureThread = new PressureThread(SharedDashboard.getInstance());
                pressureThread.start();
            }
        });

        /*
         ****************************************************************
         */
        //Thread t1 = new AbstrackMultiThread();
        panel.add(btnScript);
        add(panel);
        pack();

        setLocationRelativeTo(null); // display in centre of screen
        this.setVisible(true);

        // Set up the dashboard screen        
        JFrame dashboard = new JFrame("Demo dashboard");
        dashboard.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        dashboard.setLayout(new FlowLayout());

        // add the speed Dial
//        speedDial = new DialPanel();
        speedDial = (DialPanel) createIndicator(INDICATOR_TYPE.SPEED);
        speedDial.setLabel("Air Speed");
        speedDial.setValue(0);
        dashboard.add(speedDial);

        // add the altitude Dial
        altimeterDial = (DialPanel) createIndicator(INDICATOR_TYPE.ALTITUDE);
        //altimeterDial = new DialPanel();
        altimeterDial.setLabel("Altimeter");
        altimeterDial.setValue(0);
        dashboard.add(altimeterDial);

        // add the fuel bar
        fuelBar = (BarPanel) createIndicator(INDICATOR_TYPE.FUEL);
        //fuelBar = new BarPanel();
        fuelBar.setLabel("Fuel");
        fuelBar.setValue(0);
        dashboard.add(fuelBar);

        // add the pressure digital display
        pressureDisplay = (PressureDisplay) createIndicator(INDICATOR_TYPE.PRESSURE);
        //pressureDisplay = new PressureDisplay();
        pressureDisplay.setLabel("Air Pressure");
        pressureDisplay.setValue(0);
        dashboard.add(pressureDisplay);

        // add the hazard display
        harzardDisplay = (CircleDisplay) createIndicator(INDICATOR_TYPE.WARNING);
        //harzardDisplay = new CircleDisplay();
        harzardDisplay.setLabel("<html><center>No <br />Warning</center></html>");
        harzardDisplay.setValue(5);
        dashboard.add(harzardDisplay);

        dashboard.pack();

        // centre the dashboard frame above the control frame
        Point topLeft = this.getLocationOnScreen(); // top left of control frame (this)
        int hControl = this.getHeight(); // height of control frame (this)
        int wControl = this.getWidth(); // width of control frame (this)
        int hDash = dashboard.getHeight(); // height of dashboard frame 
        int wDash = dashboard.getWidth(); // width of dashboard frame 
        // calculate where top left of the dashboard goes to centre it over the control frame
        Point p2 = new Point((int) topLeft.getX() - (wDash - wControl) / 2, (int) topLeft.getY() - (hDash + hControl));
        dashboard.setLocation(p2);
        dashboard.setVisible(true);

        dbInstance = SharedDashboard.getInstance();
        dbInstance.setFuel(fuelBar);
        dbInstance.setSpeed(speedDial);
        dbInstance.setAltitude(altimeterDial);
        dbInstance.setPressure(pressureDisplay);
    }

    /**
     * Run the XML script file which generates events for the dashboard
     * indicators
     */
    private void runXMLScript() {
        try {
            DashboardEventGeneratorFromXML dbegXML = new DashboardEventGeneratorFromXML();

            // Register for speed events from the XML script file
//            DashBoardEventListener dbelSpeed = new DashBoardEventListener() {
//                @Override
//                public void processDashBoardEvent(Object originator, DashBoardEvent dbe) {
//                    speedDial.setValue(Integer.parseInt(dbe.getValue()));
//                }
//            };
//            dbegXML.registerDashBoardEventListener("speed", dbelSpeed);
            // Register for processScriptFile events from the XML script file
            DashBoardEventListener dbelPetril = new DashBoardEventListener() {
                @Override
                public void processDashBoardEvent(Object originator, DashBoardEvent dbe) {
//                    altimeterDial.setValue(Integer.parseInt(dbe.getValue()));
                    getFuelBar().setValue(Integer.parseInt(dbe.getValue()));
                }
            };
            dbegXML.registerDashBoardEventListener("petrol", dbelPetril);

            // Process the script file - it willgenerate events as it runs
            dbegXML.processScriptFile(XML_SCRIPT);

        } catch (Exception ex) {
            Logger.getLogger(DashboardDemoMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void runXMLScript2() {
        try {
            DashboardEventGeneratorFromXML dbegXML = new DashboardEventGeneratorFromXML();

            // Register for speed events from the XML script file
            DashBoardEventListener dbelSpeed = new DashBoardEventListener() {
                @Override
                public void processDashBoardEvent(Object originator, DashBoardEvent dbe) {
//                    getSpeedDial().setValue(Integer.parseInt(dbe.getValue()));
                    dbInstance.getSpeed().setValue(Integer.parseInt(dbe.getValue()));
                }
            };
            dbegXML.registerDashBoardEventListener("speed", dbelSpeed);

            // Process the script file - it willgenerate events as it runs
            dbegXML.processScriptFile(XML_SCRIPT);

        } catch (Exception ex) {
            Logger.getLogger(DashboardDemoMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Set the speed value to the value entered in the textfield.
     */
    public void setSpeed() {
        try {
            int value = Integer.parseInt(txtSpeedValueInput.getText().trim());
//            getSpeedDial().setValue(value);
            dbInstance.getSpeed().setValue(value);
        } catch (NumberFormatException e) {
        }
        // don't set the speed if the input can't be parsed
    }

    /**
     * Set the petrol value to the value entered in the textfield.
     */
    public void setPetrol() {
        try {
            int value = Integer.parseInt(txtPetrolValueInput.getText().trim());
//            fuelBar.setValue(value);
//            dbInstance.setFuel(value);
            dbInstance.getFuel().setValue(value);
        } catch (NumberFormatException e) {
        }
        // don't set the speed if the input can't be parsed      
    }

    public void setPressure() {
        try {
            int value = Integer.parseInt(txtAirPressureInput.getText().trim());
//            getPressureDisplay().setValue(value);
            dbInstance.getPressure().setValue(value);
        } catch (NumberFormatException e) {
        }
        // don't set the speed if the input can't be parsed      
    }

    public void setAltitude() {
        try {
            int value = Integer.parseInt(txtAltitudeInput.getText().trim());
//            getAltimeterDial().setValue(value);
            dbInstance.getAltitude().setValue(value);
        } catch (NumberFormatException e) {
        }
        // don't set the speed if the input can't be parsed      
    }

    /*
     ******************************************************************
     WE CAN DELETE BELOW AFTER OUR XML SCRIPT IS MOFIFIED
     ******************************************************************
     */
    public DialPanel getSpeedDial() {
        return speedDial;
    }

    public DialPanel getAltimeterDial() {
        return altimeterDial;
    }

    public BarPanel getFuelBar() {
        return fuelBar;
    }

    public PressureDisplay getPressureDisplay() {
        return pressureDisplay;
    }

    public CircleDisplay getHarzardDisplay() {
        return harzardDisplay;
    }

    /*
     ******************************************************************
     WE CAN DELETE ABOVE AFTER OUR XML SCRIPT IS MOFIFIED
     ******************************************************************
     */
    /**
     * Respond to user input in the Speed textfield
     */
    private class SpeedValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent event) {
            setSpeed();
        }

        @Override
        public void removeUpdate(DocumentEvent event) {
            setSpeed();
        }

        @Override
        public void changedUpdate(DocumentEvent event) {
        }
    }

    /**
     * Respond to user input in the Petrol textfield
     */
    private class PetrolValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent event) {
            setPetrol();
        }

        @Override
        public void removeUpdate(DocumentEvent event) {
            setPetrol();
        }

        @Override
        public void changedUpdate(DocumentEvent event) {
        }
    }

    private class PressureValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent event) {
            setPressure();
        }

        @Override
        public void removeUpdate(DocumentEvent event) {
            setPressure();
        }

        @Override
        public void changedUpdate(DocumentEvent event) {
        }
    }

    private class AltitudeValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent event) {
            setAltitude();
        }

        @Override
        public void removeUpdate(DocumentEvent event) {
            setAltitude();
        }

        @Override
        public void changedUpdate(DocumentEvent event) {
        }
    }

    /**
     *
     * @param args - unused
     */
    public static void main(String[] args) {
        final DashboardDemoMain me = new DashboardDemoMain();
    }
}
