package com.cinema.cinema.themes.user.service;

import com.cinema.cinema.themes.user.model.AdminUser;
import com.cinema.cinema.themes.user.repository.AdminUserRepository;
import com.cinema.cinema.themes.user.validator.AdminUserValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class AdminUserService extends UserService<AdminUser> {

    private final AdminUserRepository adminRepository;
    private final AdminUserValidator adminValidator;

    @Override
    @Transactional(readOnly = true)
    public AdminUser getUser(long adminId) {
        return adminValidator.validateExists(adminId);
    }

    @Override
    @Transactional
    public void editUser(long id, AdminUser adminFromDto) {
        AdminUser admin = adminValidator.validateExists(id);
        adminValidator.validateInput(adminFromDto);
        setFields(admin, adminFromDto);
        adminRepository.save(admin);
    }

    private void setFields(AdminUser admin, AdminUser adminFromDto) {
        admin.setFirstName(adminFromDto.getFirstName());
        admin.setLastName(adminFromDto.getLastName());
        admin.setEmail(adminFromDto.getEmail());
        admin.setPassword(adminFromDto.getPassword());
        admin.setPhone(adminFromDto.getPhone());
    }

}
