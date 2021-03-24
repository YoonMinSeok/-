package mealUI;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class management extends JFrame{
	JButton jb [] = {
			new JButton("메뉴 등록"),
			new JButton("메뉴 관리"),
			new JButton("결제 조회"),
			new JButton("종류별 차트"),
			new JButton("종 료")
	};
	JPanel jp[] = {
			new JPanel(),
			new JPanel()
	};
	
	ImageIcon img = new ImageIcon("C:\\Users\\sjcom2_6\\Desktop\\윤민석_meal\\meal_Project\\DataFiles\\main.jpg");
	JLabel imglabel = new JLabel(img);
	
	management(){
		setTitle("관리");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		for(int i=0; i<jb.length; i++) {
			jp[0].add(jb[i]);
		}
		
		for(int i=0; i<jb.length; i++) {
			jb[i].addActionListener(new btnAction());
		}
		
		
		jp[1].add(imglabel);
		
		add(jp[0], BorderLayout.NORTH);
		add(jp[1], BorderLayout.CENTER);
		
		
		
		setSize(600,470);
		setVisible(true);
	}
	
	class btnAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String st = e.getActionCommand();
			
			switch(st) {
			case "메뉴 등록" : 
				new Registration();
				break;
			case "메뉴 관리" :
				new mmanagement();
				break;
			case "결제 조회":
				new payment();
				break;
			case "종류별 차트" :
				new chart();
				break;
			case "종 료":
				dispose();
				break;
			}
			
			
		}
		
	}
}
