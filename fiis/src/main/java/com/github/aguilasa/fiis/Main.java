package com.github.aguilasa.fiis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
  public static void main(String[] args) {
    String url =
        "https://statusinvest.com.br/category/AdvancedSearchResultExport?search=%7B%22Segment%22%3A%22%22%2C%22Gestao%22%3A%22%22%2C%22my_range%22%3A%220%3B20%22%2C%22dy%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22p_vp%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22percentualcaixa%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22numerocotistas%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22dividend_cagr%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22cota_cagr%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22liquidezmediadiaria%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22patrimonio%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22valorpatrimonialcota%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22numerocotas%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%2C%22lastdividend%22%3A%7B%22Item1%22%3Anull%2C%22Item2%22%3Anull%7D%7D&CategoryType=2";
    Set<String> codes =
        new HashSet<>(
            Arrays.asList(
                "VISC11", "KNCR11", "RBRR11", "RBRF11", "XPML11", "KNIP11", "HGLG11", "PVBI11",
                "HGBS11", "BTLG11"));

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36")
            .header("Upgrade-Insecure-Requests", "1")
            .header("DNT", "1")
            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .header("Accept-Language", "en-US,en;q=0.5")
            .header("Accept-Encoding", "gzip, deflate")
            .build();

    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() != 200) {
        throw new RuntimeException("HTTP error code: " + response.statusCode());
      }

      String content = response.body();
      CSVParserBuilder parserBuilder = new CSVParserBuilder().withSeparator(';');
      CSVReader csvReader =
          new CSVReaderBuilder(new StringReader(content))
              .withCSVParser(parserBuilder.build())
              .build();
      List<String[]> records = csvReader.readAll();

      if (records.isEmpty()) {
        throw new RuntimeException("No records found in CSV.");
      }

      List<Map<String, String>> filteredRecords = new ArrayList<>();
      String[] headers = records.get(0);
      Map<String, String> headerMap = new HashMap<>();
      headerMap.put("CAGR VALOR CORA 3 ANOS", "cagrValorCota3Anos");
      headerMap.put("PRECO", "preco");
      headerMap.put("VALOR PATRIMONIAL COTA", "valorPatrimonialCota");
      headerMap.put("N COTISTAS", "numeroCotistas");
      headerMap.put("TICKER", "ticker");
      headerMap.put("PATRIMONIO", "patrimonio");
      headerMap.put("DY", "dy");
      headerMap.put("GESTAO", "gestao");
      headerMap.put("LIQUIDEZ MEDIA DIARIA", "liquidezMediaDiaria");
      headerMap.put("CAGR DIVIDENDOS 3 ANOS", "cagrDividendos3Anos");
      headerMap.put("P/VP", "pVp");
      headerMap.put("PERCENTUAL EM CAIXA", "percentualEmCaixa");
      headerMap.put("N COTAS", "numeroCotas");
      headerMap.put("ULTIMO DIVIDENDO", "ultimoDividendo");

      Set<String> floatFields =
          new HashSet<>(
              Arrays.asList(
                  "preco",
                  "dy",
                  "patrimonio",
                  "liquidezMediaDiaria",
                  "numeroCotistas",
                  "percentualEmCaixa",
                  "ultimoDividendo",
                  "valorPatrimonialCota",
                  "numeroCotas",
                  "pVp",
                  "cagrDividendos3Anos",
                  "cagrValorCota3Anos"));

      for (String[] record : records.subList(1, records.size())) {
        if (codes.contains(record[0])) { // assuming the first column is TICKER
          Map<String, String> map = new HashMap<>();
          for (int i = 0; i < headers.length; i++) {
            String value = record[i].trim();
            String jsonKey = headerMap.getOrDefault(headers[i].trim(), headers[i].trim());
            if (floatFields.contains(jsonKey)) {
              value = parseToFloatString(value);
            }
            map.put(jsonKey, value);
          }
          filteredRecords.add(map);
        }
      }

      ObjectMapper mapper = new ObjectMapper();
      String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(filteredRecords);
      System.out.println(json);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static String parseToFloatString(String value) {
    return value.replace(".", "").replace(",", ".");
  }
}

// https://aplicamais.com/calculadoras/fundos-imobiliarios
