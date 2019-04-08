package cn.huse.trace.sdk.trace;

/**
 * @author: huanxi
 * @date: 2019/3/7 15:54
 */
public class Location {
//    private final static String IP = "111.230.251.119";
    private final static String IP = "47.107.102.147";
    private static String OrdererLocation = getLocation(7050);
    private static String Peer0Location = getLocation(7051);
    private static String peerEventHubLocation = getLocation(7053);
    private static String getLocation(int port) {
        return "grpc://" + IP + ":" + port;
    }

    public static String getIP() {
        return IP;
    }

    public static String getPeer0Location() {
        return Peer0Location;
    }

    public static String getPeerEventHubLocation() {
        return peerEventHubLocation;
    }

    public static String getOrdererLocation() {
        return OrdererLocation;
    }

    public static String getCaLocation() {
        return "http://" + IP + ":7054";
    }
}
