package org.ahmedukamel.gazl.service.government;

import org.springframework.web.multipart.MultipartFile;

public interface IGovernmentService {
    Object createGovernment(String name, MultipartFile image);

    Object updateGovernment(Integer id, String name);

    Object deleteGovernment(Integer id);

    Object getGovernment(Integer id);

    Object getGovernments(long pageSize, long pageNumber);
}