package negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import beans.FormaPgto;
import beans.ItensPedido;
import beans.Pedido;
import beans.Pessoa;
import beans.Produto;
import persistencia.FormaPgtoDAO;
import persistencia.ItensPedidoDAO;
import persistencia.PedidoDAO;
import persistencia.PessoaDAO;

@ManagedBean
@SessionScoped
public class PedidoCtrl implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pedido pedido = new Pedido();
	private Produto produto = new Produto();
	private List<ItensPedido> itens = new ArrayList<ItensPedido>();
	private boolean desabilitarParcelas = true;
	public List<Produto> produtoGuardado = new ArrayList<>();
	private Pessoa cliente = new Pessoa();
	private FormaPgto forma = new FormaPgto();
	private Float subTotal = 0.0f;
	private Long numeroPedido; 

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public FormaPgto getForma() {
		return forma;
	}

	public void setForma(FormaPgto forma) {
		this.forma = forma;
	}

	public boolean isDesabilitarParcelas() {
		return desabilitarParcelas;
	}

	public void setDesabilitarParcelas(boolean desabilitarParcelas) {
		this.desabilitarParcelas = desabilitarParcelas;
	}

	public List<ItensPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItensPedido> itens) {
		this.itens = itens;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Pessoa getCliente() {
		return cliente;
	}

	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
	}
	
	public Float getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Float subTotal) {
		this.subTotal = subTotal;
	}
	
	public Long getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(Long numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	// Adiciona produtos no carrinho
	public String adicionarProdutoAoCarrinho(Produto produto) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		if (pedido.getPed_id() == 0) {

			//this.pedido.getProdutos().add(produto);
			this.itens.add(new ItensPedido(produto));
			context.addMessage(null, new FacesMessage(" ", "Produto inserido com sucesso"));
		} else {

			context.addMessage(null, new FacesMessage(" ", "Produto acabou em estoque"));
		}
		return "index";
	}
	public String excluirProdutoDoCarrinho(Produto produto) {
		
		for (int i = 0; i < this.itens.size(); i++) {
			if (this.itens.get(i).getProduto().getId() == produto.getId()) {
				this.itens.remove(i);
				float valorSub = calculaSubTotal(this.itens) - produto.getPreco();
				this.pedido.setTotal(valorSub);
				this.subTotal = valorSub;
			}
		}
		return null;
	}
	
	private float calculaSubTotal(List<ItensPedido> itens) {
		float soma = 0.0f;
		for (ItensPedido itensPedido : itens) {
			soma += itensPedido.getProduto().getPreco();
		}
		return soma;
	}

	public String actionPedido() {
		valorDoPedido();
		return "carrinho?faces-redirect=true";
	}

	public String actionPagamento() {
		return "/cliente/finalizarPgto?faces-redirect=true";
	}

	//public Date dataDoSistema() {
		
		//Date hoje = new Date();
		//return hoje;
	//}

	public void valorDoPedido() {
		// Varre a lista de produtos e soma todos os preços
		float valorTotal = 0;
		for (ItensPedido item : itens) {
			valorTotal += item.getProduto().getPreco();
		}
		this.setSubTotal(valorTotal);
		this.pedido.setTotal(valorTotal);
	}

	public String calcQuantidadeProduto(Produto produto) {

		// pega a quantidade de produtos que o cliente solicitou e o preço
		// (subtotal)
		//valorDoPedido();
		//float subtotalAtualizado = this.getSubTotal() - produto.getPreco();

		Float total = 0f;
		for (ItensPedido item : itens) {
			total += item.getProduto().getPreco() * item.getQuantidade();
		}
		this.setSubTotal(total);
		this.pedido.setTotal(total);
		
		return null;
	}

	public String controleParcelas() {
		// Controle para saber se a opção de forma de pagamento é de cartão de
		// crédito, boleto ou débito

		if (this.forma.getId() == 8) { // credito
			this.desabilitarParcelas = false;
			this.pedido.setTotal(this.getSubTotal());
			this.pedido.setDesconto(0);

		} else if (this.forma.getId() == 7) { // boleto
			this.desabilitarParcelas = true;
			FormaPgto forma = new FormaPgtoDAO().pesqId(this.forma.getId());
			descontoNoBoleto(forma.getDesconto());
			this.pedido.setDesconto((float) (forma.getDesconto() * this.getSubTotal()));
			this.forma.setNumPadraoParc(1);

		} else { // debito
			this.desabilitarParcelas = true;
			FormaPgto forma = new FormaPgtoDAO().pesqId(this.forma.getId());
			descontoNoDebito(forma.getDesconto());
			this.pedido.setDesconto((float) (forma.getDesconto() * this.getSubTotal()));
			this.forma.setNumPadraoParc(1);

		}
		return null;
	}

	public void descontoNoBoleto(double desconto) {
		// Atribui desconto se a opção de pagamento for pelo boleto
		this.pedido.setTotal((float) (this.getSubTotal() - (this.getSubTotal() * desconto)));
	}

	public void descontoNoDebito(double desconto) {
		// Atribui desconto se a opção de pagamento for debito
		this.pedido.setTotal((float) (this.getSubTotal() - (this.getSubTotal() * desconto)));
	}

	public String jurosSobreParcela() {
		// Aplica juros quando o item for parcelado acima de 10x
		double juros = 0;
		if (this.forma.getNumPadraoParc() == 11) {
			juros = 0.05;
			this.pedido.setTotal(this.getSubTotal() + (this.getSubTotal() * (float) juros));
		} else if (this.forma.getNumPadraoParc() == 12) {
			juros = 0.10;
			this.pedido.setTotal(this.getSubTotal() + (this.getSubTotal() * (float) juros));
		} else {
			this.pedido.setTotal(this.getSubTotal());
		}
		return null;
	}

	public String gravarPessoa(Pessoa pessoa) {
		// gravar o usuario logado em pedidos 
		// grava a forma de pagamento com o auxilio de outro método

		this.pedido.setPessoa(pessoa);

		return null;
	}

	public String actionGravar() {

		// Pega o Email Logado
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String userName = external.getRemoteUser();

		PessoaDAO pessoaDAO = new PessoaDAO();
		Pessoa pessoa = pessoaDAO.retornaUsuario(userName);

		// Coloca cliente no pedido 
		this.pedido.setPessoa(pessoa);

		this.pedido.setFormaPgto(this.forma);
		FacesContext context1 = FacesContext.getCurrentInstance();
		if (forma.getId() == 0) {

			context1.addMessage(null, new FacesMessage(" Falha", "Selecione uma Forma de Pagamento"));
			return "finalizarPgto";
		} else {

			context1.addMessage(null, new FacesMessage(" Sucesso", "Compra finalizada com sucesso"));
		}

		pedido.setDataEmissao(new Date());
		pedido.setStatus("Confirmado");
		pedido.setDataAutorizacao(new Date()); 
		// Inserir no banco pra conseguir ter o ID
		PedidoDAO.inserir(this.pedido);
		

		// Pegar o id do pedido no banco
		Pedido ultimoPedido = new Pedido();
		ultimoPedido = PedidoDAO.listarUltimoPedidoAdicionado();

		for (int i = 0; i < this.itens.size(); i++) {
			itens.get(i).setPedido(ultimoPedido);
			//itens.get(i).setProduto(this.pedido.getProdutos().get(i));

			ItensPedidoDAO.inserir(itens.get(i));
		}
		
		this.numeroPedido = Long.valueOf(ultimoPedido.getPed_id());

		return "statusPedido";
	}

	public String formaDePagamento() {

		return "finalizarPgto?faces-redirect=true";
	}

}