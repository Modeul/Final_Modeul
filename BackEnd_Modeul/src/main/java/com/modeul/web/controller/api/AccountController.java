package com.modeul.web.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modeul.web.entity.Account;
import com.modeul.web.service.AccountService;


@RestController
@RequestMapping("api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/account/{stuffId}")
    public Account getAccount(
        @PathVariable("stuffId") Long stuffId){
        Account account = accountService.getAccountById(stuffId);
        return account;
    }

    // 최근 계좌 
    @GetMapping("/account/recent/{memberId}")
    public List<Account> getAccountList(
        @PathVariable("memberId") Long memberId){  

        return accountService.getViewById(memberId);
    }
    
    
}
