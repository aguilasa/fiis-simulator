package com.github.aguilasa.fiis.controller;

import com.github.aguilasa.fiis.model.FII;
import com.github.aguilasa.fiis.service.FiisDataService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fiis")
@RequiredArgsConstructor
public class FiisController {

  private final FiisDataService fiisDataService;

  @GetMapping()
  public ResponseEntity<List<FII>> getAllFiiData() {
    return new ResponseEntity<>(fiisDataService.getFiiData(), HttpStatus.OK);
  }
}
