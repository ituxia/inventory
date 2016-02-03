import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.*;
/**
 * Created by Stephan on 22/02/2015.
 */
public class mainpage extends JFrame {

    private JPanel mainpage;
    private JPanel button;
    private JPanel content;
    private JButton purchaseButton;
    private JButton inventoryButton;
    private JButton reportButton;
    private JButton customerButton;
    private JPanel purchase;
    private JPanel inventory;
    private JPanel customer;
    private JPanel report;
    private JButton addToReceiptButton;
    private JList receiptlist;
    private JButton printButton;
    private JLabel userlabel;
    private JButton montlySalesButton;
    private JLabel datelb;
    private JTabbedPane invtabpan;
    private JTabbedPane custtabpan;
    private JButton deleteButton;
    private JButton saveButton;
    private JTextField invtemnumsearchfield;
    private JTextField invnamesearchfield;
    private JTextField invdescsearchfield;
    private JButton addNewItemToButton;
    private JTextField invaddnam;
    private JTextField invadditnum;
    private JTextField invadddes;
    private JPanel addpan;
    private JPanel searchpan;
    private JTextField custaddfn;
    private JTextField custaddln;
    private JTextField custaddadd;
    private JTextField custaddemail;
    private JTextField custaddtele;
    private JButton addThisCustomerToButton;
    private JTable custseartable;
    private JTextField custfnsearchfield;
    private JTextField custlnsearchfield;
    private JTextField custaddsearchfield;
    private JTextField custemailsearchfield;
    private JTextField custtelesearchfield;
    private JButton deleteButton1;
    private JButton saveButton1;
    private JSpinner invaddcost;
    private JSpinner invaddquan;
    private JSpinner invspincostsearchfield;
    private JSpinner invspinquansearchfield;
    private JLabel invname;
    private JLabel invitem;
    private JLabel invquan;
    private JLabel invdesc;
    private JLabel invcost;
    private JLabel invadnamelb;
    private JLabel invadditemlb;
    private JLabel invaddquanlb;
    private JLabel invadddeslb;
    private JLabel invaddcostlb;
    private JComboBox itemnamecombo;
    private JComboBox numcombo;
    private JSpinner posquan;
    PreparedStatement custselect;
    PreparedStatement invselect;

    private JPanel imagepan;
    private JComboBox custnamecombo;
    private JTable invsearchtable;
    private JScrollPane scrpan;
    private JButton refreshButton;
    private JButton refreshButton1;
    private JScrollPane custscrpan;
    private JTextPane barrySHardwareItemTextPane;
    private JScrollPane recpan;
    private JLabel custlab;
    private JLabel totallb;
    private JPanel repbutn;
    private JPanel contentchart;
    private JButton todaySalesButton;
    private JButton monthlySalesButton;
    private JPanel mainchartpage;
    private JPanel barchartpan;
    private JPanel piechartpan;
    private JLabel crtlb;
    private JPanel chartcard;
    private JPanel image;
    private JTable searchinvTable;
    DefaultComboBoxModel list = new DefaultComboBoxModel();
    DefaultComboBoxModel itemnumlist = new DefaultComboBoxModel();
    DefaultComboBoxModel namelist = new DefaultComboBoxModel();
    DefaultListModel listModel = new DefaultListModel();
    Statement st;
    String itemnum = null;
    String des = null;
    int cost = 0;
    int quan = 0;
    String name = null;
    int val;
    Object custid2;
    String f_name = null;
    String l_name = null;
    String add = null;
    String email = null;
    String tele = null;
    int custid = 0;
Connection conn2;
   int firstitemadd=0;
    int lastquant=0;

int sum=0;
    String[] invcolumnNames = {"Item Number",
            "Name",
            "Description",
            "Cost",
            "Quantity"};

    String[] cuscolumnNames = {"Customer ID",
            "First Name",
            "Last Name",
            "Address",
            "Email",
            "Telephone"};




