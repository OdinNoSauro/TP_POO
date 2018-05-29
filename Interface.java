package tp;

import banco.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

public class Interface {
	private static Banco bs = new Banco ("Banco do Sertão");
	
	public static void newCliente() {
		Scanner scanner = new Scanner(System.in);
		String nome, cpf, endereco, fone;
		System.out.println("Insira o nome do cliente");
		nome = scanner.nextLine();
		System.out.println("Insira o cpf ou cnpj do cliente");
		cpf = scanner.nextLine();
		System.out.println("Insira o endereço do cliente");
		endereco = scanner.nextLine();
		System.out.println("Insira o telefone do cliente");
		fone = scanner.nextLine();
		Cliente cliente = new Cliente(nome,cpf,endereco,fone);
		Interface.bs.addCliente(cliente);
	}
	
	public static void newConta () {
		Scanner scanner = new Scanner(System.in);
		ArrayList<Cliente> clientes =Interface.bs.getListaClientes();
		String cpf;
		char op;
		System.out.println("Cliente já cadastrado? <S/N>");
		op = scanner.next().charAt(0);
		scanner.nextLine();
		if (op == 'S' || op == 's') {
			System.out.print("Insira o cpf ou o cnpj do cliente: ");
			cpf = scanner.nextLine();
			int indice = Interface.bs.findClient(cpf);
			if(indice == -1)
				System.out.println("Cliente não cadastrado");
			else 
				Interface.bs.newConta(clientes.get(indice));

		}
		else if (op == 'N' || op == 'n') {
			newCliente();
			clientes =Interface.bs.getListaClientes();
			int indice = clientes.size() - 1;
			Interface.bs.newConta(clientes.get(indice));
		}
		System.out.println("Conta criado com sucesso");
	}
	
	public static void deleteCliente () {
		String cpf;
		Scanner scanner = new Scanner(System.in);
		System.out.print("Insira o cpf ou cnpj do cliente que deseja escluir: ");
		cpf = scanner.nextLine();
		Interface.bs.deleteCliente(cpf);
	}
	public static void deleteConta (int cont) {
		int sucesso = Interface.bs.deleteConta(cont);
		if (sucesso == -1)
			System.out.println("Conta inexistente");
		else if(sucesso == 1)
			System.out.println("Conta deletada com sucesso");
		else if (sucesso == 0)
			 System.out.println("Cliente  possui conta");
	}
	
	public static void deposito (int cont, double valor) {
		int sucesso = Interface.bs.deposito(cont, valor);
		if (sucesso == -1)
			System.out.println("Conta inexistente");
		else 
			System.out.println("Deposito realizado com sucesso");
	}
	public static void saque (int cont, double valor) {
		int sucesso = Interface.bs.saque(cont, valor);
		if (sucesso == -1)
			System.out.println("Conta inexistente");
		else 
			System.out.println("Saque realizado com sucesso");
	}
	public static void transferencia (int origem, int destino, double valor) {
		int sucesso = Interface.bs.transferencia(origem, destino, valor);
		if (sucesso == -1)
			System.out.println("Conta inexistente");
		else 
			System.out.println("Tranferência realizada com sucesso");
	}
	public static void tarifa () {
		Interface.bs.cobraTarifa();
	}
	public static void cobraCPMF () {
		Interface.bs.cobrarCPMF();
	}
	public static void saldo (int cont) {
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("R$ #,##0.00");
		double saldo = Interface.bs.saldo(cont);
		if (saldo != -1) {
			System.out.print("Seu saldo é: ");
			System.out.println(df.format(saldo));
		}
		else
			System.out.println("Conta inexistente");
	}
	public static ArrayList<Movimentacao> extrato (int numConta) {
		int opc;
		int dia, mes, ano;
		System.out.println("Escolha a opção de extrato: ");
		System.out.println("(1) Extrato do último mês ");
		System.out.println("(2) Extrato a partir de uma data ");
		System.out.println("(3) Extrato de um período personalizado");
		Scanner scanner = new Scanner(System.in);
		opc = scanner.nextInt();
		if (opc == 1) {
			return Interface.bs.extratoMesAtual(numConta);
		}
		else if (opc == 2) {
			System.out.print("Insira o dia: ");
			dia = scanner.nextInt();
			System.out.print("Insira o mês: ");
			mes = scanner.nextInt();
			System.out.print("Insira o ano: ");
			ano = scanner.nextInt();
			GregorianCalendar inicio = new GregorianCalendar(ano,mes,dia);
			return Interface.bs.extratoDataInicial(numConta, inicio);
		}
		else if (opc == 3) {
			System.out.print("Insira o dia do inicio: ");
			dia = scanner.nextInt();
			System.out.print("Insira o mês do inicio: ");
			mes = scanner.nextInt();
			System.out.print("Insira o ano do inicio: ");
			ano = scanner.nextInt();
			GregorianCalendar inicio = new GregorianCalendar(ano,mes,dia);
			System.out.print("Insira o dia do fim: ");
			dia = scanner.nextInt();
			System.out.print("Insira o mês do fim: ");
			mes = scanner.nextInt();
			System.out.print("Insira o ano do fim: ");
			ano = scanner.nextInt();
			GregorianCalendar fim = new GregorianCalendar(ano,mes,dia);
			return Interface.bs.extratoEntreDatas(numConta, inicio, fim);
		}else
			return null;
	}
	
