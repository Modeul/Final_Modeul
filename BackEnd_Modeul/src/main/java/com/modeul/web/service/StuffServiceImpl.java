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

	static int pageSize = 8;

	// 공구상품 목록 조회용
	@Override
	public List<StuffView> getViewAll() {
		
		return repository.findViewAll("", null, "reg_date", "desc", null, null, null,null);
	}

	@Override
	public List<StuffView> getViewAll(int page) {
		int size = 10;
		int offset = (page-1)*10;
		
		return repository.findViewAll("", null, null, null, size, offset, null,null);
	}

	@Override
	public List<StuffView> getViewAll(String query, int page) {
		int size = 10;
		int offset = (page-1)*10;
		
		return repository.findViewAll(query, null, null, null, size, offset, null,null);
	}

	@Override
	public List<StuffView> getViewAll(Long categoryId, int page) {
		int size = 10;
		int offset = (page-1)*10;
		
		return repository.findViewAll(null, categoryId, null, null, size, offset, null,null);
	}

	@Override
	public List<StuffView> getViewAll(String query, Long categoryId, int page) {
		int size = 10;
		int offset = (page-1)*10;
		
		return repository.findViewAll(query, categoryId, null, null, size, offset, null ,null);
	}

	@Override
	public List<StuffView> getViewAll(String query, Long categoryId, int page, Long memberId) {
		int size = 10;
		int offset = (page-1)*10;
		
		return repository.findViewAll(query, categoryId, null, null, size, offset, memberId ,null);
	}

	// 공구상품 글 등록용, 트랜잭션 처리!
	@Transactional
	@Override
	public void regStuff(Stuff stuff, List<MultipartFile> imgs) {

		int insertCount = repository.insert(stuff);

		// 이미지 유효성 검사
		if (imgs.get(0).getOriginalFilename().equals("")) {
			Participation participation = new Participation();
			participation.setStuffId(stuff.getId());
			participation.setMemberId(stuff.getMemberId()); // 아직은 null 값이다..
	
			int participationCount = participationRepository.insert(participation);
			System.out.printf("participationCount: %d\n", participationCount);
			return;
		}

		// 파일 경로 알아 내기(논리적, 물리적)** : urlPath, realPath
		String currentDir = System.getProperty("user.dir");

		String realPath = "../FrontEnd_Modeul/public/images/member/stuff";

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
		participation.setMemberId(stuff.getMemberId()); 

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

		int size = page * pageSize;
		return repository.findViewAll(null, categoryId, "reg_date", "desc", size, 0, null, null);
	}

	@Override
	public List<StuffView> getRecentViewList(Long categoryId, int page, Long memberId, String dongCode) {

		int size = page * pageSize;
		if (dongCode == "") {
			dongCode = null;
		}
		return repository.findViewAll(null, categoryId, "reg_date", "desc", size, 0, memberId, dongCode);
	}
	

	@Override
	public Long getListCount(Long categoryId, int page) {

		Long countList = repository.getCountList(categoryId, null) - (page * pageSize);
		Long result = countList <= 0 ? 0 : countList;
		return result;
	}

	@Override
	public Long getListCount(Long categoryId, int page, Long memberId) {

		Long countList = repository.getCountList(categoryId, memberId) - (page * pageSize);
		Long result = countList <= 0 ? 0 : countList;
		return result;
	}

	public Long getListCount(Long categoryId, int page, Long memberId, String query) {

		Long countList = repository.getCountList(categoryId, memberId, query) - (page * pageSize);
		Long result = countList <= 0 ? 0 : countList;
		return result;
	}


	@Override
	public List<StuffView> getRecentViewList(String query, Long categoryId, int page) {
		int size = page * pageSize;
		return repository.findViewAll(query, categoryId, "reg_date", "desc", size, 0, null, null);
	}
	
	@Override
	public List<StuffView> getRecentViewList(String query, Long categoryId, int page, Long memberId) {
		int size = page * pageSize;
		return repository.findViewAll(query, categoryId, "reg_date", "desc", size, 0, memberId, null);
	}

	@Override
	public List<StuffView> getRecentViewList(String query, Long categoryId, int page, String dongCode) {
		int size = page * pageSize;
		if (dongCode == "") {
			dongCode = null;
		}
		return repository.findViewAll(query, categoryId, "reg_date", "desc", size, 0, null, dongCode);
	}

	@Override
	public List<StuffView> getRecentViewList(String query, Long categoryId, int page, Long memberId, String dongCode) {
		int size = page * pageSize;
		if (dongCode == "") {
			dongCode = null;
		}
		return repository.findViewAll(query, categoryId, "reg_date", "desc", size, 0, memberId, dongCode);
	}


	/* 공구상품 정보 수정 */
	@Transactional
	@Override
	public int editStuff(Stuff stuff, List<MultipartFile> imgs) {

		System.out.println(stuff);
		System.out.println(imgs.get(0).getOriginalFilename());
		int updateCount = repository.update(stuff);

		// 이미지 유효성 검사
		if (imgs.get(0).getOriginalFilename().equals("")) {
			return 0;
		}

		// 경로 설정
		String currentDir = System.getProperty("user.dir");
		String realPath = "../FrontEnd_Modeul/public/images/member/stuff";
		File savePath = new File(currentDir, realPath);

		// 이미지 정보 얻어오기
		List<Image> images = repository.findImagebyId(stuff.getId());

		// 이미지 삭제
		if (!images.isEmpty()) {
			repository.deleteImage(stuff.getId());

			images.forEach(image -> {
				File file = new File(savePath, image.getName());
				file.delete();
			});
		}

		// uuid 추가
		String uuid = UUID.randomUUID().toString();

		// 이미지 저장
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

		// if(updateCount == 1 && stuff.getImageList() != null &&
		// stuff.getImageList().size() > 0) {
		// repository.deleteImage(stuff.getId());

		// stuff.getImageList().forEach(image -> {
		// image.setStuffId(stuff.getId());
		// repository.uploadImage(image);
		// });
		// }
		return updateCount;
	}

	/* 공구상품 정보 삭제 */
	@Transactional
	@Override
	public void deleteStuff(Long id) {

		List<Image> images = repository.findImagebyId(id);

		repository.delete(id);

		if (images.isEmpty())
			return;

		String currentDir = System.getProperty("user.dir");

		String realPath = "../FrontEnd_Modeul/public/images/member/stuff";

		File savePath = new File(currentDir, realPath);

		images.forEach(image -> {
			File file = new File(savePath, image.getName());
			file.delete();
		});
	}

	@Transactional
	@Override
	public void regCrawlingStuff(Stuff stuff) {


		int insertCount = repository.insert(stuff);
		

		if (stuff.getImgurl() == "" || stuff.getImgurl() == null){
			
			Participation participation = new Participation();
			participation.setStuffId(stuff.getId());
			participation.setMemberId(stuff.getMemberId());
	
			int participationCount = participationRepository.insert(participation);
			System.out.printf("participationCount: %d\n", participationCount);

			return;
		}

		repository.uploadImgurl(stuff.getImgurl(),stuff.getId());
		
		Participation participation = new Participation();
		participation.setStuffId(stuff.getId());
		participation.setMemberId(stuff.getMemberId());


		int participationCount = participationRepository.insert(participation);
		System.out.printf("participationCount: %d\n", participationCount);


	}



}
