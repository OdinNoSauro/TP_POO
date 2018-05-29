package banco;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Conta {
  private int numConta;
  private double saldo;
  Cliente cliente;
  private ArrayList<Movimentacao> movimentacoes = new ArrayList<Movimentacao>();
  private static int proximoNumConta;

  public Conta(Cliente cliente){
	proximoNumConta++;
	this.numConta = proximoNumConta;
    this.saldo = 0;
    this.cliente = cliente;
  }
  
  public Conta(Cliente cliente, int conta, int proxconta, double saldo) {
	  this.cliente = cliente;
	  this.numConta = conta;
	  if (proxconta > Conta.proximoNumConta)
		  Conta.proximoNumConta = proxconta;
	  this.saldo = saldo;
  }

  public int getNumConta(){
    return numConta;
  }

  public double getSaldo(){
    return saldo;
  }

  public Cliente getCliente(){
	Cliente client = new Cliente(cliente.getNomeCliente(),cliente.getCpf_cnpj(),cliente.getEndereco(),cliente.getFone());
    return client;
  }

  public ArrayList<Movimentacao> getMovimentacoes(){
    return movimentacoes;
  }

  public int getProximoNumConta(){
    return proximoNumConta;
  }

  public void debito(double valor, String descricao){
    if (this.saldo >= valor){
      Movimentacao movimentacaoDebito = new Movimentacao(descricao, 'D', valor);
      this.movimentacoes.add(movimentacaoDebito);
      this.saldo -= valor;
    }else{
      System.out.println("O saldo atual não é suficiente.");
    }
  }

  public void credito(double valor, String descricao){
    Movimentacao movimentacaoCredito = new Movimentacao(descricao, 'C', valor);
    this.movimentacoes.add(movimentacaoCredito);
    this.saldo += valor;
  }

  public ArrayList<Movimentacao> extratoDatado(GregorianCalendar inicio, GregorianCalendar fim){
    ArrayList<Movimentacao> listaRetorno = new ArrayList<Movimentacao>();
    for (int i = 0; i < this.movimentacoes.size(); i++){
    	 if (((inicio.compareTo(this.movimentacoes.get(i).getData()))< 0) && ((fim.compareTo(this.movimentacoes.get(i).getData()))> 0)){
        listaRetorno.add(this.movimentacoes.get(i));
      }
    }
    return listaRetorno;
  }

  public ArrayList<Movimentacao> extratoPartindo(GregorianCalendar inicio){
    GregorianCalendar fim = new GregorianCalendar();
    ArrayList<Movimentacao> listaRetorno = new ArrayList<Movimentacao>();
    for (int i = 0; i < this.movimentacoes.size(); i++){
      if (((inicio.compareTo(this.movimentacoes.get(i).getData()))< 0) && ((fim.compareTo(this.movimentacoes.get(i).getData()))> 0)){
        listaRetorno.add(this.movimentacoes.get(i));
      }
    }
    return listaRetorno;
  }

  public ArrayList<Movimentacao> extratoMes(){
    GregorianCalendar atual = new GregorianCalendar();
    ArrayList<Movimentacao> listaRetorno = new ArrayList<Movimentacao>();
    for (int i = 0; i < this.movimentacoes.size(); i++){
      GregorianCalendar referencia = this.movimentacoes.get(i).getData();
      if ((referencia.get(Calendar.MONTH) == atual.get(Calendar.MONTH)) && (referencia.get(Calendar.YEAR) == atual.get(Calendar.YEAR))){
        listaRetorno.add(this.movimentacoes.get(i));
      }
    }
    return listaRetorno;
  }
  
}