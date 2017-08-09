package com.ckt.homework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

// Insert one employee
public class Homework1_2 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		Connection conn = null;
		String str;
		int count = 0;

		try {
			FileReader fr = new FileReader("res/emp.txt");
			BufferedReader br = new BufferedReader(fr);
			String connUrl = "jdbc:mysql://localhost:3306/jdbc?useSSL=false";
			conn = DriverManager.getConnection(connUrl, "root", "iiii");
			String insStmt = "INSERT INTO employee VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(insStmt);
			while ((str = br.readLine()) != null) {
				String strArray[] = str.split(", ");
				pstmt.setInt(1, Integer.parseInt(strArray[0]));
				pstmt.setString(2, strArray[1]);
				pstmt.setString(3, strArray[2]);
				pstmt.setDouble(4, Double.parseDouble(strArray[3]));
				pstmt.setInt(5, Integer.parseInt(strArray[4]));
				pstmt.setString(6, strArray[5]);
				pstmt.addBatch();
				count++;
				if (count == 3)
					pstmt.executeBatch();
			}
			pstmt.executeBatch();
			pstmt = conn.prepareStatement("SELECT * FROM employee");
			ResultSet rs = pstmt.executeQuery();
			br.close();
			fr.close();
			while (rs.next()) {
				System.out.print("empno = " + rs.getString("empno") + ", ");
				System.out.print("ename = " + rs.getString("ename") + ", ");
				System.out.println("salary = " + rs.getDouble("salary"));
			}
		} // end of try
		catch (SQLException e) {
			e.printStackTrace();
			// System.out.println("資料庫有問題");
		} // end of catch SQLException
		catch (IOException f) {
			System.out.println("檔案輸入有問題");
		} // end of catch IOException
		finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		} // end of finally
	}// end of main()
}// end of class InsertDemo
