package datVe;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.*;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTable;

public class main extends JFrame {

	private JPanel contentPane;
	private JTextField maVe;
	private JTextField maHoaDon;
	private JTextField slVe;
	private JLabel notify;
	private JTable table;
	private DefaultTableModel tableModel = new DefaultTableModel();
	private JComboBox methodSelectNV;
	private JComboBox methodSelectKH;
	private JComboBox methodSelectCD;
//	private JTextField maNV;
//	private JTextField maKH;
//	private JTextField maChuyenXe;
	private JTextField price;
	private String maKhachHang[];
	DefaultComboBoxModel MaNV = new DefaultComboBoxModel();
	DefaultComboBoxModel MaKH = new DefaultComboBoxModel();
	DefaultComboBoxModel MaChuyenDi = new DefaultComboBoxModel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main frame = new main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Connection connect(String port) throws SQLException {
		String dbURL = "jdbc:sqlserver://localhost:" + port + ";databaseName=DATVEXE;user=sa;password=123";
		Connection conn = null;
		
		conn = DriverManager.getConnection(dbURL);

		DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
		System.out.println("Product name: " + dm.getURL());
	
		return conn;
	}

	void actions(String method, Connection conn) throws SQLException {
		if (method.equals("Create")) {
			String sql = "INSERT INTO VEBAN(maVe,maHoaDon,SLVeBan,maNhanVien,maKhachHang,maChuyenXe,gia) VALUES(?,?,?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, maVe.getText().toString());
			stmt.setString(2, maHoaDon.getText().toString());
			stmt.setInt(3, Integer.parseInt(slVe.getText().toString()));
			stmt.setString(4, ( String) MaNV.getSelectedItem().toString());
			stmt.setString(5, ( String) MaKH.getSelectedItem().toString());
			stmt.setString(6, ( String) MaChuyenDi.getSelectedItem().toString());
			stmt.setString(7, price.getText().toString());
			
			stmt.executeUpdate();
		} else if (method.equals("Update")) {
			String sql = "UPDATE VEBAN SET maHoaDon= ?,SLVeBan= ?,maNhanVien = ?,maKhachHang=?,maChuyenXe=?,gia = ?  WHERE maVe= ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, maHoaDon.getText().toString());
			stmt.setInt(2, Integer.parseInt(slVe.getText().toString()));
			stmt.setString(3,( String) MaNV.getSelectedItem().toString());
			stmt.setString(4, ( String) MaKH.getSelectedItem().toString());
			stmt.setString(5, ( String) MaChuyenDi.getSelectedItem().toString());
			stmt.setString(6, price.getText().toString());
			stmt.setString(7, maVe.getText().toString());
			stmt.executeUpdate();
		} else {
			String sql = "DELETE FROM VEBAN WHERE maVe= ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, maVe.getText().toString());
			stmt.executeUpdate();
		}

	}

