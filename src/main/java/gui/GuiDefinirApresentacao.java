/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.AlunoDAO;
import dao.ApresentacaoTCCDAO;
import dao.ProfessorDAO;
import dao.TCCIDAO;
import dao.TCCIIDAO;
import dao.TermoCompromissoDAO;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import model.Aluno;
import model.ApresentacaoTCC;
import model.Professor;
import model.TCCI;
import model.TCCII;
import model.TermoCompromisso;

/**
 *
 * @author jhonata
 */

@ManagedBean
public class GuiDefinirApresentacao {
    private List<Professor> banca;
    private List<Professor> professores;
    private Date data;
    private LocalDate dataApresentacao;
    private TCCI tcci;
    private TCCII tccii;
    private ApresentacaoTCC apresentacao;
    private List<Aluno> alunos;
    private List<Aluno> alunosDisp;
    private Aluno alunoSelecionado;
    private TermoCompromisso termoAluno;
    
    private final ProfessorDAO professorDao = ProfessorDAO.getInstance();
    private final TCCIDAO tccIDao = TCCIDAO.getInstance();
    private final TCCIIDAO tccIIDao = TCCIIDAO.getInstance();
    private final ApresentacaoTCCDAO apresentacaoDAO = ApresentacaoTCCDAO.getInstance();
    private final AlunoDAO alunoDao = AlunoDAO.getInstance();
    private final TermoCompromissoDAO compromissoDAO = TermoCompromissoDAO.getInstance();
    
    
    public GuiDefinirApresentacao() throws Exception{
        professores = professorDao.listar();
        alunos = alunoDao.listar();
        alunosDisp = new ArrayList<Aluno>();
        
        for(Aluno aluno: alunos){
            if(compromissoDAO.pesquisarPorAluno(aluno) != null){
                alunosDisp.add(aluno);
            }
        }
    }
    
    public void agendar() throws Exception{
        
        termoAluno = compromissoDAO.pesquisarPorAluno(alunoSelecionado);
        
        if(tccIIDao.buscarPorTermo(termoAluno) != null){
            tccii = tccIIDao.buscarPorTermo(termoAluno);
        }else{
            tcci = tccIDao.buscarPorTermo(termoAluno);
        }
        
        apresentacao = new ApresentacaoTCC();
        dataApresentacao = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        apresentacao.setDataApresentacao(dataApresentacao);
        apresentacao.setProfessoresBanca(banca);
        
        try{
            apresentacaoDAO.incluir(apresentacao);
            incluirApresentacao(apresentacao);
        }catch(Exception ex){
            ex.printStackTrace();
            mensagemErro("Erro ao agendar a apresentação");
            return;
        }

        limparCampos();
        
    }
    
    public void mensagemConfirma(String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",  "Mensagem: " + mensagem) );
    }
    
    public void mensagemErro(String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Solicitação Inválida",  "Mensagem: " + mensagem) );
    }
    
    public void incluirApresentacao(ApresentacaoTCC apresentacao) throws Exception{
        if(tcci != null){
            tcci.setApresentacao(apresentacao);
            tccIDao.alterar(tcci);
            mensagemConfirma("Apresentação agendada com sucesso!");
        }else if(tccii != null) {
            tccii.setApresentacao(apresentacao);
            tccIIDao.alterar(tccii);
            mensagemConfirma("Apresentação agendada com sucesso!");
        }else{
            mensagemErro("Nenhum TCC cadastrado para esse aluno");
        }
    }

    public List<Professor> getBanca() {
        return banca;
    }

    public void setBanca(List<Professor> banca) {
        this.banca = banca;
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public void setProfessores(List<Professor> professores) {
        this.professores = professores;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public LocalDate getDataApresentacao() {
        return dataApresentacao;
    }

    public void setDataApresentacao(LocalDate dataApresentacao) {
        this.dataApresentacao = dataApresentacao;
    }

    public List<Aluno> getAlunosDisp() {
        return alunosDisp;
    }

    public void setAlunosDisp(List<Aluno> alunosDisp) {
        this.alunosDisp = alunosDisp;
    }

    public Aluno getAlunoSeleciado() {
        return alunoSelecionado;
    }

    public void setAlunoSeleciado(Aluno alunoSeleciado) {
        this.alunoSelecionado = alunoSeleciado;
    }

    private void limparCampos() {
        dataApresentacao = null;
        banca.removeAll(banca);
    }
    
}
