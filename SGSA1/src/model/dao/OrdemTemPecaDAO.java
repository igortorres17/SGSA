package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Peca;
import java.util.ArrayList;

/**
 *
 * @author Hércules M.
 */
public class OrdemTemPecaDAO extends BaseDAO{
    
    public OrdemTemPecaDAO(){
        super();
        tabela = "ordem_tem_peca";
    }
    
    public void inserir(Peca peca, int ordemServicoID){
        
    }
    
    public ArrayList<Peca> buscar(int id_os) throws SQLException{
        String sqlQuery = "SELECT * FROM " + tabela + " WHERE ordem_servico_id = ?";
        PreparedStatement instrucaoPreparada = conexao.prepareStatement(sqlQuery);
        instrucaoPreparada.setInt(1, id_os);
        ResultSet resultado = instrucaoPreparada.executeQuery();
        
        PecaDAO pecaDao = new PecaDAO();
        ArrayList<Peca> pecas = new ArrayList();
        
        while(resultado.next()){
            int id = resultado.getInt("peca_id");
            Peca peca = pecaDao.buscar(id); 
            pecas.add(peca);
        }
        
        return pecas;
    }
}
