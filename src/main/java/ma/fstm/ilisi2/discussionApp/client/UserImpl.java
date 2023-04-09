package ma.fstm.ilisi2.discussionApp.client;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import ma.fstm.ilisi2.discussionApp.server.Forum;
import proxy.Proxy;
import proxy.ProxyImpl;

import javax.swing.*;
import java.awt.*;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 *
 * @author abdo
 */
public class UserImpl extends JFrame implements User {

    /**
     * Creates new form ForumDiscussion
     */
    public UserImpl() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold default-state="collapsed" desc="Generated Code">
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        inputChat = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        connectButton = new javax.swing.JButton();
        sendButton = new javax.swing.JButton();
        usersButton = new javax.swing.JButton();
        disconnectButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat Forum");
        getContentPane().setLayout(new java.awt.GridLayout(2, 1, 10, 10));

        chatArea.setColumns(60);
        chatArea.setRows(10);
        chatArea.setEditable(false);
        chatArea.setBackground(Color.GRAY);
        chatArea.setForeground(Color.WHITE);
        jScrollPane1.setViewportView(chatArea);

        getContentPane().add(jScrollPane1);

        inputChat.setMinimumSize(new java.awt.Dimension(300, 40));
        inputChat.setPreferredSize(new java.awt.Dimension(300, 40));
        inputChat.setSize(300,40);
        inputChat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER)
                    sendButtonActionPerformed(null);
            }
        });
        jPanel1.add(inputChat);

        jPanel1.setMinimumSize(new java.awt.Dimension(382, 30));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 30));
        jPanel1.setLayout(new java.awt.FlowLayout(FlowLayout.CENTER, 10, 10));
        connectButton.setText("Connect");
        connectButton.addActionListener(this::connectButtonActionPerformed);
        jPanel1.add(connectButton);

        sendButton.setText("Send");
        sendButton.addActionListener(this::sendButtonActionPerformed);
        sendButton.setEnabled(false);
        jPanel1.add(sendButton);

        usersButton.setText("users");
        usersButton.addActionListener(this::usersButtonActionPerformed);
        usersButton.setEnabled(false);
        jPanel1.add(usersButton);

        disconnectButton.setText("disconnect");
        disconnectButton.addActionListener(this::disconnectButtonActionPerformed);
        jPanel1.add(disconnectButton);

        getContentPane().add(jPanel1);

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>
    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            System.out.println("rmi://localhost:9099/Server");
            this.forum= (Forum) Naming.lookup("rmi://localhost:9099/Server");
            Proxy proxy = new ProxyImpl(this);
            this.id = this.forum.entrer(proxy);
            connectButton.setEnabled(false);
            disconnectButton.setEnabled(true);
            sendButton.setEnabled(true);
            usersButton.setEnabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try{
            this.forum.dire(this.id,this.inputChat.getText());
            inputChat.setText("");
        }catch( RemoteException ex){
            ex.printStackTrace();
        }
    }

    private void usersButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            this.chatArea.append(this.forum.qui()+"\n");
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    private void disconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            this.forum.quiter(this.id);
            disconnectButton.setEnabled(false);
            connectButton.setEnabled(true);
            sendButton.setEnabled(false);
            usersButton.setEnabled(false);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void ecrire(String msg) {
        try {
            this.chatArea.append(msg+"\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserImpl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new UserImpl().setVisible(true));
    }

    // Variables declaration - do not modify
    private javax.swing.JTextArea chatArea;
    private javax.swing.JButton connectButton;
    private javax.swing.JButton disconnectButton;
    private javax.swing.JTextField inputChat;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton sendButton;
    private javax.swing.JButton usersButton;
    public Forum forum = null; 	// reference to the forum remote object
    public int id; 		// identifier of the client allocated by the server
    // End of variables declaration
}
