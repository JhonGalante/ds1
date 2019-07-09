/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Jardelmc
 */
@Entity
public class ArquivoTcc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] tcc;
    private UploadedFile arquivoTcc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getTcc() {
        return tcc;
    }

    public void setTcc(byte[] tcc) {
        this.tcc = tcc;
    }

    public UploadedFile getArquivoTcc() {
        return arquivoTcc;
    }

    public void setArquivoTcc(UploadedFile arquivoTcc) {
        this.arquivoTcc = arquivoTcc;
    }


}
