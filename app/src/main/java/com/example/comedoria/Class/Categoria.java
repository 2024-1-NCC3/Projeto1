public class Categoria {
    // Campos
    private String nome; // Nome da categoria
    private String descricao; // Descrição da categoria
    private String caminho; // Caminho da categoria (possivelmente uma imagem ou ícone)
    private Boolean aparecer; // Booleano indicando se a categoria deve aparecer

    private int idCategoria; // ID da categoria

    // Construtor com todos os campos
    public Categoria(String nome, String descricao, String caminho, boolean aparecer) {
        this.nome = nome;
        this.descricao = descricao;
        this.caminho = caminho;
        this.aparecer = aparecer;
    }

    // Construtor com nome e ID apenas
    public Categoria(String nome, int idCategoria) {
        this.nome = nome;
        this.idCategoria = idCategoria;
    }

    // Getter para o ID da categoria
    public int getIdCategoria() {
        return idCategoria;
    }

    // Getter e setter para o campo 'aparecer'
    public Boolean getAparecer() {
        return aparecer;
    }
    public void setAparecer(Boolean aparecer) {
        this.aparecer = aparecer;
    }

    // Getters e setters para outros campos
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getCaminho() {
        return caminho;
    }
    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    // Sobrescreve o método toString() para retornar o nome da categoria
    @Override
    public String toString() {
        return nome;
    }
}
