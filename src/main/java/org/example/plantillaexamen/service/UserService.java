package org.example.plantillaexamen.service;

import org.example.plantillaexamen.dto.UserDto;

public interface UserService {

    /**
     * Verifica las credenciales y devuelve un DTO seguro para guardar en sesión.
     */
    UserDto authenticate(String username, String password);

    /**
     * Registra un usuario nuevo aplicando validaciones previas.
     */
    UserDto register(String username, String password);
}

