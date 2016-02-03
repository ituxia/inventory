

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {

    public static void main(String[] args) {
Connection conn = null;



        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "hits_barry";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "barryadmin";
        String password = "123456";

                try { Class.forName(driver).newInstance();
             conn = DriverManager.getConnection(url + dbName, userName, password);

            login loginpage= new login(conn);}

                catch (Exception e)
                { e.printStackTrace();
                System.out.println("Fail");
                    JFrame errframe = new JFrame();
                    JLabel errmsg= new JLabel("Could not connect to server......Please Contact Admin");
                    errframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    errframe.add(errmsg);
                    errframe.setVisible(true);
                    errframe.setPreferredSize(new Dimension(400, 200));
                    errframe.pack();
                }

    }


    }

