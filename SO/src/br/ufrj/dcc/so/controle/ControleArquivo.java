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
		clientes = new ArrayList<String>();
		requisicoes = new ArrayList<Requisicao>();
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
	
	//Verifica se um cliente ja esta conectado com o servidor
	public boolean contemCliente(String cliente)
	{
		return clientes.contains(cliente);
	}
	
	//Adiciona cliente na lista de clientes conectados com o servidor
	public void adicionarCliente(Requisicao req) {
		if(!contemCliente(req.getCliente()))
		{
			clientes.add(req.getCliente());
		}
		else
		{
			req.getErros().add("Cliente ja esta conectado com o servidor");
		}
				
	}
	
	//Remove cliente na lista de clientes conectados com o servidor
	public void removerCliente(Requisicao req) {
		if(controleArquivo.contemCliente(req.getCliente())){
			clientes.remove(req.getCliente());
		}
		else
		{
			req.getErros().add("Este cliente nao esta conectado com o servidor");
		}		
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
			if(r.getCliente().trim().equals(cliente.trim()))
				requisicoes.remove(r);
		}		
	}
	
	public void adicionarRequisicao(Requisicao req) {
		
		if(contemRequisicaoParaCliente(req)) return;
			
		if(contemRequisicaoParaOutroCliente(req))
		{
			req.getErros().add("Este arquivo ja esta aberto para escrita com outro usuario.");
			return;
		}
		
		requisicoes.add(req);				
	}
	
	public boolean contemRequisicaoParaCliente(Requisicao req) {
		for (Requisicao r : requisicoes) {
			if(r.getCaminho().equals(req.getCaminho()) && r.getCliente().equals(req.getCliente()))
				return true;				
		}
		
		return false;
	}
	
	private boolean contemRequisicaoParaOutroCliente(Requisicao req) {
		
		for (Requisicao r : requisicoes) {
			if(r.getCaminho().equals(req.getCaminho())&& !r.getCliente().equals(req.getCliente()))
				return true;				
		}
		
		return false;
	}	
}
