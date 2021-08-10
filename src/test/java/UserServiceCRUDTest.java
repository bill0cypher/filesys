import enums.EventType;
import exceptions.EmptyBodyException;
import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.Event;
import model.File;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import repository.impl.UserRepositoryImpl;
import service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceCRUDTest {

    @Mock
    private UserRepositoryImpl userRepository;
    @InjectMocks
    private UserService userService;
    @Mock
    private User user;

    @Before
    public void init() {
        File file = new File();
        file.setUser(user);
        file.setFileName("test.txt");
        file.setPath("./");
        Event event = new Event();
        event.setFile(file);
        event.setUser(user);
        event.setEventType(EventType.UPLOAD);
        event.setName(EventType.UPLOAD);
        event.setUploadedTime(new java.sql.Date(new Date().getTime()));

        user.setFiles(Collections.singletonList(file));
        user.setEvents(Collections.singletonList(event));
        user.setUsername("tester");
        user.setFullName("Test OO7");
    }

    //Success expected tests
    @Test
    public void shouldSuccessSave() {
        try {
            Mockito.when(userRepository.save(user)).then(new Answer<User>() {
                int sequence = 1;

                @Override
                public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                    User wr = invocationOnMock.getArgument(0);
                    wr.setId(sequence++);
                    return wr;
                }
            });
            userService.save(user);
            Mockito.verify(userRepository).save(user);
            Assertions.assertNotNull(user.getId());
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldSuccessUpdate() {
        try {
            user = new User();
            user.setUsername("mavenbefore");
            user.setFullName("testerbefore");
            user.setFilesUploadLimit(10);
            user.setFiles(Collections.emptyList());
            user.setEvents(Collections.emptyList());
            Mockito.when(userRepository.update(user)).then((Answer<User>) invocationOnMock -> {
                User user = invocationOnMock.getArgument(0, User.class);
                user.setUsername("mavenafter");
                user.setFullName("testerafter");
                user.setEvents(Collections.singletonList(new Event()));
                user.setFiles(Collections.singletonList(new File()));
                return user;
            });
            User user1 = userService.update(user);
            Mockito.verify(userRepository).update(user);
            assertEquals(1, user1.getFiles().size());
            assertEquals(1, user1.getEvents().size());
            assertEquals("mavenafter", user1.getUsername());
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void shouldSuccessFindById() {
        try {
            List<User> users = new ArrayList<>();
            users.add(new User(1));
            Mockito.when(userRepository.findById(1)).then((Answer<User>) invocationOnMock -> {
                int id = invocationOnMock.getArgument(0);
                return users.stream().filter(user1 -> user1.getId() == id).collect(Collectors.toList()).get(0);
            });
            User user = userService.findById(1);
            Mockito.verify(userRepository).findById(1);
            assertNotNull(user);
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldSuccessFindAll() {
        try {
            Mockito.when(userRepository.findAll()).then((Answer<List<User>>) invocationOnMock -> Collections.singletonList(user));
            List<User> userCollection = userService.getAll();
            Mockito.verify(userRepository).findAll();
            Assertions.assertTrue(userCollection.size() > 0);
        } catch (EmptyListException e) {
            e.printStackTrace();
        }
    }

    //Fail expected tests
    @Test
    public void shouldFailSave() {
        try {
            Mockito.when(userRepository.save(user)).then((Answer<User>) invocationOnMock -> null);
            userService.save(null);
            Mockito.verify(userRepository).save(user);
            Assertions.assertThrows(EmptyBodyException.class, () -> new EmptyBodyException(EmptyBodyException.DEFAULT_MESSAGE_TEXT));
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldFailUpdate() {
        try {
            Mockito.when(userRepository.update(user)).then((Answer<User>) invocationOnMock -> null);
            userService.update(null);
            Mockito.verify(userRepository).save(user);
            Assertions.assertThrows(EmptyBodyException.class, () -> new EmptyBodyException(EmptyBodyException.DEFAULT_MESSAGE_TEXT));
        } catch (EmptyBodyException | NoSuchEntryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldFailDelete() {
        try {
            Mockito.when(userRepository.delete(null)).then((Answer<User>) invocationOnMock -> null);
            userService.save(null);
            Mockito.verify(userRepository).delete(null);
            Assertions.assertThrows(NoSuchEntryException.class, () -> new NoSuchEntryException(NoSuchEntryException.DEFAULT_MESSAGE_TEXT));
        } catch (EmptyBodyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldFailFindById() {
        try {
            Mockito.when(userRepository.findById(1)).then((Answer<User>) invocationOnMock -> null);
            User user = userService.findById(1);
            Mockito.verify(userRepository).findById(1);
            Assertions.assertThrows(NoSuchEntryException.class, () -> new NoSuchEntryException(NoSuchEntryException.DEFAULT_MESSAGE_TEXT));
        } catch (NoSuchEntryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldFailFindAll() {
        try {
            Mockito.when(userRepository.findAll()).then((Answer<User>) invocationOnMock -> null);
            List<User> user = userService.getAll();
            Mockito.verify(userRepository).findAll();
            Assertions.assertThrows(EmptyListException.class, () -> new EmptyListException(EmptyListException.DEFAULT_MESSAGE_TEXT));
        } catch (EmptyListException e) {
            e.printStackTrace();
        }
    }

}
