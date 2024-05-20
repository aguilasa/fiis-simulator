package com.github.aguilasa.fiis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "fiis")
public class FII {

  @Id private String id;
  private String ticker;
  private String gestao;
  private float cagrValorCota3Anos;
  private double patrimonio;
  private float pVp;
  private double liquidezMediaDiaria;
  private float cagrDividendos3Anos;
  private double numeroCotistas;
  private float preco;
  private float dy;
  private float percentualEmCaixa;
  private double numeroCotas;
  private float valorPatrimonialCota;
  private float ultimoDividendo;
}
