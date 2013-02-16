package extension.tsu.gettweet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * メインクラス.
 *
 * @author ExtensionTsu
 */
public class Main {

    /**
     * メインメソッド. http://morado106.blog106.fc2.com/blog-entry-58.html を参考にしてみる．
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            twitter.setOAuthConsumer(TwitterService.consumerKey, TwitterService.consumerSecret);
            RequestToken reqToken = twitter.getOAuthRequestToken();
            AccessToken accToken = null;
            while (null == accToken) {
                // コンソールに表示されたアドレスにアクセスし、Twitterにログイン
                System.out.println(reqToken.getAuthorizationURL());
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                // 標準入力から取得したpinを入力
                String pin = br.readLine();
                try {
                    accToken = twitter.getOAuthAccessToken(reqToken, pin);
                } catch (TwitterException e) {
                    System.out.println("ERROR sc: " + e.getStatusCode() + " msg: " + e.getMessage());
                    return;
                }
            }
            //アクセストークン…これを保持すれば毎回ログイン処理しなくて良い？
            System.out.println("AccessToken       = " + accToken.getToken());
            System.out.println("AccessTokenSeclet = " + accToken.getTokenSecret());
            //ユーザタイムラインを取得してみる
            List<Status> statusList;
            statusList = twitter.getUserTimeline();
            for (Status status : statusList) {
                System.out.println(status.getUser().getName() + ":" + status.getText());
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
