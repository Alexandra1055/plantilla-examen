package org.example.plantillaexamen.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// para jpa/orm, cambiar examenPU por el nombre que salga en persistence.xml <persistence-unit name=" ">
public class ConnectionManager {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("examenPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
