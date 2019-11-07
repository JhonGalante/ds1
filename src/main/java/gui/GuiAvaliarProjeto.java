package gui;

import dao.ProfessorDAO;
import dao.TCCIDAO;
import dao.TCCIIDAO;
import helper.Sessao;
import helper.TCCPadrao;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import model.EstadoTccENUM;
import model.MovimentacaoTCCI;
import model.MovimentacaoTCCII;
import model.Professor;
import model.TCCI;
import model.TCCII;
import org.apache.commons.lang.ArrayUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Jhonata Galante
 */
@ManagedBean
public class GuiAvaliarProjeto {
    
    private List<TCCPadrao> projetos;
    
    private TCCPadrao projetoSelecionado;

    
    private float nota;
    private boolean repo;
    private Date dataEntrega;
    private Date minDate;
    
    private final TCCIDAO tcciDAO = TCCIDAO.getInstance();
    private final TCCIIDAO tcciiDAO = TCCIIDAO.getInstance();
    private final ProfessorDAO professorDAO = ProfessorDAO.getInstance();
    private final Sessao sessao = Sessao.getInstance();
    
    public GuiAvaliarProjeto() throws Exception{
        Professor professorTemp = professorDAO.buscarMatricula(sessao.getUsuarioSessao().getMatricula());
        projetos = new ArrayList<>();
        minDate = new Date();
        
        for(TCCI tcci: tcciDAO.listar()){
            if(/*tcci.getEstadoTccENUM() == EstadoTccENUM.FINALIZADO &&*/
                    tcci.getProfessorTcc().equals(professorTemp) && 
                    tcci.getNota() == null && !tcci.getMovimentacoesTCC().isEmpty()){
                projetos.add(new TCCPadrao(tcci.getId(), 0,tcci.getTermoCompromisso(), 
                        tcci.getMovimentacoesTCC().get(tcci.getMovimentacoesTCC().size()-1).getDataHora(), 1));
            }
        }
        
        for(TCCII tccii: tcciiDAO.listar()){
            if(/*tccii.getEstadoTccENUM() == EstadoTccENUM.FINALIZADO &&*/
                    tccii.getProfessorTcc().equals(professorTemp) && 
                    tccii.getNota() == null && !tccii.getMovimentacoes().isEmpty()){
                projetos.add(new TCCPadrao(tccii.getId(), 0,tccii.getTermoCompromisso(), 
                        tccii.getMovimentacoes().get(tccii.getMovimentacoes().size()-1).getDataHora(), 2));
            }
        }
    }
    
    public void avaliarProjeto() throws Exception{
        if(projetoSelecionado.getEtapaTCC() == 1){
            TCCI tcci = tcciDAO.buscarPorId(projetoSelecionado.getId());
            tcci.setNota(nota);
            tcci.setDispRepo(repo);
            tcci.setEstadoTccENUM(EstadoTccENUM.FINALIZADO);
            tcciDAO.alterar(tcci);
        }else{
            TCCII tccii = tcciiDAO.buscarPorId(projetoSelecionado.getId());
            tccii.setNota(nota);
            tccii.setDispRepo(repo);
            tccii.setEstadoTccENUM(EstadoTccENUM.FINALIZADO);
            tcciiDAO.alterar(tccii);
        }
        addMessage("Nota definida com sucesso!");
        nota = 0;
        repo = false;
        projetos.remove(projetoSelecionado);
        projetoSelecionado = null;
    }
    
    public void definirNovaEntrega() throws Exception{
        if(projetoSelecionado.getEtapaTCC() == 1){
            TCCI tcci = tcciDAO.buscarPorId(projetoSelecionado.getId());
            tcci.setDataEntregaFinal(convertToLocalDateViaInstant(dataEntrega));
            tcci.setEstadoTccENUM(EstadoTccENUM.NOVA_ENTREGA);
            tcciDAO.alterar(tcci);
        }else{
            TCCII tccii = tcciiDAO.buscarPorId(projetoSelecionado.getId());
            tccii.setDataEntregaFinal(convertToLocalDateViaInstant(dataEntrega));
            tccii.setEstadoTccENUM(EstadoTccENUM.NOVA_ENTREGA);
            tcciiDAO.alterar(tccii);
        }
        addMessage("Nova data definida com sucesso!");
        projetoSelecionado = null;
    }
    
    public StreamedContent downloadTCCI(TCCI selectedTccI) throws FileNotFoundException{
        List <MovimentacaoTCCI> movimentacoes = selectedTccI.getMovimentacoesTCC();
        MovimentacaoTCCI ultimaMovimentacao = movimentacoes.get(movimentacoes.size()-1);
        byte[] arquivoByte = ArrayUtils.toPrimitive(ultimaMovimentacao.getArquivoMovimentacao().getBinario());
        
        InputStream stream = new ByteArrayInputStream(arquivoByte);
        
        return new DefaultStreamedContent(stream, "application/pdf", selectedTccI.getTermoCompromisso().getTema() + " - " + 
                                                                        selectedTccI.getTermoCompromisso().getAluno().getUsuario().getNome()+".pdf");
    }
    
    public StreamedContent downloadTCCII(TCCII selectedTccII){
        List <MovimentacaoTCCII> movimentacoes = selectedTccII.getMovimentacoes();
        MovimentacaoTCCII ultimaMovimentacao = movimentacoes.get(movimentacoes.size()-1);
        byte[] arquivoByte = ArrayUtils.toPrimitive(ultimaMovimentacao.getArquivoMovimentacao().getBinario());
        InputStream stream = new ByteArrayInputStream(arquivoByte);
        return new DefaultStreamedContent(stream, "application/pdf", selectedTccII.getTermoCompromisso().getTema() + " - " + 
                                                                        selectedTccII.getTermoCompromisso().getAluno().getUsuario().getNome()+".pdf");
    }
    
    public StreamedContent download() throws FileNotFoundException{
        if(projetoSelecionado.getEtapaTCC() == 1){
            TCCI tcci = tcciDAO.buscarPorId(projetoSelecionado.getId());
            return downloadTCCI(tcci);
        }else{
            TCCII tccii = tcciiDAO.buscarPorId(projetoSelecionado.getId());
            return downloadTCCII(tccii);
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
    
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage("Notificação", summary);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public boolean isRepo() {
        return repo;
    }

    public void setRepo(boolean repo) {
        this.repo = repo;
    }
    
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDate();
    }
    
}
