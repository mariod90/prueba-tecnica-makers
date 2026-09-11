package com.makers.gestionprestamos.service;

import com.makers.gestionprestamos.entity.Lending;
import com.makers.gestionprestamos.entity.User;

import java.util.List;

public interface IUserService {

    User saveUser(User user);

    User updateUser(Long id, User user);

    User getUserById(Long id);

    void deleteUser(Long id);

    List<Lending> getLendingsByUserId(Long userId);
}
