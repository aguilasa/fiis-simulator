package com.github.aguilasa.fiis.repository;

import com.github.aguilasa.fiis.model.Tutorial;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TutorialRepository extends MongoRepository<Tutorial, String> {

    List<Tutorial> findByTitleContaining(String title);

    List<Tutorial> findByPublished(boolean published);

}