/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendamento.model;

import java.util.Date;

/**
 *
 * @author Luana Mora
 */
public class Agendamento {

    private int codigoAgendamento;
    private Veiculo codigoVeiculo;
    private String descricaoOrigem;
    private String descricaoDestino;
    private Motorista codigoMotorista;
    private Funcionario codigoFuncionario;
    private Date dataRetorno;
    private Date dataSaida;
    private int numeroPassageiros;
    private String observacao;

    public int getCodigoAgendamento() {
        return codigoAgendamento;
    }

    public void setCodigoAgendamento(int codigoAgendamento) {
        this.codigoAgendamento = codigoAgendamento;
    }

    public Veiculo getCodigoVeiculo() {
        return codigoVeiculo;
    }

    public void setCodigoVeiculo(Veiculo codigoVeiculo) {
        this.codigoVeiculo = codigoVeiculo;
    }

    public String getDescricaoOrigem() {
        return descricaoOrigem;
    }

    public void setDescricaoOrigem(String descricaoOrigem) {
        this.descricaoOrigem = descricaoOrigem;
    }

    public String getDescricaoDestino() {
        return descricaoDestino;
    }

    public void setDescricaoDestino(String descricaoDestino) {
        this.descricaoDestino = descricaoDestino;
    }

    public Motorista getCodigoMotorista() {
        return codigoMotorista;
    }

    public void setCodigoMotorista(Motorista codigoMotorista) {
        this.codigoMotorista = codigoMotorista;
    }

    public Funcionario getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public void setCodigoFuncionario(Funcionario codigoFuncionario) {
        this.codigoFuncionario = codigoFuncionario;
    }

    public Date getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(Date dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }
    
    

    public int getNumeroPassageiros() {
        return numeroPassageiros;
    }

    public void setNumeroPassageiros(int numeroPassageiros) {
        this.numeroPassageiros = numeroPassageiros;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
    
    
    
}
