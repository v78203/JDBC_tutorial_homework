package com.ckt.homework;

import java.sql.*;

public class Homework1_1 {
	public static void main(String[] args) {
		Connection conn = null;
		try {     
			String connUrl = "jdbc:mysql://localhost:3306/jdbc?useSSL=false";
			conn = DriverManager.getConnection(connUrl, "root", "iiii");
			
			String qryStmt = "SELECT empno, salary FROM employee";
			PreparedStatement pstmt = conn.prepareStatement(qryStmt);
			ResultSet rs = pstmt.executeQuery();
			
			String updateStmt = "UPDATE employee SET salary = ? WHERE empno = ?";
			pstmt = conn.prepareStatement(updateStmt);
			int batch_size = 4; int count = 0;
			while (rs.next()) {
				pstmt.setDouble(1, rs.getDouble(2) * 1.1);
				pstmt.setInt(2, rs.getInt(1));
				pstmt.addBatch();
				count++;
				if (count == batch_size){
					pstmt.executeBatch();
					count = 0;
				}
			}
			pstmt.executeBatch();
		
			qryStmt = "SELECT ename, salary FROM employee";
			pstmt = conn.prepareStatement(qryStmt);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println("name = " + rs.getString("ename"));
				System.out.println("salary = " + rs.getDouble("salary"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
		}
	}// end of main()
}// end of class BatchUpdateDemo
