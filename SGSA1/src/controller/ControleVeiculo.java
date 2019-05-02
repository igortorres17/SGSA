/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.dao.ModeloDAO;
import model.dao.VeiculoDAO;

/**
 *
 * @author root
 */
public class ControleVeiculo {
    private ModeloDAO daoMod;
    private VeiculoDAO daoVei;
    
    public ControleVeiculo(){
        daoMod= new ModeloDAO();
        daoVei = new VeiculoDAO();
    }
    
    public int inserir(){
        return 1;
    }
}
