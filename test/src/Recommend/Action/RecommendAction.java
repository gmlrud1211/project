package Recommend.Action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Recommend.DATO.TourDTO;
import ServiceManager.ServiceForward;
import ServiceManager.ServiceInterface;
import Util.HttpClientGet;

public class RecommendAction implements ServiceInterface {

	@Override
	public ServiceForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		//리턴시킬 객체생성
		ServiceForward Fowardaction = new ServiceForward(); 
		/*
		int contentTypeId = 12;
			
		String cat1 = "";
		String cat2 = "";
		String cat3 = "";
		
		switch (contentTypeId) {
		case 12:
			cat1="A02";
			break;
		case 32:
			cat1="B02";
			break;
		case 38:
			cat1="A04";
			break;
		case 39:
			cat1="A05";
			break;
		}*/

		// 요청할 주소를 
		String url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/categoryCode?ServiceKey=8q31GAJwwNMz571K7eTL7BPpMIsivagfYAbl3xJeUqGhpmGE1V5Md5czX9eJ1aXXsHLMLRiB8XNtcyGLDst5xA%3D%3D"
				+ "&contentTypeId=12&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTesting";
		System.out.println("요청주소 : " + url);
		
		// 서버로 부터 json 받아오기
		String json = HttpClientGet.get_JSONDATA(url);

		System.out.println("파싱된 관광지 데이터 : " + json);

		/** Json 파싱하기 **/
		// 관광지 담을 변수 준비
		ArrayList<TourDTO> tour_list = new ArrayList<TourDTO>();

				
		// 파싱하여 관광지 담기
		JsonParsing(tour_list, json);
			
		System.out.println(tour_list.toString());
		System.out.println(tour_list.size());
		HttpSession session = request.getSession();
		session.setAttribute("recommendTour", tour_list);
		Fowardaction = new ServiceForward();

		// dispatcher 설정
		Fowardaction.setRedirect(true);

		// 이동할 페이지
		Fowardaction.setPath("./Index.jsp");

		return Fowardaction;
		}

		public static void JsonParsing(ArrayList<TourDTO> tour_list, String json)
					throws ParseException, UnsupportedEncodingException {
				// TODO Auto-generated method stub

				JSONParser parser = new JSONParser();

				JSONObject obj = (JSONObject) parser.parse(json);

				// response 가져오기
				JSONObject response1 = (JSONObject) obj.get("response");

				// body 가져오기
				JSONObject body = (JSONObject) response1.get("body");

				
				// items 가져오기
				JSONObject items = (JSONObject) body.get("items");

				// items 로 부터 item 받아오기 
				JSONArray item = (JSONArray) items.get("item");

				// 하나씩 꺼내보기
				for (int i = 0; i < item.size(); i++) {
					JSONObject imsi = (JSONObject) item.get(i);
					// 정보 파싱하기

					String title = (String) imsi.get("title");

					// 관광 정보 id
					long contentid = (long) imsi.get("contentid");

					// 관광 정보 type
					long contenttypeid = (long) imsi.get("contenttypeid");

					String address1 = (String) imsi.get("addr1");
					if (address1 == null) {
						address1 = "주소정보 없음";
					}

					String firstimage = (String) imsi.get("firstimage");
					// 이미지 값의 경우 null값이 있을경우
					if (firstimage == null) {
						firstimage = "images/noImage.jpg";
					}

					// 문자열로 읽은 다음 변환하기
					double mapx = Double.parseDouble("" + imsi.get("mapx"));
					double mapy = Double.parseDouble("" + imsi.get("mapy"));

					// 객체만들기
					TourDTO tour_data = new TourDTO(contentid, contenttypeid, title, address1, firstimage, mapx, mapy, null,
							null, null, null, null);
					tour_list.add(tour_data);
					System.out.println("************" + i + "번째 항목 내용 ************");
					System.out.println("ID : " + contentid);
					System.out.println("장소 : " + title);
					System.out.println("주소 : " + address1);
					System.out.println("이미지 : " + firstimage);
					System.out.println("경도: " + mapx);
					System.out.println("위도: " + mapy);
					System.out.println("----------------------------------\n");

				}
			}
}
