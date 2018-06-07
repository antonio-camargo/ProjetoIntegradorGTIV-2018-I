package negocio;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import beans.Fone;
import beans.Pessoa;
import persistencia.FoneDAO;
import persistencia.PessoaDAO;

@ManagedBean
@SessionScoped
public class PessoaCtrl implements Serializable {


		private static final long serialVersionUID = 1L;
		private Pessoa pessoa = new  Pessoa();
		private String filtro = "";
		public List<Pessoa> lista = new ArrayList<>();

		public List<Pessoa> getLista() {
			return lista;
		}

		public void setLista(List<Pessoa> lista) {
			this.lista = lista;
		}

		public Pessoa getPessoa() {
			if (pessoa == null) {
				pessoa = new Pessoa();
			}
			return pessoa;
		}

		public void setPessoa(Pessoa pessoa) {
			this.pessoa = pessoa;

		}

		public String getFiltro() {
			return filtro;
		}

		public void setFiltro(String filtro) {
			this.filtro = filtro;
		}

		public List<Pessoa> getListagem() {
			return PessoaDAO.listagem(filtro);
		}
		public String actionNovaPessoa() {
			pessoa = new Pessoa();
			return "/admin/listar_pessoa";
		}
	
		public String actionGravar() {
			FacesContext context = FacesContext.getCurrentInstance();
			if (pessoa.getId() == 0) {
				PessoaDAO.inserir(pessoa);
				context.addMessage(null, new FacesMessage("Sucesso", "Inserido com sucesso"));
			} else {
				PessoaDAO.alterar(pessoa);
				context.addMessage(null, new FacesMessage("Sucesso", "Alterado com Sucesso"));
			}
			return " ";
		}

		public String actionInserir() {
			pessoa = new Pessoa();
			return "/admin/listar_pessoa";

		}

		public String actionExcluir() {
			PessoaDAO.excluir(pessoa);
			return "/admin/listar_pessoa";
		}

		public String actionInserirFone() {

			Fone fone = new Fone();
			fone.setPessoa(pessoa);
			pessoa.getFones().add(fone);
			return " ";
		}

		public String actionExcluirFone(Fone fone) {
			getPessoa().getFones().remove(fone);
			FoneDAO.excluir(fone);
			return "/admin/listar_pessoa";

		}
		public void onRowSelect(SelectEvent event) {
			FacesMessage msg = new FacesMessage("Forma de Pagamento Selecionada",
					String.valueOf(((Pessoa) event.getObject()).getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}


}
