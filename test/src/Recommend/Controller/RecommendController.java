package Recommend.Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Recommend.Action.RecommendAction;
import Recommend.Action.RecommendInfoAction;
import ServiceManager.ServiceForward;
import ServiceManager.ServiceInterface;
/**
 * Servlet implementation class MemberController
 */
@WebServlet("/Recommend")
public class RecommendController extends HttpServlet {
	private static final long serialVersionUID = 1L; 
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecommendController() {
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

		
		case "Recommending":
			action = new RecommendAction();
			try {
				forwardAction = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		
		case "RecommendInfo":
			action = new RecommendInfoAction();
			try {
				forwardAction = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		

		if (forwardAction != null)
		{
			if (forwardAction.isRedirect()) 
			{
				response.sendRedirect(forwardAction.getPath());
			} 
			else 
			{
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
