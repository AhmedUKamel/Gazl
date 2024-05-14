package org.ahmedukamel.gazl.controller.charity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.ahmedukamel.gazl.annotation.GovernmentAuthorization;
import org.ahmedukamel.gazl.dto.charity.CharityRequest;
import org.ahmedukamel.gazl.service.charity.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/v1/charity")
public class CharityController {
    private final ICharityService service;

    public CharityController(CharityService service) {
        this.service = service;
    }

    @GovernmentAuthorization
    @PostMapping
    public ResponseEntity<?> createCharity(@Valid @RequestBody CharityRequest request) {
        return ResponseEntity.created(URI.create("/api/v1/charity"))
                .body(service.createCharity(request));
    }

    @GovernmentAuthorization
    @PutMapping(value = "{charityId}")
    public ResponseEntity<?> updateCharity(@Min(value = 1) @PathVariable Integer charityId,
                                           @Valid @RequestBody CharityRequest request) {
        return ResponseEntity.accepted().body(service.updateCharity(charityId, request));
    }

    @GovernmentAuthorization
    @DeleteMapping(value = "{charityId}")
    public ResponseEntity<?> deleteCharity(@Min(value = 1) @PathVariable Integer charityId) {
        return ResponseEntity.accepted().body(service.deleteCharity(charityId));
    }

    @GetMapping(value = "public/{charityId}")
    public ResponseEntity<?> getCharity(@Min(value = 1) @PathVariable Integer charityId) {
        return ResponseEntity.ok().body(service.getCharity(charityId));
    }

    @GetMapping(value = "public/all")
    public ResponseEntity<?> getCharities(@Min(value = 1) @RequestParam(value = "size", defaultValue = "10") long pageSize,
                                          @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") long pageNumber) {
        return ResponseEntity.ok().body(service.getCharities(pageSize, pageNumber));
    }
}