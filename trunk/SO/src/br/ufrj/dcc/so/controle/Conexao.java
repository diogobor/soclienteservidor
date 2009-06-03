package br.ufrj.dcc.so.controle;

import java.net.Socket;
import java.io.*;

public class Conexao {
	
	private Socket socket;
	private BufferedReader tela;
	private BufferedReader entrada;
	private PrintStream saida;	
	
	public Conexao(Socket s){
	
		this.socket = s;
		try
		{
			tela = new BufferedReader(new InputStreamReader(System.in));
			//Cria um BufferedReader para o canal da stream de entrada de dados do socket s  
			entrada = new BufferedReader(new InputStreamReader(s.getInputStream()));
			//Cria a Stream de saida de dados com o socket
			saida = new PrintStream(s.getOutputStream());			
		}
		catch(IOException e){ 
			System.out.println("Algum problema ocorreu ao criar ou enviar dados pelo socket.");
		}		
	}
	public Conexao(){
		
	}
	
	public String ler(){
		String result= "";
		
		try
		{
			result = entrada.readLine();
		}
		catch(IOException e){ 
			System.out.println("Nao foi possivel ler o dado.");
		}		
		return result;
	}
	
	public void escrever(String s){
		
		saida.println(s);		
	}

}
