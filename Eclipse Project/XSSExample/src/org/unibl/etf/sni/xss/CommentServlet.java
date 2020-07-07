package org.unibl.etf.sni.xss;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try {
			String comment = request.getParameter("comment");
			
			Connection conn = getConnection();
			Statement st;
			String query;
			try {
				st = conn.createStatement();
				if(comment != null && !comment.equals("")){
					// Using prepared statement
					query = "insert into comments (text) values (?)";
					PreparedStatement statement = conn.prepareStatement(query);
				    statement.setString(1, comment);
				    int res = statement.executeUpdate();

					if (res>0) {
	        			response.sendRedirect("home.jsp");
	                   // rd.forward(request, response);
					} else{
	                   // session.invalidate();
	                    request.setAttribute("errorMessage", "Error");
	                    RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
	                    rd.forward(request, response);          
	                }
				}  else{
					request.setAttribute("errorMessage", "Error!");
                    RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                    rd.forward(request, response);          
				}
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("errorMessage", e.getMessage());
                RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                rd.forward(request, response);          
			}
		}

		finally {
		}
	}
	
	private Connection getConnection(){
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "sni_xss";
		String using = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
		String driver = "com.mysql.cj.jdbc.Driver";
		String dbUser = "root";
		String dbPass = "root";
		try {
			Class.forName(driver);
			//conn = DriverManager.getConnection(url + dbName, dbUser, dbPass);
			conn = DriverManager.getConnection(url + dbName + using, dbUser, dbPass); // for multiple queries
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on
	// the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
