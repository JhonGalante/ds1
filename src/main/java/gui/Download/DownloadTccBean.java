/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Download;


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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Ygor
 */
@ManagedBean
public class DownloadTccBean {
    
    @EJB
    MovimentacaoDao daoMovimentacao;
    @EJB
    TccInicioDao daoTccInicio;
    @EJB
    ArquivoTccDao daoArquivoTcc;
    
    private StreamedContent file;
     
    public DownloadTccBean() {        
    //    Ygor: Dúvida.
    //    InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/demo/images/boromir.jpg");
    //    file = new DefaultStreamedContent(stream, "image/jpg", "downloaded_boromir.jpg");
    }
 
    public StreamedContent getFile() {
        return file;
    }
    
    
}
