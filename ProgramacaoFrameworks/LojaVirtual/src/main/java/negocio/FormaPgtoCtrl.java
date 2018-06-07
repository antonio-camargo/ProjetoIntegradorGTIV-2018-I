package negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import beans.FormaPgto;
import persistencia.FormaPgtoDAO;

@ManagedBean
@SessionScoped
public class FormaPgtoCtrl implements Serializable {

	private static final long serialVersionUID = 1L;

	private FormaPgto formaPgto = new FormaPgto();
	private String filtro = "";
	public List<FormaPgto> lista = new ArrayList<>();

	public List<FormaPgto> getLista() {
		return lista;
	}

	public void setLista(List<FormaPgto> lista) {
		this.lista = lista;
	}

	public FormaPgto getFormaPgto() {
		if (formaPgto == null) {
			formaPgto = new FormaPgto();
		}
		return formaPgto;
	}

	public void setFormaPgto(FormaPgto formaPgto) {
		this.formaPgto = formaPgto;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<FormaPgto> getListagem() {
		return FormaPgtoDAO.listagem(filtro);
	}

	public String actionGravar() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (formaPgto.getId() == 0) {
			FormaPgtoDAO.inserir(formaPgto);
			context.addMessage(null, new FacesMessage("Sucesso", "Inserido com sucesso"));
		} else {
			FormaPgtoDAO.alterar(formaPgto);
			context.addMessage(null, new FacesMessage("Sucesso", "Alterado com Sucesso"));
		}
		return "/admin/cadastrar_formaPgto";

	}

	public String actionInserir() {
		formaPgto = new FormaPgto();
		return "/admin/cadastrar_formaPgto";
	}

	public String actionExcluir() {
		FormaPgtoDAO.exculir(formaPgto);
		return "/admin/cadastrar_formaPgto";
		}

	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("Forma de Pagamento Selecionada",
				String.valueOf(((FormaPgto) event.getObject()).getId()));
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}
	
}



