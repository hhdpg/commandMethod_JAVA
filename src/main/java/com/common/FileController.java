package com.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author xcd
 * @Aata 2019年1月11日
 * @Description
 */
public class FileController {

	private static int MAX_FILENAME_LENGTH = 50;

	public void upload(String fileName, String tmpFileName, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (StringUtils.isEmpty(fileName) && StringUtils.isEmpty(tmpFileName)) {
			return;
		}
		File file = new File(tmpFileName);
		if (!file.exists()) {
			return;
		}

		String postfix = tmpFileName.substring(tmpFileName.lastIndexOf("."));
		response.reset();
		String userAgent = request.getHeader("User-Agent");
		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
			response.setHeader("Content-Disposition",
					"attachment;filename=\"" + URLEncoder.encode(fileName, "utf-8") + postfix + "\"");
		} else {
			fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1"); // 下载的文件名显示编码处理
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + postfix + "\"");
		}
		response.setContentType("application/x-msdownload;charset=UTF-8");
		FileInputStream fis = new FileInputStream(file);
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());

		byte[] buffer = new byte[2048];
		int readlength = 0;
		while ((readlength = fis.read(buffer)) != -1) {
			bos.write(buffer, 0, readlength);
		}
		try {
			fis.close();
		} catch (IOException e) {
		}
		try {
			bos.flush();
			bos.close();
		} catch (IOException e) {
		}
	}

	// map to String json;
	// String jsonStr = JSON.toJSONString(map).toString();
	public void jsonFile(String filePath, String jsonFile) {
		File file = new File(filePath);
		char[] stack = new char[1024];
		int top = -1;
		StringBuffer sb = new StringBuffer();
		char[] charArray = jsonFile.toCharArray();
		int count = 0;
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if ('{' == c || '[' == c) {
				stack[++top] = c;
				sb.append(" " + charArray[i] + " ");
				for (int j = 0; j <= top; j++) {
					sb.append(" ");
				}
				continue;
			}
			if ((i + 1) <= (charArray.length - 1)) {
				char d = charArray[i + 1];
				if ('}' == d || ']' == d) {
					top--;
					sb.append(charArray[i] + "\n");
					for (int j = 0; j <= top; j++) {
						sb.append(" ");
					}
					continue;
				}
			}
			if (',' == c) {
				count++;
				if (count % 3 == 0) {
					sb.append(charArray[i] + "\n");
				} else {
					sb.append(charArray[i] + " ");
				}
				for (int j = 0; j <= top; j++) {
					sb.append("");
				}
				continue;
			}
			sb.append(c);
		}
		try {
			Writer write = new FileWriter(file, true);
			write.write(sb.toString());
			write.flush();
			write.close();
		} catch (IOException ioe) {
			System.out.println("导出失败");
		}
	}

}
