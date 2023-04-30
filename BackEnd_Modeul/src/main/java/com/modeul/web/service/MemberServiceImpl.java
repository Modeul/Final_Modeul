package com.modeul.web.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.modeul.web.entity.Image;
import com.modeul.web.entity.Member;
import com.modeul.web.entity.MemberImage;
import com.modeul.web.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public int changePwdByUid(Member member) {
		String encodedPassword = passwordEncoder.encode(member.getPwd());
		member.setPwd(encodedPassword);

		return repository.updatePwd(member);
	}

	@Override
	public int addMember(Member member) {
		String encodedPassword = passwordEncoder.encode(member.getPwd());
		member.setPwd(encodedPassword);

		return repository.insert(member);
	}

	@Override
	public String login(Member member) {
		Member loginMember = repository.getPwdByUid(member.getUid());
		if (loginMember == null) {
			return "해당 아이디가 없습니다.";
		}
		if (!passwordEncoder.matches(member.getPwd(), loginMember.getPwd())) {
			return "비밀번호가 일치하지 않습니다.";
		}
		return null;
	}

	@Override
	public String checkPwd(Member loginMember) {
		Member member = repository.getPwdByUid(loginMember.getUid());
		if (passwordEncoder.matches(loginMember.getPwd(), member.getPwd()) == false) {
			return "비밀번호가 일치하지 않습니다.";
		} else
			return "ok";
	}
	
	@Override
	public Boolean checkUid(String uid) {
		String getUid = repository.getbyUid(uid);
		Boolean result = (getUid != null) ? false : true;

		return result;
	}

	@Override
	public Boolean checkEmail(String email) {
		String getEmail = repository.getByEmail(email);
		Boolean result = (getEmail != null) ? false : true;

		return result;
	}

	@Override
	public Boolean checkNickname(String nickname) {
		String getNickname = repository.getByNickname(nickname);
		Boolean result = (getNickname != null) ? false : true;

		return result;
	}

	@Override
	public int updateMember(Member member) {
		if (member.getPwd() != null) {
			String encodedPassword = passwordEncoder.encode(member.getPwd());
			member.setPwd(encodedPassword);
		}

		return repository.update(member);
	}

	@Override
	public Member getMember(int id) {
		return repository.getbyId(id);
	}

	@Override
	public int deleteMember(Member member) {
		return repository.delete(member);
	}

	@Transactional
	@Override
	public void updateImg(long id, List<MultipartFile> imgs) {

		if (imgs.get(0).getOriginalFilename().equals("")) {
			return;
		}
		String currentDir = System.getProperty("user.dir");

		String realPath = "../FrontEnd_Modeul/images/member";

		File savePath = new File(currentDir, realPath);

		if (!savePath.exists())
			savePath.mkdirs();

		String uuid = UUID.randomUUID().toString();

		imgs.forEach(img -> {
			String uploadFileName = img.getOriginalFilename();

			uploadFileName = uuid + "_" + uploadFileName;

			MemberImage image = MemberImage.builder()
					.name(uploadFileName)
					.id(id)
					.build();

			repository.updateImg(image);

			try {
				img.transferTo(new File(savePath, uploadFileName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public Boolean checkEmailByUid(Member member) {
		String UserEmail = repository.getEmailByUid(member);
		Boolean result = UserEmail.equals(member.getEmail());

		return result;
	}


}
