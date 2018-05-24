package TP;

import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;


public class Conta {
  private int numConta;
  private double saldo;
  private Cliente cliente;
  private List<Movimentacao> movimentacoes = new ArrayList<Movimentacao>();
  private int proximoNumConta;

  public Conta(Cliente cliente){
    this.numConta = this.proximoNumConta;
    this.proximoNumConta += 1;
    this.saldo = 0;
    this.cliente = cliente;
  }

  public int getNumConta(){
    return numConta;
  }

  public double getSaldo(){
    return saldo;
  }

  public Cliente getCliente(){
    return cliente;
  }

  public List<Movimentacao> getMovimentacoes(){
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

  public List<Movimentacao> extratoDatado(GregorianCalendar inicio, GregorianCalendar fim){
    List<Movimentacao> listaRetorno = new ArrayList<Movimentacao>();
    for (int i = 0; i < this.movimentacoes.size(); i++){
      if ((this.movimentacoes.get(i).getData() >= inicio) && (this.movimentacoes.get(i).getData() <= fim)){
        listaRetorno.add(this.movimentacoes.get(i);
      }
    }
    return listaRetorno;
  }

  public List<Movimentacao> extratoPartindo(GregorianCalendar inicio){
    GregorianCalendar fim = new GregorianCalendar();
    List<Movimentacao> listaRetorno = new ArrayList<Movimentacao>();
    for (int i = 0; i < this.movimentacoes.size(); i++){
      if ((this.movimentacoes.get(i).getData() >= inicio) && (this.movimentacoes.get(i).getData() <= fim)){
        listaRetorno.add(this.movimentacoes.get(i));
      }
    }
    return listaRetorno;
  }

  public List<Movimentacao> extratoMes(){
    GregorianCalendar atual = new GregorianCalendar();
    List<Movimentacao> listaRetorno = new ArrayList<Movimentacao>();
    for (int i = 0; i < this.movimentacoes.size(); i++){
      GregorianCalendar referencia = this.movimentacoes.get(i).getData();
      if ((referencia.get(referencia.MONTH) == atual.get(atual.MONTH)) && (referencia.get(referencia.YEAR) == atual.get(atual.YEAR))){
        listaRetorno.add(this.movimentacoes.get(i));
      }
    }
    return listaRetorno;
  }
}
