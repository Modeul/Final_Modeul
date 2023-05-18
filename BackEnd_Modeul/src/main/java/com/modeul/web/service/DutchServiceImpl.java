package com.modeul.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modeul.web.entity.Account;
import com.modeul.web.entity.DutchMemberView;
import com.modeul.web.entity.DutchView;
import com.modeul.web.repository.DutchRepository;

@Service
public class DutchServiceImpl implements DutchService {

    @Autowired
    private DutchRepository repository;

    static int pageSize = 8;

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

        ObjectMapper objectMapper = new ObjectMapper();
        // Map<String, String> prices = new HashMap<>();

        // prices = (Map<String, String>) objectMapper.convertValue(dutch.get("prices"),
        // Map.class);

        // for (String memberId : prices.keySet()) {
        // String price = prices.get(memberId);

        // int insertDutchCount = repository.insertDutch(stuffId,
        // Long.parseLong(memberId), price);
        // System.out.println("insertDutchCount:" + insertDutchCount);
        // }

        // ----------------------------------------------------------------------------

        Map<String, Integer> prices = (Map<String, Integer>) dutch.get("prices");

        for (Map.Entry<String, Integer> entry : prices.entrySet()) {
        Long memberId = Long.valueOf(entry.getKey());
        String price = entry.getValue().toString();

        int insertDutchCount = repository.insertDutch(stuffId, memberId, price);
        System.out.println("insertDutchCount:" + insertDutchCount);
        }

        // ----------------------------------------------------------------------------

        Map<String, String> accountInfo = new HashMap<>();
        accountInfo = (Map<String, String>)
        objectMapper.convertValue(dutch.get("account"), Map.class);
        Account account = new Account();

        for (String id : accountInfo.keySet()) {
            if (id == "bankName") {
                account.setBankName(accountInfo.get(id));
            }

            if (id == "number") {
                account.setNumber(accountInfo.get(id));
            }

            if (id == "memberId") {
                account.setMemberId(Long.parseLong(accountInfo.get(id)));
            }
            if (id == "stuffId") {
                account.setStuffId((Long.parseLong(accountInfo.get(id))));
            }
        }

        System.out.printf(account.toString());

        int insertAccountCount = repository.insertAccount(account.getBankName(),
        account.getNumber(), account.getMemberId(), account.getStuffId());
        System.out.println("insertAccountCount:" + insertAccountCount);
    }

    @Override
    public List<DutchView> getViewAllBymemberId(Long memberId) {
        return repository.findViewAllBymemberId(memberId, "date", "desc");
    }

    @Override
    public int removeDutch(Long stuffId) {
        return repository.deleteDutch(stuffId);
    }

    @Override
	public Long getListCount(Long memberId, int page, int month) {
        Long countList = repository.findCountList(month, memberId) - (page * pageSize);
		Long result = countList <= 0 ? 0 : countList;
		return result;
	}

    @Override
    public DutchView getViewBystuffIdmemberId(Long stuffId, Long memberId) {
        return repository.findViewBystuffIdmemberId(stuffId, memberId, "date", "desc");
    }

}
