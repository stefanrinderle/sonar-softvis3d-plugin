/*
 * SoftVis3D Sonar plugin
 * Copyright (C) 2014 Stefan Rinderle
 * stefan@rinderle.info
 *
 * SoftVis3D Sonar plugin can not be copied and/or distributed without the express
 * permission of Stefan Rinderle.
 */
package de.rinderle.softvis3d.layout.bottomUp;

import att.grappa.Graph;
import att.grappa.GrappaBox;
import att.grappa.Node;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.rinderle.softvis3d.domain.LayoutViewType;
import de.rinderle.softvis3d.domain.MinMaxValue;
import de.rinderle.softvis3d.domain.SoftVis3DConstants;
import de.rinderle.softvis3d.domain.graph.ResultPlatform;
import de.rinderle.softvis3d.domain.layout.GrappaTransformer;
import de.rinderle.softvis3d.domain.layout.LayeredLayoutElement;
import de.rinderle.softvis3d.domain.tree.Edge;
import de.rinderle.softvis3d.domain.tree.TreeNode;
import de.rinderle.softvis3d.domain.tree.TreeNodeType;
import de.rinderle.softvis3d.layout.dot.DotExecutor;
import de.rinderle.softvis3d.layout.dot.DotExecutorException;
import de.rinderle.softvis3d.layout.format.LayerFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.config.Settings;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SnapshotVisitorBean implements SnapshotVisitor {

  private static final Logger LOGGER = LoggerFactory.getLogger(SnapshotVisitorBean.class);

  // getting injected - see constructor
  private final DotExecutor dotExecutor;
  private final LayerFormatter formatter;
  private final GrappaTransformer transformer;

  private final Settings settings;

  private final MinMaxValue minMaxMetricFootprint;
  private final MinMaxValue minMaxMetricHeight;
  private final MinMaxValue minMaxEdgeCounter;

  private final Map<Integer, ResultPlatform> resultingGraphList = new ConcurrentHashMap<Integer, ResultPlatform>();

  private final LayoutViewType viewType;

  @Inject
  public SnapshotVisitorBean(final LayerFormatter formatter,
    final DotExecutor dotExecutor, final GrappaTransformer transformer,
    @Assisted final Settings settings,
    @Assisted final LayoutViewType viewType,
    @Assisted(value = "minMaxFootprintMetricValues") final MinMaxValue minMaxFootprintMetricValues,
    @Assisted(value = "minMaxHeightMetricValues") final MinMaxValue minMaxHeightMetricValues,
    @Assisted(value = "minMaxEdgeCounter") final MinMaxValue minMaxEdgeCounter) {
    this.settings = settings;

    this.minMaxMetricFootprint = minMaxFootprintMetricValues;
    this.minMaxMetricHeight = minMaxHeightMetricValues;
    this.minMaxEdgeCounter = minMaxEdgeCounter;

    this.dotExecutor = dotExecutor;
    this.formatter = formatter;
    this.transformer = transformer;

    this.viewType = viewType;
  }

  @Override
  public Map<Integer, ResultPlatform> getResultingGraphList() {
    return this.resultingGraphList;
  }

  @Override
  public LayeredLayoutElement visitNode(final TreeNode node,
    final List<LayeredLayoutElement> elements) throws DotExecutorException {

    LOGGER.debug("LayoutVisitor.visitNode " + node.getId() + " " + node.getName());

    // create layout graph
    final Graph inputGraph = createGrappaInputGraph(node, elements);

    // run dot layout for this layer
    final Graph outputGraph = this.dotExecutor.run(inputGraph, this.settings, this.viewType);

    final ResultPlatform resultPlatform = new ResultPlatform(outputGraph);

    // adjust graph
    this.formatter.format(resultPlatform, node.getDepth(), this.viewType);

    this.resultingGraphList.put(node.getId(), resultPlatform);
    // this.resultLayers.put(node.getId(), )

    // adjusted graph has a bounding box !
    final GrappaBox bb = resultPlatform.getBoundingBox();

    /**
     * The dot output of the bb is given in DPI. The actual width
     * and height of the representing element has to be scaled
     * back to normal
     */
    final Double width = bb.getWidth() / SoftVis3DConstants.DPI_DOT_SCALE;
    final Double height = bb.getHeight() / SoftVis3DConstants.DPI_DOT_SCALE;

    final double platformHeight = 5;

    return LayeredLayoutElement.createLayeredLayoutElement(node, width, height, platformHeight);
  }

  private Graph createGrappaInputGraph(TreeNode node, List<LayeredLayoutElement> elements) {
    final Graph inputGraph = new Graph(node.getId().toString());

    for (final LayeredLayoutElement element : elements) {
      final Node elementNode = this.transformer.transformToGrappaNode(inputGraph, element);
      inputGraph.addNode(elementNode);
    }

    for (final LayeredLayoutElement element : elements) {
      for (final Edge edge : element.getEdges().values()) {
        inputGraph.addEdge(this.transformer.transformToGrappaEdge(inputGraph, edge, this.minMaxEdgeCounter));
      }
    }
    return inputGraph;
  }

  @Override
  public LayeredLayoutElement visitFile(final TreeNode leaf) {
    LOGGER.debug("Leaf : " + leaf.getId() + " " + leaf.getName());
    double sideLength = this.formatter.calcSideLength(leaf.getFootprintMetricValue(), this.minMaxMetricFootprint);
    sideLength = sideLength / SoftVis3DConstants.DPI_DOT_SCALE;

    double buildingHeight = this.formatter.calcBuildingHeight(leaf.getHeightMetricValue(), this.minMaxMetricHeight);
    buildingHeight = buildingHeight / SoftVis3DConstants.DPI_DOT_SCALE;

    buildingHeight = buildingHeight * 100;

    if (TreeNodeType.DEPENDENCY_GENERATED.equals(leaf.getType())
      && LayoutViewType.DEPENDENCY.equals(this.viewType)) {
      buildingHeight = 200;
    }

    return LayeredLayoutElement.createLayeredLayoutElement(leaf, sideLength, sideLength, buildingHeight);
  }

}