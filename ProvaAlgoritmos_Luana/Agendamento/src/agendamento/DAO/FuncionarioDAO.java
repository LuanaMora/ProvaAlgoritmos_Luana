/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendamento.DAO;

import agendamento.jdbc.ConnectionFactory;
import agendamento.model.Funcionario;
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
public class FuncionarioDAO implements GenericDAO<Funcionario> {

    private Connection connection = null;

    @Override
    public void save(Funcionario entity) throws SQLException {
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("insert into FUNCIONARIO(")
                    .append("  cd_funcionario, nm_funcionario, nr_matricula)")
                    .append("     VALUES (?, ?, ?);");

            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            pstm.setInt(1, entity.getCodigo());
            pstm.setString(2, entity.getNomeFuncionario());
            pstm.setInt(3, entity.getNumeroMatricula());

            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir funcionario");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Erro inesperado ao Inserir funcionario");
            ex.printStackTrace();
        } finally {
            connection.close();
        }
    }

    @Override
    public void update(Funcionario entity) throws SQLException {
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("     UPDATE funcionario")
                    .append("      SET nm_funcionario=?, nr_matricula=?")
                    .append("  WHERE cd_funcionario=?;");

            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            pstm.setString(1, entity.getNomeFuncionario());
            pstm.setInt(2, entity.getNumeroMatricula());
            pstm.setInt(3, entity.getCodigo());

            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao Atualizar funcionario");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Erro inesperado ao Atualizar funcionario");
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
            sql.append("DELETE FROM FUNCIONARIO WHERE CD_FUNCIONARIO = " + id + ";");
            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao Deletar funcionario");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Erro inesperado ao Deletar funcionario");
            ex.printStackTrace();
        } finally {
            this.connection.close();
        }
    }

    @Override
    public Funcionario getById(int id) throws SQLException {
        Funcionario funcionario = null;
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            String sql = "SELECT * FROM funcionario WHERE cd_funcionario = " + id;
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            funcionario = new Funcionario();
            while (rs.next()) {
                funcionario.setCodigo(rs.getInt("CD_FUNCIONARIO"));
                funcionario.setNomeFuncionario(rs.getString("NM_FUNCIONARIO"));
                funcionario.setNumeroMatricula(rs.getInt("NR_MATRICULA"));
            }
            pstm.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao Atualizar funcionario");
            ex.printStackTrace();
        } finally {
            this.connection.close();
        }
        return funcionario;
    }

    @Override
    public List<Funcionario> getByName(String name) throws SQLException {
        Funcionario funcionario = null;
        List<Funcionario> funcionarioList = new ArrayList<>();
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("  SELECT CD_FUNCIONARIO, NM_FUNCIONARIO, NR_MATRICULA")
                    .append(" FROM FUNCIONARIO")
                    .append(" WHERE NM_FUNCIONARIO LIKE '%" + name + "%';");

            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                funcionario = new Funcionario();
                funcionario.setCodigo(rs.getInt("cd_funcionario"));
                funcionario.setNomeFuncionario(rs.getString("nm_funcionario"));
                funcionario.setNumeroMatricula(rs.getInt("nr_matricula"));
                funcionarioList.add(funcionario);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            connection.close();
        }
        return funcionarioList;
    }

    @Override
    public List<Funcionario> getAll() throws SQLException {
        Funcionario funcionario = null;
        List<Funcionario> funcionarioList = new ArrayList<>();
        try {
            this.connection = ConnectionFactory.getInstancia().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("select * from FUNCIONARIO order by CD_FUNCIONARIO;");

            PreparedStatement pstm = connection.prepareStatement(sql.toString());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                funcionario = new Funcionario();
                funcionario.setCodigo(rs.getInt("cd_funcionario"));
                funcionario.setNomeFuncionario(rs.getString("nm_funcionario"));
                funcionario.setNumeroMatricula(rs.getInt("nr_matricula"));
                funcionarioList.add(funcionario);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            connection.close();
        }
        return funcionarioList;
    }

    @Override
    public int getLastId() throws SQLException {
        PreparedStatement pstm = null;
        try {
            this.connection =  ConnectionFactory.getInstancia().getConnection();
            String sql = "SELECT COALESCE(MAX(cd_funcionario),0)+1 AS MAIOR FROM funcionario ";
            pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getInt("MAIOR");
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao maior ID funcionario");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Erro inesperado ao maior ID funcionario");
            ex.printStackTrace();
        } finally {
            pstm.close();
            this.connection.close();
        }
        return 1;
    }

}
