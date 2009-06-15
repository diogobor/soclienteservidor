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
	private List<String> clientes;
	//TODO:devera ser outro objeto que contenha o cliente e o arquivo
	private List<Requisicao> requisicoes;
	private static Semaphore semaforoClientes;
	private static Semaphore semaforoRequisicoes;
	
	private ControleArquivo()
	{
		setClientes(new ArrayList<String>());
		setRequisicoes(new ArrayList<Requisicao>());
	}
	//Padrao Singleton: cria somente um objeto do tipo ControleArquivo;
	public static ControleArquivo Create()
	{		
		if(controleArquivo == null) {
			controleArquivo =  new ControleArquivo();
			
			semaforoClientes = new Semaphore(1, true);
			semaforoRequisicoes = new Semaphore(1,true);
		}
		return controleArquivo;
	}
	
	public void setClientes(List<String> clientes) 
	{
		this.clientes = clientes;
	}
	
	public List<String> getClientes() 
	{
		return clientes;
	}
	
	public void setRequisicoes(List<Requisicao> requisicoes) 
	{
		this.requisicoes = requisicoes;
	}
	
	public List<Requisicao> getRequisicoes() 
	{
		return requisicoes;
	}
	
	//Verifica se um cliente ja esta conectado com o servidor
	public boolean isClienteConectado(String cliente)
	{
		return getClientes().contains(cliente);
	}
	
	//Adiciona cliente na lista de clientes conectados com o servidor
	public void conectarCliente(String cliente) {
		getClientes().add(cliente);		
	}
	
	//Remove cliente na lista de clientes conectados com o servidor
	public void desconectarCliente(String cliente) {
		getClientes().remove(cliente);		
	}
	
	public void fecharAcessoListaCliente() throws InterruptedException{
		semaforoClientes.acquire();
	}	
	
	public void abrirAcessoListaCliente(){
		semaforoClientes.release();
	}
	
	public void fecharAcessoListaRequisicoes() throws InterruptedException{
		semaforoRequisicoes.acquire();
	}
	
	public void abrirAcessoListaRequisicoes(){
		semaforoRequisicoes.release();
	}
	
	public void limparRequisicoesAbertas(String cliente) {
		for (Requisicao r : requisicoes) {
			if(r.getCliente().trim() == cliente.trim())
				requisicoes.remove(r);
		}
		
	}	
}
