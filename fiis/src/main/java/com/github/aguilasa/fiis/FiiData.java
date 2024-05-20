package com.github.aguilasa.fiis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiiData {
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
