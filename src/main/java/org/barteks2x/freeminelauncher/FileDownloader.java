package org.barteks2x.freeminelauncher;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileDownloader {

	protected final int size = 1024;
	protected String directory;

	public FileDownloader(String directory) {
		this.directory = directory;
		File f = new File(directory);
		f.mkdirs();
		f.mkdir();
	}

	public void download(URL url, String localFileName) {
		OutputStream outStream = null;
		URLConnection uCon;

		InputStream is = null;
		try {
			byte[] buf;
			int ByteRead, ByteWritten = 0;
			outStream = new BufferedOutputStream(new FileOutputStream(directory + File.separator +
					localFileName));

			uCon = url.openConnection();
			is = uCon.getInputStream();
			buf = new byte[size];
			while ((ByteRead = is.read(buf)) != -1) {
				outStream.write(buf, 0, ByteRead);
				ByteWritten += ByteRead;
			}
			Logger.getLogger(FileDownloader.class.getName()).info("Downloaded Successfully.");
			Logger.getLogger(FileDownloader.class.getName()).log(Level.INFO,
																 "File name:\"{0}\"\nNo ofbytes :{1}",
																 new Object[]{localFileName,
				ByteWritten});
		} catch (Exception e) {
			Logger.getLogger(FileDownloader.class.getName()).log(Level.SEVERE, "Unable to download file: " +
					url, e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (outStream != null) {
					outStream.close();
				}
			} catch (IOException e) {
				Logger.getLogger(FileDownloader.class.getName()).log(Level.SEVERE,
																	 "Error while downloading file :" +
						url, e);
			}
		}
	}

	public void download(URL url) {
		download(url, new File(url.getFile()).getName());

	}

	public void downloadFile(URL url, String fileName) {
		download(url, fileName);
	}
}