	public static void showClientes() {
		System.out.println("Lista de clientes: ");
		ArrayList<Cliente> lista = Interface.bs.getListaClientes();
		for(int i = 0; i<lista.size();i++) {
			System.out.println(lista.get(i).getNomeCliente());
			System.out.println(lista.get(i).getCpf_cnpj());
			System.out.println(lista.get(i).getEndereco());
			System.out.println(lista.get(i).getFone());
			System.out.print("\n");
		}
	}
	
	public static void showContas() {
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("R$ #,##0.00");
		System.out.println("Lista de contas: ");
		ArrayList<Conta> lista = Interface.bs.getListaContas();
		for(int i = 0; i<lista.size();i++) {
			System.out.println("Numero da conta: "+lista.get(i).getNumConta());
			System.out.println("Cliente: "+lista.get(i).getCliente().getNomeCliente());
			System.out.println("CPF/CNPJ: "+lista.get(i).getCliente().getCpf_cnpj());
			System.out.println("Endereço: "+lista.get(i).getCliente().getEndereco());
			System.out.println("Telefone: "+lista.get(i).getCliente().getFone());
			System.out.println("Saldo: "+df.format(lista.get(i).getSaldo()));
			System.out.print("\n");
		}
	}
	
	public static void lerArq(String nomeArq) {
		Interface.bs.lerArquivo(nomeArq);
	}
	
	public static void escreveArq(String nomeArq) {
		Interface.bs.escreveArquivo(nomeArq);
	}
	
	 public static void main (String[] argv) {
		 char continuar = 'S'; 
		 lerArq("Dados.txt");
		 Locale.setDefault(new Locale("pt","BR"));
		 Scanner scanner = new Scanner(System.in);
		 double valor;
		 do {
			  System.out.println("===================Selecione um opção=================");
			  System.out.println("(1) Saque                        (2) Depósito");
			  System.out.println("(3) Tranferência                 (4) Saldo");
			  System.out.println("(5) Cadastrar um cliente         (6) Excluir um cliente");
			  System.out.println("(7) Cria uma conta         	   (8) Excluir uma conta");
			  System.out.println("(9) Listar clientes	           (10) Listar contas");
			  System.out.println("(11) Cobrar tarifa 	           (12) Cobrar CPMF");
			  System.out.println("(13) Extrato");
			  int opcao = scanner.nextInt();
			  //scanner.nextLine();
			  if (opcao == 1) {
				  System.out.println("Insira o número da sua conta");
				  int conta = scanner.nextInt();
				  System.out.println("Insira o valor que deseja sacar");
				  valor = scanner.nextDouble();
				  saque(conta,valor);
			  }
			  else if (opcao == 2 ) {
				  System.out.println("Insira o número da sua conta");
				  int conta = scanner.nextInt();
				  System.out.println("Insira o valor que deseja depositar");
				  valor = scanner.nextDouble();
				  deposito(conta,valor);
			  }
			  else if (opcao == 3) {
				  System.out.println("Insira o número da sua conta");
				  int conta = scanner.nextInt();
				  System.out.println("Insira o valor que deseja tranferir");
				  valor = scanner.nextDouble();
				  System.out.println("Insira o número da conta de destino");
				  int conta2 = scanner.nextInt();
				  transferencia(conta,conta2,valor);
			  }
			  else if(opcao == 4) {
				  System.out.println("Insira o número da sua conta");
				  int conta = scanner.nextInt();
				  saldo(conta);
			  }
			  else if (opcao == 5) {
				  newCliente();
				  
			  }
			  else if (opcao == 6) {
				  deleteCliente();
			  }
			  else if (opcao == 7) {
				  newConta();
			  }
			  else if (opcao == 8) {
				  System.out.println("Insira o numero da conta que deseja deletar");
				  int cont = scanner.nextInt();
				  deleteConta(cont);
			  }
			  else if (opcao == 9) {
				  showClientes();
			  }
			  else if (opcao == 10) {
				  showContas();
			  }
			  else if (opcao == 11) {
				  tarifa();
			  }
			  else if (opcao == 12) {
				  cobraCPMF();
			  }
			  else if (opcao == 13) {
				  System.out.println("Insira o número da sua conta");
				  int conta = scanner.nextInt();
				  extrato(conta);
			  }
		System.out.println("Deseja realizar outra opreração?");
		continuar = scanner.next().charAt(0);
		}while((continuar == 'S') || (continuar == 's'));
		System.out.println("Obrigado. Volte sempre!");
		escreveArq("Dados.txt");
		scanner.close();
	 }
}
