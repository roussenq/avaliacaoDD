/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacao.dao;

import br.com.avaliacao.modelo.PessoaJuridica;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class PessoaJuridicaDaoImpl extends BaseDaoImpl<PessoaJuridica, Long> implements PessoaJuridicaDao, Serializable {

    @Override
    public PessoaJuridica pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (PessoaJuridica) sessao.get(PessoaJuridica.class, id);
    }

    @Override
    public PessoaJuridica pesquisarPessoaJuridicaPorCnpj(String cnpj, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from PessoaJuridica p join fetch p.pedidos where p.cnpj = :cnpj");
        consulta.setParameter("cnpj",cnpj);
        return (PessoaJuridica)consulta.uniqueResult();
    }

    @Override
    public List<PessoaJuridica> pesquisarPessoaJuridicaPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from PessoaJuridica where nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

}
