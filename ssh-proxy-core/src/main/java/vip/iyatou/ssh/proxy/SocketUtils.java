package vip.iyatou.ssh.proxy;


import javax.net.ServerSocketFactory;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * The type Socket utils.
 * @author spring
 */
public class SocketUtils {
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    /**
     * Instantiates a new Socket utils.
     */
    public SocketUtils() {
    }

    /**
     * Find available tcp port int.
     *
     * @return the int
     */
    public static int findAvailableTcpPort() {
        return findAvailableTcpPort(1024);
    }

    /**
     * Find available tcp port int.
     *
     * @param minPort the min port
     * @return the int
     */
    public static int findAvailableTcpPort(int minPort) {
        return findAvailableTcpPort(minPort, 65535);
    }

    /**
     * Find available tcp port int.
     *
     * @param minPort the min port
     * @param maxPort the max port
     * @return the int
     */
    public static int findAvailableTcpPort(int minPort, int maxPort) {
        return SocketUtils.SocketType.TCP.findAvailablePort(minPort, maxPort);
    }

    /**
     * Find available tcp ports sorted set.
     *
     * @param numRequested the num requested
     * @return the sorted set
     */
    public static SortedSet<Integer> findAvailableTcpPorts(int numRequested) {
        return findAvailableTcpPorts(numRequested, 1024, 65535);
    }

    /**
     * Find available tcp ports sorted set.
     *
     * @param numRequested the num requested
     * @param minPort      the min port
     * @param maxPort      the max port
     * @return the sorted set
     */
    public static SortedSet<Integer> findAvailableTcpPorts(int numRequested, int minPort, int maxPort) {
        return SocketUtils.SocketType.TCP.findAvailablePorts(numRequested, minPort, maxPort);
    }

    /**
     * Find available udp port int.
     *
     * @return the int
     */
    public static int findAvailableUdpPort() {
        return findAvailableUdpPort(1024);
    }

    /**
     * Find available udp port int.
     *
     * @param minPort the min port
     * @return the int
     */
    public static int findAvailableUdpPort(int minPort) {
        return findAvailableUdpPort(minPort, 65535);
    }

    /**
     * Find available udp port int.
     *
     * @param minPort the min port
     * @param maxPort the max port
     * @return the int
     */
    public static int findAvailableUdpPort(int minPort, int maxPort) {
        return SocketUtils.SocketType.UDP.findAvailablePort(minPort, maxPort);
    }

    /**
     * Find available udp ports sorted set.
     *
     * @param numRequested the num requested
     * @return the sorted set
     */
    public static SortedSet<Integer> findAvailableUdpPorts(int numRequested) {
        return findAvailableUdpPorts(numRequested, 1024, 65535);
    }

    /**
     * Find available udp ports sorted set.
     *
     * @param numRequested the num requested
     * @param minPort      the min port
     * @param maxPort      the max port
     * @return the sorted set
     */
    public static SortedSet<Integer> findAvailableUdpPorts(int numRequested, int minPort, int maxPort) {
        return SocketUtils.SocketType.UDP.findAvailablePorts(numRequested, minPort, maxPort);
    }

    private enum SocketType {
        /**
         * The Tcp.
         */
        TCP {
            @Override
            protected boolean isPortAvailable(int port) {
                try {
                    ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket(port, 1, InetAddress.getByName("localhost"));
                    serverSocket.close();
                    return true;
                } catch (Exception var3) {
                    return false;
                }
            }
        },
        /**
         * The Udp.
         */
        UDP {
            @Override
            protected boolean isPortAvailable(int port) {
                try {
                    DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName("localhost"));
                    socket.close();
                    return true;
                } catch (Exception var3) {
                    return false;
                }
            }
        };

        private SocketType() {
        }

        /**
         * Is port available boolean.
         *
         * @param var1 the var 1
         * @return the boolean
         */
        protected abstract boolean isPortAvailable(int var1);

        private int findRandomPort(int minPort, int maxPort) {
            int portRange = maxPort - minPort;
            return minPort + SocketUtils.RANDOM.nextInt(portRange + 1);
        }

        /**
         * Find available port int.
         *
         * @param minPort the min port
         * @param maxPort the max port
         * @return the int
         */
        int findAvailablePort(int minPort, int maxPort) {
            int portRange = maxPort - minPort;
            int searchCounter = 0;

            while(searchCounter <= portRange) {
                int candidatePort = this.findRandomPort(minPort, maxPort);
                ++searchCounter;
                if (this.isPortAvailable(candidatePort)) {
                    return candidatePort;
                }
            }

            throw new IllegalStateException(String.format("Could not find an available %s port in the range [%d, %d] after %d attempts", this.name(), minPort, maxPort, searchCounter));
        }

        /**
         * Find available ports sorted set.
         *
         * @param numRequested the num requested
         * @param minPort      the min port
         * @param maxPort      the max port
         * @return the sorted set
         */
        SortedSet<Integer> findAvailablePorts(int numRequested, int minPort, int maxPort) {
            SortedSet<Integer> availablePorts = new TreeSet();
            int attemptCount = 0;

            while(true) {
                ++attemptCount;
                if (attemptCount > numRequested + 100 || availablePorts.size() >= numRequested) {
                    if (availablePorts.size() != numRequested) {
                        throw new IllegalStateException(String.format("Could not find %d available %s ports in the range [%d, %d]", numRequested, this.name(), minPort, maxPort));
                    } else {
                        return availablePorts;
                    }
                }

                availablePorts.add(this.findAvailablePort(minPort, maxPort));
            }
        }
    }
}
