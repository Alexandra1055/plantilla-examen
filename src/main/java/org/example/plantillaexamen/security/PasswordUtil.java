package org.example.plantillaexamen.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    private static final String ALGORITHM = "SHA-256";

    // Generamos el hash en hexadecimal
    public static String hash(String plainPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            byte[] digest = md.digest(plainPassword.getBytes(StandardCharsets.UTF_8));

            // Convertimos el byte[] a String hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b)); // 2 dígitos hex por byte
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error creando hash de la contraseña", e);
        }
    }

    // Comparamos contraseña con el hash guardado en BD
    public static boolean matches(String plainPassword, String hashedPassword) {
        String hashOfInput = hash(plainPassword);
        return hashOfInput.equals(hashedPassword);
    }//En el registro de usuario, antes de guardar en BD: user.setPasswordHash(PasswordUtil.hash(password));

}
