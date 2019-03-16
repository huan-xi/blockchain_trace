package cn.huse.trace.sdk.trace;

/**
 * @author: huanxi
 * @date: 2019/3/7 15:37
 */

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import cn.huse.trace.sdk.trace.bean.Chaincode;
import cn.huse.trace.sdk.trace.bean.Orderers;
import cn.huse.trace.sdk.trace.bean.Peers;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FabricManager {

    private static final String CHAINCODE_NAME = "cc";
    private static final String CHANNEL_NAME = "tracechannel";
    private final FabricConfig config;
    private ChaincodeManager manager;

    private static FabricManager instance = null;
    private static Logger log = LoggerFactory.getLogger(FabricManager.class);
    private String directory;

    public static FabricManager obtain(FabricConfig config)
            throws CryptoException, InvalidArgumentException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, TransactionException, IOException {
        if (null == instance) {
            synchronized (FabricManager.class) {
                if (null == instance) {
                    instance = new FabricManager(config);
                }
            }
        }
        return instance;
    }

    public static FabricManager getInstance() {
        return instance;
    }

    private FabricManager(FabricConfig config)
            throws CryptoException, InvalidArgumentException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, TransactionException, IOException {
        this.config=config;
        manager = new ChaincodeManager("Admin", getConfig());
    }

    /**
     * 获取节点服务器管理器
     *
     * @return 节点服务器管理器
     */
    public ChaincodeManager getManager() {
        return manager;
    }

    /**
     * 根据节点作用类型获取节点服务器配置
     * <p>
     * 服务器作用类型（1、执行；2、查询）
     *
     * @return 节点服务器配置
     */
    private FabricConfig getConfig() {
        FabricConfig config = new FabricConfig();
        config.setOrderers(getOrderers());
        config.setPeers(getPeers());
        config.setChaincode(getChaincode(CHANNEL_NAME,CHAINCODE_NAME , "github.com/chaincode/chaincode_example02/go/", "1.0"));
        config.setChannelArtifactsPath(getChannleArtifactsPath());
        config.setCryptoConfigPath(getCryptoConfigPath());
        return config;
    }

    private Orderers getOrderers() {
        Orderers orderer = new Orderers();
        orderer.setOrdererDomainName("example.com");
        orderer.addOrderer("orderer.example.com", Location.getOrdererLocation());
        return orderer;
    }

    /**
     * 获取节点服务器集
     *
     * @return 节点服务器集
     */
    private Peers getPeers() {
        Peers peers = new Peers();
        peers.setOrgName("org1");
        peers.setOrgMSPID("Org1MSP");
        peers.setOrgDomainName("org1.example.com");
        peers.addPeer("peer0.org1.example.com", "peer0.org1.example.com", Location.getPeer0Location(), Location.getPeerEventHubLocation(), Location.getCaLocation());
        return peers;
    }

    /**
     * 获取智能合约
     *
     * @param channelName      频道名称
     * @param chaincodeName    智能合约名称
     * @param chaincodePath    智能合约路径
     * @param chaincodeVersion 智能合约版本
     * @return 智能合约
     */
    private Chaincode getChaincode(String channelName, String chaincodeName, String chaincodePath, String chaincodeVersion) {
        Chaincode chaincode = new Chaincode();
        chaincode.setChannelName(channelName);
        chaincode.setChaincodeName(chaincodeName);
        chaincode.setChaincodePath(chaincodePath);
        chaincode.setChaincodeVersion(chaincodeVersion);
        chaincode.setDeployWaitTime(100000);
        chaincode.setTransactionWaitTime(100000);
        return chaincode;
    }

    /**
     * 获取channel-artifacts配置路径
     *
     * @return /WEB-INF/classes/fabric/channel-artifacts/
     */
    private String getChannleArtifactsPath() {
        return getDirectory() + "/channel-artifacts/";
    }

    private String getDirectory() {
        return config.getDirectory();
    }

    /**
     * 获取crypto-config配置路径
     *
     * @return /WEB-INF/classes/fabric/crypto-config/
     */
    private String getCryptoConfigPath() {
        return getDirectory() + "/crypto-config/";
    }

}
