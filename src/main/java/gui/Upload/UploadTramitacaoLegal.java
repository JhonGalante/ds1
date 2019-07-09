/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Upload;

import dao.ArquivoTramitacaoDao;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import model.ArquivoTramitacao;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Jhonata
 */

@ManagedBean (name = "uploadTramitacaoLegal")
public class UploadTramitacaoLegal {
    @EJB
    private ArquivoTramitacaoDao tramitacaoDao;
    
    private UploadedFile file;
 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
     
    public void upload() {
        if(file != null) {
            FacesMessage message = new FacesMessage("Sucesso, ", file.getFileName() + " foi enviado.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            ArquivoTramitacao tramitacao = new ArquivoTramitacao();
            tramitacao.setArquivoTramitacao(file);
            tramitacaoDao.create(tramitacao);
        }
    }
}
