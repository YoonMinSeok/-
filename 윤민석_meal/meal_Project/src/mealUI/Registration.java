package mealUI;

import javax.swing.*;

import mealDB.Driver_connect;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;

public class Registration extends JFrame{
	JLabel jl[] = {
			new JLabel("종류"),
			new JLabel("*메뉴명"),
			new JLabel("가격"),
			new JLabel("조리가능수량")
	};
	
	JTextField jtf = new JTextField();
	
	JComboBox jcb[] = { 
			new JComboBox(),
			new JComboBox(),
			new JComboBox()
			};
	
	JButton jb1 = new JButton("등록");
	JButton jb2 = new JButton("닫기");
	
	
	Registration(){
		setTitle("신규 메뉴 등록");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(5,2));
		
		
		//종류 콤보박스
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
				jcb[0] = new JComboBox(v);
			}
			
		}catch(SQLException e) {
			e.getMessage();
		}
		
		
		//가격 콤보박스
		Vector<Integer>p = new Vector<Integer>();
		
		for(int i=1000; i<=12000; i=i+500) {
			p.add(i);
		}
		
		for(int i=0; i<p.size(); i++) {
			jcb[1] = new JComboBox(p);
		}
		
		
		//조리수량 콤보박스
		Vector<Integer>su = new Vector<Integer>();
		
		for(int i=0; i<=50 ; i++) {
			su.add(i);
		}
		for(int i=0; i<su.size(); i++) {
			jcb[2] = new JComboBox(su);
		}
		
		int a=0;
		for(int i=0; i<jl.length; i++) {
			
			add(jl[i]);
			if(i==1) {
				add(jtf);
			}else {
			add(jcb[a]);
			a++;
			}
		}
		
		add(jb1);
		add(jb2);
		
		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int maxcount=0;
					int todaymeal =1;
					String sql2 = "select max(mealNo) from meal";
					Connection con2 = Driver_connect.makeConnection("meal");
					PreparedStatement psmt2 = con2.prepareStatement(sql2);
					
					ResultSet rs = psmt2.executeQuery();
					
					while(rs.next()) {
						maxcount = Integer.parseInt(rs.getString(1)); 
					}
					
					
					int cuisineno=0;
					String menukind = jcb[0].getSelectedItem().toString();
					
					switch(menukind) {
					case "한식":
						cuisineno=1;
						break;
					case "중식":
						cuisineno =2;
						break;
					case "일식":
						cuisineno = 3;
						break;
					case "양식":
						cuisineno = 4;
						break;
					}
					
					String menuname = jtf.getText();
					String menuprice = jcb[1].getSelectedItem().toString();
					String menucount = jcb[2].getSelectedItem().toString();
					
					if(!menuname.equals("")) {
					String sql = "insert into meal values(?,?,?,?,?,?)";
					
					Connection con = Driver_connect.makeConnection("meal");
					PreparedStatement psmt = con.prepareStatement(sql);
					
					
					psmt.setInt(1, maxcount+1);
					psmt.setInt(2, cuisineno);
					psmt.setString(3, menuname);
					psmt.setString(4, menuprice);
					psmt.setString(5, menucount);
					psmt.setInt(6, todaymeal);
					
					int re = psmt.executeUpdate();
					
					if(re == 1) {
						JOptionPane.showConfirmDialog(null, "메뉴가 정상적으로 등록되었습니다", "Message", JOptionPane.OK_OPTION);
						dispose();
					}else {
						 JOptionPane.showMessageDialog(null, "메뉴명을 입력해주세요", "Message", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "메뉴명을 입력해주세요", "Message", JOptionPane.ERROR_MESSAGE);
				}
				}catch(SQLException ee) {
					ee.getMessage();
				}
				
			}
		});
		
		
		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		setSize(400,300);
		setVisible(true);
	}
}
