package controller;

import java.awt.BorderLayout;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Cliente;
import model.PessoaFisica;
import model.PessoaJuridica;
import model.dao.ClienteDAO;
import terceiro.TextFieldFormatter;

/**
 *
 * @author Hércules M.
 */
public class ControleClientes extends ControleBase implements Initializable{
    @FXML
    private TextField txtPesquisar;
    
    @FXML
    private Button btnPesquisar;
    
    @FXML
    private TableView tabelaClientes;
    
    @FXML
    private Button btnEditar;
    
    @FXML
    private Button btnVisualizar;
    
    @FXML
    private TextField txtNome;
    
    @FXML
    private TextField txtCpf;
    
    @FXML
    private TextField txtNascimento;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private TextField txtTelefone;
    
    @FXML
    private TextField txtLogradouro;
    
    @FXML
    private TextField txtNumero;
    
    @FXML
    private TextField txtComplemento;
    
    @FXML
    private TextField txtBairro;
    
    @FXML
    private TextField txtCidade;
    
    @FXML
    private ComboBox cbEstado;
    
    @FXML
    private RadioButton rbFisica;
    
    @FXML
    private RadioButton rbJuridica;
    
    @FXML
    private Button btnLimpar;
    
    @FXML
    private Button btnCadastrar;
    
    @FXML
    private Label lblNome;
    
    @FXML
    private Label lblCpf;
    
    @FXML
    private Label lblNasc;
    
    @FXML
    private Label lblRua;
    
    @FXML
    private Label lblBairro;
    
    @FXML
    private Label lblCidade;
    
    @FXML
    private Label lblEmail;
    
    @FXML
    private Label lblTel;
    
    @FXML
    private TabPane abas;
    
    @FXML
    private TextField txtEditNome;
    
    @FXML
    private TextField txtEditCpf;
    
    @FXML
    private TextField txtEditNasc;
    
    @FXML
    private TextField txtEditEmail;
            
    @FXML
    private TextField txtEditTel;
                
    @FXML
    private TextField txtEditLogradouro;
    
    @FXML
    private TextField txtEditNumero;
    
    @FXML
    private TextField txtEditComplemento;
    
    @FXML
    private TextField txtEditBairro;
    
    @FXML
    private TextField txtEditCidade;
    
    @FXML
    private ComboBox cbEditEstado;
    
    @FXML
    private Button btnEditCancelar;
    
    @FXML
    private Button btnEditSalvar;
    
    @FXML
    private Label lblCadNome;
    
    @FXML 
    Label lblCadCpf;
    
    @FXML
    private ProgressIndicator progresso;
    
