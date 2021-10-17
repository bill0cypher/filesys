package servlets;

import exceptions.EmptyBodyException;
import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.File;
import model.User;
import org.apache.log4j.Logger;
import service.UserService;
import util.FileAdapter;
import util.FileInfoContainer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@MultipartConfig(
        fileSizeThreshold = 100 * 1024,
        maxFileSize = 100 * 1024,
        maxRequestSize = 1024 * 1024 * 100
)
@WebServlet(name = "UserServlet", urlPatterns = "/user")
public class UserServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(UserServlet.class.getName());
    private User user;
    private List<User> users;
    private UserService userService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init();
        userService = (UserService) servletConfig.getServletContext().getAttribute("userService");
        user = new User();
        user.setEvents(new ArrayList<>());
        user.setFiles(new ArrayList<>());
        this.users = getAll();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action");
        String id = req.getParameter("id");
        req.setAttribute("user", user);
        try {
            if (action == null && id != null && !id.isBlank()) {
                user = userService.findById(Integer.valueOf(id));
                if (user == null) {
                    resp.sendError(404, String.format("User with id: %s not found", id));
                    log.error(String.format("User by id %s not found", id));
                } else {
                    req.setAttribute("user", user);
                    req.getRequestDispatcher("/user/user-overview").forward(req, resp);
                }
            }
            switch (action == null ? "info" : action) {
                case "update" -> {
                    Integer uid = Integer.valueOf(req.getParameter("uid"));
                    log.info("REST request to get user update form" + uid);
                    user = userService.findById(uid);
                    req.setAttribute("user", user);
                    req.getRequestDispatcher("/user/user-update").forward(req, resp);
                }
                case "create" -> {
                    log.info("REST request to get user creation form");
                    user = new User();
                    req.setAttribute("user", user);
                    req.getRequestDispatcher("/user/user-create").forward(req, resp);
                }
                case "delete" -> {
                    log.info(String.format("REST request to delete user by id: %s", id));
                    assert id != null;
                    if (userService.delete(Integer.valueOf(id)))
                        user = new User();
                    users = users.stream().filter(user1 -> !user1.getId().equals(Integer.valueOf(id))).collect(Collectors.toList());
                    req.setAttribute("users", users);
                    req.getRequestDispatcher("/user/users-table").forward(req, resp);
                }
                default -> {
                    log.info("REST request to get user table page");
                    users = userService.getAll();
                    req.setAttribute("users", users);
                    resp.sendRedirect("/users-table");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        req.setAttribute("user", user);
        log.info("REST request to update/create user");
        if ("fill".equals(action)) {
            user = userService.fillFormCompleted(user, Map.of(
                    "full_name", ofNullable((req.getParameter("full_name"))).orElse(""),
                    "email", ofNullable((req.getParameter("email"))).orElse(""),
                    "username", ofNullable((req.getParameter("username"))).orElse(""),
                    "upload_limit", ofNullable((req.getParameter("upload_limit"))).orElse("")));
        } else if ("upload".equals(action)) {
            File file = new FileAdapter(new FileInfoContainer(
                    req.getPart("file").getSubmittedFileName(),
                    req.getServletContext().getRealPath(""),
                    req.getParts())).updateUserEvents(user);
            file.setUser(user);
            ofNullable(user.getFiles()).ifPresentOrElse(files -> files.add(file), () -> {
                user.setFiles(new ArrayList<>());
                user.getFiles().add(file);
            });
        } else if ("register".equals(action)) {
            save(user, resp);
        } else if ("update".equals(action)) {
            update(user, resp);
        } else if ("delete".equals(action)) {
            delete(user, resp);
            user = new User();
            req.getRequestDispatcher("/users-table").forward(req, resp);
        } else {
            req.getRequestDispatcher("/user-overview").forward(req, resp);
        }
    }

    public User save(User user, HttpServletResponse response) throws IOException {
        try {
            return userService.save(user);
        } catch (EmptyBodyException e) {
            e.printStackTrace();
            response.sendError(401, e.getMessage());
        }
        return null;
    }

    public User update(User user, HttpServletResponse response) throws IOException {
        try {
            return userService.update(user);
        } catch (EmptyBodyException e) {
            e.printStackTrace();
            response.sendError(401, e.getMessage());
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
            response.sendError(404, e.getMessage());
        }
        return null;
    }

    public boolean delete(User user, HttpServletResponse response) throws IOException {
        try {
            return userService.delete(user.getId());
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
            response.sendError(404, e.getMessage());
        }
        return false;
    }

    public List<User> getAll() {
        try {
            return userService.getAll();
        } catch (EmptyListException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
