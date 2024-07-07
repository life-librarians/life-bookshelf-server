package com.lifelibrarians.lifebookshelf.member.repository;

import com.lifelibrarians.lifebookshelf.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {

}
