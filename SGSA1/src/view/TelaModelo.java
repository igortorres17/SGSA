/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ControleModelo;
import java.awt.Color;
import java.awt.Component;

/**
 *
 * @author root
 */
public class TelaModelo extends javax.swing.JFrame {

    /**
     * Creates new form TelaModelo
     */
    
    private ControleModelo controle;
    
    public TelaModelo() {
        initComponents();
        setLocationRelativeTo(null);
        configComponent();
        controle = new ControleModelo();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        tabCadastro = new javax.swing.JPanel();
        tipoCadLabel = new javax.swing.JLabel();
        tipoCadComboBox = new javax.swing.JComboBox<>();
        outrosCadLabel = new javax.swing.JLabel();
        outrosCadTextField = new javax.swing.JTextField();
        marcaCadLabel = new javax.swing.JLabel();
        marcaCadTextField = new javax.swing.JTextField();
        numPortasCadLabel = new javax.swing.JLabel();
        numPortasCadComboBox = new javax.swing.JComboBox<>();
        combustivelCadLabel = new javax.swing.JLabel();
        cobustivelCadComboBox = new javax.swing.JComboBox<>();
        MotorCadLabel = new javax.swing.JLabel();
        MotorCadTextField = new javax.swing.JTextField();
        cadastrarCadButton = new javax.swing.JButton();
        limparCadButton = new javax.swing.JButton();
        nomeCadLabel = new javax.swing.JLabel();
        nomeCadjTextField = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        modificarModeloCheckBox = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modelo");
        setResizable(false);
        setSize(new java.awt.Dimension(100, 100));
        setType(java.awt.Window.Type.UTILITY);

        jTabbedPane1.setBackground(new java.awt.Color(204, 204, 204));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(300, 414));
        jTabbedPane1.setVerifyInputWhenFocusTarget(false);

        tabCadastro.setBackground(new java.awt.Color(255, 255, 255));
        tabCadastro.setPreferredSize(new java.awt.Dimension(200, 200));

        tipoCadLabel.setText("Tipo:");

        tipoCadComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CARRO DE PASSEIO", "VAN", "CAMINHÃO", "CAMINHONETE", "OUTROS.." }));
        tipoCadComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoCadComboBoxActionPerformed(evt);
            }
        });

        outrosCadLabel.setText("Outros:");

        outrosCadTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                outrosCadTextFieldMouseClicked(evt);
            }
        });

        marcaCadLabel.setText("Marca:");

        numPortasCadLabel.setText("N.Portas:");

        numPortasCadComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "2", "3", "4", "5" }));

        combustivelCadLabel.setText("Combustível:");

        cobustivelCadComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gasolina", "Etanol", "Diesel", "Flex", " " }));

        MotorCadLabel.setText("Motor:");

        cadastrarCadButton.setText("Cadastrar");
        cadastrarCadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cadastrarCadButtonActionPerformed(evt);
            }
        });

        limparCadButton.setText("Limpar");

        nomeCadLabel.setText("Nome:");

        javax.swing.GroupLayout tabCadastroLayout = new javax.swing.GroupLayout(tabCadastro);
        tabCadastro.setLayout(tabCadastroLayout);
        tabCadastroLayout.setHorizontalGroup(
            tabCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabCadastroLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(nomeCadLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomeCadjTextField)
                .addGap(23, 23, 23))
            .addGroup(tabCadastroLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(tipoCadLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tipoCadComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(outrosCadLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outrosCadTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(tabCadastroLayout.createSequentialGroup()
                .addGroup(tabCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(tabCadastroLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(MotorCadLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(MotorCadTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabCadastroLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(marcaCadLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(marcaCadTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addGroup(tabCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabCadastroLayout.createSequentialGroup()
                        .addComponent(combustivelCadLabel)
                        .addGap(7, 7, 7)
                        .addComponent(cobustivelCadComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabCadastroLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(numPortasCadLabel)
                        .addGap(7, 7, 7)
                        .addComponent(numPortasCadComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(tabCadastroLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(cadastrarCadButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(limparCadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );
        tabCadastroLayout.setVerticalGroup(
            tabCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabCadastroLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(tabCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoCadComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outrosCadTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outrosCadLabel)
                    .addComponent(tipoCadLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(tabCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeCadjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nomeCadLabel))
                .addGap(9, 9, 9)
                .addGroup(tabCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(marcaCadLabel)
                    .addComponent(marcaCadTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combustivelCadLabel)
                    .addComponent(cobustivelCadComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tabCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MotorCadLabel)
                    .addComponent(MotorCadTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numPortasCadLabel)
                    .addComponent(numPortasCadComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                .addGroup(tabCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cadastrarCadButton)
                    .addComponent(limparCadButton))
                .addGap(44, 44, 44))
        );

        jTabbedPane1.addTab("Cadastro", tabCadastro);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 421, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 387, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab2", jPanel3);

        modificarModeloCheckBox.setText("Modificar");
        modificarModeloCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarModeloCheckBoxActionPerformed(evt);
            }
        });

        jLabel1.setText("Cadastre seus modelos de veículos.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(modificarModeloCheckBox)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(16, 16, 16)
                .addComponent(modificarModeloCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tipoCadComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoCadComboBoxActionPerformed
        System.out.println(""+tipoCadComboBox.getSelectedIndex());
        if(tipoCadComboBox.getSelectedIndex()==4){
            outrosCadTextField.setEnabled(true);
        }
        else{
            outrosCadTextField.setEnabled(false);
        }
    }//GEN-LAST:event_tipoCadComboBoxActionPerformed

    //permite
    
    private void modificarModeloCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarModeloCheckBoxActionPerformed
        // TODO add your handling code here:
        if(modificarModeloCheckBox.isSelected()){
            habilitarComponentes(false);
        }
        else{
            habilitarComponentes(true);
        }
    }//GEN-LAST:event_modificarModeloCheckBoxActionPerformed

    private void cadastrarCadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cadastrarCadButtonActionPerformed
       
      int fun = controle.inserir(tipoCadComboBox, outrosCadTextField, nomeCadjTextField, marcaCadTextField, numPortasCadComboBox, MotorCadTextField, cobustivelCadComboBox);
// TODO add your handling code here:
    }//GEN-LAST:event_cadastrarCadButtonActionPerformed

    private void outrosCadTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outrosCadTextFieldMouseClicked
        // TODO add your handling code here:
        setBackground(Color.WHITE);
    }//GEN-LAST:event_outrosCadTextFieldMouseClicked
    
    //Habilita os componentes do painel de cadastro, altecação e Exclusão
    
    private void habilitarComponentes(boolean desabilitar){
        
            Component[] componentes = tabCadastro.getComponents();
           
           for(int i=0; i<componentes.length; i++){
               componentes[i].setEnabled(desabilitar);
               outrosCadTextField.setEnabled(false);
           }
    }
    
    private void configComponent(){
        modificarModeloCheckBox.setSelected(true);
        habilitarComponentes(false);
    }
    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel MotorCadLabel;
    private javax.swing.JTextField MotorCadTextField;
    private javax.swing.JButton cadastrarCadButton;
    private javax.swing.JComboBox<String> cobustivelCadComboBox;
    private javax.swing.JLabel combustivelCadLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton limparCadButton;
    private javax.swing.JLabel marcaCadLabel;
    private javax.swing.JTextField marcaCadTextField;
    private javax.swing.JCheckBox modificarModeloCheckBox;
    private javax.swing.JLabel nomeCadLabel;
    private javax.swing.JTextField nomeCadjTextField;
    private javax.swing.JComboBox<String> numPortasCadComboBox;
    private javax.swing.JLabel numPortasCadLabel;
    private javax.swing.JLabel outrosCadLabel;
    private javax.swing.JTextField outrosCadTextField;
    private javax.swing.JPanel tabCadastro;
    private javax.swing.JComboBox<String> tipoCadComboBox;
    private javax.swing.JLabel tipoCadLabel;
    // End of variables declaration//GEN-END:variables
}