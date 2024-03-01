package com.employeemanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.employeemanagement.model.Student;



public class StudentDao {
	private String url = "jdbc:mysql://localhost:3306/demo?useSSL=false";
	private String username = "root";
	private String password = "root";

	private static final String INSERT_STUDENT_SQL = "INSERT INTO student" + "  (name, address) VALUES "
			+ " (?, ?);";
	private static final String SELECT_STUDENT_BY_ID = "select id,name,address from student where id =?";
	private static final String SELECT_ALL_STUDENTS = "select * from student";
	private static final String DELETE_STUDENT_SQL = "delete from student where id = ?;";
	private static final String UPDATE_STUDENT_SQL = "update student set name = ?,address= ? where id = ?;";

	protected Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

		catch (SQLException e) {

			e.printStackTrace();
		}
		return con;
	}

	public void insertStudent(Student student) throws SQLException {
		try (Connection con = getConnection(); PreparedStatement pt = con.prepareStatement(INSERT_STUDENT_SQL)) {
			pt.setString(1, student.getName());
			pt.setString(2, student.getAddress());
			pt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean updateStudent(Student student) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement pt = connection.prepareStatement(UPDATE_STUDENT_SQL);) {
            pt.setString(1, student.getName());
            pt.setString(2, student.getAddress());
            
            pt.setInt(3, student.getId());

            rowUpdated = pt.executeUpdate() > 0;
        }
        return rowUpdated;
    }
		
	

	public Student getById(int id) throws SQLException {
		Student student=null;
    	  try(Connection con=getConnection();PreparedStatement pt=con.prepareStatement(SELECT_STUDENT_BY_ID)){
    		  pt.setInt(1, id);
    		  ResultSet rs=pt.executeQuery();
    		  while (rs.next()) {
	                String name = rs.getString("name");
	                String address = rs.getString("address");
	                
	                student = new Student(id, name, address);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return student;
	    }
	
	public List<Student> getAllStudent() throws SQLException{
		
		List<Student> students=new ArrayList<Student>();
		  try(Connection con=getConnection();PreparedStatement pt=con.prepareStatement(SELECT_ALL_STUDENTS)){
			  ResultSet rs=pt.executeQuery();
			  while(rs.next()) {
				  int id = rs.getInt("id");
	                String name = rs.getString("name");
	                String address = rs.getString("address"); 
	                students.add(new Student(id, name, address));
			  }
		  }
		
		return students;
		
	}
	public boolean deleteStudent(int id) throws SQLException {
		boolean rowdeleted;
		try(Connection con=getConnection();PreparedStatement pt=con.prepareStatement(DELETE_STUDENT_SQL)){
			pt.setInt(1, id);
			rowdeleted=pt.executeUpdate()>0;
		}
		return rowdeleted;
	}
 }
