package vip.iyatou;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.JSchException;
import vip.iyatou.ssh.proxy.SshProxyConfig;
import vip.iyatou.ssh.proxy.SshProxyManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author chqiang
 */
public class App {
    public static void main(String[] args) throws IOException, JSchException {
        String path = args[0];
        System.out.println("Use config " + path);
        ObjectMapper objectMapper = new ObjectMapper();

        List<SshProxyConfig> configs = objectMapper.readValue(new File(path), new TypeReference<List<SshProxyConfig>>() {
        });

        SshProxyManager manager = SshProxyManager.getSSHProxyManager(configs);
        System.out.println("Press any key to exit...");
        System.in.read();

        manager.shutdown();
        System.out.println("Bye");
    }
}
