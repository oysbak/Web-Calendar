package webCalendarSpring;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CalendarTodayController {

    private final EventRepository eventRepository;

    public CalendarTodayController(EventRepository repository) {
        this.eventRepository = repository;
    }

    // Test #1
    @GetMapping("/event/today")
    public ResponseEntity<List<Event>> getEventToday() {
        List<Event> allEvents = (List<Event>) eventRepository.findAll();
        List<Event> todaysEvents = allEvents.stream().filter(event -> LocalDate.now().equals(event.getDate())).toList();
        return ResponseEntity.ok(todaysEvents);
    }

    // Test #2
    @GetMapping("/event")
    public ResponseEntity<Iterable<Event>> getAllEvents() {
        Iterable<Event> events = eventRepository.findAll();
        if (!events.iterator().hasNext()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(events);
        }
    }

    // Test #3
    // http://localhost:28852/event?start_time=2023-10-28&end_time=2024-08-04
    // GET /event?start_time=2023-10-28&end_time=2024-08-04 should respond with status code 204, responded: 404
    @GetMapping(value = "/event", params = {"start_time", "end_time"})
    public ResponseEntity<Iterable<Event>> getEvents(@RequestParam("start_time") LocalDate startDate, @RequestParam("end_time") LocalDate endDate) {
        Iterable<Event> events = eventRepository.getByDateBetween(startDate, endDate);
        return new ResponseEntity(events, HttpStatusCode.valueOf(204));
        //        if (events.isEmpty()) {
//            return new ResponseEntity<>(Map.of("message", "No events found!"), HttpStatus.NOT_FOUND);
//        } else {
//            return ResponseEntity.ok().body(events);
//        }
    }

    // Test #4
    @PostMapping("/event")
    public ResponseEntity<Map<String, String>> postEvent(@RequestBody Event event) {
        if (event.getEvent() == null || event.getEvent().isBlank() || event.getDate() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        eventRepository.save(event);
        Map<String, String> response = new HashMap<>();
        response.put("message", "The event has been added!");
        response.put("event", event.getEvent());
        response.put("date", event.getDate().toString());
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "The event doesn't exist!"), HttpStatus.NOT_FOUND);
        }
        eventRepository.delete(event.get());
        return new ResponseEntity<>(event.get(), HttpStatus.OK);
    }

//    @GetMapping("/eventByNumber")
//    public String getANumber(@RequestParam("number") int num) {
//        return "This is  a text with " + num + " in it!";
//    }

    // Back to the future
    @GetMapping("/event/{id}")
    public ResponseEntity<Map<String, Object>> getEventById(@PathVariable("id") Long id) {
        Map<String, Object> returnMap = new HashMap<>();
        HttpStatus httpStatus;
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            returnMap.put("message", "The event doesn't exist!");
            httpStatus = HttpStatus.NOT_FOUND;
        } else {
            returnMap.put("id", 1);
            returnMap.put("event", event.get().getEvent());
            returnMap.put("date", event.get().getDate().toString());
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity<>(returnMap, httpStatus);
    }
}