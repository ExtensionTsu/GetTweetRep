package extension.tsu.gettweet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import twitter4j.auth.AccessToken;

/**
 * config.propertiesを保持.
 * XMLを使うべきかもしれないがその件は保留.
 *
 * @author ExtensionTsu
 */
public class Config {
    private final static String CONFIG_HEADER = "GetTweet Config File.";
    private final static File configFile = new File("config.properties");
    private static Config instance = null;
    private Properties config;
    private String consumerKey;
    private String consumerSecret;
    private String token;
    private String tokenSeclet;

    /**
     * コンストラクタ.
     * 
     * @throws IOException 
     */
    private Config() throws IOException {
        this.config = new Properties();
        this.config.load(new BufferedReader(new FileReader(configFile)));
        this.consumerKey = this.config.getProperty("gettweet.ConsumerKey");
        this.consumerSecret = this.config.getProperty("gettweet.ConsumerSecret");
        this.token = this.config.getProperty("gettweet.AccessToken");
        this.tokenSeclet = this.config.getProperty("gettweet.AccessTokenSeclet");
    }

    /**
     * 唯一のインスタンスの取得.
     * 
     * @return 唯一のインスタンス
     * @throws IOException 
     */
    public static Config getInstance() throws IOException {
        synchronized (Config.class) {
            if (instance == null) {
                instance = new Config();
            }
        }
        return instance;
    }
    
    /**
     * config.propertiesの保存.
     */
    public void save() throws IOException {
        this.config.setProperty("gettweet.ConsumerKey", this.consumerKey);
        this.config.setProperty("gettweet.ConsumerSecret", this.consumerSecret);
        this.config.setProperty("gettweet.AccessToken", this.token);
        this.config.setProperty("gettweet.AccessTokenSeclet", this.tokenSeclet);
        this.config.store(new BufferedWriter(new FileWriter(configFile)), CONFIG_HEADER); 
    }

    /**
     * consumerKeyの取得．
     * 
     * @return the consumerKey
     */
    public String getConsumerKey() {
        return consumerKey;
    }

    /**
     * consumerSecretの取得．
     * 
     * @return the consumerSecret
     */
    public String getConsumerSecret() {
        return consumerSecret;
    }

    /**
     * tokenの取得．
     * 
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * tokenの設定．
     * 
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * tokenSecletの取得．
     * 
     * @return the tokenSeclet
     */
    public String getTokenSeclet() {
        return tokenSeclet;
    }

    /**
     * tokenSecletの設定．
     * 
     * @param tokenSeclet the tokenSeclet to set
     */
    public void setTokenSeclet(String tokenSeclet) {
        this.tokenSeclet = tokenSeclet;
    }

    /**
     * AccessTokenの取得．
     * 
     * @return AccessToken
     */
    public AccessToken getAccessToken() {
        if (this.token == null || this.token == null || "".equals(this.token) || "".equals(this.tokenSeclet)) {
            return null;
        }
        return new AccessToken(this.token, this.tokenSeclet);
    }
}