    public mainpage(final String idnum, final Connection conn) {


        super("HITS Login");
        setContentPane(mainpage);
conn2=conn;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        mainpage.setPreferredSize(new Dimension(1000, 500));
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        DateFormat dte = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calobj = Calendar.getInstance();
        java.net.URL logoOneUrl = getClass().getResource("hits1.png");
        ImageIcon image =new ImageIcon(logoOneUrl);
        JLabel label = new JLabel("", image, JLabel.CENTER);
        imagepan.add(label, BorderLayout.CENTER);
        final String[] sdate = {dte.format(calobj.getTime())};
        userlabel.setText("User: " + idnum);
        datelb.setText("Date: " + sdate[0]);
        invtemnumsearchfield.setEditable(false);
        if(!idnum.equals("admin"))
        {

            inventoryButton.setEnabled(false);
            reportButton.setEnabled(false);
            customerButton.setEnabled(false);
        }






//Customer name  combo box
createcuscombobox();
//item name combo box
  createitemcombo();
//item number combo box
createitemnumcombo();

// Customer Search

        DefaultTableModel model2 = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        model2.setColumnIdentifiers(cuscolumnNames);
        custseartable.setModel(model2);

        try {
            custselect = conn.prepareStatement("select * from customer");
            ResultSet rs = custselect.executeQuery();

            while (rs.next()) {
                f_name = rs.getString("f_name");
                l_name = rs.getString("l_name");
                add = rs.getString("addr");
                email = rs.getString("email");
                tele = rs.getString("tele");
                custid = rs.getInt("custid");
                model2.addRow(new Object[]{custid, f_name, l_name, add, email, tele});

            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "222Error", JOptionPane.ERROR_MESSAGE);
        }


// Inventory Search

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        model.setColumnIdentifiers(invcolumnNames);
        invsearchtable.setModel(model);

        try {
            invselect = conn.prepareStatement("select * from inventory");
            ResultSet rs = invselect.executeQuery();

            while (rs.next()) {
                itemnum = rs.getString("itemnum");
                des = rs.getString("descrip");
                cost = rs.getInt("cost");
                quan = rs.getInt("quantity");
                name = rs.getString("name");
                model.addRow(new Object[]{itemnum, name, des, cost, quan});

            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


//Inventory Add Tab


        addNewItemToButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    itemnum = invadditnum.getText();
                    des = invadddes.getText();
                    cost = (Integer) invaddcost.getValue();
                    quan = (Integer) invaddquan.getValue();
                    name = invaddnam.getText();

                    String sqlst = "INSERT INTO inventory  VALUES(?,?, ?,?,?)";
                    PreparedStatement ps = conn.prepareStatement(sqlst);
                    ps.setString(1, itemnum);
                    ps.setString(2, des);
                    ps.setInt(3, cost);
                    ps.setInt(4, quan);
                    ps.setString(5, name);
                    val = ps.executeUpdate();
                    fetchcat();
                    if (val == 1) {

                        JOptionPane.showMessageDialog(null, "Record added to system go to \" Search\" and press \"Refresh\" to see record.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        invadditnum.setText("");
                        invadddes.setText("");
                        invaddcost.setValue(0);
                        invaddquan.setValue(0);
                        invaddnam.setText("");
                    }

                } catch (Exception inerr) {
                    JOptionPane.showMessageDialog(null, "Could not insert record........" + inerr.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    inerr.printStackTrace();
                }


            }
        });


        //Customer Add Tab
        addThisCustomerToButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    f_name = custaddfn.getText();
                    l_name = custaddln.getText();
                    add = custaddadd.getText();
                    email = custaddemail.getText();
                    tele = custaddtele.getText();


                    String sqlst = "INSERT INTO customer(f_name, l_name, addr, email, tele)  VALUES(?,?,?,?,?)";
                    PreparedStatement ps = conn.prepareStatement(sqlst);
                    ps.setString(1, f_name);
                    ps.setString(2, l_name);
                    ps.setString(3, add);
                    ps.setString(4, email);
                    ps.setString(5, tele);


                    val = ps.executeUpdate();
                    if (val == 1) {
                                               JOptionPane.showMessageDialog(null, "Record added to system go to \" Search\" and press \"Refresh\" to see record.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        custaddfn.setText("");
                        custaddln.setText("");
                        custaddadd.setText("");
                        custaddemail.setText("");
                        custaddtele.setText("");
                        fetchcat();

                    }


                } catch (Exception inerr) {
                    JOptionPane.showMessageDialog(null, "Could Not Insert Record........" + inerr.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    inerr.printStackTrace();
                }


            }
        });

