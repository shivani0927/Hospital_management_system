package HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	private Connection connection;
	private Scanner scanner;
	
	public Patient(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;
	}
	
	
	public void addPatient() {
		
		
		
		 int age = 0;
		    while (true) {
		        System.out.println("Enter Patient Age: ");
		        if (scanner.hasNextInt()) {
		            age = scanner.nextInt();
		            scanner.nextLine(); // consume the newline character
		            break;
		        } else {
		            System.out.println("Invalid input. Please enter a valid integer for age.");
		            scanner.nextLine(); // consume the invalid input
		        }
		    }
		    System.out.println("Enter Patient Name: ");
		    String name = scanner.nextLine();
		
//		System.out.println("Enter Patient Age: ");
//		int age = scanner.nextInt();
		
		
		System.out.println("Enter Patient Gender: ");
		String gender = scanner.nextLine();
		
		
		
		try {
			
			String query = "insert into patient(name,age,gender) values(?,?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			
			
			pst.setString(1, name);
			pst.setInt(2, age);
			pst.setString(3, gender);
			
			int affectedRows = pst.executeUpdate();
			if(affectedRows > 0) {
				System.out.println("Patient added successfully!!");
			}else {
				System.out.println("Failed to add Patient!!");
			}
			
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
			
		}
	
	
	
	public void viewPatient() {
		String query = "select * from Patient";
		
		try {
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet resultSet = pst.executeQuery();
			
			System.out.println("Patients: ");
			System.out.println("+------------+------------+-------+------------+");
	        System.out.println("| Patient Id | Name       | Age   | Gender     |");
	        System.out.println("+------------+------------+-------+------------+");
			
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				String gender = resultSet.getString("gender");
				 System.out.printf("| %-10d | %-10s | %-5d | %-10s |\n", id, name, age, gender);
	             System.out.println("+------------+------------+-------+------------+");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean getPatientById(int id) {
		String query = "select * from Patient where id = ?";
		
		try {
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, id);
			
//			ResultSet resultSet = pst.executeQuery();
//			while(resultSet.next()) {
//				return true;
//			}return false;
//			return resultSet.next();
			try (ResultSet resultSet = pst.executeQuery()) {
                return resultSet.next();
            }
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
}
