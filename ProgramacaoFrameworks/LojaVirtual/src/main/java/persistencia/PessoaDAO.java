package persistencia;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;



import beans.Pessoa;

public class PessoaDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static void inserir(Pessoa pessoa) {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.save(pessoa);
		t.commit();
		sessao.close();
	}
	public static void alterar(Pessoa pessoa) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.update(pessoa);
		t.commit();
		sessao.close();
	}
	public static void excluir(Pessoa pessoa) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.delete(pessoa);
		t.commit();
		sessao.close();
	}
	public static List<Pessoa> listagem(String filtro) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Query consulta;
		List<Pessoa> lista = null;
		if (filtro.trim().length() == 0) {
			consulta = sessao.createQuery("from Pessoa order by pes_nome");
			
		}
		else {
			consulta = sessao.createQuery("from Pessoa where pes_nome like :parametro order by pes_nome");
			consulta.setString("parametro", "%" + filtro + "%");	
		}
		lista = consulta.list();
		sessao.close();
		return lista;
	}
	
	public static List<Pessoa> listagemPedidosPorCliente(int clienteId) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Query consulta;
		List<Pessoa> lista = null;

			consulta = sessao.createQuery("from Pedido p inner join Pedido.pessoa pes where pes.pes_id = :parametro order by p.ped_dataEmissao");
			consulta.setString(clienteId, "parametro");	
		lista = consulta.list();
		sessao.close();
		return lista;
	}
	
	public Pessoa retornaUsuario(String logado) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Query query = sessao.createQuery("from Pessoa where pes_email = :logado");
			query.setParameter("logado", logado);
			Pessoa login = (Pessoa) query.uniqueResult();
			sessao.close();
			return login;
		} catch (RuntimeException ex) {
			throw ex;
		} 
	}

}
