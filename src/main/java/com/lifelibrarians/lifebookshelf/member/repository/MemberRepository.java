package com.lifelibrarians.lifebookshelf.member.repository;

import com.lifelibrarians.lifebookshelf.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);

	@Query("SELECT m FROM Member m LEFT OUTER JOIN FETCH m.memberMemberMetadata WHERE m.id = :memberId")
	Optional<Member> findByIdWithMetadata(Long memberId);

	@Query("SELECT m FROM Member m LEFT OUTER JOIN FETCH m.memberAutobiographies WHERE m.id = :memberId")
	Optional<Member> findMemberWithAutobiographiesByMemberId(Long memberId);
}
