package com.modeul.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeul.web.entity.Account;
import com.modeul.web.entity.AccountView;
import com.modeul.web.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public int addAccount(Account account) {
        
        return accountRepository.insertAccount(account);
    }

    @Override
    public List<Account> getViewById(Long memberId) {
        int size = 5;
        int offset = 0;

        return accountRepository.findViewById(memberId, size, offset, null, null );
    }

    @Override
    public AccountView getAccountById(Long stuffId) {

        return accountRepository.findAccountById(stuffId);
    }
    
}
