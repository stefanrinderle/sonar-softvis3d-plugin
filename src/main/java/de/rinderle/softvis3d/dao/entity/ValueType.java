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

/**
 * Created by stefan on 05.07.15.
 */
public enum ValueType {
  INT(Integer.class),
  FLOAT(Double.class),
  PERCENT(Double.class),
  BOOL(Boolean.class),
  STRING(String.class),
  MILLISEC(Integer.class),
  DATA(String.class),
  LEVEL(String.class),
  DISTRIB(String.class),
  RATING(Integer.class),
  WORK_DUR(Long.class),
  UNKNOWN(String.class);

  private final Class valueClass;

  ValueType(Class valueClass) {
    this.valueClass = valueClass;
  }

  private Class valueType() {
    return valueClass;
  }

  public static String[] names() {
    ValueType[] values = values();
    String[] names = new String[values.length];
    for (int i = 0; i < values.length; i += 1) {
      names[i] = values[i].name();
    }

    return names;
  }

  public boolean isNumeric() {
    return this.equals(INT) || this.equals(FLOAT) || this.equals(PERCENT);
  }
}
