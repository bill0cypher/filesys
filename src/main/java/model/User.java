package model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "USER_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "upload_limit", columnDefinition = "integer default 100")
    private Integer filesUploadLimit;
    @Column(name = "full_name")
    private String fullName;

    @OneToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE,
            CascadeType.REMOVE,
            CascadeType.REFRESH,
            CascadeType.DETACH}, orphanRemoval = true, mappedBy = "user")
    @BatchSize(size = 10)
    @Fetch(FetchMode.SUBSELECT)
    private List<File> files;

    @OneToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE,
            CascadeType.REMOVE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    }, orphanRemoval = true, mappedBy = "user")
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 10)
    private List<Event> events;

    public User() {

    }

    public User(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getFilesUploadLimit() {
        return filesUploadLimit;
    }

    public void setFilesUploadLimit(Integer filesUploadLimit) {
        this.filesUploadLimit = filesUploadLimit;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
