package com.paulomiotto.banking; //Define o pacote (namespace) onde essa classe vive.

import java.util.Objects; /* Em java, toda classe herda direta ou indiretamente de java.util.Objects.
                             Objects define métodos básicos que toda classe java tem, por exemplo:
                             equals(Object o) --> comparar objetos;
                             hashCode() --> usado por HashMap/HashSet;
                             toString() --> representação em texto. */

public class Customer { //Declara que a classe é pública (qualquer outro código pode ver) e que se chama Customer.
    private final String id;     //private --> só a classe pode acessar diretamente (encapsulamento).
    private final String name;   //final   --> depois de receber o valor pelo construtor, não pode mudar mais.
                                 //Isso torna o objeto imutável.

    //Atenção: validação no construtor garante objetos sempres válidos!
    public Customer(String id, String name) { //construtor --> É chamado quando você faz new Customer(... )
                                              //construtor --> Recebe os valores iniciais (id, nome)
        if (id == null || id.isBlank()) { //id == null evita NullPointerException e,
                                          //id.isBlank() rejeita string vazia ou só com espaços (ex.: " ").
            throw new IllegalArgumentException("Customer id cannot be null/blank"); //Se for inválido, lançamos exception
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be null/blank");
        }
        this.id = id;      //this.id e this.name são os campos da classe.
        this.name = name;  //id e name (sem this) são os parâmetros do construtor.
    }                      //Aqui você “congela” os valores nos campos final

    public String id() { //Metodo “getter” para o id.
        return id;       //Em vez de getId(), usei id() (estilo moderno; bem comum com records).
    }                    //Como o campo é private, esse metodo é o jeito controlado de ler o valor.

    public String name() {
        return name;
    }

    //Sobrescrever
    // +--> substituir a implementação herdada de Object por uma implementação que faça sentido para sua classe.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // == compara referências, não conteúdo. Então, se This e "o" apontam para o mesmo objeto na memória, retorne True. “É exatamente o MESMO objeto na memória?”
        if (!(o instanceof Customer other)) return false; // instanceof verifica se um objeto é uma instância de uma classe específica.
                                                          // Se for,
                                                          // ele já “converte” para Customer e coloca na variável other.
        return id.equals(other.id); //Regra de igualdade: comparar pelo id.
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Customer{id='%s', name='%s'}".formatted(id, name);
    }
}
/*
*
*
* */