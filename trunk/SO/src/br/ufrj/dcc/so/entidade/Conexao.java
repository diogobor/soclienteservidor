package br.ufrj.dcc.so.entidade;

import java.net.*;
import java.io.*;

public class Conexao {
	
	/*
	 * Variaveis
	 */
	public static Socket socket;
	//private BufferedReader tela;	
	private ObjectInputStream entrada = null;	
	private ObjectOutputStream saida = null;	
	
	/*
	 * Construtor
	 */
	public Conexao(Socket s){	
		this.socket = s;
	}
	
	/*
	 * Le uma string do servidor/cliente
	 */
	public String lerLinha(){
		String result= "";		
		try
		{	
			criarEntradaDados();			
			result = (String)entrada.readObject();
			//fecharEntradaDados();			
		}
		catch (ClassNotFoundException e) {
			System.out.println("Dado incorreto enviado pelo servidor");
		}
		catch(IOException e){ 
			System.out.println("Nao foi possivel ler o dado.");
		}		
		return result;
	}	
	
	/*
	 * Envia uma string para o servidor/cliente
	 */
	public void escreverLinha(String s){
		
		try 
		{
			criarSaidaDados();			
			saida.writeObject(s);
			//fecharSaidaDados();
		} 
		catch (IOException e) {
			System.out.println("Ocorreu um erro na tentativa de enviar o dado.");
		}		
	}
	
	/*
	 * Le um objeto Requisicao do servidor/Cliente
	 */
	public Requisicao lerRequisicao(){
		Requisicao req = null;
		try
		{
			criarEntradaDados();
			req = (Requisicao)entrada.readObject();
			//fecharEntradaDados();			
		}
		catch (ClassNotFoundException e) {
			System.out.println("Dado incorreto enviado pelo servidor");
		}
		catch(IOException e){ 
			System.out.println("Ocorreu um erro na tentativa de ler o dado");
		}		
		return req;
	}
	
	/*
	 * Envia um objeto Requisicao para o servidor/Cliente
	 */
	public void escreverRequisicao(Requisicao req){
		try
		{
			criarSaidaDados();
			saida.writeObject(req);
			//fecharSaidaDados();
		}
		catch(IOException e){ 
			System.out.println("Ocorreu um erro na tentativa de enviar o dado.");
		}
	}
	
	private void criarEntradaDados() throws IOException {
		if(entrada== null)
			entrada = new ObjectInputStream(socket.getInputStream());
	}
	
	private void criarSaidaDados() throws IOException {
		if(saida == null)
		saida = new ObjectOutputStream(socket.getOutputStream());
	}
}
