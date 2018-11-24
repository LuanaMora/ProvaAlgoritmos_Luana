/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendamento.DAO;

import agendamento.jdbc.ConnectionFactory;
import agendamento.model.Funcionario;
import agendamento.model.Motorista;
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
public class MotoristaDAO implements GenericDAO<Motorista> {

    private Connection connection = null;

    @Override
    public void save(Motorista entity) throws SQLException {
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("    INSERT INTO motorista(")
                    .append("   cd_motorista, cd_funcionario, nr_cnh, dt_vencimento)     VALUES (?, ?, ?, ?);");
            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            pstm.setInt(1, entity.getCodigo());
            pstm.setInt(2, entity.getCodigoFuncionario().getCodigo());
            pstm.setInt(3, entity.getNumeroCNH());
            pstm.setDate(4, (Date) entity.getDataVencimento());

            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir Motorista");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Erro inesperado ao Inserir Motorista");
            ex.printStackTrace();
        } finally {
            connection.close();
        }
    }

    @Override
    public void update(Motorista entity) throws SQLException {
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("   UPDATE motorista")
                    .append("      SET  cd_funcionario=?, nr_cnh=?, dt_vencimento=?")
                    .append("  WHERE cd_motorista=?, ");

            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            pstm.setInt(1, entity.getCodigoFuncionario().getCodigo());
            pstm.setInt(2, entity.getNumeroCNH());
            pstm.setDate(3, (Date) entity.getDataVencimento());
            pstm.setInt(4, entity.getCodigo());
            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao Atualizar Motorista");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Erro inesperado ao Atualizar Motorista");
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
            sql.append("DELETE FROM MOTORISTA WHERE CD_MOTORISTA = " + id + ";");
            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao Deletar Motorista");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Erro inesperado ao Deletar Motorista");
            ex.printStackTrace();
        } finally {
            this.connection.close();
        }
    }

    @Override
    public Motorista getById(int id) throws SQLException {
        Motorista motorista = null;
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            String sql = "SELECT * FROM MOTORISTA WHERE CD_MOTORISTA = " + id;
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            motorista = new Motorista();
            while (rs.next()) {
                motorista.setCodigo(rs.getInt("CD_CANDIDATO"));
                motorista.setCodigoFuncionario(populaFuncionario(rs.getInt("CD_FUNCIONARIO")));
                motorista.setNumeroCNH(rs.getInt("NR_CNH"));
                motorista.setDataVencimento(rs.getDate("DT_VENCIMENTO"));

            }
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao Atualizar Motorista");
            ex.printStackTrace();
        } finally {
            this.connection.close();
        }
        return motorista;
    }

    @Override
    public List<Motorista> getByName(String name) throws SQLException {
        Motorista motorista = null;
        List<Motorista> motoristaList = new ArrayList<>();
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("   SELECT M.CD_MOTORISTA, M.CD_FUNCIONARIO, M.NR_CNH, M.DT_VENCIMENTO,  ")
                    .append(" F.NM_FUNCIONARIO FROM MOTORISTA AS M")
                    .append(" INNER JOIN FUNCIONARIO AS F ON ")
                    .append(" M.CD_FUNCIONARIO = F.CD_FUNCIONARIO ")
                    .append(" WHERE F.NM_FUNCIONARIO LIKE '%" + name + "%';");

            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                motorista.setCodigo(rs.getInt("CD_CANDIDATO"));
                motorista.setCodigoFuncionario(populaFuncionario(rs.getInt("CD_FUNCIONARIO")));
                motorista.setNumeroCNH(rs.getInt("NR_CNH"));
                motorista.setDataVencimento(rs.getDate("DT_VENCIMENTO"));
                motoristaList.add(motorista);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            connection.close();
        }
        return motoristaList;
    }

    @Override
    public List<Motorista> getAll() throws SQLException {
        Motorista motorista = null;
        List<Motorista> motoristaList = new ArrayList<>();
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append(" select * from motorista order by cd_motorista; ");

            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                motorista.setCodigo(rs.getInt("CD_CANDIDATO"));
                motorista.setCodigoFuncionario(populaFuncionario(rs.getInt("CD_FUNCIONARIO")));
                motorista.setNumeroCNH(rs.getInt("NR_CNH"));
                motorista.setDataVencimento(rs.getDate("DT_VENCIMENTO"));
                motoristaList.add(motorista);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            connection.close();
        }
        return motoristaList;
    }

    @Override
    public int getLastId() throws SQLException {
         PreparedStatement pstm = null;
        try {
            this.connection =  ConnectionFactory.getInstancia().getConnection();
            String sql = "SELECT COALESCE(MAX(cd_motorista),0)+1 AS MAIOR FROM motorista ";
            pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getInt("MAIOR");
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao maior ID Motorista");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Erro inesperado ao maior ID Motorista");
            ex.printStackTrace();
        } finally {
            pstm.close();
            this.connection.close();
        }
        return 1;
    }

    public Funcionario populaFuncionario(int codigo) {
        Funcionario funcionario = new Funcionario();
        funcionario.setCodigo(codigo);
        return funcionario;
    }

}
