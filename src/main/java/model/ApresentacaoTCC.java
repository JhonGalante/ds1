/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ygor
 */
@Entity
public class ApresentacaoTCC implements Serializable {
    
    //Atributos
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private LocalDate dataApresentacao;
    @NotNull
    private String localApresentacao;
    
    @NotNull
    @ManyToMany
    @JoinTable(
            name="apresentacaotcc_professor",
            joinColumns=@JoinColumn(name="apresentacaotcc_id"),
            inverseJoinColumns=@JoinColumn(name="professoresbanca_id")
            )
    private List<Professor> professoresBanca;

    
    //Métodos
    public Long getId() {
        return id;
    }

    public LocalDate getDataApresentacao() {
        return dataApresentacao;
    }

    public void setDataApresentacao(LocalDate dataApresentacao) {
        this.dataApresentacao = dataApresentacao;
    }

    public List<Professor> getProfessoresBanca() {
        return professoresBanca;
    }

    public void setProfessoresBanca(List<Professor> professoresBanca) {
        this.professoresBanca = professoresBanca;
    }

    public String getLocalApresentacao() {
        return localApresentacao;
    }

    public void setLocalApresentacao(String localApresentacao) {
        this.localApresentacao = localApresentacao;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ApresentacaoTCC)) {
            return false;
        }
        ApresentacaoTCC other = (ApresentacaoTCC) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return dataApresentacao.toString();
    }
}
