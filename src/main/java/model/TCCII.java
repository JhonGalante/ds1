/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Ronald Tadeu
 */
public class TCCII implements Serializable {
    
    private TCCI tcci;
    private List<String> professoresBanca; //lista em string para caso de professsores de fora da faculdade
    private float nota;
    private LocalDate dataApresentacao;
    private EstadoTccENUM estado;
    
}
