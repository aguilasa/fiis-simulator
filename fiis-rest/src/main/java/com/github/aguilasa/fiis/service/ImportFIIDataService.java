package com.github.aguilasa.fiis.service;

import com.github.aguilasa.fiis.model.FII;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImportFIIDataService {

  private final FiisDataService fiisDataService;
  private final FIIService fiiService;

  public List<FII> importAndSaveFiiData() {
    List<FII> fiiData = fiisDataService.getFiiData();

    return fiiService.saveNewFIIs(fiiData);
  }
}
