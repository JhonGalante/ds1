/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.TCCIDAO;
import dao.TCCIIDAO;
import helper.Sessao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import model.TCCI;
import model.TCCII;
import model.TipoUsuarioENUM;
import model.Usuario;

/**
 *
 * @author jhonata
 */

@ManagedBean
public class GuiPesquisarTCC implements Serializable {
    
    private List tccs;
    
    private List tccsFiltred;
    private String campoPesquisar;
    private Sessao sessao;
    
    private final TCCIDAO tcciDao = TCCIDAO.getInstance();
    private final TCCIIDAO tcciiDao = TCCIIDAO.getInstance();
    
    public GuiPesquisarTCC() throws Exception{
        sessao = new Sessao();
        try{
            Usuario usuario = sessao.getUsuarioSessao();
            if(usuario.getTipo() == TipoUsuarioENUM.ALUNO){
                tccs = preencherListaTccsAluno();
            }
            if(usuario.getTipo() == TipoUsuarioENUM.PROFESSOR){
                tccs = preencherListaTccsProfessor();
            }
        }catch(NullPointerException ex){
            ex.printStackTrace();
            tccs = preencherListaTccsVisitante();
        }
    }

    public List getTccs() {
        return tccs;
    }

    public void setTccs(List tccs) {
        this.tccs = tccs;
    }

    public String getCampoPesquisar() {
        return campoPesquisar;
    }

    public void setCampoPesquisar(String campoPesquisar) {
        this.campoPesquisar = campoPesquisar;
    }

    public List getTccsFiltred() {
        return tccsFiltred;
    }

    public void setTccsFiltred(List tccsFiltred) {
        this.tccsFiltred = tccsFiltred;
    }
    
    private List preencherListaTccsVisitante() throws Exception{
        List tccs = new ArrayList<>();
        for(TCCII tccii: tcciiDao.listar()){
            if(tccii.isDispRepo()){
                tccs.add(tccii);
            }
        }
        return tccs;
    }
    
    private List preencherListaTccsAluno() throws Exception{
        List tccs = new ArrayList<>();
        
        for(TCCI tcci: tcciDao.listar()){
            if(tcci.isDispRepo()){
                tccs.add(tcci);
            }
        }
  
        for(TCCII tccii: tcciiDao.listar()){
            if(tccii.isDispRepo()){
                tccs.add(tccii);
            }
        }
        
        return tccs;
    }
    
    private List preencherListaTccsProfessor() throws Exception{
        List tccs = new ArrayList<>();
        for(TCCI tcci: tcciDao.listar()){
            tccs.add(tcci);
        }
  
        for(TCCII tccii: tcciiDao.listar()){
            tccs.add(tccii);
        }
        return tccs;
    }

}
