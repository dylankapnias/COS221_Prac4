package za.ac.up.cs.cos221;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.io.*;
import java.util.Vector;

/**
 * Hello world!
 *
 */
public class App
{
    public static JPanel createStaffTab() {
        JPanel control = new JPanel();
        control.setLayout(new BorderLayout());

        JPanel filter = new JPanel();
        filter.setLayout(new BorderLayout());

        JLabel fl = new JLabel("Filter: ");
        filter.add(fl, BorderLayout.WEST);

        JTextField tf = new JTextField(45);
        filter.add(tf, BorderLayout.CENTER);


        control.add(filter, BorderLayout.NORTH);

        Vector<String> c = new Vector<>();
        Vector<Vector<String>> d = new Vector<>();
        Vector<String> data = new Vector<>();

        c.add("First Name");
        c.add("Last Name");
        c.add("Address");
        c.add("Address 2");
        c.add("District");
        c.add("City");
        c.add("Postal Code");
        c.add("Phone");
        c.add("Store");
        c.add("Active");

        Connection conn = createConnection();
        if (conn == null) {
            System.out.println("Failed to connect to database!");
            System.exit(1);
        }

        String q = "SELECT s.first_name, s.last_name, a.address, a.address2, a.district, c.city, a.postal_code, a.phone, s.store_id, s.active " +
                   "FROM staff AS s JOIN address AS a " +
                   "ON s.address_id = a.address_id JOIN city AS c ON a.city_id = c.city_id";

        ResultSet res = queryResults(conn, q);
        try {
            while (res.next()) {
                data.add(res.getString("first_name"));
                data.add(res.getString("last_name"));
                data.add(res.getString("address"));
                data.add(res.getString("address2"));
                data.add(res.getString("district"));
                data.add(res.getString("city"));
                data.add(res.getString("postal_code"));
                data.add(res.getString("phone"));
                data.add(res.getString("store_id"));
                data.add(res.getString("active"));

                d.add(new Vector<String>(data));
                data.clear();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        DefaultTableModel m = new DefaultTableModel(d, c);

        JTable t = new JTable(m);
        t.setShowGrid(true);
        t.setShowVerticalLines(true);

        JScrollPane s = new JScrollPane(t);
        s.setBounds(10, 30, 40, 40);

        TableRowSorter<TableModel> sort = new TableRowSorter<>(t.getModel());
        t.setRowSorter(sort);
        tf.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String str = tf.getText();
                if (str.trim().length() == 0) {
                    sort.setRowFilter(null);
                } else {
                    sort.setRowFilter(RowFilter.regexFilter("(?i)" + str));
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String str = tf.getText();
                if (str.trim().length() == 0) {
                    sort.setRowFilter(null);
                } else {
                    sort.setRowFilter(RowFilter.regexFilter("(?i)" + str));
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

        control.add(s);

        return control;
    }

    public static JScrollPane createFilmTable() {
        Vector<String> c = new Vector<>();
        Vector<Vector<String>> d = new Vector<>();
        Vector<String> data = new Vector<>();

        c.add("Film ID");
        c.add("Title");
        c.add("Description");
        c.add("Year of Release");
        c.add("Language");
        c.add("Original Language");
        c.add("Rental Duration");
        c.add("Rental Price");
        c.add("Length");
        c.add("Cost of Replacement");
        c.add("Rating");
        c.add("Special Features");
        c.add("Last Updated");

        Connection conn = createConnection();
        if (conn == null) {
            System.out.println("Failed to connect to database!");
            System.exit(1);
        }

        String q = "SELECT * " +
                "FROM film ";

        ResultSet res = queryResults(conn, q);
        try {
            while (res.next()) {
                data.add(res.getString("film_id"));
                data.add(res.getString("title"));
                data.add(res.getString("description"));
                data.add(res.getString("release_year"));
                data.add(res.getString("language_id"));
                data.add(res.getString("original_language_id"));
                data.add(res.getString("rental_duration"));
                data.add(res.getString("rental_rate"));
                data.add(res.getString("length"));
                data.add(res.getString("replacement_cost"));
                data.add(res.getString("rating"));
                data.add(res.getString("special_features"));
                data.add(res.getString("last_update"));

                d.add(new Vector<String>(data));
                data.clear();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        DefaultTableModel m = new DefaultTableModel(d, c);

        JTable t = new JTable(m);
        t.setShowGrid(true);
        t.setShowVerticalLines(true);

        TableColumnModel columnModel = t.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(1);
        columnModel.getColumn(3).setPreferredWidth(1);
        columnModel.getColumn(4).setPreferredWidth(1);
        columnModel.getColumn(5).setPreferredWidth(1);
        columnModel.getColumn(6).setPreferredWidth(1);
        columnModel.getColumn(7).setPreferredWidth(1);
        columnModel.getColumn(8).setPreferredWidth(1);
        columnModel.getColumn(9).setPreferredWidth(1);
        columnModel.getColumn(10).setPreferredWidth(1);

        JScrollPane s = new JScrollPane(t);
        s.setBounds(10, 30, 40, 40);

        return s;
    }

    public static JPanel createFilmsTab() {
        Vector<String> c = new Vector<>();
        c.add("Insert Film Title");
        c.add("Insert Film Description");
        c.add("Insert Film Release Year");
        c.add("Insert Film Language");
        c.add("Insert The Original Film Language");
        c.add("Insert Film Rental Duration");
        c.add("Insert Film Rental Price");
        c.add("Insert Film Length");
        c.add("Insert Film Cost of Replacement");
        c.add("Insert Film Rating");
        c.add("Insert Film Special Features");

        JPanel control = new JPanel();
        control.setLayout(new BorderLayout());

        JPanel bSpace = new JPanel();
        bSpace.setLayout(new BorderLayout());

        JButton b = new JButton("Add Film");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel p = new JPanel();
                p.setLayout(new GridLayout(12, 2));

                JLabel labels[] = new JLabel[11];
                JTextField texts[] = new JTextField[11];

                for (int i = 0; i < 11; i++) {
                    labels[i] = new JLabel(c.get(i));
                    labels[i].setBounds(10, 10 * i, 35, 25);

                    texts[i] = new JTextField(20);
                    texts[i].setBounds(50, 10 * i, 20, 25);

                    p.add(labels[i]);
                    p.add(texts[i]);
                }
                JButton addButton = new JButton("Add");
                p.add(addButton);

                Popup pop = new PopupFactory().getPopup(control, p, 100, 100);

                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        StringBuilder qB = new StringBuilder(
                                "INSERT INTO film (title, description, release_year, language_id, " +
                                "original_language_id, rental_duration, rental_rate, length, replacement_cost," +
                                " rating, special_features) VALUES (");

                        for (int i = 0; i < 11; i++) {
                            if (texts[i].getText() != "") {
                                if ((i == 0 | i == 1 | i == 9 | i == 10) && texts[i].getText() != "") qB.append("\'" + texts[i].getText() + "\'");
                                else qB.append(texts[i].getText());
                            } else {
                                String s = null;
                                qB.append(s);
                            }
                            if (i != 10) qB.append(", ");
                        }
                        qB.append(");");

                        queryInsert(createConnection(), qB.toString().replace(", , ", ", NULL, "));

                        pop.hide();
                        // TODO: Add the refresh of the table
                    }
                });

                pop.show();
            }
        });
        bSpace.add(b, BorderLayout.CENTER);

        control.add(bSpace, BorderLayout.NORTH);

        control.add(createFilmTable());

        return control;
    }

