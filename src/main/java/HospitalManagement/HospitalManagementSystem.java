package HospitalManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;

public class HospitalManagementSystem {
	
	private static final String url = "jdbc:mysql://localhost:3306/hospital";
	
	private static final String username = "root";
	
	private static final String password = "#learning@";
	
	

	public static void main(String[] args) {
		 try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 
		 }catch (ClassNotFoundException e) {
			 e.printStackTrace();
		 }
		 
		 
		 Scanner sc = new Scanner(System.in);
		 try {
			 Connection connection = DriverManager.getConnection(url,username,password);
			 Patient patient = new Patient(connection,sc);
			 Doctor doctor = new Doctor(connection);			 
			 while(true) {
				 System.out.println("HOSPITAL MANAGEMENT SYSTEM");
				 System.out.println("1.Add Patient");
				 System.out.println("2. View Patient");
				 System.out.println("3. View Doctor");
				 System.out.println("4. Book Appointment");
				 System.out.println("5.Exit");
				 
				 System.out.println("Enter your choice: ");
				 int choice = sc.nextInt();
				 
				 switch(choice) {
				 case 1: 
					 //add Patient
					 patient.addPatient();
					 System.out.println();
					 break;
				 case 2:
					 //view Patient
					 patient.viewPatient();
					 System.out.println();
					 break;
				 case 3:
					 //view Doctors
					 doctor.viewDoctor();
					 System.out.println();
					 break;
				 case 4:
					 //book Appointment
					 bookAppointment(patient, doctor, connection, sc);
					 System.out.println();
					 break;
					 
				 case 5:
					 System.out.println("Thank You for using Hospital Management System!!");
					 return;
					 
				default:
					System.out.println("Enter valid choice !!");
					
				
				 }
				 
				 
			 }
			 
		 }catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner){
		
		System.out.println("Enter Patient ID: ");
		int patientid = scanner.nextInt();
		scanner.nextLine();
		
		System.out.println("Enter Doctor ID: ");
		int doctorid = scanner.nextInt();
		scanner.nextLine();
		
		System.out.println("Enter Appointment date(YYYY-MM-DD): ");
		String appointmentDate = scanner.nextLine();
		
		if(patient.getPatientById(patientid) && doctor.getDoctortById(doctorid)) {
			if(checkDoctorAvailability(doctorid,appointmentDate,connection)) {
				String appointmentQuery = "insert into appointment(patient_id, doctor_id, appointment_date) values(?,?,?)";
				
				try {
					PreparedStatement pst = connection.prepareStatement(appointmentQuery);
					pst.setInt(1, patientid);
					pst.setInt(2, doctorid);
					pst.setString(3, appointmentDate);
					
					int affectedRows = pst.executeUpdate();
					if(affectedRows > 0) {
						System.out.println("Appointment Booked!!");
					}else {
						System.out.println("Failed to book Appointment!!");
					}
					
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}else {
				System.out.println("Doctor is not available on this date!!");
			}
		}else {
			System.out.println("Either Doctor or Patient doesn`t exist!!");
		}
	}
	
	public static boolean checkDoctorAvailability(int doctorid, String appointmentDate, Connection connection) {
		
		String query = "select count(*) from appointment where doctor_id = ? and appointment_date = ?";
		
		try {
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, doctorid);
			pst.setString(2, appointmentDate);
			
			try(ResultSet resultSet = pst.executeQuery()){
				if(resultSet.next()) {
					int count = resultSet.getInt(1);
					return count == 0;
				}
			}
			
//			int affectedRows = pst.executeUpdate();
//			if(affectedRows > 0) {
//				System.out.println("Appointment Booked!!");
//			}else {
//				System.out.println("Failed to book Appointment!!");
//			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}

}

