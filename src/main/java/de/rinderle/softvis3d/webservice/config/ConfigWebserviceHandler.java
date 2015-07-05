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
package de.rinderle.softvis3d.webservice.config;

import com.google.inject.Inject;
import de.rinderle.softvis3d.dao.entity.Metric;
import de.rinderle.softvis3d.dao.entity.MetricAdapter;
import de.rinderle.softvis3d.webservice.AbstractWebserviceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.config.Settings;
import org.sonar.api.server.ws.Request;
import org.sonar.api.server.ws.RequestHandler;
import org.sonar.api.server.ws.Response;
import org.sonar.api.utils.text.JsonWriter;

import java.util.List;

public class ConfigWebserviceHandler extends AbstractWebserviceHandler implements RequestHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigWebserviceHandler.class);

  @Inject
  private ConfigJsonWriter configJsonWriter;

  @Inject
  private MetricAdapter metricAdapter;

  private Settings settings;

  public void setSettings(final Settings settings) {
    this.settings = settings;
  }

  @Override
  public void handleRequest(final Request request, final Response response) throws Exception {
    final Integer resourceId = Integer.valueOf(request.param("resourceId"));
    LOGGER.info("ConfigWebserviceHandler snapshot: " + resourceId + " resource " + resourceId);

    final String metric1Key = this.settings.getString("metric1");
    final String metric2Key = this.settings.getString("metric2");

    final List<Metric> metrics = metricAdapter.getFilteredMetrics();

    final boolean hasDependencies = false;//this.daoService.hasDependencies(id);

    final JsonWriter jsonWriter = response.newJsonWriter();
    configJsonWriter.transform(jsonWriter, metric1Key, metric2Key, metrics, hasDependencies);
  }



}
