package org.gatlin.util.io;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTPConnection {

	private static final Logger logger = LoggerFactory.getLogger(SFTPConnection.class);

	private Session session;
	private ChannelSftp channel;

	/** FTP 端口 */
	private int port;
	/** FTP 服务器地址IP地址 */
	private String host;
	/** FTP 登录用户名 */
	private String username;
	/** FTP 登录密码 */
	private String password;
	/** 私钥文件地址 */
	private String priKeyFile;

	public void init() throws Exception {
		try {
			JSch jsch = new JSch();
			if (null != priKeyFile)
				jsch.addIdentity(priKeyFile);
			session = jsch.getSession(username, host, port);
			if (null != password)
				session.setPassword(password);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			Channel channelw = session.openChannel("sftp");
			channelw.connect();
			channel = (ChannelSftp) channelw;
			logger.info(String.format("sftp server host:[%s] port:[%s] is connect successfull", host, port));
		} catch (JSchException e) {
			logger.error("Cannot connect to specified sftp server : {}:{} \n {}", host, port, e);
			throw e;
		}
	}
	
	public void upload(String directory, String fileName, InputStream in) throws Exception {
		try {
			channel.cd(directory);
		} catch (SftpException e) {
			logger.warn("directory is not exist");
			channel.mkdir(directory);
			channel.cd(directory);
		}
		channel.put(in, fileName);
		logger.info("File {} is upload successful to sftp server {}", fileName, host);
	}

	public void close() {
		if (null != channel) {
			if (channel.isConnected()) {
				channel.disconnect();
				logger.info("sftp is closed already");
			}
		}
		if (null != session) {
			if (session.isConnected()) {
				session.disconnect();
				logger.info("sftp ssh session is closed already");
			}
		}
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setPriKeyFile(String priKeyFile) {
		this.priKeyFile = priKeyFile;
	}
}
