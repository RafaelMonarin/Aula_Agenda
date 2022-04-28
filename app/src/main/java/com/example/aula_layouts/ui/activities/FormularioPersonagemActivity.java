package com.example.aula_layouts.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.aula_layouts.R;
import com.example.aula_layouts.dao.PersonagemDAO;
import com.example.aula_layouts.model.Personagem;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import static com.example.aula_layouts.ui.activities.ConstatesActivities.CHAVE_PERSONAGEM;

public class FormularioPersonagemActivity extends AppCompatActivity {

    // Declaração de variáveis.
    // Variáveis de string para mudar o texto do cabeçario, static para não ser editado pelo usuário.
    private static final String TITULO_APPBAR_EDITA_PERSONAGEM = "Editar o Personagem";
    private static final String TITULO_APPBAR_NOVO_PERSONAGEM = "Novo Personagem";
    // Variáveis de campo de textos.
    private EditText campoNome;
    private EditText campoNascimento;
    private EditText campoAltura;
    // Variável das classes javas criadas: PersonagemDAO e Personagem.
    private final PersonagemDAO dao = new PersonagemDAO();
    private Personagem personagem;

    // "Infla / Cria" um menu no cabeçario, menu feito no xml.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Sobrepoe o onOptionsItemSelected da classe pai, passa o id e retorna item.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_personagem_menu_salvar){
            finalizarFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    // Sobrepoe o método onCreate, chama a primeira tela e alguns métodos.
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_formulario_personagem));
        inicializacaoCampos();
        carregaPersonagem();
        //checaPermissoes();
    }

    // Método que carrega os personagens criados.
    private void carregaPersonagem(){
        Intent dados = getIntent();
        if(dados.hasExtra(CHAVE_PERSONAGEM)){
            setTitle(TITULO_APPBAR_EDITA_PERSONAGEM);
            personagem = (Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
            preencheCampos();
        } else {
            setTitle(TITULO_APPBAR_NOVO_PERSONAGEM);
            personagem = new Personagem();
        }
    }

    // Método que passa os dados preenchidos para a classe Personagem.
    private void preencheCampos(){
        campoNome.setText(personagem.getNome());
        campoAltura.setText(personagem.getAltura());
        campoNascimento.setText(personagem.getNascimento());
    }

    // Método que finaliza salvando os dados.
    private void finalizarFormulario(){
        preencherPersonagem();
        if(personagem.IdValido()){
            dao.edita(personagem);
            finish();
        } else {
            dao.salva(personagem);
        }
        finish();
    }

    // Método que busca os campos criados no xml e atribui eles as variáveis de campo de textos, formata os inputs de textos.
    private void inicializacaoCampos(){
        campoNome = findViewById(R.id.editText_nome);
        campoAltura = findViewById(R.id.editText_altuta);
        campoNascimento = findViewById(R.id.editText_nascimento);

        SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("N,NN");
        MaskTextWatcher mtwAltura = new MaskTextWatcher(campoAltura, smfAltura);
        campoAltura.addTextChangedListener(mtwAltura);

        SimpleMaskFormatter smfNascimento = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwNascimento = new MaskTextWatcher(campoNascimento, smfNascimento);
        campoNascimento.addTextChangedListener(mtwNascimento);
    }

    // Método que passa os dados inputados pelo usuário para a classe Personagem.
    private void preencherPersonagem(){
        String nome = campoNome.getText().toString();
        String altura = campoAltura.getText().toString();
        String nascimento = campoNascimento.getText().toString();

        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);
    }
}
