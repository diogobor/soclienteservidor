package br.ufrj.dcc.so.vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;

import br.ufrj.dcc.so.controle.Cliente;

import br.ufrj.dcc.so.entidade.*;

public class MainConsole {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));  
        String mensagem;
		try {
			mensagem = in.readLine();		
        
			while(!mensagem.equals("exit"))
			{			
        	
	        	if (mensagem.equals("conectar")){
	        		conectarServidor("");
	        	}
				else if (mensagem.equals("desconectar")){
					desconectarServidor();	
				}			
				else if (mensagem.equals("listarDirServ")){				
					listarDiretorio();					
				}
				else if (mensagem.equals("listarDir")){
					//listarDiretorioLocal();
				}
				else if (mensagem.equals("enviarArquivo")){
					//enviarArquivo();
				}
				else if (mensagem.equals("apagarArquivo")){
					//apagarArquivo();
				}
				else if (mensagem.equals("apagarArquivoExtensao")){
					//apagarArquivoExtensao();
				}
				else if (mensagem.equals("infArquivo")){
					//informacoesArquivo();
				}
				else if (mensagem.equals("renomearArquivo")){
					//renomearArquivo();
				}
				else if (mensagem.equals("receberArquivo")){
					//receberArquivo();
				}
				else if (mensagem.equals("enviarArquivoServ")){
					//enviarArquivoServidor();
				}
				else{
					System.out.println("Opcao invalida! Digite novamente:");
				}
        	
	        	mensagem = in.readLine();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Requisicao executarRequisicao(Requisicao req, String enderecoServidor) throws InterruptedException {
		
		Cliente cliente = new Cliente(enderecoServidor, 7000, req);
		cliente.start();
		//espera fim da execucao da thread
		cliente.join();		
		
		return cliente.getRequisicao();
	}

	public static void desconectarServidor() throws InterruptedException{
		
		DesconectarServidor desconectar = new DesconectarServidor();
		
		desconectar = (DesconectarServidor)executarRequisicao(desconectar, null);
		
		if(desconectar.hasErros()){
			System.out.println(desconectar.errosString());
			return;			
		}		
		System.out.println("Cliente desconectado");		
	}	

	public static void conectarServidor(String caminhoServidor) throws InterruptedException{
		
		ConectarServidor conectar = new ConectarServidor();
		
		conectar = (ConectarServidor)executarRequisicao(conectar, caminhoServidor);
		
		if(conectar.hasErros()){
			System.out.println(conectar.errosString());
			return;			
		}		
		System.out.println("Cliente conectado");		
	}
	
	public static void apagarArquivo(String nomeArquivo){
		ApagarArquivo deletaArquivo = new ApagarArquivo();
		deletaArquivo.setNomeArquivo(nomeArquivo);
	}

	public static void listarDiretorio() throws InterruptedException {
		LerDiretorio lerDiretorio = new LerDiretorio();
		lerDiretorio.setCaminho("");
		
		lerDiretorio = (LerDiretorio)executarRequisicao(lerDiretorio, null);
		
		if(lerDiretorio.hasErros()){
			System.out.println(lerDiretorio.errosString());
			return;
		}		
		exibirLeituraDiretorio(lerDiretorio);
	}
	
	public static void exibirLeituraDiretorio(Requisicao requisicao) {
		
		System.out.println("==== Lista Diretorio do Servidor ====");
		
		LerDiretorio lerDiretorio = (LerDiretorio)requisicao;
		File[] arquivos = lerDiretorio.getDiretorio().listFiles(); 
			  
		if(arquivos != null){			
		    for(int i = 0; i < arquivos.length; ++i){ 
		        File f = arquivos[i]; 
			    
		        if(f.isFile()){ 
		            System.out.println(f.getName()); 
		        } 
		        else if(f.isDirectory()){ 
		        	System.out.println("Diretorio: " + f.getName()); 
		        } 
		    } 
		}
		System.out.println("==== Termino ====");
	}

}
