/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.gre.comp1549.dashboard.threads;

import uk.ac.gre.comp1549.dashboard.controls.BarPanel;
import uk.ac.gre.comp1549.dashboard.controls.CircleDisplay;
import uk.ac.gre.comp1549.dashboard.controls.DialPanel;
import uk.ac.gre.comp1549.dashboard.controls.PressureDisplay;

/**
 *
 * @author Elwyne
 */
public class SharedDashboard {

    private DialPanel speed;
    private DialPanel altitude;
    private BarPanel fuel;
    private PressureDisplay pressure;
    private CircleDisplay warning;

    private static SharedDashboard instance = null;

    public static synchronized SharedDashboard getInstance() {
        if (instance == null) {
            instance = new SharedDashboard();
        }
        return instance;
    }

    public DialPanel getSpeed() {
        return speed;
    }

    public void setSpeed(DialPanel speed) {
        this.speed = speed;
    }

    public DialPanel getAltitude() {
        return altitude;
    }

    public void setAltitude(DialPanel altitude) {
        this.altitude = altitude;
    }

    public BarPanel getFuel() {
        return fuel;
    }

    public void setFuel(BarPanel fuel) {
        this.fuel = fuel;
//        this.fuel.setValue(fuel);

        if (this.fuel.getValue() == this.fuel.getThreshold()) {
//            while (speed != 0 && altitude != 0 && pressure != 0) {
//                speed -= 10;
//                altitude -= 10;
//                pressure -= 10;
//            }

        }
    }

    public PressureDisplay getPressure() {
        return pressure;
    }

    public void setPressure(PressureDisplay pressure) {
        this.pressure = pressure;
    }

    public CircleDisplay getWarning() {
        return warning;
    }

    public void setWarning(CircleDisplay warning) {
        this.warning = warning;
    }
}
