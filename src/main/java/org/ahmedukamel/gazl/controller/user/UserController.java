package org.ahmedukamel.gazl.controller.user;

import jakarta.validation.Valid;
import org.ahmedukamel.gazl.dto.user.UpdateProfileRequest;
import org.ahmedukamel.gazl.service.user.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "api/v1/user")
public class UserController {
    private final IUserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "profile")
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.ok().body(service.getProfile());
    }

    @PutMapping(value = "profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok().body(service.updateProfile(request));
    }

    @PutMapping(value = "password-change")
    public ResponseEntity<?> changePassword(@RequestParam(value = "oldPassword") String oldPassword,
                                            @RequestParam(value = "newPassword") String newPassword,
                                            @RequestParam(value = "devicesSignOut", defaultValue = "false") boolean devicesSignOut) {
        return ResponseEntity.accepted().body(service.updatePassword(oldPassword, newPassword, devicesSignOut));
    }

    @PostMapping(value = "picture")
    public ResponseEntity<?> uploadPicture(@RequestParam(value = "picture") MultipartFile file) throws IOException {
        service.uploadPicture(file);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "picture")
    public ResponseEntity<?> deletePicture() throws IOException {
        service.deletePicture();
        return ResponseEntity.noContent().build();
    }
}