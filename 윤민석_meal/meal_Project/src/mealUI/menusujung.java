package mealUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import mealDB.Driver_connect;

import java.sql.*;
import java.util.*;

public class menusujung extends JFrame{
	JLabel jl[] = {
			new JLabel("종류"),
			new JLabel("*메뉴명"),
			new JLabel("가격"),
			new JLabel("조리가능수량")
	};
	
	JComboBox jcb[] = {
			new JComboBox(),
			new JComboBox(),
			new JComboBox()
	};
	
	JTextField jtf = new JTextField();
	
	JButton jb[] = {
		new JButton("수정"),
		new JButton("닫기")
	};
	String mealkind;
	Vector<Object> menulist = new Vector<Object>();
	menusujung(String mealkind,Vector<Object> vv){
		
		this.mealkind = mealkind;
		menulist = vv;
		
		
		setTitle("메뉴 수정");
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
			
		// 내가 누른 값을 적용시키는곳
		switch(mealkind) {
			case "한식":
				jcb[0].setSelectedIndex(0);
				break;
			case "중식":
				jcb[0].setSelectedIndex(1);
				break;
			case "일식":
				jcb[0].setSelectedIndex(2);
				break;
			case "양식":
				jcb[0].setSelectedIndex(3);
				break;
		}
		
		jtf.setText(menulist.get(0).toString());		
		
		//여기서 내가 가져온 값의 콤보박스값으로 바꿔야됨 현재 내 객체의 콤보박스를
		int combonum=0;
		
		for(int i=1000; i<=12000; i+=500) {
			if(menulist.get(1).toString().equals(Integer.toString(i))) {
				jcb[1].setSelectedIndex(combonum);
				break;
			}
			else {
				combonum++;
			}
		}
		
		int comboz = 0;
		for(int i=0; i<=50; i++) {
			if(menulist.get(2).toString().equals(Integer.toString(i))) {
				jcb[2].setSelectedIndex(comboz);
				break;
			}else {
				comboz++;
			}
		}
		
		jb[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection con = Driver_connect.makeConnection("meal");
					Statement st = con.createStatement();
					
					String kind = jcb[0].getSelectedItem().toString();
					
					String sql = "update meal set  mealName = '"+jtf.getText()+"',price = '"+jcb[1].getSelectedItem().toString()+"' , maxCount = '"+jcb[2].getSelectedItem().toString()+"'  where mealName = '"+menulist.get(0).toString()+"'";
					
					st.executeUpdate(sql);
					
					JOptionPane.showMessageDialog(null, "메뉴가 정상적으로 수정되었습니다.");
					
				}
				catch(SQLException ee) {
					ee.getMessage();
				}
			}
		});
		
		
		jb[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		
		add(jb[0]);
		add(jb[1]);
		
		setSize(300,300);
		setVisible(true);
	}
	
}
