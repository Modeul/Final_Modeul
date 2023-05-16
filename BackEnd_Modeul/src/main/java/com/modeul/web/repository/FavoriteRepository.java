package com.modeul.web.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.modeul.web.entity.Favorite;
import com.modeul.web.entity.FavoriteView;

@Mapper
public interface FavoriteRepository {

    List<FavoriteView> findViewById(Long memberId, 
                                    Long categoryId, 
                                    String orderField, 
                                    String orderDir,
                                    Integer size, 
                                    Integer offset);

        //List<FavoriteView> findViewById(Long memberId);

                                


    void addList(Long memberId, Long stuffId);

    void deleteList(Long memberId, Long stuffId);

   

    FavoriteView findViewByStuffId(Long stuffId);

    Long getCountList(Long categoryId, Long memberId);

    Favorite findListBystuffIdmemberId(Long stuffId, Long memberId);

}