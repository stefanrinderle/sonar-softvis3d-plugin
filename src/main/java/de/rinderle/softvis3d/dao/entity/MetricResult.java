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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * {"key":"complexity","val":8.0,"frmt_val":"8"}
 *
 * There is a val type: INT | BOOL | FLOAT | PERCENT | STRING | LEVEL
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricResult {

  private String key;
  private String val;

  public String getKey() {
    return key;
  }

  public String getVal() {
    return val;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setVal(String val) {
    this.val = val;
  }

  @Override
  public String toString() {
    return "MetricResult{" +
            "key='" + key + '\'' +
            ", val='" + val + '\'' +
            '}';
  }
}
