package Search.DATO;

import java.util.ArrayList;

public class TourDTO {
	
	// 오류를 방지 하기 위해서 초기화 시킨다.
	
	long 		contentid		=	0; 		//관광지 id값
	long		contenttypeid	=	0;		//관광지 type id값
	String 	title 				= ""; 		// 관광지 제목
	String 	address 			= ""; 		// 관광지 주소
	String		firstimage 		= "";	 	// 관광지 이미지
	double 	mapx				=	0; 		// GPS X좌표 WGS84 경도좌표
	double 	mapy				=	0; 		// GPS Y좌표 WGS84 위도좌표
	
	
	String 	address2 		= ""; 		//관광지 상세 주소
	String 	homepage 		= ""; 		//관련 홈페이지
	String 	overview 		= ""; 		//관광지 설명
	String 	telname 			= ""; 		//전화번호 이름
	String 	tel 				= ""; 		//전화번호
	
	
	ArrayList<ImageData> imageData = new ArrayList<ImageData>(1);	// 추가이미지 배열
	
	public TourDTO(long contentid,long contenttypeid, String title, String address, String firstimage, double mapx, double mapy,
			String address2, String homepage, String overview, String telname, String tel) // 생성자
	{	
		this.contentid = contentid;
		this.contenttypeid = contenttypeid;
		this.title = title;
		this.address = address;
		this.firstimage = firstimage;
		this.mapx = mapx;
		this.mapy = mapy;
		
		this.address2 = address2;
		this.homepage = homepage;
		this.overview = overview;
		this.telname = telname;
		this.tel = tel;
	}
	
	
	public void addImageData(ImageData data)
	{
		imageData.add(data);
	}
	

	public ArrayList<ImageData> getImageData() {
		return imageData;
	}


	public void setImageData(ArrayList<ImageData> imageData) {
		this.imageData = imageData;
	}


	public long getContenttypeid() {
		return contenttypeid;
	}


	public void setContenttypeid(long contenttypeid) {
		this.contenttypeid = contenttypeid;
	}


	public String getAddress2() {
		return address2;
	}


	public void setAddress2(String address2) {
		this.address2 = address2;
	}


	public String getHomepage() {
		return homepage;
	}


	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}


	public String getOverview() {
		return overview;
	}


	public void setOverview(String overview) {
		this.overview = overview;
	}


	public String getTelname() {
		return telname;
	}


	public void setTelname(String telname) {
		this.telname = telname;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public long getContentid() {
		return contentid;
	}

	public void setContentid(long contentid) {
		this.contentid = contentid;
	}

	public double getMapx() {
		return mapx;
	}

	public void setMapx(double mapx) {
		this.mapx = mapx;
	}

	public double getMapy() {
		return mapy;
	}

	public void setMapy(double mapy) {
		this.mapy = mapy;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFirstimage() {
		return firstimage;
	}

	public void setFirstimage(String firstimage) {
		this.firstimage = firstimage;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = this.title + " " + this.address + " " + this.firstimage+ " " +this.mapx+ " " + this.mapy 
				+" "+ this.address2+" "+ this.homepage+" "+ this.overview+" "+ this.telname+" "+ this.tel ;
		return str;
	}

}
