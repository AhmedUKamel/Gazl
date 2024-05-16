package org.ahmedukamel.gazl.service.opportunity;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.gazl.constant.LocaleConstants;
import org.ahmedukamel.gazl.dto.api.ApiResponse;
import org.ahmedukamel.gazl.dto.opportunity.OpportunityRequest;
import org.ahmedukamel.gazl.dto.opportunity.OpportunityResponse;
import org.ahmedukamel.gazl.mapper.opportunity.OpportunityResponseMapper;
import org.ahmedukamel.gazl.model.Opportunity;
import org.ahmedukamel.gazl.model.User;
import org.ahmedukamel.gazl.model.enumration.Role;
import org.ahmedukamel.gazl.repository.OpportunityRepository;
import org.ahmedukamel.gazl.saver.opportunity.OpportunitySaver;
import org.ahmedukamel.gazl.service.db.DatabaseService;
import org.ahmedukamel.gazl.updater.opportunity.OpportunityUpdater;
import org.ahmedukamel.gazl.util.ContextHolderUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpportunityService implements IOpportunityService {
    private final OpportunityResponseMapper opportunityResponseMapper;
    private final OpportunityRepository opportunityRepository;
    private final OpportunityUpdater opportunityUpdater;
    private final OpportunitySaver opportunitySaver;
    private final DatabaseService databaseService;
    private final MessageSource messageSource;

    @Override
    public Object createOpportunity(Object object) {
        OpportunityRequest request = (OpportunityRequest) object;
        Opportunity savedOpportunity = opportunitySaver.apply(request);

        OpportunityResponse response = opportunityResponseMapper.apply(savedOpportunity);
        String message = messageSource.getMessage("org.ahmedukamel.gazl.service.opportunity.OpportunityService.createOpportunity",
                null, LocaleConstants.ARABIC);

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object updateOpportunity(Long id, Object object) {
        Opportunity opportunity = databaseService.get(opportunityRepository::findById, id, Opportunity.class);
        OpportunityRequest request = (OpportunityRequest) object;

        validateAuthority(opportunity);
        Opportunity updatedOpportunity = opportunityUpdater.apply(opportunity, request);

        OpportunityResponse response = opportunityResponseMapper.apply(updatedOpportunity);
        String message = messageSource.getMessage("org.ahmedukamel.gazl.service.opportunity.OpportunityService.updateOpportunity",
                null, LocaleConstants.ARABIC);

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object deleteOpportunity(Long id) {
        Opportunity opportunity = databaseService.get(opportunityRepository::findById, id, Opportunity.class);

        validateAuthority(opportunity);
        opportunityRepository.delete(opportunity);

        String message = messageSource.getMessage("org.ahmedukamel.gazl.service.opportunity.OpportunityService.deleteOpportunity",
                null, LocaleConstants.ARABIC);

        return new ApiResponse(true, message, "");
    }

    @Override
    public Object getOpportunity(Long id) {
        Opportunity opportunity = databaseService.get(opportunityRepository::findById, id, Opportunity.class);

        OpportunityResponse response = opportunityResponseMapper.apply(opportunity);
        String message = messageSource.getMessage("org.ahmedukamel.gazl.service.opportunity.OpportunityService.getOpportunity",
                null, LocaleConstants.ARABIC);

        return new ApiResponse(true, message, response);
    }

    @Override
    public Object getOpportunities(long pageSize, long pageNumber) {
        List<OpportunityResponse> response = opportunityRepository
                .selectOpportunitiesWithPagination(pageSize, pageSize * (pageNumber - 1))
                .stream()
                .map(opportunityResponseMapper)
                .toList();
        String message = messageSource.getMessage("org.ahmedukamel.gazl.service.opportunity.OpportunityService.getOpportunities",
                null, LocaleConstants.ARABIC);

        return new ApiResponse(true, message, response);
    }

    private void validateAuthority(Opportunity opportunity) {
        User user = ContextHolderUtils.getUser();
        String message = messageSource.getMessage("org.ahmedukamel.gazl.service.opportunity.OpportunityService.validateAuthority.IllegalArgumentException",
                null, LocaleConstants.ARABIC);

        switch (opportunity.getType()) {
            case BUSINESS -> {
                if (user.getRole().equals(Role.VISITOR) ||
                    user.getRole().equals(Role.CHARITY) ||
                    user.getRole().equals(Role.GOVERNMENT)) {
                    throw new IllegalArgumentException(message);
                }
            }
            case GOVERNMENT -> {
                if (!user.getRole().equals(Role.GOVERNMENT)) {
                    throw new IllegalArgumentException(message);
                }
            }
            case CHARITY -> {
                if (!user.getRole().equals(Role.CHARITY)) {
                    throw new IllegalArgumentException(message);
                }
            }
        }
    }
}