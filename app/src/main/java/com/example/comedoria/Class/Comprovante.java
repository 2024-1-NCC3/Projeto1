import java.util.List;

public class Comprovante {
    // Campos
    private String status; // Status do pedido
    private String observacoes; // Observações sobre o pedido
    private int numeroPedido; // Número do pedido
    private String dataRetirada; // Data de retirada do pedido
    private String horaRetirada; // Hora de retirada do pedido

    private String idPedido; // ID do pedido
    private List<Produto> listaProdutos; // Lista de produtos associados ao pedido

    // Construtor para inicializar um objeto Comprovante com os detalhes do pedido
    public Comprovante(String status, String observacoes, int numeroPedido, String dataRetirada, String horaRetirada, List<Produto> listaProdutos, String idPedido) {
        this.status = status;
        this.observacoes = observacoes;
        this.numeroPedido = numeroPedido;
        this.dataRetirada = dataRetirada;
        this.horaRetirada = horaRetirada;
        this.listaProdutos = listaProdutos;
        this.idPedido = idPedido;
    }

    // Getter para o status do pedido
    public String getStatus() {
        return status;
    }

    // Getter para as observações do pedido
    public String getObservacoes() {
        return observacoes;
    }

    // Getter para o número do pedido
    public int getNumeroPedido() {
        return numeroPedido;
    }

    // Getter para a data de retirada do pedido
    public String getDataRetirada() {
        return dataRetirada;
    }

    // Getter para a hora de retirada do pedido
    public String getHoraRetirada() {
        return horaRetirada;
    }

    // Getter para a lista de produtos associados ao pedido
    public List<Produto> getListaProdutos() {
        return listaProdutos;
    }

    // Getter para o ID do pedido
    public String getIdPedido() {
        return idPedido;
    }
}
