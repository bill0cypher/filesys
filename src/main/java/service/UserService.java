package service;

import exceptions.EmptyBodyException;
import exceptions.EmptyListException;
import exceptions.NoSuchEntryException;
import model.User;
import repository.UserRepository;
import repository.common.FormCompleted;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserService implements FormCompleted<User> {

    private final UserRepository userRepository;

    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    public User save(User user) throws EmptyBodyException {
        if (user == null) throw new EmptyBodyException(EmptyBodyException.DEFAULT_MESSAGE_TEXT);
        return userRepository.save(user);
    }

    public User update(User user) throws EmptyBodyException, NoSuchEntryException {
        if (user == null) throw new EmptyBodyException(EmptyBodyException.DEFAULT_MESSAGE_TEXT);
        else if (user.getId() == null) throw new NoSuchEntryException(NoSuchEntryException.DEFAULT_MESSAGE_TEXT);
        return Optional.ofNullable(userRepository.update(user)).orElseThrow(() -> new NoSuchEntryException(NoSuchEntryException.DEFAULT_MESSAGE_TEXT));
    }

    public boolean delete(Integer id) throws NoSuchEntryException {
        boolean res = userRepository.delete(id);
        if (!res) throw new NoSuchEntryException(NoSuchEntryException.DEFAULT_MESSAGE_TEXT);
        return true;
    }

    public User findById(Integer id) throws NoSuchEntryException {
        return Optional.ofNullable(userRepository.findById(id)).orElseThrow(() -> new NoSuchEntryException(NoSuchEntryException.DEFAULT_MESSAGE_TEXT));
    }

    public List<User> getAll() throws EmptyListException {
        return Optional.ofNullable(userRepository.findAll()).orElseThrow(() -> new EmptyListException(String.format(EmptyListException.DEFAULT_MESSAGE_TEXT, User.class.getName())));
    }

    @Override
    public User fillFormCompleted(User user, Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey().equals("full_name") && (entry.getValue() != null && !entry.getValue().isBlank())) {
                user.setFullName(entry.getValue());
            } else if (entry.getKey().equals("email") && (entry.getValue() != null && !entry.getValue().isBlank())) {
                user.setEmail(entry.getValue());
            } else if (entry.getKey().equals("username") && (entry.getValue() != null && !entry.getValue().isBlank())) {
                user.setUsername(entry.getValue());
            } else if (entry.getKey().equals("upload_limit") && (entry.getValue() != null && !entry.getValue().isBlank())) {
                user.setFilesUploadLimit(Integer.valueOf(entry.getValue()));
            }
        }
        return user;
    }
}
