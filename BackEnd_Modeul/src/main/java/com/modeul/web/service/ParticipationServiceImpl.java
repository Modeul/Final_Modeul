package com.modeul.web.service;

import java.security.Key;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.modeul.web.entity.Message;
import com.modeul.web.entity.Participation;
import com.modeul.web.entity.ParticipationMemberView;
import com.modeul.web.entity.ParticipationView;
import com.modeul.web.repository.ParticipationRepository;

@Service
public class ParticipationServiceImpl implements ParticipationService {

    @Autowired
    ParticipationRepository repository;

    static int pageSize = 8;

    @Override
    public int addParticipation(Participation participation) {
        return repository.insert(participation);
    }

    @Override
    public List<ParticipationView> getByMemberId(Long memberId, String orderField, String orderDir, int page) {

        int size = page * 8;

        return repository.findByMemberId(memberId, orderField, orderDir, size, 0);
    }

    @Override
    public List<ParticipationMemberView> getMemberBystuffId(Long stuffId) {
        return repository.findMemberBystuffId(stuffId);
    }

    @Override
    public int cancelParticipation(Long stuffId, Long memberId) {
        return repository.delete(stuffId, memberId);
    }

    @Override
    public int getMemberCountBystuffId(Long stuffId) {
        Integer memberCount = repository.findMemberCountBystuffId(stuffId);
        int result = memberCount == null ? 0 : memberCount;

        return result;
    }

    @Override
    public int getStuffCountBymemberId(Long memberId) {

        Integer stuffCount = repository.findStuffCountBymemberId(memberId);
        int result = stuffCount == null ? 0 : stuffCount;

        return result;
    }

    @Override
    public ParticipationMemberView getMemberBystuffIdmemberId(Long stuffId, Long memberId) {
        return repository.findMemberBystuffIdmemberId(stuffId, memberId);
    }

    @Override
    public void saveCalResultMsg(Message message) {

        repository.putCalResultMsg(message);
    }

    @Override
    @Transactional
    public void calculatedAmount(Long stuffId, Map<Long, Integer> prices) {

        // System.out.println(prices.keySet());

        for (Long key : prices.keySet()) {
            Integer value = prices.get(key);
            repository.insertCalculatedAmount(stuffId, key, value);
        }

        

        // ((Object) prices).keySet().stream().forEach(System.out::println);

        
    }

    @Override
    public void inputAccount(Long stuffId, String account) {
        
        repository.insertAccount(stuffId, account);        
    }

    @Override
    public String getAccount(Long leaderId) {
        
        return repository.getAccount(leaderId);
    }

    @Override
    public Long getCountList(Long memberId, int page) {
        Long countList = repository.findCountList(memberId) - (page * pageSize);
		Long result = countList <= 0 ? 0 : countList;
		return result;
    }

}
