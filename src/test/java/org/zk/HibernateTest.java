package org.zk;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zk.entity.User;

/**
 * Unit test for simple App.
 */
public class HibernateTest {

	private SessionFactory sessionFactory;
	private Session session;

	@Before
	public void before() {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		sessionFactory = cfg.buildSessionFactory();
		session = sessionFactory.openSession();
	}

	@After
	public void after() {
		session.close();
		sessionFactory.close();
	}



	@Test
	public void testSave() {
		Transaction t = session.beginTransaction();

		User e1 = new User();
		e1.setUsername("Max2");
		session.persist(e1);

		t.commit();
	}

	@Test
	public void testQuery() {
		User user = (User)session.get(User.class, 101L);
		System.out.println(user);
	}
}
