package Search.Action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Search.DATO.TourDTO;
import ServiceManager.ServiceForward;
import ServiceManager.ServiceInterface;
import Util.HttpClientGet;

public class SearchLocationAction implements ServiceInterface {

	@Override
	public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		ServiceForward Fowardaction = null;
		
		String position_x = request.getParameter("X");
		String position_y = request.getParameter("Y");
		
		System.out.println("X 좌표 : "+position_x);
		System.out.println("Y 좌표 : "+position_y);
		
		String key = "8q31GAJwwNMz571K7eTL7BPpMIsivagfYAbl3xJeUqGhpmGE1V5Md5czX9eJ1aXXsHLMLRiB8XNtcyGLDst5xA%3D%3D";
		String url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList?ServiceKey="
				+key+ "&mapX="+position_x+"&mapY="+position_y+"&radius=1000&pageNo=1&numOfRows=10&listYN=Y&arrange=A&MobileOS=ETC&MobileApp=AppTesting&_type=json";
		
		System.out.println("준비된 위치기반 검색 url : "+url);
		// 다음 서버로 부터 json 받아오기 
		String json = HttpClientGet.get_JSONDATA(url);
		
		System.out.println("서버로 부터 반환된 위치기반 JSON : "+json);
		/**Json 파싱하기**/
		// 관광지 담을 변수 준비
		ArrayList<TourDTO> tour_list = new ArrayList<TourDTO>();
		
		// 파싱하여 관광지 담기
		JsonParsing(tour_list, json); 
		
		System.out.println("전체 데이터 : "+tour_list.toString());
		
		
		HttpSession session = request.getSession();
		session.setAttribute("LocationTour", tour_list);
		Fowardaction = new ServiceForward();
		
		//dispatcher 설정
		Fowardaction.setRedirect(false);
		
		//이동할 페이지
		Fowardaction.setPath("./NearLocation.jsp");
		
		return Fowardaction;
		
	}

	private void JsonParsing(ArrayList<TourDTO> tour_list, String json) throws ParseException, UnsupportedEncodingException{
		// TODO Auto-generated method stub
		
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(json);
		
		
		// response 가져오기 
		JSONObject response1 = (JSONObject) obj.get("response");
		// body 가져오기 
		JSONObject body = (JSONObject) response1.get("body");
		// items 가져오기
		JSONObject items = (JSONObject) body.get("items");
		
		//items 로 부터 item 받아오기 jsonarray 이다 : [ 로 시작하기 때문에 
		JSONArray item = (JSONArray) items.get("item");
		
		
		// 하나씩 꺼내보기 
		
		for(int i = 0 ; i < item.size(); i ++)
		{
			JSONObject imsi = (JSONObject) item.get(i);
			//정보 파싱하기
			String title = (String) imsi.get("title");
			
			// 타이틀 인코딩 변환
			long contentid= (long) imsi.get("contentid");
			long contenttypeid = (long) imsi.get("contenttypeid");
			
			String address1= (String) imsi.get("addr1");
			if(address1==null)
			{
				address1 = "주소정보 없음";
			}
			
			
			String firstimage = (String) imsi.get("firstimage");
			//이미지 값의 경우 null값이 있을 수 있다.
			if(firstimage==null)
			{
				firstimage = "images/noImage.jpg";
			}
			
			
			double mapx = Double.parseDouble(""+imsi.get("mapx"));
			double mapy = Double.parseDouble(""+imsi.get("mapy"));
			
			//객체만들기
			TourDTO tour_data = new TourDTO(contentid, contenttypeid, title,address1,firstimage,mapx,mapy,null,null,null,null,null);
			tour_list.add(tour_data);
			System.out.println("************"+i+"번째 항목 내용 ************");
			System.out.println("ID : "+contentid);
			System.out.println("장소 : "+title);
			System.out.println("주소 : " + address1);
			System.out.println("이미지 : " + firstimage);
			System.out.println("경도: " +mapx);
			System.out.println("위도: " +mapy);
			System.out.println("----------------------------------\n");
				
		}
	}

}
