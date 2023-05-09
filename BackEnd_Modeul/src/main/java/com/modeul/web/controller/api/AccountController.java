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
import com.modeul.web.entity.AccountView;
import com.modeul.web.service.AccountService;


@RestController
@RequestMapping("api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // 계좌 등록
    @PostMapping("/account")
    public int inputAccount(
        @RequestBody Account account){

            int accountCount = accountService.addAccount(account);

        return accountCount;
    }

    // 최근 계좌 
    @GetMapping("/account/recent/{memberId}")
    public List<Account> getAccountList(
        @PathVariable("memberId") Long memberId
    ){  

        return accountService.getViewById(memberId);
    }
    
    
}
