package com.lnsoft.device.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lnsoft.core.log.exception.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class BarCode {
	private final static Log log = LogFactory.getLog(BarCode.class);

	// /**
	//  * 生成条形码标签
	//  *
	//  * @param drawStr1
	//  * @param drawStr2
	//  * @param drawStr3
	//  * @param warnStr
	//  * @param devcode
	//  * @return
	//  */
	// public static String createBarImage(String drawStr1, String drawStr2,
	// 									String drawStr3, String warnStr, String devcode) throws IOException {
	// 	int imageWidth = 880;// 图片的宽度
	// 	int imageHeight = 213;// 图片的高度
	//
	// 	String[] fontNames = new String[] { "微软雅黑", "黑体", Font.SANS_SERIF };
	// 	Font lbFont = null;
	// 	Font wsFont = null;
	// 	for (String fontName : fontNames) {
	// 		lbFont = new Font(fontName, Font.PLAIN, 28);
	// 		wsFont = new Font(fontName, Font.PLAIN, 44);
	// 		if (lbFont.canDisplay('a')) {
	// 			break;
	// 		}
	// 	}
	// 	if (lbFont == null) {
	// 		log.error("字体错误，请联系运维人员处理");
	// 		return null;
	// 	}
	// 	BufferedImage image = new BufferedImage(imageWidth, imageHeight,
	// 			BufferedImage.TYPE_INT_RGB);
	//
	// 	Graphics2D graphics = image.createGraphics();
	// 	graphics.setColor(Color.WHITE);
	// 	graphics.fillRect(0, 0, imageWidth, imageHeight);
	// 	graphics.setColor(Color.BLACK);
	//
	// 	InputStream logois = BarCode.class.getResourceAsStream("/face/printBarCode/logo.png");
	// 	BufferedImage logo = null;
	// 	BufferedImage barcode = null;
	// 	try {
	// 		if (logois == null) {
	// 			throw new BaseException("face/printBarCode/logo.png为空");
	// 		}
	// 		logo = ImageIO.read(logois);
	// 		barcode = createBarCode(devcode);
	// 	} catch (IOException e) {
	// 		throw new BaseException(e.getMessage());
	// 	} finally {
	// 		if (logois != null) {
	// 			try {
	// 				logois.close();
	// 			} catch (IOException e) {
	// 				throw new BaseException(e.getMessage());
	// 			}
	// 		}
	// 	}
	//
	// 	if (logo != null) {
	// 		graphics.drawImage(logo, 0, 0, null);
	// 	}
	// 	if (barcode != null) {
	// 		graphics.drawImage(barcode, 390, 59, null);
	// 	}
	//
	// 	graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	// 	graphics.setFont(lbFont);
	//
	// 	int str_len = 26;
	// 	graphics.drawString(getSubString(drawStr1, str_len), 3, 130);
	// 	graphics.drawString(getSubString(drawStr2, str_len), 3, 167);
	// 	graphics.drawString(getSubString(drawStr3, str_len + 10), 3, 204);
	// 	graphics.setFont(wsFont);
	// 	graphics.drawString(warnStr, 443, 50);
	//
	// 	graphics.dispose();
	// 	FileOutputStream fos = null;
	// 	try {
	// 		String filename = Long.toString(System.currentTimeMillis()) + ".png";
	// 		String filepath = getFilePath() + filename;
	// 		fos = new FileOutputStream(filepath);
	// 		ImageIO.write(image, "png", fos);
	// 		log.info("标签图片生成成功！");
	// 		return filename;
	// 	} catch (Exception e) {
	// 		throw new BaseException("标签图片生成失败！");
	// 	} finally {
	// 		if (fos != null){
	// 			try {
	// 				fos.close();
	// 			} catch (IOException e){
	// 				throw new IOException("关流失败:" + e.getMessage());
	// 			}
	// 		}
	// 	}
	// }

	/**
	 * 生成条形码
	 *
	 * @param devCode
	 * @return
	 */
	public static BufferedImage createBarCode(String devCode) {
		int bWidth = 486;
		int bHeight = 146;
		BufferedImage barcodeL = new BufferedImage(bWidth, bHeight, BufferedImage.TYPE_INT_RGB);
		try {
			JBarcode jbc = new JBarcode(Code128Encoder.getInstance(),
					WidthCodedPainter.getInstance(),
					BaseLineTextPainter.getInstance());
			BufferedImage barcode = null;
			jbc.setShowText(false);
			barcode = jbc.createBarcode(devCode);
			Graphics2D g = barcodeL.createGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, bWidth, bHeight);
			g.setColor(Color.BLACK);
			int w = barcode.getWidth();
			int h = barcode.getHeight();

			g.drawImage(barcode.getSubimage(10, 0, w - 20, h), 0, 10, bWidth,
					bHeight - 40, null);
			String[] fontNames = new String[] { "微软雅黑", "黑体", Font.SANS_SERIF };
			Font bfont = null;
			for (String fontName : fontNames) {
				bfont = new Font(fontName, Font.PLAIN, 30);
				if (bfont.canDisplay('a')) {
					break;
				}
			}
			if (bfont == null) {
				log.error("字体错误，请联系运维人员处理");
			} else {
				g.setFont(bfont);
			}
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.drawString(devCode, 180, bHeight);

		} catch (InvalidAtributeException e) {
			log.warn(e.getMessage());
		}
		return barcodeL;
	}

	// 获取文件绝对路径
	public static String getRealPath() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String path = request.getSession().getServletContext().getRealPath(File.separator);
		path += "Common"+File.separator+"printBarCode"+File.separator;
		return path;
	}

	// 创建文件目录
	public static String getFilePath() {
		String filepath = getRealPath();
		// log.info(filepath);
		File dir = new File(filepath);
		if (!dir.exists()) {
			log.info("标签存储目录不存在,正在创建此目录...");
			if (dir.mkdirs()) {
				log.info("标签存储目录创建成功!");
			} else {
				log.info("标签存储目录创建失败!");
			}
		}
		return filepath;
	}

	// 删除文件
	public static boolean removeBarCode(String[] filenames) {
		String v_pro = getRealPath();
		String v_filepath;
		for (int i = 0; i < filenames.length; i++) {
			v_filepath = v_pro + filenames[i];
			deleteFile(v_filepath);
		}
		log.info("标签已清空！");
		return true;
	}

	public static boolean deleteFile(String filepath) {
		File file = new File(filepath);
		return file.delete();
	}

	private static String getSubString(String str, int length) {
		int count = 0;
		int offset = 0;
		char[] c = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] > 256) {
				offset = 2;
				count += 2;
			} else {
				offset = 1;
				count++;
			}
			if (count == length) {
				return str.substring(0, i + 1);
			}
			if ((count == length + 1 && offset == 2)) {
				return str.substring(0, i);
			}
		}
		return str;
	}

	// --------------二维码生成---------------//
	/**
	 * 生成二维码
	 *
	 * @param devCode
	 * @return
	 */
	public static BufferedImage createQrBarCode(String devCode, int QrLength) {
		BufferedImage QrBarCode = null;
		try {
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.MARGIN, 0);
			BitMatrix bmx = new MultiFormatWriter().encode(devCode, BarcodeFormat.QR_CODE, QrLength, QrLength, hints);
			QrBarCode = MatrixToImageWriter.toBufferedImage(bmx);
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return QrBarCode;
	}

	// /**
	//  * 生成二维码标签-通信专业(兄弟打印机)
	//  * @param drawStr1
	//  * @param drawStr2
	//  * @param drawStr3
	//  * @param devcode
	//  * @return
	//  */
	// public static String createQrBarImage(String drawStr1, String drawStr2,
	// 									  String drawStr3, String devcode) {
	// 	int imageWidth = 880;// 图片的宽度
	// 	int imageHeight = 213;// 图片的高度
	//
	// 	String[] fontNames = new String[] { "黑体", Font.SANS_SERIF };
	// 	Font lbFont = null;
	// 	for (String fontName : fontNames) {
	// 		lbFont = new Font(fontName, Font.BOLD, 30);
	// 		if (lbFont.canDisplay('a')) {
	// 			break;
	// 		}
	// 	}
	// 	if (lbFont == null) {
	// 		log.error("字体错误，请联系运维人员处理");
	// 		return null;
	// 	}
	// 	BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
	//
	// 	Graphics2D graphics = image.createGraphics();
	// 	graphics.setColor(Color.WHITE);
	// 	graphics.fillRect(0, 0, imageWidth, imageHeight);
	// 	graphics.setColor(Color.BLACK);
	//
	// 	int x1 = 211;
	// 	int x2 = 230;
	// 	int y1 = 70;
	// 	int y2 = 140;
	//
	// 	graphics.setStroke(new BasicStroke(3.0f));
	// 	graphics.drawLine(2, 2, imageWidth, 2); // 横线1
	// 	graphics.drawLine(x1, y1, imageWidth, y1); // 横线2
	// 	graphics.drawLine(x1, y2, imageWidth, y2); // 横线3
	// 	graphics.drawLine(2, imageHeight - 2, imageWidth, imageHeight - 2); // 横线4
	//
	// 	graphics.drawLine(2, 2, 2, imageHeight);// 竖线1
	// 	graphics.drawLine(x1, 2, x1, imageHeight);// 竖线2
	// 	graphics.drawLine(imageWidth - 2, 2, imageWidth - 2, imageHeight);// 竖线3
	//
	// 	BufferedImage barcode = null;
	// 	try {
	// 		barcode = createQrBarCode(devcode, 200);
	// 	} catch (Exception e) {
	// 		log.warn(e.getMessage());
	// 	}
	//
	// 	if (barcode != null) {
	// 		graphics.drawImage(barcode, 7, 7, 200, 200, null);
	// 	}
	//
	// 	graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	// 	graphics.setFont(lbFont);
	//
	// 	int str_len = 40;
	// 	String subStr1 = getSubString(drawStr1, str_len);
	// 	if(subStr1.equals(drawStr1)){
	// 		graphics.drawString(drawStr1, x2, 50);
	// 	}else{
	// 		graphics.drawString(subStr1, x2, 35);
	// 		graphics.drawString(drawStr1.substring(subStr1.length()), x2, 65);
	// 	}
	//
	// 	graphics.drawString(getSubString(drawStr2, str_len), x2, 120);
	// 	graphics.drawString(getSubString(drawStr3, str_len), x2, 190);
	//
	// 	graphics.dispose();
	// 	FileOutputStream fos = null;
	// 	try {
	// 		String filename = Long.toString(System.currentTimeMillis())
	// 				+ ".png";
	// 		String filepath = getFilePath() + filename;
	// 		fos = new FileOutputStream(filepath);
	// 		ImageIO.write(image, "png", fos);
	// 		fos.close();
	// 		log.info("标签图片生成成功！");
	// 		return filename;
	// 	} catch (Exception e) {
	// 		log.warn("标签图片生成失败！", e);
	// 		log.info("标签图片生成失败！");
	// 		try {
	// 			if (fos != null) {
	// 				fos.close();
	// 			}
	// 		} catch (IOException e1) {
	// 			log.error("关流失败!", e1);
	// 		}
	// 	}
	// 	return null;
	// }


	private static Font BASE_FONT;

	static {
		try (InputStream is = BarCode.class.getClassLoader().getResourceAsStream("fonts/simhei.ttf")){

			if (is == null) {
				throw new RuntimeException("字体文件 fonts/simhei.ttf 不存在");
			}

			BASE_FONT = Font.createFont(Font.TRUETYPE_FONT, is);

		} catch (Exception e) {
			throw new RuntimeException("加载黑体失败", e);
		}
	}


	private static Font getBaseFont(float size) {
		return BASE_FONT.deriveFont(Font.BOLD, size);
	}

	/**
	 * 生成二维码标签-信息专业
	 *
	 * @param drawStr1
	 * @param drawStr2
	 * @param drawStr3
	 * @param deviceCode
	 * @return
	 */
	public static BufferedImage createQrBarImageInfo(String drawTitle, String drawStr1, String drawStr2,
													 String drawStr3, String drawStr4, String deviceCode) {

		int imageWidth = 880;// 图片的宽度
		int imageHeight = 213;// 图片的高度

		// String[] fontNames = new String[] { "黑体", Font.SANS_SERIF };
		// Font strFont = null;
		// Font titleFont = null;
		// for (String fontName : fontNames) {
		// 	strFont = new Font(fontName, Font.BOLD, 24);
		// 	titleFont = new Font(fontName, Font.BOLD, 44);
		// 	if (strFont.canDisplay('a')) {
		// 		break;
		// 	}
		// }
		// if (strFont == null) {
		// 	log.error("字体错误，请联系运维人员处理");
		// 	return null;
		// }
		BufferedImage image = new BufferedImage(imageWidth, imageHeight,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D graphics = image.createGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(Color.BLACK);

		InputStream logois = BarCode.class.getResourceAsStream("/printBarCodeImages/logo2.png");

		BufferedImage logo = null;
		BufferedImage qrbarcode = null;
		try {
			if (logois == null) {
				throw new ServiceException("printBarCodeImages/logo2.png不存在");
			}
			logo = ImageIO.read(logois);
			qrbarcode = createQrBarCode(deviceCode, 213);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} finally {
			closeInputStream(logois);
		}

		if (logo != null) {
			graphics.drawImage(logo, 0, 0, null);
		}


		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		//绘制标题 设备资产标签
		graphics.setFont(getBaseFont(44f));
		graphics.drawString(drawTitle, 270, 40);
		//绘制内容 设备信息
		graphics.setFont(getBaseFont(24f));
		int strX = 166;
		graphics.drawString(drawStr1, strX, 90);
		graphics.drawString(drawStr2, strX, 120);
		graphics.drawString(drawStr3, strX, 150);
		graphics.drawString(drawStr4, strX, 180);

		if (qrbarcode != null) {
			graphics.drawImage(qrbarcode, 666, 0, null);
		}

		int lineX1 = 278;
		int lineX2 = 650;
		graphics.setStroke(new BasicStroke(3.0f));
		graphics.drawLine(lineX1, 95, lineX2, 95); // 横线1
		graphics.drawLine(lineX1, 125, lineX2, 125); // 横线2
		graphics.drawLine(lineX1, 155, lineX2, 155); // 横线3
		graphics.drawLine(lineX1, 185, lineX2, 185); // 横线4

		graphics.dispose();
		return image;
	}

	private static  <T extends Closeable> void closeInputStream(T resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				log.error("关流失败!");
			}
		}
	}

}
