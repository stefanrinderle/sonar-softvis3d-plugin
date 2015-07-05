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

import de.rinderle.softvis3d.dao.webservice.SonarAccess;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class MetricAdapterTest {

  @InjectMocks
  private MetricAdapter underTest;

  @Mock
  private SonarAccess sonarAccess;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

//  @Test
//  public void testGetMetricById() throws Exception {
//    final String testResultString = "[{\"key\":\"ncloc\",\"name\":\"Lines of code\",\"description\":\"Non Commenting Lines of Code\",\"domain\":\"Size\",\"qualitative\":false,\"user_managed\":false,\"direction\":-1,\"val_type\":\"INT\",\"hidden\":false}]";
//    when(sonarAccess.getUrlAsResultString(anyString())).thenReturn(testResultString);
//
//    String key = "ncloc";
//    Metric metric = underTest.getMetricById(key);
//
//    assertEquals(key, metric.getKey());
//  }

  @Test
  public void testGetAllMetrics() throws Exception {
    final String testResultString = "[{\"key\":\"lines\",\"name\":\"Lines\",\"description\":\"Lines\",\"domain\":\"Size\",\"qualitative\":false,\"user_managed\":false,\"direction\":-1,\"val_type\":\"INT\",\"hidden\":false},{\"key\":\"generated_lines\",\"name\":\"Generated Lines\",\"description\":\"Number of generated lines\",\"domain\":\"Size\",\"qualitative\":false,\"user_managed\":false,\"direction\":-1,\"val_type\":\"INT\",\"hidden\":false},{\"key\":\"ncloc\",\"name\":\"Lines of code\",\"description\":\"Non Commenting Lines of Code\",\"domain\":\"Size\",\"qualitative\":false,\"user_managed\":false,\"direction\":-1,\"val_type\":\"INT\",\"hidden\":false},{\"key\":\"ncloc_language_distribution\",\"name\":\"Lines of code per language\",\"description\":\"Non Commenting Lines of Code Distributed By Language\",\"domain\":\"Size\",\"qualitative\":false,\"user_managed\":false,\"direction\":-1,\"val_type\":\"DATA\",\"hidden\":false}]";
    when(sonarAccess.getUrlAsResultString(anyString())).thenReturn(testResultString);

    List<Metric> metrics = underTest.getFilteredMetrics();

    assertEquals(3, metrics.size());
    assertEquals(ValueType.INT, metrics.get(0).getValueType());
  }

}