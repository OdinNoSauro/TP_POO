package banco;

import java.util.GregorianCalendar;


public class Movimentacao{
	private GregorianCalendar dataMov;
	private String descricao;
	private char debitoCredito;
	private double valor;

	public Movimentacao(String desc, char DC, double val){
		this.dataMov = new GregorianCalendar();
		this.descricao = new String(desc);
		this.debitoCredito = DC;
		this.valor = val;
	}
	public Movimentacao(String desc, char DC, double val, int dia, int mes, int ano){
		this.dataMov = new GregorianCalendar(ano,mes,dia);
		this.descricao = new String(desc);
		this.debitoCredito = DC;
		this.valor = val;
	}
	public GregorianCalendar getData() {
		return dataMov;
	}
	public double getValor() {
		return valor;
	}
	public String getDescricao() {
		return descricao;
	}
	public char getTipo() {
		return debitoCredito;
	}
}