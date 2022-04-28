package com.example.aula_layouts.dao;

import com.example.aula_layouts.model.Personagem;

import java.util.ArrayList;
import java.util.List;

public class PersonagemDAO {

    // Declaração de variáveis.
    private final static List<Personagem> personagens = new ArrayList<>();
    private static int contadorDeIds = 1;

    // Método para salvar na classe Personagem passando id e todos os dados do personagem.
    public void salva(Personagem personagemSalvo){
        personagemSalvo.setId(contadorDeIds);
        personagens.add(personagemSalvo);
        atualizaId();
    }

    //+1 para o contador.
    private void atualizaId() { contadorDeIds++;}
    
    // Método para editar um personagem já criado na classe Personagem.
    public void edita(Personagem personagem){
        Personagem personagemEncontrado = buscaPersonagemId(personagem);
        if(personagemEncontrado != null){
            int posicaoDoPersonagem = personagens.indexOf(personagemEncontrado);
            personagens.set(posicaoDoPersonagem, personagem);
        }
    }

    //Busca o personagem pelo id.
    private Personagem buscaPersonagemId(Personagem personagem){
        for (Personagem p : personagens){
            if(p.getId() == personagem.getId()){
                return p;
            }
        }
        return null;
    }

    //Cria uma nova lista da classe Personagem e retorna a classe com os personagens editados.
    public List<Personagem> todos() {return new ArrayList<>(personagens);}

    //Método para remover um personagem na classe Personagem.
    public void remove(Personagem personagem){
        Personagem personagemDevolvido = buscaPersonagemId(personagem);
        if(personagemDevolvido != null){
            personagens.remove(personagemDevolvido);
        }
    }
}
