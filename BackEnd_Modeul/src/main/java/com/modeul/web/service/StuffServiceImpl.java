package com.modeul.web.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.modeul.web.entity.Image;
import com.modeul.web.entity.Participation;
import com.modeul.web.entity.Stuff;
import com.modeul.web.entity.StuffView;
import com.modeul.web.repository.ParticipationRepository;
import com.modeul.web.repository.StuffRepository;


@Service
public class StuffServiceImpl implements StuffService {
	
	@Autowired
	private StuffRepository repository;

	@Autowired
	private ParticipationRepository participationRepository;
	
	// 공구상품 목록 조회용
	@Override
	public List<StuffView> getViewAll() {
		
		return repository.findViewAll("", null, null, null, 10, 0);
	}

	@Override
	public List<StuffView> getViewAll(int page) {
		int size = 10;
		int offset = (page-1)*10;
		
		return repository.findViewAll("", null, null, null, size, offset);
	}

	@Override
	public List<StuffView> getViewAll(String query, int page) {
		int size = 10;
		int offset = (page-1)*10;
		
		return repository.findViewAll(query, null, null, null, size, offset);
	}

	@Override
	public List<StuffView> getViewAll(Long categoryId, int page) {
		int size = 10;
		int offset = (page-1)*10;
		
		return repository.findViewAll(null, categoryId, null, null, size, offset);
	}

	@Override
	public List<StuffView> getViewAll(String query, Long categoryId,int page) {
		int size = 10;
		int offset = (page-1)*10;
		
		return repository.findViewAll(query, categoryId, null, null, size, offset);
	}
	
	// 공구상품 글 등록용, 트랜잭션 처리!
	@Transactional
	@Override
	public void regStuff(Stuff stuff, List<MultipartFile> imgs) {

		int insertCount = repository.insert(stuff);
		
		// 이미지 유효성 검사
		if(imgs.get(0).getOriginalFilename().equals("")) {
			return;
		}
		
		// 파일 경로 알아 내기(논리적, 물리적)** : urlPath, realPath
		String currentDir = System.getProperty("user.dir");
		  
		String realPath = "../FrontEnd_Modeul/images/member/stuff";
		
		// 물리 경로에 폴더가 없으면, 폴더도 생성
		File savePath = new File(currentDir, realPath);
		
		if (!savePath.exists())
			savePath.mkdirs();

		// uuid 추가
		String uuid = UUID.randomUUID().toString();
		
		imgs.forEach(img -> {
			System.out.println(img);
			
			String uploadFileName = img.getOriginalFilename();
			  
			uploadFileName = uuid + "_" + uploadFileName;
			
			Image image = Image.builder()
				.name(uploadFileName)
				.stuffId(stuff.getId())
				.build();
			
			repository.uploadImage(image);
			
			// 그 물리적 경로로 파일 저장하는 방법**
			try {
				img.transferTo(new File(savePath, uploadFileName));
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});

		Participation participation = new Participation();
		participation.setStuffId(stuff.getId());
		participation.setMemberId(stuff.getMemberId());	// 아직은 null 값이다..

		// 글 삭제 시, 참여 채팅방도 같이 사라져야 한다???, 일단 편의를 위해 이렇게 동작하도록 함.
		
		int participationCount = participationRepository.insert(participation);
		System.out.printf("participationCount: %d\n", participationCount);
	}

	@Override
	public Stuff getById(Long id) {
		
		return repository.findbyId(id);
	}

	@Override
	public StuffView getViewById(Long id) {
		
		return repository.findViewbyId(id);
	}

	@Override
	public List<StuffView> getRecentViewList(Long categoryId, int page) {
		
		int size = page * 7;
		return repository.findViewAll(null, categoryId, "reg_date", "desc", size, 0);
	}

	@Override
	public Long getListCount(Long categoryId, int page) {
		
		Long countList = repository.getCountList(categoryId) - (page * 7);
		Long result = countList <= 0 ? 0 : countList;
		return result;
	}

	@Override
	public List<StuffView> getRecentViewList(String query, Long categoryId, int page) {
			int size = page * 7;
		return repository.findViewAll(query, categoryId, "reg_date", "desc", size, 0);
	}


	/* 공구상품 정보 수정 */
	@Transactional
	@Override
	public int editStuff(Stuff stuff) {

		int updateCount = repository.update(stuff);
		
		if(updateCount == 1 && stuff.getImageList() != null && stuff.getImageList().size() > 0) {
			repository.deleteImage(stuff.getId());
			
			stuff.getImageList().forEach(image -> {
				image.setStuffId(stuff.getId());
				repository.uploadImage(image);
			});
		}
		return updateCount;
	}

	/* 공구상품 정보 삭제 */
	@Transactional
	@Override
   	public void deleteStuff(Long id) {
		
		List<Image> images = repository.findImagebyId(id);
		
		repository.delete(id);
		
		if(images.isEmpty())
			return;
		
		String currentDir = System.getProperty("user.dir");
		  
		String realPath = "../FrontEnd_Modeul/images/member/stuff";

		File savePath = new File(currentDir, realPath);
		
		images.forEach(image ->{
			File file = new File(savePath, image.getName());
			file.delete();
		});
   	}

}
