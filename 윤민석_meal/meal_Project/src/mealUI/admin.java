package mealUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.*;

import mealDB.Driver_connect;

import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class admin extends JFrame{
	adminform adminform = new adminform();
	admin(){
		int result = JOptionPane.showConfirmDialog(null,adminform, "관리자 패스워드 입력",JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION) {
			
			try {
				
				String pwd = new String(adminform.jtf.getPassword());

				if(pwd.equals("0000")) {
					new management();
				}else {
					 JOptionPane.showMessageDialog(null, "관리자 패스워드를 확인하십시오", "Message", JOptionPane.ERROR_MESSAGE);
				 }
				
			}catch(Exception e) {
				
			}
		}else {
			dispose();
		}
	}
	
}


class adminform extends JPanel{
	
	JPasswordField jtf = new JPasswordField();
	JButton jb[]= new JButton[10];
	JPanel cnumber = new JPanel();
	JPanel number = new JPanel();
	
	adminform() {
	 
	 setLayout(new BorderLayout());
	 cnumber.setLayout(new GridLayout(3,3));
	 number.setLayout(new BorderLayout());
	
	 jtf.setEnabled(false); 
	 
		
		//숫자판 만들기
	 for(int i = 1; i<=9; i++) {
		jb[i-1] = new JButton(Integer.toString(i));
		cnumber.add(jb[i-1]);
		jb[i-1].addActionListener(new btnaction());
	 }
	 
	 jb[9] = new JButton("0");
	 jb[9].setPreferredSize(new Dimension(40,30));
	 jb[9].addActionListener(new btnaction());
	 
	 number.add(cnumber, BorderLayout.CENTER);
	 number.add(jb[9], BorderLayout.SOUTH);
	 
	 add(jtf, BorderLayout.NORTH);
	 add(number, BorderLayout.CENTER);
	 
	}
	class btnaction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String num = e.getActionCommand();
			String text = new String(jtf.getPassword());
			jtf.setText(text+num);
		}
	}	
	
}//adminform 끝나는부분


