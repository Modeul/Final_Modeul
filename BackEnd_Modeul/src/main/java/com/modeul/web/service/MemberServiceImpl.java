package com.modeul.web.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
			System.out.println("login");
			System.out.println(member.getPwd());
			System.out.println(loginMember.getPwd());
			return "비밀번호가 일치하지 않습니다.";

		}
		return null;
	}
	
	@Override
	public Boolean isValid(Member member) {
		Member loginMember = repository.getPwdByUid(member.getUid());
		if (loginMember == null) {
			return false;
		}
		if (!passwordEncoder.matches(member.getPwd(), loginMember.getPwd())) {
			System.out.println("isValid");
			System.out.println(member.getPwd());
			System.out.println(loginMember.getPwd());
			return false;
		}
		return true;
	}

	@Override
	public Member getMemberByUid(String uid) {
		return repository.getPwdByUid(uid);
	}

	@Override
	public Boolean checkPwd(Member loginMember) {
		
		Member member = repository.getPwdByUid(loginMember.getUid());
		return passwordEncoder.matches(loginMember.getPwd(), member.getPwd()); 
	}
	
	@Override
	public Boolean checkUid(String uid) {
		String getUid = repository.getbyUid(uid);
		Boolean result = (getUid != null) ? false : true;

		return result;
	}

	@Override
	public Boolean checkName(String name) {
		String getName = repository.getByName(name);
		Boolean result = (getName != null) ? true : false;

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

	@Override
	public Boolean checkEmailByName(Member member) {
		String UserName = repository.getNameByEmail(member);
		Boolean result = UserName.equals(member.getName());

		return result;
	}

	@Override
	public String findUid(String name,String email) {
		String uid = repository.getUid(name,email);
		
		return uid;
	}

	@Override
	public List<Member> getMemberList() {

		return repository.findAll();
	}

	@Override
	public Member getMemberByEmail(String email) {

		return repository.getMemberByEmail(email);
	}

	@Override
	public int addGoogleMember(Member member) {
		
		//랜덤 패스워드 생성
		String ranPwd = createPwd(); 
		String encodedPassword = passwordEncoder.encode(ranPwd);
		// 랜덤 아이디 생성
		String ranUid = "";
		boolean isdup = false;
		do {
			ranUid = "G-" + createUid();
			isdup = (repository.getbyUid(ranUid) != null ? true : false);
		} while (isdup);
		
		// 멤버 객체에 패스워드 넣어주기
		member.setPwd(encodedPassword);
		//멤버 객체에 아이디 넣어주기
		member.setUid(ranUid);
		return repository.insert(member);
	}

	@Override
	public String createUid() {
		StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 : 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤, rnd 값에 따라서 아래 switch 문이 실행됨

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    // a~z (ex. 1+97=98 => (char)98 = 'b')
                    break;

                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    // A~Z
                    break;

                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;

            }

        }
        return key.toString();
	}

	@Override
	public String createPwd() {
		StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 : 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤, rnd 값에 따라서 아래 switch 문이 실행됨

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    // a~z (ex. 1+97=98 => (char)98 = 'b')
                    break;

                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    // A~Z
                    break;

                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;

            }

        }
        return key.toString();
	}


}
