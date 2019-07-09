/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author jhona
 */
@Entity
public class ArquivoTramitacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UploadedFile arquivoTramitacao;
    private Byte[] binario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UploadedFile getArquivoTramitacao() {
        return arquivoTramitacao;
    }

    public void setArquivoTramitacao(UploadedFile arquivoTramitacao) {
        this.arquivoTramitacao = arquivoTramitacao;
    }

    public Byte[] getBinario() {
        return binario;
    }

    public void setBinario(Byte[] binario) {
        this.binario = binario;
    }
}
