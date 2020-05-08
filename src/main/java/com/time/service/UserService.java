package com.time.service;

import com.time.pojo.User;

public interface UserService {

    User checkUser(String username, String password);
}
