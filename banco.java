import TP;

import java.util.List;

public class Banco{
  private String nomeBanco;
  private List<Cliente> clientes = new ArrayList<Cliente>();
  private List<Conta> contas = new ArrayList<Conta>();

  public Banco(String nomeBanco){
    this.nomeBanco = nomeBanco;
  }

  public int getConta(int numeroConta){
    for (int i = 0; i < this.contas.size(); i++){
      if (this.contas.get(i).getNumConta() == numeroConta){
        return i;
      }
  }

  public void addCliente(Cliente cliente){
    this.clientes.add(cliente);
  }

  public void newConta(Cliente cliente){
    Conta conta = new Conta(cliente);
    this.contas.add(conta);
  }

  public void deleteCliente(String cpf_cnpj){
    int indiceCliente;
    for (int i = 0; i < this.clientes.size(); i++){
      if (this.clientes.get(i).getCpf_cnpj() == cpf_cnpj){
        indiceCliente = i;
      }else{
        System.out.println("Cliente nao cadastrado");
      }
    }
    for (int i = 0; i < this.contas.size(); i++){
      if (this.contas.get(i).cliente.getCpf_cnpj() == cpf_cnpj){
        this.clientes.remove(indiceCliente);
      }else{
        System.out.println("Cliente nao possui conta");
      }
    }
  }

  public void deleteConta(int numeroConta){
    int indiceConta = getConta(numeroConta);
    this.contas.remove(indiceConta);
  }

  public void deposito(int numeroConta, double valor){
    int indiceConta = getConta(numeroConta);
    this.contas.get(indiceConta).credito(valor, "Deposito");
  }

  public void saque(int numeroConta, double valor){
    int indiceConta  = getConta(numeroConta);
    this.contas.get(indiceConta).debito(valor, "Saque");
  }

  public void transferencia(int contaOrigem, int contaDestino, double valor){
    int origem  = getConta(contaOrigem);
    int destino = getConta(contaDestino);
    String mensagemOrigem = "Transferencia para a conta " + contaDestino;
    String mensagemDestino = "Transferencia da conta " + contaOrigem;
    this.contas.get(contaOrigem).debito(valor, mensagemOrigem);
    this.contas.get(contaDestino).credito(valor, mensagemDestino);
  }

  public void cobraTarifa(){
    for (int i = 0; i < this.contas.size(); i++){
      this.contas.get(i).debito(15, "Cobranca de tarifa");
    }
  }

  public void cobrarCPMF(){

  }

  public double saldo(int numeroConta){
    int indiceConta = getConta(numeroConta);
    return this.contas.get(indiceConta).getSaldo;
  }

  public List<Movimentacao> extratoMesAtual(int numeroConta){
    int indiceConta = getConta(numeroConta);
    return this.contas.get(indiceConta).extratoMes();
  }

  public List<Movimentacao> extratoDataInicial(int numeroConta, GregorianCalendar inicio){
    int indiceConta = getConta(numeroConta);
    return this.contas.get(indiceConta).extratoPartindo(inicio);
  }

  public List<Movimentacao> extratoEntreDatas(int numeroConta, GregorianCalendar inicio, GregorianCalendar fim){
    int indiceConta = getConta(numeroConta);
    return this.contas.get(indiceConta).extratoPartindo(inicio, fim);
  }

  public List<Cliente> getListaClientes(){
    return this.clientes;
  }

  public List<Conta> getListaContas(){
    return this.contas;
  }
}
