package mealUI;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class main extends JFrame{
	JButton jb[] = {
			new JButton("사용자"),
			new JButton("관리자"),
			new JButton("사원등록"),
			new JButton("종료")
	};
	
 main(){
	setTitle("메인");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Container c = getContentPane();
	
	c.setLayout(new GridLayout(4,1));
	
	for(int i=0; i<jb.length; i++) {
		c.add(jb[i]);
	}
	
	jb[0].addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new user();
			dispose();
		}
	});
	
	jb[1].addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new admin();
			dispose();
		}
	});
	
	jb[2].addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new sawon();
			dispose();
		}
	});
	jb[3].addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	});
	
	
	setVisible(true);
	setSize(300,300);
 }
 public static void main(String [] args) {
	 new main();
 }
}
