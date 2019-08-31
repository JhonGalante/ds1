/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.TCCIDAO;
import java.util.List;
import javax.faces.bean.ManagedBean;
import model.ArquivoMovimentacao;
import model.MovimentacaoTCCI;
import model.TCCI;
import model.TipoMovimentacaoENUM;
import org.apache.commons.lang.ArrayUtils;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author jhonata
 */

@ManagedBean
public class GuiControleTCCIAluno {
    
    private UploadedFile file;
    private String comentario;
    private TCCI tcc;
    private TCCIDAO dao = TCCIDAO.getInstance();
    
    public void uploadTCC() throws Exception{
        MovimentacaoTCCI mov = new MovimentacaoTCCI();
        ArquivoMovimentacao arqMov = new ArquivoMovimentacao();
        
        //Cria novo arquivo binario, converte para Byte, e seta todos os atributos da
        //classe MovimentacaoTCC
        byte[] bytes = file.getContents();
        Byte[] byteObject = ArrayUtils.toObject(bytes);
        arqMov.setBinario(byteObject);
        mov.setArquivoMovimentacao(arqMov);
        mov.setComentario(comentario);
        mov.setTipoMovimentacaoENUM(TipoMovimentacaoENUM.ENTREGA);
        
        //Puxa a lista de movimentações do objeto TCC, adiciona a nova movimentacao a lista e retorna ao objeto
        List<MovimentacaoTCCI> movimentacoes = tcc.getMovimentacoes();
        movimentacoes.add(mov);
        tcc.setMovimentacoes(movimentacoes);
        
        //Atualiza o objeto no banco
        dao.alterar(tcc);
    }
}
