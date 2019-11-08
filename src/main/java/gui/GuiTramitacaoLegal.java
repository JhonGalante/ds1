package gui;

import dao.TCCIDAO;
import dao.TCCIIDAO;
import helper.TCCPadrao;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.ArquivoTramitacao;
import model.TCCI;
import model.TCCII;
import org.apache.commons.lang.ArrayUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Jhonata Galante
 */
@ManagedBean
@ViewScoped
public class GuiTramitacaoLegal implements Serializable {

    private List<TCCPadrao> projetos;

    private TCCPadrao projetoSelecionado;
    private UploadedFile file;
    private HttpSession session;

    private final TCCIDAO tcciDAO = TCCIDAO.getInstance();
    private final TCCIIDAO tcciiDAO = TCCIIDAO.getInstance();

    public GuiTramitacaoLegal() throws Exception {
        projetos = new ArrayList<>();
        ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        
        for (TCCI tcci : tcciDAO.listar()) {
            if (tcci.getArquivoTramitacao() == null) {
                projetos.add(new TCCPadrao(tcci.getId(), tcci.getTermoCompromisso(), 1));
            }
        }

        for (TCCII tccii : tcciiDAO.listar()) {
            if (tccii.getArquivoTramitacao() == null) {
                projetos.add(new TCCPadrao(tccii.getId(), tccii.getTermoCompromisso(), 2));
            }
        }
    }

    public void selecionarProjeto() {
        
    }

    public void realizarUpload(FileUploadEvent event) {
        
        file = event.getFile();
        if (projetoSelecionado != null && projetoSelecionado.getEtapaTCC() == 1) {
            uploadTCCI();
        } else if (projetoSelecionado != null && projetoSelecionado.getEtapaTCC() == 2) {
            uploadTCCII();
        } else {
            FacesMessage message = new FacesMessage("Erro", "Nenhum projeto selecionado");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        session.removeAttribute("projetoSelecionado");
        projetos.remove(projetoSelecionado);
        addMessage("Upado com sucesso!");
        PrimeFaces.current().ajax().update("j_idt4:form:msgs");
    }

    public void uploadTCCI() {
        ArquivoTramitacao arqTram = new ArquivoTramitacao();
        TCCI tcci = tcciDAO.buscarPorId(projetoSelecionado.getId());
        try {
            InputStream input = file.getInputstream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            for (int tamanho = 0; (tamanho = input.read(buffer)) > 0;) {
                output.write(buffer, 0, tamanho);
            }

            arqTram.setBinario(ArrayUtils.toObject(output.toByteArray()));
            tcci.setArquivoTramitacao(arqTram);
            tcciDAO.alterar(tcci);

        } catch (Exception ex) {
            ex.printStackTrace();
            FacesMessage message = new FacesMessage("Erro", "Por favor, tente novamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void uploadTCCII() {
        ArquivoTramitacao arqTram = new ArquivoTramitacao();
        TCCII tccii = tcciiDAO.buscarPorId(projetoSelecionado.getId());
        try {
            InputStream input = file.getInputstream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            for (int tamanho = 0; (tamanho = input.read(buffer)) > 0;) {
                output.write(buffer, 0, tamanho);
            }

            arqTram.setBinario(ArrayUtils.toObject(output.toByteArray()));
            tccii.setArquivoTramitacao(arqTram);
            tcciiDAO.alterar(tccii);

        } catch (Exception ex) {
            ex.printStackTrace();
            FacesMessage message = new FacesMessage("Erro", "Por favor, tente novamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    

    public List<TCCPadrao> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<TCCPadrao> projetos) {
        this.projetos = projetos;
    }

    public TCCPadrao getProjetoSelecionado() {
        return projetoSelecionado;
    }

    public void setProjetoSelecionado(TCCPadrao projetoSelecionado) {
        this.projetoSelecionado = projetoSelecionado;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage("Notificação", summary);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
