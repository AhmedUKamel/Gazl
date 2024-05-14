package org.ahmedukamel.gazl.service.charity;

public interface ICharityService {
    Object createCharity(Object object);

    Object updateCharity(Integer id, Object object);

    Object deleteCharity(Integer id);

    Object getCharity(Integer id);

    Object getCharities(long pageSize, long pageNumber);
}