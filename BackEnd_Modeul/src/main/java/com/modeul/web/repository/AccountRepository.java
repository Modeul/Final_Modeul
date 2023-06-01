package com.modeul.web.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.modeul.web.entity.Account;
import com.modeul.web.entity.AccountView;

@Mapper
public interface AccountRepository {

    int insertAccount(Account account);

    List<Account> findViewById(Long memberId, int size, int offset, String orderField, String orderDir);

    AccountView findAccountById(Long stuffId);
    
}
