package com.cinema.cinema.themes.user.validator;

import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.themes.user.model.AdminUser;
import com.cinema.cinema.themes.user.repository.AdminUserRepository;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminUserValidator extends ValidatorService<AdminUser> {

    private final AdminUserRepository adminUserRepository;

    public AdminUserValidator(Validator validator, AdminUserRepository adminUserRepository) {
        super(validator);
        this.adminUserRepository = adminUserRepository;
    }

    @Override
    public AdminUser validateExists(long adminId) {
        Optional<AdminUser> admin = adminUserRepository.findById(adminId);
        if (admin.isEmpty()) {
            throw new ElementNotFoundException("Admin user with ID " + adminId + " not found");
        }
        return admin.get();
    }

}
