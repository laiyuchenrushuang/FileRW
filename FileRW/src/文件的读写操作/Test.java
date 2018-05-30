package 文件的读写操作;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;

public class Test {

	public static void main(String[] args) {
		File file = new File("D:/test/1.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		writeMethod1(file.toString());
		writeMethod2(file.toString());
		writeMethod3(file.toString());

		readMethod1(file.toString());
		readMethod2(file.toString());

		File filepicture = new File("D:/test/temp.jpg");
		byte[] mData =  image2byte(filepicture.toString());
		File filepicture1 = new File("D:/test/temp1.jpg");
		byte2image(mData,filepicture1.toString());
	}

	/**
	 * 使用FileWriter类写文本文件
	 * 
	 * @param fileName
	 */
	public static void writeMethod1(String fileName) {
		try {
			// 使用这个构造函数时，如果存在kuka.txt文件，
			// 则先把这个文件给删除掉，然后创建新的kuka.txt
			FileWriter writer = new FileWriter(fileName);
			writer.write("Hello writeMethod1\n");
			writer.write("  My name is coolszy!\n");
			writer.write("  I like you and miss you。");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用FileWriter类往文本文件中追加信息
	 * 
	 * @param fileName
	 */
	public static void writeMethod2(String fileName) {
		try {
			// 使用这个构造函数时，如果存在kuka.txt文件，
			// 则直接往kuka.txt中追加字符串
			FileWriter writer = new FileWriter(fileName, true);
			SimpleDateFormat format = new SimpleDateFormat();
			String time = format.format(new Date());
			writer.write("writeMethod2\n\t" + time);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 注意：上面的例子由于写入的文本很少，使用FileWrite类就可以了。但如果需要写入的
	// 内容很多，就应该使用更为高效的缓冲器流类BufferedWriter。
	/**
	 * 使用BufferedWriter类写文本文件
	 * 
	 * @param fileName
	 */
	public static void writeMethod3(String fileName) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
			out.write("Hello writeMethod3:");
			out.newLine(); // 注意\n不一定在各种计算机上都能产生换行的效果
			out.write("  My name is coolszy!\n");
			out.write("  I like you and miss you。");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 使用FileReader类读文本文件
	 */
	public static void readMethod1(String fileName) {
		int c = 0;
		try {
			FileReader reader = new FileReader(fileName);
			c = reader.read();
			while (c != -1) {
				System.out.print((char) c);
				c = reader.read();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用BufferedReader类读文本文件
	 */
	public static void readMethod2(String fileName) {
		String line = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			line = in.readLine();
			while (line != null) {
				System.out.println(line);
				line = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 图片到byte数组
	public static byte[] image2byte(String path) {
		byte[] data = null;
		FileImageInputStream input = null;
		try {
			input = new FileImageInputStream(new File(path));
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int numBytesRead = 0;
			while ((numBytesRead = input.read(buf)) != -1) {
				output.write(buf, 0, numBytesRead);
			}
			data = output.toByteArray();
			output.close();
			input.close();
		} catch (FileNotFoundException ex1) {
			ex1.printStackTrace();
		} catch (IOException ex1) {
			ex1.printStackTrace();
		}
		return data;
	}

	// byte数组到图片
	public static void byte2image(byte[] data, String path) {
		if (data.length < 3 || path.equals(""))
			return;
		try {
			FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
			imageOutput.write(data, 0, data.length);
			imageOutput.close();
			System.out.println("Make Picture success,Please find image in " + path);
		} catch (Exception ex) {
			System.out.println("Exception: " + ex);
			ex.printStackTrace();
		}
	}

	// byte数组到16进制字符串
	public String byte2string(byte[] data) {
		if (data == null || data.length <= 1)
			return "0x";
		if (data.length > 200000)
			return "0x";
		StringBuffer sb = new StringBuffer();
		int buf[] = new int[data.length];
		// byte数组转化成十进制
		for (int k = 0; k < data.length; k++) {
			buf[k] = data[k] < 0 ? (data[k] + 256) : (data[k]);
		}
		// 十进制转化成十六进制
		for (int k = 0; k < buf.length; k++) {
			if (buf[k] < 16)
				sb.append("0" + Integer.toHexString(buf[k]));
			else
				sb.append(Integer.toHexString(buf[k]));
		}
		return "0x" + sb.toString().toUpperCase();
	}

}
