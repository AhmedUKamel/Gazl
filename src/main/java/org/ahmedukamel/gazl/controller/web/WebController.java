package org.ahmedukamel.gazl.controller.web;

import org.ahmedukamel.gazl.service.web.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/public")
public class WebController {
    private final IWebService service;

    public WebController(WebService service) {
        this.service = service;
    }

    @GetMapping(value = "resources-images")
    public ResponseEntity<byte[]> getResourceImages(@RequestParam(value = "image") String name) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(service.readStaticImage(name));
    }
}