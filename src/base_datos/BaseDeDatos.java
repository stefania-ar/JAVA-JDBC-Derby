package base_datos;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BaseDeDatos {

	public static void main(String[] args) {
		String driver = "org.apache.derby.jdbc.EmbeddedDriver";

		try {
			Class.forName(driver).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | 
				InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {

			e.printStackTrace();
			System.exit(1);
		}

		String uri= "jdbc:derby:base1;create=true";

		try {
			Connection conn = DriverManager.getConnection(uri);
			createTables(conn);
			addPersona(conn, 5, "Jesús", 33);
			addPersona(conn, 6, "Gemma", 24);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void addPersona(Connection conn, int i, String string, int j) throws SQLException {
		String insert = "INSERT INTO persona (id, nombre, edad) VALUES (?,?,?)";
		PreparedStatement ps = conn.prepareStatement(insert);
		ps.setInt(1, i);
		ps.setString(2, string);
		ps.setInt(3, j);
		ps.executeUpdate();
		ps.close();
		conn.commit();
	}

	private static void createTables(Connection conn) throws SQLException {
		String tabla= "CREATE TABLE persona(" + 
				"id INT," + 
				"nombre VARCHAR(200)," +
				 "edad INT," + 
				"PRIMARY KEY (id))";
		conn.prepareStatement(tabla).execute();
		//conn.close();
		conn.commit();
		
	}

}
