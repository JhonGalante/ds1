/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Ygor
 */
public enum CursoENUM {
    ADMINISTRACAO("Administração"),
    ENGENHARIA_PRODUCAO("Engenharia de Produção"),
    LICENCIATURA_MATEMATICA("Licenc. em Matemática"),
    SISTEMAS_INFORMACAO("Sistemas de Informação");
    
    private final String descricao;
    
    CursoENUM(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
