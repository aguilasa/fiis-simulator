package com.github.aguilasa.fiis.service;

import com.github.aguilasa.fiis.model.FII;
import com.github.aguilasa.fiis.repository.FIIRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FIIService {

  private final FIIRepository fiiRepository;

  public List<FII> saveNewFIIs(List<FII> fiis) {
    List<FII> newFiis =
        fiis.stream()
            .filter(fii -> fiiRepository.findByTickerIgnoreCase(fii.getTicker()).isEmpty())
            .collect(Collectors.toList());

    return fiiRepository.saveAll(newFiis);
  }
}
