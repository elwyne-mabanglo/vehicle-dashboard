package uk.ac.gre.comp1549.dashboard.controls;

import javax.swing.JPanel;

public class IndicatorFactory {
// https://stackoverflow.com/questions/4709175/what-are-enums-and-why-are-they-useful

    public static Indicator createIndicator(INDICATOR_TYPE type) {

        switch (type) {
            case FUEL:
                return new BarPanel();
            case WARNING:
                return new CircleDisplay();
            case SPEED:
            case ALTITUDE:
                return new DialPanel();
            case PRESSURE:
                return new PressureDisplay();
            default:
                return null;
        }
    }

    public enum INDICATOR_TYPE {

        FUEL, SPEED, PRESSURE, ALTITUDE, WARNING;
    }
}
