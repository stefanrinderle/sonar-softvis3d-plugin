/*
 * SoftViz3d Sonar plugin
 * Copyright (C) 2013 Stefan Rinderle
 * dev@sonar.codehaus.org
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
package de.rinderle.softviz3d.layout.interfaces;

import java.util.List;

public interface SourceObject {

  /**
   * Unique identifier.
   */
  Integer getId();

  /**
   * Display name.
   */
  String getName();

  List<? extends SourceObject> getChildrenNodes();

  List<? extends SourceObject> getChildrenLeaves();

  List<Integer> getChildrenIds();

  Integer getDepth();

  /**
   * Delivers the building footprint metric.
   * @return null if no metric is available.
   */
  Double getMetricFootprint();

  /**
   * Delivers the building height metric.
   * @return null if no metric is available.
   */
  Double getMetricHeight();

}