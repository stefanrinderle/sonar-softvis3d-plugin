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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by stefan on 12.07.15.
 */
public class LayoutViewTypeTest {

  @Test
  public void testValueOfRequestCity() throws Exception {
    final LayoutViewType result = LayoutViewType.valueOfRequest("city");
    assertEquals(result, LayoutViewType.CITY);
  }

  @Test
  public void testValueOfRequestDependency() throws Exception {
    final LayoutViewType result = LayoutViewType.valueOfRequest("dependency");
    assertEquals(result, LayoutViewType.DEPENDENCY);
  }

  @Test
  public void testValueOfRequestAnythingElse() throws Exception {
    final LayoutViewType result = LayoutViewType.valueOfRequest("ksudhgfifdgsuh");
    assertEquals(result, LayoutViewType.DEPENDENCY);
  }
}
