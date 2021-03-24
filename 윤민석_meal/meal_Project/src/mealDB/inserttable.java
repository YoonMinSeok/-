package mealDB;

import java.sql.*;
import java.util.*;
import java.io.*;

public class inserttable {
	inserttable(){
		Connection con = Driver_connect.makeConnection("meal");
		PreparedStatement psmt = null;
		
		String text [] = {
				"insert into member values (?,?,?)",
				"insert into cuisine values (?,?)",
				"insert into meal values(?,?,?,?,?,?)",
				"insert into orderlist values(?,?,?,?,?,?,?)"
		};
		
		String name [] = {
				"member", "cuisine", "meal", "orderlist"
		};
		
		for(int i = 0; i<name.length; i++) {
			try {
				Scanner scanner = new Scanner(new FileInputStream("DataFiles\\"+name[i]+ ".txt"));
				StringTokenizer stt = new StringTokenizer(scanner.nextLine(),"\t");
				
				String line[] = new String[stt.countTokens()];
				psmt = con.prepareStatement(text[i]);
				
				while(scanner.hasNext()) {
					stt = new StringTokenizer(scanner.nextLine(), "\t");
					for(int j =0; j<line.length; j++) {
						line[j] = stt.nextToken();
						psmt.setString(j+1, line[j]);
					}
					psmt.executeUpdate();
				}
				
			}catch(IOException e) {
				System.out.println("파일 오류!!!");
			}catch(SQLException e) {
				System.out.println("SQL 오류!!!");
			}
		}
	}
 public static void main(String [] args) { 
	 new meal_dbcreate();
	 new createtable();
	 new inserttable();
 }
}