//purchase tab button
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                content.removeAll();
                content.add(purchase, "card1");
                content.repaint();
                content.revalidate();

            }
        });

        //inventory tab button
        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                content.removeAll();
                content.add(inventory, "card2");
                content.repaint();
                content.revalidate();

            }
        });

        //customer tab button
        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                content.removeAll();
                content.add(customer, "card3");
                content.repaint();
                content.revalidate();

            }
        });
//report tab button
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                content.removeAll();
                content.add(report, "card4");
                content.repaint();
                content.revalidate();
            }
        });

//refresh database table on screen for inventory
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        //all cells false
                        return false;
                    }
                };
                model.setColumnIdentifiers(invcolumnNames);
                invsearchtable.setModel(model);

                try {
                    invselect = conn.prepareStatement("select * from inventory");
                    ResultSet rs = invselect.executeQuery();

                    while (rs.next()) {
                        itemnum = rs.getString("itemnum");
                        des = rs.getString("descrip");
                        cost = rs.getInt("cost");
                        quan = rs.getInt("quantity");
                        name = rs.getString("name");
                        model.addRow(new Object[]{itemnum, name, des, cost, quan});

                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Could not refresh table.........." + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //refresh database table on screen for customer
        refreshButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model2 = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        //all cells false
                        return false;
                    }
                };
                model2.setColumnIdentifiers(cuscolumnNames);
                custseartable.setModel(model2);

                try {
                    custselect = conn.prepareStatement("select * from customer");
                    ResultSet rs = custselect.executeQuery();

                    while (rs.next()) {
                        f_name = rs.getString("f_name");
                        l_name = rs.getString("l_name");
                        add = rs.getString("addr");
                        email = rs.getString("email");
                        tele = rs.getString("tele");
                        custid = rs.getInt("custid");
                        model2.addRow(new Object[]{custid, f_name, l_name, add, email, tele});

                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "222Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        //update save for inventory
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (invsearchtable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Select the item  you wish to save changes for.", "Item not Selected", JOptionPane.ERROR_MESSAGE);

                }
                try {

                    itemnum = invtemnumsearchfield.getText();
                    des = invdescsearchfield.getText();
                    cost = (Integer) invspincostsearchfield.getValue();
                    quan = (Integer) invspinquansearchfield.getValue();
                    name = invnamesearchfield.getText();


                    String sqlst = "UPDATE inventory SET itemnum=?,descrip=?,cost=?,quantity=?, name=? where itemnum = ?";
                    PreparedStatement ps = conn.prepareStatement(sqlst);
                    ps.setString(1, itemnum);
                    ps.setString(2, des);
                    ps.setInt(3, cost);
                    ps.setInt(4, quan);
                    ps.setString(5, name);
                    ps.setString(6, itemnum);
                    val = ps.executeUpdate();
                    fetchcat();
                    if (val == 1) {

                        JOptionPane.showMessageDialog(null, "Record successfully updated, press \"Refresh\" ", "Success", JOptionPane.INFORMATION_MESSAGE);


                    }


                } catch (Exception inerr) {
                    JOptionPane.showMessageDialog(null, "Could Not Update Record........" + inerr.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    inerr.printStackTrace();
                }

            }
        });
        //update for customer
        saveButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (custseartable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Select the item  you wish to save changes for.", "Item not Selected", JOptionPane.ERROR_MESSAGE);

                }

                try {
                    custid = (Integer) custseartable.getValueAt(custseartable.getSelectedRow(), 0);
                    f_name = custfnsearchfield.getText();
                    l_name = custlnsearchfield.getText();
                    add = custaddsearchfield.getText();
                    email = custemailsearchfield.getText();
                    tele = custtelesearchfield.getText();
                    String sqlst = "UPDATE customer SET f_name=?,l_name=?,addr=?,email=?,tele=? where custid = ?";
                    PreparedStatement ps = conn.prepareStatement(sqlst);
                    ps.setString(1, f_name);
                    ps.setString(2, l_name);
                    ps.setString(3, add);
                    ps.setString(4, email);
                    ps.setString(5, tele);
                    ps.setInt(6, custid);

                    val = ps.executeUpdate();
                    if (val == 1) {

                        JOptionPane.showMessageDialog(null, "Record successfully updated, press \"Refresh\" ", "Success", JOptionPane.INFORMATION_MESSAGE);
                        fetchcat();

                    }
                    if (val != 1) {
                        JOptionPane.showMessageDialog(null, "Record already deleted, press \"Refresh\" ", "Hold Up", JOptionPane.INFORMATION_MESSAGE);

                    }


                } catch (Exception inerr) {
                    JOptionPane.showMessageDialog(null, "Could Not Update Record........" + inerr.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    inerr.printStackTrace();
                }


            }
        });

