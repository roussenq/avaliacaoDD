/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacao.dao;

import br.com.avaliacao.modelo.PessoaFisica;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author David
 */
public interface PessoaFisicaDao extends BaseDao<PessoaFisica, Long> {
    
    PessoaFisica pesquisarPessoaFisicaPorCpf(String cpf, Session sessao) throws HibernateException;

    List<PessoaFisica> pesquisarPessoaFisicaPorNome(String nome, Session sessao) throws HibernateException;
}
