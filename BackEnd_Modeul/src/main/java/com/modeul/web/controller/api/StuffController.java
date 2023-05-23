package com.modeul.web.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.modeul.web.entity.Category;
import com.modeul.web.entity.Crawling;
import com.modeul.web.entity.Image;
import com.modeul.web.entity.ParticipationMemberView;
import com.modeul.web.entity.Stuff;
import com.modeul.web.entity.StuffView;
import com.modeul.web.service.CategoryService;
import com.modeul.web.service.CrawlingService;
import com.modeul.web.service.FavoriteService;
import com.modeul.web.service.ImageService;
import com.modeul.web.service.ParticipationService;
import com.modeul.web.service.StuffService;

// 데이터만 전송하는 것이 Rest인 것을 까먹음.. 즉, RestController이다.
@RestController("apiMenuController")
@RequestMapping("/api")
public class StuffController {

	@Autowired
	private StuffService service;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private ParticipationService participationService;

	@Autowired
	private CrawlingService crawlingservice;

	@Autowired
	private FavoriteService favoriteService;


	// 반환 타입 주의!
	@GetMapping("/stuffs")
	public Map<String, Object> getList(@RequestParam(name = "q", required = false) String query,
			@RequestParam(name = "p", defaultValue = "1") int page,
			@RequestParam(name = "c", required = false) Long categoryId,
			@RequestParam(name = "id", required = false) Long memberId,
			@RequestParam(name = "dc", required = false) String dongCode
			) {
		System.out.println("동코드"+dongCode);
		// List<StuffView> queryList = service.getRecentViewList(query, categoryId, page, dongCode);
		List<StuffView> list = service.getRecentViewList(query, categoryId, page, memberId, dongCode); 
		List<Category> categoryList = categoryService.getList();
		
		Long listCount = service.getListCount(categoryId, page, memberId, query);

		Map<String, Object> dataList = new HashMap<>();
		// dataList.put("queryList", queryList);
		dataList.put("list", list);
		dataList.put("categoryList", categoryList);
		dataList.put("listCount", listCount);

		return dataList;
	}

	@GetMapping("/stuffAll")
	public List<StuffView> getListAll() {

		return service.getViewAll();
	} 

	@GetMapping("/stuff/categories")
	public List<Category> getCategoryList() {

		List<Category> categoryList = categoryService.getList();

		return categoryList;
	}

	@GetMapping("/stuff/{id}")
	public Map<String, Object> get(@PathVariable("id") long id) {

		Stuff stuff = service.getById(id);
		Category category = categoryService.getById(stuff.getCategoryId());
		List<Image> imageList = imageService.getListById(stuff.getId());

		List<ParticipationMemberView> participantList = participationService.getMemberBystuffId(stuff.getId());
		int memberCount = participationService.getMemberCountBystuffId(stuff.getId());
		StuffView stuffView = service.getViewById(id);

		Map<String, Object> data = new HashMap<>();
		data.put("stuff", stuff);
		data.put("category", category);
		data.put("imageList", imageList);
		data.put("participantList", participantList);
		data.put("memberCount", memberCount);
		data.put("stuffView", stuffView);

		return data;
	}

	// ======================== 추후에 카테고리 Id 추가 ===========================

	// post 요청은 먼저 JS로 와서 JS가 해당 RESTAPI url로
	// 요청 바디에 데이터를 실어서 해당 요청 바디에 대한 서버의 응답 데이터를 요청하고
	// 그 응답 데이터를 받아와서 비교해서 그다음을 동작 시킨다!
	/* 공구상품 글 등록 */
	@PostMapping("/stuff/upload")
	public String regStuff(Stuff stuff, List<MultipartFile> imgs) {
		
		service.regStuff(stuff, imgs);

		return "ok";
	}

	/* 공구상품 정보 수정 */
	@PutMapping("/stuff/update/{id}")
	public String editStuff(Stuff stuff, List<MultipartFile> imgs) {
			
		/* 수정시 이미지 처리 무시 -> 1차 이후 활용 */
		/*
			for (int i = 0; i < imgs.length; i++) {			
				MultipartFile img = imgs[i]; 			// 파일 업로드가 안될 시, 예외 처리		

				if (img.isEmpty())				
					continue; 			// 파일 경로 알아 내기(논리적, 물리적)** : urlPath, realPath

				String urlPath = "/images/member/stuff/" + img.getOriginalFilename();			
				String realPath = request.getServletContext().getRealPath(urlPath); 			
				
				System.out.printf("%s", realPath); 			// 물리 경로에 폴더가 없으면, 폴더도 생성			
				File savePath = new File(realPath); 			
		
				~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~1차까지 주석처리~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

				if (!savePath.exists())				
					savePath.mkdirs(); 			// 그 물리적 경로로 파일 저장하는 방법**			
			
				img.transferTo(new File(realPath));
				
				System.out.printf("%s", realPath); 			// 그 이미지를 DB에 저장하기!!**			
			
				Image image = new Image();			
				image.setName(img.getOriginalFilename()); 			// ArrayList에 add해서 Image 정보 넣기!			
				imageList.add(image);		
			} 		
	 */
	
			int updateCount = service.editStuff(stuff, imgs);
	
			return "ok";

	}

		@DeleteMapping("/stuff/{id}")
			public String deleteStuff(
				@PathVariable("id") Long id){
					service.deleteStuff(id);
			return "stuff del:";      
   		}

// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 크롤링 컨트롤러 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

	//  get ,   insert  , delete
	@GetMapping("/stuff/recommends")
	public Map<String,Object> getCrawlingList(
			@RequestParam(name = "q", required = false) String query,
			@RequestParam(name="p", defaultValue = "1") int page,
			@RequestParam(name="c", required=false) Long categoryId,
			@RequestParam(name="cname", required=false) String categoryName) {
		
		List<Crawling> list = crawlingservice.getViewAll(query , page , categoryId);
		List<Crawling> category = crawlingservice.getCategoryNameList(page , categoryName);
		Long listCount = crawlingservice.getListCount(categoryId, page);
		Map<String, Object> dataList = new HashMap<>();
		dataList.put("list", list);
		dataList.put("listCount", listCount);
		dataList.put("category", category);

		return dataList;
	}
	@GetMapping("/stuff/crawling/{id}")
	public Map<String, Object> getCrwalingReg(@PathVariable("id") long id) {

		
		Crawling crawlingData = crawlingservice.getById(id);

		Map<String, Object> data = new HashMap<>();
		data.put("crawlingData", crawlingData);

		return data;
	}
	@PostMapping("/stuff/recommend")
	public String regCrawlingStuff(Stuff stuff) {
		service.regCrawlingStuff(stuff);

		return "ok";
	}
}
