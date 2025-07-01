package com.demo.services;

import com.demo.api.dto.AccountDTO;
import com.demo.models.Account;

import java.util.List;

public interface IAccountService {
    List<Account> getAll();
    Account findById(Long id);
    boolean existsById(Long id);
    Account save(AccountDTO cat) throws Exception;
    void deleteById(Long id) throws Exception;
}
