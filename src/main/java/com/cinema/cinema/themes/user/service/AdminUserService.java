package com.cinema.cinema.themes.user.service;

import com.cinema.cinema.themes.user.UserException;
import com.cinema.cinema.themes.user.model.AdminUser;
import com.cinema.cinema.themes.user.repository.AdminUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AdminUserService extends UserService<AdminUser> {

    private AdminUserRepository userRepository;

    public AdminUser getAdminUser(long id) {
        return getUser(id);
    }

    @Override
    protected AdminUser getUser(long id) {
        Optional<AdminUser> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserException("User with given ID not found");
        }
        return user.get();
    }

    public void editAdminUser(long id, String firstName, String lastName, String password, String email, int phone) {
        AdminUser user = editUser(id, firstName, lastName, password, email, phone);
        userRepository.save(user);
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
