import java.io.*;
import java.net.*;

public class ProxyServer {
    public static void main(String[] args) throws IOException {
        int port = 8080; // Porta que o servidor proxy irá ouvir

        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new ProxyThread(clientSocket)).start();
        }
    }
}


