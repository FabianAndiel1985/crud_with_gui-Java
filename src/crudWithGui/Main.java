package crudWithGui;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

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
import javax.swing.table.TableColumn;



public class Main {
	
	public static void main(String[] args) {
		
		JFrame frame=new JFrame();
		frame.setSize(510,510);
		frame.setLayout(new FlowLayout());
		JButton b=new JButton("Enter new person");
		b.setBounds(130,100,100, 40);  
		frame.add(b);
		
		JButton updateButton=new JButton("Update");
		b.setBounds(130,100,100, 40);  
		frame.add(updateButton);
		
		JFrame frameWithForm = new JFrame();
		frameWithForm.setSize(200,470);
		frameWithForm.setResizable(false);
		frameWithForm.setLayout(new FlowLayout());
		
		b.addActionListener((e)->{
			frame.setVisible(false);
			JPanel mainPanel = createMainPanel( frame, frameWithForm,Optional.ofNullable(null));
			frameWithForm.getContentPane().add(mainPanel);
			frameWithForm.setVisible(true);
		});
		
		String[] columnNames = {"id","Firstname", "Lastname","Street","Housenumber","Doornumber","Zip","City","Email"};
	        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0){
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };

	    JTable table = new JTable(tableModel);
		
		updateButton.addActionListener(
				(e)->{
					int selectedRow = table.getSelectedRow();
					if(selectedRow != -1) {
						frame.setVisible(false);
						String id = table.getModel().getValueAt(selectedRow, 0).toString();
						
						Person createdPerson = createPersonFromRow(table, selectedRow);
						
						JPanel mainPanel = createMainPanel( frame, frameWithForm,Optional.ofNullable(createdPerson));
						frameWithForm.getContentPane().add(mainPanel);
						frameWithForm.setVisible(true);	
					}
				}
		);
		
		
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
		
	        TableColumn idColumn = table.getColumnModel().getColumn(0);
	        table.getColumnModel().removeColumn(idColumn);

	        JScrollPane scrollPane = new JScrollPane(table);
	        frame.add(scrollPane);
	        
	        addRecordsIntoTable(persons,tableModel);
	        
	        frame.setVisible(true);
	}
	
	
	
	
	
	private static JPanel createMainPanel ( JFrame frame, JFrame frameWithForm, Optional<Person> person) {
		
		JPanel mainPanel = new JPanel();
	 
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	    mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		JLabel firstNameLabel = createLabel("Firstname");
	    JLabel lastNameLabel = createLabel("Lastnname");
        JLabel streetLabel = createLabel("Street");
        JLabel housenumberLabel = createLabel("Housenumber");
        JLabel doornumberLabel = createLabel("Doornumber");
        JLabel zipLabel = createLabel("Zip");
        JLabel cityLabel = createLabel("City");
        JLabel emailLabel = createLabel("Email");
        
		JTextField firstNameTextField = createTextField();
        JTextField lastNameTextField = createTextField();
        JTextField streetTextField = createTextField();
        JTextField housenumberField = createTextField();
        JTextField doornumberField = createTextField();
        JTextField zipField = createTextField();
        JTextField cityField = createTextField();
        JTextField emailField = createTextField();
        
        if (person.isPresent()) {	
        	Person personToUpdate = person.get();
        	firstNameTextField.setText(personToUpdate.getFirst_name());
        	lastNameTextField.setText(personToUpdate.getLast_name());
        	streetTextField.setText(personToUpdate.getStreet());
        	housenumberField.setText(personToUpdate.getHousenumber());
        	doornumberField.setText(personToUpdate.getDoornumber());
        	zipField.setText(personToUpdate.getZip());
        	cityField.setText(personToUpdate.getCity());
        	emailField.setText(personToUpdate.getEmail());
        }
        
        
        
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
		
	
		backButton.addActionListener((e)->{
			frameWithForm.setVisible(false);
			frame.setVisible(true);
		});
		
		
		submitButton.addActionListener((e)->{
			
			String firstname = firstNameTextField.getText();
			String lastname = lastNameTextField.getText();
			String street = streetTextField.getText();
			String housenumber = housenumberField.getText();
			String doornumber = doornumberField.getText();
			String zip = zipField.getText();
			String city = cityField.getText();
			String email = emailField.getText();
			
			
			
			if(person.isPresent()) {
				
				Person tempPerson = new Person(firstname,lastname,street,housenumber,doornumber,zip,city,email);
				
					if(person.get().continueEqualityCheck(tempPerson)) {
						ArrayList<String> differentFields = person.get().getDifferentFields(tempPerson); 
						if(differentFields.size()>0 ) {
							String query = createUpdateQuery(differentFields);
//							 System.out.println(query);
//							 System.out.println(person.get().getId());
							
							System.out.println("Size of different fields " + differentFields.size());
							try (var conn = DBconnection.getConnection();
									PreparedStatement preparedStatement = conn.prepareStatement(query)) {
										
									for( int i= 1;i <= differentFields.size(); i++) {
										
										if(differentFields.size() == 1) {
											preparedStatement.setString(i, getFieldValue(tempPerson, differentFields.get(i-1)));	
											preparedStatement.setInt(i+1, person.get().getId()); 
										}
										if (i != differentFields.size() && differentFields.size() > 1  ) {
											preparedStatement.setString(i, getFieldValue(tempPerson, differentFields.get(i-1)));	
										}
										if( i == differentFields.size() && differentFields.size() > 1 ) {
											preparedStatement.setString(i, getFieldValue(tempPerson, differentFields.get(i-1)));
											preparedStatement.setInt(i+1, person.get().getId());
										}
									
									}
											
									int rows = preparedStatement.executeUpdate();
									
								} catch (SQLException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						}
					}
						
			}
			
			else {
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
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			});
		
		return mainPanel;
	}
	
	

	private static String getFieldValue(Person person,String fieldName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		   Field field = Person.class.getDeclaredField(fieldName);
           field.setAccessible(true);  // Bypass private modifier
           return (String) field.get(person);
	}





	private static String createUpdateQuery(ArrayList<String> differentFields) {
		
	
	
		String basicStatement = "UPDATE person SET ";
		String endBasicStatement = " WHERE id = ?";
		
		for( int i=0; i< differentFields.size(); i++) {
				basicStatement += differentFields.get(i)+ " = ?";
				if(i != differentFields.size()-1) {
					basicStatement += ", ";
				}
		}
		
		return basicStatement.concat(endBasicStatement);
	}





	private static JTextField createTextField(){
		JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(60,20));
		return textField;
	}
	
	private static Person createPersonFromRow(JTable table,int selectedRow){
		Integer id = Integer.parseInt(table.getModel().getValueAt(selectedRow, 0).toString());
		String firstName = table.getValueAt(selectedRow, 0).toString();
		String lastName = table.getValueAt(selectedRow, 1).toString();
		String street = table.getValueAt(selectedRow, 2).toString();
		String housenumber = table.getValueAt(selectedRow, 3).toString();
		String doornumber = table.getValueAt(selectedRow, 4).toString();
		String zip = table.getValueAt(selectedRow, 5).toString();
		String city = table.getValueAt(selectedRow, 6).toString();
		String email = table.getValueAt(selectedRow, 7).toString();
		
		return new Person(id,firstName,lastName,street,housenumber,doornumber,zip,city,email);
	}
	
	private static JLabel createLabel (String name) {
		 JLabel label = new JLabel(name);
	     label.setBounds(50, 50, 300, 50);
	     label.setFont(new Font("Arial", Font.PLAIN, 14)); 
	     return label;
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
}
