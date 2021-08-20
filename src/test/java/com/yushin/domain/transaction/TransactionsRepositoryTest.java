package com.yushin.domain.transaction;

import com.yushin.domain.Address;
import com.yushin.domain.member.Authority;
import com.yushin.domain.member.Member;
import com.yushin.domain.member.MemberRepository;
import com.yushin.web.dto.transaction.BalanceDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Transactional
class TransactionsRepositoryTest {

    @Autowired
    TransactionsRepository transactionsRepository;

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


        Transactions 김유신 = transactionsRepository.save(
                Transactions.builder()
                        .bankAccount("7560010003226")
                        .transactionType(TransactionType.deposit)
                        .money(new BigDecimal("100000"))
                        .counterparty_name("김유신")
                        .counterparty_account("01047105883")
                        .member(member)
                        .build());

        Transactions 김유신1 = transactionsRepository.save(
                Transactions.builder()
                        .bankAccount("7560010003226")
                        .transactionType(TransactionType.deposit) // 입금
                        .money(new BigDecimal("10000"))
                        .counterparty_name("김유신")
                        .counterparty_account("")
                        .member(member)
                        .build());
        Transactions 김유2 = transactionsRepository.save(
                Transactions.builder()
                        .bankAccount("7560010003226")
                        .transactionType(TransactionType.deposit)
                        .money(new BigDecimal("10000"))
                        .counterparty_name("김유신")
                        .counterparty_account("")
                        .member(member)
                        .build());
        Transactions 김유신3 = transactionsRepository.save(
                Transactions.builder()
                        .bankAccount("7560010003226")
                        .transactionType(TransactionType.transfer) // 이체
                        .money(new BigDecimal("6000"))
                        .counterparty_name("박흥")
                        .counterparty_account("010666622222")
                        .member(member)
                        .build());
        Transactions 김유신4 = transactionsRepository.save(
                Transactions.builder()
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
        List<Transactions> byMember = transactionsRepository.findAllByMember(all.get(0));

        //then
        for (Transactions transactions : byMember) {
            System.out.println("transaction == >  " + transactions.getBankAccount());
            System.out.println("transaction == >  " + transactions.getMember());
            System.out.println("transaction == >  " + transactions.getMoney());

        }
    }


    @Test
    public void  findByBankAccount테스트() throws Exception{
        //given

        //when
        List<Transactions> byMember = transactionsRepository.findAllByBankAccount("7560010003226");

        //then
        for (Transactions transactions : byMember) {
            System.out.println("transaction == >  " + transactions.getBankAccount());
            System.out.println("transaction == >  " + transactions.getMember());
            System.out.println("transaction == >  " + transactions.getMoney());
        }
    }


    @Test
    public void  findByTransactionTypeAndMember테스트() throws Exception{
        //given
        List<Member> all = memberRepository.findAll();

        //when
        List<Transactions> byMember = transactionsRepository.findAllByTransactionTypeAndMember(TransactionType.deposit,all.get(0));

        //then
        System.out.println("findByTransactionTypeAndMember 테스트");
        for (Transactions transactions : byMember) {
            System.out.println("transaction == >  " + transactions.getBankAccount());
            System.out.println("transaction == >  " + transactions.getMember());
            System.out.println("transaction == >  " + transactions.getMoney());
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
        List<Transactions> byMember = transactionsRepository.findAllByMemberAndCreateDateBetween(all.get(0),startDate,endDate);

        //then
        System.out.println("findByTransactionTypeAndMember 테스트");
        for (Transactions transactions : byMember) {
            System.out.println("transaction == >  " + transactions.getBankAccount());
            System.out.println("transaction == >  " + transactions.getMember());
            System.out.println("transaction == >  " + transactions.getMoney());
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
        List<Transactions> search = transactionsRepository.search(all.get(0),null ,startDate, endDate);


        //then
        for (Transactions transactions : search) {
            System.out.println("transace.type  " + transactions.getTransactionType());
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
        BigDecimal byDeposit = transactionsRepository.findByDeposit(all.get(0));
        BigDecimal byTransferAndPayment = transactionsRepository.findByTransferAndPayment(all.get(0));

        //then
        System.out.println("balance == ? "   + byDeposit);
        System.out.println("minus_balance == ? "  + byTransferAndPayment);
        System.out.println("balance =!? "  + byDeposit.subtract(byTransferAndPayment).setScale(0));
    }

    @Test
    public void get_balanceTest() throws Exception{
        //given
        List<Member> all = memberRepository.findAll();
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0,0,0)); //어제 00:00:00
        LocalDateTime endDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)); //오늘 24:00:00

//        StringUtils.hasText() "", null 처리
        //when
         Optional<BalanceDto>balanceDto = transactionsRepository.getBalance(all.get(0).getId());

        //then
        if (balanceDto.isEmpty()){
            System.out.println("balance = ? "  + balanceDto);
            System.out.println("데이터 x");
        }
        else {
            System.out.println("balancedto.moeny" + balanceDto.get().getDeposit());
            System.out.println("balancedto.minusmoeny" + balanceDto.get().getMinus_money());
        }
    }

    @Test
    public void findByMemberId() throws Exception{
        //given
        List<Member> all = memberRepository.findAll();
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0,0,0)); //어제 00:00:00
        LocalDateTime endDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)); //오늘 24:00:00

//        StringUtils.hasText() "", null 처리
        //when
        PageRequest pageRequest = PageRequest.of(3, 3, Sort.by("createAt").descending());
        Page<Transactions> pageByMemberId = transactionsRepository.findPageByMemberId(all.get(0).getId(), pageRequest);


        //then
        for (Transactions transactions : pageByMemberId) {
            System.out.println(" transactions == " + transactions.getId());
            System.out.println(" transactions == " + transactions.getMoney());
        }
    }
}