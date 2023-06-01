package com.modeul.web.service;

import java.util.List;

import com.modeul.web.entity.Account;
import com.modeul.web.entity.AccountView;

public interface AccountService {

    int addAccount(Account account);

    List<Account> getViewById(Long memberId);

    AccountView getAccountById(Long stuffId);
    
}
