/*
 * SoftVis3D Sonar plugin
 * Copyright (C) 2014 Stefan Rinderle
 * stefan@rinderle.info
 *
 * SoftVis3D Sonar plugin can not be copied and/or distributed without the express
 * permission of Stefan Rinderle.
 */
package de.rinderle.softvis3d.webservice.visualization;

import de.rinderle.softvis3d.domain.graph.ResultPlatform;
import org.sonar.api.server.ws.Response;

import java.util.Map;

public interface VisualizationJsonWriter {

  void transformResponseToJson(Response response, Map<Integer, ResultPlatform> tree);
}