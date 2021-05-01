/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacao.dao;

import org.hibernate.*;

/**
 *
 * @author David
 * @param <T>
 * @param <ID>
 */
public interface BaseDao<T, ID> {
    
    void salvarOuAlterar(T entidade, Session sessao)throws HibernateException;
    
    void excluir(T entidade, Session sessao)throws HibernateException;
    
    T pesquisarPorId(ID id, Session sessao)throws HibernateException;
    
    
}
