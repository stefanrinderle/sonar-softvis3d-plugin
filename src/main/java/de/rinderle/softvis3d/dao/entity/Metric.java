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
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metric {

  private static final Logger LOGGER = LoggerFactory.getLogger(Metric.class);

  private String key;
  private String name;
  @JsonProperty("val_type")
  private String valueType;
  private boolean hidden;
  private int direction;
  private boolean qualitative;

  public void setKey(String key) {
    this.key = key;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setValueType(String valueType) {
    this.valueType = valueType;
  }

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }

  public void setDirection(int direction) {
    this.direction = direction;
  }

  public void setQualitative(boolean qualitative) {
    this.qualitative = qualitative;
  }

  public String getKey() {
    return key;
  }

  public String getName() {
    return name;
  }

  public ValueType getValueType() {
    try {
      return ValueType.valueOf(this.valueType);
    } catch (IllegalArgumentException e) {
      LOGGER.error("Unknown metric value type " + this.valueType);
      return ValueType.UNKNOWN;
    }
  }

  public boolean isHidden() {
    return hidden;
  }

  public int getDirection() {
    return direction;
  }

  public boolean isQualitative() {
    return qualitative;
  }
}
