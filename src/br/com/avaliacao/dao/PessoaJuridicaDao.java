/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacao.dao;

import br.com.avaliacao.modelo.PessoaJuridica;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author David
 */
public interface PessoaJuridicaDao extends BaseDao<PessoaJuridica, Long>{
    
    PessoaJuridica pesquisarPessoaJuridicaPorCnpj(String cnpj, Session sessao) throws HibernateException;

    List<PessoaJuridica> pesquisarPessoaJuridicaPorNome(String nome, Session sessao) throws HibernateException;
    
}
