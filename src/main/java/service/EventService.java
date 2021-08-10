package service;

import model.Event;
import repository.EventRepository;

import java.util.List;

public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }
    public Event update(Event event) {
        return eventRepository.update(event);
    }
    public boolean delete(Integer id) {
        return eventRepository.delete(id);
    }
    public Event findById(Integer id) {
        return eventRepository.findById(id);
    }

    public List<Event> findEventsByUserId(Integer id) {
        return eventRepository.findEventsByUserId(id);
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }
}