//get selected row for inventory
        invsearchtable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultTableModel model = (DefaultTableModel) invsearchtable.getModel();

                invnamesearchfield.setText(model.getValueAt(invsearchtable.getSelectedRow(), 1).toString());
                invtemnumsearchfield.setText(model.getValueAt(invsearchtable.getSelectedRow(), 0).toString());
                invspinquansearchfield.setValue(model.getValueAt(invsearchtable.getSelectedRow(), 4));
                invdescsearchfield.setText(model.getValueAt(invsearchtable.getSelectedRow(), 2).toString());
                invspincostsearchfield.setValue(model.getValueAt(invsearchtable.getSelectedRow(), 3));
            }
        });

//get selected row for inventory
        custseartable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultTableModel model = (DefaultTableModel) custseartable.getModel();


                custfnsearchfield.setText(model.getValueAt(custseartable.getSelectedRow(), 1).toString());
                custlnsearchfield.setText(model.getValueAt(custseartable.getSelectedRow(), 2).toString());
                custaddsearchfield.setText(model.getValueAt(custseartable.getSelectedRow(), 3).toString());
                custemailsearchfield.setText(model.getValueAt(custseartable.getSelectedRow(), 4).toString());
                custtelesearchfield.setText(model.getValueAt(custseartable.getSelectedRow(), 5).toString());

            }
        });


