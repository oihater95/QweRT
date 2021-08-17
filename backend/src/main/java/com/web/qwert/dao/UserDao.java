
package com.web.qwert.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.qwert.model.category.Category;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User getUserByEmail(String email);
    
    // 로그인시 email과 password로 User 찾는다.
    Optional<User> findUserByEmailAndPassword(String email, String password);
    
    Optional<User> findUserByEmailOrNickname(String email, String nickname);
    
    Optional<User> findUserByEmail(String email);
    
    Optional<User> findUserByNickname(String nickname);
    
    List<User> findByNicknameContainingIgnoreCase(String nickname, Pageable pageable);
}
