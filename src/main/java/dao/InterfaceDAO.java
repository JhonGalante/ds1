/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

/**
 *
 * @author ygor.daudt
 */
public interface InterfaceDAO {
    public void incluir(Object objeto) throws Exception;
    public void alterar(Object objeto) throws Exception;
    public void excluir(Object objeto) throws Exception;
    public List<Object> listar() throws Exception;
}