//delete inventory item
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (invsearchtable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Select the item you wish to delete.", "Item not Selected", JOptionPane.ERROR_MESSAGE);

                } else

                    try {

                        itemnum = invtemnumsearchfield.getText();
                        String sqlst = "DELETE from inventory where itemnum = ?";
                        PreparedStatement ps = conn.prepareStatement(sqlst);
                        ps.setString(1, itemnum);
                        val = ps.executeUpdate();
                        fetchcat();
                        if (val == 1) {

                            JOptionPane.showMessageDialog(null, "Record successfully deleted, press \"Refresh\" ", "Success", JOptionPane.INFORMATION_MESSAGE);


                        }
                        if (val != 1) {
                            JOptionPane.showMessageDialog(null, "Record already deleted, press \"Refresh\" ", "Hold Up", JOptionPane.INFORMATION_MESSAGE);

                        }


                    } catch (Exception inerr) {
                        JOptionPane.showMessageDialog(null, "Could Not Delete Record........" + inerr.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        inerr.printStackTrace();
                    }

            }
        });

     //   delete customer item
        deleteButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (custseartable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Select the item  you wish to delete.", "Item not Selected", JOptionPane.ERROR_MESSAGE);

                } else

                    try {
                        custid = (Integer) custseartable.getValueAt(custseartable.getSelectedRow(), 0);

                        String sqlst = "DELETE from customer where custid = ?";
                        PreparedStatement ps = conn.prepareStatement(sqlst);
                        ps.setInt(1, custid);
                        val = ps.executeUpdate();
                        if (val == 1) {

                            JOptionPane.showMessageDialog(null, "Record successfully deleted, press \"Refresh\" ", "Success", JOptionPane.INFORMATION_MESSAGE);

                            fetchcat();
                        }
                        if (val != 1) {
                            JOptionPane.showMessageDialog(null, "Record already deleted, press \"Refresh\" ", "Hold Up", JOptionPane.INFORMATION_MESSAGE);

                        }


                    } catch (Exception inerr) {
                        JOptionPane.showMessageDialog(null, "Could Not Delete Record........" + inerr.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        inerr.printStackTrace();
                    }

            }
        });

//change item number combo box based of name selected for point of sale item name
        itemnamecombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String selecteditemcombo =(String) itemnamecombo.getSelectedItem();

                try {


                    String invsql = "SELECT itemnum FROM inventory WHERE name=?";
                    PreparedStatement ps = conn.prepareStatement(invsql);
                    ps.setString(1, selecteditemcombo);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {

                        String c = rs.getString("itemnum");


                        numcombo.setSelectedItem(c);

                    }



                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, err, "Hold Up", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });



        //change item name combo box based of item number selected for point of sale
        numcombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String selectednum =(String) numcombo.getSelectedItem();

                try {


                    String invsql = "SELECT name FROM inventory WHERE itemnum=?";
                    PreparedStatement ps = conn.prepareStatement(invsql);
                    ps.setString(1, selectednum);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {

                        String c = rs.getString("name");


                        itemnamecombo.setSelectedItem(c);

                    }



                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, err, "Hold Up", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //Add item to receipt queue
        addToReceiptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int v;
                String selectednum =(String) numcombo.getSelectedItem();

                v = (Integer) posquan.getValue();

                if (v <= 0){
                    JOptionPane.showMessageDialog(null, "Please Increase the Quantity to a value greater than 0", "OOPS", JOptionPane.ERROR_MESSAGE);

            }
                else
                    try {

                        Statement s = conn.createStatement();
                        String invsql = "SELECT itemnum, quantity,cost,name FROM inventory WHERE itemnum=?";
                        PreparedStatement ps = conn.prepareStatement(invsql);
                        ps.setString(1, selectednum);
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {

                            lastquant = rs.getInt("quantity");
                            String a = rs.getString("itemnum");
                            String b = rs.getString("name");
                            int ct = rs.getInt("cost");

                            if (lastquant < v) {
                                JOptionPane.showMessageDialog(null, "Only " + lastquant + " of this item is in storage, reduce order amount", "OOPS", JOptionPane.ERROR_MESSAGE);

                            } else {
                                /*JOptionPane.showMessageDialog(null, "Item added to Receipt", "Added", JOptionPane.INFORMATION_MESSAGE);*/

                                addtorep(v, a, b, ct);
                                sum=sum+(ct*v);
                                totallb.setText("Total: $"+sum);
                                if (firstitemadd == 0) {
                                    custnamecombo.setEnabled(false);
                                    firstitemadd = 1;
                                    custlab.setText("Customer: " + custnamecombo.getSelectedItem());

                                }


                            }
                        }



                    } catch (Exception err) {
                        JOptionPane.showMessageDialog(null, err, "Hold Up", JOptionPane.INFORMATION_MESSAGE);
                    }

            }
        });
