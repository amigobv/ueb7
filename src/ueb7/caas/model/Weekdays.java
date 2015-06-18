package ueb7.caas.model;

/**
 * Weekday Enumeration
 * 
 * @author s1310307036
 *
 */
public enum Weekdays {
    MONDAY("Montag"), TUESDAY("Dienstag"), WEDNESDAY("Mitwoch"), 
    THURSDAY("Donnerstag"), FRIDAY("Freitag"), SATURDAY("Samstag"), 
    SUNDAY("Sonntag");

    private String weekday;

    private Weekdays(String day) {
        weekday = day;
    }

    public String toString() {
        return weekday;
    }
}
