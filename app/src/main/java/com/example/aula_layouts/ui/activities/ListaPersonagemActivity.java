package com.example.aula_layouts.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aula_layouts.R;
import com.example.aula_layouts.dao.PersonagemDAO;
import com.example.aula_layouts.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.aula_layouts.ui.activities.ConstatesActivities.CHAVE_PERSONAGEM;

public class ListaPersonagemActivity extends AppCompatActivity {

    // Declaração das variáveis.
    public static final String TITULO_APPBAR = "Lista de Personagens";
    private final PersonagemDAO dao = new PersonagemDAO();
    private ArrayAdapter<Personagem> adapter;

    // Sobrepoe o método onCreate, chama a primeira tela e alguns métodos.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personagem);
        setTitle(TITULO_APPBAR);
        configuraFabNovoPersonagem();
        configuraLista();
    }

    // Método para a configuração do botão "FloatingActionButton" que abre um formulário para adicionar um personagem.
    private void configuraFabNovoPersonagem(){
        FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_add);
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { abreFormulario();}
        });
    }

    // Método para abrir o formulário.
    private void abreFormulario(){
        startActivity(new Intent(this, FormularioPersonagemActivity.class));
    }

    // Sobrepoe o método onResume da classe pai, chama o método onResume() e atualizaPersonagem().
    @Override
    protected void onResume(){
        super.onResume();
        atualizaPersonagem();
    }

    // Método que atualiza e exibe cada personagem criado.
    private void atualizaPersonagem(){
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    // Método que remove o personagem e apaga ele da lista.
    private void remove(Personagem personagem){
        dao.remove(personagem);
        adapter.remove(personagem);
    }

    // Sobrepoe o método onCreateContextMenu da classe pai infla o menu.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        //menu.add("Remover");
        getMenuInflater().inflate(R.menu.activity_lista_personagem_menu, menu);
    }

    // Sobrepoe o método onContextItemSelected da classe pai e mostra opções quando um item for selecionado, como deseja remover? e a escolha do usuário (sim ou não).
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item){

        int itemId = item.getItemId();
        if(itemId == R.id.activity_lista_personagem_menu_remover){
            new AlertDialog.Builder(this)
                    .setTitle("Removendo Personagem")
                    .setMessage("Tem certeza que quer remover?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                            remove(personagemEscolhido);
                        }
                    })
                    .setNegativeButton("Não", null)
                    .show();
        }
        return super.onContextItemSelected(item);
    }

    // Método que configura a lista, o visual e suas funcionalidades.
    private void configuraLista(){
        ListView listaDePersonagens = findViewById(R.id.activity_main_lista_personagem);
        configuraAdapter(listaDePersonagens);
        configuraItemPorClique(listaDePersonagens);
        registerForContextMenu(listaDePersonagens);
    }

    // Método que possibilita clicar em um personagem e editar ele.
    private void configuraItemPorClique(ListView listaDePersonagens){
        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition(posicao);
                abreFormularioEditar(personagemEscolhido);
            }
        });
    }

    // Método qeu abre a edição do personagem.
    private void abreFormularioEditar(Personagem personagemEscolhido){
        Intent vaiParaFormulario = new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class);
        vaiParaFormulario.putExtra(CHAVE_PERSONAGEM, personagemEscolhido);
        startActivity(vaiParaFormulario);
    }

    // Método que configura a lista com os personagens.
    private void configuraAdapter(ListView listaDePersonagens){
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDePersonagens.setAdapter(adapter);
    }
}
