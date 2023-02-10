package com.cinema.cinema.themes.user.controller;

import com.cinema.cinema.themes.user.model.AdminUserDtoRead;
import com.cinema.cinema.themes.user.model.AdminUserDtoWrite;
import com.cinema.cinema.themes.user.model.StandardUserDtoRead;
import com.cinema.cinema.themes.user.service.AdminUserService;
import com.cinema.cinema.themes.user.service.StandardUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@Tag(name = "Admins")
@RequestMapping("/${app.prefix}/${app.version}")
public class AdminUserController {

    private final AdminUserService adminService;
    private final StandardUserService userService;

    @GetMapping("/admins/{adminId}")
    @Operation(summary = "Get admin by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin returned"),
            @ApiResponse(responseCode = "404", description = "Admin not found")})
    public ResponseEntity<AdminUserDtoRead> getAdmin(@PathVariable long adminId) {
        AdminUserDtoRead admin = adminService.getUser(adminId);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @PutMapping("/admins/{adminId}")
    @Operation(summary = "Update admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Admin not found")})
    public ResponseEntity<Void> editAdmin(@PathVariable long adminId, @RequestBody AdminUserDtoWrite admin) {
        adminService.editUser(adminId, admin);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/admin-panel/users/{userId}/activate")
    @Operation(summary = "Activate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User activated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    public ResponseEntity<StandardUserDtoRead> activateUser(@PathVariable long userId) {
        StandardUserDtoRead user = userService.editIsActive(userId, true);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/admin-panel/users/{userId}/deactivate")
    @Operation(summary = "Deactivate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deactivated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    public ResponseEntity<StandardUserDtoRead> deactivateUser(@PathVariable long userId) {
        StandardUserDtoRead user = userService.editIsActive(userId, false);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/admin-panel/users/{userId}/password")
    @Operation(summary = "Generate new password for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New password generated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    public ResponseEntity<String> generateNewPasswordForUser(@PathVariable long userId) {
        String password = userService.generateNewPassword(userId);
        return new ResponseEntity<>(password, HttpStatus.OK);
    }

}
