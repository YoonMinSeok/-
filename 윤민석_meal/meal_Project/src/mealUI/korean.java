package mealUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import mealDB.Driver_connect;

public class korean extends JFrame{
	JPanel bjp [] = {
			new JPanel(),
			new JPanel(),
			new JPanel()
	};
	
	String menuname;
	 String x;
	Vector<JButton> menu = new Vector<JButton>();
	JTextField jtb1;
	JTextField jtb2;
	int bigsum=0;
	
 korean(String name){
	 
	 JLabel top = new JLabel(this.menuname= name);
	 setTitle("결제");
	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 Container c = getContentPane();
	
	 try {
		 
		 Connection con = Driver_connect.makeConnection("meal");
		 Statement st = con.createStatement();
		 
		 String menuno= null;
		 
		 String sql2 = "select cuisineNo from cuisine where cuisineName = '"+ menuname + "'";
		 
		 ResultSet rss = st.executeQuery(sql2);
		 
		 while(rss.next()) {
			 menuno = rss.getString(1);
		 }
		 
		 String sql = "select mealName, price, maxCount from meal where cuisineNo = '"+ menuno + "' and todayMeal = '1'";
		 
		 ResultSet rs = st.executeQuery(sql);
		 
		 while(rs.next()) {
			 
			 button bt = new button(rs.getString("mealName"), rs.getString("price"));
			 String x= rs.getString("maxCount");
			 if(Integer.parseInt(x) == 0) {
				 bt.setEnabled(false);
			 }
			 menu.add(bt);

		 }
		 
		 
		 
	 }catch(SQLException e) {
		 System.out.println("SQL오류!!");
	 }
	 
	 //메뉴판세팅
	 int row = menu.size();
	 if(row%5==0) {
		 row= row / 5;
	 }else {
		 row= row/5 +1;
	 }
	 
	 bjp[1].setLayout(new GridLayout(row,5));
	 
	 for(int i = 0; i<menu.size(); i++) {
		 bjp[1].add(menu.get(i));
		 
	 }
	 Font f1 = new Font("돋움", Font.BOLD, 20);
	 
	 top.setFont(f1);
	 bjp[0].add(top);
	 
	 
	 bjp[2].setLayout(new GridLayout(2,1,0,15));
	 
	 
	 
	//오른쪽 리스트 테이블 생성 및 디자인 
	 
	 JPanel Listbox = new JPanel();
	 
	 Vector<Vector<String>>rowData = new Vector<Vector<String>>();
	 Vector<String> colData = new Vector<String>();
	 
	 colData.add("상품번호");
	 colData.add("품명");
	 colData.add("수량");
	 colData.add("금액");
	 
	 //J테이블 만들기
	 DefaultTableModel model = new DefaultTableModel(rowData,colData);
	 
	 JTable jt = new JTable(model);
	 
	 JScrollPane jsp = new JScrollPane(jt);
	 
	 JLabel Easttop1 = new JLabel("총결제금액: ");
	 JLabel Eastnum = new JLabel("0");
	 JLabel won = new JLabel("원");
	 JPanel Easttop2 = new JPanel();
	 
	 Easttop2.add(Eastnum);
	 Easttop2.add(won);
	 
	 Easttop1.setFont(f1);
	 won.setFont(f1);
	 Eastnum.setFont(f1);
	 
	 JPanel Easttop = new JPanel();
	 Easttop.setLayout(new BorderLayout());
	 Easttop.add(Easttop1,BorderLayout.WEST);
	 Easttop.add(Easttop2,BorderLayout.EAST);
	 
	 Listbox.setLayout(new BorderLayout());
	 Listbox.add(Easttop, BorderLayout.NORTH);
	 Listbox.add(jsp , BorderLayout.CENTER);
	 
	 //jtable 행 지우기
	 jt.addMouseListener(new MouseAdapter() {
		 public void mousePressed(MouseEvent e) {
			 if(e.getClickCount()==2) {
				 int selection = jt.getSelectedRow();
				 
				 //
				 Vector <String> clickrow = rowData.get(selection);
				 int clickprice= Integer.parseInt(clickrow.get(3));
				
				 bigsum = bigsum-clickprice; 
				 String newsum = NumberFormat.getInstance().format(bigsum);
				 Eastnum.setText(newsum);
				 
				 rowData.remove(selection);
				 String name =clickrow.get(1);
				 jt.updateUI();
				 
				 //메뉴 버튼 다시 true시키는것
				 for(int i=0; i<menu.size(); i++) {
					 JButton jbtn = menu.get(i);
					 String menulist = jbtn.getText();
					 StringTokenizer st = new StringTokenizer(menulist,"<html><center><br>");
					 String menuname = st.nextToken();
					 
					 if(menuname.equals(name)) {
						 jbtn.setEnabled(true);
					 }
				 }
				 
			 }
		 }
	 });
	 
	 //오른쪽 밑에 버튼및 등등 디자인
	 JPanel ejp[] = {
			 new JPanel(),
			 new JPanel(),
			 new JPanel(),
			 new JPanel()
	 };
	 
	 JPanel number = new JPanel();
	 JPanel cnumber = new JPanel();

	 JPanel ebtn = new JPanel();
	 
	 cnumber.setLayout(new GridLayout(3,3));
	 
	 // 선택품명 및 수량 선택하는 곳
	 JLabel Eastb1 = new JLabel("선택품명: ");
	 JLabel Eastb2 = new JLabel("수량: ");
	 jtb1 = new JTextField(30);
	 jtb2 = new JTextField(10);
	 
	 jtb1.setEnabled(false);
	 jtb2.setEnabled(false);
	 
	 ejp[1].add(Eastb1);
	 ejp[1].add(jtb1);
	 ejp[1].add(Eastb2);
	 ejp[1].add(jtb2);
	 
	 
	 // 숫자판
	 JButton jb[]= new JButton[10];
	 
	 for(int i = 1; i<=9; i++) {
		jb[i-1] = new JButton(Integer.toString(i));
		cnumber.add(jb[i-1]);
		jb[i-1].addActionListener(new btnaction());
	 }
	 
	 jb[9] = new JButton("0");
	 jb[9].setPreferredSize(new Dimension(40,50));
	 jb[9].addActionListener(new btnaction());
	
	 	 
	 // 입력 및 초기화
	 JButton in = new JButton("입력");
	 JButton reset = new JButton("초기화");
	 reset.setPreferredSize(new Dimension(70,50));
	 ebtn.setLayout(new BorderLayout());
	 number.setLayout(new BorderLayout());
	 
	 ebtn.add(in , BorderLayout.CENTER);
	 ebtn.add(reset , BorderLayout.SOUTH);
	 number.add(cnumber, BorderLayout.CENTER);
	 number.add(jb[9], BorderLayout.SOUTH);
	 

	 //입력 이벤트
	 in.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			 
			 if(jtb1.getText().equals("")) {
				 JOptionPane.showMessageDialog(null, "품명을 선택해주세요", "Message", JOptionPane.ERROR_MESSAGE);
			 }
			 else if(jtb2.getText().equals("")) {
				 JOptionPane.showMessageDialog(null, "수량을 지정해주세요", "Message", JOptionPane.ERROR_MESSAGE);
			 }
			 else {
				 try {
					 
					 int maxcount=0;
					 
					 Connection con = Driver_connect.makeConnection("meal");
					 Statement st = con.createStatement();
					 Vector<String>row = new Vector<String>();
					 String sql = "select maxCount from meal";
					 int su = Integer.parseInt(jtb2.getText());
					 
					 ResultSet rs = st.executeQuery(sql);
					 //여기서 조리가능 수량이랑 내가 입력한 수량이랑 확인하는 작업해야함
					 
					 while(rs.next()) {
						  maxcount = Integer.parseInt(rs.getString(1));
					 }
					 
					 if( maxcount <= Integer.parseInt(jtb2.getText())) {
						 JOptionPane.showMessageDialog(null, "조리가능수량을 초과하였습니다.", "Message", JOptionPane.ERROR_MESSAGE);
					 }else {
						 
						
						String sql2 = "select mealNo , price from meal where mealName = '"+ jtb1.getText()+ "'";
						ResultSet rs2 = st.executeQuery(sql2);
						String number = null;
						String price = null;
						
						while(rs2.next()) {
							number = rs2.getString(1);
							price = rs2.getString(2);
						}
						
						int sum = Integer.parseInt(jtb2.getText()) * Integer.parseInt(price);
						
						row.add(number);
						row.add(jtb1.getText());
						row.add(jtb2.getText());
						row.add(Integer.toString(sum));
						rowData.add(row);
						
						//총결제금액 바꾸는거
						bigsum+=sum;
						String newsum = NumberFormat.getInstance().format(bigsum);
						Eastnum.setText(newsum);
						
					 }
					 
					 jt.updateUI();
					 
					 jtb1.setText("");
					 jtb2.setText("");
					 
				 }catch(Exception ee) {
					 ee.getMessage();
				 }
			 }
		 }
	 });
	 
	 //초기화 이벤트
	 
	 reset.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			 jtb2.setText("");
		 }
	 });
	 
	 //중간 버튼 디자인
	 ejp[2].setLayout(new BorderLayout());
	 ejp[2].add(number, BorderLayout.CENTER); 
	 ejp[2].add(ebtn, BorderLayout.EAST);
	 
	 //바텀 버튼 디자인
	 ejp[3].setLayout(new GridLayout(1,2));
	 JButton pay = new JButton("결제");
	 JButton cancel = new JButton("취소");
	 
	 pay.setPreferredSize(new Dimension(50,70));
	 ejp[3].add(pay);
	 ejp[3].add(cancel);
	 
	 //결제버튼 이벤트
	 pay.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			slash slash = new slash();
			int result = JOptionPane.showConfirmDialog(null, slash, "결제자 인증",JOptionPane.OK_CANCEL_OPTION);
			
			if(result == JOptionPane.OK_OPTION) {
				
				try {
					
					 Connection con = Driver_connect.makeConnection("meal");
					 PreparedStatement psmt = con.prepareStatement("select * from member where memberNo = ?and passwd = ?");
					 
					 String id= slash.snum.getSelectedItem().toString();
					 String pwd = new String(slash.jtf.getPassword());
					 
					 psmt.setString(1, id);
					 psmt.setString(2, pwd);
					 
					 ResultSet rs = psmt.executeQuery();
					 
					 if(rs.next()) {
						 
						 JOptionPane.showMessageDialog(null, "결제가 완료되었습니다\n식권을 출력합니다", "Message", JOptionPane.INFORMATION_MESSAGE);
						 
						 //식권 폼
						 new skigun(model, id, menuname);
						 
					 }
					 else {
						 JOptionPane.showMessageDialog(null, "패스워드가 일치하지 않습니다.", "Message", JOptionPane.ERROR_MESSAGE);
					 }
					 
				}catch(Exception ee) {
					System.out.println(ee.getMessage());
				}
				
			}else {
				dispose();
			}
		 }
	 });
	 
	 
	 ejp[0].setLayout(new BorderLayout(10,10));
	 ejp[0].add(ejp[1], BorderLayout.NORTH);
	 ejp[0].add(ejp[2], BorderLayout.CENTER);
	 ejp[0].add(ejp[3],BorderLayout.SOUTH);
	 
	 bjp[2].setLayout(new GridLayout(2,1));
	 bjp[2].add(Listbox);
	 bjp[2].add(ejp[0]);
	 
	 c.add(bjp[0], BorderLayout.NORTH);
	 c.add(bjp[1], BorderLayout.WEST);
	 c.add(bjp[2], BorderLayout.EAST);
	 

	 setVisible(true);
	 setSize(1200,700);
 }
 
 //수량 이벤트
 public class btnaction implements ActionListener{
	 public void actionPerformed(ActionEvent e) {
		 String num = e.getActionCommand();
		 jtb2.setText(jtb2.getText()+ num);
	 }
 }

 //결제자 인증 
  class slash extends JPanel{
	
	 JLabel sawon = new JLabel("사원번호");
	 JLabel pwd = new JLabel("패스워드");
	 JComboBox snum = null;
	 JPasswordField jtf = new JPasswordField(10);
	 Vector <String> v = new Vector<String>();
	 slash(){	
		 
		 setLayout(new GridLayout(2,2));
		 try {
			 
			 Connection con =Driver_connect.makeConnection("meal");
			 Statement st = con.createStatement();
			 
			 String sql = "select memberNO from member";
			 
			 ResultSet rs = st.executeQuery(sql);
			
			 while(rs.next()) {
				 v.add(rs.getString("memberNO"));
			 }
			 for(int i=0; i<v.size(); i++) {
			 	snum = new JComboBox(v);
			 }
			 
		 	}catch(Exception e) {
		 		e.getMessage();
		 	}
		 
	
		 add(sawon);
		 add(snum);
		 add(pwd);
		 add(jtf);
		 
		 setSize(100,100);
		 setVisible(true);
		 
	 } 
	
 }
 
 //메뉴판 버튼 만들기
 public class button extends JButton{
	 
	 String name , price;
	 
	 button(String name){
		 
		this.name = name;
		
	 }
	 
	 button(String name , String price){
		 
		this.name = name; this.price=price;
		
		setText("<html><center>"+name+"<br><br>" + price+ "</html>");
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			JButton jbtn=	(JButton)e.getSource();
				// TODO Auto-generated method stub
				jtb1.setText(name);
				jbtn.setEnabled(false);
			}
			
		});
	 }
 }
 
}

