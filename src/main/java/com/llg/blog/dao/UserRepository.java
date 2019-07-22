package com.llg.blog.dao;

import com.llg.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {



    User findByUsernameAndPassword(String username, String password);

}
