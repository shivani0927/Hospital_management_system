package HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Doctor {
	private Connection connection;

	
	public Doctor(Connection connection) {
		this.connection = connection;
	}
	
	
	
	
	public void viewDoctor() {
		String query = "select * from Doctor";
		
		try {
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet resultSet = pst.executeQuery();
			
			System.out.println("Doctors: ");
			System.out.println("+------------+------------+-----------------+");
			System.out.println("| Doctor Id  | Name       | specialization  |");
			System.out.println("+------------+------------+-----------------+");
			
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String specialization = resultSet.getString("specialization");
	
				System.out.printf("| %-10d | %-10s | %-15s |\n", id, name , specialization);
				System.out.println("+------------+------------+-----------------+");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getDoctortById(int id) {
		String query = "select * from Doctor where id = ?";
		
		try {
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, id);
			
			try(ResultSet resultSet = pst.executeQuery()){
				 return resultSet.next();
			}
//			while(resultSet.next()) {
//				return true;
//			}return false;
//			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
}
