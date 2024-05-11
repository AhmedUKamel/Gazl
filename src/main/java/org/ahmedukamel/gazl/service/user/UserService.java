package org.ahmedukamel.gazl.service.user;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.constant.LocaleConstants;
import org.ahmedukamel.gazl.constant.PathConstants;
import org.ahmedukamel.gazl.dto.api.ApiResponse;
import org.ahmedukamel.gazl.dto.user.ProfileResponse;
import org.ahmedukamel.gazl.dto.user.UpdateProfileRequest;
import org.ahmedukamel.gazl.mapper.user.ProfileResponseMapper;
import org.ahmedukamel.gazl.model.User;
import org.ahmedukamel.gazl.repository.BearerTokenRepository;
import org.ahmedukamel.gazl.repository.UserRepository;
import org.ahmedukamel.gazl.service.image.IImageService;
import org.ahmedukamel.gazl.updater.user.ProfileUpdater;
import org.ahmedukamel.gazl.util.ContextHolderUtils;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    final ProfileResponseMapper profileResponseMapper;
    final BearerTokenRepository bearerTokenRepository;
    final PasswordEncoder passwordEncoder;
    final UserRepository userRepository;
    final ProfileUpdater profileUpdater;
    final MessageSource messageSource;
    final IImageService imageService;

    @Override
    public Object getProfile() {
        User user = ContextHolderUtils.getUser();
        ProfileResponse response = profileResponseMapper.apply(user);
        String message = messageSource.getMessage("org.ahmedukamel.gazl.service.user.UserService.getProfile", null, LocaleConstants.ARABIC);
        return new ApiResponse(true, message, response);
    }

    @Override
    public Object updateProfile(Object object) {
        UpdateProfileRequest request = (UpdateProfileRequest) object;
        User user = ContextHolderUtils.getUser();
        User updatedUser = profileUpdater.apply(user, request);
        ProfileResponse response = profileResponseMapper.apply(updatedUser);
        String message = messageSource.getMessage("org.ahmedukamel.gazl.service.user.UserService.updateProfile", null, LocaleConstants.ARABIC);
        return new ApiResponse(true, message, response);
    }

    @Override
    public Object updatePassword(String oldPassword, String newPassword, boolean devicesSignOut) {
        User user = ContextHolderUtils.getUser();

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            String message = messageSource.getMessage("org.ahmedukamel.gazl.service.user.UserService.updatePassword.wrong.password", null, LocaleConstants.ARABIC);
            throw new IllegalArgumentException(message);
        }

        if (oldPassword.equals(newPassword)) {
            String message = messageSource.getMessage("org.ahmedukamel.gazl.service.user.UserService.updatePassword.same.password", null, LocaleConstants.ARABIC);
            throw new IllegalArgumentException(message);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        if (devicesSignOut) {
            bearerTokenRepository.revokeTokens(user);
        }

        String message = messageSource.getMessage("org.ahmedukamel.gazl.service.user.UserService.updatePassword", null, LocaleConstants.ARABIC);
        return new ApiResponse(true, message, "");
    }

    @Override
    public void uploadPicture(MultipartFile file) throws IOException {
        User user = ContextHolderUtils.getUser();

        if (StringUtils.hasLength(user.getPicture())) {
            try {
                imageService.deleteImage(PathConstants.PROFILE_PICTURE_PATH.resolve(user.getPicture()));
            } catch (IOException ignored) {
            }
        }

        String name = imageService.saveImage(file, PathConstants.PROFILE_PICTURE_PATH);
        user.setPicture(name);
        userRepository.save(user);
    }

    @Override
    public void deletePicture() throws IOException {
        User user = ContextHolderUtils.getUser();

        if (StringUtils.hasLength(user.getPicture())) {
            imageService.deleteImage(PathConstants.PROFILE_PICTURE_PATH.resolve(user.getPicture()));

            user.setPicture(null);
            userRepository.save(user);
        }
    }
}