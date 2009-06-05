package br.ufrj.dcc.so.entidade;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class InformacoesArquivo extends Requisicao{

	private String tarefa = "esta obtendo informacoes do arquivo";
	private Date dataCriacao;
	private Date dataModificacao;
	private String tamanhoArquivo;
	private String nomeArquivo;	
	
	public InformacoesArquivo(){
		
	}
	
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;		
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setTamanhoArquivo(String tamanhoArquivo) {
		this.tamanhoArquivo = tamanhoArquivo;
	}

	public String getTamanhoArquivo() {
		return tamanhoArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setDataModificacao(Date dataModificacao) {
		this.dataModificacao = dataModificacao;
	}

	public Date getDataModificacao() {
		return dataModificacao;
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		
		mensagemInicioTarefa(tarefa);
		
		String completePath = getCaminho() + "\\" + nomeArquivo;
		File arquivo = new File(completePath);		
		if (arquivo.exists()){
			//DateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");			
			//String data = formatData.format(new Date(arquivo.lastModified()));  
						
			dataModificacao = new Date(arquivo.lastModified());			
			tamanhoArquivo = String.valueOf(arquivo.length());
		}
		else{
			getErros().add("Nao existe o arquivo no servidor.");
		}
		
		mensagemFimTarefa(tarefa);
		
	}
}
