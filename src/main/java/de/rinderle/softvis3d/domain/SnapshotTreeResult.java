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

import de.rinderle.softvis3d.domain.tree.RootTreeNode;

public class SnapshotTreeResult {

  private final SnapshotStorageKey snapshotStorageKey;
  private final RootTreeNode tree;
  private final MinMaxValue minMaxMetricFootprint;
  private final MinMaxValue minMaxMetricHeight;

  public SnapshotTreeResult(final SnapshotStorageKey snapshotStorageKey,
    final RootTreeNode tree, MinMaxValue minMaxMetricFootprint, MinMaxValue minMaxMetricHeight) {
    this.snapshotStorageKey = snapshotStorageKey;
    this.tree = tree;
    this.minMaxMetricFootprint = minMaxMetricFootprint;
    this.minMaxMetricHeight = minMaxMetricHeight;
  }

  public SnapshotStorageKey getStorageKey() {
    return this.snapshotStorageKey;
  }

  public RootTreeNode getTree() {
    return this.tree;
  }

  public MinMaxValue getMinMaxMetricFootprint() {
    return minMaxMetricFootprint;
  }

  public MinMaxValue getMinMaxMetricHeight() {
    return minMaxMetricHeight;
  }
}
