import java.util.List;

public class Pedido {
    // Campos
    private String data; // Data do pedido
    private String hora; // Hora do pedido
    private String status; // Status do pedido
    private List<String> produtos; // Lista de produtos no pedido
    private double total; // Total do pedido
    private String id_pedido; // ID do pedido
    private String nomeCliente; // Nome do cliente
    private int numeroPedido; // Número do pedido

    // Construtor para inicializar um objeto Pedido com data, produtos, total, status e ID do pedido
    public Pedido(String data, List<String> produtos, double total, String status, String id_pedido) {
        this.data = data;
        this.produtos = produtos;
        this.total = total;
        this.status = status;
        this.id_pedido = id_pedido;
    }

    // Construtor adicional para inicializar um objeto Pedido com data, hora, produtos, total, status, ID do pedido, nome do cliente e número do pedido
    public Pedido(String data, String hora, List<String> produtos, double total, String status, String id_pedido, String nomeCliente, int numeroPedido) {
        this.data = data;
        this.hora = hora;
        this.produtos = produtos;
        this.total = total;
        this.status = status;
        this.id_pedido = id_pedido;
        this.nomeCliente = nomeCliente;
        this.numeroPedido = numeroPedido;
    }

    // Getter para a data do pedido
    public String getData() {
        return data;
    }

    // Getter para a hora do pedido
    public String getHora() {
        return hora;
    }

    // Getter para o status do pedido
    public String getStatus() {
        return status;
    }

    // Getter para a lista de produtos no pedido
    public List<String> getProdutos() {
        return produtos;
    }

    // Método para obter os produtos como uma string formatada
    public String produtosComoString() {
        String retorno = "";
        for(int i = 0; i < produtos.size(); i++) {
            if(i == produtos.size() - 1) {
                retorno += produtos.get(i);
            } else {
                retorno += produtos.get(i) + "\n";
            }
        }
        return retorno;
    }

    // Getter para o ID do pedido
    public String getId_pedido() {
        return id_pedido;
    }

    // Getter para o total do pedido
    public double getTotal() {
        return total;
    }

    // Getter para o nome do cliente
    public String getNomeCliente() {
        return nomeCliente;
    }

    // Getter para o número do pedido
    public int getNumeroPedido() {
        return numeroPedido;
    }
}
