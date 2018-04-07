package com.hsbank.util.java.tool;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Java中调用其它程序的封装类
 * @author Arthur.Xie
 * CreateDate 2008-12-23
 */
public class RuntimeUtil {
	/**
	 * 调用其它程序
	 * <p>示例: execute(new String[]{"FlashPrinter.exe", "c:/test.doc", "-o", "d:/test.swf"})
	 * @param args 参数数组
	 */
	public static void execute(String[] args) {
		if (args.length < 1) {
			System.out.println("USAGE: java RuntimeUtil <cmd>");
			System.exit(1);
		}
		try {
			Process p = Runtime.getRuntime().exec(args);
			//处理错误流
			StreamWatch errorStream = new StreamWatch(p.getErrorStream(), "ERROR");
			//处理输出流
			StreamWatch outputStream = new StreamWatch(p.getInputStream(), "OUTPUT");
			//把上述流抛给子线程处理
			errorStream.start();
			outputStream.start();
			//待进程运行结束
			int exitVal = p.waitFor();
			Log.d(RuntimeUtil.class.getName(), "ExitValue: " + exitVal);
		} catch (Exception e) {
			Log.d(RuntimeUtil.class.getName(), e.getMessage());
		}
	}
}

/**
 * 内部类: 流处理
 * @author Arthur.Xie
 * CreateDate 2008-12-23
 */
class StreamWatch extends Thread {
	/**输入流*/
	InputStream is;
	/**流类型*/
	String type;

	StreamWatch(InputStream is, String type) {
		this.is = is;
		this.type = type;
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				Log.d(this.getClass().getName(), type + ">" + line);
			}
		} catch (IOException ioe) {
			Log.d(this.getClass().getName(), "", ioe);
		}
	}
}
