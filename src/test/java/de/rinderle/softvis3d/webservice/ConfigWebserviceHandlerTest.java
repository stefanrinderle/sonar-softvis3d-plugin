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
package de.rinderle.softvis3d.webservice;

import de.rinderle.softvis3d.dao.entity.Metric;
import de.rinderle.softvis3d.dao.entity.MetricAdapter;
import de.rinderle.softvis3d.webservice.config.ConfigJsonWriter;
import de.rinderle.softvis3d.webservice.config.ConfigWebserviceHandler;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sonar.api.config.Settings;
import org.sonar.api.server.ws.Request;
import org.sonar.api.server.ws.Response;
import org.sonar.api.server.ws.WebService;
import org.sonar.api.utils.text.JsonWriter;
import org.sonar.api.utils.text.XmlWriter;

import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Ignore
public class ConfigWebserviceHandlerTest {

  private final Integer snapshotId = 123;

  @Mock
  private MetricAdapter metricAdapter;
  @Mock
  private ConfigJsonWriter configJsonWriter;

  @InjectMocks
  private final ConfigWebserviceHandler handler = new ConfigWebserviceHandler();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    final Settings settings = new Settings();
    settings.setProperty("metric1", "ncloc");
    settings.setProperty("metric2", "lines");
    this.handler.setSettings(settings);
  }

  @Test
  public void testConfigHandler() throws Exception {
    final Request request = this.createRequest();
    final Response response = this.createResponse();

    when(metricAdapter.getAllMetrics()).thenReturn(Collections.<Metric>emptyList());

    this.handler.handle(request, response);

    verify(configJsonWriter, times(1)).transform(
            any(JsonWriter.class), eq("ncloc"), eq("lines"), anyListOf(Metric.class), anyBoolean());
  }

  private Request createRequest() {
    return new Request() {
      @Override
      public WebService.Action action() {
        return null;
      }

      @Override
      public String method() {
        return null;
      }

      @Override
      public String param(final String key) {
        if ("snapshotId".equals(key)) {
          return ConfigWebserviceHandlerTest.this.snapshotId.toString();
        } else if ("resourceId".equals(key)) {
          return ConfigWebserviceHandlerTest.this.snapshotId.toString();
        } else {
          return "";
        }
      }
    };
  }

  private Response createResponse() {
    return new Response() {
      @Override
      public JsonWriter newJsonWriter() {
        return null;
      }

      @Override
      public XmlWriter newXmlWriter() {
        return null;
      }

      @Override
      public Response noContent() {
        return null;
      }

      @Override
      public Stream stream() {
        return null;
      }
    };
  }

}
