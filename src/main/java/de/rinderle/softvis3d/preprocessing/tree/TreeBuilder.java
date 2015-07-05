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
package de.rinderle.softvis3d.preprocessing.tree;

import com.google.inject.Inject;
import de.rinderle.softvis3d.dao.entity.ApiException;
import de.rinderle.softvis3d.dao.entity.ProjectWrapper;
import de.rinderle.softvis3d.dao.webservice.SonarAccess;
import de.rinderle.softvis3d.domain.VisualizationRequest;
import de.rinderle.softvis3d.domain.tree.RootTreeNode;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TreeBuilder {

  private static final Logger LOGGER = LoggerFactory.getLogger(TreeBuilder.class);

  @Inject
  private ProjectWrapper projectWrapper;

  public RootTreeNode createTreeStructure(final VisualizationRequest requestDTO) throws ApiException {
    LOGGER.info("Create tree structure for id " + requestDTO.getRootResourceId());

    final StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    String url = "http://localhost";
    SonarAccess sonarAccess = new SonarAccess(url, "admin", "admin");
    RootTreeNode result = projectWrapper.initializeProject(requestDTO);

    stopWatch.stop();

    LOGGER.info("Time for getting snapshots with " + result.getAllChildrenNodesSize() + " nodes: "
            + stopWatch.getTime() + " ms");

    return result;
  }

}
