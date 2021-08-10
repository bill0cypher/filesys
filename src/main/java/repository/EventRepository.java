package repository;

import model.Event;
import repository.common.GenericRepository;

import java.util.List;

public interface EventRepository extends GenericRepository<Event, Integer> {
    List<Event> findEventsByUserId(Integer id);
}
