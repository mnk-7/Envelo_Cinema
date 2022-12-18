package com.cinema.cinema.services.user;

import com.cinema.cinema.exceptions.UserException;
import com.cinema.cinema.models.user.AdminUser;
import com.cinema.cinema.repositories.user.AdminUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminUserService extends UserService<AdminUser> {

    private AdminUserRepository userRepository;

    public AdminUser getAdminUser(long id) {
        return getUser(id);
    }

    @Override
    protected AdminUser getUser(long id) {
        Optional<AdminUser> user = userRepository.findAdminUserById(id);
        if (user.isEmpty()) {
            throw new UserException("User with given ID not found");
        }
        return user.get();
    }

    public void editAdminUser(long id, String firstName, String lastName, String password, String email, int phone) {
        AdminUser user = editUser(id, firstName, lastName, password, email, phone);
        userRepository.update(id, user);
    }

    @Override
    protected AdminUser editUser(long id, String firstName, String lastName, String password, String email, int phone) {
        AdminUser user = getAdminUser(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        return user;
    }

}
