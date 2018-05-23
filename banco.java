public class Banco{
  private String nomeBanco;
  private List<Cliente> clientes = new ArrayList<Cliente>();
  private List<Conta> contas = new ArrayList<Conta>();

  public Banco(String nomeBanco){
    this.nomeBanco = nomeBanco;
  }

  public addCliente(Cliente cliente){
    this.clientes.add(cliente);
  }

  public newConta(Cliente cliente){

  }

  public deleteCliente(String cpf_cnpj){

  }

  public deleteConta(){

  }

  
}
