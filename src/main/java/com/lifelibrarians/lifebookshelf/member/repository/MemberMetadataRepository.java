package com.lifelibrarians.lifebookshelf.member.repository;

import com.lifelibrarians.lifebookshelf.member.domain.MemberMetadata;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberMetadataRepository extends JpaRepository<MemberMetadata, Long> {

}