    // Custom
    ClienteDAO clienteDao;
    private final int LIMITE_CLIENTES = 8;
    private Cliente clienteEmEdicao = null;
    private Cliente clienteEmVisualizacao = null;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {        
        configurarTableView();
        clienteDao = new ClienteDAO();
        
        try {
            ArrayList<Cliente> clientes = clienteDao.buscar("", LIMITE_CLIENTES);
            preencherTableView(clientes);
        } catch (SQLException ex) {
            exibirErro(ex);
            System.out.println("Erro ao buscar clientes: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        tabelaClientes.getSelectionModel().selectFirst();
        if(tabelaClientes.getItems().size() > 0)    
            habilitarBotoesEditarVisualizar(true);
        
        cbEstado.getItems().addAll("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO", "Selecionar");
        cbEditEstado.getItems().addAll("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO", "Selecionar");
    }
    
    private void configurarTableView(){
        TableColumn colId = (TableColumn) tabelaClientes.getColumns().get(0);
        TableColumn colNome = (TableColumn) tabelaClientes.getColumns().get(1);
        TableColumn colCpf = (TableColumn) tabelaClientes.getColumns().get(2);
        TableColumn colEmail = (TableColumn) tabelaClientes.getColumns().get(3);
        TableColumn colTelefone = (TableColumn) tabelaClientes.getColumns().get(4);
        TableColumn colTipo = (TableColumn) tabelaClientes.getColumns().get(5);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colId.setResizable(false);
        colNome.setResizable(false);
        colCpf.setResizable(false);
        colEmail.setResizable(false);
        colTelefone.setResizable(false);
        colTipo.setResizable(false);
    }
    
    private void preencherTableView(ArrayList<Cliente> clientes){
        if(clientes == null)
            return;
        
        for(int i = 0; i < clientes.size(); i++){
                tabelaClientes.getItems().add(clientes.get(i));
            }
    }
    
    private void habilitarBotoesEditarVisualizar(Boolean habilitar){
        btnEditar.setDisable(!habilitar);
        btnVisualizar.setDisable(!habilitar);
    }
    
    private void buscarCliente(String nome){
        progresso.setVisible(true);
        txtPesquisar.getStyleClass().remove("pesquisar-cinza-icon");
        habilitarBotoesEditarVisualizar(false);
        Task task = new Task(){
            public Void call() {
                ArrayList<Cliente> clientes;
                try {
                    clientes = clienteDao.buscar(nome, LIMITE_CLIENTES);
                    Platform.runLater(
                        () -> {
                            tabelaClientes.getItems().clear();
                            preencherTableView(clientes);
                            if(tabelaClientes.getItems().isEmpty())
                                habilitarBotoesEditarVisualizar(false);
                            else
                                habilitarBotoesEditarVisualizar(true);

                            tabelaClientes.getSelectionModel().selectFirst();
                            progresso.setVisible(false);
                            txtPesquisar.getStyleClass().add("pesquisar-cinza-icon");
                        }
                    );
                } catch (SQLException ex) {
                    exibirErro(ex);
                    System.out.println("Falha ao buscar clientes: " + ex.getMessage());
                }

                return null;
            }
        };
        new Thread(task).start();
    }
    
    private void limparCamposCadastro(){
        txtNome.getStyleClass().remove("text-prompt-erro");
        txtNome.setText("");        
        
        txtCpf.getStyleClass().remove("text-prompt-erro");
        txtCpf.setText("");        
        
        txtNascimento.getStyleClass().remove("text-prompt-erro");
        txtNascimento.setText("");        
        
        txtEmail.getStyleClass().remove("text-prompt-erro");
        txtEmail.setText("");
        
        txtTelefone.getStyleClass().remove("text-prompt-erro");
        txtTelefone.setText("");        
        
        txtLogradouro.getStyleClass().remove("text-prompt-erro");
        txtLogradouro.setText("");        
        
        txtNumero.setText("");

        txtBairro.getStyleClass().remove("text-prompt-erro");
        txtBairro.setText("");        
        
        txtCidade.getStyleClass().remove("text-prompt-erro");
        txtCidade.setText("");
        
        
        cbEstado.getSelectionModel().selectLast();
        //rbFisica.selectedProperty().set(true);
    }    
    
    private void limparCamposEditar(){
        txtEditNome.getStyleClass().remove("text-prompt-erro");
        txtEditNome.setText("");        
        
        txtEditCpf.getStyleClass().remove("text-prompt-erro");
        txtEditCpf.setText("");        
        
        txtEditNasc.getStyleClass().remove("text-prompt-erro");
        txtEditNasc.setText("");        
        
        txtEditEmail.getStyleClass().remove("text-prompt-erro");
        txtEditEmail.setText("");
        
        txtEditTel.getStyleClass().remove("text-prompt-erro");
        txtEditTel.setText("");        
        
        txtEditLogradouro.getStyleClass().remove("text-prompt-erro");
        txtEditLogradouro.setText("");        
        
        txtEditNumero.setText("");

        txtEditBairro.getStyleClass().remove("text-prompt-erro");
        txtEditBairro.setText("");        
        
        txtEditCidade.getStyleClass().remove("text-prompt-erro");
        txtEditCidade.setText("");
        
        
        cbEditEstado.getSelectionModel().selectLast();
    }    
    

    private void visualizarInfo(Cliente cliente){
        lblNasc.setText(cliente.getData_nascimento());
        
        lblRua.setText(cliente.getLogradouro() + ", " + cliente.getNumero());
        lblBairro.setText(cliente.getBairro());
        lblCidade.setText(cliente.getMunicipio() + " / " + cliente.getEstado());
        
        lblEmail.setText(cliente.getEmail());
        lblTel.setText(cliente.getTelefone());
        
        if(cliente instanceof PessoaFisica){
            PessoaFisica pf = (PessoaFisica)cliente;
            lblNome.setText(pf.getNome());
            lblCpf.setText(pf.getCpf());
        }else{
            PessoaJuridica pj = (PessoaJuridica)cliente;
            lblNome.setText(pj.getRazaoSocial());
            lblCpf.setText(pj.getCnpj());
        }
        
        clienteEmVisualizacao = cliente;
    }
    
    private boolean validarCamposCadastro(){
        if(txtNome.getText().isEmpty()){
            txtNome.getStyleClass().add("text-prompt-erro");
            new Alert(AlertType.ERROR, "Campo nome é obrigatório!", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(txtCpf.getText().isEmpty()){
            new Alert(AlertType.ERROR, "Campo CPF/CNPJ é obrigatório", ButtonType.OK).showAndWait();
            return false;              
        }
        
        if(txtNascimento.getText().isEmpty()){
            txtNascimento.getStyleClass().add("text-prompt-erro");
            new Alert(AlertType.ERROR, "Campo Data de Nascimento é obrigatório", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(txtEmail.getText().isEmpty()){
            txtEmail.getStyleClass().add("text-prompt-erro");
            new Alert(AlertType.ERROR, "Campo Email é obrigatório", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(!txtEmail.getText().contains("@")){
            new Alert(AlertType.ERROR, "Campo Email deve conter pelo menos um '@'", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(txtTelefone.getText().isEmpty()){
            txtTelefone.getStyleClass().add("text-prompt-erro");
            new Alert(AlertType.ERROR, "Campo Telefone é obrigatório", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(txtLogradouro.getText().isEmpty()){
            txtLogradouro.getStyleClass().add("text-prompt-erro");
            new Alert(AlertType.ERROR, "Campo Logradouro é obrigatório", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(txtNumero.getText().isEmpty()){
            txtNumero.getStyleClass().add("text-prompt-erro");
            new Alert(AlertType.ERROR, "Campo Número é obrigatório", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(txtBairro.getText().isEmpty()){
            txtBairro.getStyleClass().add("text-prompt-erro");
            new Alert(AlertType.ERROR, "Campo Bairro é obrigatório", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(txtCidade.getText().isEmpty()){
            txtCidade.getStyleClass().add("text-prompt-erro");
            new Alert(AlertType.ERROR, "Campo Cidade é obrigatório", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(cbEstado.getSelectionModel().getSelectedItem() == null || cbEstado.getSelectionModel().getSelectedItem() == cbEstado.getItems().get(cbEstado.getItems().size()-1)){
            new Alert(AlertType.ERROR, "Selecione uma Unidade Federativa", ButtonType.OK).showAndWait();
            return false;
        }
        
        return true;
    }
    
    private boolean validarCamposEditar(){
        if(txtEditNome.getText().isEmpty()){
            txtEditNome.getStyleClass().add("text-prompt-erro");
            new Alert(AlertType.ERROR, "Campo nome é obrigatório!", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(txtEditCpf.getText().length() < 11){
            new Alert(AlertType.ERROR, "Campo CPF/CNPJ deve conter pelo menos 11 dígitos", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(txtEditNasc.getText().isEmpty()){
            txtEditNasc.getStyleClass().add("text-prompt-erro");
            new Alert(AlertType.ERROR, "Campo Data de Nascimento é obrigatório", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(txtEditEmail.getText().isEmpty()){
            txtEditEmail.getStyleClass().add("text-prompt-erro");
            new Alert(AlertType.ERROR, "Campo Email é obrigatório", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(!txtEditEmail.getText().contains("@")){
            new Alert(AlertType.ERROR, "Campo Email deve conter pelo menos um '@'", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(txtEditTel.getText().isEmpty()){
            txtEditTel.getStyleClass().add("text-prompt-erro");
            new Alert(AlertType.ERROR, "Campo Telefone é obrigatório", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(txtEditLogradouro.getText().isEmpty()){
            txtEditLogradouro.getStyleClass().add("text-prompt-erro");
            new Alert(AlertType.ERROR, "Campo Logradouro é obrigatório", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(txtEditBairro.getText().isEmpty()){
            txtEditBairro.getStyleClass().add("text-prompt-erro");
            new Alert(AlertType.ERROR, "Campo Bairro é obrigatório", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(txtEditCidade.getText().isEmpty()){
            txtEditCidade.getStyleClass().add("text-prompt-erro");
            new Alert(AlertType.ERROR, "Campo Cidade é obrigatório", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(cbEditEstado.getSelectionModel().getSelectedItem() == null || cbEditEstado.getSelectionModel().getSelectedItem() == cbEditEstado.getItems().get(cbEditEstado.getItems().size()-1)){
            new Alert(AlertType.ERROR, "Selecione uma Unidade Federativa", ButtonType.OK).showAndWait();
            return false;
        }
        
        return true;
    }
    
    private void editarCliente(){
        if(clienteEmEdicao == null)
            return;
       
        try {
            SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
            Date data = formatador.parse(clienteEmEdicao.getData_nascimento());
            String data_str = formatador.format(data);
            formatador = new SimpleDateFormat("dd/MM/yyyy");
            data_str = formatador.format(data);
            txtEditNasc.setText(data_str);
        } catch (ParseException ex) {
            exibirErro(ex);
        }
        
        
        txtEditEmail.setText(clienteEmEdicao.getEmail());
        txtEditTel.setText(clienteEmEdicao.getTelefone());
        txtEditLogradouro.setText(clienteEmEdicao.getLogradouro());
        txtEditNumero.setText(""+clienteEmEdicao.getNumero());
        txtEditComplemento.setText(clienteEmEdicao.getComplemento());
        txtEditBairro.setText(clienteEmEdicao.getBairro());
        txtEditCidade.setText(clienteEmEdicao.getMunicipio());
        cbEditEstado.getSelectionModel().select(clienteEmEdicao.getEstado());
        
        if(clienteEmEdicao instanceof PessoaFisica){
            PessoaFisica cli = (PessoaFisica) clienteEmEdicao;
            txtEditNome.setText(cli.getNome());
            txtEditCpf.setText(cli.getCpf());
        }else{
            PessoaJuridica cli = (PessoaJuridica) clienteEmEdicao;
            txtEditNome.setText(cli.getRazaoSocial());
            txtEditCpf.setText(cli.getCnpj()); 
        }        
        
    }
    
    /*
    * Tratamento de eventos
    */
    @FXML
    protected void btnPesquisar_pressed(ActionEvent event){
        String termo_busca = txtPesquisar.getText();
        
        if(termo_busca.isEmpty())
            return;
        
        buscarCliente(termo_busca);
    }
    
    @FXML
    protected void txtPesquisar_keypressed(KeyEvent event){
        String termo_busca = txtPesquisar.getText();
        KeyCode tecla = event.getCode();
        
        if(tecla == KeyCode.ENTER){
            buscarCliente(txtPesquisar.getText());
        }else if(tecla == KeyCode.BACK_SPACE && termo_busca.isEmpty()){
            buscarCliente("");
        }
    }
    
    @FXML
    protected void btnLimpar_pressed(ActionEvent event){
        Alert alert = new Alert(AlertType.CONFIRMATION, "Tem certeza que deseja limpar TODOS os campos?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        
        if(alert.getResult() == ButtonType.YES){
            limparCamposCadastro();
        }
    }
    
    @FXML
    protected void btnCadastrar_pressed(ActionEvent event){
        if(!validarCamposCadastro())
                return;
        
        Cliente pessoa;
        if(rbFisica.isSelected()){
            pessoa = new PessoaFisica();
            PessoaFisica pf = (PessoaFisica) pessoa;
            pf.setNome(txtNome.getText());
            pf.setCpf(txtCpf.getText());
        }else{
            pessoa = new PessoaJuridica();
            PessoaJuridica pj = (PessoaJuridica) pessoa;
            pj.setCnpj(txtCpf.getText());
            pj.setRazaoSocial(txtNome.getText());
        }
        
        
        try {
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            Date data = formatador.parse(txtNascimento.getText());
            formatador = new SimpleDateFormat("yyyy-MM-dd");
            pessoa.setData_nascimento(formatador.format(data));
        } catch (ParseException ex) {
            new Alert(AlertType.ERROR, "Data de nascimento informada é inválida", ButtonType.OK).showAndWait();
            return;
        }
        
        
        pessoa.setEmail(txtEmail.getText());
        pessoa.setTelefone(txtTelefone.getText());
        pessoa.setLogradouro(txtLogradouro.getText());
        pessoa.setNumero(Integer.parseInt(txtNumero.getText().trim()));
        pessoa.setComplemento(txtComplemento.getText());
        pessoa.setBairro(txtBairro.getText());
        pessoa.setMunicipio(txtCidade.getText());
        pessoa.setEstado(cbEstado.getSelectionModel().getSelectedItem().toString());
        try {
            ClienteDAO clienteDao = new ClienteDAO();
            clienteDao.inserir(pessoa);
            Alert alert = new Alert(AlertType.INFORMATION, "Cliente cadastrado com sucesso. Deseja fazer outro cadastro?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.NO){
                abas.getSelectionModel().selectFirst();
                buscarCliente("");
            }
            
            limparCamposCadastro();
        } catch (SQLException e) { 
            exibirErro(e);
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    @FXML
    protected void btnVisualizar_pressed(ActionEvent event){
        Cliente cliente = (Cliente)tabelaClientes.getSelectionModel().getSelectedItem();         
        visualizarInfo(cliente);
        abas.getTabs().get(0).setDisable(true);
        abas.getTabs().get(1).setDisable(true);
        abas.getTabs().get(2).setDisable(true);
        abas.getTabs().get(3).setDisable(false);
        abas.getSelectionModel().select(3);
        
    }
    
    @FXML
    protected void btnEditar_pressed(ActionEvent event){
        clienteEmEdicao = (Cliente) tabelaClientes.getSelectionModel().getSelectedItem();
        editarCliente();
        abas.getSelectionModel().select(2);
        abas.getTabs().get(0).setDisable(true);
        abas.getTabs().get(1).setDisable(true);
        abas.getTabs().get(2).setDisable(false);
    }
    
    @FXML
    protected void vis_btnVoltar_pressed(ActionEvent event){        
        abas.getTabs().get(0).setDisable(false);
        abas.getTabs().get(1).setDisable(false);
        abas.getTabs().get(2).setDisable(true);
        abas.getTabs().get(3).setDisable(true);
        abas.getSelectionModel().selectFirst();
        clienteEmVisualizacao = null;
    }
    
    @FXML
    protected void btnEditCancelar_pressed(ActionEvent event){
        limparCamposEditar();
        abas.getSelectionModel().selectFirst();
        abas.getTabs().get(2).setDisable(true);
        abas.getTabs().get(0).setDisable(false);
        abas.getTabs().get(1).setDisable(false);
    }    
    
    @FXML
    protected void btnEditSalvar_pressed(ActionEvent event){
        if(!validarCamposEditar())
            return;
        
        Alert alert = new Alert(AlertType.CONFIRMATION, "Tem certeza que deseja salvar todas as alterações?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Salvar alterações?");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.NO)
            return;
        
        try {
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            Date data = formatador.parse(txtEditNasc.getText());
            formatador = new SimpleDateFormat("yyyy-MM-dd");
            clienteEmEdicao.setData_nascimento(formatador.format(data));
        } catch (ParseException ex) {
            new Alert(AlertType.ERROR, "Data de nascimento informada é inválida", ButtonType.OK).showAndWait();
            return;
        }
                
        clienteEmEdicao.setEmail(txtEditEmail.getText());
        clienteEmEdicao.setTelefone(txtEditTel.getText());
        clienteEmEdicao.setLogradouro(txtEditLogradouro.getText());
        clienteEmEdicao.setNumero(Integer.parseInt(txtEditNumero.getText().trim()));
        clienteEmEdicao.setComplemento(txtEditComplemento.getText());
        clienteEmEdicao.setBairro(txtEditBairro.getText());
        clienteEmEdicao.setMunicipio(txtEditCidade.getText());   
        clienteEmEdicao.setEstado(cbEditEstado.getSelectionModel().getSelectedItem().toString());
        
        if(clienteEmEdicao instanceof PessoaFisica){
            PessoaFisica cli = (PessoaFisica) clienteEmEdicao;
            cli.setNome(txtEditNome.getText());
            cli.setCpf(txtEditCpf.getText());            
        }else{
            PessoaJuridica cli = (PessoaJuridica) clienteEmEdicao;
            cli.setRazaoSocial(txtEditNome.getText());
            cli.setCnpj(txtEditCpf.getText());           
        }
        
        ClienteDAO clienteDao = new ClienteDAO();
        try {
            clienteDao.alterar(clienteEmEdicao);
            Alert alert_ = new Alert(AlertType.INFORMATION, "Todas as alterações foram salvas com sucesso!", ButtonType.OK);
            alert_.setHeaderText("Alterações salvas");
            alert_.showAndWait();
            abas.getSelectionModel().getSelectedItem().setDisable(true);
            abas.getTabs().get(0).setDisable(false);
            abas.getTabs().get(1).setDisable(false);
            abas.getSelectionModel().selectFirst();
            txtPesquisar.setText(txtEditNome.getText());
            buscarCliente(txtEditNome.getText());
            limparCamposEditar();
        } catch (SQLException ex) {
            new Alert(AlertType.ERROR, "Erro ao salvar cliente. Contate o suporte!", ButtonType.OK).show();
            System.out.println("Falha ao alterar cliente: " + ex.getMessage());
        }
    }
    
    @FXML
    private void txtNascimento_keypressed(KeyEvent event){}
    
    @FXML 
    private void txtNascimento_keyReleased(KeyEvent event){
        TextFieldFormatter formatter = new TextFieldFormatter();
        formatter.setMask("##/##/####");
        formatter.setCaracteresValidos("0123456789");
        formatter.setTf(txtNascimento);
        formatter.formatter();
    }
    
    @FXML
    private void txtCpf_keyReleased(KeyEvent event){
        TextFieldFormatter formatter = new TextFieldFormatter();        
        formatter.setCaracteresValidos("0123456789");
        if(rbFisica.isSelected()){
            formatter.setMask("###.###.###-##");
        }else {
            formatter.setMask("##.###.###/####-##");
        }
        formatter.setTf(txtCpf);
        formatter.formatter();
    }
    
    @FXML
    private void txtTelefone_keyReleased(KeyEvent event){
        TextFieldFormatter formatter = new TextFieldFormatter();
        formatter.setMask("(##)#####-####");
        formatter.setCaracteresValidos("0123456789");
        formatter.setTf(txtTelefone);
        formatter.formatter();
    }
    
    @FXML
    private void txtNumero_keyReleased(KeyEvent event){
        TextFieldFormatter formatter = new TextFieldFormatter();
        formatter.setMask("#####");
        formatter.setCaracteresValidos("0123456789");
        formatter.setTf(txtNumero);
        formatter.formatter();
    }
    @FXML
    private void radioTipoClienteCheck(ActionEvent event){
        if(rbFisica.isSelected()){
            lblCadCpf.setText("CPF");
            lblCadNome.setText("Nome Completo");
            txtCpf.setPromptText("XXX.XXX.XXX-XX");            
        }else{
            lblCadCpf.setText("CNPJ");
            lblCadNome.setText("Razão Social");
            txtCpf.setPromptText("XX.XXX.XXX/XXXX-XX");
        }
    }

    @FXML
    void vis_btnModificar_pressed(ActionEvent event) {
        clienteEmEdicao = clienteEmVisualizacao;
        clienteEmVisualizacao = null;
        editarCliente();
        abas.getTabs().get(3).setDisable(true);
        abas.getSelectionModel().select(2);
        abas.getSelectionModel().getSelectedItem().setDisable(false);
        
    }
    
    @FXML
    private void btnEditDeletar_pressed(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Tem certeza que deseja apagar permanentemente este cliente?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Deletar cliente?");
        alert.showAndWait();
        
        if(alert.getResult() == ButtonType.NO)
            return;
        
        
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.excluir(clienteEmEdicao);
            new Alert(AlertType.INFORMATION, "Cliente deletado com sucesso").showAndWait();
            abas.getSelectionModel().getSelectedItem().setDisable(true);
            abas.getSelectionModel().selectFirst();
            abas.getSelectionModel().getSelectedItem().setDisable(false);
            buscarCliente("");
        } catch (SQLException ex) {
            exibirErro(ex);
            System.out.println("Erro ao apagar cliente: " + ex.getMessage());
            ex.printStackTrace();
        }
        
    }
    
    @FXML
    void txtEditCpf_keyReleased(KeyEvent event) {
        TextFieldFormatter formatter = new TextFieldFormatter();        
        formatter.setCaracteresValidos("0123456789");
        if(Cliente.ePessoaFisica(clienteEmEdicao)){
            formatter.setMask("###.###.###-##");
        }else {
            formatter.setMask("##.###.###/####-##");
        }
        formatter.setTf(txtEditCpf);
        formatter.formatter();
    }
    
    @FXML
    void txtEditNasc_keyReleased(KeyEvent event) {
        TextFieldFormatter formatter = new TextFieldFormatter();
        formatter.setMask("##/##/####");
        formatter.setCaracteresValidos("0123456789");
        formatter.setTf(txtEditNasc);
        formatter.formatter();
    }
    
    @FXML
    void txtEditTel_keyReleased(KeyEvent event) {
        TextFieldFormatter formatter = new TextFieldFormatter();
        formatter.setMask("(##)#####-####");
        formatter.setCaracteresValidos("0123456789");
        formatter.setTf(txtEditTel);
        formatter.formatter();
    }
    
    
    @FXML
    void txtEditNumero_keyReleased(KeyEvent event) {
        TextFieldFormatter formatter = new TextFieldFormatter();
        formatter.setMask("#####");
        formatter.setCaracteresValidos("0123456789");
        formatter.setTf(txtEditNumero);
        formatter.formatter();
    }

    
}
