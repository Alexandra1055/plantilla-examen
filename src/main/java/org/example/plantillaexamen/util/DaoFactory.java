package org.example.plantillaexamen.util;

import org.example.plantillaexamen.dao.UserDao;
import org.example.plantillaexamen.dao.UserDaoJdbc;
import org.example.plantillaexamen.dao.UserDaoOrm;

public class DaoFactory {
    private static final String IMPL = System.getProperty("dao.impl", "jdbc");

    private static final UserDao USER_DAO =
            "orm".equalsIgnoreCase(IMPL) ? new UserDaoOrm() : new UserDaoJdbc();

    public static UserDao getUserDao() {
        return USER_DAO;
    }
}
