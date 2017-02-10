package Search.Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Search.Action.SearchAction;
import Search.Action.SearchInfoAction;
import Search.Action.SearchLocationAction;
import Search.Action.SearchLocationDetailAction;
import ServiceManager.ServiceForward;
import ServiceManager.ServiceInterface;

/**
 * Servlet implementation class SearchController
 */
@WebServlet("/Search")
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// JSP 페이지 인코딩이 UTF-8이기 때문에 인코딩 설정
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		ServiceForward forwardAction = null;
		ServiceInterface action = null;

		String cmd = request.getParameter("cmd");
		System.out.println("컨트롤 분기 명령어 : "+cmd);
		String word = request.getParameter("tour");

		switch (cmd) {

		case "searching":
			action = new SearchAction();

			try {
				forwardAction = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case "searchinfo":

			action = new SearchInfoAction();
			try {
				forwardAction = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case "location":

			forwardAction = new ServiceForward();

			forwardAction.setRedirect(false);
			forwardAction.setPath("./location.jsp");
			break;
			
		case "locationsearching":
			action = new SearchLocationAction();
			try {
				forwardAction = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "locationDetail":
			action = new SearchLocationDetailAction();
			try {
				forwardAction = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		

		if (forwardAction != null) {
			if (forwardAction.isRedirect()) {
				response.sendRedirect(forwardAction.getPath());
			} else {
				RequestDispatcher dis = request.getRequestDispatcher(forwardAction.getPath());
				dis.forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
