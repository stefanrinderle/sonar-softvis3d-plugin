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
package de.rinderle.softvis3d.dao.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.inject.Inject;
import de.rinderle.softvis3d.dao.webservice.SonarAccess;
import de.rinderle.softvis3d.dao.webservice.UrlPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class MetricAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(MetricAdapter.class);

  @Inject
  private SonarAccess sonarAccess;

  public Metric getMetricById(final String key) throws ApiException {
    LOGGER.info("Retrieving metric info for " + key);
    try {
      String input = sonarAccess.getUrlAsResultString(UrlPath.METRIC + UrlPath.SLASH + key);

      List<Metric> result = getMetrics(input);

      return result.get(0);

    } catch (IOException e) {
      throw new ApiException(e);
    }
  }

  public List<Metric> getAllMetrics() throws ApiException {
    try {
      String input = sonarAccess.getUrlAsResultString(UrlPath.METRIC);
      return getMetrics(input);
    } catch (IOException e) {
      throw new ApiException(e);
    }
  }

  private List<Metric> getMetrics(final String input) throws IOException {
    ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
    TypeFactory typeFactory = mapper.getTypeFactory();
    CollectionType collectionType = typeFactory.constructCollectionType(
      List.class, Metric.class);
    return mapper.readValue(input, collectionType);
  }
}
