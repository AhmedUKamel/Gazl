package org.ahmedukamel.gazl.service.web;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WebService implements IWebService {
    @Override
    public byte[] readStaticImage(String name) {
        final byte[] image;
        try {
            Resource resource = new ClassPathResource("static/images/" + name);

            if (!resource.exists()) {
                resource = new ClassPathResource("static/images/image-not-found.png");
            }

            image = resource.getContentAsByteArray();

        } catch (IOException ignored) {
            throw new RuntimeException("Unable to read static image: " + name);
        }

        return image;
    }
}