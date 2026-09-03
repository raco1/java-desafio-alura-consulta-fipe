package com.desafio_tabela_fipe.main;

import com.desafio_tabela_fipe.modelo.Dados;
import com.desafio_tabela_fipe.modelo.Detalhes;
import com.desafio_tabela_fipe.modelo.ListaDados;
import com.desafio_tabela_fipe.modelo.Modelos;
import com.desafio_tabela_fipe.services.DataConverter;
import com.desafio_tabela_fipe.services.ParallelumApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    Scanner scanner = new Scanner(System.in);
    ParallelumApi api = new ParallelumApi();
    DataConverter converter = new DataConverter();
    String endereco;

    public void exibirMenu() {
        // -------------------------- INICIO: Buscar tipo do veículo --------------------------
            System.out.print("""
                    **********Tipos de veículos disponíveis para consulta**********
                    
                    - Carros
                    - Motos
                    - Caminhões
                    
                    Qual o tipo de veículo que procura?
                    """);
            String dados = scanner.nextLine().toLowerCase();
            if (dados.toLowerCase().contains("carr")) {
                endereco = URL_BASE + "carros/marcas";
            } else if (dados.toLowerCase().contains("mot")) {
                endereco = URL_BASE + "motos/marcas";
            } else if(dados.toLowerCase().contains("caminh")){
                endereco = URL_BASE + "caminhoes/marcas";
            }else {
                System.out.println("\n******Opção inválida. Tente novamente.******");
            }
            var json = api.apiFipe(endereco);
            List<Dados> marcas = converter.obterListaDados(json, Dados.class);
            marcas.stream()
                    .sorted(Comparator.comparing(Dados::codigo))
                    .forEach(System.out::println);
        // -------------------------- FIM: Buscar tipo de veículo --------------------------


        //-------------------------- INICIO: Buscar marca de veículo por código --------------------------
            System.out.println("\nPor favor, digite o código referente à marca que deseja consultar: ");
            String marca = scanner.nextLine().toLowerCase();
            endereco = endereco + "/" + marca + "/modelos/";
            json = api.apiFipe(endereco);
            Modelos dataModelos = converter.obterDados(json, Modelos.class);
            boolean isEmpty = marcas.stream().noneMatch(t -> t.codigo().startsWith(marca));

            if (isEmpty){
                System.out.println("\n******Opção não encontrada. Tente novamente.******");
            } else {
                dataModelos.modelos().stream()
                        .sorted(Comparator.comparing(Dados::codigo))
                        .forEach(System.out::println);
            }
        //-------------------------- FIM: Buscar marca de veículo --------------------------


        //-------------------------- INICIO: Buscar marca de veículo pelo nome --------------------------
            System.out.println("\n Por favor, digite o nome do veículo a ser buscado: ");
            String nomeVeiculo = scanner.nextLine();
            List<Dados> filtradoPorNome = dataModelos.modelos().stream()
                    .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                    .toList();
            System.out.println("\nModelos filtrados pelo nome: ");
            filtradoPorNome.forEach(System.out::println);
        //-------------------------- FIM: Buscar veículo pelo nome --------------------------


        //-------------------------- INICIO: Buscar modelo de veículo --------------------------
//            System.out.println("\nPor favor, digite o código referente ao modelo que deseja consultar:");
//            String modelo = scanner.nextLine().toLowerCase();
//            endereco = endereco + modelo + "/anos/";
//            json = api.apiFipe(endereco);
//            List<Dados> dataAnos = converter.obterListaDados(json, Dados.class);
//            dataAnos.stream()
//                    .sorted(Comparator.comparing(Dados::codigo))
//                    .forEach(System.out::println);
        //-------------------------- FIM: Buscar modelo de veículo --------------------------


        //-------------------------- INICIO: Buscar ano/detalhe de veículo --------------------------
            System.out.println("\nPor favor, digite o código referente ao ano que deseja consultar(por exemplo, 2023-1):");
            var codigoModelo = scanner.nextLine().toLowerCase();
            endereco = endereco + codigoModelo + "/anos/";
            json = api.apiFipe(endereco);

            List<Dados> anos = converter.obterListaDados(json, Dados.class);
            List<Detalhes> veiculos = new ArrayList<>();

            for (int i = 0; i < anos.size(); i++) {
                var enderecoAnos = endereco + anos.get(i).codigo();
                json = api.apiFipe(enderecoAnos);
                Detalhes dataDetalhe = converter.obterDados(json, Detalhes.class);
                veiculos.add(dataDetalhe);
            }
            System.out.println("\nTodos os veículos filtrados com avaliações por ano: ");
            veiculos.forEach(System.out::println);
        //-------------------------- FIM: Buscar ano/detalhe de veículo --------------------------
    }
}
