package com.modeul.web.service;

import java.util.List;

import com.modeul.web.entity.Favorite;
import com.modeul.web.entity.FavoriteView;

public interface FavoriteService {


    // List<FavoriteView> getFavoriteByMemberId(Long memberId, int page);
    List<FavoriteView> getFavoriteByMemberId(Long memberId, Long categoryId, int page);

    
    void addFavorite(Long memberId, Long stuffId);
    void removeFavorite(Long memberId, Long stuffId);
    FavoriteView getListByStuffId(Long stuffId);
    Long getListCount(Long categoryId, int page, Long memberId);


    Favorite getListBystuffIdmemberId(Long stuffId, Long memberId);
}
