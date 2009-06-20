package br.ufrj.dcc.so.vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;

import br.ufrj.dcc.so.controle.Cliente;

import br.ufrj.dcc.so.entidade.*;
import br.ufrj.dcc.so.entidade.Requisicao.TipoArquivo;

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
				else if (mensagem.equals("lerDiretorio")){				
					listarDiretorio();					
				}
				else if (mensagem.equals("lerArquivo")){
					lerArquivo();
				}
				else if (mensagem.equals("listarDir")){
					//listarDiretorioLocal();
				}
				else if (mensagem.equals("enviarArquivo")){
					//enviarArquivo();
				}
				else if (mensagem.equals("apagarArquivo")){
					apagarArquivo("alan3.txt");
				}
				else if (mensagem.equals("apagarArquivoExtensao")){
					apagarArquivoExtensao();
				}
				else if (mensagem.equals("infArquivo")){
					//informacoesArquivo();
				}
				else if (mensagem.equals("renomearArquivo")){
					renomearArquivo();
				}				
				else if (mensagem.equals("lerArquivoExtensao")){
					lerArquivoExtensao();
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
	
	public static void apagarArquivo(String arq) throws InterruptedException {
		ApagarArquivo deletaArquivo = new ApagarArquivo();
		deletaArquivo.setCaminho("./filesServer/arquivos");		
		deletaArquivo.setNomeArquivo(arq);
		
		deletaArquivo = (ApagarArquivo)executarRequisicao(deletaArquivo, null);
		
		if(deletaArquivo.hasErros()){
			System.out.println(deletaArquivo.errosString());
			return;
		}
		
		System.out.println("Arquivo removido com sucesso.");
		
	}
	
	private static void apagarArquivoExtensao() throws InterruptedException {
		ApagarExtensao deletarExtensao = new ApagarExtensao();
		deletarExtensao.setCaminho("./filesServer/arquivos");
		deletarExtensao.setNomeExtensao("txt");
		
		deletarExtensao = (ApagarExtensao)executarRequisicao(deletarExtensao, null);
		
		if(deletarExtensao.hasErros()){
			System.out.println(deletarExtensao.errosString());
			return;
		}
		
		System.out.println("Arquivos removidos com sucesso.");
		
	}

	public static void listarDiretorio() throws InterruptedException {
		LerDiretorio lerDiretorio = new LerDiretorio();
		lerDiretorio.setCaminho("./filesServer/arquivos");
		
		lerDiretorio = (LerDiretorio)executarRequisicao(lerDiretorio, null);
		
		if(lerDiretorio.hasErros()){
			System.out.println(lerDiretorio.errosString());
			return;
		}		
		exibirLeituraDiretorio(lerDiretorio);
	}
	
	private static void lerArquivo() throws InterruptedException{
		LerArquivo lerArquivo = new LerArquivo();
		lerArquivo.setTipo(TipoArquivo.ESCRITA);
		lerArquivo.setCaminho("./filesServer/arquivos");
		lerArquivo.setNomeArquivo("alan3.txt");
		
		lerArquivo = (LerArquivo)executarRequisicao(lerArquivo, null);
		
		if(lerArquivo.hasErros()){
			System.out.println(lerArquivo.errosString());
			return;
		}		
		exibirLeituraArquivo(lerArquivo);
		
	}
	
	private static void lerArquivoExtensao() throws InterruptedException{
		LerArquivoExtensao lerArquivo = new LerArquivoExtensao();
		lerArquivo.setTipo(TipoArquivo.ESCRITA);
		lerArquivo.setCaminho("./filesServer/arquivos");
		lerArquivo.setNomeExtensao("txt");
		
		lerArquivo = (LerArquivoExtensao)executarRequisicao(lerArquivo, null);
		
		if(lerArquivo.hasErros()){
			System.out.println(lerArquivo.errosString());
			return;
		}		
		exibirLeituraArquivoExtensao(lerArquivo);
		
	}
	
	private static void renomearArquivo() throws InterruptedException{
		RenomearArquivo renomearArquivo = new RenomearArquivo();		
		renomearArquivo.setCaminho("./filesServer/arquivos");
		renomearArquivo.setNomeArquivo("alan3.txt");
		renomearArquivo.setNomeNovoArquivo("alanalan3.txt");
		
		renomearArquivo = (RenomearArquivo)executarRequisicao(renomearArquivo, null);
		
		if(renomearArquivo.hasErros()){
			System.out.println(renomearArquivo.errosString());
			return;
		}
		else System.out.println("Arquivo renomeado ocm sucesso.");
		
	}
	
	private static void exibirLeituraArquivoExtensao(Requisicao requisicao) {
		System.out.println("==== Lista Arquivo por extensao do Servidor ====");
		
		LerArquivoExtensao lerArquivo = (LerArquivoExtensao)requisicao;
		
		for (File arquivo : lerArquivo.getArquivos()) {
			if(arquivo != null){
			    			    
			   if(arquivo.isFile()){ 
				   System.out.println(arquivo.getName());
			   }		        
			}
		}
		System.out.println("==== Termino ====");
		
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
	
	public static void exibirLeituraArquivo(Requisicao requisicao) {
		
		System.out.println("==== Lista Arquivo do Servidor ====");
		
		LerArquivo lerArquivo = (LerArquivo)requisicao;
		File arquivo = lerArquivo.getArquivo(); 
			  
		if(arquivo != null){
		    			    
		   if(arquivo.isFile()){ 
			   System.out.println(arquivo.getName());
		   }		        
		}
		System.out.println("==== Termino ====");
	}

}
