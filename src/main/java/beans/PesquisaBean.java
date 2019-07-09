/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.TccFinalDao;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import model.ArquivoTcc;
import model.TccFinal;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Jardelmc
 */

@ManagedBean(name="dtFilterView")
@ViewScoped
@SessionScoped
public class PesquisaBean implements Serializable{
    
   @EJB
   TccFinalDao daoTccFinal;
   
   private StreamedContent file;
   private List<TccFinal> tccFinals;
   private List<TccFinal> filtrados;
   
   public List<TccFinal> pesquisaTcc(String palavra) {
       
      List<TccFinal> tccFinal = daoTccFinal.findAll();
      List<TccFinal> retorno = new ArrayList<>();
       
       for (TccFinal tcc : tccFinal) {
           if( tcc.getAutor().getNome().contains(palavra) || 
                   tcc.getOrientador().getNome().contains(palavra) ||
                   tcc.getProfessorTcc().getNome().contains(palavra) ||
                   tcc.getTema().contains(palavra) ||
                   tcc.getTitulo().contains(palavra)) {
                       retorno.add(tcc);
           }
       }
       return retorno;
   }
   
   public void download(TccFinal download){
        try {
            ArquivoTcc tcc = download.getTcc();
            byte[] arquivo = tcc.getTcc();
            arquivo.getClass().getResourceAsStream("tcc");
            InputStream stream = new ByteArrayInputStream(arquivo);
            file = new DefaultStreamedContent(stream, "application/pdf", "download.pdf");
            
            
        } catch (Exception e) {
        }
    }
   
   
    
}
