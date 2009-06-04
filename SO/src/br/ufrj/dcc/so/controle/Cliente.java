package br.ufrj.dcc.so.controle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import br.ufrj.dcc.so.vista.BarraDeMenu;
import br.ufrj.dcc.so.vista.Comecar;
import br.ufrj.dcc.so.vista.PainelPrincipal;

/**
 * Classe do Cliente
 * @author SO
 *
 */

public class Cliente extends Thread  {
	
	//Variaveis
	String servidor = "";
	int porta = 0;
	Conexao conexao;
	public static String mensagem = "";
	
	//Construtor
	public Cliente (String servidor, int porta){
		this.servidor = servidor;
		this.porta = porta;
    }
	
	//m�todos publicos
	public void run() {
		executaCliente();
	}
	
	public void executaCliente(){         
		Socket socket = null; 
        try{        	
            //Cria o socket com o recurso desejado na porta especificada  
        	socket = new Socket(servidor, porta);
        	conexao=  new Conexao(socket);
        	
            primeiraConexao();
            
            //TODO:esse metodo nao deveria esta aqui e sim na vista
            inicializarTela();
            
            //TODO:Provavelmente esse metodo vai deixar de existir quando a tela estiver pronta.
            tratarSolicitacao();              
         
        }catch(IOException e){              
            System.out.println("Algum problema ocorreu ao criar ou enviar dados pelo socket.");
            
            //TODO:esse metodo nao deveria esta aqui e sim na vista
        	PainelPrincipal.situacaoServidor = "Erro na conexao !";
            Comecar.painelFundo.repaint();
            BarraDeMenu.menuConectarServidor.setEnabled(true);
          
        }finally{  
              
            try{                  
                //Encerra o socket cliente  
            	socket.close();  
                  
            }catch(Exception e){
            	System.out.println("Erro ao fechar o arquivo !");
            }   	
        }  
	}

	private void tratarSolicitacao()
			throws FileNotFoundException, IOException {
		
		//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));  
        //String mensagem = in.readLine();            
        //msgTela = "";
        
		while(!mensagem.equals("exit")){
			
			if (mensagem.equals("listarDir")){
				listarDiretorioLocal();
			}
			else if (mensagem.equals("listarDirServ")){
				listarDiretorioServidor();
			}
			else if (mensagem.equals("enviarArquivo")){
				enviarArquivo();
			}
			else if (mensagem.equals("apagarArquivo")){
				apagarArquivo();
			}
			else if (mensagem.equals("apagarArquivoExtensao")){
				apagarArquivoExtensao();
			}
			else if (mensagem.equals("infArquivo")){
				informacoesArquivo();
			}
			else if (mensagem.equals("renomearArquivo")){
				renomearArquivo();
			}
			else if (mensagem.equals("receberArquivo")){
				receberArquivo();
			}
			else if (mensagem.equals("enviarArquivoServ")){
				enviarArquivoServidor();
			}
			else{
				System.out.println("Opcao invalida! Digite novamente:");
			}
			//mensagem = in.readLine();			
		}		
	}

	private void enviarArquivoServidor() throws FileNotFoundException,
			IOException {
		System.out.println("==== Envia arquivo para o Servidor ====");
		conexao.escrever("enviarArquivoServ");

		
		/*
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
		*/
	}

	private void receberArquivo() throws FileNotFoundException, IOException {
		System.out.println("==== Recebe arquivo do Servidor ====");
		conexao.escrever("receberArquivo");
		
		/*
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
		*/
	}

	private void renomearArquivo() {
		System.out.println("==== Obtem informacao do arquivo no Servidor ====");
		conexao.escrever("renomearArquivo");
		//conexao.escrever("teste.fla");
		//conexao.escrever("funcionaaaaa.fla");
		System.out.println("==== Termino ====");
	}

	private void informacoesArquivo() {
		System.out.println("==== Obtem informacao do arquivo no Servidor ====");
		conexao.escrever("infArquivo");
		//conexao.escrever("teste.fla");
		System.out.println("==== Termino ====");
	}

	private void apagarArquivoExtensao() {
		System.out.println("==== Apaga arquivo no Servidor ====");
		conexao.escrever("apagarArquivoExtensao");
		//conexao.escrever("txt");
		System.out.println("==== Termino ====");
	}

	private void apagarArquivo() {
		System.out.println("==== Apaga arquivo no Servidor ====");
		conexao.escrever("apagarArquivo");
		//conexao.escrever("teste2.txt");
		System.out.println("==== Termino ====");
	}

	private void listarDiretorioServidor() {
		System.out.println("==== Lista Diretorio do Servidor ====");
		conexao.escrever("listarDir");
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
		String bemVindo = conexao.ler();        
		System.out.println("Servidor: " + bemVindo);
	}
	
	//TODO:Esse metodo nao deve estar nesta classe pois mexe com tela
	// deve estar na tela, quando obter sucesso na conexao (evento de conectar)
	private void inicializarTela() {
		PainelPrincipal.situacaoServidor = "Conectado !";
		Comecar.painelFundo.repaint();
		BarraDeMenu.menuConectarServidor.setEnabled(false);
	}

}
