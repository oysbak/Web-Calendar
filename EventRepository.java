package webCalendarSpring;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface EventRepository extends CrudRepository<Event, Long> {
    public Iterable<Event> getByDateBetween(LocalDate fromDate, LocalDate endDate);
}
