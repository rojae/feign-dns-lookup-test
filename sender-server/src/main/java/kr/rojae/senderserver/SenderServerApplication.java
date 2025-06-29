package kr.rojae.senderserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.security.Security;

@SpringBootApplication
@EnableFeignClients
public class SenderServerApplication {

    public static void main(String[] args) {
        showNetworkConfig();
        SpringApplication.run(SenderServerApplication.class, args);
    }

    /**
     * --------------------------------------------------------------
     * Need JVM OPTION : --add-exports java.base/sun.net=ALL-UNNAMED
     * --------------------------------------------------------------
     * Note:
     * The JVM option -Dnetworkaddress.cache.ttl=0 might be overridden
     * by the value configured in java.security,
     * which usually takes precedence on JDK 17 to 21.
     * So If you want to modify ttl option
     * --------------------------------------------------------------
     * First way, Adding JVM Option
     * -Dnetworkaddress.cache.ttl=N
     * -Dnetworkaddress.cache.negative.ttl=N
     * -Dnetworkaddress.cache.stale.ttl=N (Jdk >= 17)
     * If this does not work, please try the second option:
     * --------------------------------------------------------------
     * Second way, Modify java.security
     * cat $JAVA_HOME/conf/security/java.security | grep ttl
     * --------------------------------------------------------------
     * Plus:
     * 0 means no caching; the JVM performs a fresh DNS lookup on every request.
     * -1 means permanent caching until the JVM is restarted.
     * Any positive value (>0) means caching for that many seconds,
     * after which the name will be re-resolved.
     */
    private static void showNetworkConfig() {
        int neg = sun.net.InetAddressCachePolicy.getNegative();
        int pos = sun.net.InetAddressCachePolicy.get();
        System.out.println("------------------------------------------");
        System.out.println("Network TTL Configuration");
        System.out.println("> positive TTL = " + pos);
        System.out.println("> negative TTL = " + neg);
        System.out.println("------------------------------------------");
    }


}
