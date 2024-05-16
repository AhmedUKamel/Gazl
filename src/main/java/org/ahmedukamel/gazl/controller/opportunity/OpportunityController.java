package org.ahmedukamel.gazl.controller.opportunity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.ahmedukamel.gazl.dto.opportunity.OpportunityRequest;
import org.ahmedukamel.gazl.service.opportunity.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/v1/opportunity")
public class OpportunityController {
    private final IOpportunityService service;

    public OpportunityController(OpportunityService service) {
        this.service = service;
    }

    @PreAuthorize(value = "hasAnyAuthority('BUSINESS', 'CHARITY', 'GOVERNMENT', 'ADMIN')")
    @PostMapping
    public ResponseEntity<?> createOpportunity(@Valid @RequestBody OpportunityRequest request) {
        return ResponseEntity.created(URI.create("/api/v1/opportunity"))
                .body(service.createOpportunity(request));
    }

    @PreAuthorize(value = "hasAnyAuthority('BUSINESS', 'CHARITY', 'GOVERNMENT', 'ADMIN')")
    @PutMapping(value = "{opportunityId}")
    public ResponseEntity<?> updateOpportunity(@Min(value = 1) @PathVariable(value = "opportunityId") Long id,
                                               @Valid @RequestBody OpportunityRequest request) {
        return ResponseEntity.accepted().body(service.updateOpportunity(id, request));
    }

    @PreAuthorize(value = "hasAnyAuthority('BUSINESS', 'CHARITY', 'GOVERNMENT', 'ADMIN')")
    @DeleteMapping(value = "{opportunityId}")
    public ResponseEntity<?> deleteOpportunity(@Min(value = 1) @PathVariable(value = "opportunityId") Long id) {
        return ResponseEntity.accepted().body(service.deleteOpportunity(id));
    }

    @GetMapping(value = "public/{opportunityId}")
    public ResponseEntity<?> getOpportunity(@PathVariable(value = "opportunityId") Long id) {
        return ResponseEntity.ok().body(service.getOpportunity(id));
    }

    @GetMapping(value = "public/all")
    public ResponseEntity<?> getOpportunities(@Min(value = 1) @RequestParam(value = "size", defaultValue = "10") long pageSize,
                                              @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") long pageNumber) {
        return ResponseEntity.ok().body(service.getOpportunities(pageSize, pageNumber));
    }
}