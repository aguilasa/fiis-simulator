package com.github.aguilasa.fiis.service;

import com.github.aguilasa.fiis.model.Tutorial;
import java.util.List;
import java.util.Optional;

public interface TutorialService {
  List<Tutorial> getAllTutorials(String title);

  Optional<Tutorial> getTutorialById(String id);

  Tutorial createTutorial(Tutorial tutorial);

  Optional<Tutorial> updateTutorial(String id, Tutorial tutorial);

  void deleteTutorial(String id);

  void deleteAllTutorials();

  List<Tutorial> findByPublished();
}
