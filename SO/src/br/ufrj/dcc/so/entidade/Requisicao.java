package br.ufrj.dcc.so.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import br.ufrj.dcc.so.controle.ControleArquivo;;

public abstract class Requisicao implements Serializable {
	
	public enum TipoArquivo{
		LEITURA,
		ESCRITA;
	}
	/*
	 * Variaveis	 
	 */
	private String caminho;
	private String cliente;
	private List<String> erros;
	 
		
	public abstract void executar(ControleArquivo controleArquivo);
	
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}

	public List<String> getErros() {
		if(erros == null) erros = new ArrayList<String>();
		return erros;
	}
	
	public String errosString(){
		String s = ""; 
		
		for (String erro : erros) {
			s += erro + "\n";
		}
		return s;
	}
	
	public boolean hasErros(){
		return ((erros != null) && (erros.size()!= 0));	
	}
	
	protected void mensagemFimTarefa(String tarefa) {
		System.out.println(String.format("FIM: Cliente %s %s.", getCliente(), tarefa));
	}

	protected void mensagemInicioTarefa(String tarefa) {
		System.out.println(String.format("INICIO: Cliente %s %s", getCliente(), tarefa));
	}
	
}
