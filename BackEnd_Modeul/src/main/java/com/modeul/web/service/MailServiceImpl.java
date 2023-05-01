package com.modeul.web.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;

// MimeMessage, JavaMailSender에 대한 이해 필요
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender emailsender; // Bean 등록해둔 MailConfig를 autowired 해준다.

    private String ePw; // 인증번호
    private String tempPwd; // 임시 비밀번호

    // 메일 내용 작성
    @Override
    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
        System.out.println("보내는 대상 : " + to);
        System.out.println("인증 번호 : " + ePw);

        MimeMessage message = emailsender.createMimeMessage();
        //아래 코드를 Mo_deul로 돌아가 입력해주세요
         // 메일에 인증번호 넣기
        message.addRecipients(RecipientType.TO, to);// 보내는 대상
        message.setSubject("[Mo_deul] 이메일 인증 코드 메일입니다.");// 제목

        String msgg = "";
        msgg += "<div style='line-height:1; font-family:Consolas,Menlo,monospace; margin-top:20px'>";
        msgg += """
            <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;__&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;___&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
            <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/\\&nbsp;\\&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/\\_&nbsp;\\&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
            <div>&nbsp;&nbsp;___&nbsp;___&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;___&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\\_\\&nbsp;\\&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;__&nbsp;&nbsp;&nbsp;&nbsp;__&nbsp;&nbsp;__&nbsp;&nbsp;\\//\\&nbsp;\\&nbsp;&nbsp;&nbsp;&nbsp;</div>
            <div>/\'&nbsp;__`&nbsp;__`\\&nbsp;&nbsp;&nbsp;/&nbsp;__`\\&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/\'_`&nbsp;\\&nbsp;&nbsp;&nbsp;/\'__`\\&nbsp;/\\&nbsp;\\/\\&nbsp;\\&nbsp;&nbsp;&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;&nbsp;&nbsp;</div>
            <div>/\\&nbsp;\\/\\&nbsp;\\/\\&nbsp;\\&nbsp;/\\&nbsp;\\L\\&nbsp;\\&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/\\&nbsp;\\L\\&nbsp;\\&nbsp;/\\&nbsp;&nbsp;__/&nbsp;\\&nbsp;\\&nbsp;\\_\\&nbsp;\\&nbsp;&nbsp;&nbsp;\\_\\&nbsp;\\_&nbsp;</div>
            <div>\\&nbsp;\\_\\&nbsp;\\_\\&nbsp;\\_\\\\&nbsp;\\____/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\\&nbsp;\\___,_\\\\&nbsp;\\____\\&nbsp;\\&nbsp;\\____/&nbsp;&nbsp;&nbsp;/\\____\\</div>
            <div>&nbsp;\\/_/\\/_/\\/_/&nbsp;\\/___/&nbsp;&nbsp;&nbsp;_______&nbsp;&nbsp;\\/__,_&nbsp;/&nbsp;\\/____/&nbsp;&nbsp;\\/___/&nbsp;&nbsp;&nbsp;&nbsp;\\/____/</div>
            <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/\\______\\&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
            <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\\/______/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                """;
        msgg += "</div>";
        msgg += "<div style='margin-top:30px; font-family:Noto Mono;'>";
        msgg += "<h1 style='color:#333'> 안녕하세요, </h1>";
        msgg += "<div style='display:flex; margin-bottom:30px'>";
        msgg += "<h1 style='color:#7299BE'> Mo_deul </h1> <h1 style='color:#333'>&nbsp;입니다!</h1>";
        msgg += "</div>";
        msgg += "<h4 style='font-weight:400; font-size:15px;'>아래 코드를 Mo_deul로 돌아가 입력해주세요 :)<h4>";
        msgg += "<div align='center' style='border:1px solid black; padding:50px; margin:30px 0; font-size:17px;'>";
        msgg += "<h3 style='color:blue;'>이메일 인증 코드입니다.</h3>";
        msgg += ePw + "</strong><div>"; // 메일에 임시 비밀번호 넣기
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        message.setFrom(new InternetAddress("modeulprj@naver.com", "Mo_deul"));// 보내는 사람

        return message;

    }

    // StringBuffer에 대한 이해 필요
    // 랜덤 인증 코드 전송
    @Override
    public String createKey() {
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

    // 메일 발송
    // sendSimpleMessage 의 매개변수로 들어온 to 는 곧 이메일 주소가 되고,
    // MimeMessage 객체 안에 내가 전송할 메일의 내용을 담는다.
    // 그리고 bean 으로 등록해둔 javaMail 객체를 사용해서 이메일 send
    @Override
    public String sendConfirmMessage(String to) throws Exception {

        ePw = createKey(); // 랜덤 인증번호 생성

        MimeMessage message = createMessage(to); // 메일 발송
        try {
            emailsender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }

        return ePw; // 메일로 보냈던 인증 코드를 서버로 변환
    }

    @Override
    public MimeMessage createMessage2(String to) throws MessagingException, UnsupportedEncodingException {
        System.out.println("보내는 대상 : " + to);
        System.out.println("임시 비밀 번호 : " + tempPwd);

        MimeMessage message = emailsender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);// 보내는 대상
        message.setSubject("[Mo_deul] 임시 비밀번호 발급 메일입니다.");// 제목

        String msgg = "";
        msgg += "<div style='line-height:1; font-family:Consolas,Menlo,monospace; margin-top:20px'>";
        msgg += """
            <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;__&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;___&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
            <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/\\&nbsp;\\&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/\\_&nbsp;\\&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
            <div>&nbsp;&nbsp;___&nbsp;___&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;___&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\\_\\&nbsp;\\&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;__&nbsp;&nbsp;&nbsp;&nbsp;__&nbsp;&nbsp;__&nbsp;&nbsp;\\//\\&nbsp;\\&nbsp;&nbsp;&nbsp;&nbsp;</div>
            <div>/\'&nbsp;__`&nbsp;__`\\&nbsp;&nbsp;&nbsp;/&nbsp;__`\\&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/\'_`&nbsp;\\&nbsp;&nbsp;&nbsp;/\'__`\\&nbsp;/\\&nbsp;\\/\\&nbsp;\\&nbsp;&nbsp;&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;&nbsp;&nbsp;</div>
            <div>/\\&nbsp;\\/\\&nbsp;\\/\\&nbsp;\\&nbsp;/\\&nbsp;\\L\\&nbsp;\\&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/\\&nbsp;\\L\\&nbsp;\\&nbsp;/\\&nbsp;&nbsp;__/&nbsp;\\&nbsp;\\&nbsp;\\_\\&nbsp;\\&nbsp;&nbsp;&nbsp;\\_\\&nbsp;\\_&nbsp;</div>
            <div>\\&nbsp;\\_\\&nbsp;\\_\\&nbsp;\\_\\\\&nbsp;\\____/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\\&nbsp;\\___,_\\\\&nbsp;\\____\\&nbsp;\\&nbsp;\\____/&nbsp;&nbsp;&nbsp;/\\____\\</div>
            <div>&nbsp;\\/_/\\/_/\\/_/&nbsp;\\/___/&nbsp;&nbsp;&nbsp;_______&nbsp;&nbsp;\\/__,_&nbsp;/&nbsp;\\/____/&nbsp;&nbsp;\\/___/&nbsp;&nbsp;&nbsp;&nbsp;\\/____/</div>
            <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/\\______\\&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
            <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\\/______/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                """;
        msgg += "</div>";
        msgg += "<div style='margin-top:30px; font-family:Noto Mono;'>";
        msgg += "<h1 style='color:#333'> 안녕하세요, </h1>";
        msgg += "<div style='display:flex; margin-bottom:30px'>";
        msgg += "<h1 style='color:#7299BE'> Mo_deul </h1> <h1 style='color:#333'>&nbsp;입니다!</h1>";
        msgg += "</div>";
        msgg += "<h4 style='font-weight:400; font-size:15px;'>임시 비밀번호를 발급해드립니다. 아래 발급된 비밀번호로 로그인해주세요. 감사합니다 :)<h4>";
        msgg += "<div align='center' style='border:1px solid black; padding:50px; margin:30px 0; font-size:17px;'>";
        msgg += "<h3 style='color:blue;'>임시 비밀번호 입니다.</h3>";
        msgg += tempPwd + "</strong><div>"; // 메일에 임시 비밀번호 넣기
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        message.setFrom(new InternetAddress("modeulprj@naver.com", "Mo_deul"));// 보내는 사람

        return message;

    }

    @Override
    public String createTempPwd() {
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
    public String sendTempPwdMessage(String to) throws Exception {

        tempPwd = createKey(); // 랜덤 비밀번호 생성

        MimeMessage message = createMessage2(to); // 메일 발송
        try {
            emailsender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }

        return tempPwd; // 메일로 보냈던 임시 비밀번호를 서버로 변환
    }
}
