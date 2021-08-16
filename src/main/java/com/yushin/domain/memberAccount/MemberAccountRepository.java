package com.yushin.domain.memberAccount;

import com.yushin.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface MemberAccountRepository extends JpaRepository<MemberAccount,Long> {

    Optional<MemberAccount> findByMemberId(long memberId);

    @Query("select ma from MemberAccount ma where  ma.member.id = :memberId and ma.bankAccount = :bankAccount")
    Optional<MemberAccount> findByMemberIdandBankAccount(@Param("memberId") long id,@Param("bankAccount") String bankAccount);

    @Query("select ma from MemberAccount ma where ma.member.id = :memberId")
    List<MemberAccount> findAllByMemberId(@Param("memberId") long id);
}
