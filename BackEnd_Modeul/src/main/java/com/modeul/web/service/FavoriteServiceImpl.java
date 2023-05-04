package com.modeul.web.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeul.web.entity.FavoriteView;
import com.modeul.web.repository.FavoriteRepository;
@Service
public class FavoriteServiceImpl implements FavoriteService {


    @Autowired
    private FavoriteRepository favoriteRepository;


    

    // @Override
	// public List<FavoriteView> getFavoriteByMemberId(Long memberId, int page) {
		
	// 	int size = page * 7;
	// 	return favoriteRepository.findViewById( memberId , size);
	// }

         

    @Override
	public List<FavoriteView> getFavoriteByMemberId(Long memberId,Long categoryId, int page) {
		int size = page * 7;

		return favoriteRepository.findViewById( memberId,categoryId, "participation_date", "desc", size, 0);
	}
  


    @Override
    public void addFavorite(Long memberId, Long stuffId) {
        LocalDateTime date = LocalDateTime.now();
        favoriteRepository.addList(memberId, stuffId, date);
    }

    @Override
    public void removeFavorite(Long memberId, Long stuffId) {
        favoriteRepository.deleteList(memberId, stuffId);
    }

    


    
}
