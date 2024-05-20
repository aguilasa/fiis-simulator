package com.github.aguilasa.fiis.repository;

import com.github.aguilasa.fiis.model.FII;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FIIRepository extends MongoRepository<FII, String> {

  Optional<FII> findByTickerIgnoreCase(String title);
}
