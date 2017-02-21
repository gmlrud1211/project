package Recommend.DATO;

public class ImageData {
	String originimgurl = "";
	String smallimageurl = "";

	public ImageData(String ori, String small) {
		this.originimgurl = ori;
		this.smallimageurl = small;
	}

	public String getOriginimgurl() {
		return originimgurl;
	}

	public void setOriginimgurl(String originimgurl) {
		this.originimgurl = originimgurl;
	}

	public String getSmallimageurl() {
		return smallimageurl;
	}

	public void setSmallimageurl(String smallimageurl) {
		this.smallimageurl = smallimageurl;
	}
}
