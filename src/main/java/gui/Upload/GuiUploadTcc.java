/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Upload;

import dao.ArquivoTccDao;
import dao.MovimentacaoDao;
import dao.TccInicioDao;
import java.time.LocalDate;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import model.ArquivoTcc;
import model.Movimentacao;
import model.Usuario;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Jardelmc
 */
@ManagedBean
public class GuiUploadTcc {
    
    @EJB
    MovimentacaoDao daoMovimentacao;
    @EJB
    TccInicioDao daoTccInicio;
    @EJB
    ArquivoTccDao daoArquivoTcc;
   
    
    private UploadedFile tcc;
    
    public UploadedFile getTcc() {
        return tcc;
    }
    
    public void setTcc(UploadedFile file) {
        this.tcc = file;
    }
    
    public void upload() {
        if(tcc != null) {
            FacesMessage message = new FacesMessage("Succesful", tcc.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            Movimentacao movimentacao = new Movimentacao();
            movimentacao.setInformacoesTcc(daoTccInicio.tccInicio(new Usuario().getAluno()));
            ArquivoTcc arquivoTcc = new ArquivoTcc();
            arquivoTcc.setArquivoTcc(tcc);
            daoArquivoTcc.create(arquivoTcc);
            movimentacao.setTcc(arquivoTcc);
            movimentacao.setData(LocalDate.now());
            daoMovimentacao.create(movimentacao);
        }
    }
    
    
}
