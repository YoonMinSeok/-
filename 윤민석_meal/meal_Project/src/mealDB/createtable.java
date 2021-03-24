package mealDB;

import java.sql.*;

public class createtable {
	createtable(){
		try {
			
			Connection con = Driver_connect.makeConnection("meal");
			Statement st = con.createStatement();
			
			String member = "create table if not exists member(memberNO int not null auto_increment primary key, memberName varchar(20), passwd varchar(4))";
			String cuisine = "create table if not exists cuisine(cuisineNo int primary key not null auto_increment , cuisineName varchar(10))";
			String meal = "create table if not exists meal(mealNo int primary key not null auto_increment, cuisineNo int , mealName varchar(20), price int , maxCount int , todayMeal tinyint(1))";
			String orderlist = "create table if not exists orderlist(orderNo int primary key not null auto_increment , cuisineNo int , mealNo int , memberNo int , orderCount int , amount int , orderDate datetime)";
			
			st.executeUpdate(member);
			st.executeUpdate(cuisine);
			st.executeUpdate(meal);
			st.executeUpdate(orderlist);
			
		}catch(SQLException e) {
			System.out.println("SQL 오류!!");
		}
		
	}

}
