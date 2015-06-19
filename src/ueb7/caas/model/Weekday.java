package ueb7.caas.model;

import java.io.Serializable;

/**
 * Weekday Enumeration
 * 
 * @author s1310307036
 *
 */
public enum Weekday implements Serializable {
    MONDAY("Montag"), TUESDAY("Dienstag"), WEDNESDAY("Mitwoch"), 
    THURSDAY("Donnerstag"), FRIDAY("Freitag"), SATURDAY("Samstag"), 
    SUNDAY("Sonntag");

    private String weekday;

    private Weekday(String day) {
        weekday = day;
    }

    @Override
    public String toString() {
        return weekday;
    }
}
