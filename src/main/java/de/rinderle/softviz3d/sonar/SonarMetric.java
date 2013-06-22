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
package de.rinderle.softviz3d.sonar;

import de.rinderle.softviz3d.layout.interfaces.SourceMetric;

public class SonarMetric implements SourceMetric {

  private Double minValue;
  private Double maxValue;

  public SonarMetric(Double minValue, Double maxValue) {
    super();
    this.minValue = minValue;
    this.maxValue = maxValue;
  }

  @Override
  public Double getMinValue() {
    return minValue;
  }

  @Override
  public Double getMaxValue() {
    return maxValue;
  }

}