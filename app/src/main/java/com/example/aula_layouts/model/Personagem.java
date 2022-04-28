package com.example.aula_layouts.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Personagem implements Serializable {

    //Declaração das variáveis dos dados dos personagens.
    private String nome;
    private String altura;
    private String nascimento;
    private int id = 0;

    //Método que recebe variáveis do mesmo tipo criadas anteriormente para poder salva-las na classe.
    public Personagem(String nome, String altura, String nascimento){

        this.nome = nome;
        this.altura = altura;
        this.nascimento = nascimento;
    }

    public Personagem(){

    }

    //Métodos que seta as variáveis que podem ser acessados em outro código.
    public void setNome(String nome) { this.nome = nome;}

    public void setAltura(String altura) { this.altura = altura;}

    public void setNascimento(String nascimento) { this.nascimento = nascimento;}

    public String getNome() { return nome;}

    public String getAltura() { return altura;}

    public String getNascimento() { return nascimento;}

    @NonNull
    @Override
    public String toString() { return nome;}

    public void setId(int id) { this.id = id;}

    public int getId() { return id;}

    public boolean IdValido() { return id > 0;}
}
