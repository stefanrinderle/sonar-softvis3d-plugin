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
package de.rinderle.softvis3d.dao;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.measures.Metric;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by stefanrinderle on 05.02.16.
 */
public class SonarDaoTest {

  @Mock
  private DatabaseSession session;

  @InjectMocks
  private SonarDao underTest;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetDistinctMetricsBySnapshotIdEmptyList() throws Exception {
    final List<Metric> metrics = new ArrayList<>();
    when(session.getResults(org.sonar.api.measures.Metric.class)).thenReturn(metrics);

    underTest.getDistinctMetricsBySnapshotId(null);
  }

  @Test
  public void testGetDistinctMetricsBySnapshotIdEmptySingleEntry() throws Exception {
    final List<Metric> metrics = new ArrayList<>();
    metrics.add(createMetric(1, "key1"));
    when(session.getResults(eq(org.sonar.api.measures.Metric.class))).thenReturn(metrics);

    underTest.getDistinctMetricsBySnapshotId(null);
  }

  @Test
  public void testGetDistinctMetricsBySnapshotIdEmptyMultipleEntry() throws Exception {
    final List<Metric> metrics = new ArrayList<>();
    metrics.add(createMetric(1, "key1"));
    metrics.add(createMetric(2, "key2"));
    metrics.add(createMetric(3, "key3"));
    when(session.getResults(eq(org.sonar.api.measures.Metric.class))).thenReturn(metrics);

    underTest.getDistinctMetricsBySnapshotId(null);
  }

  /**
   * Exception comes from Metric class de.rinderle.softvis3d.base.domain.Metric.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetDistinctMetricsBySnapshotIdKeyNull() throws Exception {
    final List<Metric> metrics = new ArrayList<>();
    metrics.add(createMetric(null, "key"));
    when(session.getResults(eq(org.sonar.api.measures.Metric.class))).thenReturn(metrics);

    underTest.getDistinctMetricsBySnapshotId(null);
  }

  @Test
  public void testGetDistinctMetricsBySnapshotIdHiddenNull() throws Exception {
    final List<Metric> metrics = new ArrayList<>();
    final Metric metric = createMetric(1, "key");
    metric.setHidden(null);
    metrics.add(metric);
    when(session.getResults(eq(org.sonar.api.measures.Metric.class))).thenReturn(metrics);

    underTest.getDistinctMetricsBySnapshotId(null);
  }

  @Test
  public void testGetDistinctMetricsBySnapshotIdTypeNull() throws Exception {
    final List<Metric> metrics = new ArrayList<>();
    final Metric metric = createMetric(2, "key");
    metric.setType(null);
    metrics.add(metric);
    when(session.getResults(eq(org.sonar.api.measures.Metric.class))).thenReturn(metrics);

    underTest.getDistinctMetricsBySnapshotId(null);
  }

  @Test
  public void testGetDistinctMetricsBySnapshotIdEnabledNull() throws Exception {
    final List<Metric> metrics = new ArrayList<>();
    final Metric metric = createMetric(3, "key");
    metric.setEnabled(null);
    metrics.add(metric);
    when(session.getResults(eq(org.sonar.api.measures.Metric.class))).thenReturn(metrics);

    underTest.getDistinctMetricsBySnapshotId(null);
  }

  private Metric createMetric(final Integer id, final String key) {
    Metric.Builder builder = new Metric.Builder(key, "name", Metric.ValueType.BOOL);
    builder.setDescription("abc");
    Metric metric = builder.create();
    metric.setId(id);
    return metric;
  }

}