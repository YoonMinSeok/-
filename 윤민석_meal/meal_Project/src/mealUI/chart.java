package mealUI;

import javax.imageio.ImageIO;
import javax.swing.*;

import mealDB.Driver_connect;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;

public class chart extends JFrame{
	JLabel jl = new JLabel("종류별 결제 건수 통계 차트");
	String jmenu[] = {
			"한식",
			"중식",
			"일식",
			"양식"
	};
	JButton jb[] = {
			new JButton("차트이미지저장"),
			new JButton("닫기")
	};
	JPanel jp[] = {
			new JPanel(),
			new JPanel(),
			new JPanel()
	};
	Color c[] = new Color[4];
	int menu [] = new int [4]; 
	int roundarc [] = new int [4];
	double sum = 0;
	
	chart(){
		setTitle("종류별 결제현황차트");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Connection con = Driver_connect.makeConnection("meal");
		
		//파이 그래프 값 받아오는 곳
		try {
			
			Vector <String> v = new Vector<String>();
			String sql= "";
			Statement st = con.createStatement();
			
			
			for(int i=1; i <= 4; i++) {
				sql = "select sum(cuisineNo) from orderlist where cuisineNo ='" + i +"'";
				ResultSet rs = st.executeQuery(sql);
				
				while(rs.next()) {
					v.add(rs.getString(1));
				}
			}
			
			for(int i=0; i <= 3; i++) {
				menu[i] = Integer.parseInt(v.get(i));
				jmenu[i] = (jmenu[i] + "("+menu[i] + "건)");
				sum+= menu[i];
			}
			
			for(int i=0; i <= 3; i++) {
				roundarc[i] =(int) Math.round((Integer.parseInt(v.get(i))/sum *360));
			}
			
			
		}catch(SQLException e) {
			e.getMessage();
		}
		
		
		jp[0].add(jl);
		for(int i=0; i<jb.length; i++) {
			jp[0].add(jb[i]);
		}
		
		
		//JFrame에 올리기
		add(jp[0], BorderLayout.NORTH);
		add(new circle(), BorderLayout.CENTER);
		
		//action주기
		for(int i=0; i<jb.length; i++) {
			jb[i].addActionListener(new btnL());
		}
		
		setVisible(true);
		setSize(500,500);
	}
	
	class btnL implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			switch ( e.getActionCommand()) {
				case "차트이미지저장" : isSave(); break;
				case "닫기" : dispose(); break;
			}
		}
	}
	
	public void isSave() {
		Calendar cal = new GregorianCalendar();
		String clockText = Integer.toString(cal.get(Calendar.YEAR))
				 + Integer.toString((cal.get(Calendar.MONTH)+1))
				 + Integer.toString(cal.get(Calendar.DAY_OF_MONTH))
				 + Integer.toString(cal.get(Calendar.HOUR_OF_DAY))
				 + Integer.toString(cal.get(Calendar.MINUTE))
				 + Integer.toString(cal.get(Calendar.SECOND))
				 + "-종류별결제현황차트.jpg";
		
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		   paint(g2);
		   try {
			   ImageIO.write(image, "jpg", new File(clockText));
			   System.out.println("저장 되었습니다.");
		   }catch(IOException e) {
			   e.printStackTrace();
		   }
	}
	
	//파이 그래프 그리는 곳
	class circle extends JPanel{
		
		circle(){
			setLayout(null);
		}
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			int r, gg, b, startarc =0;
		
			for(int i=0; i<menu.length; i++) {
				r = (int)(Math.random() *256);
				gg = (int)(Math.random() *256);
				b = (int)(Math.random() *256);
				c[i] = new Color(r,gg,b);
				g.setColor(c[i]);
				g.fillArc(25, 50, 300, 300, startarc, roundarc[i]);
				startarc+= roundarc[i];
			}
			for(int i=0; i<menu.length; i++) {
				g.setColor(c[i]);
				g.fillRect(400, 165 + (i*20), 10, 10);
				g.setColor(Color.BLACK);
				g.drawString(jmenu[i], 415, 174+ (i*20));
			}
		}
	}
	
	
}
