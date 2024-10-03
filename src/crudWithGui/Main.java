package crudWithGui;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.border.EmptyBorder;

import javax.swing.table.DefaultTableModel;



public class Main {

	public static void main(String[] args) {
		
		JFrame frame=new JFrame();
		frame.setSize(510,510);
		frame.setLayout(new FlowLayout());
		JButton b=new JButton("Enter new person");
		b.setBounds(130,100,100, 40);  
		frame.add(b);
		

		JFrame frameWithForm = new JFrame();
		frameWithForm.setSize(200,470);
		frameWithForm.setResizable(false);
		frameWithForm.setLayout(new FlowLayout());
		
		
		JPanel mainPanel = new JPanel();
	 
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	    mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // 20px padding on all sides
	     
	    
		JLabel firstNameLabel = new JLabel("Firstname");
		firstNameLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font to bold and size to 24
		
		JTextField firstNameTextField = new JTextField();
		firstNameTextField.setPreferredSize(new Dimension(60,20));
        
        JLabel lastNameLabel = new JLabel("Lastnname");
        lastNameLabel.setBounds(50, 50, 300, 50); // Set the position and size of the label
        lastNameLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font to bold and size to 24
		
        JTextField lastNameTextField = new JTextField();
        lastNameTextField.setPreferredSize(new Dimension(60,20));
        
        JLabel streetLabel = new JLabel("Street");
        streetLabel.setBounds(50, 50, 300, 50); // Set the position and size of the label
        streetLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font to bold and size to 24
		
        JTextField streetTextField = new JTextField();
        streetTextField.setPreferredSize(new Dimension(60,20));
        
        JLabel housenumberLabel = new JLabel("Housenumber");
        housenumberLabel.setBounds(50, 50, 300, 50); // Set the position and size of the label
        housenumberLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font to bold and size to 24
		
        JTextField housenumberField = new JTextField();
        housenumberField.setPreferredSize(new Dimension(60,20));
        
        JLabel doornumberLabel = new JLabel("Doornumber");
        doornumberLabel.setBounds(50, 50, 300, 50); // Set the position and size of the label
        doornumberLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font to bold and size to 24
		
        JTextField doornumberField = new JTextField();
        doornumberField.setPreferredSize(new Dimension(60,20));
        
        JLabel zipLabel = new JLabel("Zip");
        zipLabel.setBounds(50, 50, 300, 50); // Set the position and size of the label
        zipLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font to bold and size to 24
		
        JTextField zipField = new JTextField();
        zipField.setPreferredSize(new Dimension(60,20));
        
        JLabel cityLabel = new JLabel("City");
        cityLabel.setBounds(50, 50, 300, 50); // Set the position and size of the label
        cityLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font to bold and size to 24
		
        JTextField cityField = new JTextField();
        cityField.setPreferredSize(new Dimension(60,20));
        
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(50, 50, 300, 50); // Set the position and size of the label
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font to bold and size to 24
		
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(60,20));
        
        JPanel submitButtonPanel = new JPanel();
   	 
        submitButtonPanel.setLayout(new BoxLayout(submitButtonPanel, BoxLayout.Y_AXIS));
        submitButtonPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // 20px padding on all sides
        
        JButton submitButton = new JButton("Eintragen");
        submitButtonPanel.add(submitButton);
        
        JPanel backButtonPanel = new JPanel();
      	 
        backButtonPanel.setLayout(new BoxLayout(backButtonPanel, BoxLayout.Y_AXIS));
        backButtonPanel.setBorder(new EmptyBorder(10, 20, 20, 10)); // 20px padding on all sides
        
        JButton backButton = new JButton("Zurück");
        backButtonPanel.add(backButton);
        
        
      
		mainPanel.add(firstNameLabel);
		mainPanel.add(firstNameTextField);
		mainPanel.add(lastNameLabel);
		mainPanel.add(lastNameTextField);
		mainPanel.add(streetLabel);
		mainPanel.add(streetTextField);
		mainPanel.add(housenumberLabel);
		mainPanel.add(housenumberField);
		mainPanel.add(doornumberLabel);
		mainPanel.add(doornumberField);
		mainPanel.add(zipLabel);
		mainPanel.add(zipField);
		mainPanel.add(cityLabel);
		mainPanel.add(cityField);
		mainPanel.add(emailLabel);
		mainPanel.add(emailField);
		mainPanel.add(submitButtonPanel);
		mainPanel.add(backButtonPanel);
		
		
		
		frameWithForm.getContentPane().add(mainPanel);
		
		
		b.addActionListener((e)->{
			frame.setVisible(false);
			frameWithForm.setVisible(true);
		});
		
		backButton.addActionListener((e)->{
			frameWithForm.setVisible(false);
			frame.setVisible(true);
		});
		
		submitButton.addActionListener((e)->{
			
			String firstname = firstNameTextField.getText();
			String lastname = firstNameTextField.getText();
			String street = streetTextField.getText();
			String housenumber = housenumberField.getText();
			String doornumber = doornumberField.getText();
			String zip = zipField.getText();
			String city = cityField.getText();
			String email = emailField.getText();
			
			String query = "INSERT INTO person (first_name, last_name, street, housenumber, doornumber, zip, city, email) VALUES (?, ?, ?, ?,?,?,?,?)";
			
			try (var conn = DBconnection.getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(query)) {
				preparedStatement.setString(1, firstname);
				preparedStatement.setString(2, lastname);
				preparedStatement.setString(3, street);
				preparedStatement.setString(4, housenumber);
				preparedStatement.setString(5, doornumber);
				preparedStatement.setString(6, zip);
				preparedStatement.setString(7, city);
				preparedStatement.setString(8, email);
			
				int rows = preparedStatement.executeUpdate();
				System.out.println(rows);
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			})
		
		;
		
	
		
		String query = "SELECT * FROM person";
		
		ArrayList<ArrayList<String>> persons = new ArrayList<>();
		
		try (var conn = DBconnection.getConnection();
				Statement statement = conn.createStatement()) {
			try (ResultSet rs = statement.executeQuery(query)) {
				while (rs.next()) {					
					persons.add(createPerson(rs));
				}
			};
		}
		catch(Exception e) {	
		}
		
	        String[] columnNames = { "Firstname", "Lastname","Street","Housenumber","Doornumber","Zip","City","Email"};
	        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0){
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };

	        JTable table = new JTable(tableModel);

	        JScrollPane scrollPane = new JScrollPane(table);
	        frame.add(scrollPane);
	        
	        addRecordsIntoTable(persons,tableModel);
	        
	        frame.setVisible(true);
		
	}
	
	
	
	
	private static void addRecordsIntoTable(ArrayList<ArrayList<String>> persons,DefaultTableModel tableModel) {
		int index = 0;
		for (Object element: persons) {
		tableModel.addRow(persons.get(index).toArray());
		index++;
		}	
		
	}
		
	private static ArrayList<String> createPerson(ResultSet rs) throws SQLException {
		
		ArrayList<String> person = new ArrayList<>();
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
}
