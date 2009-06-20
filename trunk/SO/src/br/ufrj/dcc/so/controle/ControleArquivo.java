package br.ufrj.dcc.so.controle;

import java.util.concurrent.Semaphore;
import java.util.ArrayList;
import java.util.List;
import br.ufrj.dcc.so.entidade.*;
public class ControleArquivo {
	
	private static ControleArquivo controleArquivo;
	
	public final String ENDERECO_SERVIDOR = "./filesServer";
	
	
	//Listas que serao usadas para controle dos arquivos utilizados pelos clientes
	//serao usados semaforos nelas
	private List<String> listaCliente;
	//TODO:devera ser outro objeto que contenha o cliente e o arquivo
	private List<ArquivoUtilizado> listaArquivoUtilizado;
	private static Semaphore semaforoCliente;
	private static Semaphore semaforoArquivoUtilizado;
	
	private ControleArquivo()
	{
		listaCliente = new ArrayList<String>();
		listaArquivoUtilizado = new ArrayList<ArquivoUtilizado>();
	}
	//Padrao Singleton: cria somente um objeto do tipo ControleArquivo;
	public static ControleArquivo Create()
	{		
		if(controleArquivo == null) {
			controleArquivo =  new ControleArquivo();
			
			semaforoCliente = new Semaphore(1, true);
			semaforoArquivoUtilizado = new Semaphore(1,true);
		}
		return controleArquivo;
	}
	
	
	public boolean contemCliente(String cliente){
		return listaCliente.contains(cliente);
	}	
	
	public void adicionarCliente(String cliente) {
		listaCliente.add(cliente);				
	}	
	
	public void removerCliente(String cliente) {		
			listaCliente.remove(cliente);			
	}
	
	//Remove todas as requisicoes do cliente
	public void removerArquivosUtilizados(String cliente) {
		for (ArquivoUtilizado r : listaArquivoUtilizado) {
			if(r.getCliente().trim().equals(cliente.trim()))
				listaArquivoUtilizado.remove(r);
				break;
		}	
	}
	
	public void adicionarArquivoUtilizado(ArquivoUtilizado au ) {
		listaArquivoUtilizado.add(au);				
	}
	
	public void removerArquivoUtilizado(ArquivoUtilizado au){
		for (ArquivoUtilizado r : listaArquivoUtilizado) {
			if(r.getCaminhoArquivo().equals(au.getCaminhoArquivo()) && r.getCliente().equals(au.getCliente()))
			{
				listaArquivoUtilizado.remove(r);
				break;
			}
		}
	}
	
	public boolean isArquivoUsadoPorCliente(ArquivoUtilizado au) {
		for (ArquivoUtilizado r : listaArquivoUtilizado) {
			if(r.getCaminhoArquivo().equals(au.getCaminhoArquivo()) && r.getCliente().equals(au.getCliente()))
				return true;				
		}
		
		return false;
	}
	
	public boolean isArquivoUsadoPorOutroCliente(ArquivoUtilizado au) {
		
		for (ArquivoUtilizado r : listaArquivoUtilizado) {
			if(r.getCaminhoArquivo().equals(au.getCaminhoArquivo())&& !r.getCliente().equals(au.getCliente()))
				return true;				
		}
		
		return false;
	}	
	
	public void fecharAcessoListaCliente() throws InterruptedException{
		semaforoCliente.acquire();
	}	
	
	public void abrirAcessoListaCliente(){
		semaforoCliente.release();
	}
	
	public void fecharAcessoListaArquivoUtilizado() throws InterruptedException{
		semaforoArquivoUtilizado.acquire();
	}
	
	public void abrirAcessoListaArquivoUtilizado(){
		semaforoArquivoUtilizado.release();
	}
}
