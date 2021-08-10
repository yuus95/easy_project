package com.yushin.web.controller;

import com.yushin.domain.user.Authority;
import com.yushin.domain.user.Member;
import com.yushin.domain.user.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;



@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MemberRepository memberRepository;


    @Test
    public void 회원가입() throws Exception{
        //given
        Member testMember = memberRepository.save(Member.builder()
                .nickname("유신")
                .email("kkad45@naver.com")
                .password("1234")
                .phone("010-4710-5883")
                .authority(Authority.ROLE_USER)
                .build());

        String url = "http://localhost:" + port +"/auth/login";

        //when
        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url,testMember,Long.class);

        System.out.println("test");


        //then
    }



}