//package com.adilzhan.firsttask.service.hibernate;
//
//import com.adilzhan.firsttask.dao.hibernateDao.UserDao;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthService {
//    private final UserDao userDao;
//
//    public AuthService(UserDao userDao) {
//        this.userDao = userDao;
//    }
//
//    public boolean authenticate(String username, String password) {
//        return userDao.findByUsername(username)
//                .map(user -> user.getPassword().equals(password))
//                .orElse(false);
//    }
//}
