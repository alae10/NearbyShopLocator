package com.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;



public class JSONfunctions {

	public static JSONObject getJSONfromURL(String url) {
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;
		try {
			/*HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();*/
			URL url1 = new URL(url);
			  URLConnection conn = url1.openConnection();
			  conn.setRequestProperty("User-Agent", "Mozilla 59.0.2");
		     is = url1.openStream();
			

		} catch (Exception e) {
			
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			
		}

		try {

			jArray = new JSONObject(result);
		} catch (JSONException e) {
			
		}

		return jArray;
	}
}
