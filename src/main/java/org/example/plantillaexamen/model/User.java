package org.example.plantillaexamen.model;

import jakarta.persistence.*;
import lombok.*;
/**
 * Entitat JPA que representa els usuaris registrats amb el seu correu i hash de contrasenya.
 */

//este es un ejemplo de modelo para dar sentido a la interfaz del dao por ahora
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

//  Si es password sin hash este
    @Column(nullable = false)
    private String password;

    // Si pidiera password cifrado, guardamos el hash, no la contraseña
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /*
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    */
    public User() {
    }
}