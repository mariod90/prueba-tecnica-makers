package com.makers.gestionprestamos.service;

import com.makers.gestionprestamos.entity.Lending;

import java.util.List;

public interface ILendingService {

    Lending saveLending(Lending lending);

    Lending updateLending(Long id, Lending.State newState);

    Lending getLendingById(Long id);

    List<Lending> getAllLendings();
}
