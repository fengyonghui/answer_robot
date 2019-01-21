package cn.mchina.robot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUtils {
	/**
	 * 发送Get请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String sendGet(String url, Map<String, String> params, String charset) throws IOException {
		return send(url, "GET", params, charset);
	}

	/**
	 * 发送post请求
	 *
	 * @param url
	 *            URL路径
	 * @param params
	 *            传递的参数
	 * @return
	 */
	public static String sendPost(String url, Map<String, String> params, String charset) throws IOException {
		return send(url, "POST", params, charset);
	}

	/**
	 * 发送HTTP请求
	 * 
	 * @param urlStr
	 * @return 请求结果
	 */
	private static String send(String urlStr, String method,
			Map<String, String> parameters, String charset) throws IOException {
		HttpURLConnection urlConnection = null;
		// 封装Get请求路径，Get为不加密请求路径
		if (method.equalsIgnoreCase("GET") && parameters != null) {
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (String key : parameters.keySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=").append(parameters.get(key));
				i++;
			}
			urlStr += param;
		}

		try {
			URL url = new URL(urlStr);
			// 请求配置，可根据实际情况采用灵活配置
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod(method);
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setUseCaches(true);
			urlConnection.setConnectTimeout(1000*60);
			urlConnection.setRequestProperty("Charset", charset);
			urlConnection.setRequestProperty("Content-Type", "text/plain");
			// 封装post请求参数，
			if (method.equals("POST") && parameters != null) {
				StringBuffer param = new StringBuffer();
				param.append("{");
				param.append("\"userid\"").append(":\"")
						.append(parameters.get("userid"));// "\"userid\""
															// \标识强转"号，因为参数传递时需要使用""
				param.append("\"}");

				urlConnection.getOutputStream().write(
						param.toString().getBytes());// 写入参数，必须为byte
														// OutputStream
														// 只能接收byte类型
				urlConnection.getOutputStream().flush();
				urlConnection.getOutputStream().close();
				urlConnection.connect();
			}
			// 调用http请求
			return makeContent(urlConnection,charset);
		}finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
	}

	/**
	 * 得到响应对象
	 * 
	 * @param urlConnection
	 * @return 响应对象
	 * @throws Exception
	 */
	private static String makeContent(HttpURLConnection urlConnection, String charset) throws IOException {
			InputStream in = urlConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(in, charset));
			StringBuilder temp = new StringBuilder();
			String line = bufferedReader.readLine();
			while (line != null) {
				temp.append(line).append("\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
			return temp.toString();
	}

	public static void main(String[] args) {
        for(int i=0;i<10;i++){
            String text = null;
            try {
                text = sendGet("http://wap.baidu.com/s?word=鲜花",
                        null, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            System.out.println("********" + i);
		    System.out.println(text);
        }
	}
	
	public static Set<String> getAllLinkForOnePage(String htmlSource) {//提取页面上的所有超链接
        Set<String> urlSet = new HashSet<String>();
  
        Pattern linkElementPattern = Pattern.compile("<a\\s.*?href=\"([^\"]+)\"[^>]*>(.*?)</a>");
        Matcher linkElementMatcher = linkElementPattern.matcher(htmlSource);
  
        while (linkElementMatcher.find()) {  
            String temp = linkElementMatcher.group(1);
            if(temp!=null)  
                temp=temp.trim();  
              
            if (temp==null||temp.startsWith("#") || temp.toLowerCase().startsWith("javascript:")
            		|| !(temp.indexOf(".shtml")>0 || temp.indexOf(".html") > 0)) {//  
                continue;  
            }  
            temp = temp.replace("&amp;", "&");  
            urlSet.add(temp);  
        }  

        return urlSet;  
    } 
	
	/**
	 * format the line in following format:
	 *  1 11 15 6B Ba GG 8f Ub bk kp wy 
	 * @param line
	 * @return
	 */
	private String formatLine(String line){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < line.length(); i++){
			if( i%2 == 0){
				sb.append(line.charAt(i)).append(" ");
			}else{
				sb.append(line.charAt(i));
			}
		}
		
		return sb.toString();
	}
    
	/**
	 * define customer comparator
	 * @author Yonghui.Feng
	 *
	 */
	class SortComparator implements Comparator<String> {
		public final String ruleString = "j<a<G<g<8<r<D<f<U<b<W";

		public int compare(String s0, String s1) {
			int s0Index = ruleString.indexOf(s0);
			int s1Index = ruleString.indexOf(s1);

			if (s0Index >= 0 && s1Index >= 0) {
				return s0Index - s1Index;
			}
			return s0.compareTo(s1);
		}
	}
	
	/**
	 * 
	 * @param sText
	 * @return
	 */
	private String sort(String sText) {
		char[] sTextArray = sText.toCharArray();
		List<String> tempList = new ArrayList<String>();
		for (char c : sTextArray) {
			if (c != ' ') {
				tempList.add("" + c);
			}
		}

		String firstChar = tempList.get(tempList.size() - 1);
		tempList.remove(tempList.size() - 1);
		String[] strArray = tempList.toArray(new String[0]);

		Arrays.sort(strArray, new SortComparator());
		StringBuffer sb = new StringBuffer();
		sb.append(firstChar);
		for(String s : strArray){
			sb.append(s);
		}
		return sb.toString();
	}
}