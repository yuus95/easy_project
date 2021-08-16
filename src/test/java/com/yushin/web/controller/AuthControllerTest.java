package com.yushin.web.controller;

import com.yushin.domain.member.Member;
import com.yushin.domain.member.MemberRepository;
import com.yushin.web.dto.member.MemberRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


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

        MemberRequestDto member =new MemberRequestDto("hello","kkad45@naver.com","1234","01047105883","1995-11-18");

        String url = "http://localhost:" + port +"/auth/login";

        //when
        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url,member,Long.class);




        //then

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("kkad45@naver.com");

        Optional<Member> byEmail = memberRepository.findByEmail(member.getEmail());
        assertThat(byEmail.get().getId()).isEqualTo(1L);
    }
}