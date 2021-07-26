
package com.web.qwert.dao.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.qwert.model.user.User;

public interface UserDao extends JpaRepository<User, String> {
    User getUserByEmail(String email);
    
    // 로그인시 email과 password로 User 찾는다.
    Optional<User> findUserByEmailAndPassword(String email, String password);
    
    Optional<User> findUserByEmailOrNickname(String email, String nickname);
    
    Optional<User> findUserByEmail(String email);
    
    Optional<User> findUserByNickname(String nickname);
}
