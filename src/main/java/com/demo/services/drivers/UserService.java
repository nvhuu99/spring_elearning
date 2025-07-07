package com.demo.services.drivers;

import com.demo.exceptions.NotFoundException;
import com.demo.models.User;
import com.demo.repositories.IUserRepository;
import com.demo.services.IUserService;
import com.demo.services.dto.user.SaveUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return repo.findAll();
    }

    @Override
    public User findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return repo.findByUsername(username).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repo.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }

    @Override
    public User save(Long id, SaveUser data) throws Exception {
        User user = new User();
        if (id != null) {
            user = findById(id);
            if (user == null) {
                throw new NotFoundException("User ID", id.toString());
            }
        }
        user.setUsername(data.getUsername());
        user.setEmail(data.getEmail());
        user.setPassword(data.getPassword());
        user.setAddress(data.getAddress());
        user.setBirthdate(data.getBirthdate());
        if (data.getPassword() != null) {
            var hash = passwordEncoder.encode(data.getPassword());
            user.setPassword(hash);
        } else {
            user.setPassword(null);
        }
        if (id == null) {
            user.setCreated(LocalDateTime.now());
        }
        user.setUpdated(LocalDateTime.now());
        return repo.save(user);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        if (! existsById(id)) {
            throw new NotFoundException("User ID", id.toString());
        }
        repo.deleteById(id);
    }

    @Override
    public boolean checkAccessToken(String username, String accessToken) {
        var usr = repo.findByUsernameAndAccessToken(username, accessToken);
        return usr.isPresent();
    }

    @Override
    public String invokeAccessToken(String username) throws Exception {
        var usr = findByUsername(username);
        if (usr == null) {
            throw new NotFoundException("Username", username);
        }

        var accessToken = UUID.randomUUID().toString();
        usr.setAccessToken(accessToken);
        repo.save(usr);

        return accessToken;
    }

    @Override
    public void invalidateAccessToken(String username) throws Exception {
        var usr = findByUsername(username);
        if (usr == null) {
            throw new NotFoundException("Username", username);
        }
        usr.setAccessToken(null);
        repo.save(usr);
    }
}
