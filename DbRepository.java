package webCalendarSpring;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DbRepository {

    private EventRepository repository;

    public DbRepository(EventRepository repository) {
        this.repository = repository;
    }
}