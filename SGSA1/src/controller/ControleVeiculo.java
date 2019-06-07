/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Cliente;
import model.Modelo;
import model.PessoaFisica;
import model.PessoaJuridica;
import model.Veiculo;
import model.dao.VeiculoDAO;

/**
 * FXML Controller class
 *
 * @author root
 */
public class ControleVeiculo extends ControleBase implements Initializable {

    @FXML
    private TabPane abas;
    @FXML
    private TableView tabelaVeiculo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnVisualizar;
    @FXML
    private TextField txtPlaca;
    @FXML
    private TextField txtChassi;
    @FXML
    private TextField txtAno;
    @FXML
    private TextField txtQuilometragem;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnLimpar;
    @FXML
    private TextField txtModelo;
    @FXML
    private Button btnSelecionarModelo;
    @FXML
    private TextField txtNomeCliente;
    @FXML
    private Button btnSelecionarCliente;
    @FXML
    private TextField txtEditPlaca;
    @FXML
    private TextField txtEditChassi;
    @FXML
    private TextField txtEditQuilo;
    @FXML
    private TextField txtEditAno;
    @FXML
    private TextField txtEditModelo;
    @FXML
    private TextField txtEditProprietario;
    @FXML
    private Button btnEditSalvar;
    @FXML
    private Button btnEditCancelar;
    @FXML
    private Button btnEditModelo;
    @FXML
    private Button btnEditProprietario;
    @FXML
    private TextField txtId;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnViewVoltar;
    @FXML
    private Button btnViewModificar;
    @FXML
    private Button btnViewImprimir;
    @FXML
    private Label viewPlaca;
    @FXML
    private Label viewChassi;
    @FXML
    private Label viewAno;
    @FXML
    private Label viewModelo;
    @FXML
    private Label viewProprietario;
    @FXML
    private Label viewQuilometragem;

