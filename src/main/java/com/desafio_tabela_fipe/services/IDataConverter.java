package com.desafio_tabela_fipe.services;

import java.util.List;

public interface IDataConverter {
    <T> T obterDados(String json, Class<T> classe);
    <T>List<T> obterListaDados(String json, Class<T> classe);
}