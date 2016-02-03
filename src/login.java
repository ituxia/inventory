import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Stephan on 18/02/2015.
 */
public class login extends JFrame {
    private JButton logInButton;
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
public String idnum;


    public login(final Connection conn)
    {
        super("HITS Login");
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);



        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = textField1.getText();
                /*JOptionPane.showConfirmDialog(login.this,"Are you sure");*/
                char[] input = passwordField1.getPassword();
                String password = new String(input);
                idnum=id;

                int idnumfound = 0;

                try {

                    Statement st = conn.createStatement();
                    ResultSet res = st.executeQuery("SELECT * FROM users");


                    try {
                        while (res.next() && idnumfound == 0) {
                            int pasw = Integer.parseInt(password);
                            String msg = res.getString("Name");
                            int dbid = res.getInt("passw");

                            if (idnum.equals(msg)) {
                                idnumfound=1;
                                if (dbid == pasw) {

                                    mainpage mp= new mainpage(idnum,conn);
                                    mp.setVisible(true);
                                    dispose();

                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "Wrong Password", "Try Again", JOptionPane.ERROR_MESSAGE);
                                }


                            }
                        }
                        if (idnumfound==0){
                            JOptionPane.showMessageDialog(null, "Wrong Id" , "Try Again", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    catch (Exception lg){
                        lg.printStackTrace();

                        JOptionPane.showMessageDialog(null, "Invalid Login......" + lg.getMessage(), "Cant Log In", JOptionPane.ERROR_MESSAGE);
                    }

                }


                catch (Exception ae) {

                   ae.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Could not connect to server......Please Contact Admin .." +ae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                }


           }
        });

    }


}
