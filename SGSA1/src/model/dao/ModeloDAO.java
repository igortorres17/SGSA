/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Modelo;

/**
 *
 * @author root
 */
public class ModeloDAO extends BaseDAO {

    private ResultSet rs;
    private PreparedStatement stmt;

    public ModeloDAO() {
        super();
        tabela = "modelo";
    }

    public void inserir(Modelo mod) throws SQLException {

        String SQL = "INSERT INTO modelo (tipo,nome,marca,quantidade_portas,motor,combustivel) VALUES (?,?,?,?,?,?)";
        stmt = conexao.prepareStatement(SQL);
        stmt.setString(1, mod.getTipo());
        stmt.setString(2, mod.getNome());
        stmt.setString(3, mod.getMarca());
        stmt.setInt(4, mod.getQuantidade_portas());
        stmt.setString(5, mod.getMotor());
        stmt.setInt(6, mod.getCombustivel());
        stmt.execute();
        stmt.close();

    }

    public ArrayList<Modelo> relatorio() throws SQLException {

        ArrayList<Modelo> mods = new ArrayList<>();

        String SQL = "SELECT * FROM modelo";
        stmt = conexao.prepareStatement(SQL);
        rs = stmt.executeQuery();

        while (rs.next()) {
            int id;
            String tipo;
            String nome;
            String marca;
            int qtdPortas;
            String motor;
            int combustivel;

            id = rs.getInt("id");
            tipo = rs.getString("tipo");
            nome = rs.getString("nome");
            marca = rs.getString("marca");
            qtdPortas = rs.getInt("quantidade_portas");
            motor = rs.getString("motor");
            combustivel = rs.getInt("combustivel");

            mods.add(new Modelo(id, tipo, nome, marca, qtdPortas, motor, combustivel));
        }

        rs.close();
        stmt.close();
        return mods;
    }

    public Modelo buscar(int id) throws SQLException {
        String SQL = "SELECT * FROM modelo WHERE id=?";
        stmt = conexao.prepareStatement(SQL);
        stmt.setInt(1, id);
        rs = stmt.executeQuery();

        if (rs.next()) {

            int ident = rs.getInt("id");
            String tipo = rs.getString("tipo");
            String nome = rs.getString("nome");
            String marca = rs.getString("marca");
            int qtdPortas = rs.getInt("quantidade_portas");
            String motor = rs.getString("motor");
            int combustivel = rs.getInt("combustivel");
            stmt.close();
            rs.close();

            Modelo mod = new Modelo(id, tipo, nome, marca, qtdPortas, motor, combustivel);
            return mod;
        }
        return null;
    }
    
        public Modelo buscar(String nomes) throws SQLException {
        String SQL = "SELECT * FROM modelo WHERE nome LIKE ?";
        stmt = conexao.prepareStatement(SQL);
        stmt.setString(1, nomes);
        rs = stmt.executeQuery();

        if (rs.next()) {

            int ident = rs.getInt("id");
            String tipo = rs.getString("tipo");
            String nome = rs.getString("nome");
            String marca = rs.getString("marca");
            int qtdPortas = rs.getInt("quantidade_portas");
            String motor = rs.getString("motor");
            int combustivel = rs.getInt("combustivel");
            stmt.close();
            rs.close();

            Modelo mod = new Modelo(ident, tipo, nome, marca, qtdPortas, motor, combustivel);
            return mod;
        }
        return null;
    }

    public void alterar(Modelo mod) throws SQLException {

        String SQL = "UPDATE  modelo SET tipo=?, nome=?, marca=?, quantidade_portas=?, motor=?, combustivel=? WHERE id=?";
        stmt = conexao.prepareStatement(SQL);
        stmt.setString(1, mod.getTipo());
        stmt.setString(2, mod.getNome());
        stmt.setString(3, mod.getMarca());
        stmt.setInt(4, mod.getQuantidade_portas());
        stmt.setString(5, mod.getMotor());
        stmt.setInt(6, mod.getCombustivel());
        stmt.setInt(7, mod.getId());
        stmt.execute();
        stmt.close();

    }

    public void excluir(int id) throws SQLException {
        String SQL = "DELETE FROM modelo WHERE id=?";
        stmt = conexao.prepareStatement(SQL);
        stmt.setInt(1, id);
        stmt.execute();
        stmt.close();
    }

}
