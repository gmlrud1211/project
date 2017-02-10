package Member.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ServiceManager.ServiceForward;
import ServiceManager.ServiceInterface;

public class LogOutAction implements ServiceInterface {

	@Override
	public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ServiceForward Forwardation = new ServiceForward();

		HttpSession session = request.getSession();

		session.invalidate();

		Forwardation.setPath("Index.jsp");
		Forwardation.setRedirect(true);

		return Forwardation;
	}

}
