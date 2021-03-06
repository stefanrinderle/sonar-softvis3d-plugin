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

public class VisualizationRequest {

  private final int rootSnapshotId;

  private final LayoutViewType viewType;

  private final int footprintMetricId;
  private final int heightMetricId;

  private final ScmInfoType scmInfoType;

  public VisualizationRequest(final int rootSnapshotId, final LayoutViewType viewType, final int footprintMetricId, final int heightMetricId, ScmInfoType scmInfoType) {
    this.rootSnapshotId = rootSnapshotId;

    this.viewType = viewType;

    this.footprintMetricId = footprintMetricId;
    this.heightMetricId = heightMetricId;
    this.scmInfoType = scmInfoType;
  }

  public int getRootSnapshotId() {
    return this.rootSnapshotId;
  }

  public LayoutViewType getViewType() {
    return this.viewType;
  }

  public int getFootprintMetricId() {
    return this.footprintMetricId;
  }

  public int getHeightMetricId() {
    return this.heightMetricId;
  }

  public ScmInfoType getScmInfoType() {
    return scmInfoType;
  }

  @Override
  public String toString() {
    return "VisualizationRequest{" +
            "rootSnapshotId=" + rootSnapshotId +
            ", viewType=" + viewType +
            ", footprintMetricId=" + footprintMetricId +
            ", heightMetricId=" + heightMetricId +
            ", scmInfoType=" + scmInfoType +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    VisualizationRequest that = (VisualizationRequest) o;

    if (rootSnapshotId != that.rootSnapshotId) return false;
    if (footprintMetricId != that.footprintMetricId) return false;
    if (heightMetricId != that.heightMetricId) return false;
    if (viewType != that.viewType) return false;
    return scmInfoType == that.scmInfoType;

  }

  @Override
  public int hashCode() {
    int result = rootSnapshotId;
    result = 31 * result + viewType.hashCode();
    result = 31 * result + footprintMetricId;
    result = 31 * result + heightMetricId;
    result = 31 * result + scmInfoType.hashCode();
    return result;
  }
}
