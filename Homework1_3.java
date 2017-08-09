package com.ckt.homework;

import java.sql.*;
import java.io.*;

public class Homework1_3 {
	public static void main(String[] args) {
		Connection conn = null; //�]���n�b�̫�finally�����ҥH���ŧi
		String inFile; //�ɮ׸��|
		
		try {
			String connUrl = "jdbc:mysql://localhost:3306/jdbc?useSSL=false"; //��Ʈw�s�u��m
			conn = DriverManager.getConnection(connUrl, "root", "iiii");
			for (int i = 1001; i < 1009; i++) {
				inFile ="res/"+ i +".jpg"; //�ɮצW�٬����u�s��
				String qryStmt = "SELECT photo FROM employee2 WHERE empno = ?"; // ���Ʈw�����S���o���ɮ�
				PreparedStatement stmt = conn.prepareStatement(qryStmt);
				stmt.setInt(1, i);
				ResultSet rs = stmt.executeQuery();
				
				if (rs.next()) { //�Y�����ܴN�]�w��null�Ϩ�i�H���s�W��
					String deleteStmt = "UPDATE employee2 SET photo = null WHERE empno = ?"; // �����ܴN�R��
					stmt = conn.prepareStatement(deleteStmt);
					stmt.setInt(1, i); 
					stmt.executeUpdate();
					System.out.println("Delete blob is successful!");
				}

				File f = new File(inFile); //�]�w�ɮ�input�౵
				FileInputStream fis = new FileInputStream(f);
				String insertStmt = "UPDATE employee2 SET photo = ? where empno =? "; // ��Ϲ��ɮצs�J
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
