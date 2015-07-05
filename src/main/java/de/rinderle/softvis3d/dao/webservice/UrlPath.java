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
package de.rinderle.softvis3d.dao.webservice;

import org.sonar.api.resources.Scopes;

public class UrlPath {

  // First level
  public static final String RESOURCES = "/api/resources?resource=";
  public static final String METRIC = "/api/metrics";

  // Second level
  public static final String DEPTH_0 = "&depth=0";
  public static final String DEPTH_1 = "&depth=1";

  public static final String PROJECT_SCOPE = "&scopes=" + Scopes.PROJECT;
  public static final String SLASH = "/";
  public static final String COMMA = ",";

  public static final String JSON_SOURCE = "&format=json";

  public static final String QUALIFIERS = "&qualifiers=";
  public static final String METRICS = "&metrics=";
}