	void setData(Connection conn) {
		try {

			String sql = "select * from VEBAN";
			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(sql);
			Vector data = null;
			tableModel.setRowCount(0);
			while (rs.next()) {
				data = new Vector();
				data.add(rs.getString("maVe"));
				data.add(rs.getString("maHoaDon"));
				data.add(rs.getInt("SLVeBan"));
				data.add(rs.getString("maNhanVien"));
				data.add(rs.getString("maKhachHang"));
				data.add(rs.getString("maChuyenXe"));
				data.add(rs.getString("gia"));
				System.out.println(rs.getString("maVe"));
				tableModel.addRow(data);
			}
			table.setModel(tableModel);
		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {

		}
	}
	void findOtherTable(Connection conn) {
		try {

			String sql = "select MaNV from NHANVIEN ";
			String sql_1 = "select MaKhachHang from KHACHHANG ";
			String sql_2 = "select MaChuyenDi from CHUYENXE ";
//			PreparedStatement  ps = conn.prepareStatement(sql); 
//			ps.setString(1, "Hung");
//			System.out.println(sql);
//			ResultSet rs = ps.executeQuery();
//			System.out.println(rs);
			Statement st = conn.createStatement();
			Statement st_1 = conn.createStatement();
			Statement st_2 = conn.createStatement();

			ResultSet rs = st.executeQuery(sql);
			ResultSet rs_1 = st_1.executeQuery(sql_1);
			ResultSet rs_2 = st_2.executeQuery(sql_2);
//			System.out.println(rs);
			Vector data = null;
			tableModel.setRowCount(0);
			
			while (rs.next()) {
//				System.out.println(rs.getInt("MaNV"));
				MaNV.addElement(rs.getInt("MaNV"));
			}
			while (rs_1.next()) {
//				System.out.println(rs_1.getInt("MaKhachHang"));
				MaKH.addElement(rs_1.getInt("MaKhachHang"));
			}
			while (rs_2.next()) {
//				System.out.println(rs_2.getInt("MaChuyenDi"));
				MaChuyenDi.addElement(rs_2.getInt("MaChuyenDi"));
			}
//			System.out.println(KH);
//			table.setModel(tableModel);
		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {

		}
	}


	 void loadData(String server) {
		try {
			System.out.println(server);
			switch (server) {
			case "Server1":
				Connection conn1 = connect("49150");
				findOtherTable(conn1);
				setData(conn1);
//				findOtherTable(conn1);
				break;
			case "Server2":
				Connection conn2 = connect("49151");
				setData(conn2);
				break;
			case "Server3":
				Connection conn3 = connect("49152");
				setData(conn3);
				break;
			default:
				break;
			}	

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * Create the frame.
	 */
	public main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 872, 422);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Server");
		lblNewLabel.setBounds(717, 63, 46, 14);
		contentPane.add(lblNewLabel);

		String server[] = { "Server1", "Server2", "Server3" };
//		System.out.println(server);
		JComboBox serverName = new JComboBox(server);
		serverName.setBounds(773, 59, 73, 22);
		contentPane.add(serverName);

		String methodOptions[] = { "Create", "Update", "Delete" };
		JComboBox methodSelect = new JComboBox(methodOptions);
		methodSelect.setBounds(773, 92, 73, 22);
		contentPane.add(methodSelect);

		JButton btnNewButton = new JButton("Thuc hien");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String server = (String) serverName.getSelectedItem();
				String method = (String) methodSelect.getSelectedItem();
				System.out.println(MaKH.getSelectedItem());
				try {

					switch (server) {
					case "Server1":
						Connection conn1 = connect("49150");
						actions(method, conn1);
						notify.setText(method + "  Successfully" + server);
						break;
					case "Server2":
						Connection conn2 = connect("49151");
						actions(method, conn2);
						notify.setText(method + "  Successfully" + server);
						break;
					case "Server3":
						Connection conn3 = connect("49152");
						actions(method, conn3);
						notify.setText(method + "  Successfully -" + server);
						break;
					default:
						break;
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//String server = (String) serverName.getSelectedItem();
				loadData(server);
			}
		});
		btnNewButton.setBounds(539, 347, 105, 23);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("Mã Hóa Đơn");
		lblNewLabel_1.setBounds(539, 99, 73, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("SL vé");
		lblNewLabel_1_1.setBounds(539, 132, 58, 14);
		contentPane.add(lblNewLabel_1_1);

		maHoaDon = new JTextField();
		maHoaDon.setBounds(622, 96, 86, 20);
		contentPane.add(maHoaDon);
		maHoaDon.setColumns(10);

		slVe = new JTextField();
		slVe.setColumns(10);
		slVe.setBounds(622, 127, 86, 20);
		contentPane.add(slVe);
		
		JLabel lblNewLabel_2 = new JLabel("mã khách hàng");
		lblNewLabel_2.setBounds(539, 198, 58, 14);
		contentPane.add(lblNewLabel_2);


		JLabel lblMethod = new JLabel("Method");
		lblMethod.setBounds(717, 96, 46, 14);
		contentPane.add(lblMethod);

		JLabel lblNewLabel_1_2 = new JLabel("Mã vé");
		lblNewLabel_1_2.setBounds(539, 66, 58, 14);
		contentPane.add(lblNewLabel_1_2);

		maVe = new JTextField();
		maVe.setColumns(10);
		maVe.setBounds(622, 63, 86, 20);
		contentPane.add(maVe);

		JLabel lblNewLabel_3 = new JLabel("Đăng kí chuyến xe");
		lblNewLabel_3.setForeground(Color.BLUE);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel_3.setBounds(320, 11, 300, 37);
		contentPane.add(lblNewLabel_3);

		notify = new JLabel("");
		notify.setForeground(Color.GREEN);
		notify.setHorizontalAlignment(SwingConstants.CENTER);
		notify.setFont(new Font("Tahoma", Font.ITALIC, 14));
		notify.setBounds(539, 304, 307, 32);
		contentPane.add(notify);

		String header[] = { "Ma Vé", "hoa Don", "SL ve", "ma NV", "maKH","ma C.Xe","gia" };
		tableModel.setColumnIdentifiers(header);

		table = new JTable(tableModel);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);

		ListSelectionModel cellSelectionModel = table.getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try {
					int row = table.getSelectedRow();
					if(row == -1) return;
					String _mv = table.getModel().getValueAt(row, 0).toString();
					String _mHoaDon = table.getModel().getValueAt(row, 1).toString();
					int _slVe = (int) table.getModel().getValueAt(row, 2);
					String _maNV = table.getModel().getValueAt(row, 3).toString();
					String _maKH = table.getModel().getValueAt(row, 4).toString();
					String _maChuyenDi = table.getModel().getValueAt(row, 5).toString();
					String _price = table.getModel().getValueAt(row, 6).toString();
					

					maVe.setText(_mv);
					maHoaDon.setText(_mHoaDon);
					slVe.setText(Integer.toString(_slVe));
					MaNV.setSelectedItem(_maNV);
					MaKH.setSelectedItem(_maKH);
					MaChuyenDi.setSelectedItem(_maChuyenDi);
					price.setText(_price);
					
					
				} catch (IndexOutOfBoundsException e123) {
					e123.printStackTrace();
				}
			}

		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 59, 504, 311);
		contentPane.add(scrollPane);
		
