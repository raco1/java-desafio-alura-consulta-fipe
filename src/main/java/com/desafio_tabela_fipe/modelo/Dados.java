package com.desafio_tabela_fipe.modelo;

public record Dados(String codigo,
                    String nome
) {
    @Override
    public String toString() {
        return "\nCódigo: " + codigo + "\n" +
                "Nome: " + nome;
    }
}
