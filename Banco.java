package banco;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Banco{
  private String nomeBanco;
  private ArrayList <Cliente> clientes;
  private ArrayList <Conta> contas;

  public Banco(String nomeBanco){
    this.nomeBanco = nomeBanco;
    this.clientes = new ArrayList<Cliente>();
    this.contas =  new ArrayList<Conta>();
  }

  public int getConta(int numeroConta){
    for (int i = 0; i < this.contas.size(); i++){
      if (this.contas.get(i).getNumConta() == numeroConta) {
        return i;
      }
    }
    return -1;
  }

  public void addCliente(Cliente cliente){
    this.clientes.add(cliente);
  }

  public void newConta(Cliente cliente){
    Conta conta = new Conta(cliente);
    this.contas.add(conta);
  }

  public int findClient(String documento) {
	  for (int i = 0; i < this.clientes.size(); i++){
      if (this.clientes.get(i).getCpf_cnpj().equals(documento)){
        return i;
	    }
	  }
      return -1;
  }

  public int deleteCliente(String cpf_cnpj){
   int indiceCliente = findClient(cpf_cnpj);
   if (indiceCliente == -1)
	  return -1;
    for (int i = 0; i < this.contas.size(); i++){
      if (this.contas.get(i).cliente.getCpf_cnpj().equals(cpf_cnpj)){
        return 0;
      }
    }
    this.clientes.remove(indiceCliente);
    return 1;
  }

  public int deleteConta(int numeroConta){
    int indiceConta = getConta(numeroConta);
    if (indiceConta == -1)
		  return -1;
    this.contas.remove(indiceConta);
    return 1;
  }

  public int deposito(int numeroConta, double valor){
    int indiceConta = getConta(numeroConta);
    if(indiceConta == -1)
    	return -1;
    this.contas.get(indiceConta).credito(valor, "Deposito");
    return 1;
  }

  public int saque(int numeroConta, double valor){
    int indiceConta  = getConta(numeroConta);
    if (indiceConta == -1)
    	return -1;
    this.contas.get(indiceConta).debito(valor, "Saque");
    return 1;
  }

  public int transferencia(int contaOrigem, int contaDestino, double valor){
    int origem  = getConta(contaOrigem);
    int destino = getConta(contaDestino);
    if (origem == -1 || destino == -1)
    	return -1;
    String mensagemOrigem = "Transferencia para a conta " + destino;
    String mensagemDestino = "Transferencia da conta " + origem;
    this.contas.get(contaOrigem).debito(valor, mensagemOrigem);
    this.contas.get(contaDestino).credito(valor, mensagemDestino);
    return 1;
  }

  public void cobraTarifa(){
    for (int i = 0; i < this.contas.size(); i++){
      this.contas.get(i).debito(15, "Cobranca de tarifa");
    }
  }

  public void cobrarCPMF(){
  	GregorianCalendar now = new GregorianCalendar();
  	GregorianCalendar weekago = new GregorianCalendar();
  	weekago.add(Calendar.DAY_OF_MONTH,-7);
  	for (int i = 0; i < this.contas.size(); i++){
  	  double montante = 0;
  	  ArrayList<Movimentacao> extrato = this.contas.get(i).extratoDatado(weekago,now);
  	  for(int j = 0; j< extrato.size();j++) {
  		  if (extrato.get(j).getTipo() == 'D')
  		  montante += extrato.get(j).getValor();
  	  }
  	  double tarifa = 0.0038*montante;
  	  this.contas.get(i).debito(tarifa, "Cobranca de CPMF");
  	}
  }

  public double saldo(int numeroConta){
    int indiceConta = getConta(numeroConta);
    if (indiceConta == -1) {
    	return -1;
    }
    return this.contas.get(indiceConta).getSaldo();
  }

  public ArrayList<Movimentacao> extratoMesAtual(int numeroConta){
    int indiceConta = getConta(numeroConta);
    return this.contas.get(indiceConta).extratoMes();
  }

  public ArrayList<Movimentacao> extratoDataInicial(int numeroConta, GregorianCalendar inicio){
    int indiceConta = getConta(numeroConta);
    return this.contas.get(indiceConta).extratoPartindo(inicio);
  }

  public ArrayList<Movimentacao> extratoEntreDatas(int numeroConta, GregorianCalendar inicio, GregorianCalendar fim){
    int indiceConta = getConta(numeroConta);
    return this.contas.get(indiceConta).extratoDatado(inicio,fim);
  }

  public ArrayList<Cliente> getListaClientes(){
    return this.clientes;
  }

  public ArrayList<Conta> getListaContas(){
    return this.contas;
  }

  public void lerArquivo(String nomeArquivo) {
	  String nome, linha,documento,endereco,fone, titular,descricao;
	  double saldo, valor;
	  char tipo;
	  int numConta, indice,dia,mes,ano;
	  String[] dados,data;
	  try {
		  FileReader arquivo = new FileReader(nomeArquivo);
		  BufferedReader lerArq = new BufferedReader(arquivo);
		  linha = lerArq.readLine();
		  System.out.println(linha);

		  if (linha.equals("Clientes")) {
			  linha = lerArq.readLine();
			  System.out.println(linha);

			  while (!linha.equals("Contas")) {

				  System.out.println(linha);
				  linha = lerArq.readLine();
				  System.out.println(linha);
				  nome = linha;

				  linha = lerArq.readLine();
				  documento = linha.substring(10);

				  linha = lerArq.readLine();
				  endereco = linha.substring(10);

				  linha = lerArq.readLine();
				  fone = linha.substring(10);

				  Cliente client = new Cliente(nome,documento,endereco,fone);
				  this.addCliente(client);

				  linha = lerArq.readLine();
			  }
		  }
		  else if (linha.equals("Contas")) {
			  linha = lerArq.readLine();
			  while(!linha.equals("Fim")) {
				  linha = lerArq.readLine();
				  titular = linha.substring(9);

				  linha = lerArq.readLine();
				  documento = linha.substring(10);

				  linha = lerArq.readLine();
				  numConta = Integer.parseInt(linha.substring(7));

				  linha = lerArq.readLine();
				  saldo = Double.parseDouble(linha.substring(7));

				  indice = findClient(documento);

				  Conta cont = new Conta(this.clientes.get(indice),numConta,numConta+1,saldo);

				  linha = lerArq.readLine();
				  if (linha.equals("Movimentações")) {
					  linha = lerArq.readLine();
					  while(!linha.isEmpty()) {
						  dados = linha.split(",");
						  descricao = dados[0];
						  valor = Double.parseDouble(dados[1].substring(9));
						  tipo = dados[2].substring(1).charAt(0);
						  data = dados[3].substring(7).split("/");
						  dia = Integer.parseInt(data[0]);
						  mes = Integer.parseInt(data[1]);
						  ano = Integer.parseInt(data[2]);

						  Movimentacao mov = new Movimentacao(descricao,tipo,valor,dia,mes,ano);

						  cont.getMovimentacoes().add(mov);

						  linha = lerArq.readLine();
					  }
				  }
				  this.contas.add(cont);
				  linha = lerArq.readLine();
			  }
		  }
		  lerArq.close();
	  } catch (IOException e) {
		  System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
	  }
  }

  public void escreveArquivo (String nomeArquivo) {
	  String linha,nome,documento,endereco,fone, titular,descricao;
	  double saldo, valor;
	  char tipo;
	  int numConta, indice;
	  String dia,mes,ano;


	  try {
		  FileWriter arquivo = new FileWriter(nomeArquivo);
		  BufferedWriter escreverArq = new BufferedWriter(arquivo);

		  escreverArq.write("Clientes");
		  escreverArq.write("\n");
		  escreverArq.write("\n");

		  for(int i = 0; i < this.getListaClientes().size(); i++) {
			  escreverArq.write("Cliente: "+this.getListaClientes().get(i).getNomeCliente());
			  escreverArq.write("\n");

			  escreverArq.write("CPF/CNPJ: "+this.getListaClientes().get(i).getCpf_cnpj());
			  escreverArq.write("\n");

			  escreverArq.write("Endereço: "+this.getListaClientes().get(i).getEndereco());
			  escreverArq.write("\n");

			  escreverArq.write("Telefone: "+this.getListaClientes().get(i).getFone());
			  escreverArq.write("\n");

			  escreverArq.write("\n");

		  }

		  escreverArq.write("Contas");
		  escreverArq.write("\n");
		  escreverArq.write("\n");

		  for(int j = 0; j < this.getListaContas().size(); j++) {
			  escreverArq.write("Titular: "+this.getListaContas().get(j).getCliente().getNomeCliente());
			  escreverArq.write("\n");

			  escreverArq.write("CPF/CNPJ: "+this.getListaContas().get(j).getCliente().getCpf_cnpj());
			  escreverArq.write("\n");

			  escreverArq.write("Conta: "+this.getListaContas().get(j).getNumConta());
			  escreverArq.write("\n");

			  escreverArq.write("Saldo: "+this.getListaContas().get(j).getSaldo());
			  escreverArq.write("\n");

			  escreverArq.write("Movimentações");
			  escreverArq.write("\n");
			  for (int i = 0; i < this.getListaContas().get(j).getMovimentacoes().size(); i++) {
				  escreverArq.write(this.getListaContas().get(j).getMovimentacoes().get(i).getDescricao()+", ");

				  escreverArq.write("Valor = "+this.getListaContas().get(j).getMovimentacoes().get(i).getValor()+", ");

				  escreverArq.write(this.getListaContas().get(j).getMovimentacoes().get(i).getTipo()+", ");

				  GregorianCalendar data = this.getListaContas().get(j).getMovimentacoes().get(i).getData();
				  dia = Integer.toString(data.get(Calendar.DATE));
				  mes = Integer.toString(data.get(Calendar.MONTH));
				  ano = Integer.toString(data.get(Calendar.YEAR));
				  escreverArq.write("Data: "+dia+"/"+mes+"/"+ano);

				  escreverArq.write("\n");
			  }
		  }
		  escreverArq.write("Fim");
		  escreverArq.close();
	  }catch(IOException e) {
		  System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
	  }
  }
}