		JButton btnNewButton_1 = new JButton("Thoat");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1.setBounds(757, 347, 89, 23);
		contentPane.add(btnNewButton_1);
		
//		JComboBox methodSelectKH = new JComboBox(KH);
		
//		JLabel lblNewLabel_2 = new JLabel("mã khách hàng");
//		lblNewLabel_2.setBounds(539, 198, 58, 14);
//		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_4 = new JLabel("mã chuyến đi");
		lblNewLabel_4.setBounds(539, 231, 58, 14);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Gia");
		lblNewLabel_5.setBounds(539, 264, 58, 14);
		contentPane.add(lblNewLabel_5);
		
//		Int Kh[] = { "Server1", "Server2", "Server3" };
		methodSelectNV = new JComboBox(MaNV);
		methodSelectNV.setBounds(622, 162, 86, 20);
		contentPane.add(methodSelectNV);
		
		methodSelectKH = new JComboBox(MaKH);
		methodSelectKH.setBounds(622, 195, 86, 20);
		contentPane.add(methodSelectKH);
		
		methodSelectCD = new JComboBox(MaChuyenDi);
		methodSelectCD.setBounds(622, 228, 86, 20);
		contentPane.add(methodSelectCD);
		
		JLabel lblNewLabel_6 = new JLabel("Mã Nhân viên");
		lblNewLabel_6.setBounds(539, 165, 58, 14);
		contentPane.add(lblNewLabel_6);
		
//		maNV = new JTextField();
//		maNV.setColumns(10);
//		maNV.setBounds(622, 162, 86, 20);
//		contentPane.add(maNV);
		
//		maKH = new JTextField();
//		maKH.setColumns(10);
//		maKH.setBounds(622, 195, 86, 20);
//		contentPane.add(maKH);
//		
//		maChuyenXe = new JTextField();
//		maChuyenXe.setColumns(10);
//		maChuyenXe.setBounds(622, 228, 86, 20);
//		contentPane.add(maChuyenXe);
		
		price = new JTextField();
		price.setColumns(10);
		price.setBounds(622, 261, 86, 20);
		contentPane.add(price);

//		JButton btnNewButton_1 = new JButton("Load data");
//		btnNewButton_1.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				String server = (String) serverName.getSelectedItem();
//				loadData(server);
//
//			}
//		});
//		btnNewButton_1.setBounds(456, 212, 100, 23);
//		contentPane.add(btnNewButton_1);
		loadData("Server1");

	}
}