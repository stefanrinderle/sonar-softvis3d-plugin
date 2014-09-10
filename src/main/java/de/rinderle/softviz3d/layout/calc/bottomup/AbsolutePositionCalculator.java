/*
 * SoftViz3d Sonar plugin
 * Copyright (C) 2013 Stefan Rinderle
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
package de.rinderle.softviz3d.layout.calc.bottomup;

import att.grappa.Graph;
import att.grappa.GrappaBox;
import att.grappa.GrappaPoint;
import att.grappa.Node;
import de.rinderle.softviz3d.layout.calc.topdown.LayoutVisitorImpl;
import de.rinderle.softviz3d.layout.interfaces.SoftViz3dConstants;
import de.rinderle.softviz3d.sonar.SonarSnapshot;
import de.rinderle.softviz3d.tree.ResourceTreeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static att.grappa.GrappaConstants.*;

public class AbsolutePositionCalculator {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(LayoutVisitorImpl.class);

  private static final int MIN_BUILDING_HEIGHT = 10;

  private Map<Integer, Graph> inputGraphs;

  private Map<Integer, GrappaPoint> innerGraphTranslation;

  private double rootBbMax;

private ResourceTreeService resourceTreeService;

  public AbsolutePositionCalculator(Map<Integer, Graph> inputGraphList, ResourceTreeService resourceTreeService) {
    this.inputGraphs = inputGraphList;
    this.resourceTreeService = resourceTreeService;

    this.innerGraphTranslation = new HashMap<Integer, GrappaPoint>();
  }

  public void calculate(SonarSnapshot source) {
    Graph graph = inputGraphs.get(source.getId());
    GrappaBox bb = (GrappaBox) graph.getAttributeValue("bb");

    // this will be used as max building height
    this.rootBbMax = Math.max(bb.getWidth(), bb.getHeight());

    this.addTranslationToLayer(source.getId(), new GrappaPoint(0, 0), 0);
  }

  private void addTranslationToLayer(Integer sourceId, GrappaPoint posTranslation, Integer height3d) {
      LOGGER.debug("AbsolutePositionCalculator addTranslationToLayer " + sourceId);

    // inputGraphs --> Map<Integer, Graph>
    // Step 1 - search the graph for the source given
    Graph graph = inputGraphs.get(sourceId);
    GrappaBox bb = (GrappaBox) graph.getAttributeValue("bb");

    // Step 2 - set translation for the graph itself (will be a layer later)
    GrappaBox translatedBb = new GrappaBox(posTranslation.getX(), posTranslation.getY(), bb.getWidth(), bb.getHeight());
    graph.setAttribute("bb", translatedBb);

    graph.setAttribute(SoftViz3dConstants.LAYER_HEIGHT_3D, height3d.toString());

    GrappaPoint pos;
    double nodeLocationX;
    double nodeLocationY;

    // Step 3 - for all leaves, just add the parent point3d changes
    for (Node leaf : graph.nodeElementsAsArray()) {
      pos = (GrappaPoint) leaf.getAttributeValue(POS_ATTR);

      innerGraphTranslation.put(Integer.valueOf(leaf.getAttributeValue("id").toString()), pos);

      leaf.setAttribute(SoftViz3dConstants.LAYER_HEIGHT_3D, height3d.toString());

      // set the position of the node
      nodeLocationX = posTranslation.getX() + pos.getX() - translatedBb.getWidth() / 2;
      nodeLocationY = posTranslation.getY() + pos.getY() + translatedBb.getHeight() / 2;
      pos.setLocation(nodeLocationX, nodeLocationY);

      Double width = (Double) leaf.getAttributeValue(WIDTH_ATTR);
      // keep some distance to each other
      width = width * SoftViz3dConstants.DPI_DOT_SCALE;
      leaf.setAttribute(WIDTH_ATTR, width);

      leaf.setAttribute(HEIGHT_ATTR, "not used");

      setBuildingHeight(leaf);
    }

    // Step 4 - for all dirs, call this method (recursive) with the parent + the self changes
    List<Integer> children = resourceTreeService.getChildrenNodeIds(sourceId);
//    for (SourceObject childrenSource : source.getChildrenNodes()) {
    for (Integer childId : children) {
      pos = innerGraphTranslation.get(childId);

      addTranslationToLayer(childId, pos, height3d + 20);

      graph.removeNode("dir_" + childId.toString());
    }

  }

  /**
   * sets actual building height (buildingHeight is given in percent)
   */
  private void setBuildingHeight(Node leaf) {
    // there is an x at the beginning of the buildingHeight percent value
    String heightString = leaf.getAttributeValue("buildingHeight").toString();

    Double height = this.rootBbMax / 2 / 100 * Double.valueOf(heightString.substring(1)) + MIN_BUILDING_HEIGHT;
    leaf.setAttribute("buildingHeight", height.toString());
  }

}