/*
 * SoftVis3D Sonar plugin
 * Copyright (C) 2014 Stefan Rinderle
 * stefan@rinderle.info
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package de.rinderle.softvis3d.dao.webservice;

import de.rinderle.softvis3d.dao.entity.ApiException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SonarAccess {

  private static final Logger LOGGER = LoggerFactory.getLogger(SonarAccess.class);

  /**
   * Sonar URL (i.e. http://localhost:9000/sonar).
   */
  private String sonarUrl;

  /**
   * Username for access Sonar WS API. Null for no authentication.
   */
  private String username;

  /**
   * Password for access Sonar WS API. Only used if username != null.
   */
  private String password;

  /**
   * Sonar host.
   */
  private String host;

  /**
   * Sonar port.
   */
  private int port;

  public SonarAccess() {
  }

  public SonarAccess(String sonarUrl, String username, String password) throws ApiException {
    initialize(sonarUrl, username, password);
  }

  public String getUrlAsResultString(String urlPath) throws IOException {
    HttpClient httpclient = HttpClientBuilder.create().build();
    try {

      // specify the host, protocol, and port
      HttpHost target = new HttpHost("localhost", 9000, "http");

      // specify the get request
      HttpGet getRequest = new HttpGet(urlPath);

      LOGGER.info("executing request to " + getRequest.toString());

      HttpResponse httpResponse = httpclient.execute(target, getRequest);
      HttpEntity entity = httpResponse.getEntity();

      return EntityUtils.toString(entity);

    } finally {
      // When HttpClient instance is no longer needed,
      // shut down the connection manager to ensure
      // immediate deallocation of all system resources
      httpclient.getConnectionManager().shutdown();
    }
  }

  private void initialize(String sonarUrl, String username, String password) throws ApiException {
    if (!sonarUrl.endsWith("/")) {
      this.sonarUrl = sonarUrl;
    } else {
      this.sonarUrl = sonarUrl.substring(0, sonarUrl.length() - 1);
    }
    this.username = username;
    this.password = password;
    if (sonarUrl.startsWith("http://")) {
      String withoutProtocol = sonarUrl.substring(7);
      if (withoutProtocol.contains(":")) {
        this.host = withoutProtocol.substring(0, withoutProtocol.indexOf(':'));
      } else if (withoutProtocol.contains("/")) {
        this.host = withoutProtocol.substring(0, withoutProtocol.indexOf('/'));
      } else {
        this.host = withoutProtocol;
      }
      if (withoutProtocol.contains(":")) {
        if (withoutProtocol.contains("/")) {
          this.port = Integer.valueOf(withoutProtocol.substring(withoutProtocol.indexOf(':') + 1, withoutProtocol
            .indexOf('/')));
        } else {
          this.port = Integer.valueOf(withoutProtocol.substring(withoutProtocol.indexOf(':') + 1));
        }
      } else {
        this.port = 80;
      }
    } else if (sonarUrl.startsWith("https://")) {
      String withoutProtocol = sonarUrl.substring(8);
      if (withoutProtocol.contains(":")) {
        this.host = withoutProtocol.substring(0, withoutProtocol.indexOf(':'));
      } else if (withoutProtocol.contains("/")) {
        this.host = withoutProtocol.substring(0, withoutProtocol.indexOf('/'));
      } else {
        this.host = withoutProtocol;
      }
      if (withoutProtocol.contains(":")) {
        if (withoutProtocol.contains("/")) {
          this.port = Integer.valueOf(withoutProtocol.substring(withoutProtocol.indexOf(':') + 1, withoutProtocol
            .indexOf('/')));
        } else {
          this.port = Integer.valueOf(withoutProtocol.substring(withoutProtocol.indexOf(':') + 1));
        }
      } else {
        this.port = 443;
      }
    } else {
      throw new ApiException("Unknown URL format: " + sonarUrl + " (forgot http:// before host?)");
    }
  }

}
