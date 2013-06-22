/*
 * SoftViz3d Sonar plugin
 * Copyright (C) 2013 Stefan Rinderle
 * dev@sonar.codehaus.org
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
package de.rinderle.softviz3d.layout.helper;

import att.grappa.Graph;
import att.grappa.Node;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import static att.grappa.GrappaConstants.HEIGHT_ATTR;
import static att.grappa.GrappaConstants.POS_ATTR;
import static att.grappa.GrappaConstants.WIDTH_ATTR;

public class GraphDebugPrinter {

  public static String printSimpleGraphLayoutInfos(Graph graph) {
    StringBuilder builder = new StringBuilder();
    builder.append(graph.getId() + " " + graph.getName() + "\n");
    if (graph.getAttribute("bb") != null) {
      builder.append("bb: " + graph.getAttribute("bb").toString() + "\n");
    }

    for (Node node : graph.nodeElementsAsArray()) {
      builder.append("--" + node.getId() + " " + node.getName());
      builder.append(" " + node.getAttribute(POS_ATTR).toString());
      builder.append(" " + node.getAttribute(HEIGHT_ATTR) + " " + node.getAttribute(WIDTH_ATTR) + "\n");
    }

    return builder.toString();
  }

  public static String printFullGraph(Graph graph) {
    StringBuilder builder = new StringBuilder();
    StringOutputStream os = new StringOutputStream();
    builder.append("-----------------------\n\n");
    graph.printGraph(os);
    builder.append(os.toString());
    builder.append("-----------------------\n\n");
    return builder.toString();
  }

  public static void printGraphsWithAbsolutePosition(StringBuilder builder,
      Map<Integer, Graph> resultGraphs) {
    Iterator<Entry<Integer, Graph>> iterator = resultGraphs.entrySet()
        .iterator();
    builder.append("-------Result graphs with absolute position--------<br /><br />");
    iterator = resultGraphs.entrySet().iterator();
    Entry<Integer, Graph> graph;
    while (iterator.hasNext()) {
      graph = iterator.next();
      StringOutputStream os = new StringOutputStream();
      graph.getValue().printGraph(os);
      builder.append(os.toString());
    }

    builder.append("-----------------------<br /><br />");
    builder.append("-----------------------<br /><br />");
  }

  public static void printGraphsWithoutAbsolutePosition(StringBuilder builder,
      Map<Integer, Graph> resultGraphs) {
    builder.append("-------Result graphs without absolute position--------<br /><br />");
    Iterator<Entry<Integer, Graph>> iterator = resultGraphs.entrySet()
        .iterator();
    Entry<Integer, Graph> graph;
    while (iterator.hasNext()) {
      graph = iterator.next();
      builder.append(GraphDebugPrinter.printSimpleGraphLayoutInfos(graph
          .getValue()));
    }
    builder.append("-----------------------<br /><br />");
    builder.append("-----------------------<br /><br />");
  }
}