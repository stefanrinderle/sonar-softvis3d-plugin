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

import de.rinderle.softvis3d.dao.entity.Metric;
import org.sonar.api.utils.text.JsonWriter;

import java.util.List;

public class ConfigJsonWriter {

  public void transform(final JsonWriter jsonWriter, String metric1Key, String metric2Key, List<Metric> metrics, boolean hasDependencies) {
    jsonWriter.beginObject();
    jsonWriter.prop("hasDependencies", hasDependencies);
    this.transformMetricSettings(jsonWriter, metric1Key, metric2Key);
    this.transformMetrics(jsonWriter, metrics);
    jsonWriter.endObject();
    jsonWriter.close();
  }

  private void transformMetricSettings(JsonWriter jsonWriter, String metric1, String metric2) {
    jsonWriter.name("settings");
    jsonWriter.beginObject();
    jsonWriter.prop("metric1", metric1);
    jsonWriter.prop("metric2", metric2);
    jsonWriter.endObject();
  }

  private void transformMetrics(JsonWriter jsonWriter, List<Metric> metrics) {
    jsonWriter.name("metricsForSnapshot");
    jsonWriter.beginArray();

    for (Metric metric : metrics) {
      jsonWriter.beginObject();
      jsonWriter.prop("key", metric.getKey());
      jsonWriter.prop("name", metric.getName());
      jsonWriter.endObject();
    }

    jsonWriter.endArray();
  }

}
