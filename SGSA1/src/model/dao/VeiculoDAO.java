package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Cliente;
import model.Modelo;
import model.Veiculo;

/**
 *
 * @author Hércules
 */
public class VeiculoDAO extends BaseDAO{
    
    private ModeloDAO modDao;
    private ClienteDAO cliDao;
    
    public VeiculoDAO(){
        super();
        modDao = new ModeloDAO();
        cliDao = new ClienteDAO();
    }

/**
 *
 * @author root
 *      placa
        chassi
        ano
        quilometragem
        proprietario
        modelo
        
 */

    private ResultSet rs;
    private PreparedStatement stmt;

    public void inserir(Veiculo veic) throws SQLException {

        String SQL = "INSERT INTO veiculo (placa,chassi,ano,quilometragem,cliente_id,modelo_id) VALUES (?,?,?,?,?,?)";
        stmt = conexao.prepareStatement(SQL);
        stmt.setString(1, veic.getPlaca());
        stmt.setString(2, veic.getChassi());
        stmt.setInt(3, veic.getAno());
        stmt.setInt(4, veic.getQuilometragem());
        stmt.setInt(5, veic.getProprietario().getId());
        stmt.setInt(6, veic.getModelo().getId());
        stmt.execute();
        stmt.close();

    }

    public ArrayList<Veiculo> relatorio() throws SQLException {

        ArrayList<Veiculo> veics = new ArrayList<>();

        String SQL = "SELECT * FROM veiculo";
        stmt = conexao.prepareStatement(SQL);
        rs = stmt.executeQuery();

        while (rs.next()) {
            int id;
            String placa;
            String chassi;
            int ano;
            int quilometragem;
            int idProprietario;
            int idModelo;
           
            id = rs.getInt("id");
            placa = rs.getString("placa");
            chassi = rs.getString("chassi");
            ano = rs.getInt("ano");
            quilometragem = rs.getInt("quilometragem");
            idProprietario = rs.getInt("cliente_id");
            idModelo = rs.getInt("modelo_id");
            Modelo mod = modDao.buscar(idModelo);
            Cliente cli = cliDao.buscar(idProprietario);
            
            veics.add(new Veiculo(id,placa,chassi,ano,quilometragem,cli,mod));
            
        }

        rs.close();
        stmt.close();
        return veics;
    }

    public Veiculo buscar(int id) throws SQLException {
        String SQL = "SELECT * FROM veiculo WHERE id=?";
        stmt = conexao.prepareStatement(SQL);
        stmt.setInt(1, id);
        rs = stmt.executeQuery();

        if (rs.next()) {

            int iden = rs.getInt("id");
           String placa = rs.getString("placa");
           String chassi = rs.getString("chassi");
           int ano = rs.getInt("ano");
           int quilometragem = rs.getInt("quilometragem");
           int idProprietario = rs.getInt("cliente_id");
           int idModelo = rs.getInt("modelo_id");
           Cliente cli = cliDao.buscar(idProprietario); 
           Modelo mod = modDao.buscar(idModelo); 
            stmt.close();
            rs.close();

            Veiculo veic = new Veiculo(iden,placa,chassi,ano,quilometragem,cli,mod);
            return veic;
        }
        return null;
    }
    
    public Veiculo buscar(String placa) throws SQLException {
        String SQL = "SELECT * FROM veiculo WHERE placa LIKE ?";
        stmt = conexao.prepareStatement(SQL);
        stmt.setString(1, placa+"%");
        rs = stmt.executeQuery();

        if (rs.next()) {

            int iden = rs.getInt("id");
           String _placa = rs.getString("placa");
           String chassi = rs.getString("chassi");
           int ano = rs.getInt("ano");
           int quilometragem = rs.getInt("quilometragem");
           int idProprietario = rs.getInt("cliente_id");
           int idModelo = rs.getInt("modelo_id");
           Cliente cli = cliDao.buscar(idProprietario); 
           Modelo mod = modDao.buscar(idModelo); 
            stmt.close();
            rs.close();

            Veiculo veic = new Veiculo(iden,_placa,chassi,ano,quilometragem,cli,mod);
            return veic;
        }
        return null;
    }
    
    public ArrayList<Veiculo> buscar(String placa, int limite) throws SQLException {
        String SQL = "SELECT veiculo.*, modelo.* FROM veiculo JOIN modelo ON veiculo.modelo_id = modelo.id WHERE placa LIKE ? LIMIT ?";
        stmt = conexao.prepareStatement(SQL);
        stmt.setString(1, placa+"%");
        stmt.setInt(2, limite);
        rs = stmt.executeQuery();
        
        ArrayList<Veiculo> veiculos = new ArrayList();
        while( rs.next() ) {
            int iden = rs.getInt(1);
            String _placa = rs.getString(2);
            String chassi = rs.getString(3);
            int ano = rs.getInt(4);
            int quilometragem = rs.getInt(5);
            int idProprietario = rs.getInt(6);
            int idModelo = rs.getInt(7);
            Cliente cli = cliDao.buscar(idProprietario); 
            Modelo mod = new Modelo(
                    rs.getInt(8),
                    rs.getString(9),
                    rs.getString(10),
                    rs.getString(11),
                    rs.getInt(12),
                    rs.getString(13),
                    rs.getInt(14)
            );
            Veiculo veic = new Veiculo(iden,_placa,chassi,ano,quilometragem,cli,mod);
            veiculos.add(veic);
        }
        return veiculos;
    }

    public void alterar(Veiculo veic) throws SQLException {

        String SQL = "UPDATE  veiculo SET placa=?, chassi=?, ano=?, quilometragem=?, cliente_id=?, modelo_id=? WHERE id=?";
        stmt = conexao.prepareStatement(SQL);
        stmt.setString(1, veic.getPlaca());
        stmt.setString(2, veic.getChassi());
        stmt.setInt(3, veic.getAno());
        stmt.setInt(4, veic.getQuilometragem());
        stmt.setInt(5, veic.getProprietario().getId());
        stmt.setInt(6, veic.getModelo().getId());
        stmt.setInt(7, veic.getId());
        stmt.execute();
        stmt.close();

    }

    public void excluir(int id) throws SQLException {
        String SQL = "DELETE FROM veiculo WHERE id=?";
        stmt = conexao.prepareStatement(SQL);
        stmt.setInt(1, id);
        stmt.execute();
        stmt.close();
    }
    
    public void excluir(Veiculo veiculo) throws SQLException{
        this.excluir(veiculo.getId());
    }

}
