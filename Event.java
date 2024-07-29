package webCalendarSpring;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String event;
    @NotNull
    private LocalDate date;

    public Event() {
    }

    public Event(final String event, final LocalDate date, final String message) {
        this.event = event;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getEvent() {
        return this.event;
    }

    public void setEvent(final String event) {
        this.event = event;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

//    public boolean isBetween(LocalDate fromDate, LocalDate toDate) {
//        return date.isAfter(fromDate) && (date.isBefore(toDate) || date.isEqual(toDate));
//    }
}