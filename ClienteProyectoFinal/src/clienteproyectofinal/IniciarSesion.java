/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteproyectofinal;

import clases.CodigosUso;
import clases.Comunicacion;
import clases.Seguridad;
import clases.Usuario;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.swing.JOptionPane;

/**
 *
 * @author disen
 */
public class IniciarSesion extends javax.swing.JFrame {

    
    private Socket servidor;
    private PrivateKey clavePrivPropia;
    private PublicKey clavePubAjena;
    private PublicKey clavePubPropia;
    private SealedObject so;
    /**
     * Creates new form IniciarSesion
     */
    public IniciarSesion() throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        
            initComponents();
            setLocationRelativeTo(null);
            
            rsscalelabel.RSScaleLabel.setScaleLabel(limagen,"src/imagenes/png-clipart-computer-icons-dating-couple-icon-design-couple-love-text.png");
            iniciar();
            //generaClaves();
        
    }

    
    private void iniciar() throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        //blError_Pwd.setVisible(false);
        //lblError_User.setVisible(false);
        //setBackground(new Color(0, 255, 0, 0));
        iniciarConexionServidor();
        intercambioPublicas();
    }
    
    
    private void iniciarConexionServidor() throws UnknownHostException, NoSuchAlgorithmException, IOException {

        servidor = new Socket(InetAddress.getLocalHost(), 5000);
        Object[] claves = Seguridad.generarClaves();

        clavePrivPropia = (PrivateKey) claves[0];
        clavePubPropia = (PublicKey) claves[1];
    }
    
     private void intercambioPublicas() throws IOException, ClassNotFoundException {

        //envio mi clave publica
        Comunicacion.enviarObjeto(servidor, clavePubPropia);
        //recibo la clave publica del servidor
        this.clavePubAjena = (PublicKey) Comunicacion.recibirObjeto(servidor);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnIniciarSesion = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        txtUsuario = new javax.swing.JTextField();
        pswPassword = new javax.swing.JPasswordField();
        limagen = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 153, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Usuario:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Password:");

        btnIniciarSesion.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnIniciarSesion.setText("Inciar Sesion");
        btnIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarSesionActionPerformed(evt);
            }
        });

        btnRegistrar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnRegistrar.setText("Registrarse");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        txtUsuario.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        pswPassword.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        pswPassword.setText("jPasswordField1");

        limagen.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(btnIniciarSesion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRegistrar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUsuario)
                            .addComponent(pswPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE))))
                .addGap(198, 198, 198))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(limagen, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(limagen, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pswPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar)
                    .addComponent(btnIniciarSesion))
                .addGap(75, 75, 75))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        Registro r=new Registro(servidor, clavePrivPropia, clavePubAjena);
        r.show();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private String resumirPwd() throws NoSuchAlgorithmException {
        char[] pass = pswPassword.getPassword();
        String passStr = new String(pass);
        return new String(Seguridad.resumirPwd(passStr));
    }

    
    private void btnIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarSesionActionPerformed
       if (!txtUsuario.getText().isEmpty() && pswPassword.getPassword().length != 0) {

            try {
                enviarRespuesta(CodigosUso.LOGIN);
                
                System.out.println("Escuchando login");
                
                String pwd = resumirPwd();
                Usuario u = new Usuario(txtUsuario.getText(), pwd);
                so = Seguridad.cifrar(clavePubAjena, u);
                Comunicacion.enviarObjeto(servidor, so);
                System.out.println("Usuario enviado " + u.getEmail());

                
                so = (SealedObject) Comunicacion.recibirObjeto(servidor);
                short res = (short) Seguridad.descifrar(clavePrivPropia, so);
                System.out.println("Respuesta del Servidor " + res);

                ComprobarUsuarioExiste(res);

            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                    | IOException | IllegalBlockSizeException | ClassNotFoundException | BadPaddingException ex) {
            }

        } else {
            if (txtUsuario.getText().isEmpty()) {
                //lblError_User.setVisible(true);
            } else {
                //lblError_Pwd.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnIniciarSesionActionPerformed

    
    /**
     * 
     * @param res 
     */
    private void ComprobarUsuarioExiste(short res) {
        try {
            switch (res) {

                case CodigosUso.CODE_USER_EXISTS:
                    System.out.println("Usuario inicio sesion correctamente");
                    //Recibo usuario existente en la tabla usuario
                    so = (SealedObject) Comunicacion.recibirObjeto(servidor);
                    
                    Usuario user = (Usuario) Seguridad.descifrar(clavePrivPropia, so);

                    //recibimos si el usuario esta activado, tiene preferencias y
                    //el rol que tiene en la tabla roles_users
                    so = (SealedObject) Comunicacion.recibirObjeto(servidor);
                    short code = (short) Seguridad.descifrar(clavePrivPropia, so);
                    System.out.println(code);
                    DatosUsuario(code, user);
                    break;

                case CodigosUso.EMAIL_LOGIN_INCORRECTO:
                    System.out.println("Este email no existe o usuario ");
                    //lblError_User.setText("Este email no existe");
                    //lblError_User.setVisible(true);
                    break;

                case CodigosUso.PWD_LOGIN_INCORRECTO:
                    System.out.println("La contraseña no coincide con el email insertado");
                    //lblError_Pwd.setText("La contraseña no coincide con el email insertado");
                    //lblError_Pwd.setVisible(true);
                    break;
                
            }
        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            ex.printStackTrace();
        }
    }
    /**
     *
     * @param res
     */
    private void enviarRespuesta(short res) {
        try {
            so = Seguridad.cifrar(clavePubAjena, res);
            Comunicacion.enviarObjeto(servidor, so);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException ex) {
        }
    }
    
    
     /**
     * 
     * @param code
     * @param userLog 
     */
    private void DatosUsuario(short code, Usuario userLog) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        //System.out.println("d "+code );
        switch (code) {
            case CodigosUso.CODE_USER_NOT_ACTIVATED:
                JOptionPane.showMessageDialog(null, "Usuario no activado espera a que lo haga un Admin");
                break;

            case CodigosUso.PREFERENCES:
                //initPreferences(userLog);
                break;

            case CodigosUso.LOGIN_CORRECTO:
                IniciarVentanaPrincipal(false);
                //iniciamos menu principal en modo usuario
                //initMainView(false);
            break;
            case CodigosUso.CODE_USER_USER:
                
                try {
                    enviarRespuesta(CodigosUso.C_Preferencias);
                    so = Seguridad.cifrar(clavePubAjena, userLog);
                    Comunicacion.enviarObjeto(servidor, so);
                    
                    so = (SealedObject) Comunicacion.recibirObjeto(servidor);
                    code = (short) Seguridad.descifrar(clavePrivPropia, so);
                    System.out.println(userLog.getId());
                    if(code==CodigosUso.C_Preferencias_notiene){
                        System.out.println("no tiene preferencias en la BD");
                        
                        VentanaPreferencias pf=new VentanaPreferencias(userLog,servidor, clavePrivPropia, clavePubAjena,"crear");
                        pf.show();
                        this.hide();
                    }
                    else{
                        VentanaPrincipal vp=new VentanaPrincipal(userLog,false,servidor,clavePrivPropia,clavePubAjena);
                        vp.show();
                        
                        this.hide();
                    }
            
            
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
                //iniciamos menu principal en modo usuario
                //IniciarVentanaPrincipal(false);
                break;

            case CodigosUso.CODE_USER_ADMIN:
                //iniciamos menu principal en modo admin
                IniciarVentanaPrincipal(true);
                //initMainView(true);
                break;
        }
    }
    
    /**
     * 
     * @param admin 
     */
    private void IniciarVentanaPrincipal(boolean admin) {
        VentanaPrincipal vpa=new VentanaPrincipal(admin, servidor, clavePrivPropia, clavePubAjena);
        vpa.setVisible(true);
        this.dispose();
    }
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
            java.util.logging.Logger.getLogger(IniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new IniciarSesion().setVisible(true);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(IniciarSesion.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(IniciarSesion.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(IniciarSesion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciarSesion;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel limagen;
    private javax.swing.JPasswordField pswPassword;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
