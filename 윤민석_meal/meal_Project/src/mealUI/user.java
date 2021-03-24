package mealUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class user extends JFrame{
	JPanel jp[] = {
			new JPanel(),
			new JPanel(),
			new JPanel()
	};
	String [] menu ={
		"한식", "중식", "일식", "양식"
	};
	JLabel jl = new JLabel("식권 발매 프로그램");
	JLabel jl2 = new JLabel();
	ImageIcon img1 = new ImageIcon("C:\\Users\\sjcom2_6\\Desktop\\윤민석_meal\\meal_Project\\DataFiles\\menu_1.png");
	ImageIcon img2 = new ImageIcon("C:\\Users\\sjcom2_6\\Desktop\\윤민석_meal\\meal_Project\\DataFiles\\menu_2.png");
	ImageIcon img3 = new ImageIcon("C:\\Users\\sjcom2_6\\Desktop\\윤민석_meal\\meal_Project\\DataFiles\\menu_3.png");
	ImageIcon img4 = new ImageIcon("C:\\Users\\sjcom2_6\\Desktop\\윤민석_meal\\meal_Project\\DataFiles\\menu_4.png");
	
	JTabbedPane t = new JTabbedPane();
	String clock;
	
	JButton jb[] = {
			new JButton(img1),
			new JButton(img2),
			new JButton(img3),
			new JButton(img4)
	};

	 user(){
		 setTitle("식권 발매 프로그램");
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 Container c = getContentPane();
		 
		 jp[0].add(jl);
		 jp[1].setLayout(new GridLayout(2,2));
		 
		 
		 int sum=0;
		 for(int i=0; i<jb.length; i++) {
			
			 jp[1].add(jb[i]);
			 jb[i].setToolTipText(menu[i]);
			 
		 }
		 
		 for(int i=0; i<jb.length; i++) {
			 jb[i].addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent e) {
					JButton aa = (JButton)e.getSource();
					
					if(aa == jb[0]) {
						new korean("한식");
					}else if(aa == jb[1]) {
						new korean("중식");
					}else if(aa == jb[2]) {
						new korean("일식");
					}else if(aa == jb[3]) {
						new korean("양식");
					}
				 }
			 });
		 }
		 
		 jb[0].setToolTipText("한식");
		 jp[2].add(jl2);
		 jp[2].setBackground(Color.black);
		 jl2.setForeground(Color.BLUE);
		 
		 time ti = new time();
		 
		 t.add("메뉴",jp[1]);
		 c.add(jp[0], BorderLayout.NORTH);
		 c.add(t, BorderLayout.CENTER);
		 c.add(jp[2], BorderLayout.SOUTH);
		 
		 Font font = new Font("돋움", Font.BOLD, 20);
		
		jl2.setFont(font);
		jl2.setText(timer());
		 setVisible(true);
		 setSize(500,700); 
		 ti.start();
	 }
	 
class time extends Thread{
	
	public void run() {
		while(true) {
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jl2.setText(timer());
		}
	}
}
	
public String timer(){
	Calendar c = Calendar.getInstance();
	int year = c.get(Calendar.YEAR);
	int month = c.get(Calendar.MONTH)+1;
	int day = c.get(Calendar.DAY_OF_MONTH);
	int hour =c.get(Calendar.HOUR_OF_DAY);
	int min =c.get(Calendar.MINUTE);
	int second =c.get(Calendar.SECOND);
	
	 clock ="현재시간 : " +Integer.toString(year) + "년" + Integer.toString(month) +"월" + Integer.toString(day) +"일" +Integer.toString(hour) +"시" + Integer.toString(min) +"분" +Integer.toString(second) + "초";
	
	return clock;
}

}
