package request;

public class AreaBasedListRequest extends RequestParameter{
	String serviceUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList";
	String areaCode = "";
	String contentTypeId = "";
	String sigunguCode = "";
	String cat2 = "";
	
	
	public String getQueryString() {
		String queryString="";
		queryString += super.getQueryString();
		queryString += "&areaCode=" + areaCode;
		queryString += "&contentTypeId=" + contentTypeId;
		queryString += "&sigunguCode=" + sigunguCode;
		queryString += "&cat2=" + cat2;
		
		return queryString;
	}

	public String getUrl() {
		return serviceUrl + "?" + getQueryString();
	}

	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getContentTypeId() {
		return contentTypeId;
	}
	public void setContentTypeId(String contentTypeId) {
		this.contentTypeId = contentTypeId;
	}
	public String getSigunguCode() {
		return sigunguCode;
	}
	public void setSigunguCode(String sigunguCode) {
		this.sigunguCode = sigunguCode;
	}
	public String getCat2() {
		return cat2;
	}
	public void setCat2(String cat2) {
		this.cat2 = cat2;
	}

}
