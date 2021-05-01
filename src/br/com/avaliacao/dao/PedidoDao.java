/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacao.dao;

import br.com.avaliacao.modelo.Pedido;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author David
 */
public interface PedidoDao extends BaseDao<Pedido, Long>{
    
    Pedido pesquisarPedidoPorNumero(String numeroPedido, Session sessao) throws HibernateException;
    
}
