package com.employeemanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.employeemanagement.dao.StudentDao;
import com.employeemanagement.model.Student;

public class StudentServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StudentDao studentDao;

	public void init() {
		studentDao = new StudentDao();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String action = req.getServletPath();
		System.out.println(action);
		switch (action) {
		case "/new":
			showNewForm(req, res);
			break;
		case "/insert":
			try {
				insertStudent(req, res);
			} catch (SQLException | IOException e) {

				e.printStackTrace();
			}
			break;
		case "/delete":
			try {
				deleteStudent(req, res);
			} catch (SQLException | IOException e) {

				e.printStackTrace();
			}
			break;
		case "/edit":
			try {
				showEditForm(req, res);
			} catch (SQLException | ServletException | IOException e) {

				e.printStackTrace();
			}
			break;
		case "/update":
			try {
				update(req, res);
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			try {
				studentList(req, res);
			} catch (SQLException | ServletException | IOException e) {

				e.printStackTrace();
			}
			break;
		}
	}

	public void showNewForm(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher("student-form.jsp");
		rd.forward(req, res);
	}

	public void insertStudent(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException {
		String name = req.getParameter("name");
		String address = req.getParameter("address");
		Student newstudent = new Student(name, address);
		studentDao.insertStudent(newstudent);
		res.sendRedirect("list");

	}

	public void deleteStudent(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException {
		int sid = Integer.parseInt(req.getParameter("id"));
		studentDao.deleteStudent(sid);
		res.sendRedirect("list");
	}

	public void showEditForm(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, ServletException, IOException {
		int sid = Integer.parseInt(req.getParameter("id"));
		Student existStudent = studentDao.getById(sid);
		req.setAttribute("student", existStudent);
		RequestDispatcher rd = req.getRequestDispatcher("student-form.jsp");
		rd.forward(req, res);

	}

	public void update(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {
		int sid = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String address = req.getParameter("address");

		Student student = new Student(sid, name, address);
		studentDao.updateStudent(student);
		res.sendRedirect("list");

	}

	public void studentList(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, ServletException, IOException {
		System.out.println("StudentServlet.studentList()");
		List<Student> studentList = studentDao.getAllStudent();
		req.setAttribute("studentList", studentList);
		RequestDispatcher rd = req.getRequestDispatcher("student-list.jsp");
		rd.forward(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
