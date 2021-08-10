package model;

import enums.EventType;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "event_id_seq", sequenceName = "EVENT_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "event_id_seq", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(name = "uploaded")
    private Date uploadedTime;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Event() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EventType getName() {
        return eventType;
    }

    public void setName(EventType eventType) {
        this.eventType = eventType;
    }

    public Date getUploadedTime() {
        return uploadedTime;
    }

    public void setUploadedTime(Date uploadedTime) {
        this.uploadedTime = uploadedTime;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
