/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacao.dao;

import br.com.avaliacao.modelo.Cliente;
import br.com.avaliacao.modelo.Pedido;
import static br.com.utilitario.UtilGerador.*;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David
 */
public class PedidoDaoImplTest {

    private Pedido pedido;
    private PedidoDao pedidoDao;
    private Session sessao;
    private int numeroPedido;
    private Cliente cliente;

    public PedidoDaoImplTest() {
        pedidoDao = new PedidoDaoImpl();
    }

    @Test
    public void testSalvar() {
        System.out.println("salvar Pedido");

        buscaClienteBd();

        gerarNumeroPedido();
        pedido = new Pedido(
                null,
                Integer.toString(numeroPedido),
                Double.parseDouble(gerarNumero(2)),
                new Date()
        );
        pedido.setCliente(cliente);

        sessao = HibernateUtil.abrirConexao();
        pedidoDao.salvarOuAlterar(pedido, sessao);
        sessao.close();
        assertNotNull(pedido.getId());
    }

    //@Test
    public void testAlterar() {
        System.out.println("alterar Pedido");

        buscarPedidoBd();
        pedido.setTotal(Double.parseDouble(gerarNumero(3)));

        sessao = HibernateUtil.abrirConexao();
        pedidoDao.salvarOuAlterar(pedido, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirConexao();
        Pedido pedidoAlt = pedidoDao.pesquisarPorId(pedido.getId(), sessao);
        sessao.close();

        assertEquals(Double.valueOf(pedido.getTotal()), Double.valueOf(pedidoAlt.getTotal()));
    }

   // @Test
    public void testExcluir() {
        System.out.println("excluir Pedido");

        buscarPedidoBd();

        sessao = HibernateUtil.abrirConexao();
        pedidoDao.excluir(pedido, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirConexao();
        Pedido pedidoExcluido = pedidoDao.pesquisarPorId(pedido.getId(), sessao);
        sessao.close();

        assertNull(pedidoExcluido);
    }

   // @Test
    public void testPesquisarPedidoPorNumero() {
        System.out.println("pesquisarPedidoPorNumero");

        buscarPedidoBd();

        sessao = HibernateUtil.abrirConexao();
        Pedido pedidoPesquisado = pedidoDao.pesquisarPedidoPorNumero(pedido.getNumero(), sessao);
        sessao.close();
      
        assertNotNull(pedidoPesquisado);
        assertNotNull(pedidoPesquisado.getCliente().getEndereco().getId());

    }

    public Pedido buscarPedidoBd() {
        List<Pedido> pedidos = pesquisarPedidoBD();
        if (pedidos.isEmpty()) {
            testSalvar();
        } else {
            pedido = pedidos.get(0);
        }
        return pedido;
    }

    private List<Pedido> pesquisarPedidoBD() throws HibernateException {
        sessao = HibernateUtil.abrirConexao();
        Query consulta = sessao.createQuery("from Pedido");
        List<Pedido> pedidos = consulta.list();
        sessao.close();
        return pedidos;
    }

    private void gerarNumeroPedido() {
        List<Pedido> pedidos = pesquisarPedidoBD();
        if (pedidos.isEmpty()) {
            numeroPedido = 1;
        } else {
            int tamanhoPedido = pedidos.size();
            numeroPedido = tamanhoPedido + 1;
        }
    }

    public Cliente buscaClienteBd() {

        sessao = HibernateUtil.abrirConexao();
        Query consulta = sessao.createQuery("from Cliente");
        List<Cliente> clientes = consulta.list();
        sessao.close();

        if (clientes.isEmpty()) {
            PessoaFisicaDaoImplTest pF = new PessoaFisicaDaoImplTest();
            pF.testSalvar();
            PessoaJuridicaDaoImplTest pJ = new PessoaJuridicaDaoImplTest();
            pJ.testSalvar();
        } else {
            cliente = clientes.get(0);
        }
        return cliente;
    }

}
