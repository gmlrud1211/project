package Recommend.Action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Recommend.DATO.ImageData;
import Recommend.DATO.TourDTO;
import ServiceManager.ServiceForward;
import ServiceManager.ServiceInterface;
import Util.HttpClientGet;

public class RecommendInfoAction implements ServiceInterface{

	private String key ="8q31GAJwwNMz571K7eTL7BPpMIsivagfYAbl3xJeUqGhpmGE1V5Md5czX9eJ1aXXsHLMLRiB8XNtcyGLDst5xA%3D%3D";
	
	
	@Override
	public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		ServiceForward Fowardaction = new ServiceForward();
		HttpSession session = request.getSession();

		String conkey = URLEncoder.encode(request.getParameter("contentid"), "UTF-8");
		long contentid = Long.parseLong(conkey);

		System.out.println("조회할 상세 content ID : "+contentid);


		// 세션으로 받아서 찾기
		@SuppressWarnings("unchecked")
		ArrayList<TourDTO> tour_list = (ArrayList<TourDTO>) session.getAttribute("recommendTour");
		System.out.println(tour_list);

		TourDTO tour_data = null;
		for (int i = 0; i < tour_list.size(); i++) {
			if (tour_list.get(i).getContentid() == contentid) {
				tour_data = tour_list.get(i);
			}
		}
		
		System.out.println("표시할 관광지 데이터 : "+tour_data);

		String url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey="+key
				+ "&contentId=" + conkey 
				+ "&MobileOS=ETC&MobileApp=Travel Planner&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y&_type=json";
		
		System.out.println("상세 관광지 정보 요청 url : "+url);
		
		String json = HttpClientGet.get_JSONDATA(url);
	
		//파싱
		JsonParsing(tour_data, json); 
		
		System.out.println(tour_data);
		request.setAttribute("tourinfo", tour_data);

		// dispatcher 설정
		Fowardaction.setRedirect(false);

		// 이동할 페이지
		Fowardaction.setPath("./Single.jsp");
		
		return Fowardaction;

	}
	
	private void JsonParsing(TourDTO tour_data, String json) throws ParseException, UnsupportedEncodingException{
		
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(json);
		
		// response 가져오기 
		JSONObject response1 = (JSONObject) obj.get("response");
		// body 가져오기 
		JSONObject body = (JSONObject) response1.get("body");
		// items 가져오기
		JSONObject items = (JSONObject) body.get("items");
		
		//items 로 부터 item 받아오기 json array
		JSONObject item = (JSONObject) items.get("item");
		
		String address = (String) item.get("addr1");
	
		System.out.println(address);
		String address2 = (String) item.get("addr2");
		String homepage = (String) item.get("homepage");
		String overview = (String) item.get("overview");
		String telname = (String) item.get("telname");
		String tel = (String) item.get("tel");
		
		tour_data.setAddress2(address2);
		tour_data.setHomepage(homepage);
		tour_data.setOverview(overview);
		tour_data.setTelname(telname);
		tour_data.setTel(tel);

		System.out.println("************상세 추가 항목 내용 ************");
		System.out.println("주소2" + address2);
		System.out.println("홈페이지" + homepage);
		System.out.println("개요" + overview);
		System.out.println("전화이름" + telname);
		System.out.println("tel" + tel);
		System.out.println("----------------------------------\n");

		
		
		
		/** ------------------------------------------------------------------- */
		
		// 추가로 이미지 정보가 있는지 조회 해본다.
		String YN = "";
		if(tour_data.getContenttypeid() == 12) // 관광지
		{
			YN = "Y";
		}
		else if(tour_data.getContenttypeid() == 39) // 음식점일때
		{
			YN = "N";
		}
					
					
		String url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailImage?ServiceKey="+key
						+"&contentTypeId="+tour_data.getContenttypeid()
						+ "&MobileOS=ETC&MobileApp=Travel Planner"
						+ "&contentId="+tour_data.getContentid()
						+"&imageYN="+YN
						+"&_type=json";
					
		String ImageJson = HttpClientGet.get_JSONDATA(url);
		System.out.println("이미지 요청 제이슨 데이터 : "+ImageJson);
				
		JSONParser image_parser = new JSONParser();
					
		JSONObject image_obj = (JSONObject) parser.parse(ImageJson);
					
					
		// response 가져오기 
		JSONObject image_response = (JSONObject) image_obj.get("response");
		// body 가져오기 
		JSONObject image_body = (JSONObject) image_response.get("body");
					
					
		//이미지 전체 갯수 가져오기
		int totalCount = Integer.parseInt(""+image_body.get("totalCount"));
		System.out.println("Image 갯수 : "+totalCount);
					
					
		
				
		// totalCount가 있을때만 가져 온다.
		
		if(totalCount > 0) // image데이터 가져오기
		{
			
			// items 가져오기
			JSONObject image_items = (JSONObject) image_body.get("items");
			
			//items 로 부터 item 받아오기 json array
			final JSONArray image_item = (JSONArray) image_items.get("item");		
			
			
			tour_data.getImageData().clear(); // 기존데이터 삭제
			
			for(int i = 0 ; i < 4; i++) // 이미지는 최대 4장만 필요 하다.
			{
				JSONObject imsi = (JSONObject) image_item.get(i);
				
				String ori 		= ""+imsi.get("originimgurl");
				String small		= ""+imsi.get("smallimageurl");
				
				ImageData data = new ImageData(ori, small);
				
				
				
				tour_data.addImageData(data);
				
			}
		}// if end
					
	}
}
