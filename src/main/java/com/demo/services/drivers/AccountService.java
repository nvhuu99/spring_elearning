package com.demo.services.drivers;

import com.demo.api.dto.AccountDTO;
import com.demo.api.dto.CategoryDTO;
import com.demo.exceptions.NotFoundException;
import com.demo.models.Account;
import com.demo.models.Category;
import com.demo.repositories.IAccountRepository;
import com.demo.repositories.ICategoryRepository;
import com.demo.services.IAccountService;
import com.demo.services.ICategoryService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AccountService implements IAccountService {
    IAccountRepository repo;

    @Override
    public List<Account> getAll() {
        return repo.findAll();
    }

    @Override
    public Account findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }

    @Override
    public Account save(AccountDTO data) throws Exception {
        Account account = new Account();
        if (data.getId() != null) {
            account = findById(data.getId());
            if (account == null) {
                throw new NotFoundException("Account ID",  data.getId().toString());
            }
        }
        account.setUsername(data.getUsername());
        account.setEmail(data.getEmail());
        account.setPassword(data.getPassword());
        account.setAddress(data.getAddress());
        account.setBirthdate(data.getBirthdate());
        if (data.getId() == null) {
            account.setCreated(LocalDateTime.now());
        }
        account.setUpdated(LocalDateTime.now());
        return repo.save(account);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        if (! existsById(id)) {
            throw new NotFoundException("Account ID", id.toString());
        }
        repo.deleteById(id);
    }
}
