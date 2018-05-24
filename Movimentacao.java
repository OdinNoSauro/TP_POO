package TP;

import java.util.Calendar;

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
	public static void main(String[] args) {
		Movimentacao number = new Movimentacao("compra de um carro",'C',32710.00);
		double i = number.getValor();
		char c = number.getTipo();
		String s = number.getDescricao();
		GregorianCalendar data = number.getData();
		System.out.println(i+"\n"+c+"\n"+s);
		System.out.println("\n"+data.get(Calendar.YEAR));
	}
}
