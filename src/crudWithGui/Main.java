package crudWithGui;

import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.TableView;


public class Main {

	public static void main(String[] args) {
		
		JFrame frame=new JFrame();
        
		JButton b=new JButton("click");
		b.setBounds(130,100,100, 40);  
	
		          
		frame.add(b);
		          
		frame.setSize(400,500);
		frame.setLayout(new FlowLayout());
		frame.setVisible(true);  
			
		String query = "SELECT * FROM person";
		
		ArrayList<ArrayList<Object>> persons = new ArrayList<>();
		
		try (var conn = DBconnection.getConnection();
				Statement statement = conn.createStatement()) {
			try (ResultSet rs = statement.executeQuery(query)) {
				while (rs.next()) {
					ArrayList<Object> person = new ArrayList<>();
					person.add(rs.getString("first_name"));
					person.add(rs.getString("last_name"));
					person.add(rs.getString("street"));
					person.add(rs.getInt("housenumber"));
					person.add(rs.getInt("doornumber"));
					person.add(rs.getString("zip"));
					person.add(rs.getString("city"));
					person.add(rs.getString("email"));
					
					persons.add(person);

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
	        
	        int index = 0;

			for (Object element: persons) {
			tableModel.addRow(persons.get(index).toArray());
			index++;
			
		}
			
			
	       
	        frame.setSize(500, 300);
	        frame.setVisible(true);
		
	}

}