    public static JPanel createInventoryTab() {
        JPanel control = new JPanel();
        control.setLayout(new BorderLayout());

        Vector<String> c = new Vector<>();
        Vector<Vector<String>> d = new Vector<>();
        Vector<String> data = new Vector<>();

        c.add("Store Name");
        c.add("Genre");
        c.add("Number of Movies");

        Connection conn = createConnection();
        if (conn == null) {
            System.out.println("Failed to connect to database!");
            System.exit(1);
        }

        String q = "SELECT v.store, e.Genre, e.Stock FROM (" +
                        "SELECT * FROM staff JOIN (" +
                            "SELECT i.store_id as sID, COUNT(i.inventory_id) AS Stock, v.category AS Genre FROM " +
                            "inventory AS i JOIN film_list as v ON i.film_id = v.FID GROUP BY v.category ORDER BY " +
                            "i.store_id) " +
                        "AS s ON staff.store_id = s.sID) " +
                    "AS e JOIN sales_by_store AS v ON v.manager LIKE CONCAT(e.first_name, ' ', e.last_name);";

        ResultSet res = queryResults(conn, q);
        try {
            while (res.next()) {
                data.add(res.getString("store"));
                data.add(res.getString("Genre"));
                data.add(res.getString("Stock"));

                d.add(new Vector<String>(data));
                data.clear();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        DefaultTableModel m = new DefaultTableModel(d, c);

        JTable t = new JTable(m);
        t.setShowGrid(true);
        t.setShowVerticalLines(true);

        JScrollPane s = new JScrollPane(t);
        s.setBounds(10, 30, 40, 40);

        control.add(s);

        return control;
    }

    public static JScrollPane createClientTable() {
        Vector<String> c = new Vector<>();
        Vector<Vector<String>> d = new Vector<>();
        Vector<String> data = new Vector<>();

        c.add("Client ID");
        c.add("Store ID");
        c.add("First Name");
        c.add("Last Name");
        c.add("Email");
        c.add("Address ID");
        c.add("Active");
        c.add("Date of Creation");
        c.add("Date of Update");

        Connection conn = createConnection();
        if (conn == null) {
            System.out.println("Failed to connect to database!");
            System.exit(1);
        }

        String q = "SELECT * " +
                "FROM customer ";

        ResultSet res = queryResults(conn, q);
        try {
            while (res.next()) {
                data.add(res.getString("customer_id"));
                data.add(res.getString("store_id"));
                data.add(res.getString("first_name"));
                data.add(res.getString("last_name"));
                data.add(res.getString("email"));
                data.add(res.getString("address_id"));
                data.add(res.getString("active"));
                data.add(res.getString("create_date"));
                data.add(res.getString("last_update"));

                d.add(new Vector<String>(data));
                data.clear();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        DefaultTableModel m = new DefaultTableModel(d, c);

        JTable t = new JTable(m);
        t.setShowGrid(true);
        t.setShowVerticalLines(true);

        JScrollPane s = new JScrollPane(t);
        s.setBounds(10, 30, 400, 400);

        return s;
    }

    public static JPanel createClientsTab() {
        Vector<String> c = new Vector<>();
        c.add("Insert Store ID");
        c.add("Insert Client First Name");
        c.add("Insert Client Last Name");
        c.add("Insert Client Email");
        c.add("Insert Client Address ID");
        c.add("Insert Client Active Setting");

        JPanel control = new JPanel();
        control.setLayout(new GridLayout(1, 4));

        JButton lB = new JButton("List");
        control.add(lB);
        lB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel p = new JPanel();
                //p.setLayout(new BorderLayout());
                p.setBounds(100, 100, 500, 500);

                p.add(createClientTable(), BorderLayout.CENTER);

                JButton cButton = new JButton("Close");
                p.add(cButton, BorderLayout.SOUTH);

                Popup pop = new PopupFactory().getPopup(control, p, 100, 100);

                cButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pop.hide();
                    }
                });

                pop.show();
            }
        });

        JButton aB = new JButton("Add");
        control.add(aB);
        aB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel p1 = new JPanel();
                p1.setLayout(new GridLayout(7, 2));

                JLabel labels[] = new JLabel[6];
                JTextField texts[] = new JTextField[6];

                for (int i = 0; i < 6; i++) {
                    labels[i] = new JLabel(c.get(i));
                    labels[i].setBounds(10, 10 * i, 35, 25);

                    texts[i] = new JTextField(20);
                    texts[i].setBounds(50, 10 * i, 20, 25);

                    p1.add(labels[i]);
                    p1.add(texts[i]);
                }
                JButton addButton = new JButton("Add");
                p1.add(addButton);

                Popup pop = new PopupFactory().getPopup(control, p1, 100, 100);
                pop.show();

                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        StringBuilder qB = new StringBuilder(
                                "INSERT INTO customer (store_id, first_name, last_name, email, " +
                                        "address_id, active, create_date) VALUES (");

                        for (int i = 0; i < 6; i++) {
                            if (texts[i].getText() != "") {
                                if ((i ==  1 | i == 2 | i == 3 | i == 4) && texts[i].getText() != "")
                                    qB.append("\'" + texts[i].getText() + "\'");
                                else qB.append(texts[i].getText());
                            } else {
                                String s = null;
                                qB.append(s);
                            }
                            qB.append(", ");
                        }
                        qB.append("SYSDATE()");
                        qB.append(");");

                        queryInsert(createConnection(), qB.toString().replace(", , ", ", NULL, "));

                        pop.hide();
                        // TODO: Add the refresh of the table
                    }
                });
            }
        });

        JButton uB = new JButton("Update");
        control.add(uB);
        uB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel p1 = new JPanel();
                p1.setLayout(new GridLayout(8, 2));

                JLabel jL = new JLabel("Customer ID to Update");
                jL.setBounds(10, 10, 35, 25);
                JTextField jT = new JTextField(20);
                jT.setBounds(50, 10, 20, 25);
                p1.add(jL);
                p1.add(jT);

                JLabel labels[] = new JLabel[6];
                JTextField texts[] = new JTextField[6];

                for (int i = 0; i < 6; i++) {
                    labels[i] = new JLabel(c.get(i));
                    labels[i].setBounds(10, 10 * i + 1, 35, 25);

                    texts[i] = new JTextField(20);
                    texts[i].setBounds(50, 10 * i + 1, 20, 25);

                    p1.add(labels[i]);
                    p1.add(texts[i]);
                }
                JButton addButton = new JButton("Add");
                p1.add(addButton);

                Popup pop = new PopupFactory().getPopup(control, p1, 100, 100);
                pop.show();

                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        StringBuilder qB = new StringBuilder(
                                "INSERT INTO customer (store_id, first_name, last_name, email, " +
                                        "address_id, active, create_date) VALUES (");

                        for (int i = 0; i < 6; i++) {
                            if (texts[i].getText() != "") {
                                if ((i ==  1 | i == 2 | i == 3 | i == 4) && texts[i].getText() != "")
                                    qB.append("\'" + texts[i].getText() + "\'");
                                else qB.append(texts[i].getText());
                            } else {
                                String s = null;
                                qB.append(s);
                            }
                            qB.append(", ");
                        }
                        qB.append("SYSDATE()");
                        qB.append(");");

                        queryInsert(createConnection(), qB.toString().replace(", , ", ", NULL, "));

                        pop.hide();
                        // TODO: Add the refresh of the table
                    }
                });
            }
        });

        JButton dB = new JButton("Delete");
        control.add(dB);

        return control;
    }
    public static JFrame createGUI() {
        JFrame mainFrame = new JFrame();

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        mainPanel.setLayout(new GridLayout(1, 1));

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Staff", createStaffTab());
        tabs.addTab("Films", createFilmsTab());
        tabs.addTab("Inventory", createInventoryTab());
        tabs.addTab("Clients", createClientsTab());

        mainPanel.add(tabs);

        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
        //mainFrame.pack();
        mainFrame.setSize(2000, 600);
        mainFrame.setTitle("COS221_Prac4");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return mainFrame;
    }

    public static Connection createConnection() {
        String connString = new StringBuilder()
        .append("jdbc:")
        .append(System.getenv("SAKILA_DB_PROTO") + "://")
        .append(System.getenv("SAKILA_DB_HOST") + ":")
        .append(System.getenv("SAKILA_DB_PORT") + "/")
        .append(System.getenv("SAKILA_DB_NAME") + "?")
        .append("user=" + System.getenv("SAKILA_DB_USERNAME") + "&")
        .append("password=" + System.getenv("SAKILA_DB_PASSWORD"))
        .toString();

        try {
            return DriverManager.getConnection(connString);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet queryResults(Connection c, String q) {
        try {
            Statement stmt = c.createStatement();
            return stmt.executeQuery(q);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void queryInsert(Connection c, String q) {
        try {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main( String[] args )
    {
        JFrame gui = createGUI();
    }
}
