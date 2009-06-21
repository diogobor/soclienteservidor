package br.ufrj.dcc.so.entidade;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class SalvarArquivoExtensao extends Requisicao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nomeExtensao;
	private File diretorio;
	private List<File> arquivos;
	private String tarefa = "salvando lista de arquivos";
	
	public SalvarArquivoExtensao(){
		arquivos = new ArrayList<File>();		
	}
		
	public void setNomeExtensao(String nomeExtensao) {
		this.nomeExtensao = nomeExtensao;
	}

	public String getNomeExtensao() {
		return nomeExtensao;
	}

	public void setDiretorio(File diretorio) {
		this.diretorio = diretorio;
	}
	
	public void setArquivos(List<File> arquivos) {
		this.arquivos = arquivos;
	}

	public List<File> getArquivos() {
		return arquivos;
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		mensagemInicioTarefa(tarefa);
		try 
		{	
			
			File[] arquivos = diretorio.listFiles();     

	        if(arquivos == null){        	
	        	getErros().add("Nao existe arquivo(s) no diretorio");
	        	return;
	        }
	        
	        List<String> arquivosComExtensao = obterListaArquivosComExtensao(arquivos, getNomeExtensao());
			
	        if(arquivosComExtensao.size() == 0) getErros().add("Nao existe nenhum arquivo com esta extensao.");
	        
	        for (String nomeArquivo : arquivosComExtensao) {
				SalvarArquivo salvar = criarSalvarArquivo(new File(diretorio.getPath() + "\\\\" + nomeArquivo));
				salvar.executar(controleArquivo);
				
				if(salvar.hasErros()) getErros().addAll(salvar.getErros());
				else this.arquivos.add(salvar.getArquivo());
				
			}
	
		}
		catch (Exception e) {				
			getErros().add("Nao foi possivel Salvar o Arquivo no Servidor");
		}
		
		mensagemFimTarefa(tarefa);
	}
	
	private SalvarArquivo criarSalvarArquivo(File nomeArquivo) {
		SalvarArquivo salvar = new SalvarArquivo();
		salvar.setCliente(getCliente());
		salvar.setArquivo(nomeArquivo);
		salvar.setCaminho(getCaminho());
		salvar.setNomeArquivo(nomeArquivo.getName());
		return salvar;
	}

}
