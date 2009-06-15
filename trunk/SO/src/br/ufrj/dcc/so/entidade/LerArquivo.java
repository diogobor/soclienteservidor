package br.ufrj.dcc.so.entidade;

import java.io.File;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class LerArquivo extends Requisicao{

	private String nomeArquivo;
	private TipoArquivo tipo;
	private File arquivo;
	
	private String tarefa = "lendo arquivo";
	
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}

	public File getArquivo() {
		return arquivo;
	}

	public void setTipo(TipoArquivo tipo) {
		this.tipo = tipo;
	}

	public TipoArquivo getTipo() {
		return tipo;
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {		
		mensagemInicioTarefa(tarefa);
		
		try 
		{			
			arquivo = new File(getCaminho());
			
			if(arquivo.exists()){
				if(isEscrita())
				{
					controleArquivo.fecharAcessoListaRequisicoes();					
					controleArquivo.adicionarRequisicao(this);					
					controleArquivo.abrirAcessoListaRequisicoes();
				}
			}
			else
			{
				getErros().add("Não existe este arquivo no servidor");
				arquivo = null;
			}
		}
		catch (InterruptedException e) {
				
			getErros().add("Ocorreu um erro na execucao do semaforo.");
		}			
				
		mensagemFimTarefa(tarefa);
	}
	
	private boolean isEscrita(){
		return getTipo() != null && getTipo() == TipoArquivo.ESCRITA;
	}

}
