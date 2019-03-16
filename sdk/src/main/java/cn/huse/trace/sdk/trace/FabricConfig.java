package cn.huse.trace.sdk.trace;

import java.io.File;
import java.util.Objects;

import cn.huse.trace.sdk.trace.bean.Chaincode;
import cn.huse.trace.sdk.trace.bean.Orderers;
import cn.huse.trace.sdk.trace.bean.Peers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FabricConfig {

	private static Logger log = LoggerFactory.getLogger(FabricConfig.class);

	/** 节点服务器对象 */
	private Peers peers;
	/** 排序服务器对象 */
	private Orderers orderers;
	/** 智能合约对象 */
	private Chaincode chaincode;
	/** channel-artifacts所在路径：默认channel-artifacts所在路径/xxx/WEB-INF/classes/fabric/channel-artifacts/ */
	private String channelArtifactsPath;
	/** crypto-config所在路径：默认crypto-config所在路径/xxx/WEB-INF/classes/fabric/crypto-config/ */
	private String cryptoConfigPath;
	private boolean openCATLS=true;
	private boolean registerEvent = false;
	private String directory;

	public FabricConfig() {
		// 默认channel-artifacts所在路径 /xxx/WEB-INF/classes/fabric/channel-artifacts/
		channelArtifactsPath = getChannlePath() + "/channel-artifacts/";
		// 默认crypto-config所在路径 /xxx/WEB-INF/classes/fabric/crypto-config/
		cryptoConfigPath = getChannlePath() + "/crypto-config/";
	}

	/**
	 * 默认fabric配置路径
	 *
	 * @return D:/installSoft/apache-tomcat-9.0.0.M21-02/webapps/xxx/WEB-INF/classes/fabric/channel-artifacts/
	 */
	private String getChannlePath() {
		String directorys = Objects.requireNonNull(FabricConfig.class.getClassLoader().getResource("fabric")).getFile();
		log.debug("directorys = " + directorys);
		File directory = new File(directorys);
		log.debug("directory = " + directory.getPath());
		return directory.getPath()+"fdsa";
	}

	public Peers getPeers() {
		return peers;
	}

	public void setPeers(Peers peers) {
		this.peers = peers;
	}

	public Orderers getOrderers() {
		return orderers;
	}

	public void setOrderers(Orderers orderers) {
		this.orderers = orderers;
	}

	public Chaincode getChaincode() {
		return chaincode;
	}

	public void setChaincode(Chaincode chaincode) {
		this.chaincode = chaincode;
	}

	public String getChannelArtifactsPath() {
		return channelArtifactsPath;
	}

	public void setChannelArtifactsPath(String channelArtifactsPath) {
		this.channelArtifactsPath = channelArtifactsPath;
	}

	public String getCryptoConfigPath() {
		return cryptoConfigPath;
	}

	public void setCryptoConfigPath(String cryptoConfigPath) {
		this.cryptoConfigPath = cryptoConfigPath;
	}

	public boolean isRegisterEvent() {
		return registerEvent;
	}

	/**
	 * 设置是否开启TLS
	 *
	 * @param openCATLS
	 *            是否
	 */
	public void openCATLS(boolean openCATLS) {
		this.openCATLS = openCATLS;
	}

	/**
	 * 获取是否开启TLS
	 *
	 * @return 是否
	 */
	public boolean openCATLS() {
		return openCATLS;
	}

	public void setRegisterEvent(boolean registerEvent) {
		this.registerEvent = registerEvent;
	}

	public String getDirectory() {
		return this.directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}
}
