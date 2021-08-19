package com.yushin.domain.transaction;

import com.yushin.domain.Address;
import com.yushin.domain.QAddress;
import com.yushin.domain.member.Authority;
import com.yushin.domain.member.Member;
import com.yushin.domain.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void 데이터생성(){
        Address address = new Address("경기도","소하1동","1955");
        Member member = memberRepository.save(
                Member.builder()
                        .email("kkad45@naver.com")
                        .password("1234")
                        .phone("01047105883")
                        .nickname("바쟈")
                        .birthDay("19951119")
                        .authority(Authority.ROLE_USER)
                        .build());


        Transaction 김유신 = transactionRepository.save(
                Transaction.builder()
                        .bankAccount("7560010003226")
                        .transactionType(TransactionType.deposit)
                        .money(new BigDecimal("100000"))
                        .counterparty_name("김유신")
                        .counterparty_account("01047105883")
                        .member(member)
                        .build());

        Transaction 김유신1 = transactionRepository.save(
                Transaction.builder()
                        .bankAccount("7560010003226")
                        .transactionType(TransactionType.deposit) // 입금
                        .money(new BigDecimal("10000"))
                        .counterparty_name("김유신")
                        .counterparty_account("")
                        .member(member)
                        .build());
        Transaction 김유2 = transactionRepository.save(
                Transaction.builder()
                        .bankAccount("7560010003226")
                        .transactionType(TransactionType.deposit)
                        .money(new BigDecimal("10000"))
                        .counterparty_name("김유신")
                        .counterparty_account("")
                        .member(member)
                        .build());
        Transaction 김유신3 = transactionRepository.save(
                Transaction.builder()
                        .bankAccount("7560010003226")
                        .transactionType(TransactionType.transfer) // 이체
                        .money(new BigDecimal("6000"))
                        .counterparty_name("박흥")
                        .counterparty_account("010666622222")
                        .member(member)
                        .build());
        Transaction 김유신4 = transactionRepository.save(
                Transaction.builder()
                        .bankAccount("7560010003226")
                        .transactionType(TransactionType.payment) //결제
                        .money(new BigDecimal("30000"))
                        .payment_address(address)
                        .payment_name("고구려짬뽕")
                        .payment_card("0788")
                        .payment_phone("010-4710-5883")
                        .member(member)
                        .build());
    }


    @Test
    public void  findByMember테스트() throws Exception{
        //given
        List<Member> all = memberRepository.findAll();

        //when
        List<Transaction> byMember = transactionRepository.findAllByMember(all.get(0));

        //then
        for (Transaction transaction : byMember) {
            System.out.println("transaction == >  " + transaction.getBankAccount());
            System.out.println("transaction == >  " + transaction.getMember());
            System.out.println("transaction == >  " + transaction.getMoney());

        }
    }


    @Test
    public void  findByBankAccount테스트() throws Exception{
        //given

        //when
        List<Transaction> byMember = transactionRepository.findAllByBankAccount("7560010003226");

        //then
        for (Transaction transaction : byMember) {
            System.out.println("transaction == >  " + transaction.getBankAccount());
            System.out.println("transaction == >  " + transaction.getMember());
            System.out.println("transaction == >  " + transaction.getMoney());
        }
    }


    @Test
    public void  findByTransactionTypeAndMember테스트() throws Exception{
        //given
        List<Member> all = memberRepository.findAll();

        //when
        List<Transaction> byMember = transactionRepository.findAllByTransactionTypeAndMember(TransactionType.deposit,all.get(0));

        //then
        System.out.println("findByTransactionTypeAndMember 테스트");
        for (Transaction transaction : byMember) {
            System.out.println("transaction == >  " + transaction.getBankAccount());
            System.out.println("transaction == >  " + transaction.getMember());
            System.out.println("transaction == >  " + transaction.getMoney());
        }

        Assertions.assertThat(byMember.get(0).getBankAccount()).isEqualTo("7560010003226");
    }


    @Test
    public void  findAllByMemberAndCreateDateBetween테스트() throws Exception{
        //given
        List<Member> all = memberRepository.findAll();
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0,0,0)); //어제 00:00:00
        LocalDateTime endDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)); //오늘 24:00:00


        System.out.println("Start Date " + startDate);
        System.out.println("endDateTime "+ endDate);

        //when
        List<Transaction> byMember = transactionRepository.findAllByMemberAndCreateDateBetween(all.get(0),startDate,endDate);

        //then
        System.out.println("findByTransactionTypeAndMember 테스트");
        for (Transaction transaction : byMember) {
            System.out.println("transaction == >  " + transaction.getBankAccount());
            System.out.println("transaction == >  " + transaction.getMember());
            System.out.println("transaction == >  " + transaction.getMoney());
        }

    }


    @Test
    public void 동적쿼리TEST() throws Exception{
        //given
        List<Member> all = memberRepository.findAll();
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0,0,0)); //어제 00:00:00
        LocalDateTime endDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)); //오늘 24:00:00

//        StringUtils.hasText() "", null 처리
        //when
        List<Transaction> search = transactionRepository.search(all.get(0),null ,startDate, endDate);


        //then
        for (Transaction transaction : search) {
            System.out.println("transace.type  " + transaction.getTransactionType());
        }
    }

    @Test
    public void find_balanceTest() throws Exception{
        //given
        List<Member> all = memberRepository.findAll();
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0,0,0)); //어제 00:00:00
        LocalDateTime endDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)); //오늘 24:00:00

//        StringUtils.hasText() "", null 처리
        //when
        BigDecimal byDeposit = transactionRepository.findByDeposit(all.get(0));
        BigDecimal byTransferAndPayment = transactionRepository.findByTransferAndPayment(all.get(0));

        //then
        System.out.println("balance == ? "   + byDeposit);
        System.out.println("minus_balance == ? "  + byTransferAndPayment);
        System.out.println("balance =!? "  + byDeposit.subtract(byTransferAndPayment).setScale(0));

    }
}