package com.mailor.app.images.services;

import static java.lang.String.format;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

import javax.imageio.ImageIO;

/**
 * Helper methods for testing code related to image modifications.
 * 
 * @author krablak
 *
 */
public final class ImagesTestUtil {

	private ImagesTestUtil() {
	}

	/**
	 * Creates human "readable" string representation of image as RGB colors
	 * matrix. <br>
	 * <br>
	 * White image sample result: <code>
	 * <br>
	 * <br>
	 * 	[-1][-1][-1][-1][-1]<br>
	 *	[-1][-1][-1][-1][-1]<br>
	 *	[-1][-1][-1][-1][-1]<br>
	 *	[-1][-1][-1][-1][-1]<br>
	 *	[-1][-1][-1][-1][-1]<br>
	 *<br>
	 * </code>
	 * 
	 * Each number represents point in image with number representing its color.
	 * 
	 * @param srcImg
	 *            source image. On case of <code>null</code> value returns empty
	 *            string.
	 * @return string representation of passed image.
	 */
	public static String toRGBString(BufferedImage srcImg) {
		StringBuilder resStrBldr = new StringBuilder();
		if (srcImg != null) {
			for (int x = 0; x < srcImg.getWidth(); x++) {
				for (int y = 0; y < srcImg.getHeight(); y++) {
					resStrBldr.append("[").append(srcImg.getRGB(x, y)).append("]");
				}
				resStrBldr.append("\n");
			}
		}
		return resStrBldr.toString();
	}

	/**
	 * Creates MD5 digest of given image string representation.
	 * 
	 * @param srcImg
	 *            source image.
	 * @return MD5 digest representing given image.
	 */
	public static String toStringDigest(BufferedImage srcImg) {
		try {
			return new String(Base64.getEncoder().encode(MessageDigest.getInstance("MD5").digest(toRGBString(srcImg).getBytes("UTF-8"))));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new RuntimeException("Unexpected error when creating MD5 diggest from image.");
		}
	}

	/**
	 * Saves image into temporary directory and returns path to created file.
	 * 
	 * @param srcImg
	 *            source image. Should be not <code>null</code>.
	 * @param format
	 *            output format (png,jpg,gif). Should be not <code>null</code>.
	 * @return path to created file as string.
	 */
	public static String saveToTemp(BufferedImage srcImg, String format) {
		File outFile = Paths.get(System.getProperty("java.io.tmpdir"), new Date().getTime() + "." + format).toFile();
		try {
			ImageIO.write(srcImg, format, outFile);
		} catch (IOException e) {
			throw new RuntimeException("Unexpected error when saving image file into temp directory.");
		}
		return outFile.getAbsolutePath();
	}

	/**
	 * Loads image from file on classpath.
	 * 
	 * @param pathOnClassPath
	 *            path to image file on classpath.
	 * @return loaded image from classpath.
	 */
	public static BufferedImage loadImage(String pathOnClassPath) {
		try {
			try (InputStream imgIs = ImagesTestUtil.class.getResourceAsStream(pathOnClassPath)) {
				if (imgIs != null) {
					return ImageIO.read(imgIs);
				} else {
					throw new RuntimeException(format("Cannot find image from path on classpath '%s'", pathOnClassPath));
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(format("Cannot load image from path on classpath '%s'", pathOnClassPath));
		}
	}

}
