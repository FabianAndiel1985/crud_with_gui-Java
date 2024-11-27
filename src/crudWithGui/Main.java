package crudWithGui;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		
		JButton b = FrameService.createButton("Enter new person");
		JButton updateButton = FrameService.createButton("Update");
		JButton deleteButton = FrameService.createButton("Delete");
		JButton exportButton = FrameService.createButton("Export as CSV");
		
		frame.add(b);  
		frame.add(updateButton);
		frame.add(deleteButton);
		frame.add(exportButton);
		
		JFrame frameWithForm = new JFrame();
		frameWithForm.setSize(200,470);
		frameWithForm.setResizable(false);
		frameWithForm.setLayout(new FlowLayout());
		
		b.addActionListener((e)->{
			frame.setVisible(false);
			JPanel mainPanel = FrameWithFormService.createMainPanel( frame, frameWithForm,Optional.ofNullable(null));
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
	    
	    deleteButton.addActionListener((e)->{
	    	int selectedRow = table.getSelectedRow();
	    	if (selectedRow != -1) {
	    		String id = table.getModel().getValueAt(selectedRow, 0).toString();
	    		FrameService.deleteSelectedRow(id);
	    	}
	    	
		});
		
	    
		updateButton.addActionListener(
				(e)->{
					int selectedRow = table.getSelectedRow();
					if(selectedRow != -1) {
						frame.setVisible(false);
						String id = table.getModel().getValueAt(selectedRow, 0).toString();
						
						Person createdPerson = FrameWithFormService.createPersonFromRow(table, selectedRow);
						
						JPanel mainPanel = FrameWithFormService.createMainPanel( frame, frameWithForm,Optional.ofNullable(createdPerson));
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
					persons.add(FrameService.createPerson(rs));
				}
			};
		}
		catch(Exception e) {	
		}
		
		exportButton.addActionListener((e) -> {
			if(persons.isEmpty()) {
				return;
			}
			FrameService.exportToFile(persons);
		}
		);
		
	        TableColumn idColumn = table.getColumnModel().getColumn(0);
	        table.getColumnModel().removeColumn(idColumn);

	        JScrollPane scrollPane = new JScrollPane(table);
	        frame.add(scrollPane);
	        
	        FrameService.addRecordsIntoTable(persons,tableModel);
	        frame.setVisible(true);
	}
			
}
