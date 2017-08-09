package com.ckt.homework;

import java.sql.*;
import java.io.*;

public class Homework1_3 {
	public static void main(String[] args) {
		Connection conn = null; //因為要在最後finally關閉所以先宣告
		String inFile; //檔案路徑
		
		try {
			String connUrl = "jdbc:mysql://localhost:3306/jdbc?useSSL=false"; //資料庫連線位置
			conn = DriverManager.getConnection(connUrl, "root", "iiii");
			for (int i = 1001; i < 1009; i++) {
				inFile ="res/"+ i +".jpg"; //檔案名稱為員工編號
				String qryStmt = "SELECT photo FROM employee2 WHERE empno = ?"; // 找資料庫內有沒有這個檔案
				PreparedStatement stmt = conn.prepareStatement(qryStmt);
				stmt.setInt(1, i);
				ResultSet rs = stmt.executeQuery();
				
				if (rs.next()) { //若有的話就設定為null使其可以重新上傳
					String deleteStmt = "UPDATE employee2 SET photo = null WHERE empno = ?"; // 有的話就刪掉
					stmt = conn.prepareStatement(deleteStmt);
					stmt.setInt(1, i); 
					stmt.executeUpdate();
					System.out.println("Delete blob is successful!");
				}

				File f = new File(inFile); //設定檔案input轉接
				FileInputStream fis = new FileInputStream(f);
				String insertStmt = "UPDATE employee2 SET photo = ? where empno =? "; // 把圖像檔案存入
				stmt = conn.prepareStatement(insertStmt);
				stmt.setInt(2, i);
				stmt.setBinaryStream(1, fis, f.length());
				stmt.executeUpdate();
				System.out.println("Insert blob is successful!");
			}
			
		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}// end of main()
}// end of class BLOBDemo
