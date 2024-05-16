package org.ahmedukamel.gazl.service.opportunity;

public interface IOpportunityService {
    Object createOpportunity(Object object);

    Object updateOpportunity(Long id, Object object);

    Object deleteOpportunity(Long id);

    Object getOpportunity(Long id);

    Object getOpportunities(long pageSize, long pageNumber);
}