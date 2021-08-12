package com.yushin.domain.member;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserTest {
    @Autowired
    private MemberRepository memberRepository;

//    @AfterEach
//    public void cleanup(){
//        memberRepository.deleteAll();
//    }

    @Test
    @Commit
    public void 멤버저장() throws Exception{

        //when
        Member test1 = memberRepository.save(Member.builder()
                .nickname("test1")
                .email("kkad45@naver.com")
                .password("1234")
                .phone("010-4710-5883")
                .birthDay("951118")
                .authority(Authority.ROLE_USER)
                .build());
        System.out.println("tseet1 == "+ test1);
        //then
        List<Member> all = memberRepository.findAll();
        assertThat(all.get(0).getEmail()).isEqualTo(test1.getEmail());

    }

    @Test
    @Commit
    public void 멤버저장실패() throws Exception{

        //when
        Member test1 = memberRepository.save(Member.builder()
                .nickname("test1")
                .email("kkad45@naver.com")
                .password("1234")
                .phone("010-4710-5883")
                .build());
        System.out.println("tseet1 == "+ test1);
        //then
        List<Member> all = memberRepository.findAll();
        assertThat(all.get(0).getEmail()).isEqualTo(test1.getEmail());
        
        //  Caused by: java.sql.SQLException at UserTest.java:62  DTO 만들어서 제어하기
    }

}