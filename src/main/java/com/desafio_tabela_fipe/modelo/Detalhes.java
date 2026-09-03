package com.desafio_tabela_fipe.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Detalhes(@JsonAlias("TipoVeiculo") Integer tipo,
                      @JsonAlias("Valor") String valor,
                      @JsonAlias("Marca") String marca,
                      @JsonAlias("Modelo") String modelo,
                      @JsonAlias("AnoModelo") Integer anoModelo,
                      @JsonAlias("Combustivel") String combustivel,
                      @JsonAlias("CodigoFipe") String codigoFipe
){
    @Override
    public String toString() {
        return "\nDetalhes do veículo selecionado: " + "\n" +
                "Marca: " + marca + "\n" +
                "Modelo: " + modelo + "\n" +
                "Ano do modelo: " + anoModelo + "\n" +
                "Valor: " + valor + "\n" +
                "Tipo de combustível: " + combustivel + "\n" +
                "Código FIPE: " + codigoFipe;
    }
}
