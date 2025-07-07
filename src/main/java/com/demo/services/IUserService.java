package com.demo.services;

import com.demo.services.dto.user.SaveUser;
import com.demo.models.User;
import java.util.List;

public interface IUserService {
    List<User> getAll();
    User findById(Long id);
    User findByUsername(String username);

    boolean existsById(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    User save(Long id, SaveUser cat) throws Exception;
    void deleteById(Long id) throws Exception;

    boolean checkAccessToken(String username, String accessToken);
    String invokeAccessToken(String username) throws Exception;
    void invalidateAccessToken(String username) throws Exception;
}
