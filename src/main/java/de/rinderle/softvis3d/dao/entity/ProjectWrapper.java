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

import com.google.inject.Inject;
import de.rinderle.softvis3d.domain.VisualizationRequest;
import de.rinderle.softvis3d.domain.sonar.SonarSnapshot;
import de.rinderle.softvis3d.domain.sonar.SonarSnapshotBuilder;
import de.rinderle.softvis3d.domain.tree.RootTreeNode;
import de.rinderle.softvis3d.preprocessing.tree.PathWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.resources.Scopes;

import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulates the Project info.
 */
public class ProjectWrapper {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProjectWrapper.class);

  @Inject
  private ResourceAdapter resourceAdapter;

  public RootTreeNode initializeProject(final VisualizationRequest requestDTO) throws ApiException {
    List<Resource> allFiles = initProjectStructure(requestDTO);

    final PathWalker pathWalker = new PathWalker(requestDTO.getRootResourceId());

    for (Resource file : allFiles) {

      double footprintMeasure = findMeasureValue(file, requestDTO.getFootprintMetricKey());
      double heightMeasure = findMeasureValue(file, requestDTO.getHeightMetricKey());

      SonarSnapshot moduleElement = new SonarSnapshotBuilder((int) file.getId())
        .withPath(file.getName())
        .withFootprintMeasure(footprintMeasure)
        .withHeightMeasure(heightMeasure)
        .build();

      pathWalker.addPath(moduleElement);
    }

    return pathWalker.getTree();
  }

  private Double findMeasureValue(Resource file, String metricKey) {
    for (MetricResult metric : file.getMsr()) {
      if (metric.getKey().equals(metricKey)) {
        return Double.parseDouble(metric.getVal());
      }
    }

    LOGGER.error("No measures found " + file.toString());

    return 0.0;
  }

  List<Resource> initProjectStructure(final VisualizationRequest requestDTO) throws ApiException {
    Resource resource = resourceAdapter.getResourceById(requestDTO.getRootResourceId());

    List<Resource> result = new ArrayList<>();
    List<Resource> modules = resourceAdapter.getModules(resource.getId());

    if (modules.isEmpty()) {
      result.addAll(initModule(requestDTO, resource, false));
    } else {
      for (Resource module : modules) {
        result.addAll(initModule(requestDTO, module, true));
      }
    }

    return result;
  }

  private List<Resource> initModule(VisualizationRequest requestDTO, Resource module, boolean prefix)
          throws ApiException {
    LOGGER.info("init module " + module.getId());

    List<Resource> result = new ArrayList<>();

    List<Resource> directories = resourceAdapter.getDirectories(module.getId());

    for (Resource directory : directories) {
      // check really for directories, otherwise write big info message
      if (!directory.getScope().equals(Scopes.DIRECTORY)) {
        LOGGER.info("--------------------------NOT A DIR ------- " + directory.getId());
      }

      result.addAll(initDirectory(requestDTO, directory));
    }

    addModulePrefix(result, module, prefix);

    return result;
  }

  private void addModulePrefix(List<Resource> result, Resource module, boolean prefix) {
    for (Resource file : result) {
      if (prefix) {
        // TODO: get a short good key
        file.setName(module.getKey() + "/" + file.getName());
      }
    }
  }

  private List<Resource> initDirectory(VisualizationRequest requestDTO, Resource directory) throws
          ApiException {
    // could be extended to show also test sources by request parameter.
    final boolean includeTestSources = false;
    List<Resource> files = resourceAdapter.getFiles(requestDTO, directory.getId(), includeTestSources);

    for (Resource file : files) {
      // check really for directories, otherwise write big info message
      if (!file.getScope().equals(Scopes.FILE)) {
        LOGGER.info("--------------------------NOT A FILE ------- " + file.getId());
      } else {
        if (!directory.getName().equals("/")) {
          file.setName(directory.getName() + "/" + file.getName());
        }
      }
    }

    return files;

  }

}
