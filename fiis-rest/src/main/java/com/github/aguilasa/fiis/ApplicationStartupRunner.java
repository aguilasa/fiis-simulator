package com.github.aguilasa.fiis;

import com.github.aguilasa.fiis.service.ImportFIIDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationStartupRunner {

  private final ImportFIIDataService importFIIDataService;

  @EventListener(ApplicationReadyEvent.class)
  public void onApplicationEvent() {
    importFIIDataService.importAndSaveFiiData();
  }
}
