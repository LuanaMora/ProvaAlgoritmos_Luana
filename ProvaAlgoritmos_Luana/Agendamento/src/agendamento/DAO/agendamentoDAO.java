/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendamento.DAO;

import agendamento.jdbc.ConnectionFactory;
import agendamento.model.Agendamento;
import agendamento.model.Funcionario;
import agendamento.model.Motorista;
import agendamento.model.Veiculo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luana Mora
 */
public class agendamentoDAO implements GenericDAO<Agendamento> {

    private Connection connection = null;

    @Override
    public void save(Agendamento entity) throws SQLException {
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO agendamento(  cd_agendamento, cd_veiculo, ")
                    .append("ds_origem, ds_destino, cd_motorista, cd_funcionario, dt_saida, dt_retorno,")
                    .append("nr_passageiros, ds_observacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            pstm.setInt(1, entity.getCodigoAgendamento());
            pstm.setInt(2, entity.getCodigoVeiculo().getCodigoVeiculo());
            pstm.setString(3, entity.getDescricaoOrigem());
            pstm.setString(4, entity.getDescricaoDestino());
            pstm.setInt(5, entity.getCodigoMotorista().getCodigo());
            pstm.setInt(6, entity.getCodigoFuncionario().getCodigo());
            pstm.setDate(7, (Date) entity.getDataRetorno()); //ISSO VAI DAR PROBLEMA AINDA
            pstm.setDate(8, (Date) entity.getDataSaida()); //ISSO VAI DAR PROBLEMA AINDA
            pstm.setInt(9, entity.getNumeroPassageiros());
            pstm.setString(10, entity.getObservacao());

            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir Agendamento");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Erro inesperado ao Inserir Agendamento");
            ex.printStackTrace();
        } finally {
            connection.close();
        }
    }

