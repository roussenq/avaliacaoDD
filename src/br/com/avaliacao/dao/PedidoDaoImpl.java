/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacao.dao;

import br.com.avaliacao.modelo.Pedido;
import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


public class PedidoDaoImpl extends BaseDaoImpl<Pedido, Long> implements PedidoDao, Serializable {

    @Override
    public Pedido pesquisarPedidoPorNumero(String numeroPedido, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Pedido where numero = :numero");
        consulta.setParameter("numero", numeroPedido);
        return (Pedido) consulta.uniqueResult();
    }
   
    @Override
    public Pedido pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Pedido) sessao.get(Pedido.class, id);
    }
    
}
