package org.unibl.etf.sni.xss;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			Connection conn = getConnection();
			Statement st;
			String query;
			try {
				st = conn.createStatement();
				if(username != null && !username.equals("") && password != null && !password.equals("")){
					// Using prepared statement
					query = "SELECT * FROM  user where username=? AND password=?";
					PreparedStatement statement = conn.prepareStatement(query);
				    statement.setString(1, username);
				    statement.setString(2, password);
				    ResultSet res = statement.executeQuery();
					
				    // Without prepared statement
					//query = "SELECT * FROM  user where username='" + username + "' AND password='" + password + "'";
					//ResultSet res = st.executeQuery(query);
					if (res.next()) {
						String loggedUser = res.getString("username");
						request.setAttribute("username", loggedUser);
						
						String query2 = "SELECT * FROM comments ORDER BY comment_id DESC LIMIT 1";
						ResultSet res2 = st.executeQuery(query2);
						String lastComment=null;
						if(res2.next())
							lastComment = res2.getString("text");					
	                 //   RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");
	                    
	                    HttpSession session = request.getSession();
	        			session.setAttribute("user", loggedUser);
	        			session.setAttribute("lastComment", lastComment);
	        			//setting session to expiry in 30 mins
	        			session.setMaxInactiveInterval(30*60);
	        			Cookie loginCookie = new Cookie("JSESSIONID", getCookieValue(request, "JSESSIONID"));
	        			loginCookie.setHttpOnly(false);
	        			//loginCookie.setSecure(true);
	        			loginCookie.setMaxAge(30*60);
	        			response.addCookie(loginCookie);
	        			response.sendRedirect("home.jsp");
	                   // rd.forward(request, response);
					} else{
	                   // session.invalidate();
	                    request.setAttribute("errorMessage", "Invalid username or password");
	                    RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
	                    rd.forward(request, response);          
	                }
				}  else{
					request.setAttribute("errorMessage", "You must enter credentials or a pin!");
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
	
	public static String getCookieValue(HttpServletRequest request, String name) {
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (name.equals(cookie.getName())) {
	                return cookie.getValue();
	            }
	        }
	    }
	    return null;
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
