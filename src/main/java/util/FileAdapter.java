package util;

import enums.EventType;
import model.Event;
import model.File;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

public class FileAdapter {

    private final FileInfoContainer fileInfoContainer;

    public FileAdapter(FileInfoContainer fileInfoContainer) {
        this.fileInfoContainer = fileInfoContainer;
    }

    private final Logger log = Logger.getLogger(FileAdapter.class.getName());
    private static final String filePath = "uploads" + java.io.File.separator + "{{username}}";

    private File uploadAndConvertToFile(String username) throws IOException {
        String uploadFilePath = fileInfoContainer.getRealPath() + java.io.File.separator + filePath.replace("{{username}}", username);
        File file = new File();
        java.io.File fileSaveDir = new java.io.File(uploadFilePath);
        if (!fileSaveDir.exists())
            fileSaveDir.mkdir();
        log.info("Upload file directory: " + fileSaveDir.getAbsolutePath());
        file.setPath(fileSaveDir.getAbsolutePath());
        String fileName = fileInfoContainer.getFileName();
        file.setFileName(fileName);
        for (Part part : fileInfoContainer.getParts()) {
            part.write(uploadFilePath + java.io.File.separator + fileName);
        }
        return file;
    }

    public File updateUserEvents(User user) throws IOException {
        File file = uploadAndConvertToFile(user.getUsername());
        Event event = new Event();
        event.setUser(user);
        event.setFile(file);
        event.setName(EventType.UPLOAD);
        event.setUploadedTime(new Date(new java.util.Date().getTime()));
        Optional.ofNullable(user.getEvents()).ifPresentOrElse(events -> events.add(event), () -> {
            user.setEvents(new ArrayList<>());
            user.getEvents().add(event);
        });
        return file;
    }
}
