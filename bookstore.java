import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class javaproject {

	private JFrame frame;
	private JTextField txtbname;
	private JTextField txtedition;
	private JTextField txtprice;
	private JTable table;
	private JTextField txtbid;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					javaproject window = new javaproject();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	
	public void connection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject","root","771975"); 
		}
			
		catch(ClassNotFoundException ex) {
			
		}
		catch(SQLException ex) {
			
		}

	}
	public void table_load() {
		try {
			pst = con.prepareStatement("select * from BOOKS");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public javaproject() {
		initialize();
		connection();
		table_load();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1280, 678);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("BOOK STORE");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(486, 11, 222, 75);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(51, 80, 478, 371);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("BOOK NAME");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(29, 65, 136, 57);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("EDITION");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_2.setBounds(29, 150, 136, 31);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("PRICE");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_3.setBounds(29, 213, 136, 47);
		panel.add(lblNewLabel_3);
		
		txtbname = new JTextField();
		txtbname.setBounds(175, 82, 247, 31);
		panel.add(txtbname);
		txtbname.setColumns(10);
		
		txtedition = new JTextField();
		txtedition.setColumns(10);
		txtedition.setBounds(175, 150, 247, 31);
		panel.add(txtedition);
		
		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(175, 216, 247, 31);
		panel.add(txtprice);
		
		JButton btnNewButton = new JButton("SAVE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bname,edition,price;
				bname=txtbname.getText();
				edition=txtedition.getText();
				price = txtprice.getText();
				
			try {
			 pst=con.prepareStatement("insert into BOOKS(NAME,EDITION,PRICE) VALUES(?,?,?)");
			 pst.setString(1, bname);
			 pst.setString(2, edition);
			 pst.setString(3, price);
			 pst.executeUpdate();
			 JOptionPane.showMessageDialog(null,"RECORD ADDEDD!!!");
			 table_load();
			 txtbname.setText("");
			 txtedition.setText("");
			 txtprice.setText("");
			 txtbname.requestFocus();
			}
			catch(SQLException el) {
				
			}
		
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(29, 271, 136, 57);
		panel.add(btnNewButton);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnExit.setBounds(181, 271, 136, 57);
		panel.add(btnExit);
		
		JButton btnNewButton_1_1 = new JButton("CLEAR");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtbname.setText("");
				 txtedition.setText("");
				 txtprice.setText("");
				 txtbname.requestFocus();
				
			}
		});
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_1_1.setBounds(332, 271, 136, 57);
		panel.add(btnNewButton_1_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(562, 87, 607, 363);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 462, 523, 81);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String id = txtbid.getText();
				try {
					pst= con.prepareStatement("select NAME, EDITION,PRICE FROM BOOKS WHERE ID =?");
					pst.setString(1, id);
					rs = pst.executeQuery();
					if(rs.next()==true) {
						String name = rs.getString(1);
						String edition = rs.getString(2);
						String price = rs.getString(3);
						
						txtbname.setText(name);
						txtedition.setText(edition);
						txtprice.setText(price);
					}
					else {
						txtbname.setText("");
						txtedition.setText("");
						txtprice.setText("");
					}
				} catch (SQLException e1) {
				
					e1.printStackTrace();
				}
		
			}
		});
		txtbid.setColumns(10);
		txtbid.setBounds(150, 25, 265, 31);
		panel_1.add(txtbid);
		
		JLabel lblNewLabel_2_1 = new JLabel("BOOK-ID");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_2_1.setBounds(10, 25, 136, 31);
		panel_1.add(lblNewLabel_2_1);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bname,edition,price,bid;
				bname=txtbname.getText();
				edition=txtedition.getText();
				price = txtprice.getText();
				bid=txtbid.getText();
				
			try {
			 pst=con.prepareStatement("update BOOKS set NAME =?,EDITION=?,PRICE=? WHERE ID =?");
			 pst.setString(1, bname);
			 pst.setString(2, edition);
			 pst.setString(3, price);
			 pst.setString(4, bid);
			 pst.executeUpdate();
			 JOptionPane.showMessageDialog(null,"RECORD UPDATED!!!");
			 table_load();
			 txtbname.setText("");
			 txtedition.setText("");
			 txtprice.setText("");
			 txtbname.requestFocus();
			}
			catch(SQLException el) {
				
			} 
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUpdate.setBounds(590, 477, 136, 57);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnNewButton_1_2 = new JButton("DELETE");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String bid;
				
				bid=txtbid.getText();
				
			try {
			 pst=con.prepareStatement("DELETE FROM BOOKS WHERE ID =?");
			 pst.setString(1, bid);
			 pst.executeUpdate();
			 JOptionPane.showMessageDialog(null,"RECORD DELETED!!!");
			 table_load();
			 txtbname.setText("");
			 txtedition.setText("");
			 txtprice.setText("");
			 txtbname.requestFocus();
			}
			catch(SQLException el) {
			} 	
			}
		});
		btnNewButton_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_1_2.setBounds(778, 477, 136, 57);
		frame.getContentPane().add(btnNewButton_1_2);
	}
}