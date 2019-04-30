package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Servico;

/**
 *
 * @author Hércules M.
 */
public class OrdemTemServicoDAO extends BaseDAO{
        
    public OrdemTemServicoDAO(){
        super();
        tabela = "ordem_tem_servico";
    }
    
    public void inserir(Servico servico, int ordemServicoID){
        
    }
    
    public ArrayList<Servico> buscar(int id_os) throws SQLException{
        String sqlQuery = "SELECT * FROM " + tabela + " WHERE ordem_servico_id = ?";
        PreparedStatement instrucaoPreparada = conexao.prepareStatement(sqlQuery);
        instrucaoPreparada.setInt(1, id_os);
        ResultSet resultado = instrucaoPreparada.executeQuery();
        
        ServicoDAO servicoDao = new ServicoDAO();
        ArrayList<Servico> servicos = new ArrayList();
        
        while(resultado.next()){
            int id = resultado.getInt("servico_id");
            Servico servico = servicoDao.buscar(id); 
            servicos.add(servico);
        }
        
        return servicos;
    }
}
