package mealUI;

import javax.swing.*;

import mealDB.Driver_connect;
import java.sql.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.table.*;

public class payment extends JFrame{
	
	JPanel jp [] = {
			new JPanel(),
			new JPanel()
	};
	JLabel jl = new JLabel("메뉴명:");
	JTextField jtf = new JTextField(10);
	
	JButton btn[] = {
			new JButton("조회"),
			new JButton("새로고침"),
			new JButton("인쇄"),
			new JButton("닫기")
	};
	
	JTable jt = new JTable();
	JScrollPane scrollPane = new JScrollPane();
	
	Vector<Vector<Object>>rows = new Vector<Vector<Object>>();
	Vector<Object>cols = new Vector<Object>();
	DefaultTableModel model = new DefaultTableModel(rows,cols);
	
	
	payment(){
		setTitle("결제 조회");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		cols.add("종류");
		cols.add("메뉴명");
		cols.add("사원명");
		cols.add("결제수량");
		cols.add("총결제금액");
		cols.add("결제일");
		
		search("");
		
		//상위 레이아웃 
		jp[0].setLayout(new FlowLayout());
		jp[0].add(jl);
		jp[0].add(jtf);
		for(int i=0; i<btn.length; i++) {
			jp[0].add(btn[i]);
		}
		
		jt = new JTable(model);
		scrollPane = new JScrollPane(jt);
		
		jp[1].add(scrollPane);
		
		add(jp[0], BorderLayout.NORTH);
		add(scrollPane,BorderLayout.CENTER);
		
		DefaultTableCellRenderer dter = new DefaultTableCellRenderer();
		dter.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = jt.getColumnModel();
		for(int i=0; i< tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dter);
		}
		
		btn[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = jtf.getText();
				search(text);
			}
		});
		
		btn[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtf.setText("");
				search("");
			}
		});
		
		btn[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new print();
			}
		});
		btn[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		setSize(600,800);
		setVisible(true);
	}
	
	public void search(String text) {
			String text2 = text;
			Connection con = Driver_connect.makeConnection("meal");
			String sql = "";
			
		try {
			
			Statement st = con.createStatement();
			rows.clear();
			if(!text.equals("")) {
				sql = "select cuisine.cuisineName, meal.mealName,member.memberName, "
						+ "orderCount, amount, orderDate from orderlist inner join member "
						+ "on orderlist.memberNo = member.memberNo join meal "
						+ "on orderlist.mealNo = meal.mealNo "
						+ "join cuisine on orderlist.cuisineNo = cuisine.cuisineNo "
						+ "where meal.mealName like '%"+text2+"%'";
				
			}else  {
				sql = "select cuisine.cuisineName, meal.mealName,member.memberName, "
						+ "orderCount, amount, orderDate from orderlist inner join member "
						+ "on orderlist.memberNo = member.memberNo join meal "
						+ "on orderlist.mealNo = meal.mealNo "
						+ "join cuisine on orderlist.cuisineNo = cuisine.cuisineNo";
				
			}
			
				ResultSet rs = st.executeQuery(sql);
				
				while(rs.next()) {
					Vector<Object>v = new Vector<Object>();
					
					v.add(rs.getString(1));
					v.add(rs.getString(2));
					v.add(rs.getString(3));
					v.add(rs.getString(4));
					v.add(rs.getString(5));
					v.add(rs.getString(6));
					
					rows.add(v);
				}
			
		}catch(SQLException e) {
			e.getMessage();
		}
		
		jt.updateUI();
		
	}
	
}
