package Member.Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Member.Action.JoinAction;
import Member.Action.LogOutAction;
import Member.Action.LoginAction;
import ServiceManager.ServiceForward;
import ServiceManager.ServiceInterface;

/**
 * Servlet implementation class MemberController
 */
@WebServlet("/Member")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// JSP 페이지 인코딩이 UTF-8이기 때문에 인코딩 설정
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		ServiceForward forwardAction = null;
		ServiceInterface action = null;

		String cmd = request.getParameter("cmd");
		System.out.println("멤버 컨트롤 분기 명령어 : " + cmd);

		switch (cmd) 
		{
			case "join" :
				action = new JoinAction();
				try {
					forwardAction = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			break;
			
			case "login" :
				action = new LoginAction();
				try {
					forwardAction = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			break;
			
			case "Logout" : // 로그아웃 액션 처리 
				action = new LogOutAction();
				
				try 
				{
					forwardAction = action.execute(request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		break;
		} // switch end

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
		}// if end
		

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
