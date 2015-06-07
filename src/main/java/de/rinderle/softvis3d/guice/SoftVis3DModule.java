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
package de.rinderle.softvis3d.guice;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import de.rinderle.softvis3d.VisualizationProcessor;
import de.rinderle.softvis3d.VisualizationProcessorBean;
import de.rinderle.softvis3d.cache.LayoutCacheService;
import de.rinderle.softvis3d.cache.LayoutCacheServiceBean;
import de.rinderle.softvis3d.cache.SnapshotCacheService;
import de.rinderle.softvis3d.cache.SnapshotCacheServiceBean;
import de.rinderle.softvis3d.layout.LayoutProcessor;
import de.rinderle.softvis3d.layout.LayoutProcessorBean;
import de.rinderle.softvis3d.layout.bottomUp.SnapshotVisitor;
import de.rinderle.softvis3d.layout.bottomUp.SnapshotVisitorBean;
import de.rinderle.softvis3d.layout.format.LayerFormatter;
import de.rinderle.softvis3d.layout.format.LayerFormatterBean;
import de.rinderle.softvis3d.postprocessing.PostProcessor;
import de.rinderle.softvis3d.postprocessing.PostProcessorBean;
import de.rinderle.softvis3d.preprocessing.PreProcessor;
import de.rinderle.softvis3d.preprocessing.PreProcessorBean;
import de.rinderle.softvis3d.preprocessing.dependencies.DependencyExpander;
import de.rinderle.softvis3d.preprocessing.dependencies.DependencyExpanderBean;
import de.rinderle.softvis3d.preprocessing.tree.TreeBuilder;
import de.rinderle.softvis3d.preprocessing.tree.TreeBuilderBean;
import de.rinderle.softvis3d.webservice.ExceptionJsonWriter;
import de.rinderle.softvis3d.webservice.ExceptionJsonWriterImpl;
import de.rinderle.softvis3d.webservice.config.ConfigWebserviceHandler;
import de.rinderle.softvis3d.webservice.config.ConfigWebserviceHandlerBean;
import de.rinderle.softvis3d.webservice.visualization.TreeNodeJsonWriter;
import de.rinderle.softvis3d.webservice.visualization.TreeNodeJsonWriterImpl;
import de.rinderle.softvis3d.webservice.visualization.VisualizationJsonWriter;
import de.rinderle.softvis3d.webservice.visualization.VisualizationJsonWriterImpl;
import de.rinderle.softvis3d.webservice.visualization.VisualizationWebserviceHandler;
import de.rinderle.softvis3d.webservice.visualization.VisualizationWebserviceHandlerBean;

public class SoftVis3DModule extends AbstractModule {
  @Override
  protected void configure() {
    this.bind(SnapshotCacheService.class).to(SnapshotCacheServiceBean.class);
    this.bind(DependencyExpander.class).to(DependencyExpanderBean.class);

    this.bind(VisualizationProcessor.class).to(VisualizationProcessorBean.class);
    this.bind(LayerFormatter.class).to(LayerFormatterBean.class);

    this.bind(PostProcessor.class).to(PostProcessorBean.class);

    this.bind(PreProcessor.class).to(PreProcessorBean.class);
    this.bind(TreeBuilder.class).to(TreeBuilderBean.class);

    this.bind(LayoutProcessor.class).to(LayoutProcessorBean.class);

    this.bind(LayoutCacheService.class).to(LayoutCacheServiceBean.class);

    this.bind(TreeNodeJsonWriter.class).to(TreeNodeJsonWriterImpl.class);
    this.bind(VisualizationJsonWriter.class).to(VisualizationJsonWriterImpl.class);
    this.bind(VisualizationWebserviceHandler.class).to(VisualizationWebserviceHandlerBean.class);
    this.bind(ConfigWebserviceHandler.class).to(ConfigWebserviceHandlerBean.class);
    this.bind(ExceptionJsonWriter.class).to(ExceptionJsonWriterImpl.class);

    this.install(new FactoryModuleBuilder().implement(SnapshotVisitor.class, SnapshotVisitorBean.class).build(
      SnapshotVisitorFactory.class));

  }
}
