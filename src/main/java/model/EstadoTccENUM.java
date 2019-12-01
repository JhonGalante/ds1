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
public enum EstadoTccENUM {
    ENTREGA, // professor aceitou a solicitação e aguarda o envio
    NOVA_ENTREGA, // aluno envia nova versão para análise
    ANALISE, // professor analisa o envio do aluno
    AGUARDANDO_NOTA, // aluno marcou o checkbox de versão final
    FINALIZADO // professor avaliou e finalizou o tcc, tanto para aprovado quanto reprovado
}
