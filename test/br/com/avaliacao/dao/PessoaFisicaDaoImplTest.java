/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacao.dao;

import br.com.avaliacao.modelo.Endereco;
import br.com.avaliacao.modelo.PessoaFisica;
import static br.com.utilitario.UtilGerador.*;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author David
 */
public class PessoaFisicaDaoImplTest {

    private PessoaFisica pessoaFisica;
    private PessoaFisicaDao pessoaFisicaDao;
    private Session sessao;

    public PessoaFisicaDaoImplTest() {
        pessoaFisicaDao = new PessoaFisicaDaoImpl();
    }

    //@Test
    public void testSalvar() {
        System.out.println("Salvar");

        pessoaFisica = new PessoaFisica(
                null,
                gerarNome(),
                gerarEmail(),
                gerarTelefoneFixo(),
                gerarNumero(3) + "." + gerarNumero(3) + "." + gerarNumero(3) + "-" + gerarNumero(2),
                gerarNumero(7)
        );
        Endereco endereco = gerarEndereco();
        pessoaFisica.setEndereco(endereco);
        sessao = HibernateUtil.abrirConexao();
        pessoaFisicaDao.salvarOuAlterar(pessoaFisica, sessao);
        sessao.close();

        assertNotNull(pessoaFisica.getId());
        assertNotNull(pessoaFisica.getEndereco().getId());
    }

    private Endereco gerarEndereco() {
        Endereco endereco = new Endereco(
                null,
                "Rua " + gerarNome(),
                gerarNumero(5) + "-" + gerarNumero(3),
                "Centro",
                gerarCidade(),
                "SC",
                "Casa dos fundos",
                "Bla obs reta dobra rua"
        );
        endereco.setCliente(pessoaFisica);
        return endereco;
    }

    @Test
    public void testAlterar() {
        System.out.println("Alterar");

        buscaPessoaFisicaBd();

        pessoaFisica.setNome(gerarNome());

        sessao = HibernateUtil.abrirConexao();
        pessoaFisicaDao.salvarOuAlterar(pessoaFisica, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirConexao();
        PessoaFisica pfAlterado = pessoaFisicaDao.pesquisarPorId(pessoaFisica.getId(), sessao);
        sessao.close();

        assertEquals(pessoaFisica.getNome(), pfAlterado.getNome());
    }

    @Test
    public void testPesquisarPessoaFisicaPorNome() {
        System.out.println("pesquisarPessoaFisicaPorNome");

        buscaPessoaFisicaBd();

        String nome = pessoaFisica.getNome();
        int letra = nome.indexOf(" ");
        nome = nome.substring(0, letra);

        sessao = HibernateUtil.abrirConexao();
        List<PessoaFisica> pessoasPf = pessoaFisicaDao.pesquisarPessoaFisicaPorNome(nome, sessao);
        sessao.close();

        assertTrue(!pessoasPf.isEmpty());
    }

    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");

        buscaPessoaFisicaBd();

        sessao = HibernateUtil.abrirConexao();
        PessoaFisica pfPesquisa = pessoaFisicaDao.pesquisarPorId(pessoaFisica.getId(), sessao);
        sessao.close();

        assertNotNull(pfPesquisa);
    }

    @Test
    public void testPesquisarPessoaFisicaPorCpf() {
        System.out.println("pesquisarPessoaFisicaPorCpf");
        buscaPessoaFisicaBd();
        sessao = HibernateUtil.abrirConexao();
        PessoaFisica pessoaPesquisada = pessoaFisicaDao.pesquisarPessoaFisicaPorCpf(pessoaFisica.getCpf(), sessao);
        sessao.close();

        System.out.println("cliente :" + pessoaPesquisada.getNome() + " \ncpf:" + pessoaPesquisada.getCpf());
        System.out.println("Endereço:" + pessoaPesquisada.getEndereco().getLogradouro());
        System.out.println("Pedido Nº:" + pessoaPesquisada.getPedidos().get(0).getNumero() + " \nTotal R$:" + pessoaPesquisada.getPedidos().get(0).getTotal());
        assertNotNull(pessoaPesquisada);
        assertNotNull(pessoaPesquisada.getEndereco().getId());

    }

    @Test
    public void testExcluir() {
        System.out.println("Excluir");

        buscaPessoaFisicaBd();

        sessao = HibernateUtil.abrirConexao();
        pessoaFisicaDao.excluir(pessoaFisica, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirConexao();
        PessoaFisica pfExcluido = pessoaFisicaDao.pesquisarPorId(pessoaFisica.getId(), sessao);
        sessao.close();

        assertNull(pfExcluido);
    }

    public PessoaFisica buscaPessoaFisicaBd() {

        sessao = HibernateUtil.abrirConexao();
        Query consulta = sessao.createQuery("from PessoaFisica p join fetch p.endereco");
        List<PessoaFisica> pessoasPf = consulta.list();
        sessao.close();

        if (pessoasPf.isEmpty()) {
            testSalvar();
        } else {
            pessoaFisica = pessoasPf.get(0);
        }

        return pessoaFisica;
    }

}
