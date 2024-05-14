package org.ahmedukamel.gazl.service.charity;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.constant.LocaleConstants;
import org.ahmedukamel.gazl.dto.api.ApiResponse;
import org.ahmedukamel.gazl.dto.charity.CharityRequest;
import org.ahmedukamel.gazl.dto.charity.CharityResponse;
import org.ahmedukamel.gazl.mapper.charity.CharityResponseMapper;
import org.ahmedukamel.gazl.model.Charity;
import org.ahmedukamel.gazl.repository.CharityRepository;
import org.ahmedukamel.gazl.saver.charity.CharitySaver;
import org.ahmedukamel.gazl.service.db.DatabaseService;
import org.ahmedukamel.gazl.updater.charity.CharityUpdater;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharityService implements ICharityService {
    private final DatabaseService databaseService;
    private final CharityRepository repository;
    private final CharityResponseMapper mapper;
    private final MessageSource messageSource;
    private final CharityUpdater updater;
    private final CharitySaver saver;

    @Override
    public Object createCharity(Object object) {
        CharityRequest request = (CharityRequest) object;
        Charity savedCharity = saver.apply(request);

        CharityResponse response = mapper.apply(savedCharity);
        String message = messageSource.getMessage("org.ahmedukamel.gazl.service.charity.CharityService.createCharity",
                null, LocaleConstants.ARABIC);

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object updateCharity(Integer id, Object object) {
        Charity charity = databaseService.get(repository::findById, id, Charity.class);
        CharityRequest request = (CharityRequest) object;

        Charity updatedCharity = updater.apply(charity, request);

        CharityResponse response = mapper.apply(updatedCharity);
        String message = messageSource.getMessage("org.ahmedukamel.gazl.service.charity.CharityService.updateCharity",
                null, LocaleConstants.ARABIC);

        return new ApiResponse(true, message, response);
    }

    @Transactional
    @Override
    public Object deleteCharity(Integer id) {
        Charity charity = databaseService.get(repository::findById, id, Charity.class);

        final String message;

        if (charity.getEmployees().isEmpty()) {
            repository.delete(charity);

            message = messageSource.getMessage("org.ahmedukamel.gazl.service.charity.CharityService.deleteCharity",
                    null, LocaleConstants.ARABIC);

            return new ApiResponse(true, message, "");
        }

        message = messageSource.getMessage("org.ahmedukamel.gazl.service.charity.CharityService.deleteCharity.IllegalArgumentException",
                null, LocaleConstants.ARABIC);

        throw new IllegalArgumentException(message);
    }

    @Override
    public Object getCharity(Integer id) {
        Charity charity = databaseService.get(repository::findById, id, Charity.class);

        CharityResponse response = mapper.apply(charity);
        String message = messageSource.getMessage("org.ahmedukamel.gazl.service.charity.CharityService.getCharity",
                null, LocaleConstants.ARABIC);

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getCharities(long pageSize, long pageNumber) {
        List<CharityResponse> response = repository
                .selectCharitiesWithPagination(pageSize, pageSize * (pageNumber - 1))
                .stream()
                .map(mapper)
                .toList();

        String message = messageSource.getMessage("org.ahmedukamel.gazl.service.charity.CharityService.getCharities",
                null, LocaleConstants.ARABIC);

        return new ApiResponse(true, message, response);
    }
}