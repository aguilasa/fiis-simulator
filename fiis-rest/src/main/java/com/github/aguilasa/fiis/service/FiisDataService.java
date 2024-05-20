package com.github.aguilasa.fiis.service;

import com.github.aguilasa.fiis.client.FiisSearchClient;
import com.github.aguilasa.fiis.constants.FiiHeaders;
import com.github.aguilasa.fiis.model.FII;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.StringReader;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FiisDataService {

  private final FiisSearchClient client;

  public List<FII> getFiiData() {
    List<FII> filteredRecords = new ArrayList<>();

    try {
      String content = client.getCsvData();

      CSVParserBuilder parserBuilder = new CSVParserBuilder().withSeparator(';');
      CSVReader csvReader =
          new CSVReaderBuilder(new StringReader(content))
              .withCSVParser(parserBuilder.build())
              .build();

      List<String[]> records = csvReader.readAll();
      if (!records.isEmpty()) {
        String[] headers = records.get(0);
        Map<String, Integer> headerMap = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
          headerMap.put(headers[i].trim(), i);
        }

        for (String[] record : records.subList(1, records.size())) {
          FII fiiData =
              FII.builder()
                  .ticker(record[headerMap.get(FiiHeaders.TICKER)])
                  .gestao(record[headerMap.get(FiiHeaders.GESTAO)])
                  .cagrValorCota3Anos(
                      parseToFloat(record[headerMap.get(FiiHeaders.CAGR_VALOR_COTA_3_ANOS)]))
                  .patrimonio(parseToDouble(record[headerMap.get(FiiHeaders.PATRIMONIO)]))
                  .pVp(parseToFloat(record[headerMap.get(FiiHeaders.P_VP)]))
                  .liquidezMediaDiaria(
                      parseToDouble(record[headerMap.get(FiiHeaders.LIQUIDEZ_MEDIA_DIARIA)]))
                  .cagrDividendos3Anos(
                      parseToFloat(record[headerMap.get(FiiHeaders.CAGR_DIVIDENDOS_3_ANOS)]))
                  .numeroCotistas(parseToDouble(record[headerMap.get(FiiHeaders.NUMERO_COTISTAS)]))
                  .preco(parseToFloat(record[headerMap.get(FiiHeaders.PRECO)]))
                  .dy(parseToFloat(record[headerMap.get(FiiHeaders.DY)]))
                  .percentualEmCaixa(
                      parseToFloat(record[headerMap.get(FiiHeaders.PERCENTUAL_EM_CAIXA)]))
                  .numeroCotas(parseToDouble(record[headerMap.get(FiiHeaders.NUMERO_COTAS)]))
                  .valorPatrimonialCota(
                      parseToFloat(record[headerMap.get(FiiHeaders.VALOR_PATRIMONIAL_COTA)]))
                  .ultimoDividendo(parseToFloat(record[headerMap.get(FiiHeaders.ULTIMO_DIVIDENDO)]))
                  .build();
          filteredRecords.add(fiiData);
        }

        return filteredRecords;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return filteredRecords;
  }

  private static float parseToFloat(String value) {
    return value == null || value.trim().isEmpty()
        ? 0
        : Float.parseFloat(value.replace(".", "").replace(",", "."));
  }

  private static double parseToDouble(String value) {
    return value == null || value.trim().isEmpty()
        ? 0
        : Double.parseDouble(value.replace(".", "").replace(",", "."));
  }
}
