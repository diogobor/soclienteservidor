package br.ufrj.dcc.so.entidade;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ufrj.dcc.so.controle.ControleArquivo;

public class LerDiretorio extends Requisicao {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tarefa = "lendo diretorio";
	private File diretorio;
	private byte[] diretorioPrincipal;
	private ArrayList<byte[]> diretorioDescompactado;
	private HashMap<String, byte[]> arquivosBytes;
	
	public LerDiretorio(){
		
	}

	public void setDiretorio(File arquivo) {
		this.diretorio = arquivo;
	}

	public File getDiretorio() {
		try {
			diretorio = transformaByteDirectory(diretorioDescompactado,getCaminho());
		} catch (Exception e) {
			getErros().add("Erro ao pegar o Diretorio.");
			System.out.println("Erro ao pegar o Diretorio.");
		}
		return diretorio;
	}
	
	public byte[] getDiretorioPrincipal(){
		return diretorioPrincipal;
	}
	
	public List<File> getArquivos() {
		List<File> arquivos = new ArrayList<File>();
		try {			
			for (String caminhoCompleto : arquivosBytes.keySet()) 
			{				
				File arquivo = transformaByteFile(arquivosBytes.get(caminhoCompleto), caminhoCompleto);
					
				arquivos.add(arquivo);				
			}
		} catch (Exception e) {
			getErros().add("Erro ao transformar bytes em Arquivo.");			
		}
		return arquivos;
	}

	@Override
	public void executar(ControleArquivo controleArquivo) {
		
		mensagemInicioTarefa(tarefa);
		
//		if(getCaminho().equals(""))diretorio = new File(controleArquivo.ENDERECO_SERVIDOR);
//		else diretorio = new File(getCaminho());
//		
//		try {
//
//			//diretorioPrincipal = getBytesFromFile(diretorio);
//			diretorioDescompactado = getBytesFromDirectory(diretorio);
//
//			diretorioPrincipal = transformaFileByte(diretorio);
//
//		} catch (Exception e) {
//			getErros().add("Erro ao ler diretorio no Servidor");
//			System.out.println("Erro ao ler diretorio no Servidor");
//		}
		
		
		
		File diretorio = new File("./filesServer");
		//File diretorio = new File(getCaminho());
		arquivosBytes = new HashMap<String, byte[]>();
		
		if(!diretorio.exists()){
			getErros().add("Diretorio inexistente no servidor!");
			return;
		}
		
        File[] arquivos = diretorio.listFiles();        
        if(arquivos == null){        	
        	getErros().add("Nao existe arquivo(s) no diretorio");
        	return;
        }
			
		List<String> arquivosComExtensao = obterListaArquivos(arquivos);
		
		if(arquivosComExtensao.size() == 0) getErros().add("Nao existe nenhum arquivo com esta extensao.");
		
		for (String nomeArquivo : arquivosComExtensao) {
			
			LerArquivo ler = criarLerArquivo(nomeArquivo);
			ler.executar(controleArquivo);
			
			if(ler.hasErros()) getErros().addAll(ler.getErros());
			else arquivosBytes.put(ler.getCaminhoCompleto(), ler.getArquivoBytes());			
		}
	
		mensagemFimTarefa(tarefa);
		
	}	
	
	private LerArquivo criarLerArquivo(String nomeArquivo) {
		LerArquivo ler = new LerArquivo();
		ler.setCliente(getCliente());
		ler.setCaminho(getCaminhoArquivoDir(nomeArquivo));
		ler.setNomeArquivo(getNameArquivoDir(nomeArquivo));
		ler.setTipo(Requisicao.TipoArquivo.LEITURA);
		return ler;
	}
	
	private String getNameArquivoDir(String nomeAntigo){
		String[] temp = nomeAntigo.split("\\\\");
		nomeAntigo = temp[temp.length-1];
		return nomeAntigo;
	}
	
	private String getCaminhoArquivoDir(String caminhoAntigo){
		String[] temp = caminhoAntigo.split("\\\\");
		caminhoAntigo = temp[0];
		for(int i =1; i < temp.length-1; i++){
			caminhoAntigo += "\\\\" + temp[i];
		}
		return caminhoAntigo;
	}


}
