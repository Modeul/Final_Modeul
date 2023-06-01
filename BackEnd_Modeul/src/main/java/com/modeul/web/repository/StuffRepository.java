package com.modeul.web.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.modeul.web.entity.Image;
import com.modeul.web.entity.Stuff;
import com.modeul.web.entity.StuffView;

@Mapper
public interface StuffRepository {

	List<StuffView> findViewAll(String query,
			Long categoryId,
			String orderField,
			String orderDir,
			Integer size,
			Integer offset,
			Long memberId,
			String dongCode);

	Stuff findbyId(Long id);

	StuffView findViewbyId(Long id);

	int insert(Stuff stuff);

	/* 공구상픔 이미지 등록 */
	int uploadImage(Image image);
	int uploadImgurl(String imgurl , Long stuffId);

	Long getCountList(Long categoryId, Long memberId);
	Long getCountList(Long categoryId, Long memberId, String query);
	
	/* 공구상품 수정 */
	int update(Stuff stuff);

	/* 특정 공구상품의 이미지만 삭제 */
	int deleteImage(Long stuffId);

	/* 특정 공구상품 정보 삭제 */
	void delete(Long id);

	/* 이미지 정보 조회 */
	List<Image> findImagebyId(Long id);

}
