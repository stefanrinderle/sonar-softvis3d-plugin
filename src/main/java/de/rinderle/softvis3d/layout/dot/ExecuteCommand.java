/*
 * SoftVis3D Sonar plugin
 * Copyright (C) 2014 Stefan Rinderle
 * stefan@rinderle.info
 *
 * SoftVis3D Sonar plugin can not be copied and/or distributed without the express
 * permission of Stefan Rinderle.
 */
package de.rinderle.softvis3d.layout.dot;

public interface ExecuteCommand {

  String executeCommandReadAdot(String command, String inputGraph) throws DotExecutorException;

  String executeCommandReadErrorStream(String command);

}