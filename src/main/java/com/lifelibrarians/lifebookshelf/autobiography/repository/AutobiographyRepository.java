package com.lifelibrarians.lifebookshelf.autobiography.repository;

import com.lifelibrarians.lifebookshelf.autobiography.domain.Autobiography;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AutobiographyRepository extends JpaRepository<Autobiography, Long> {

	@Query("SELECT a FROM Autobiography a JOIN FETCH a.chapter WHERE a.member.id = :memberId")
	List<Autobiography> findByMemberId(Long memberId);

	@Query("SELECT a FROM Autobiography a JOIN FETCH a.chapter WHERE a.chapter.id = :chapterId")
	Optional<Autobiography> findByChapterId(Long chapterId);

	@Query("SELECT a FROM Autobiography a JOIN FETCH a.autobiographyInterviews WHERE a.id = :autobiographyId")
	Optional<Autobiography> findWithInterviewById(Long autobiographyId);

	@Query("SELECT a FROM Autobiography a JOIN FETCH a.autobiographyInterviews WHERE a.member.id = :memberId")
	List<Autobiography> findWithInterviewByMemberId(Long memberId);
}
