package beans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pro_id")
	private int id;

	@Column(name = "pro_nome", length = 60, nullable = true)
	private String nome;

	@Column(name = "pro_preco", nullable = true)
	private float preco;

	@Column(name = "pro_imagem")
	private String foto;

	@ManyToMany(mappedBy = "produto", cascade = CascadeType.ALL)
	private List<ItensPedido> produto;

	public List<ItensPedido> getProduto() {
		return produto;
	}

	public void setProduto(List<ItensPedido> produto) {
		this.produto = produto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}
	
}
