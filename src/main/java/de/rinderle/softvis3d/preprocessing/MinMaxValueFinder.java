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
package de.rinderle.softvis3d.preprocessing;

import de.rinderle.softvis3d.domain.MinMaxValue;
import de.rinderle.softvis3d.domain.tree.RootTreeNode;
import de.rinderle.softvis3d.domain.tree.TreeNode;
import de.rinderle.softvis3d.domain.tree.ValueTreeNode;

/**
 * Created by stefan on 05.07.15.
 */
public class MinMaxValueFinder {

  public MinMaxValue getMinMaxMetricFootprint(RootTreeNode tree) {
    return findFootprintMinMaxValues(tree, new MinMaxValue(Double.MAX_VALUE, Double.MIN_VALUE));
  }

  public MinMaxValue getMinMaxMetricHeight(RootTreeNode tree) {
    return findHeightMinMaxValues(tree, new MinMaxValue(Double.MAX_VALUE, Double.MIN_VALUE));
  }

  private MinMaxValue findHeightMinMaxValues(TreeNode tree, MinMaxValue minMaxValue) {
    for (ValueTreeNode leave : tree.getChildrenLeaves()) {
      if (leave.getHeightMetricValue() < minMaxValue.getMinValue()) {
        minMaxValue.setMinValue(leave.getHeightMetricValue());
      }
      if (leave.getHeightMetricValue() > minMaxValue.getMaxValue()) {
        minMaxValue.setMaxValue(leave.getHeightMetricValue());
      }
    }

    for (TreeNode node : tree.getChildrenNodes()) {
      minMaxValue = this.findHeightMinMaxValues(node, minMaxValue);
    }

    return minMaxValue;
  }

  private MinMaxValue findFootprintMinMaxValues(TreeNode tree, MinMaxValue minMaxValue) {

    for (ValueTreeNode leave : tree.getChildrenLeaves()) {
      if (leave.getFootprintMetricValue() < minMaxValue.getMinValue()) {
        minMaxValue.setMinValue(leave.getFootprintMetricValue());
      }
      if (leave.getFootprintMetricValue() > minMaxValue.getMaxValue()) {
        minMaxValue.setMaxValue(leave.getFootprintMetricValue());
      }
    }

    for (TreeNode node : tree.getChildrenNodes()) {
      minMaxValue = this.findFootprintMinMaxValues(node, minMaxValue);
    }

    return minMaxValue;
  }
}
