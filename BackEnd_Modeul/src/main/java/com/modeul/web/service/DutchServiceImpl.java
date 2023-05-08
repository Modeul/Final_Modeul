package com.modeul.web.service;

import java.security.DrbgParameters.Reseed;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modeul.web.entity.Account;
import com.modeul.web.entity.DutchMemberView;
import com.modeul.web.entity.DutchView;
import com.modeul.web.repository.DutchRepository;

@Service
public class DutchServiceImpl implements DutchService {

    @Autowired
    private DutchRepository repository;

    @Override
    public List<DutchView> getViewBymemberId(Long memberId, int page, int month) {

        int size = page * 8;

        return repository.findViewBymemberId(memberId, month, "date", "desc", size, 0);
    }

    @Override
    public List<DutchMemberView> getMemberViewBystuffId(Long stuffId) {
        return repository.findMemberViewBystuffId(stuffId);
    }

    @Override
    public List<Integer> getViewMonthBymemberId(Long memberId) {
        return repository.findViewMonthBymemberId(memberId);
    }

    @Transactional
    @Override
    public void addAllDutch(Long stuffId, Map<String, Object> dutch) {
    // public void addAllDutch(Long stuffId, Map<Long, Integer> prices, Account account) {
        
        ObjectMapper objectMapper = new ObjectMapper();
        Map<Long,Integer> prices = objectMapper.convertValue(dutch.get("prices"), Map.class);
        // Map<Long,Integer> prices = (Map<Long, Integer>) dutch.get("prices");
        
        // 원래는 int int 형이다.
        // 하지만, String, String으로 불러냄.
        for (Long memberId : prices.keySet()) {
            System.out.println("value:"+prices.get(memberId));
            Integer price = prices.get(memberId);
            System.out.println("memberId: "+memberId);
            System.out.println("price: "+price);
            
            // int insertDutchCount = repository.insertDutch(stuffId, memberId, price);
            // System.out.println("insertDutchCount:" + insertDutchCount);
        }

        ObjectMapper objectMapper1 = new ObjectMapper();
        // Map<String,String> accountInfo = (Map<String, String>) dutch.get("account");
        Map<String,String> accountInfo = objectMapper1.convertValue(dutch.get("account"), Map.class);
        Account account = new Account();

        for (String id : accountInfo.keySet()) {
            if(id=="bankName"){
                System.out.println("bankName: "+accountInfo.get(id));
                account.setBankName(accountInfo.get(id)); 
            }

            if(id=="number"){
                System.out.println("number: "+accountInfo.get(id));
                account.setNumber(accountInfo.get(id));
            }

            if(id=="memberId"){
                System.out.println("memberId: "+Long.parseLong(accountInfo.get(id)));
                account.setMemberId(Long.parseLong(accountInfo.get(id)));
            }
            
        }
        System.out.printf(account.toString());

        // int insertAccountCount = repository.insertAccount(account.getBankName(), account.getNumber(), account.getMemberId());
        // System.out.println("insertAccountCount:" + insertAccountCount);
    }
    
}
