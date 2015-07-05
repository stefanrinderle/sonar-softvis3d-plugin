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
import de.rinderle.softvis3d.domain.sonar.SonarSnapshot;
import de.rinderle.softvis3d.domain.sonar.SonarSnapshotBuilder;
import de.rinderle.softvis3d.domain.tree.RootTreeNode;
import de.rinderle.softvis3d.preprocessing.tree.PathWalker;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * This class encapsulates the Project info.
 */
public class ProjectWrapper {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProjectWrapper.class);

  @Inject
  private ResourceAdapter resourceAdapter;

  public RootTreeNode initializeProject(final long id) throws ApiException {
    List<Resource> allFiles = initProjectStructure(id);

    final PathWalker pathWalker = new PathWalker((int) id);

    for (Resource file : allFiles) {
      SonarSnapshot moduleElement = new SonarSnapshotBuilder((int) file.getId())
              .withPath(file.getName())
              .withFootprintMeasure(50.0)
              .withHeightMeasure(50.0)
              .build();

      pathWalker.addPath(moduleElement);
    }

    return pathWalker.getTree();
  }

  List<Resource> initProjectStructure(final long id) throws ApiException {
    Resource resource = resourceAdapter.getResourceById(id);

    List<Resource> result = new ArrayList<>();
    List<Resource> modules = resourceAdapter.getModules(resource.getId());

    if (modules.isEmpty()) {
      result.addAll(initModule(resource, false));
    } else {
      for (Resource module : modules) {
        result.addAll(initModule(module, true));
      }
    }

    return result;
  }

  private List<Resource> initModule(Resource module, boolean prefix) throws ApiException {
    LOGGER.info("init module " + module.getId());

    List<Resource> result = new ArrayList<>();

    List<Resource> directories = resourceAdapter.getDirectories(module.getId());

    for (Resource directory : directories) {
      // check really for directories, otherwise write big info message
      if (!directory.getScope().equals("DIR")) {
        LOGGER.info("--------------------------NOT A DIR ------- " + directory.getId());
      }

      result.addAll(initDirectory(directory));

    }

    for (Resource file : result) {
      // check really for directories, otherwise write big info message
      if (!file.getScope().equals("FIL")) {
        LOGGER.info("--------------------------NOT A FILE ------- " + file.getId());
      } else {
        if (prefix) {
          LOGGER.info("---Would set prefix");
//           TODO: get a short good key
          file.setName(module.getKey() + "/" + file.getName());
        }
      }
    }


    return result;
  }

  private List<Resource> initDirectory(Resource directory) throws ApiException {
    List<Resource> files = resourceAdapter.getFiles(directory.getId());

    for (Resource file : files) {
      // check really for directories, otherwise write big info message
      if (!file.getScope().equals("FIL")) {
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
