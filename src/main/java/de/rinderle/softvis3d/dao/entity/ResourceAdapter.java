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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.inject.Inject;
import de.rinderle.softvis3d.dao.webservice.SonarAccess;
import de.rinderle.softvis3d.dao.webservice.UrlPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class ResourceAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProjectWrapper.class);

  @Inject
  private SonarAccess sonarAccess;

  public Resource getResourceById(final Integer id) throws ApiException {
    LOGGER.info("Retrieving project info for " + id);
    try {
      String input = sonarAccess.getUrlAsResultString(UrlPath.RESOURCES + id + UrlPath.DEPTH_0
        + UrlPath.JSON_SOURCE);

      List<Resource> result = getResources(input);

      return result.get(0);

    } catch (IOException e) {
      throw new ApiException(e);
    }
  }

  public List<Resource> getModules(long id) throws ApiException {
    try {
      String input = sonarAccess.getUrlAsResultString(UrlPath.RESOURCES + id + UrlPath.DEPTH_1
        + UrlPath.JSON_SOURCE + UrlPath.PROJECT_SCOPE);
      return getResources(input);
    } catch (IOException e) {
      throw new ApiException(e);
    }
  }

  private List<Resource> getResources(final String input) throws IOException {
    ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
    TypeFactory typeFactory = mapper.getTypeFactory();
    CollectionType collectionType = typeFactory.constructCollectionType(
      List.class, Resource.class);
    return mapper.readValue(input, collectionType);
  }
}
