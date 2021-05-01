/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacao.dao;

import br.com.avaliacao.modelo.Cliente;
import br.com.avaliacao.modelo.Endereco;
import br.com.avaliacao.modelo.Pedido;
import br.com.avaliacao.modelo.PessoaFisica;
import br.com.avaliacao.modelo.PessoaJuridica;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author David
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory; 
    
    static {
        try {
            Configuration cfg = new Configuration(); 
            cfg.addAnnotatedClass(Cliente.class);
            cfg.addAnnotatedClass(Pedido.class);
            cfg.addAnnotatedClass(PessoaFisica.class);
            cfg.addAnnotatedClass(PessoaJuridica.class);
            cfg.addAnnotatedClass(Endereco.class);

            cfg.configure("/br/com/avaliacao/dao/hibernate.cfg.xml");
            StandardServiceRegistryBuilder build = new StandardServiceRegistryBuilder().
                                           applySettings(cfg.getProperties());
            sessionFactory = cfg.buildSessionFactory(build.build());
        } catch (HibernateException ex) {
            System.err.println("Erro ao criar Hibernate util." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static Session abrirConexao() {
        return sessionFactory.openSession();
    }
}
