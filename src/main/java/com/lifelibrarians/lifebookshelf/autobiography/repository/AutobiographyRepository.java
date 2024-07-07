package com.lifelibrarians.lifebookshelf.autobiography.repository;

import com.lifelibrarians.lifebookshelf.autobiography.domain.Autobiography;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AutobiographyRepository extends JpaRepository<Autobiography, String> {

}
