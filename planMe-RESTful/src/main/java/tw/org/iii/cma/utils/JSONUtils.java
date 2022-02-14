package tw.org.iii.cma.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class JSONUtils {
	// 將OpenData藉由url得到，並將之讀成大String，方便轉換JSON
	public static String fetchOpenDataUrl(String OpenDataUrl) {
		try {
			URL url = new URL(OpenDataUrl);
			URLConnection connection = url.openConnection();
			connection.connect();

			BufferedInputStream bin = new BufferedInputStream(connection.getInputStream());

			byte[] buf = new byte[1024 * 1024];
			int len;
			StringBuffer sb = new StringBuffer();

			while ((len = bin.read(buf)) != -1) {
				sb.append(new String(buf, 0, len));
			}

			bin.close();
			return sb.toString();

		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}

	}
	// 將OpenData藉由file得到，並將之讀成大String，方便轉換JSON
	public static String fetchOpenDataFile(String Fileposition) {
		File file = new File(Fileposition);// 讀取檔案位置
		String data;
		StringBuffer jsonData = new StringBuffer();
		BufferedReader reader = null;// 使用BuffereReader 加入讀取
		try {
			reader = new BufferedReader(new java.io.FileReader(file));
			while ((data = reader.readLine()) != null){ // 讀取json檔
				jsonData.append(data); // 將讀取到的資料存入jsonData
			}
		} catch (Exception e) {
			e.toString();
		}
		return jsonData.toString();
	}
}
