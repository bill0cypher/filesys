package model;

import javax.persistence.*;

@Entity
@Table(name = "file")
public class File {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "file_id_seq", sequenceName = "FILE_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "file_id_seq", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "file_path")
    private String path;

    @OneToOne(mappedBy = "file", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public File() {
    }

    public File(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
