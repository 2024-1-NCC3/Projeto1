package com.example.comedoria;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.pm.PackageManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class ComprovantePedido extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprovante_pedido);

        ImageView homeButton = findViewById(R.id.homebutt);

        homeButton.setOnClickListener(v -> {
            // Crie um Intent para iniciar a HomeActivity
            Intent intent = new Intent(ComprovantePedido.this, Teste.class);
            // Inicie a HomeActivity
            startActivity(intent);
            // Finalize a atividade atual se necessário
            finish();
        });

        TextView tokenTextView = findViewById(R.id.tokenTextView);

        // Gerar um novo token
        Token token = Token.create(getPackageName(), getPackageManager());

        // Exibir o token na interface do usuário
        tokenTextView.setText("Token: " + token.getToken());
    }

    public static class Token {
        private String token;

        private Token(String token) {
            this.token = token;
        }

        // Método para criar um novo token
        public static Token create(String packageName, PackageManager packageManager) {
            String token = generateToken();
            // Realize qualquer lógica necessária para associar o token com o pacote
            return new Token(token);
        }

        // Método para verificar se um token corresponde a um determinado pacote
        public boolean matches(String packageName, PackageManager packageManager) {
            // Implemente sua lógica de verificação aqui
            // Por exemplo, você pode comparar o token com uma lista de tokens associados ao pacote
            return true; // Simplesmente retornando verdadeiro para este exemplo
        }

        // Método para gerar um token aleatório de 6 caracteres
        private static String generateToken() {
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            Random random = new Random();
            StringBuilder token = new StringBuilder(6);
            for (int i = 0; i < 6; i++) {
                token.append(characters.charAt(random.nextInt(characters.length())));
            }
            return token.toString();
        }

        // Método para serializar o token em um array de bytes
        public byte[] serialize() throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        }

        // Método para desserializar um token de um array de bytes
        public static Token deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (Token) objectInputStream.readObject();
        }

        // Método para obter o token
        public String getToken() {
            return token;
        }
    }
}
