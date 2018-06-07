package negocio;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FileUploadEvent;

import beans.Produto;
import persistencia.ProdutoDAO;

@ManagedBean
@SessionScoped
public class ProdutoCtrl implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto;
	private String filtro = "";
	private UploadImagem arquivo = new UploadImagem();
	
	public ProdutoCtrl() {
		this.produto = new Produto();
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getListagem() {
		return ProdutoDAO.listagem(filtro);
	}

	public String actionGravar() {

		if (produto.getId() == 0) {
			ProdutoDAO.inserir(produto);
			this.arquivo.gravar();
			this.produto = new Produto();
			this.arquivo = new UploadImagem();
			return actionInserir();
		} else {
			ProdutoDAO.alterar(produto);
			this.arquivo.gravar();
			this.produto = new Produto();
			this.arquivo = new UploadImagem();
			return "lista_produto";
		}
	}

	public void uploadAction(FileUploadEvent event) {
		this.arquivo.fileUpload(event, ".jpg", "/home/tony/img/");
		this.produto.setFoto(this.arquivo.getNome());
	}

	public String actionInserir() {
		return "lista_produto";
	}

	public String actionExcluir() {
		ProdutoDAO.excluir(getProduto());
		return "lista_produto";
	}

	public String actionAlterar() {
		return "inserir_produto";
	}

	public String actionNovoProduto() {
		produto = new Produto();
		return "lista_produto";
	}

	public String actionListar() {
		return "lista_produto";
	}

}
