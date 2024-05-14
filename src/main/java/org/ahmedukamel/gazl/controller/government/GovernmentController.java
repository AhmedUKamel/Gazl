package org.ahmedukamel.gazl.controller.government;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.ahmedukamel.gazl.annotation.AdminAuthorization;
import org.ahmedukamel.gazl.annotation.NotEmpty;
import org.ahmedukamel.gazl.service.government.GovernmentService;
import org.ahmedukamel.gazl.service.government.IGovernmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping(value = "api/v1/government")
public class GovernmentController {
    private final IGovernmentService service;

    public GovernmentController(GovernmentService service) {
        this.service = service;
    }

    @AdminAuthorization
    @PostMapping
    public ResponseEntity<?> createGovernment(@NotBlank @RequestParam(value = "name") String name,
                                              @NotEmpty @RequestParam(value = "logo") MultipartFile image) {
        return ResponseEntity.created(URI.create("/api/v1/government"))
                .body(service.createGovernment(name, image));
    }

    @AdminAuthorization
    @PutMapping(value = "{governmentId}")
    public ResponseEntity<?> updateGovernment(@Min(value = 1) @PathVariable(value = "governmentId") Integer id,
                                              @NotBlank @RequestParam(value = "name") String name) {
        return ResponseEntity.accepted().body(service.updateGovernment(id, name));
    }

    @AdminAuthorization
    @PostMapping(value = "{governmentId}/logo")
    public ResponseEntity<?> updateLogo(@Min(value = 1) @PathVariable(value = "governmentId") Integer id,
                                        @NotEmpty @RequestParam(value = "logo") MultipartFile image) {
        return ResponseEntity.accepted().body(service.updateLogo(id, image));
    }

    @AdminAuthorization
    @DeleteMapping(value = "{governmentId}")
    public ResponseEntity<?> deleteGovernment(@Min(value = 1) @PathVariable(value = "governmentId") Integer id) {
        return ResponseEntity.accepted().body(service.deleteGovernment(id));
    }

    @GetMapping(value = "public/{governmentId}")
    public ResponseEntity<?> getGovernment(@Min(value = 1) @PathVariable(value = "governmentId") Integer id) {
        return ResponseEntity.ok().body(service.getGovernment(id));
    }

    @GetMapping(value = "public/all")
    public ResponseEntity<?> getGovernments(@Min(value = 1) @RequestParam(value = "size", defaultValue = "10") long pageSize,
                                            @Min(value = 1) @RequestParam(value = "number", defaultValue = "1") long pageNumber) {
        return ResponseEntity.ok().body(service.getGovernments(pageSize, pageNumber));
    }
}