/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prolog.java;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.jpl7.Query;
import org.jpl7.Term;

/**
 *
 * @author Daniel Diaz
 */
public class principal extends javax.swing.JFrame {

    JButton btnSugerencia =   new JButton("Sugerencia");
    JButton btnReiniciar =   new JButton("Reiniciar");
    JButton btnVerificar =   new JButton("Verificar");
    JButton btnSolucion =   new JButton("Ver solucion");
    JButton btnTablero =   new JButton("Generar Tablero");
    public JTextField fields[][];
    public JTextField fieldsSolucion[][];
    int cantidadSugerencias = 0;
    String tableroInicial [][] = new String[9][9];
    boolean flagTableroInicio = true;
    int resultadoTableroFinal[][] = new int[9][9];
    boolean flagSugerencias = false;
    int cantidadDigitos = 0;

    /**
     * Creates new form principal
     */
    public principal() {
        initComponents();
//        JFrame frame = new JFrame();
//        jPanel2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jPanel2.setLayout(new BorderLayout());
        jPanel2.add(new SudokuBoard(false));
        
        jPanel2.add(new MenuPane(), BorderLayout.AFTER_LINE_ENDS);
//        jPanel2.pack();
        jPanel2.setVisible(true);
        
        jPanel4.setLayout(new BorderLayout());
        jPanel4.add(new SudokuBoard(true));
        
//        jPanel2.add(new MenuPane(), BorderLayout.AFTER_LINE_ENDS);
//        jPanel2.pack();
        jPanel4.setVisible(true);
        
        
        
        btnTablero.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
              nuevoJuego();
              comprobarTablero();
              
            } 
        } );
        
       
        btnVerificar.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
              System.out.println(getEspaciosVacios());
              System.out.println(getErrores());
            } 
        } );
        btnSugerencia.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
              if(!flagSugerencias){sugerencia();}
              colocarPistas();
              jTextField3.setText(Integer.toString(cantidadSugerencias));
              
              
            } 
        } );
        btnReiniciar.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
              reiniciar();
            } 
        } );
        marcarDiagonal1();
        marcarDiagonal2();
        
        Query load = new Query("consult('C:/Users/Usuario/Documents/NetBeansProjects/Prolog-Java/src/prolog/Inicio.pl')");
        load.hasSolution();
 
    }
    
    
    
    public int getEspaciosVacios(){
        String tablero[][] = returnTablero();
        int espaciosVacios=0;
        for(int i =0; i<9;i++){
            for(int j=0;j<9;j++){
                if(tablero[i][j].equals("_")){
                   espaciosVacios++;

                }
                        
            }
        }
        return espaciosVacios;
    }
    
    
    public int getErrores(){
        int cantidadErrores = 0;
        String tablero[][] = returnTablero();
        for(int i =0; i<9;i++){
            for(int j=0;j<9;j++){
                if(!tablero[i][j].equals("_")){
                   if(!tablero[i][j].equals(Integer.toString(resultadoTableroFinal[i][j]))){
                      cantidadErrores++;
                   }

                }
                        
            }
        }
        return cantidadErrores;
    }
    
    
    public void nuevoJuego(){
         flagSugerencias = false;
        flagTableroInicio = true;
        cantidadSugerencias = 0;
        cantidadDigitos = 0;
        for(int i =0; i<9;i++){
            for(int j=0;j<9;j++){
                JTextField field = fields[i][j];
                field.setForeground(Color.BLACK);
                        
            }
        }
    }
    
    
    public void sugerencia(){
        
        String tablero[][] = returnTablero();
//         System.out.println("Tablero a prolog:" + Arrays.deepToString(tablero));
        String verify = String.format("sudoku(%s,T)", Arrays.deepToString(tablero));
        Query verifyBoard = new Query(verify);
        java.util.Map<String, Term> res;
        res =  verifyBoard.oneSolution();
        Term[] tableroFinal;
        int tablerolista [];
        tableroFinal = res.get("T").listToTermArray();
        int tableroLista[][] = new int[9][9];
        for(int i = 0;i<9;i++){
            String objeto = tableroFinal[i].toString();
            int[] arr = Arrays.stream(objeto.substring(1, objeto.length()-1).split(","))
            .map(String::trim).mapToInt(Integer::parseInt).toArray();
//            System.out.println(Arrays.toString(arr));
            
            System.arraycopy(arr,0,tableroLista[i],0,arr.length);
        }
//        System.out.println(Arrays.toString(tableroLista));
//        colocarPistas(tableroLista);
        for(int i =0; i<9;i++){
             System.arraycopy(tableroLista[i], 0, resultadoTableroFinal[i], 0,9);
         }
        flagSugerencias = true;
        
//        return tableroLista;
    }
    
    
    public void llenarTableroSolucion(){
        Font font1 = new Font("SansSerif", Font.BOLD, 35);
        for(int i =0; i<9;i++){
            for(int j=0;j<9;j++){
                JTextField field = fieldsSolucion[i][j];
          
                field.setFont(font1);
                field.setHorizontalAlignment(JTextField.CENTER);
                 field.setText(Integer.toString(resultadoTableroFinal[i][j]));
                        
            }
        }
    }
    
    public void colocarPistas(){
        
        if(cantidadSugerencias == 5){
            JOptionPane.showMessageDialog(jPanel1,
            "Ya no quedan mas sugerencias",
            "Sin sugerencias",
            JOptionPane.ERROR_MESSAGE);
            return;
        }
        String tablero[][] = returnTablero();
        int x=0;
        int y=0;
        boolean flag=false;
        int posicionesX[] = new int[64];
        int posicionesY[] = new int[64];
        int cont = 0;
        for(int i =0; i<9;i++){
            for(int j=0;j<9;j++){
                if(tablero[i][j].equals("_")){
                    x = i;
                    y = j;
                    posicionesX[cont] = x ;
                    posicionesY[cont] = y;
                    cont++;
//                    flag = true;
//                    break;
                }
                        
            }
//            if(flag){
//                break;
//            }
        }
        int posRandom =   (int)Math.floor(Math.random()*(cont-0+1)+0);
        int xR = posicionesX[posRandom];
        int yR = posicionesY[posRandom];
        
        int valor = resultadoTableroFinal[xR][yR];
        JTextField field = fields[xR][yR];
        field.setText(Integer.toString(valor));
        field.setForeground(Color.GREEN);
        cantidadSugerencias++;
        
    }
    
    public void comprobarTablero(){
        System.out.println("entra");
        boolean flag = true;
        String tablero[][] = generarTablero();
        while(flag){      
            String verify = String.format("sudoku(%s,T)", Arrays.deepToString(tablero));
            Query verifyBoard = new Query(verify);
            
            if (verifyBoard.hasSolution()) {
                System.out.println("es valido");
                flag = false;
            }
            else{
                tablero = generarTablero();
            }
            
        }
        llenarTablero(tablero);
        sugerencia();
        llenarTableroSolucion();
        
        
    }
    
    
    public void llenarTablero(String tablero[][]){
        for(int i =0; i<9;i++){
            for(int j=0;j<9;j++){
                JTextField field = fields[i][j];
                field.setText(tablero[i][j]);
                field.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
//                  System.out.println("cambio");
                }
                public void removeUpdate(DocumentEvent e) {
//                  System.out.println("cambio1");
                }
                public void insertUpdate(DocumentEvent e) {
//                  System.out.println("cambio2");
                    cantidadDigitos++;
                    jTextField5.setText(Integer.toString(cantidadDigitos));
                    
                }
                        
               });
        }
    }
    }
    
    public String[][] generarTablero(){
        int tablero[][]= new int[9][9];
        String tableroString[][] = new String[9][9];
        int min = 1;
        int max = 9;
        int numeroRandom; 
        int posRandom1;
        int posRandom2;
        int cont = 1;
        for(int i =0; i<9;i++){
            for(int j=0;j<9;j++){
                tablero[i][j] = 0;
            }
        }   
        while(cont <= 17){
        numeroRandom = (int)Math.floor(Math.random()*(max-min+1)+min);
        posRandom1 = (int)Math.floor(Math.random()*(8-min+1)+0);
        posRandom2 =  (int)Math.floor(Math.random()*(8-min+1)+0);
            if(tablero[posRandom1][posRandom2] == 0){
                tablero[posRandom1][posRandom2] = numeroRandom;
                cont++;
            }
            

        }
        
//       System.out.println(Arrays.deepToString(formatoTablero(tablero)));
        
       if(flagTableroInicio){
           copiarTablero(formatoTablero(tablero));
           
       }
       return formatoTablero(tablero);
    }
    
    public void copiarTablero(String tablero[][]){
         for(int i =0; i<9;i++){
             System.arraycopy(tablero[i], 0, tableroInicial[i], 0,9);
         }
//         System.out.println(Arrays.deepToString(tableroInicial));
    }
        
    
    public void reiniciar(){
        for(int i =0; i<9;i++){
            for(int j=0;j<9;j++){
                JTextField field = fields[i][j];
                field.setForeground(Color.BLACK);
                field.setText(tableroInicial[i][j]);
                        
            }
        }
        cantidadSugerencias = 0;
        cantidadDigitos = 0;
    }
    
    public String[][] formatoTablero(int tablero[][]){
        String tableroString[][] = new String[9][9];
        String valor;
         for(int i =0; i<9;i++){
            for(int j=0;j<9;j++){
                valor = String.valueOf(tablero[i][j]);
                if(valor.equals("0")){
                    valor = "_";
                }
                tableroString[i][j] = valor;
            }
        } 
        return tableroString;
    }
    
    public void marcarDiagonal1(){
        Font font1 = new Font("SansSerif", Font.BOLD, 35);
       
        for(int i =0; i<9;i++){
            for(int j=0;j<9;j++){
                JTextField field = fields[i][j];
          
                field.setFont(font1);
                field.setHorizontalAlignment(JTextField.CENTER);
                if(i==j){
                  field.setBackground(Color.GRAY);  
                }
                if(i+j==8){
                    field.setBackground(Color.GRAY);  
                }
                        
            }
        }
       
    }
    public void marcarDiagonal2(){
        Font font1 = new Font("SansSerif", Font.BOLD, 35);
       
        for(int i =0; i<9;i++){
            for(int j=0;j<9;j++){
                JTextField field = fieldsSolucion[i][j];
          
                field.setFont(font1);
                field.setHorizontalAlignment(JTextField.CENTER);
                if(i==j){
                  field.setBackground(Color.GRAY);  
                }
                if(i+j==8){
                    field.setBackground(Color.GRAY);  
                }
                        
            }
        }
       
    }
    
    
    public String[][] returnTablero(){
        String tablero[][] = new String[9][9];
        for(int i =0; i<9;i++){
            for(int j=0;j<9;j++){
                JTextField field = fields[i][j];
//                System.out.println(field.getText());
                tablero[i][j] = field.getText();
                        
            }
        }
//        System.out.println(Arrays.deepToString(tablero));
        return tablero;
    }
  
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1293, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 761, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Juego", jPanel2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1293, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 761, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Solucion", jPanel4);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Cantidad de verificaciones realizadas:");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Cantidad de celdas de ingreso de dígitos:");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Tipo Finalización:");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Cantidad de errores de verificación:");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText(" Cantidad de sugerencias utilizadas:");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        jTextField4.setEditable(false);
        jTextField4.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        jTextField5.setEditable(false);
        jTextField5.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(478, 478, 478)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(582, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(97, 97, 97)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(804, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(288, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(108, 108, 108)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(608, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Estadisticas", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables




    public class MenuPane extends JPanel {

        public MenuPane() {
            setBorder(new EmptyBorder(4, 4, 4, 4));
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            
            add(btnTablero , gbc);
            gbc.gridy++;
            add(btnSugerencia , gbc);
            gbc.gridy++;
            add(btnReiniciar, gbc);
            gbc.gridy++;
            add(btnVerificar, gbc);
            gbc.gridy++;
            add(btnSolucion, gbc);
            
            

        }
        
        
        
    }

    public class SudokuBoard extends JPanel {

        public static final int GRID_ROWS = 1;
        public static final int GRID_COLUMNS = 1;
        public static final int BOARD_ROWS = 9;
        public static final int BOARD_COLUMNS = 9;
//        public JTextField fields[][];

        public SudokuBoard(boolean solucion) {
            if(solucion){
                crearTableroSolucion();
            }
            else{
                crearTableroNormal();
            }
            
        }
        
        public void crearTableroSolucion(){
            setBorder(new EmptyBorder(4, 4, 4, 4));
            fieldsSolucion = new JTextField[GRID_ROWS * BOARD_ROWS][GRID_COLUMNS * BOARD_COLUMNS];

            setLayout(new GridLayout(GRID_ROWS, GRID_COLUMNS, 2, 2));
            for (int row = 0; row < GRID_ROWS; row++) {
                for (int col = 0; col < GRID_COLUMNS; col++) {
                    int startRow = row * GRID_ROWS;
                    int startCol = col * GRID_COLUMNS;
                    add(createBoardSolucion(fieldsSolucion, startRow, startCol));
                }
            }
        }
        
        public void crearTableroNormal(){
            setBorder(new EmptyBorder(4, 4, 4, 4));
            fields = new JTextField[GRID_ROWS * BOARD_ROWS][GRID_COLUMNS * BOARD_COLUMNS];

            setLayout(new GridLayout(GRID_ROWS, GRID_COLUMNS, 2, 2));
            for (int row = 0; row < GRID_ROWS; row++) {
                for (int col = 0; col < GRID_COLUMNS; col++) {
                    int startRow = row * GRID_ROWS;
                    int startCol = col * GRID_COLUMNS;
                    add(createBoardNormal(fields, startRow, startCol));
                }
            }
        }
        
        protected JPanel createBoardSolucion(JTextField fiels[][], int startRow, int startCol) {
            JPanel panel = new JPanel(new GridLayout(3, 3, 2, 2));
            panel.setBorder(new CompoundBorder(new LineBorder(Color.DARK_GRAY, 2), new EmptyBorder(2, 2, 2, 2)));

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    int rowIndex = (startRow + row) * 3;
                    int colIndex = (startCol + col) * 3;
                    panel.add(createSubBoard(fieldsSolucion, rowIndex, colIndex));
                }
            }
            return panel;
        }

        protected JPanel createBoardNormal(JTextField fiels[][], int startRow, int startCol) {
            JPanel panel = new JPanel(new GridLayout(3, 3, 2, 2));
            panel.setBorder(new CompoundBorder(new LineBorder(Color.DARK_GRAY, 2), new EmptyBorder(2, 2, 2, 2)));

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    int rowIndex = (startRow + row) * 3;
                    int colIndex = (startCol + col) * 3;
                    panel.add(createSubBoard(fields, rowIndex, colIndex));
                }
            }
            return panel;
        }

        protected JPanel createSubBoard(JTextField[][] fields, int startRow, int startCol) {
            JPanel panel = new JPanel(new GridLayout(3, 3, 2, 2));
            panel.setBorder(new CompoundBorder(new LineBorder(Color.GRAY, 2), new EmptyBorder(2, 2, 2, 2)));

            populateFields(fields, startRow, startCol);
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    panel.add(fields[row + startRow][col + startCol]);
                }
            }
            return panel;
        }

        protected void populateFields(JTextField[][] fields, int startRow, int startCol) {
            for (int row = startRow; row < startRow + 3; row++) {
                for (int col = startCol; col < startCol + 3; col++) {
                    fields[row][col] = new JTextField(4);
                }
            }
        }
    }

    
    
    
    
    

}
