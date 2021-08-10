package util;

import javax.servlet.http.Part;
import java.util.Collection;

public class FileInfoContainer {
    private final Collection<Part> parts;
    private final String fileName;
    private String realPath;

    public FileInfoContainer(String fileName, String realPath, Collection<Part> parts) {
        if (!realPath.isBlank()) {
            this.realPath = realPath.substring(0, realPath.indexOf("\\target")).concat("\\");
        }
        this.parts = parts;
        this.fileName = fileName;
    }

    public String getRealPath() {
        return realPath;
    }

    public Collection<Part> getParts() {
        return parts;
    }

    public String getFileName() {
        return fileName;
    }
}
