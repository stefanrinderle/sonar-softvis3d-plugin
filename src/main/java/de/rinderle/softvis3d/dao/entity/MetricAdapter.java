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
import com.google.inject.Singleton;
import de.rinderle.softvis3d.dao.webservice.SonarAccess;
import de.rinderle.softvis3d.dao.webservice.UrlPath;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@Singleton
public class MetricAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(MetricAdapter.class);

  @Inject
  private SonarAccess sonarAccess;

  private List<Metric> availableMetrics;
  private List<Metric> filteredMetrics;

  public MetricAdapter() {
    super();
    LOGGER.info("Constructor MetricAdapter");
  }

  public Metric getMetricByKey(String key) {
    for (Metric metric : this.availableMetrics) {
      if (metric.getKey().equals(key)) {
        return metric;
      }
    }

    LOGGER.error("Metric not found " + key);
    return null;
  }

  public List<Metric> getFilteredMetrics() throws ApiException {
    initialize();

    return this.filteredMetrics;
  }

  private void initialize() throws ApiException {
    if (availableMetrics == null) {
      initializeMetrics();
    }
  }

  private void initializeMetrics() throws ApiException {
    try {
      String input = sonarAccess.getUrlAsResultString(UrlPath.METRIC);
      this.availableMetrics = getMetrics(input);

      this.filteredMetrics = filterMetrics();
    } catch (IOException e) {
      throw new ApiException(e);
    }
  }

  private List<Metric> filterMetrics() {
    List<Metric> result = new ArrayList<>();
    for (Metric metric : this.availableMetrics) {
      if (metric.getValueType().isNumeric() && !metric.isHidden()) {
        result.add(metric);
      }
    }
    return result;
  }

  private List<Metric> getMetrics(final String input) throws IOException {
    ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
    TypeFactory typeFactory = mapper.getTypeFactory();
    CollectionType collectionType = typeFactory.constructCollectionType(
      List.class, Metric.class);
    return mapper.readValue(input, collectionType);
  }
}
