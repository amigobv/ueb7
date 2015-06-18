package ueb7.caas.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Skin;
import javafx.util.StringConverter;

public class DateTimePicker extends DatePicker {

    private ObjectProperty<LocalTime> timeValue = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> dateTimeValue;

    public DateTimePicker() {
        super();
        setValue(LocalDate.now());
        setTimeValue(LocalTime.now());
        setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss");

            @Override
            public String toString(LocalDate object) {
                if (dateTimeValue == null) {
                    dateTimeValue = new SimpleObjectProperty<>(LocalDateTime.of(getValue(), timeValue.get()));
                    return dateTimeValue.get().format(formatter);
                } 
                   
                return dateTimeValue.get().format(formatter);
            }

            @Override
            public LocalDate fromString(String string) {
                return LocalDate.parse(string, formatter);
            }
        });
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new DateTimePickerSkin(this);
    }

    public LocalTime getTimeValue() {
        return timeValue.get();
    }

    void setTimeValue(LocalTime timeValue) {
        this.timeValue.set(timeValue);
    }

    public ObjectProperty<LocalTime> timeValueProperty() {
        return timeValue;
    }

    public LocalDateTime getDateTimeValue() {
        return dateTimeValue.get();
    }

    public void setDateTimeValue(LocalDateTime dateTimeValue) {
        this.dateTimeValue.set(dateTimeValue);
    }

    public ObjectProperty<LocalDateTime> dateTimeValueProperty() {
        if (dateTimeValue == null) {
            dateTimeValue = new SimpleObjectProperty<>(LocalDateTime.of(this.getValue(), timeValue.get()));
            timeValue.addListener(t -> {
                dateTimeValue.set(LocalDateTime.of(this.getValue(), timeValue.get()));
            });

            valueProperty().addListener(
                    t -> { 
                        dateTimeValue.set(LocalDateTime.of(this.getValue(), timeValue.get()));
                    });
        }
        
        return dateTimeValue;
    }

}
