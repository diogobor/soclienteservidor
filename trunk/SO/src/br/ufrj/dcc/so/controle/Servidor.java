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
	
	
	

	/*
	private void enviarArqServ() {
		System.out.println("==== Envia arquivo para o Servidor ====");	
		
		
		DataInputStream dadoEntrada = new DataInputStream (socket.getInputStream());
		 // abrir arquivo para o envio  
		FileOutputStream arquivoSaida = new FileOutputStream ("F:\\DVD\\soAgora.txt");
		DataOutputStream dadoSaida = new DataOutputStream (arquivoSaida);

		// cria um buffer de 1024 bytes para o envio 
		byte buffer[] = new byte[1024];            

		// envia os dados :) 
		while (dadoEntrada.read(buffer) != -1)
			dadoSaida.write(buffer,0,buffer.length);
		//dadoSaida.close();
		dadoEntrada.close();
		arquivoSaida.close();
		
		
		System.out.println("==== T�rmino ====");
		//conexao = null;
	}	
	
	private void receberArquivo() {
		System.out.println("==== Recebe arquivo do Servidor ====");
		System.out.println("Cliente: " + socket.getInetAddress());
		
		
		FileInputStream fileIn = new FileInputStream(Funcoes.ENDERECOSERVIDOR + "\\" + conexao.ler());                      
		OutputStream out = socket.getOutputStream();  
		byte data[] = new byte[1024]; 
		int size;  
		while ((size = fileIn.read(data)) != -1)  
		{  
		    out.write(data, 0, size);  
		    out.flush();  
		}   
		fileIn.close(); 
		out.close(); 
		 
     
		System.out.println("==== T�rmino ====");		
	}	*/
} 