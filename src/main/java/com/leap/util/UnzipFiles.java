package com.leap.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.leap.properties.PropertyHandler;
 

/**
 * @author LEAP
 */
public class UnzipFiles {
	private static Logger logger = Logger.getLogger(UnzipFiles.class.getName());
	public static void main(String[] args) {
		String zipFilePath = "C:/test/Zip samples/tmp.zip";
		File zipFile = new File(zipFilePath);
		String destDir = "C:/test/Zip samples";

		try {
			unzip(zipFile, destDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void unzip(File zipFile, String baseDir) throws IOException {

		String fileNameWithOutExt = FilenameUtils.removeExtension(zipFile.getName());
		File dir = new File(baseDir + File.separator + fileNameWithOutExt);
		// create output directory if it doesn't exist
		if (!dir.exists())
			dir.mkdirs();
		logger.debug("dir " + dir);
		FileInputStream fis = null;
		// buffer for read and write data to file
		byte[] buffer = new byte[1024];
		ZipInputStream zis = null;
		try {
			fis = new FileInputStream(zipFile);
			zis = new ZipInputStream(fis);
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();

				File newFile = new File(dir + File.separator + fileName);
				if (!ze.isDirectory()) {
					logger.debug("Unzipping to " + newFile.getAbsolutePath());
					// create directories for sub directories in zip
					logger.debug("Parent : " + newFile.getParent());
					new File(newFile.getParent()).mkdirs();

					FileOutputStream fos = new FileOutputStream(newFile);
					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.close();

				}
				// close this ZipEntry
				zis.closeEntry();
				ze = zis.getNextEntry();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			// close last ZipEntry
			zis.closeEntry();
			zis.close();
			fis.close();
		}

	}

}