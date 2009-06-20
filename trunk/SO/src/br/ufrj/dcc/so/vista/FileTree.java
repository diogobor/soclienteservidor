package br.ufrj.dcc.so.vista;

import java.awt.BorderLayout;
import java.io.File;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class FileTree extends JPanel{
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scroll;
	private JTree arvoreArquivos;
	private Comecar comecar;
	
	public FileTree(File dir) {
	    
	    arvoreArquivos = new JTree(adicionarNos(null, dir));
	    scroll = new JScrollPane();
	    
	    //arvoreArquivos.addTreeSelectionListener(this);
	    scroll.getViewport().add(arvoreArquivos);
	    setLayout(new BorderLayout());
	    add(BorderLayout.CENTER, scroll);
	  }

  /** Add nodes from under "dir" into curTop. Highly recursive. */
  private DefaultMutableTreeNode adicionarNos(DefaultMutableTreeNode raizAtual, File dir) {
	  
    String caminhoAtual = dir.getPath();
    DefaultMutableTreeNode diretorioAtual = new DefaultMutableTreeNode(caminhoAtual);
    
    if (raizAtual != null) { // should only be null at root
      raizAtual.add(diretorioAtual);
    }
    
    Vector ol = new Vector();
    String[] tmp = dir.list();
    
    for (int i = 0; i < tmp.length; i++){
      ol.addElement(tmp[i]);
    }
    
    Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
    File f;
    Vector arquivos = new Vector();
    
    for (int i = 0; i < ol.size(); i++) {
      String objeto = (String) ol.elementAt(i);
      String novoCaminho;
      if (caminhoAtual.equals("."))
        novoCaminho = objeto;
      else
        novoCaminho = caminhoAtual + File.separator + objeto;
      if ((f = new File(novoCaminho)).isDirectory())
        adicionarNos(diretorioAtual, f);
      else
        arquivos.addElement(objeto);
    }
   
    for (int fnum = 0; fnum < arquivos.size(); fnum++){
      diretorioAtual.add(new DefaultMutableTreeNode(arquivos.elementAt(fnum)));
    }
    
    return diretorioAtual;
  }
 
  public void addComecarListener(Comecar comecar){
	  this.comecar=comecar;
	  arvoreArquivos.addTreeSelectionListener(this.comecar);
  }
}
