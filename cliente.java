package banco;

public class Cliente{
  private String nomeCliente;
  private String cpf_cnpj;
  private String endereco;
  private String fone;

  public Cliente(String nomeCliente, String cpf_cnpj, String endereco, String fone){
    this.nomeCliente = nomeCliente;
    this.cpf_cnpj = cpf_cnpj;
    this.endereco = endereco;
    this.fone = fone;
  }

  public String getNomeCliente(){
    return nomeCliente;
  }

  public String getCpf_cnpj(){
    return cpf_cnpj;
  }

  public String getEndereco(){
    return endereco;
  }

  public String getFone(){
    return fone;
  }

  public void setNomeCliente(String nomeCliente){
    this.nomeCliente = nomeCliente;
  }

  public void setCpf_cnpj(String cpf_cnpj){
    this.cpf_cnpj = cpf_cnpj;
  }

  public void setEndereco(String endereco){
    this.endereco = endereco;
  }

  public void setFone(String fone){
    this.fone = fone;
  }
}
