package br.ufrj.dcc.so.controle;

import java.io.IOException;
import java.net.Socket;

import br.ufrj.dcc.so.entidade.Conexao;
import br.ufrj.dcc.so.entidade.Requisicao;
 

public class Servidor extends Thread{
	
	/*
	 * Variaveis
	 */
	private Socket socket;
	Conexao conexao;
	
	/*
	 * Construtor
	 */
	public Servidor(Socket s) {
		socket = s;		  
	} 
	
	public void run() {
			
		conexao = new Conexao(socket);		
		try 
		{
			Requisicao requisicao = conexao.lerRequisicao();				
			requisicao.executar(ControleArquivo.Create());					
			conexao.escreverRequisicao(requisicao);	
				
			socket.close();
			
		}catch(IOException e){  
			System.out.println("Erro no encerramento do socket.");
		}			
	}
} 