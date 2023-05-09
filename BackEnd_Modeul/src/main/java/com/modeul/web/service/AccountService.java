package com.modeul.web.service;

import java.util.List;

import com.modeul.web.entity.Account;
import com.modeul.web.entity.AccountView;

public interface AccountService {

    int addAccount(Account account);

    // Account getById(Long memberId);

    List<Account> getViewById(Long memberId);
    
}
