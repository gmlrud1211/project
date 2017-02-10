package Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
 
public class HttpClientGet {
 
	public static String get_JSONDATA(String url) {
		
		StringBuffer sb = new StringBuffer();
	
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpGet httpget = new HttpGet(url);
 
			System.out.println("요청주소 request " + httpget.getURI());
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
 
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (entity != null) {
				System.out.println("Response content length: "
						+ entity.getContentLength());
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent(),"UTF-8")); //인코딩
 
				String line = "";
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}
			}
			httpget.abort();
			System.out.println("----------------------------------------");
			httpclient.getConnectionManager().shutdown();
 
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		
		return sb.toString();
	}
 
}