package crudWithGui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class FrameService {
	
	
public static ArrayList<String> createPerson(ResultSet rs) throws SQLException {
		
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


public static void addRecordsIntoTable(ArrayList<ArrayList<String>> persons,DefaultTableModel tableModel) {
	int index = 0;
	for (ArrayList<String> element: persons) {
	tableModel.addRow(persons.get(index).toArray());
	index++;
	}	
}

}
