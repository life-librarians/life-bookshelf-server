package com.lifelibrarians.lifebookshelf.publication.repository;

import com.lifelibrarians.lifebookshelf.publication.domain.Publication;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PublicationRepository extends JpaRepository<Publication, Long> {

}
