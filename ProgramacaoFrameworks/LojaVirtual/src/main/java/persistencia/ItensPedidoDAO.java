package persistencia;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.Transaction;

import beans.ItensPedido;
import persistencia.HibernateUtil;

public class ItensPedidoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public static void inserir(ItensPedido itens) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.save(itens);
		t.commit();
		sessao.close();
	}
}
