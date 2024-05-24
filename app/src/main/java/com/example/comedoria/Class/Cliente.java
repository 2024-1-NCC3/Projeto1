public class Cliente {
    // Campos
    private String nome; // Nome do cliente
    private String sobrenome; // Sobrenome do cliente
    private String idCliente; // ID do cliente

    // Construtor para inicializar um objeto Cliente com nome, sobrenome e ID
    public Cliente(String nome, String sobrenome, String idCliente) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idCliente = idCliente;
    }

    // Getter para o nome do cliente
    public String getNome() {
        return nome;
    }

    // Getter para o sobrenome do cliente
    public String getSobrenome() {
        return sobrenome;
    }

    // Getter para o ID do cliente
    public String getIdCliente() {
        return idCliente;
    }

    // Método para obter o nome completo do cliente
    public String getNomeCompleto(){
        // Verifica se o sobrenome é nulo ou "null"
        if(this.sobrenome == "null" || this.sobrenome == null){
            return nome; // Se for, retorna apenas o nome
        }
        else{
            // Caso contrário, retorna o nome seguido do sobrenome
            return nome + " " +sobrenome;
        }
    }
}
