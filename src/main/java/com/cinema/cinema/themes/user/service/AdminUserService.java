package com.cinema.cinema.themes.user.service;

import com.cinema.cinema.themes.user.model.AdminUser;
import com.cinema.cinema.themes.user.model.AdminUserOutputDto;
import com.cinema.cinema.themes.user.model.AdminUserInputDto;
import com.cinema.cinema.themes.user.repository.AdminUserRepository;
import com.cinema.cinema.themes.user.validator.AdminUserValidator;
import com.cinema.cinema.utils.DtoMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class AdminUserService extends UserService<AdminUserOutputDto, AdminUserInputDto> {

    private final AdminUserRepository adminRepository;
    private final AdminUserValidator adminValidator;
    private final DtoMapperService mapperService;

    @Override
    @Transactional(readOnly = true)
    public AdminUserOutputDto getUser(long adminId) {
        AdminUser adminUser = adminValidator.validateExists(adminId);
        return mapperService.mapToAdminUserDto(adminUser);
    }

    @Override
    @Transactional
    public void editUser(long id, AdminUserInputDto adminUserDto) {
        AdminUser admin = adminValidator.validateExists(id);
        AdminUser adminFromDto = mapperService.mapToAdminUser(adminUserDto);
        adminValidator.validateInput(adminFromDto);
        setFields(admin, adminFromDto);
        adminRepository.save(admin);
    }

    private void setFields(AdminUser admin, AdminUser adminFromDto){
        admin.setFirstName(adminFromDto.getFirstName());
        admin.setLastName(adminFromDto.getLastName());
        admin.setEmail(adminFromDto.getEmail());
        admin.setPassword(adminFromDto.getPassword());
        admin.setPhone(adminFromDto.getPhone());
    }

}
