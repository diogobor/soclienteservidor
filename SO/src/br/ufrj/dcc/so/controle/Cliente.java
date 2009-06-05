package br.ufrj.dcc.so.controle;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import br.ufrj.dcc.so.entidade.*;

public class Cliente extends Thread  {
	
	//Variaveis
	String servidor = "";
	int porta = 0;
	Conexao conexao;
	Socket socket;
	Requisicao requisicao;
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
	
	/*
	private void enviarArquivoServidor() throws FileNotFoundException,
			IOException {
		System.out.println("==== Envia arquivo para o Servidor ====");
		conexao.escreverLinha("enviarArquivoServ");

		
		
		DataOutputStream dadoSaida = new DataOutputStream (s.getOutputStream());
		 // abrir arquivo para o envio  
		FileInputStream arquivoEntrada = new FileInputStream ("F:\\Diogo\\so.txt");
		DataInputStream dadoEntrada = new DataInputStream (arquivoEntrada);

		// cria um buffer de 1024 bytes para o envio 
		byte buffer[] = new byte[1024];            

		// envia os dados :) 
		while (dadoEntrada.read(buffer) != -1)
			dadoSaida.write(buffer,0,buffer.length);
		dadoSaida.close();
		arquivoEntrada.close();
		//in2.close();
		System.out.println("==== T�rmino ====");
		
	}

	private void receberArquivo() throws FileNotFoundException, IOException {
		System.out.println("==== Recebe arquivo do Servidor ====");
		conexao.escreverLinha("receberArquivo");
		
		
		conexao.escrever("diogo.txt");
		
		InputStream in2 = s.getInputStream();  
		FileOutputStream fileOut = new FileOutputStream("F:\\Diogo\\so.txt");  
		byte data[] = new byte[1024]; 
		int size;  
		while ((size = in2.read(data)) != -1)  
		{  
		    fileOut.write(data, 0, size);  
		    fileOut.flush();
		}  
		fileOut.close();
		System.out.println("==== T�rmino ====");
		
	}

	private void renomearArquivo() {
		System.out.println("==== Obtem informacao do arquivo no Servidor ====");
		conexao.escreverLinha("renomearArquivo");
		//conexao.escrever("teste.fla");
		//conexao.escrever("funcionaaaaa.fla");
		System.out.println("==== Termino ====");
	}

	private void informacoesArquivo() {
		System.out.println("==== Obtem informacao do arquivo no Servidor ====");
		conexao.escreverLinha("infArquivo");
		//conexao.escrever("teste.fla");
		System.out.println("==== Termino ====");
	}

	private void apagarArquivoExtensao() {
		System.out.println("==== Apaga arquivo no Servidor ====");
		conexao.escreverLinha("apagarArquivoExtensao");
		//conexao.escrever("txt");
		System.out.println("==== Termino ====");
	}

	private void apagarArquivo() {
		System.out.println("==== Apaga arquivo no Servidor ====");
		conexao.escreverLinha("apagarArquivo");
		//conexao.escrever("teste2.txt");
		System.out.println("==== Termino ====");
	}	
	
	private void enviarArquivo() {
		System.out.println("==== Envia arquivo para Servidor ====");
		Funcoes.detectarFuncao(1, Funcoes.ENDERECOSERVIDOR, null);
		System.out.println("==== Termino ====");
	}

	private void listarDiretorioLocal() {
		System.out.println("==== Lista Diretorio Local ====");
		Funcoes.detectarFuncao(0, "/home/diogobor", null);
		System.out.println("==== Termino ====");
	}

	private void primeiraConexao() {			
			String bemVindo = conexao.lerLinha();        
			System.out.println("Servidor: " + bemVindo);
	}
	
	//TODO:Esse metodo nao deve estar nesta classe pois mexe com tela
	// deve estar na tela, quando obter sucesso na conexao (evento de conectar)
	private void inicializarTela() {
		PainelPrincipal.situacaoServidor = "Conectado !";
		Comecar.painelFundo.repaint();
		BarraDeMenu.menuConectarServidor.setEnabled(false);
	}
	*/

}
