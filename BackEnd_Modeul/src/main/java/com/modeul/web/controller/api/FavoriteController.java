package com.modeul.web.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.modeul.web.entity.Category;
import com.modeul.web.entity.Favorite;
import com.modeul.web.entity.FavoriteView;
import com.modeul.web.service.CategoryService;
import com.modeul.web.service.FavoriteService;

@RestController("apiFavoriteController")
@RequestMapping("/api")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @Autowired
	private CategoryService categoryService;

    @GetMapping("/favorites")
	public Map<String, Object> getList(Long memberId,
    @RequestParam(name="p", defaultValue = "1") int page,
    @RequestParam(name="c", required=false) Long categoryId
    ) {

		List<FavoriteView> list = favoriteService.getFavoriteByMemberId(memberId,categoryId, page);
		List<Category> categoryList = categoryService.getList();
        Long listCount = favoriteService.getListCount(categoryId, page, memberId);
        
		Map<String, Object> dataList = new HashMap<>();
		dataList.put("list", list);
		dataList.put("listCount", listCount);
		dataList.put("categoryList", categoryList);

		return dataList;
	}

    @GetMapping("/favorite/{stuffId}/{memberId}")
    public Map<String, Object> getList(
        @PathVariable("stuffId") Long stuffId,
        @PathVariable("memberId") Long memberId){

        Favorite favoriteInfo = favoriteService.getListBystuffIdmemberId(stuffId,memberId);

        Map<String, Object> data = new HashMap<>();
        data.put("favoriteInfo", favoriteInfo);
        System.out.println(favoriteInfo);
            return data;

    }

    
    //찜등록
    @PostMapping("/favorite")
    public void addFavorite(@RequestBody Map<String, Long> request){
        Long memberId = request.get("memberId");
        Long stuffId = request.get("stuffId");
        favoriteService.addFavorite(memberId,stuffId);
        
      
    }
    
    //찜해제
    @DeleteMapping("/favorite")
    public void removeFavorite(@RequestBody Map<String, Long> request){
        Long memberId = request.get("memberId");
        Long stuffId = request.get("stuffId");
        favoriteService.removeFavorite(memberId,stuffId);
    }
}
