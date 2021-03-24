package mealUI;

import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.awt.image.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class skigun extends JFrame {
	DefaultTableModel list;
	String id;
	String menuname;
		skigun(DefaultTableModel model, String id, String menuname) {
		list = model;
		this.id = id;
		this.menuname = menuname;
		Vector<String> menu = null;
		int menucount = list.getRowCount();
		JScrollPane jsp = new JScrollPane();
		int sum = 0;
		for (int i = 0; i < menucount; i++) {
			sum += Integer.parseInt(list.getValueAt(i, 2).toString());
		}
		
		int menunum = 0;
		if (menuname.equals("한식"))
			menunum = 1;
		else if (menuname.equals("중식"))
			menunum = 2;
		else if (menuname.equals("일식"))
			menunum = 3;
		else if (menuname.equals("양식"))
			menunum = 4;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss");
		Date time = new Date();
		String time1 = format1.format(time);
		String top=time1 + "-" + id + "-" + Integer.toString(menunum);
		
		setLayout(new GridLayout(sum, 1, 5, 5));
		this.setBackground(Color.WHITE);

		// 데이터 받아오는것
		for (int i = 0; i < menucount; i++) {
			menu = new Vector<String>();

			for (int j = 1; j <= 3; j++) {
				menu.add(model.getValueAt(i, j).toString());
			}

			int fmcount = Integer.parseInt(menu.get(1).toString());
			int mcount = 1;

			Color sky = new Color(80, 188, 233);
			Color pink = new Color(0xedacb1);

			if (i % 2 == 0) {
				for (int j = 0; j < fmcount; j++) {
					add(new base(menu, id, menuname, fmcount, mcount, pink, top));
					mcount++;
				}
			} else {
				for (int j = 0; j < fmcount; j++) {
					add(new base(menu, id, menuname, fmcount, mcount, sky, top));
					mcount++;
				}
			}
		}

		setSize(400, sum * 250);
		setVisible(true);
		
		
		
		savescreenshot(this.getContentPane(),
				"C:\\Users\\sjcom2_6\\Desktop\\윤민석_meal\\식권\\ " + top+ "-ticket.jpg");
	}

	public static BufferedImage getScreenshot(Component com) {
		BufferedImage image = new BufferedImage(com.getWidth(), com.getHeight(), BufferedImage.TYPE_INT_RGB);
		com.printAll(image.getGraphics());
		return image;
	}

	public static void savescreenshot(Component com, String filename) {
		BufferedImage img = getScreenshot(com);
		try {
			ImageIO.write(img, "jpg", new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class base extends JPanel {
	JPanel panel[] = { new JPanel(), new JPanel(), new JPanel() };
	
	JLabel sikgun = new JLabel("식권");
	JLabel lbottom = new JLabel("메뉴:");
	JLabel price = new JLabel();
	JLabel won = new JLabel("원");
	JPanel lprice = new JPanel();
	JLabel lmenu = new JLabel();
	JLabel lcount = new JLabel();
	JLabel slash = new JLabel("/");
	JLabel rcount = new JLabel();
	JPanel bleft = new JPanel();
	JPanel fullcount = new JPanel();
	Vector<String> menu = new Vector<String>();
	String id, menuname;
	int fmcount = 0, mcount = 0;

	base(Vector<String> menu, String id, String menuname, int fmcount, int mcount, Color color, String top) {

		this.menu = menu;
		this.id = id;
		this.menuname = menuname;
		
		this.fmcount = fmcount;
		this.fmcount = mcount;

		String commanum = NumberFormat.getInstance().format((Integer.parseInt(menu.get(2)) / fmcount));

		for (int i = 0; i < menu.size(); i++) {
			lmenu.setText(menu.get(0).toString());

			price.setText(commanum);
		}

		setLayout(new BorderLayout());
		lprice.add(price);
		lprice.add(won);
		panel[1].setLayout(new GridLayout(2, 1));
		panel[2].setLayout(new GridLayout(1, 2));

		Font f1 = new Font("돋움", Font.BOLD, 30);
		sikgun.setFont(f1);
		price.setFont(f1);
		won.setFont(f1);

		panel[0].setLayout(new FlowLayout(FlowLayout.LEFT));
		
		panel[0].add(new JLabel(top));

		sikgun.setHorizontalAlignment(JLabel.CENTER);
		panel[1].add(sikgun);

		panel[1].add(lprice);

		bleft.add(lbottom);
		bleft.add(lmenu);
		panel[2].add(bleft);

		rcount.setText(Integer.toString(fmcount));
		lcount.setText(Integer.toString(mcount));
		fullcount.add(lcount, BorderLayout.CENTER);
		fullcount.add(slash, BorderLayout.CENTER);
		fullcount.add(rcount, BorderLayout.CENTER);
		panel[2].add(fullcount);

		
		fullcount.setBackground(color); 
		lprice.setBackground(color);
		panel[0].setBackground(color); 
		panel[1].setBackground(color);
		bleft.setBackground(color); 
		panel[2].setBackground(color);
		 
		add(panel[0], BorderLayout.NORTH);
		add(panel[1], BorderLayout.CENTER);
		add(panel[2], BorderLayout.SOUTH);
		

		// setSize(380,300);

	}

}
