/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacao.dao;

import br.com.avaliacao.modelo.PessoaFisica;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class PessoaFisicaDaoImpl extends BaseDaoImpl<PessoaFisica, Long> implements PessoaFisicaDao, Serializable {

    @Override
    public PessoaFisica pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (PessoaFisica) sessao.get(PessoaFisica.class, id);
    }

    @Override
    public PessoaFisica pesquisarPessoaFisicaPorCpf(String cpf, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from PessoaFisica p join fetch p.pedidos where p.cpf = :cpf");
        consulta.setParameter("cpf", cpf);
        return (PessoaFisica)consulta.uniqueResult();
    }

    @Override
    public List<PessoaFisica> pesquisarPessoaFisicaPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from PessoaFisica where nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

}
