package org.gatlin.util.io;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.gatlin.util.Consts;

public class ZipWriter {

	private byte[] buffer;
	private OutputStream out;
	private ZipOutputStream zip;     
	
	public ZipWriter() {
		this(512);
	}
	
	public ZipWriter(int bufferSize) {
		this(bufferSize, new ByteArrayOutputStream(bufferSize));
	}
	
	public ZipWriter(int bufferSize, OutputStream out) {
		this.out = out;
		this.buffer = new byte[bufferSize];
		this.zip = new ZipOutputStream(this.out, Consts.UTF_8);
	}
	
	public void wrap(String name, InputStream in) throws Exception {
		this.zip.putNextEntry(new ZipEntry(name));
		int readCounts = 0;
		while((readCounts = in.read(buffer)) > 0)
			this.zip.write(buffer , 0 , readCounts);
		this.zip.closeEntry();
	}
	
	public void close() throws Exception {
		if (null != zip)
			this.zip.close();
	}
	
	public OutputStream getOut() {
		return out;
	}
}
