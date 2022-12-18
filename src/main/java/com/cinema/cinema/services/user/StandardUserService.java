package com.cinema.cinema.services.user;

import com.cinema.cinema.exceptions.UserException;
import com.cinema.cinema.models.user.StandardUser;
import com.cinema.cinema.repositories.user.StandardUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StandardUserService extends UserService<StandardUser> {

    private StandardUserRepository userRepository;

    public StandardUser getStandardUser(long id) {
        return getUser(id);
    }

    @Override
    protected StandardUser getUser(long id) {
        Optional<StandardUser> user = userRepository.findStandardUserById(id);
        if (user.isEmpty()) {
            throw new UserException("User with given ID not found");
        }
        return user.get();
    }

    public List<StandardUser> getAllUsers() {
        return userRepository.findAllStandardUsers();
    }

    public void editStandardUser(long id, String firstName, String lastName, String password, String email, int phone) {
        StandardUser user = editUser(id, firstName, lastName, password, email, phone);
        userRepository.update(id, user);
    }

    @Override
    protected StandardUser editUser(long id, String firstName, String lastName, String password, String email, int phone) {
        StandardUser user = getStandardUser(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        return user;
    }

    public void editIsActive(long id, boolean isActive) {
        StandardUser user = getStandardUser(id);
        user.setIsActive(isActive);
        userRepository.update(id, user);
    }

    public void editPassword(long id, String password) {
        StandardUser user = getStandardUser(id);
        user.setPassword(password);
        userRepository.update(id, user);
    }

}
