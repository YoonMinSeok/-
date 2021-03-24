package mealUI;

import javax.swing.*;

import mealDB.Driver_connect;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.*;

public class mmanagement extends JFrame{
	JPanel jp = new JPanel();
	JLabel jl = new JLabel("종류:");
	JComboBox jcb;
	JButton jb[] = {
			new JButton("검색"),
			new JButton("수정"),
			new JButton("삭제"),
			new JButton("오늘의 메뉴 선정"),
			new JButton("닫기")
	};
	
	JTable jt = new JTable();
	JScrollPane scrollPane = new JScrollPane();
	
	Vector<Vector<Object>>rows = new Vector<Vector<Object>>();
	Vector<Object>cols = new Vector<Object>() ;
	
	public void search(){
		try {
			
			Connection con = Driver_connect.makeConnection("meal");
			Statement st = con.createStatement();
			int cuisineno =0;
			String menu = jcb.getSelectedItem().toString();
			
			switch(menu) {
			case "한식" :
				cuisineno=1;
				break;
			case "중식":
				cuisineno =2;
				break;
			case "일식":
				cuisineno =3;
				break;
			case "양식":
				cuisineno = 4;
				break;
			}
			
			String sql = "select mealName,price,maxCount,todayMeal from meal where cuisineNo ='"+cuisineno+"'";
			ResultSet rs = st.executeQuery(sql);
			
			rows.clear();
			
			while(rs.next()) {
				
				Vector <Object> v = new Vector<Object>();
				
				String todaymeal ="";
				
				if(rs.getString("todayMeal").equals("0")) {
					todaymeal = "N";
				}else if(rs.getString("todayMeal").equals("1")) {
					todaymeal = "Y";
				}
				
				v.add(false);
				v.add(rs.getString("mealName"));
				v.add(rs.getString("price"));
				v.add(rs.getString("maxCount"));
				v.add(todaymeal);
				
				rows.add(v);
			}
			
		}catch(SQLException ee) {
			ee.getMessage();
		}
		
		jt.updateUI();
	}
	
	
	mmanagement(){
		setTitle("메뉴 관리");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		
		try {
			
			Connection con = Driver_connect.makeConnection("meal");
			Statement st = con.createStatement();
			Vector <String> v = new Vector<String>();
			String sql = "select cuisineName from cuisine";
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				v.add(rs.getString(1));
			}
			
			for(int i=0; i<v.size(); i++) {
				jcb = new JComboBox(v);
			}
			
		}catch(SQLException e) {
			e.getMessage();
		}
		
		jp.add(jl);
		jp.add(jcb);
		for(int i=0; i<jb.length; i++) {
			jp.add(jb[i]);
		}
		
		cols.add("□");
		cols.add("menuName");
		cols.add("price");
		cols.add("maxCount");
		cols.add("todayMeal");
		
		
		DefaultTableModel model = new DefaultTableModel(rows, cols) {
			public Class<?> getColumnClass(int column){
				switch(column) {
				case 0 :
					return Boolean.class;
				default: 
					return String.class;
				}
			}
		};
		
		jt = new JTable(model);
		scrollPane = new JScrollPane(jt);
		
		
		
		try {
			
			Connection con = Driver_connect.makeConnection("meal");
			Statement st = con.createStatement();
			int cuisineno =0;
			String menu = jcb.getSelectedItem().toString();
			
			switch(menu) {
			case "한식" :
				cuisineno=1;
				break;
			case "중식":
				cuisineno =2;
				break;
			case "일식":
				cuisineno =3;
				break;
			case "양식":
				cuisineno = 4;
				break;
			}
			
			String sql = "select mealName,price,maxCount,todayMeal from meal where cuisineNo ='"+cuisineno+"'";
			ResultSet rs = st.executeQuery(sql);
			
			rows.clear();
			
			while(rs.next()) {
				
				Vector <Object> v = new Vector<Object>();
				
				String todaymeal ="";
				
				if(rs.getString("todayMeal").equals("0")) {
					todaymeal = "N";
				}else {
					todaymeal = "Y";
				}
				
				v.add(false);
				v.add(rs.getString("mealName"));
				v.add(rs.getString("price"));
				v.add(rs.getString("maxCount"));
				v.add(todaymeal);
				
				rows.add(v);
			}
			
		}catch(SQLException e) {
			e.getMessage();
		}
	
