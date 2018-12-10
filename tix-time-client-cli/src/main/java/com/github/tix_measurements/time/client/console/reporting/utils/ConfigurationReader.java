package com.github.tix_measurements.time.client.console.reporting.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.tix_measurements.time.client.console.Setup;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.yaml.snakeyaml.Yaml;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class ConfigurationReader {
    private static ConfigurationReader instance = null;
    private ConfigurationData configurationData;
    public String getServerIp() {
        return configurationData.getServerIp();
    }

    public int getServerPort() {
        return configurationData.getServerPort();
    }

    public int getClientPort() {
        return configurationData.getClientPort();
    }

    public String getServerUrl() {
        return configurationData.getServerUrl();
    }


    private CloseableHttpClient client;

    public ConfigurationReader(){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream configFileIs = Setup.class.getClassLoader()
                .getResourceAsStream("Application.yml");
        try {
            configurationData = mapper.readValue(configFileIs, ConfigurationData.class);
            setCloseableHttpClient();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ConfigurationReader getInstance() {
        if (instance == null)
            instance = new ConfigurationReader();

        return instance;
    }
    private boolean serverIsHttps(){
        String serverUrl = configurationData.getServerUrl();
        return serverUrl.matches("(https://).*");
    }
    private void setCloseableHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        if (serverIsHttps()) {
            final SSLContext sslContext = new SSLContextBuilder()
                    .loadTrustMaterial(null, (certificate, authType) -> true).build();

            client = HttpClients.custom()
                    .setSSLContext(sslContext)
                    .setSSLHostnameVerifier(new NoopHostnameVerifier())
                    .build();
        }else{
            client = HttpClients.custom()
                    .build();
        }
    }
    public CloseableHttpClient getCloseableHttpClient(){
        return client;
    }
}
class ConfigurationData{
    private String serverUrl;
    private String serverIp;
    private int serverPort;
    private int clientPort;


    public String getServerIp() {
        return serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }


    public int getClientPort() {
        return clientPort;
    }

    public String getServerUrl() {
        return serverUrl;
    }
}