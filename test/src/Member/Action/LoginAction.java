package Member.Action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.ParseException;

import Member.DATO.MemberDAO;
import Member.DATO.MemberDTO;
import Search.Action.SearchAction;
import Search.DATO.TourDTO;
import ServiceManager.ServiceForward;
import ServiceManager.ServiceInterface;
import Util.HttpClientGet;

public class LoginAction implements ServiceInterface{ // 로그인을 처리하는 클래스

	@Override
	public ServiceForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 리턴 시킬 객체를 준비
		ServiceForward Fowardaction = new ServiceForward();
		
		
		// id pwd 구별
		String usr_id = request.getParameter("usr_id");
		String usr_pwd	= request.getParameter("usr_pwd");

		
		//사용자 객체 만들기
		MemberDTO data = new MemberDTO(usr_id, usr_pwd);
		
		// DAO를 통해서 DB에서 처리 하기
		boolean b = MemberDAO.isMember(data);
		
		
		if(b) // 로그인 성공시 
		{
			// request 세션에 login 데이터를 넣어 준다.
			
			String msg = "로그인에 성공 하였습니다";
			
			HttpSession session = request.getSession();
    		session.setAttribute("member", data);
    		
    		Fowardaction.setRedirect(false);
    		Fowardaction.setPath("./Index.jsp?msg=success");
    		
		}
		else // 로그인 실패시
		{
			String msg="아이디 또는 비밀번호가 틀려요!";
			
			Fowardaction.setRedirect(true);
    		Fowardaction.setPath("./Login.jsp?msg=fail");
    	   	   		
    		
		}
		
		return Fowardaction;
	}
	
	
	public ArrayList<TourDTO> setSessionData(String keyword) throws UnsupportedEncodingException, ParseException
	{
	
		try {
			keyword = URLEncoder.encode(keyword, "UTF-8");
			System.out.println(keyword);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		// 요청할 주소를 넣으세요
		String url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchKeyword?ServiceKey=8q31GAJwwNMz571K7eTL7BPpMIsivagfYAbl3xJeUqGhpmGE1V5Md5czX9eJ1aXXsHLMLRiB8XNtcyGLDst5xA%3D%3D"
				+ "&keyword=" + keyword + "&MobileOS=ETC&MobileApp=TravelPlanner&_type=json";
		
		System.out.println("요청주소 : "+url);
		// 다음 서버로 부터 json 받아오기 
		String json = HttpClientGet.get_JSONDATA(url);
		
		System.out.println("파싱된 관광지 데이터 : "+json);
		
		/**Json 파싱하기**/
		// 관광지 담을 변수 준비
		ArrayList<TourDTO> tour_list = new ArrayList<TourDTO>();
		
		// 파싱하여 관광지 담기
		SearchAction.JsonParsing(tour_list, json); 
		
		System.out.println(tour_list.toString());
		
		return tour_list;
		
	
	}

}
