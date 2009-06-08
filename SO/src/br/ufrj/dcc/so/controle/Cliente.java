package br.ufrj.dcc.so.controle;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import br.ufrj.dcc.so.entidade.*;

public class Cliente extends Thread  {
	
	//Variaveis
	private String servidor = "";
	private int porta = 0;
	private Conexao conexao;
	private Socket socket;
	private Requisicao requisicao;
	//public static String mensagem = "";
	
	//Construtor
	public Cliente (String servidor, int porta, Requisicao requisicao){
		this.servidor = servidor;
		this.porta = porta;
		this.requisicao = requisicao;		
    }
	
	//metodos publicos
	public void run() {			
		try{
			socket = new Socket(servidor, porta);
			conexao=  new Conexao(socket);
					
			requisicao.setCliente(socket.getInetAddress().getHostAddress());
					
			conexao.escreverRequisicao(requisicao);			
			requisicao = conexao.lerRequisicao();
			
        } 
		catch (UnknownHostException e) {        		
			requisicao.getErros().add("Nao foi encontrado o servidor.");			
        } 
		catch (IOException e) {        		
        	requisicao.getErros().add("Nao foi encontrado o servidor.");			
        }
        finally{
        	try{
        		if(!socket.isClosed()) socket.close(); 
        	}
        	catch(Exception e){
                requisicao.getErros().add("Erro ao fechar o arquivo !");
            } 
        }
	}

	public Requisicao getRequisicao()
	{
		return requisicao;
	}
}
