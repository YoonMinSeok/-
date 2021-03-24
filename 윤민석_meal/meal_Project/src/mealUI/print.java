package mealUI;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.util.*;

public class print extends JFrame{

	print(){
	
		JTextPane jtextpane = new JTextPane();
		jtextpane.setEditorKit(new HTMLEditorKit());
		try {
			jtextpane.setContentType("text/html");
			
			boolean done = jtextpane.print();
			if(done){
				JOptionPane.showMessageDialog(null, "Printing is done");
			}else {
				JOptionPane.showMessageDialog(null, "Error while printing");
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error while printing");
			e.printStackTrace();
		}
		
		setVisible(false);
		setSize(500,500);
	}
	
}
