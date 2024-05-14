package org.ahmedukamel.gazl.service.government;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.ahmedukamel.gazl.constant.LocaleConstants;
import org.ahmedukamel.gazl.constant.PathConstants;
import org.ahmedukamel.gazl.dto.api.ApiResponse;
import org.ahmedukamel.gazl.dto.government.GovernmentResponse;
import org.ahmedukamel.gazl.mapper.government.GovernmentResponseMapper;
import org.ahmedukamel.gazl.model.Government;
import org.ahmedukamel.gazl.repository.GovernmentRepository;
import org.ahmedukamel.gazl.service.db.DatabaseService;
import org.ahmedukamel.gazl.service.image.IImageService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GovernmentService implements IGovernmentService {
    private final GovernmentResponseMapper mapper;
    private final GovernmentRepository repository;
    private final DatabaseService databaseService;
    private final MessageSource messageSource;
    private final IImageService imageService;

    @SneakyThrows
    @Override
    public Object createGovernment(String name, MultipartFile image) {
        databaseService.unique(repository::existsByName, name.strip(), Government.class);

        String logo = imageService.saveImage(image, PathConstants.GOVERNMENT_LOGO_PATH);

        Government government = new Government();
        government.setName(name);
        government.setLogo(logo);

        Government savedGovernment = repository.save(government);

        GovernmentResponse response = mapper.apply(savedGovernment);
        String message = messageSource.getMessage("org.ahmedukamel.gazl.service.government.GovernmentService.createGovernment",
                null, LocaleConstants.ARABIC);

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object updateGovernment(Integer id, String name) {
        Government government = databaseService.get(repository::findById, id, Government.class);

        GovernmentResponse response = mapper.apply(government);

        if (!government.getName().equalsIgnoreCase(name)) {
            databaseService.unique(repository::existsByName, name.strip(), Government.class);

            government.setName(name);
            Government savedGovernment = repository.save(government);

            response = mapper.apply(savedGovernment);
        }

        String message = messageSource.getMessage("org.ahmedukamel.gazl.service.government.GovernmentService.updateGovernment",
                null, LocaleConstants.ARABIC);

        return new ApiResponse(true, message, response);
    }

    @Transactional
    @Override
    public Object deleteGovernment(Integer id) {
        Government government = databaseService.get(repository::findById, id, Government.class);

        final String message;

        if (government.getEmployees().isEmpty()) {
            repository.delete(government);

            message = messageSource.getMessage("org.ahmedukamel.gazl.service.government.GovernmentService.deleteGovernment",
                    null, LocaleConstants.ARABIC);

            return new ApiResponse(true, message, "");
        }

        message = messageSource.getMessage("org.ahmedukamel.gazl.service.government.GovernmentService.deleteGovernment.IllegalArgumentException",
                null, LocaleConstants.ARABIC);

        throw new IllegalArgumentException(message);
    }

    @Override
    public Object getGovernment(Integer id) {
        Government government = databaseService.get(repository::findById, id, Government.class);

        GovernmentResponse response = mapper.apply(government);
        String message = messageSource.getMessage("org.ahmedukamel.gazl.service.government.GovernmentService.getGovernment",
                null, LocaleConstants.ARABIC);

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getGovernments(long pageSize, long pageNumber) {
        List<GovernmentResponse> response = repository
                .selectGovernmentWithPagination(pageSize, pageSize * (pageNumber - 1))
                .stream()
                .map(mapper)
                .toList();
        String message = messageSource.getMessage("org.ahmedukamel.gazl.service.government.GovernmentService.getGovernments",
                null, LocaleConstants.ARABIC);

        return new ApiResponse(true, message, response);
    }
}