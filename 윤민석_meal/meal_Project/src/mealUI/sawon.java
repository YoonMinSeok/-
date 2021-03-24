package mealUI;

import javax.swing.*;
import javax.swing.text.*;
import mealDB.Driver_connect;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class sawon extends JFrame{
	Connection con;
	Statement st;
	String sawonnum = "";
	JLabel jl[] = {
			new JLabel("사원번호:"),
			new JLabel("*사원명:"),
			new JLabel("*패스워드:"),
			new JLabel("*패스워드 재입력:")
	};
	JLabel hide = new JLabel();
	JPanel jp  = new JPanel();
	JTextField jtf[] = {
			new JTextField(),
			new JTextField()
	};
	JPasswordField jpf [] = {
			new JPasswordField(),
			new JPasswordField()
	};
	
	//패스워드 필드 새로 두개 만들어야함
	JButton jb[] = {
			new JButton("동록"),
			new JButton("닫기")
	};
	sawon(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("사원등록");
		setLayout(new GridLayout(5,2));
		
		new search();
		
		hide.setVisible(false);
		jp.setLayout(new GridLayout(1,2));
		jp.add(jl[3]);
		jp.add(hide);
		
		for(int i=0; i<jpf.length; i++) {
			jpf[i].setDocument(new JTextFieldLimit(4));
		}
		
		add(jl[0]);
		add(jtf[0]);
		add(jl[1]);
		add(jtf[1]);
		add(jl[2]);
		add(jpf[0]);
		add(jp);
		add(jpf[1]);
		
		
		jpf[1].addActionListener(new keyL());
		jpf[1].requestFocus();
		
		int sawon = Integer.parseInt(sawonnum)+1;
		
		jtf[0].setText(Integer.toString(sawon));
		jtf[0].enable(false);
		
		for(int i=0; i<jb.length; i++) {
			jb[i].addActionListener(new btnL());
			add(jb[i]);
		}
		
		setVisible(true);
		setSize(400,350);
	}
	
	//사원번호 찾는 클래스
	class search{
		search(){
			try {
				con = Driver_connect.makeConnection("meal");
				st = con.createStatement();
				String sql = "select max(memberNO) from member";
	
				ResultSet rs = st.executeQuery(sql);
				
				while(rs.next()) {
					sawonnum = rs.getString(1);
				}
			}catch(SQLException e) {
				e.getMessage();
			}
		}
	}
	
	//패스워드 글자수 제한
	public class JTextFieldLimit extends PlainDocument{
		private int limit;
		public JTextFieldLimit(int limit) {
			super();
			this.limit = limit;
		}
		public void insertString(int offset , String str , AttributeSet attr)throws BadLocationException{
			if(str == null)
				return;
			if(getLength() + str.length() <= limit)
				super.insertString(offset, str, attr);
		}
	}
	
	//패스워드 재입력이랑 같은지 확인하는곳
	class keyL implements ActionListener{
		public void actionPerformed(ActionEvent e) {
				String pwd =new String(jpf[0].getPassword());
				String repwd =new String(jpf[1].getPassword());
				
				if(pwd.equals(repwd)) {
					hide.setVisible(true);
					hide.setForeground(Color.BLUE);
					hide.setText("일치");
				}else {
					hide.setVisible(true);
					hide.setForeground(Color.RED);
					hide.setText("불일치");
				}
		}
	}
	
	//버튼 리스너
	class btnL implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			switch ( e.getActionCommand()) {
				case "동록" : insert(); break;
				case "닫기" : dispose(); break;
			}
		}
	}
	
	public void insert(){
		String sawonnum = jtf[0].getText();
		String sawonname = jtf[1].getText();
		String password =new String(jpf[0].getPassword());
		String rpassword =new String(jpf[1].getPassword());
		
		if(sawonname.equals("") || password.equals("") || rpassword.equals("")) {
			JOptionPane.showMessageDialog(null, "필수 항목(*)누락","Message", JOptionPane.ERROR_MESSAGE);
		}else if(!password.equals(rpassword)) {
			JOptionPane.showMessageDialog(null, "패스워드 확인요망", "Message" , JOptionPane.ERROR_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, "사원이 등록되었습니다", "Message", JOptionPane.INFORMATION_MESSAGE);
			try {
				con = Driver_connect.makeConnection("meal");
				st = con.createStatement();
				String sql = "insert into member(memberNO,memberName, passwd) values ('"+sawonnum+"' , '"+sawonname+"' , '"+password+"')";
				
				int rs = st.executeUpdate(sql);
				
			}catch(SQLException e) {
				e.getMessage();
			}
		}
		
	}
	
}
