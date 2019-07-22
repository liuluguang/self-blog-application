package com.llg.blog.service;

import com.llg.blog.po.User;

public interface UserService {

    User checkUser(String username,String password);
}
