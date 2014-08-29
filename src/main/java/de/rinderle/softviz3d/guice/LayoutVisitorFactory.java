/*
 * SoftViz3d Sonar plugin
 * Copyright (C) 2013 Stefan Rinderle
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
package de.rinderle.softviz3d.guice;

import org.sonar.api.config.Settings;

import com.google.inject.Inject;
import com.google.inject.Provider;

import de.rinderle.softviz3d.layout.calc.LayoutVisitor;
import de.rinderle.softviz3d.layout.calc.LayoutVisitorInterface;
import de.rinderle.softviz3d.layout.dot.DotExecutorInterface;
import de.rinderle.softviz3d.layout.interfaces.SourceMetric;

public class LayoutVisitorFactory implements LayoutVisitorInterfaceFactory {
private Provider<DotExecutorInterface> dotExcecutor;

  @Inject
  public LayoutVisitorFactory(Provider<DotExecutorInterface> dotExcecutor) {
    this.dotExcecutor = dotExcecutor;
  }

  public LayoutVisitorInterface create(Settings settings, SourceMetric metricFootprint, SourceMetric metricHeight) {
    return new LayoutVisitor(settings, metricFootprint, metricHeight, dotExcecutor.get());
  }
}