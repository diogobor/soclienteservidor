package br.ufrj.dcc.so.controle;

import java.io.IOException;
import java.net.Socket;
 
/**
 * Classe do Servidor
 * @author Diogo e Aline
 *
 */
public class Servidor extends Thread{
	
	private Socket socket;
	Conexao conexao;
	//private ObjectOutputStream saida_arquivo ;  
	//private ObjectInputStream entrada_arquivo ;
	
	/**
	 * construtor da classe
	 * @param s
	 */
	public Servidor(Socket s) {
		socket = s;		  
	}    
    
    // execucao da thread
	public void run() {
		try {
			
			conexao = new Conexao(socket);
			 
			 conexao.escrever("Conexao estabelecida com o Cliente !");
						
			String mensagem ="";
			System.out.println("Cliente:" + socket.getInetAddress().getHostName());
			  
			//Aguarda por algum dado e imprime a linha recebida quando recebe  
				try {					
					mensagem = conexao.ler();
					System.out.println(mensagem);
				
					while(!mensagem.equals("exit")){
						if (mensagem.equals("listarDir")){
							listarDiretorio();
						}
						else if (mensagem.equals("apagarArquivo")){
							apagarArquivo();
						}
						else if (mensagem.equals("apagarArquivoExtensao")){
							apagarArquivoExtensao();
						}
						else if (mensagem.equals("infArquivo")){
							informacaoArquivo();
						}
						else if (mensagem.equals("renomearArquivo")){
							renomearArquivo();
						}
						else if (mensagem.equals("receberArquivo")){
							receberArquivo();
						}
						else if (mensagem.equals("enviarArquivoServ")){
							enviarArqServ();
						}
						
						mensagem = conexao.ler();
						System.out.println(mensagem);
				}		
				} catch (Exception e) {
					System.out.println("Cliente -" + socket.getInetAddress().getHostName() + "- encerrou sessao !");
				}
     
			//Encerro o socket de comunicacao  
			socket.close();  
			                       
			}catch(IOException e){  
				System.out.println("Erro de leitura ou escrita no arquivo no Servidor!");
			}
			
	}

	private void enviarArqServ() {
		System.out.println("==== Envia arquivo para o Servidor ====");	
		
		/*
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
		*/
		
		System.out.println("==== T�rmino ====");
		//conexao = null;
	}

	private void receberArquivo() {
		System.out.println("==== Recebe arquivo do Servidor ====");
		System.out.println("Cliente: " + socket.getInetAddress());
		
		/*
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
		*/ 
     
		System.out.println("==== T�rmino ====");
	}

	private void renomearArquivo() {
		System.out.println("==== Renomeia arquivo no Servidor ====");
		System.out.println("Cliente: " + socket.getInetAddress());
		//Funcoes.detectarFuncao(3, conexao.ler(), conexao.ler());
		System.out.println("==== T�rmino ====");
	}

	private void informacaoArquivo() {
		System.out.println("==== Obtem informacao do arquivo no Servidor ====");
		System.out.println("Cliente: " + socket.getInetAddress());
		//Funcoes.detectarFuncao(6, conexao.ler(), null);
		System.out.println("==== T�rmino ====");
	}

	private void apagarArquivoExtensao() {
		System.out.println("==== Apaga arquivo por Extensao no Servidor ====");
		System.out.println("Cliente: " + socket.getInetAddress());
		//Funcoes.detectarFuncao(5, conexao.ler(), null);
		System.out.println("==== T�rmino ====");
	}

	private void apagarArquivo() {
		System.out.println("==== Apaga arquivo no Servidor ====");
		System.out.println("Cliente: " + socket.getInetAddress());
		//Funcoes.detectarFuncao(4,conexao.ler() , null);
		System.out.println("==== T�rmino ====");
	}

	private void listarDiretorio() {
		System.out.println("==== Lista Diretorio Servidor ====");
		System.out.println("Cliente: " + socket.getInetAddress());
		//Funcoes.detectarFuncao(0, Funcoes.ENDERECOSERVIDOR, null);
		System.out.println("==== T�rmino ====");
	}  
} 