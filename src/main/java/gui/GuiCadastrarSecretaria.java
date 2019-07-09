/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.SecretariaDao;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Secretaria;

/**
 *
 * @author Ygor
 */
@SessionScoped
@ManagedBean
public class GuiCadastrarSecretaria {

    Secretaria secretaria = new Secretaria();
    private String mensagem;

    @EJB
    SecretariaDao daoSecretaria;

    public void cadastrarSecretaria() {
        daoSecretaria.create(secretaria);
        mensagemSucesso();
    }
    
    public void mensagemSucesso() {
        mensagem = secretaria.getNome() + " Cadastrado";
    }

    public Secretaria getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(Secretaria secretaria) {
        this.secretaria = secretaria;
    }

    public SecretariaDao getDaoSecretaria() {
        return daoSecretaria;
    }

    public void setDaoSecretaria(SecretariaDao daoSecretaria) {
        this.daoSecretaria = daoSecretaria;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
