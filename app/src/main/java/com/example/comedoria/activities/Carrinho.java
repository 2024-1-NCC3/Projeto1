package com.example.comedoria.activities;

import static com.example.comedoria.BuildConfig.API_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import com.example.comedoria.Adapter.AdapterCarrinho;
import com.example.comedoria.Class.Estoque;
import com.example.comedoria.Class.Produto;
import com.example.comedoria.ConectorAPI;
import com.example.comedoria.R;
import com.example.comedoria.VolleyCallback;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Carrinho extends AppCompatActivity {
    TextView txtHora, txtData,txtTotal, txtTituloQuantidade, txtTituloSoma, txtTituloHorario;
    TextInputEditText inputObservacoes;
    RecyclerView recycleCarrinho;
    List<Produto> produtos;
    String accessToken,idUsuario,idPedido,date,horas;
    Calendar dataFinal;
    int dia, mes, ano, hora, minuto;

    Map<Integer, Estoque> relacaoProdutoEstoque = new HashMap<>();
    double saldo;
    FloatingActionButton fabHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**Configura as variáveis que precisam ser trazidas ao iniciar a tela, como o token de acesso e o adapter da RecyclerView*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        accessToken = getIntent().getStringExtra("accessToken");
        idUsuario = getIntent().getStringExtra("idUsuario");

        String arrayProdutos = getIntent().getStringExtra("produtosSelecionados");
        produtos = Arrays.asList(new Gson().fromJson(arrayProdutos, Produto[].class));

        txtTotal = findViewById(R.id.txtTotal);
        txtHora = findViewById(R.id.txtHora);
        txtData = findViewById(R.id.txtData);
        inputObservacoes = findViewById(R.id.inputObservacoes);
        recycleCarrinho = findViewById(R.id.recylerCarrinho);
        txtTituloQuantidade = findViewById(R.id.txtTituloQuantidade);
        txtTituloSoma = findViewById(R.id.txtTituloSoma);
        txtTituloHorario = findViewById(R.id.txtTituloHorario);

        fabHome = findViewById(R.id.btnFlutuante);
        fabHome.setImageTintList(ColorStateList.valueOf(Color.WHITE));

        /**seta a largura do título quantidade corretamente*/
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.remove);
        int larguraIcon = bitmap.getWidth();

        TextPaint paintQuantidade = txtTituloHorario.getPaint();
        float tamanhoQuantidade = paintQuantidade.measureText("000");
        txtTituloQuantidade.setWidth((int) (tamanhoQuantidade) + (larguraIcon *2));

        float tamanhoSoma = paintQuantidade.measureText("R$ 0000,00 ");
        txtTituloSoma.setWidth((int) (tamanhoSoma));


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycleCarrinho.setLayoutManager(layoutManager);
        recycleCarrinho.setHasFixedSize(true);

        AdapterCarrinho adapter = new AdapterCarrinho(produtos, this);
        recycleCarrinho.setAdapter(adapter);
        atualizarTotal();
    }

    /**Configura o botão de selecionar a hora para a entrega do pedido*/
    public void selecionarHora(View view){
        Calendar calendar = Calendar.getInstance();
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                .setMinute(calendar.get(Calendar.MINUTE))
                .setTitleText("Selecione a hora de retirada")
                .build();



        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora = picker.getHour();
                minuto = picker.getMinute();

                calendar.set(Calendar.HOUR_OF_DAY, hora);
                calendar.set(Calendar.MINUTE, minuto);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

                horas = simpleDateFormat.format(calendar.getTime());
                txtHora.setText(horas);


                System.out.printf(txtHora.getText().toString());
            }
        });
        Timestamp time = new Timestamp(calendar.getTimeInMillis());
        picker.show(getSupportFragmentManager(),"tag");
    }

    /**Configura o botão de selecionar a data para a entrega do pedido*/
    public void selecionarData(View view){
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecione a data de retirada")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(selection));

                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat meuFormato = new SimpleDateFormat("dd/MM/yy");
                try {
                    Date dataFormatada = formato.parse(date);
                    txtData.setText(meuFormato.format(dataFormatada));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        picker.show(getSupportFragmentManager(),"tag");
    }

    /**Atualiza a lista dos produtos*/
    public void atualizarLista(List<Produto> listaAtualizada){
        this.produtos = listaAtualizada;
    }

    /**Atualiza o total da soma dos pedidos*/
    public void atualizarTotal(){
        double soma = 0;
        for(Produto produto: produtos){
            soma+= produto.getPreco() * produto.getQuantidade();
        }
        txtTotal.setText(String.format(Locale.getDefault(), "R$ %.2f", soma));
    }

    /**Volta para a tela anterior*/
    public void voltarTelaCarrinho(View view){
        finish();
    }

    /**Volta para o início do aplicativo*/
    public void voltarInicio(View view){
        Intent intent = new Intent(this, PaginaInicial.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }


    /**Função para o botão de finalizar o pedido*/
    public void finalizarPedido(View view) throws JSONException {

        double soma = 0;

        /**Verifica se o campo de data e hora não estão vazios*/
        if(date == null || horas == null){
            Toast.makeText(this, "Por favor, selecione uma data e hora para retirada", Toast.LENGTH_SHORT).show();
            return;
        }
        for(Produto produto: produtos){
            soma += produto.getPreco() * produto.getQuantidade();
        }
        if(soma <= 0){
            Toast.makeText(this, "Selecione no mínimo um produto", Toast.LENGTH_SHORT).show();
            return;
        }else{
            /**Verifica o estoque enquanto o cliente está finalizando o pedido, para conferir se tem produtos disponíveis*/
            verificarEstoque(new VolleyCallback() {
                @Override
                public void onResponse(boolean sucesso) throws JSONException {
                    if(sucesso){
                        verificarSaldo(new VolleyCallback() {
                            @Override
                            public void onResponse(boolean sucesso) throws JSONException {
                                if(sucesso){
                                    criarPedido();
                                }
                            }
                        });
                    }
                }
            });


        }

    }


    /**Verifica se o usuário tem saldo suficiente para o pedido*/
    private void verificarSaldo( final VolleyCallback saldoCallback){
        Map<String, String> headers = new HashMap<>();

        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/usuarios?select=*",
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        JSONObject objCliente = response.getJSONObject(0);
                        saldo = objCliente.getDouble("saldo");
                        double total = 0;

                        for(Produto produto: produtos){
                            total += produto.getPreco() * produto.getQuantidade();
                        }

                        if(saldo < total){
                            Toast.makeText(getApplicationContext(), "Saldo insuficiente", Toast.LENGTH_SHORT).show();
                            saldoCallback.onResponse(false);
                        }else{
                            saldoCallback.onResponse(true);
                        }
                    }

                    @Override
                    public void onError(VolleyError error) throws JSONException {
                        saldoCallback.onResponse(false);
                    }
                }

        );

    }

    /**Verifica se o estoque tem os produtos*/
    private void verificarEstoque(final VolleyCallback estoqueCallBack){
        String produtosDoPedido = "";
        for(Produto produto:produtos){
            produtosDoPedido += produto.getId() +",";
        }
        produtosDoPedido = produtosDoPedido.substring(0,produtosDoPedido.length()-1);

        Map<String, String> headers = new HashMap<>();
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/produtos?select=*," +
                        "categoria(nome_categoria)," +
                        "estoque(id_estoque,quantidade)" +
                        "&id_produto=in.(" + produtosDoPedido + ")",
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {


                        for(int i = 0; i<response.length();i++){
                            JSONObject objEstoque = response.getJSONObject(i);
                            int idProduto = objEstoque.getInt("id_produto");
                            JSONObject estoque = objEstoque.getJSONObject("estoque");
                            int idEstoque = estoque.getInt("id_estoque");
                            int quantidadeEstoque = estoque.getInt("quantidade");

                            relacaoProdutoEstoque.put(idProduto,new Estoque(idEstoque,quantidadeEstoque));
                        }

                        for(Produto produto:produtos){
                            //Se a quantidade selecionada for maior que o estoque da tia, retorna falso
                            if(produto.getQuantidade() > relacaoProdutoEstoque.get(produto.getId()).getQuantidade() ){
                                Toast.makeText(getApplicationContext(), "Não temos mais o produto: " + produto.getNome() +" em estoque.", Toast.LENGTH_SHORT).show();
                                estoqueCallBack.onResponse(false);
                                return;
                            };
                        }
                        estoqueCallBack.onResponse(true);
                    }

                    @Override
                    public void onError(VolleyError error) throws JSONException {
                        estoqueCallBack.onResponse(false);
                    }
                }

        );

    }

    /**Função de criar o pedido no banco de dados*/
    private void criarPedido() throws JSONException {
        Map<String, String> headers = new HashMap<>();
        //define os heades que a solicitação vai precisar
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("Content-Type", "application/json");
        headers.put("Prefer", "return=representation");



        //Faz a requisição para criar um pedido no servidor
        ConectorAPI.conexaoArrayPOST(
                "/rest/v1/pedido",
                headers,
                JsonNovoPedido(),
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        idPedido = response.getJSONObject(0).getString("id_pedido");
                        adicionarProdutosNoPedido();
                        reduzirEstoque();
                        reduzirSaldo();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        /**se a resposta for um erro, irá apresentar um Toast com o erro*/
                        String body = null;
                        try {
                            body = new String(error.networkResponse.data, "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                        JSONObject data = null;
                        try {
                            data = new JSONObject(body);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        String message = data.optString("error_description");
                        Toast.makeText(Carrinho.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    /**Função para reduzir o saldo do usuário após a compra*/
    private void reduzirSaldo() throws JSONException {
        double total = 0.0;
        Map<String, String> headers = new HashMap<>();
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("Content-Type", "application/json");
        headers.put("Prefer", "return=representation");


        for(Produto produto: produtos){
            total+= produto.getQuantidade() * produto.getPreco();
        }

        JSONObject objCorpo = new JSONObject();
        objCorpo.put("saldo", saldo - total);

        JSONArray req = new JSONArray();
        req.put(objCorpo);


        ConectorAPI.conexaoArrayPATCH(
                "/rest/v1/usuarios?id_user=eq." + idUsuario,
                headers,
                req,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {

                    }

                    @Override
                    public void onError(VolleyError error) throws JSONException {

                    }
                }


        );
    }

    /**Função para reduzir o estoque após a compra*/
    private void reduzirEstoque() throws JSONException {
        Map<String, String> headers = new HashMap<>();
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("Content-Type", "application/json");
        headers.put("Prefer", "return=representation");

        for(Produto produto: produtos){
            JSONObject objCorpo = new JSONObject();
            int quantidadePedido = produto.getQuantidade();
            int quantidadeEstoque = relacaoProdutoEstoque.get(produto.getId()).getQuantidade();

            objCorpo.put("quantidade", quantidadeEstoque - quantidadePedido );

            JSONArray req = new JSONArray();
            req.put(objCorpo);

            ConectorAPI.conexaoArrayPATCH(
                    "/rest/v1/estoque?id_estoque=eq." + relacaoProdutoEstoque.get(produto.getId()).getIdEstoque(),
                    headers,
                    req,
                    getApplicationContext(),
                    new ConectorAPI.VolleyArrayCallback() {
                        @Override
                        public void onSuccess(JSONArray response) throws JSONException {

                        }

                        @Override
                        public void onError(VolleyError error) throws JSONException {

                        }
                    }


            );

        }

    }

    /**Função para formular o corpo de requisição correto para registrar um novo pedido*/
    private JSONArray JsonNovoPedido() throws JSONException {
        Log.i("SUpabase", date);

        JSONObject solicitacao = new JSONObject();
        solicitacao.put("status", "Aguardando Pagamento");
        solicitacao.put("id_usuario", idUsuario);
        solicitacao.put("hora_para_retirada", horas + ":00");
        solicitacao.put("data_para_retirada", date);
        solicitacao.put("observacoes", inputObservacoes.getText().toString());


        JSONArray req = new JSONArray();
        req.put(solicitacao);

        return req;
    }

    /**Função de adicionar os produtos selecionados ao pedido no banco de dados*/
    private void adicionarProdutosNoPedido() throws JSONException {


        Map<String, String> headers = new HashMap<>();
        //define os heades que a solicitação vai precisar
        headers.put("apikey", API_KEY);
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("Prefer", "return=representation");

        ConectorAPI.conexaoArrayPOST(
                "/rest/v1/detalhes_pedido",
                headers,
                JsonPedidosParaAdicionar(),
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        Toast.makeText(Carrinho.this, "Pedido feito com sucesso", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Carrinho.this, ComprovantePedido.class);
                        i.putExtra("accessToken", accessToken);
                        i.putExtra("idPedido", idPedido);
                        startActivity(i);
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Toast.makeText(Carrinho.this, "Erro", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    /**Função para formular o corpo de requisição correto para inserir os produtos no pedido*/
    private JSONArray JsonPedidosParaAdicionar() throws JSONException {
        JSONArray req = new JSONArray();
        for(Produto produto: produtos){
            JSONObject prod = new JSONObject();

            prod.put("id_produto", produto.getId());
            prod.put("id_pedido", idPedido);
            prod.put("quantidade",produto.getQuantidade());

            req.put(prod);
        }
        return req;
    }

}