//delete item that is double clicked
        receiptlist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int h;
               h=  receiptlist.getSelectedIndex();
                if (e.getClickCount() == 2) {
                    String  elemens;
                    elemens=(String )listModel.getElementAt(h);
                    String[] parts = elemens.split("                   ");
                    quan = Integer.parseInt(parts[2]);
                    cost= Integer.parseInt(parts[3]);
                listModel.removeElementAt(h);
                sum=sum-(quan*cost);

                totallb.setText("Total: $"+sum);}

            }
        });

        //update database with selected items and print
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
firstitemadd=0;
                custlab.setText("Customer: ");
                custnamecombo.setEnabled(true);
                int listsize;
                int reqquan = 0;
                listsize=listModel.getSize();
                String [] parts1 =new String[listsize];
                for (int i=0; i<listsize;i++){
                    String  elemens;
                    elemens=(String )listModel.getElementAt(i);
                    String[] parts = elemens.split("                   ");
                    parts1[i]=parts[i];
                     reqquan = Integer.parseInt(parts[2]);
                    int newquant;
                    newquant=lastquant-reqquan;
                    try {
                        String sqlst = "UPDATE inventory SET quantity=? where itemnum = ?";
                        PreparedStatement ps = conn.prepareStatement(sqlst);
                        ps.setInt(1, newquant);
                        ps.setString(2, parts[0]);
                        val = ps.executeUpdate();


                    } catch (Exception inerr) {
                        JOptionPane.showMessageDialog(null, "Could Not Update Record........" + inerr.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        inerr.printStackTrace();
                    }

                }

                try {
                    String sqlst1 = "Select custid from customer where f_name=? and l_name=?";
                    PreparedStatement ps1 = conn.prepareStatement(sqlst1);
                    String names=  (String) custnamecombo.getSelectedItem();
                    String[] nameparts=names.split(" ");
                    ps1.setString(1,nameparts[0]);
                    ps1.setString(2, nameparts[1]);
                    ResultSet rs1 = ps1.executeQuery();
                    int custidnm=0;
                    while (rs1.next()) {

                        custidnm = rs1.getInt("custid");


                    }

                       String sqlst = "INSERT INTO purchases(custid, itemid, quantity, transdte, user) VALUES(?,?, ?,?,?)";
                        PreparedStatement ps = conn.prepareStatement(sqlst);
                        ps.setInt(1, custidnm);
                        ps.setString(2, parts1[0]);
                        ps.setInt(3, reqquan);
                        ps.setString(4,sdate[0]);
                        ps.setString(5, idnum);
                        val = ps.executeUpdate();
                }

                catch (Exception err)
                {

                }
                listModel.clear();
                totallb.setText("Total: $0.00");

            }
        });

        //monthly report card

        monthlySalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultCategoryDataset barchartdata= new DefaultCategoryDataset();
                barchartdata.setValue(400000,"Cow", "January");
                barchartdata.setValue(3000,"Hammer", "February");
                barchartdata.setValue(13,"Nail", "March");
                barchartdata.setValue(4435,"Window Pane", "April");


                JFreeChart barchart = ChartFactory.createBarChart("Monthly Item Sales","Month","Amount",barchartdata, PlotOrientation.VERTICAL,true,true,false);
                CategoryPlot barchrt= barchart.getCategoryPlot();
                barchrt.setRangeGridlinePaint(Color.ORANGE);
                ChartPanel barpanel =new ChartPanel(barchart);
                contentchart.removeAll();
                contentchart.add(barpanel);
                contentchart.validate();
            }
        });
        //generate todays sales chart
        todaySalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultPieDataset piedata= new DefaultPieDataset();
                piedata.setValue("Cow",200);
                piedata.setValue("Hammer",400);
                piedata.setValue("Nail",200);
                piedata.setValue("Window",100);


                JFreeChart piechart = ChartFactory.createPieChart("Today Item Sales", (PieDataset) piedata,  true, true,false);
                PiePlot barchrt= (PiePlot) piechart.getPlot();
                ChartPanel barpanel =new ChartPanel(piechart);
                contentchart.removeAll();
                contentchart.add(barpanel);
                contentchart.validate();
            }
        });
    }

    //update combobox if changes are made to system
    public void fetchcat (){
        list.removeAllElements();
        itemnumlist.removeAllElements();
        namelist.removeAllElements();
        try{

            Statement  s = conn2.createStatement();
            s.executeQuery("SELECT * FROM inventory ORDER BY name");

            ResultSet rs = s.getResultSet();
            while(rs.next()){


                String c = rs.getString("itemnum");
                itemnumlist.addElement(c);
            }
        }catch(Exception err){
            JOptionPane.showMessageDialog(null, "System error ..report to admin" + err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } numcombo.setModel(itemnumlist);
        try{

            Statement  s = conn2.createStatement();
            s.executeQuery("SELECT * FROM inventory ORDER BY name");

            ResultSet rs = s.getResultSet();
            while(rs.next()){


                String c = rs.getString("name");
                list.addElement(c);

            }

        }catch(Exception err){
            JOptionPane.showMessageDialog(null, "System error ..report to admin" + err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        itemnamecombo.setModel(list);
        try{
            // assume that all objects were all properly defined
            Statement  s = conn2.createStatement();
            s.executeQuery("SELECT * FROM customer ORDER BY l_name");

            ResultSet rs = s.getResultSet();
            while(rs.next()){


                String d = rs.getString("l_name");
                String c = rs.getString("f_name");
                namelist.addElement(c+" "+d);
            }
        }catch(Exception err){
            JOptionPane.showMessageDialog(null, err, "Hold Up", JOptionPane.INFORMATION_MESSAGE);
        }
        custnamecombo.setModel(namelist);

    }

//add item to receipt
public void addtorep(int c, String itemnum,String b, int e){
      listModel.addElement(itemnum+"                   "+b+"                   "+c+"                   "+e);
      receiptlist.setModel(listModel);

}
    //create customer name combo box for point of sale page
public void createcuscombobox(){
    try{
        // assume that all objects were all properly defined
        Statement  s = conn2.createStatement();
        s.executeQuery("SELECT * FROM customer ORDER BY l_name");

        ResultSet rs = s.getResultSet();
        while(rs.next()){


            String d = rs.getString("l_name");
            String c = rs.getString("f_name");
            namelist.addElement(c+" "+d);
        }
    }catch(Exception err){
        JOptionPane.showMessageDialog(null, err, "Hold Up", JOptionPane.INFORMATION_MESSAGE);
    }
    custnamecombo.setModel(namelist);
}
//create item combo box for point of sale page
    public void createitemcombo(){
        try{

            Statement  s = conn2.createStatement();
            s.executeQuery("SELECT * FROM inventory ORDER BY name");

            ResultSet rs = s.getResultSet();
            while(rs.next()){


                String c = rs.getString("name");
                list.addElement(c);
            }
        }catch(Exception err){
            JOptionPane.showMessageDialog(null, err, "Hold Up", JOptionPane.INFORMATION_MESSAGE);
        }
        itemnamecombo.setModel(list);
    }
public void createitemnumcombo(){
    try{
        // assume that all objects were all properly defined
        Statement  s = conn2.createStatement();
        s.executeQuery("SELECT * FROM inventory ORDER BY itemnum");

        ResultSet rs = s.getResultSet();
        while(rs.next()){


            String c = rs.getString("itemnum");
            itemnumlist.addElement(c);
        }
    }catch(Exception err){
        JOptionPane.showMessageDialog(null, err, "Hold Up", JOptionPane.INFORMATION_MESSAGE);
    }
    numcombo.setModel(itemnumlist);
}
}
