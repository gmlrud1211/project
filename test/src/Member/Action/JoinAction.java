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

/**
 * 회원 가입을 처리 하는 클래스
 * @author Administrator
 */
public class JoinAction implements ServiceInterface 
{

	@Override
	public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		// 리턴 객체 준비 
		ServiceForward forward = null; // null 로 처리 하는 이유는 , 회원 가입/실패 후 response를 조작할 것 이기 때문에
		
		// 사용자 입력 정보 받아오기

		String 	usr_id 		= request.getParameter("usr_id");
		String 	usr_pwd		= request.getParameter("usr_pwd");
		String 	name 	= request.getParameter("name");
		
		// 사용자 객체 생성
		MemberDTO dto = new MemberDTO(usr_id, usr_pwd, name);
		
		// 회원 가입 처리
		boolean joinCheck = MemberDAO.insertMember(dto);
		
		forward = new ServiceForward();
		
		if(joinCheck) // 회원 가입이 되었을때 
		{
			/** 회원 가입 정보, 로그인 정보는 세션 영역에 저장 한다. */
			// 세션 가져오기
			HttpSession session = request.getSession();
			// 사용자 정보 지정
			session.setAttribute("member", dto);
		
			forward.setRedirect(true);
			forward.setPath("./Index.jsp?msg=success");
			
		}
		else // 회원 가입이 안되었을때
		{
			forward.setRedirect(false);
			forward.setPath("./Register.jsp?msg=fail");
		
		}
		
		return forward;
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
				+ "&keyword=" + keyword + "&MobileOS=ETC&MobileApp=KoreaTourismOrganization&_type=json";
		
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
