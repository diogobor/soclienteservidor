package br.ufrj.dcc.so.entidade;

public class ArquivoUtilizado {
	
	private String cliente;
	private String caminhoArquivo;
	
	public ArquivoUtilizado(String cliente, String caminhoArquivo){
		this.setCaminhoArquivo(caminhoArquivo);
		this.setCliente(cliente);
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}
	
	

}
