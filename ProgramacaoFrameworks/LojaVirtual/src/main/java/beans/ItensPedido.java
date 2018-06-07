package beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "itens_pedido")
public class ItensPedido  implements Serializable  {
	
		private static final long serialVersionUID = 1L;
		
		

		public ItensPedido() {
			super();
		}
		
		
		public ItensPedido(Produto produto) {
			super();
			this.produto = produto;
			this.quantidade = 1;
		}


		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "ipe_id")
		private int id;

		@Column(name = "ipe_subtotal")
		private float subTotal;

		@Column(name = "ipe_qtde")
		private int quantidade;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getQuantidade() {
			return quantidade;
		}

		public void setQuantidade(int quantidade) {
			this.quantidade = quantidade;
		}


		public float getSubTotal() {
			return subTotal;
		}

		public void setSubTotal(float subTotal) {
			this.subTotal = subTotal;
		}
		@ManyToOne
		@JoinColumn(name = "ped_id")
		private Pedido pedido;

		@ManyToOne
		@JoinColumn(name = "pro_id")
		private Produto produto;




		public Pedido getPedido() {
			return pedido;
		}

		public void setPedido(Pedido pedido) {
			this.pedido = pedido;
		}

		public Produto getProduto() {
			return produto;
		}

		public void setProduto(Produto produto) {
			this.produto = produto;
		}

		
	
}