    private VeiculoDAO daoveic;
    private Cliente clienteSelecionado;
    private boolean controle = false;
    private Modelo modeloSelecionado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnEditar.setDisable(false);
        btnVisualizar.setDisable(false);
        tabelaVeiculo.getItems().clear();
        configurarTableView();
        atualizarTabela();

    }

    public void configurarTableView() {
        TableColumn colId = (TableColumn) tabelaVeiculo.getColumns().get(0);
        TableColumn colPlaca = (TableColumn) tabelaVeiculo.getColumns().get(1);
        TableColumn colChassi = (TableColumn) tabelaVeiculo.getColumns().get(2);
        TableColumn colAno = (TableColumn) tabelaVeiculo.getColumns().get(3);
        TableColumn colQuilometragem = (TableColumn) tabelaVeiculo.getColumns().get(4);
        TableColumn colProprietario = (TableColumn) tabelaVeiculo.getColumns().get(5);
        TableColumn colModelo = (TableColumn) tabelaVeiculo.getColumns().get(6);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colChassi.setCellValueFactory(new PropertyValueFactory<>("chassi"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colQuilometragem.setCellValueFactory(new PropertyValueFactory<>("quilometragem"));
        colProprietario.setCellValueFactory(new PropertyValueFactory<>("nomeProprietario"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("nomeModelo"));
    }

    public void atualizarTabela() {
        ArrayList<Veiculo> veic = new ArrayList<>();
        daoveic = new VeiculoDAO();
        tabelaVeiculo.getItems().clear();
        try {
            veic = daoveic.buscar("", 10);
        } catch (Exception ex) {
            System.out.println("Erro ao buscar veiculos: " + ex.getMessage());
            ex.printStackTrace();
        }
        configurarTableView();

        if (veic == null) {
            return;
        } else {

            for (int i = 0; i < veic.size(); i++) {
                tabelaVeiculo.getItems().add(veic.get(i));
            }
        }

    }

    @FXML
    private void txtPesquisar_keypressed(KeyEvent event) {

        if (event.getCode().toString().equals("ENTER")) {
            tabelaVeiculo.getItems().clear();
            daoveic = new VeiculoDAO();
            ArrayList<Veiculo> veiculos = new ArrayList<>();
            try {
                veiculos = daoveic.buscar(txtPesquisar.getText().toUpperCase(), 10);
            } catch (Exception ex) {
                System.out.println("Erro ao buscar o Veículo: " + ex.getMessage());
            }
                controle = true;
            if (!veiculos.isEmpty()) {
                for (int i = 0; i < veiculos.size(); i++) {
                    tabelaVeiculo.getItems().add(veiculos.get(i));
                }
                
            } else {
                System.out.println("Veículo não encontrado.");
            }
        } else if (event.getCode().toString().equals("BACK_SPACE")) {

            if (txtPesquisar.getText().isEmpty() && controle) {
                tabelaVeiculo.getItems().clear();
                atualizarTabela();
                controle = false;
            }

        }
    }

    @FXML
    private void btnEditar_pressed(ActionEvent event) {
        btnEditSalvar.setDisable(false);
        btnEditCancelar.setDisable(false);
        Veiculo veic = (Veiculo) tabelaVeiculo.getSelectionModel().getSelectedItem();
        if (veic != null) {
            abas.getSelectionModel().select(2);
            abas.getTabs().get(0).setDisable(true);
            abas.getTabs().get(1).setDisable(true);
            abas.getTabs().get(2).setDisable(false);
            preencherEditar(veic);
        } else {
            Alert mens = new Alert(Alert.AlertType.INFORMATION, "Selecione um Modelo na Tabela.", ButtonType.OK);
            mens.showAndWait();
        }
    }
    
    private void preencherEditar(Veiculo veic){
        
        txtId.setText(String.valueOf(veic.getId()));
        txtEditAno.setText(String.valueOf(veic.getAno()));
        txtEditChassi.setText(veic.getChassi());
        txtEditModelo.setText(veic.getModelo().getNome() + "/" + veic.getModelo().getMarca() + "/" + veic.getModelo().getQuantidade_portas()+" portas");
        if (veic.getProprietario() instanceof PessoaFisica) {
            PessoaFisica pf = (PessoaFisica) veic.getProprietario();
            txtEditProprietario.setText(pf.getId() + "-" + pf.getNome() + " /" + pf.getCpf());
        } else {
            PessoaJuridica pj = (PessoaJuridica) veic.getProprietario();
            txtEditProprietario.setText(pj.getId() + "-" + pj.getRazaoSocial() + " /" + pj.getCnpj());
        }
        txtEditQuilo.setText(String.valueOf(veic.getQuilometragem()));
        txtEditPlaca.setText(veic.getPlaca());
        clienteSelecionado = veic.getProprietario();
        modeloSelecionado = veic.getModelo();
    }

    @FXML
    private void btnSelecionarCliente_pressed(ActionEvent event) {
        SelecionarClienteModal climodal = (SelecionarClienteModal) abrirModal("/view/SelecionarClienteModal.fxml");
        climodal.getStage().setTitle("Selecionar Cliente");
        climodal.getStage().showAndWait();
        clienteSelecionado = climodal.buscaCliente();
        if (clienteSelecionado == null) {
            return;
        }
        if (clienteSelecionado instanceof PessoaFisica) {
            PessoaFisica pf = (PessoaFisica) clienteSelecionado;
            txtNomeCliente.setText(pf.getId() + "-" + pf.getNome() + " /" + pf.getCpf());
        } else {
            PessoaJuridica pj = (PessoaJuridica) clienteSelecionado;
            txtNomeCliente.setText(pj.getId() + "-" + pj.getRazaoSocial() + " /" + pj.getCnpj());
        }

    }

    @FXML
    private void btnSelecionarModelo_press(ActionEvent event) {
        SelecionarModeloModal modeloModal = (SelecionarModeloModal) abrirModal("/view/SelecionarModeloModal.fxml");

        modeloModal.getStage().setTitle("Selecionar Modelo");
        modeloModal.getStage().showAndWait();
        modeloSelecionado = modeloModal.buscarModelo();
        if (modeloSelecionado == null) {
            return;
        }

        txtModelo.setText(modeloSelecionado.getNome() + "/" + modeloSelecionado.getMarca() + "/" + modeloSelecionado.getQuantidade_portas()+" portas");

    }

    @FXML
    private void btnVisualizar_pressed(ActionEvent event) {
        Veiculo veic = (Veiculo) tabelaVeiculo.getSelectionModel().getSelectedItem();
        if (veic != null) {
            abas.getSelectionModel().select(3);
            abas.getTabs().get(0).setDisable(true);
            abas.getTabs().get(1).setDisable(true);
            abas.getTabs().get(2).setDisable(true);
            abas.getTabs().get(3).setDisable(false);
            preencherView(veic);
            preencherEditar(veic);
        } else {
            Alert mens = new Alert(Alert.AlertType.INFORMATION, "Selecione um Modelo na Tabela.", ButtonType.OK);
            mens.showAndWait();
        }
    }
    
    private void preencherView(Veiculo veic){
        viewAno.setText(String.valueOf(veic.getAno()));
        viewChassi.setText(veic.getChassi());
        viewModelo.setText(veic.getModelo().getNome() + "/" + veic.getModelo().getMarca() + "/" + veic.getModelo().getQuantidade_portas()+" portas");
        viewPlaca.setText(veic.getPlaca());
        viewQuilometragem.setText(String.valueOf(veic.getQuilometragem()));
         if (veic.getProprietario() instanceof PessoaFisica) {
            PessoaFisica pf = (PessoaFisica) veic.getProprietario();
            viewProprietario.setText(pf.getId() + "-" + pf.getNome() + " /" + pf.getCpf());
        } else {
            PessoaJuridica pj = (PessoaJuridica) veic.getProprietario();
            viewProprietario.setText(pj.getId() + "-" + pj.getRazaoSocial() + " /" + pj.getCnpj());
        }
    
    }

    @FXML
    private void btnCadastrar_pressed(ActionEvent event) {
        if(validarCadastrar()){
            try{
                Veiculo veic = new Veiculo(txtPlaca.getText(),txtChassi.getText(),Integer.valueOf(txtAno.getText()),Integer.
                    valueOf(txtQuilometragem.getText()),clienteSelecionado,modeloSelecionado);
             daoveic.inserir(veic);
             Alert mens = new Alert(Alert.AlertType.CONFIRMATION, "Cadastro efetuado com sucesso. Gostaria de fazer outro cadastro?", ButtonType.YES,ButtonType.NO);
             mens.showAndWait();
             if(mens.getResult() == ButtonType.NO){
                 limpar();
                 atualizarTabela();
            abas.getSelectionModel().select(0);
            abas.getTabs().get(0).setDisable(false);
            abas.getTabs().get(1).setDisable(false);
            abas.getTabs().get(2).setDisable(true);
            abas.getTabs().get(3).setDisable(true); 
             }
             else{
                 limpar();
             }
            }
            catch(Exception ex){
                System.out.println("Erro ao tentar inserir veículo: "+ex.getMessage());
                new Alert(Alert.AlertType.ERROR, "Erro ao tentar inserir o registro.", ButtonType.OK).showAndWait();
            }
            
        }
    }
    
    private boolean validarCadastrar(){
        try {
            Integer.parseInt(txtAno.getText());
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "Campo ano deve ser preenchido corretamente.", ButtonType.OK).showAndWait();
            return false;
        }
        try {
            Integer.parseInt(txtQuilometragem.getText());
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "Campo Quilometragem deve ser preenchido corretamente.", ButtonType.OK).showAndWait();
            return false;
        }
        if (txtChassi.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Campo chassi é obrigatório", ButtonType.OK).showAndWait();
            return false;
        }
        else if(txtModelo.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Selecione um modelo para o veículo.", ButtonType.OK).showAndWait();
            return false;
        }
        else if(txtNomeCliente.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Selecione um proprietário para o veículo.", ButtonType.OK).showAndWait();
            return false;
        }
        else if(txtPlaca.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Campo Placa é obrigatório.", ButtonType.OK).showAndWait();
            return false;
        }
     return true;   
    }
    
    private boolean validarEditar(){
        try {
            Integer.parseInt(txtEditAno.getText());
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "Campo ano deve ser preenchido corretamente.", ButtonType.OK).showAndWait();
            return false;
        }
        try {
            Integer.parseInt(txtEditQuilo.getText());
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "Campo Quilometragem deve ser preenchido corretamente.", ButtonType.OK).showAndWait();
            return false;
        }
        if (txtEditChassi.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Campo chassi é obrigatório", ButtonType.OK).showAndWait();
            return false;
        }
        else if(txtEditModelo.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Selecione um modelo para o veículo.", ButtonType.OK).showAndWait();
            return false;
        }
        else if(txtEditProprietario.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Selecione um proprietário para o veículo.", ButtonType.OK).showAndWait();
            return false;
        }
        else if(txtEditPlaca.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Campo Placa é obrigatório.", ButtonType.OK).showAndWait();
            return false;
        }
        
        return true;
    }

    @FXML
    private void btnLimpar_pressed(ActionEvent event) {
        limpar();
               
    }
    private void limpar(){
        txtAno.setText("");
        txtChassi.setText("");
        txtModelo.setText("");
        modeloSelecionado=null;
        txtNomeCliente.setText("");
        txtPlaca.setText("");
        txtQuilometragem.setText("");
        clienteSelecionado = null;
    }

    @FXML
    private void btnEditSalvar_pressed(ActionEvent event) {
        if(validarEditar()){
            try{
                daoveic.alterar(new Veiculo(Integer.valueOf(txtId.getText()),txtEditPlaca.getText(),txtEditChassi.getText(),Integer.valueOf(txtEditAno.getText()),Integer.
                    valueOf(txtEditQuilo.getText()),clienteSelecionado,modeloSelecionado));
               new Alert(Alert.AlertType.INFORMATION, "Registro editado com sucesso.", ButtonType.OK).showAndWait();
            atualizarTabela();
            abas.getSelectionModel().select(0);
            abas.getTabs().get(0).setDisable(false);
            abas.getTabs().get(1).setDisable(false);
            abas.getTabs().get(2).setDisable(true);
            abas.getTabs().get(3).setDisable(true); 
             
            }
            catch(Exception ex){
                System.out.println("Erro ao editar o veículo: "+ex.getMessage());
                new Alert(Alert.AlertType.ERROR, "Erro ao editar o registro.", ButtonType.OK).showAndWait();
                ex.printStackTrace();
            }
        }
        
    }

    @FXML
    private void btnEditCancelar_pressed(ActionEvent event) {
        abas.getSelectionModel().select(0);
            abas.getTabs().get(0).setDisable(false);
            abas.getTabs().get(1).setDisable(false);
            abas.getTabs().get(2).setDisable(true);
            abas.getTabs().get(3).setDisable(true);
    }

    @FXML
    private void btnViewVoltar_press(ActionEvent event) {
        abas.getSelectionModel().select(0);
            abas.getTabs().get(0).setDisable(false);
            abas.getTabs().get(1).setDisable(false);
            abas.getTabs().get(2).setDisable(true);
            abas.getTabs().get(3).setDisable(true);
    }

    @FXML
    private void btnEditModelo_pres(ActionEvent event) {
        SelecionarModeloModal modeloModal = (SelecionarModeloModal) abrirModal("/view/SelecionarModeloModal.fxml");

        modeloModal.getStage().setTitle("Selecionar Modelo");
        modeloModal.getStage().showAndWait();
        modeloSelecionado = modeloModal.buscarModelo();
        if (modeloSelecionado == null) {
            return;
        }

        txtEditModelo.setText(modeloSelecionado.getNome() + "/" + modeloSelecionado.getMarca() + "/" + modeloSelecionado.getQuantidade_portas()+" portas");        
    }

    @FXML
    private void btnEditProprietario_press(ActionEvent event) {
        SelecionarClienteModal climodal = (SelecionarClienteModal) abrirModal("/view/SelecionarClienteModal.fxml");
        climodal.getStage().setTitle("Selecionar Cliente");
        climodal.getStage().showAndWait();
        clienteSelecionado = climodal.buscaCliente();
        if (clienteSelecionado == null) {
            return;
        }
        if (clienteSelecionado instanceof PessoaFisica) {
            PessoaFisica pf = (PessoaFisica) clienteSelecionado;
            txtEditProprietario.setText(pf.getId() + "-" + pf.getNome() + " /" + pf.getCpf());
        } else {
            PessoaJuridica pj = (PessoaJuridica) clienteSelecionado;
            txtEditProprietario.setText(pj.getId() + "-" + pj.getRazaoSocial() + " /" + pj.getCnpj());
        }

    }
    
    @FXML
    private void btnExcluir_press(ActionEvent event){
        Alert mens = new Alert(Alert.AlertType.CONFIRMATION, "Gostaria de excluir este registro?", ButtonType.YES, ButtonType.NO);
        mens.showAndWait();
        if (mens.getResult() == ButtonType.YES) {
            try {
                daoveic.excluir(Integer.valueOf(txtId.getText()));
                Alert res = new Alert(Alert.AlertType.INFORMATION, "Excluído com sucesso.", ButtonType.OK);
                res.showAndWait();

            } catch (Exception ex) {
                System.out.println("Erro ao excluir o veículo:" + ex.getMessage());
            }
            abas.getSelectionModel().select(0);
            abas.getTabs().get(0).setDisable(false);
            abas.getTabs().get(1).setDisable(false);
            abas.getTabs().get(2).setDisable(true);
            abas.getTabs().get(3).setDisable(true);
            atualizarTabela();
        } else {
            Alert res = new Alert(Alert.AlertType.INFORMATION, "Registro mantido.", ButtonType.OK);
            res.showAndWait();
        }
        
    }
    @FXML
    private void btnViewModificar_pressed(ActionEvent event){
        abas.getSelectionModel().select(2);
            abas.getTabs().get(0).setDisable(true);
            abas.getTabs().get(1).setDisable(true);
            abas.getTabs().get(2).setDisable(false);
            abas.getTabs().get(3).setDisable(true);
    }
}
