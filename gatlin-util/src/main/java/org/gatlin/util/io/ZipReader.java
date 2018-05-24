package org.gatlin.util.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipReader {

	private byte[] buffer;
	private ZipInputStream zip;

	public void unzip(File unzipFile) throws Exception {
		FileOutputStream fileOut = null;
		try {
			this.zip = new ZipInputStream(new BufferedInputStream(new FileInputStream(unzipFile)));
			ZipEntry entry = null;
			while ((entry = this.zip.getNextEntry()) != null) {
				File file = new File(unzipFile.getParent() + File.separator + entry.getName());
				if (entry.isDirectory())
					file.mkdirs();
				else {
					File parent = file.getParentFile();
					if (!parent.exists())
						parent.mkdirs();
					fileOut = new FileOutputStream(file);
					int readCounts = 0;
					while ((readCounts = zip.read(buffer)) > 0)
						fileOut.write(buffer, 0, readCounts);
				}
				zip.closeEntry();
			}
		} finally {
			if (null != zip) 
				zip.close();
			if (null != fileOut) 
				fileOut.close();
		}

	}
}