		jt.updateUI();
		
		//검색 
		jb[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		
		//수정
		jb[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=0;
				String mealkind =jcb.getSelectedItem().toString();
				Vector<Object> vv = new Vector<Object>();
				for(int j=0; j<model.getRowCount(); j++) {
					if(model.getValueAt(j, 0).equals(true)) {
						i++;
						for(int k=0; k<model.getColumnCount()-2; k++) {
							vv.add(model.getValueAt(j, k+1));
						}
					}
				}
				if(i>1) {
					JOptionPane.showMessageDialog(null, "하나씩 수정가능합니다.", "Message", JOptionPane.ERROR_MESSAGE);
				}
				else if(i==0) {
					JOptionPane.showMessageDialog(null, "수정할 메뉴를 선택해주세요.", "Message", JOptionPane.ERROR_MESSAGE);
				}else {
					new menusujung(mealkind,vv);
				}
				
			}
		});
		
		
		//삭제
		
		jb[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count =0;
				for(int i=0; i<model.getRowCount(); i++) {
					if(model.getValueAt(i, 0).equals(true)) {
						count++;
					}
				}
				
				if(count == 0) {
					JOptionPane.showMessageDialog(null, "삭제할 메뉴를 선택해주세요", "Message", JOptionPane.ERROR_MESSAGE);
				}else {
					try {
						Connection con = Driver_connect.makeConnection("meal");
						Statement st = con.createStatement();
						
						for(int i=0; i<model.getRowCount(); i++) {
							if(model.getValueAt(i, 0).equals(true)) {
								String sql = "delete from meal where mealName = '"+model.getValueAt(i,1)+"'";
								
								st.execute(sql);
								
								JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다");
							}
						}
					
					}catch(SQLException ee) {
						ee.getMessage();
					}
				}
				
			}
		});
		
		//오늘의 메뉴 선정
		jb[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count =0;
				for(int i=0; i<model.getRowCount(); i++) {
					if(model.getValueAt(i, 0).equals(true)) {
						count++;
					}
				}
				if(count >=25) {
					JOptionPane.showMessageDialog(null, "25개를 초과할 수 없습니다.", "Message", JOptionPane.ERROR_MESSAGE);
				}else {
					try {
						Connection con = Driver_connect.makeConnection("meal");
						Statement st = con.createStatement();
						
						int cuisionno = 0;
						//모든테이블의 오늘 의 메뉴 0으로 만들기
						switch(jcb.getSelectedItem().toString()) {
						case "한식" : 
							cuisionno = 1;
							break;
						case "중식" : 
							cuisionno = 2;
							break;
						case "일식" : 
							cuisionno = 3;
							break;
						case "양식" : 
							cuisionno = 4;
							break;
						}
						
						System.out.println(cuisionno);
						for(int i=0; i<model.getRowCount(); i++) {
							boolean a = (boolean) model.getValueAt(i, 0);
							String name =model.getValueAt(i, 1).toString();
							
							String sql= "";
							if(a) {
								
								sql = "update meal set todayMeal = 1 where mealName = '"+name+"'";
								
							}else{
								
								sql = "update meal set todayMeal = 0 where mealName = '"+name+"'";
								
							}
							st.executeUpdate(sql);
							
						}
						
						search();
					}catch(SQLException ee) {
						ee.getMessage();
					}
				}
				
			}
		});
		
		//닫기
		jb[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		DefaultTableCellRenderer dter = new DefaultTableCellRenderer();
		dter.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = jt.getColumnModel();
		for(int i=0; i< tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dter);
		}
		
		add(jp, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		
		setSize(600,700);
		setVisible(true);
	}
}
