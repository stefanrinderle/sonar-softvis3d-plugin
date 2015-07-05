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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class VisualizationRequest {

  private final int rootSnapshotId;

  private final LayoutViewType viewType;

  private final String footprintMetricKey;
  private final String heightMetricKey;

  public VisualizationRequest(int rootSnapshotId, LayoutViewType viewType, String footprintMetricKey, String heightMetricKey) {
    this.rootSnapshotId = rootSnapshotId;

    this.viewType = viewType;

    this.footprintMetricKey = footprintMetricKey;
    this.heightMetricKey = heightMetricKey;
  }

  public int getRootSnapshotId() {
    return this.rootSnapshotId;
  }

  public LayoutViewType getViewType() {
    return this.viewType;
  }

  public String getFootprintMetricId() {
    return this.footprintMetricKey;
  }

  public String getHeightMetricId() {
    return this.heightMetricKey;
  }

  @Override
  public String toString() {
    return "VisualizationRequest{" + "rootSnapshotId=" + rootSnapshotId + ", viewType=" + viewType
      + ", footprintMetricId=" + footprintMetricKey + ", heightMetricId=" + heightMetricKey + '}';
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
      .append(rootSnapshotId, that.rootSnapshotId)
      .append(footprintMetricKey, that.footprintMetricKey)
      .append(heightMetricKey, that.heightMetricKey)
      .append(viewType, that.viewType)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .append(rootSnapshotId)
      .append(viewType)
      .append(footprintMetricKey)
      .append(heightMetricKey)
      .toHashCode();
  }
}
