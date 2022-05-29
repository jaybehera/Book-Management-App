import java.awt.EventQueue;

import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BookCRUD {

	private JFrame frame;
	private JTextField txtBName;
	private JTextField txtEdition;
	private JTextField txtPrice;
	private JTable table;
	private JTextField txtBId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookCRUD window = new BookCRUD();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BookCRUD() {
		initialize();
		connect();
		table_load();
	}
	
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/schema1", "admin","7873");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void table_load() {
		try {
			pst = con.prepareStatement("select * from book");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 30));
		frame.setBounds(100, 100, 587, 374);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(191, 11, 186, 37);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(23, 52, 252, 158);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(10, 36, 76, 19);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1.setBounds(10, 72, 76, 28);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Price");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1_1.setBounds(10, 111, 76, 19);
		panel.add(lblNewLabel_1_1_1);
		
		txtBName = new JTextField();
		txtBName.setBounds(96, 37, 146, 20);
		panel.add(txtBName);
		txtBName.setColumns(10);
		
		txtEdition = new JTextField();
		txtEdition.setColumns(10);
		txtEdition.setBounds(96, 78, 146, 20);
		panel.add(txtEdition);
		
		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(96, 112, 146, 20);
		panel.add(txtPrice);
		
		//save
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bName,edition,price;
				
				bName = txtBName.getText();
				edition = txtEdition.getText();
				price = txtPrice.getText();
				
				try {
					pst = con.prepareStatement("insert into book(name,edition,price) values (?,?,?)");
					pst.setString(1, bName);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Record Added!!");
					table_load();
					txtBName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtBName.requestFocus();
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
		});
		btnSave.setBounds(23, 219, 72, 23);
		frame.getContentPane().add(btnSave);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtBName.setText("");
				txtEdition.setText("");
				txtPrice.setText("");
				txtBId.setText("");
				txtBName.requestFocus();
			}
		});
		btnClear.setBounds(203, 221, 72, 23);
		frame.getContentPane().add(btnClear);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});
		btnExit.setBounds(121, 221, 72, 23);
		frame.getContentPane().add(btnExit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(285, 59, 264, 181);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(23, 253, 252, 66);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_2 = new JLabel("Book ID");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_2.setBounds(10, 21, 76, 19);
		panel_1.add(lblNewLabel_1_2);
		
		txtBId = new JTextField();
		txtBId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				
				
				try {
					String id = txtBId.getText();
					pst = con.prepareStatement("select name,edition,price from book where id = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();
					
					if(rs.next()==true) {
						String name = rs.getString(1);
						String edition = rs.getString(2);
						String price = rs.getString(3);
						
						txtBName.setText(name);
						txtEdition.setText(edition);
						txtPrice.setText(price);
					}else {
						txtBName.setText("");
						txtEdition.setText("");
						txtPrice.setText("");
					}
					
				} catch (Exception e2) {
					
				}
			}
		});
		txtBId.setColumns(10);
		txtBId.setBounds(96, 22, 146, 20);
		panel_1.add(txtBId);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bName,edition,price,bId;
				
				bName = txtBName.getText();
				edition = txtEdition.getText();
				price = txtPrice.getText();
				bId = txtBId.getText();
				
				try {
					pst = con.prepareStatement("update book set name=?,edition=?,price=? where id=?");
					pst.setString(1, bName);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.setString(4, bId);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Record Updated!!");
					table_load();
					txtBName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtBName.requestFocus();
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
				
			}
		});
		btnUpdate.setBounds(317, 251, 90, 23);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bId;
				
				bId = txtBId.getText();
				
				try {
					pst = con.prepareStatement("delete from book where id=?");
					pst.setString(1, bId);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Deleted!!");
					table_load();
					txtBName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtBName.requestFocus();
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		btnDelete.setBounds(433, 251, 90, 23);
		frame.getContentPane().add(btnDelete);
	}
}
