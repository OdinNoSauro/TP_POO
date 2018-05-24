import banco;

import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;s

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
        System.out.println("Cliente não cadastrado");
      }
    }
    for (int i = 0; i < this.contas.size(); i++){
      if (this.contas.get(i).cliente.getCpf_cnpj() == cpf_cnpj){
        this.clientes.remove(indiceCliente);
      }else{
        System.out.println("Cliente não possui conta");
      }
    }
  }

  public void deleteConta(int numeroConta){
    int indiceConta = getConta(numeroConta);
    this.contas.remove(indiceConta);
  }

  public void deposito(int numeroConta, double valor){
    int indiceConta = getConta(numeroConta);
    this.contas.get(indiceConta).credito(valor, "Depósito");
  }

  public void saque(int numeroConta, double valor){
    int indiceConta  = getConta(numeroConta);
    this.contas.get(indiceConta).debito(valor, "Saque");
  }

  public void transferencia(int contaOrigem, int contaDestino, double valor){
    int origem  = getConta(contaOrigem);
    int destino = getConta(contaDestino);
    String mensagemOrigem = "Transferência para a conta " + contaDestino;
    String mensagemDestino = "Transferência da conta " + contaOrigem;
    this.contas.get(contaOrigem).debito(valor, mensagemOrigem);
    this.contas.get(contaDestino).credito(valor, mensagemDestino);
  }

  public void cobraTarifa(){
    for (int i = 0; i < this.contas.size(); i++){
      this.contas.get(i).debito(15, "Cobrança de tarifa");
    }
  }

  public void cobrarCPMF(){
    GregorianCalendar dataInicio = new GregorianCalendar();
    float valorTotal;
    dataInicio.add(dataInicio.DAY_OF_MONTH, -7);
    for (int i = 0; i < this.contas.size(); i++){
      for (int j = 0; j < this.contas.get(i).getMovimentacoes().size(); j++) {
        GregorianCalendar referencia = this.contas.get(i).getMovimentacoes().get(j).getData();
        if ((this.contas.get(i).getMovimentacoes().get(j).getTipo() == 'D') && (referencia >= dataInicio)) {
          valorTotal += this.contas.get(i).getMovimentacoes().get(j).getValor();
        }
      }
      this.contas.get(i).debito(valorTotal * 0.0038, "Cobrança de CPMF");
      valorTotal = 0;
    }
  }

  public double saldo(int numeroConta){
    int indiceConta = getConta(numeroConta);
    return this.contas.get(indiceConta).getSaldo;
  }

  public List<Movimentacao> extratoMesBanco(int numeroConta){
    int indiceConta = getConta(numeroConta);
    return this.contas.get(indiceConta).extratoMes();
  }

  public List<Movimentacao> extratoInicioBanco(int numeroConta, GregorianCalendar inicio){
    int indiceConta = getConta(numeroConta);
    return this.contas.get(indiceConta).extratoInicio(inicio);
  }

  public List<Movimentacao> extratoInicioFimBanco(int numeroConta, GregorianCalendar inicio, GregorianCalendar fim){
    int indiceConta = getConta(numeroConta);
    return this.contas.get(indiceConta).extratoInicioFim(inicio, fim);
  }

  public List<Cliente> getListaClientes(){
    return this.clientes;
  }

  public List<Conta> getListaContas(){
    return this.contas;
  }

  public void gravarArquivo() throws IOException{
    FileWriter arq = new FileWriter("Dados Bancários.txt");
    PrintWriter gravarArq = new PrintWriter(arq);
    gravarArq.printf(this.nomeBanco);
    gravarArq.printf("+-+");
    for (int i = 0; i < this.clientes.size(); i++) {
      gravarArq.printf(this.clientes.get(i).getNomeCliente() + " " + this.clientes.get(i).getCpf_cnpj() + " " + this.clientes.get(i).getEndereco() + " " + this.clientes.get(i).getFone());
    }
    gravarArq.printf("+-+");
    for (int i = 0; i < this.contas.size(); i++) {
      gravarArq.printf(this.contas.get(i).getNumConta() + " " + this.contas.get(i).getSaldo() + " " + this.contas.get(i).getCliente().getCpf_cnpj);
    }
    arq.close();
  }

  public void LerArquivo() throws IOException{
    FileReader arq = new FileReader("Dados Bancários.txt");

  }
}
