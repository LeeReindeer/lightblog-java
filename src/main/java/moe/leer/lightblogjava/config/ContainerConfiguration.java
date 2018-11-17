package moe.leer.lightblogjava.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author leer
 * Created at 11/17/18 1:27 PM
 * https://gist.github.com/drissamri/8def1ce9322caab47e8e#file-containerconfiguration-java
 *
 * firewall:
 *  firewall-cmd --add-port=80/tcp
 *  firewall-cmd --add-port=433/tcp
 *
 *  for redirect 80 to 8080, 443 to 8433,
 *  not used in this app
 *  firewall-cmd --add-port=8080/tcp
 *  firewall-cmd --add-port=8433/tcp
 *  firewall-cmd --add-forward-port=port=80:proto=tcp:toport=8080
 *  firewall-cmd --add-forward-port=port=443:proto=tcp:toport=8443
 */

@Configuration
public class ContainerConfiguration {
  @Bean
  public ServletWebServerFactory servletContainer() {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
      @Override
      protected void postProcessContext(Context context) {
        SecurityConstraint securityConstraint = new SecurityConstraint();
        securityConstraint.setUserConstraint("CONFIDENTIAL");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern("/*");
        securityConstraint.addCollection(collection);
        context.addConstraint(securityConstraint);
      }
    };
    tomcat.addAdditionalTomcatConnectors(redirectHttpConnector());
    return tomcat;
  }

  /**
   * Redirect http(80) to https(443)
   *
   * @return the connector
   */
  private Connector redirectHttpConnector() {
    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setScheme("http");
    // open 80 and 443 ports in server, run in root
    connector.setPort(80);
    connector.setSecure(false);
    connector.setRedirectPort(443);

    return connector;
  }
}
