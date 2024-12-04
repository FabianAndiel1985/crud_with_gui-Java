package crudWithGui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

public class FrameService {
	
	
private static ArrayList<String> createPerson(ResultSet rs) throws SQLException {
		
		ArrayList<String> person = new ArrayList<>();
		person.add(rs.getString("id"));
		person.add(rs.getString("first_name"));
		person.add(rs.getString("last_name"));
		person.add(rs.getString("street"));
		person.add(rs.getString("housenumber"));
		person.add(rs.getString("doornumber"));
		person.add(rs.getString("zip"));
		person.add(rs.getString("city"));
		person.add(rs.getString("email"));
		return person;

	}


public static ArrayList<ArrayList<String>> getPersonsFromDataBase () {
	
	String query = "SELECT * FROM person";
	
	ArrayList<ArrayList<String>> persons = new ArrayList<>();
	
	try (var conn = DBconnection.getConnection();
			Statement statement = conn.createStatement()) {
		try (ResultSet rs = statement.executeQuery(query)) {
			while (rs.next()) {					
				persons.add(FrameService.createPerson(rs));
			}
		};
	}
	catch(Exception e) {	
	}
	return persons;
}

public static void addRecordsIntoTable(ArrayList<ArrayList<String>> persons,DefaultTableModel tableModel) {
	int index = 0;
	for (ArrayList<String> element: persons) {
	tableModel.addRow(persons.get(index).toArray());
	index++;
	}	
}

	public static void exportToFile(ArrayList<ArrayList<String>> persons) {
		//creates the path object representing a path
		Path folderPath = Paths.get("src/exports");
		//creates whole path for the file
		Path pathOfFile = folderPath.resolve("persons.txt");
		
		try {
			if(!Files.exists(folderPath)) {
			  Files.createDirectory(folderPath);
			}
			if(!Files.exists(pathOfFile)) {
				Files.createFile(pathOfFile);	
			}
			Files.write(pathOfFile,toBytes(persons));
		}
		catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
	}
	
	
	public static void importFromFile() {
		//creates the path object representing a path
		Path folderPath = Paths.get("src/import");
		//creates whole path for the file
		Path pathOfFile = folderPath.resolve("import.txt");
		
		try {
			if(!Files.exists(folderPath)) {
				throw new Exception("The required import folder doesn't exist");
			}
			if(!Files.exists(pathOfFile)) {
				throw new Exception("The required import txt file doesn't exist");
			}
			
			byte[] fileBytes = Files.readAllBytes(pathOfFile);
			
			
			
			 try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(fileBytes))) {
	    
	                ArrayList<ArrayList<String>> persons = (ArrayList<ArrayList<String>>) ois.readObject();
	                
	                for (ArrayList<String> person : persons) {
	                    System.out.println(person);
	                }
	            }
	         catch (IOException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
		
		}
		catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
	}
	
	
	
	private static byte[] toBytes(ArrayList<ArrayList<String>> list) throws IOException {
	        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

	        // ObjectOutputStream writes the persons to the Byte Array Output Stream
	        try (ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)) {
	            objectStream.writeObject(list); 
	        }
	        //muss man so machen weil der byteStream kein Array ist aber man nur zum stream schreiben kann,.
	        return byteStream.toByteArray();
	    }
	
	


	public static void deleteSelectedRow (String id) {
		String query = "DELETE FROM person WHERE id = ?";
		
		try (var conn = DBconnection.getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(query)) {
				preparedStatement.setString(1, id);
				int rows = preparedStatement.executeUpdate();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		
	}


	public static JButton createButton(String name) {
		JButton button=new JButton(name);
		button.setBounds(130,100,100, 40);  
		return button;
	}

}
