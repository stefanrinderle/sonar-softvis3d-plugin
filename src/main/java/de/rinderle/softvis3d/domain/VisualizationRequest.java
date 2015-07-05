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
package de.rinderle.softvis3d.domain;

import de.rinderle.softvis3d.dao.entity.Metric;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class VisualizationRequest {

  private final int rootResourceId;

  private final LayoutViewType viewType;

  private final Metric footprintMetric;
  private final Metric heightMetric;

  public VisualizationRequest(int rootResourceId, LayoutViewType viewType, Metric footprintMetric, Metric heightMetric) {
    this.rootResourceId = rootResourceId;

    this.viewType = viewType;

    this.footprintMetric = footprintMetric;
    this.heightMetric = heightMetric;
  }

  public int getRootResourceId() {
    return this.rootResourceId;
  }

  public LayoutViewType getViewType() {
    return this.viewType;
  }

  public String getFootprintMetricKey() {
    return this.footprintMetric.getKey();
  }

  public String getHeightMetricKey() {
    return this.heightMetric.getKey();
  }

  @Override
  public String toString() {
    return "VisualizationRequest{" + "rootResourceId=" + rootResourceId + ", viewType=" + viewType
      + ", footprintMetricId=" + footprintMetric + ", heightMetricId=" + heightMetric + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    VisualizationRequest that = (VisualizationRequest) o;

    return new EqualsBuilder()
      .append(rootResourceId, that.rootResourceId)
      .append(footprintMetric, that.footprintMetric)
      .append(heightMetric, that.heightMetric)
      .append(viewType, that.viewType)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .append(rootResourceId)
      .append(viewType)
      .append(footprintMetric)
      .append(heightMetric)
      .toHashCode();
  }

  public Metric getFootprintMetric() {
    return footprintMetric;
  }

  public Metric getHeightMetric() {
    return heightMetric;
  }
}
