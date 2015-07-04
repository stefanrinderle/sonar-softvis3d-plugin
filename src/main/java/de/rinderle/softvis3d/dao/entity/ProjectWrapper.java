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
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;

import java.io.IOException;
import java.util.List;

/**
 * This class encapsulates the Project info.
 */
public class ProjectWrapper {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProjectWrapper.class);

  @Inject
  private ResourceAdapter resourceAdapter;

  public void initializeProject(final Integer id) throws IOException,
    ApiException, DocumentException, JAXBException {

    initProjectStructure(id);
  }

  void initProjectStructure(final Integer id) throws ApiException {
    Resource resource = resourceAdapter.getResourceById(id);

    List<Resource> modules = resourceAdapter.getModules(resource.getId());

    if (modules.isEmpty()) {
      initModule(resource);
    } else {
      for (Resource module : modules) {
        initModule(module);
      }
    }
  }

  private void initModule(Resource resource) {
    LOGGER.info("init module " + resource.getId());
  }

}