    @Override
    public void update(Agendamento entity) throws SQLException {
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE agendamento")
                    .append("   SET  cd_veiculo=?, ds_origem=?, ds_destino=?, cd_motorista=?, ")
                    .append("       cd_funcionario=?, dt_saida=?, dt_retorno=?, nr_passageiros=?, ")
                    .append("       ds_observacao=? ")
                    .append(" WHERE where cd_agendamento=?;");

            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            pstm.setInt(1, entity.getCodigoAgendamento());
            pstm.setInt(2, entity.getCodigoVeiculo().getCodigoVeiculo());
            pstm.setString(3, entity.getDescricaoOrigem());
            pstm.setString(4, entity.getDescricaoDestino());
            pstm.setInt(5, entity.getCodigoMotorista().getCodigo());
            pstm.setInt(6, entity.getCodigoFuncionario().getCodigo());
            pstm.setDate(7, (Date) entity.getDataRetorno()); //ISSO VAI DAR PROBLEMA AINDA
            pstm.setDate(8, (Date) entity.getDataSaida()); //ISSO VAI DAR PROBLEMA AINDA
            pstm.setInt(9, entity.getNumeroPassageiros());
            pstm.setString(10, entity.getObservacao());
            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao Atualizar Agendamento");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Erro inesperado ao Atualizar Agendamento");
            ex.printStackTrace();
        } finally {
            connection.close();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM AGENDAMENTO WHERE CD_AGENDAMENTO = " + id + ";");
            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao Deletar Agendamento");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Erro inesperado ao Deletar Agendamento");
            ex.printStackTrace();
        } finally {
            this.connection.close();
        }
    }

    @Override
    public Agendamento getById(int id) throws SQLException {
        Agendamento agendamento = null;
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            String sql = "SELECT * FROM AGENDAMENTO WHERE CD_AGENDAMENTO = " + id;
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            agendamento = new Agendamento();
            while (rs.next()) {
                agendamento.setCodigoAgendamento(rs.getInt("CD_CANDIDATO"));
                agendamento.setCodigoVeiculo(populaVeiculo(rs.getInt("CD_VEICULO")));
                agendamento.setDescricaoOrigem(rs.getString("DS_ORIGEM"));
                agendamento.setDescricaoDestino(rs.getString("DS_DESTINO"));
                agendamento.setCodigoMotorista(populaMotorista(rs.getInt("CD_MOTORISTA")));
                agendamento.setCodigoFuncionario(populaFuncionario(rs.getInt("CD_FUNCIONARIO")));
                agendamento.setDataSaida(rs.getDate("DT_SAIDA"));
                agendamento.setDataRetorno(rs.getDate("DT_RETORNO"));
                agendamento.setNumeroPassageiros(rs.getInt("NR_PASSAGEIROS"));
                agendamento.setObservacao(rs.getString("DS_OBSERVACAO"));
            }
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao Atualizar Agendamento");
            ex.printStackTrace();
        } finally {
            this.connection.close();
        }
        return agendamento;
    }

    @Override
    public List<Agendamento> getByName(String name) throws SQLException {
        Agendamento agendamento = null;
        List<Agendamento> agendamentoList = new ArrayList<>();
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT A.CD_FUNCIONARIO, F.NM_FUNCIONARIO ")
                    .append("FROM AGENDAMENTO AS A")
                    .append(" inner join partido as p")
                    .append(" INNER JOIN FUNCIONARIO AS F ON A.CD_FUNCIONARIO = F.CD_FUNCIONARIO")
                    .append(" WHERE F.NM_FUNCIONARIO LIKE '%" + name + "%'");

            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                agendamento.setCodigoAgendamento(rs.getInt("CD_CANDIDATO"));
                agendamento.setCodigoVeiculo(populaVeiculo(rs.getInt("CD_VEICULO")));
                agendamento.setDescricaoOrigem(rs.getString("DS_ORIGEM"));
                agendamento.setDescricaoDestino(rs.getString("DS_DESTINO"));
                agendamento.setCodigoMotorista(populaMotorista(rs.getInt("CD_MOTORISTA")));
                agendamento.setCodigoFuncionario(populaFuncionario(rs.getInt("CD_FUNCIONARIO")));
                agendamento.setDataSaida(rs.getDate("DT_SAIDA"));
                agendamento.setDataRetorno(rs.getDate("DT_RETORNO"));
                agendamento.setNumeroPassageiros(rs.getInt("NR_PASSAGEIROS"));
                agendamento.setObservacao(rs.getString("DS_OBSERVACAO"));
                agendamentoList.add(agendamento);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            connection.close();
        }
        return agendamentoList;
    }

    @Override
    public List<Agendamento> getAll() throws SQLException {
        Agendamento agendamento = null;
        List<Agendamento> agendamentoList = new ArrayList<>();
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM AGENDAMENTO ORDER BY CD_AGENDAMENTO");

            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                agendamento.setCodigoAgendamento(rs.getInt("CD_CANDIDATO"));
                agendamento.setCodigoVeiculo(populaVeiculo(rs.getInt("CD_VEICULO")));
                agendamento.setDescricaoOrigem(rs.getString("DS_ORIGEM"));
                agendamento.setDescricaoDestino(rs.getString("DS_DESTINO"));
                agendamento.setCodigoMotorista(populaMotorista(rs.getInt("CD_MOTORISTA")));
                agendamento.setCodigoFuncionario(populaFuncionario(rs.getInt("CD_FUNCIONARIO")));
                agendamento.setDataSaida(rs.getDate("DT_SAIDA"));
                agendamento.setDataRetorno(rs.getDate("DT_RETORNO"));
                agendamento.setNumeroPassageiros(rs.getInt("NR_PASSAGEIROS"));
                agendamento.setObservacao(rs.getString("DS_OBSERVACAO"));
                agendamentoList.add(agendamento);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            connection.close();
        }
        return agendamentoList;
    }

    @Override
    public int getLastId() throws SQLException {
        PreparedStatement pstm = null;
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            String sql = "SELECT COALESCE(MAX(CD_AGENDAMENTO),0)+1 AS MAIOR FROM AGENDAMENTO ";
            pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getInt("MAIOR");
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao maior ID Agendamento");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Erro inesperado ao maior ID Agendamento");
            ex.printStackTrace();
        } finally {
            pstm.close();
            this.connection.close();
        }
        return 1;
    }

    public Veiculo populaVeiculo(int codigoVeiculo) {
        Veiculo veiculo = new Veiculo();
        veiculo.setCodigoVeiculo(codigoVeiculo);
        return veiculo;
    }

    public Motorista populaMotorista(int codigoMotorista) {
        Motorista motorista = new Motorista();
        motorista.setCodigo(codigoMotorista);
        return motorista;
    }

    public Funcionario populaFuncionario(int codigoFuncionario) {
        Funcionario funcionario = new Funcionario();
        funcionario.setCodigo(codigoFuncionario);
        return funcionario;
    }

}
