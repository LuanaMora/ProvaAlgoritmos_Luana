/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendamento.DAO;

import agendamento.jdbc.ConnectionFactory;
import agendamento.model.Veiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luana Mora
 */
public class VeiculoDAO implements GenericDAO<Veiculo> {

    private Connection connection = null;

    @Override
    public void save(Veiculo entity) throws SQLException {
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("   INSERT INTO veiculo ( cd_veiculo, nr_placa, nr_ano, nr_passageiros)")
                    .append("     VALUES (?, ?, ?, ?);");

            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            pstm.setInt(1, entity.getCodigoVeiculo());
            pstm.setInt(2, entity.getNumeroPlaca());
            pstm.setInt(3, entity.getAnoVeiculo());
            pstm.setInt(4, entity.getNumeroPassageiros());

            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir Veiculo");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Erro inesperado ao Inserir Veiculo");
            ex.printStackTrace();
        } finally {
            connection.close();
        }
    }

    @Override
    public void update(Veiculo entity) throws SQLException {
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE veiculo")
                    .append("     SET nr_placa=?, nr_ano=?, nr_passageiros=?")
                    .append(" WHERE  cd_veiculo=?; ");

            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            pstm.setInt(1, entity.getNumeroPlaca());
            pstm.setInt(2, entity.getAnoVeiculo());
            pstm.setInt(3, entity.getNumeroPassageiros());
            pstm.setInt(4, entity.getCodigoVeiculo());

            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao Atualizar Veiculo");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Erro inesperado ao Atualizar Veiculo");
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
            sql.append("DELETE FROM VEICULO WHERE CD_VEICULO = " + id + ";");
            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao Deletar Veiculo");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Erro inesperado ao Deletar Veiculo");
            ex.printStackTrace();
        } finally {
            this.connection.close();
        }
    }

    @Override
    public Veiculo getById(int id) throws SQLException {
        Veiculo veiculo = null;
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            String sql = "SELECT * FROM VEICULO WHERE CD_VEICULO = " + id;
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            veiculo = new Veiculo();
            while (rs.next()) {
                veiculo.setCodigoVeiculo(rs.getInt("CD_VEICULO"));
                veiculo.setNumeroPlaca(rs.getInt("NR_PLACA"));
                veiculo.setAnoVeiculo(rs.getInt("NR_ANO"));
                veiculo.setNumeroPassageiros(rs.getInt("NR_PASSAGEIROS"));
            }
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao Atualizar Veiculo");
            ex.printStackTrace();
        } finally {
            this.connection.close();
        }
        return veiculo;
    }

    @Override
    public List<Veiculo> getByName(String name) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Veiculo> getAll() throws SQLException {
        List<Veiculo> veiculoList = null;
        Veiculo veiculo = null;
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            String sql = ("select * from veiculo order by cd_veiculo;");

            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            veiculoList = new ArrayList<>();
            while (rs.next()) {
                veiculo = new Veiculo();
                veiculo.setCodigoVeiculo(rs.getInt("CD_VEICULO"));
                veiculo.setNumeroPlaca(rs.getInt("NR_PLACA"));
                veiculo.setAnoVeiculo(rs.getInt("NR_ANO"));
                veiculo.setNumeroPassageiros(rs.getInt("NR_PASSAGEIROS"));

                veiculoList.add(veiculo);
            }
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar todos OS Veiculos");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Erro inesperado ao consultar todos OS Veiculos");
            ex.printStackTrace();
        } finally {
            this.connection.close();
        }
        return veiculoList;
    }

    @Override
    public int getLastId() throws SQLException {
        PreparedStatement pstm = null;
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            String sql = "SELECT COALESCE(MAX(CD_VEICULO),0)+1 AS MAIOR FROM VEICULO ";
            pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getInt("MAIOR");
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao maior ID Veiculo");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Erro inesperado ao maior ID Veiculo");
            ex.printStackTrace();
        } finally {
            pstm.close();
            this.connection.close();
        }
        return 1;
    }

}
