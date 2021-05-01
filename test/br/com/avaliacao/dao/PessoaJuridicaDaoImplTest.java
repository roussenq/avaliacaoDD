/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacao.dao;

import br.com.avaliacao.modelo.Endereco;
import br.com.avaliacao.modelo.PessoaJuridica;
import static br.com.utilitario.UtilGerador.*;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David
 */
public class PessoaJuridicaDaoImplTest {

    private PessoaJuridica pessoaJuridica;
    private PessoaJuridicaDao pessoaJuridicaDao;
    private Session sessao;

    public PessoaJuridicaDaoImplTest() {
        pessoaJuridicaDao = new PessoaJuridicaDaoImpl();
    }

    //@Test
    public void testSalvar() {
        System.out.println("Salvar");
        pessoaJuridica = new PessoaJuridica(
                null,
                gerarNome(),
                gerarEmail(),
                gerarTelefoneFixo(),
                gerarNumero(2) + "." + gerarNumero(3) + "." + gerarNumero(3) + "/" + gerarNumero(4) + "-" + gerarNumero(2),
                gerarNumero(8)
        );
        Endereco endereco = gerarEndereco();
        pessoaJuridica.setEndereco(endereco);
        sessao = HibernateUtil.abrirConexao();
        pessoaJuridicaDao.salvarOuAlterar(pessoaJuridica, sessao);
        sessao.close();

        assertNotNull(pessoaJuridica.getId());
        assertNotNull(pessoaJuridica.getEndereco().getId());
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
        endereco.setCliente(pessoaJuridica);
        return endereco;
    }

    @Test
    public void testAlterar() {
        System.out.println("Alterar");

        buscaPessoaJuridicaBd();

        pessoaJuridica.setNome(gerarNome() + " LTDA");

        sessao = HibernateUtil.abrirConexao();
        pessoaJuridicaDao.salvarOuAlterar(pessoaJuridica, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirConexao();
        PessoaJuridica pjAlterado = pessoaJuridicaDao.pesquisarPorId(pessoaJuridica.getId(), sessao);
        sessao.close();

        assertEquals(pessoaJuridica.getNome(), pjAlterado.getNome());
    }

    @Test
    public void testPesquisarPessoaFisicaPorNome() {
        System.out.println("pesquisarPessoaFisicaPorNome");

        buscaPessoaJuridicaBd();

        String nome = pessoaJuridica.getNome();
        int letra = nome.indexOf(" ");
        nome = nome.substring(0, letra);

        sessao = HibernateUtil.abrirConexao();
        List<PessoaJuridica> pessoasPj = pessoaJuridicaDao.pesquisarPessoaJuridicaPorNome(nome, sessao);
        sessao.close();

        assertTrue(!pessoasPj.isEmpty());
    }

    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");

        buscaPessoaJuridicaBd();

        sessao = HibernateUtil.abrirConexao();
        PessoaJuridica pjPesquisa = pessoaJuridicaDao.pesquisarPorId(pessoaJuridica.getId(), sessao);
        sessao.close();

        assertNotNull(pjPesquisa);
    }

    @Test
    public void testPesquisarPessoaJuridicaPorCnpj() {
        System.out.println("pesquisarPessoaJuridicaPorCnpj");
        buscaPessoaJuridicaBd();
        sessao = HibernateUtil.abrirConexao();
        PessoaJuridica pessoaPesquisada = pessoaJuridicaDao.pesquisarPessoaJuridicaPorCnpj(pessoaJuridica.getCnpj(), sessao);
        sessao.close();

        assertNotNull(pessoaPesquisada);
        assertNotNull(pessoaPesquisada.getEndereco().getId());
    }

    @Test
    public void testExcluir() {
        System.out.println("Excluir");

        buscaPessoaJuridicaBd();

        sessao = HibernateUtil.abrirConexao();
        pessoaJuridicaDao.excluir(pessoaJuridica, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirConexao();
        PessoaJuridica pjExcluido = pessoaJuridicaDao.pesquisarPorId(pessoaJuridica.getId(), sessao);
        sessao.close();

        assertNull(pjExcluido);
    }

    public PessoaJuridica buscaPessoaJuridicaBd() {

        sessao = HibernateUtil.abrirConexao();
        Query consulta = sessao.createQuery("from PessoaJuridica p join fetch p.endereco");
        List<PessoaJuridica> pessoasPj = consulta.list();
        sessao.close();

        if (pessoasPj.isEmpty()) {
            testSalvar();
        } else {
            pessoaJuridica = pessoasPj.get(0);
        }

        return pessoaJuridica;
    }

}
