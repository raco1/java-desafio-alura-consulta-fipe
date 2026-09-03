package com.desafio_tabela_fipe.services;

import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

public class DataConverter implements IDataConverter {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public <T> List<T> obterListaDados(String json, Class<T> classe) {
        try {
            JavaType lista = mapper.getTypeFactory()
                    .constructCollectionType(List.class, classe);
            return mapper.readValue(json, lista);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON: " + e.getMessage(), e);
        }
    }
}