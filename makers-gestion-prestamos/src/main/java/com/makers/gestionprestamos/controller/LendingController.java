package com.makers.gestionprestamos.controller;

import com.makers.gestionprestamos.entity.Lending;
import com.makers.gestionprestamos.service.ILendingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lendings")
public class LendingController {

    @Autowired
    private ILendingService lendingService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Lending> createLending(@Valid @RequestBody Lending lending) {
        return ResponseEntity.ok(lendingService.saveLending(lending));
    }

    @PatchMapping("/{id}/state")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Lending> updateLendingState(@PathVariable Long id, @RequestBody UpdateStateRequest request) {
        return ResponseEntity.ok(lendingService.updateLending(id, request.state()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MASTER','ADMIN','USER')")
    public ResponseEntity<Lending> getById(@PathVariable Long id) {
        return ResponseEntity.ok(lendingService.getLendingById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MASTER','ADMIN','USER')")
    public ResponseEntity<List<Lending>> getAll() {
        return ResponseEntity.ok(lendingService.getAllLendings());
    }

    public record UpdateStateRequest(Lending.State state) {}
}
