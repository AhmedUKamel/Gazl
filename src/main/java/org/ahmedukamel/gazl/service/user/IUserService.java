package org.ahmedukamel.gazl.service.user;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUserService {
    Object getProfile();

    Object updateProfile(Object object);

    Object updatePassword(String oldPassword, String newPassword, boolean devicesSignOut);

    void uploadPicture(MultipartFile file) throws IOException;

    void deletePicture() throws IOException;
}