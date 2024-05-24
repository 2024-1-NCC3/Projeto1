public class Estoque {
    // Campos
    private int idEstoque; // ID do estoque
    private int quantidade; // Quantidade em estoque

    // Construtor para inicializar um objeto Estoque com o ID e a quantidade
    public Estoque(int idEstoque, int quantidade) {
        this.idEstoque = idEstoque;
        this.quantidade = quantidade;
    }

    // Getter para o ID do estoque
    public int getIdEstoque() {
        return idEstoque;
    }

    // Getter para a quantidade em estoque
    public int getQuantidade() {
        return quantidade;
    }
}
