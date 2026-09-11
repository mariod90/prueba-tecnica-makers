package com.makers.gestionprestamos.service.impl;

import com.makers.gestionprestamos.entity.Lending;
import com.makers.gestionprestamos.exception.ResourceNotFoundException;
import com.makers.gestionprestamos.repository.LendingRepository;
import com.makers.gestionprestamos.service.ILendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LendingService implements ILendingService {

    @Autowired
    private LendingRepository lendingRepository;


    @Override
    public Lending saveLending(Lending lending) {
        return lendingRepository.save(lending);
    }

    @Override
    public Lending updateLending(Long id, Lending.State newState) {
        Lending existingLending = lendingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prestamo no encontrado con id: " + id));
        existingLending.setState(newState);

        return lendingRepository.save(existingLending);
    }

    @Override
    public Lending getLendingById(Long id) {
        return lendingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prestamo no encontrado con id: " + id));
    }

    @Override
    public List<Lending> getAllLendings() {
        return lendingRepository.findAll();
    }

}
