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
package de.rinderle.softviz3d.layout.calc.topdown;

import att.grappa.Graph;
import att.grappa.GrappaBox;
import att.grappa.Node;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.rinderle.softviz3d.layout.calc.LayeredLayoutElement;
import de.rinderle.softviz3d.layout.calc.LayeredLayoutElement.Type;
import de.rinderle.softviz3d.layout.calc.bottomup.ViewLayerFormatter;
import de.rinderle.softviz3d.layout.dot.DotExcecutorException;
import de.rinderle.softviz3d.layout.dot.DotExecutor;
import de.rinderle.softviz3d.layout.interfaces.SoftViz3dConstants;
import de.rinderle.softviz3d.sonar.SonarMetric;
import de.rinderle.softviz3d.sonar.SonarSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.config.Settings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static att.grappa.GrappaConstants.*;

public class LayoutVisitorImpl implements LayoutVisitor {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(LayoutVisitorImpl.class);

    private Settings settings;

    private SonarMetric metricFootprint;
    private SonarMetric metricHeight;

    private ViewLayerFormatter formatter = new ViewLayerFormatter();

    private Map<Integer, Graph> resultingGraphList = new HashMap<Integer, Graph>();

    private DotExecutor dotExecutor;

    @Inject
    public LayoutVisitorImpl(DotExecutor dotExecutor, @Assisted Settings settings,
            @Assisted List<Double> minMaxValues) {
        this.settings = settings;

        this.metricFootprint = new SonarMetric(
                minMaxValues.get(0), minMaxValues.get(1));

        this.metricHeight = new SonarMetric(minMaxValues.get(2),
                minMaxValues.get(3));

        this.dotExecutor = dotExecutor;
    }

    @Override
    public Map<Integer, Graph> getResultingGraphList() {
        return this.resultingGraphList;
    }

    @Override
    public LayeredLayoutElement visitNode(SonarSnapshot snapshot,
            List<LayeredLayoutElement> elements) throws DotExcecutorException {

        LOGGER.debug("LayoutVisitor.visitNode " + snapshot.getId() + " " + snapshot.getName());

        // create layout graph
        Graph inputGraph = new Graph(snapshot.getId().toString());

        for (LayeredLayoutElement element : elements) {
            Node elementNode = new Node(inputGraph, element.getName());
            elementNode.setAttribute("id", element.getId().toString());
            elementNode.setAttribute("type", element.getElementType().name());
            elementNode.setAttribute(WIDTH_ATTR, element.getWidth());
            elementNode.setAttribute(HEIGHT_ATTR, element.getHeight());

            // keep the size of the node only dependend on the width and height
            // attribute
            // and not from the node name
            elementNode.setAttribute(LABEL_ATTR, ".");

            elementNode.setAttribute(SHAPE_ATTR, "box");

            elementNode.setAttribute("buildingHeight", element
                    .getBuildingHeight().toString());

            elementNode.setAttribute("displayName", element.getDisplayName());

            inputGraph.addNode(elementNode);
        }

        // run dot layout for this layer
        Graph outputGraph = dotExecutor.run(inputGraph, settings);

        // adjust graph
        Graph adjustedGraph = formatter.format(outputGraph, snapshot.getDepth());
        resultingGraphList.put(snapshot.getId(), adjustedGraph);

        // adjusted graph has a bounding box !
        GrappaBox bb = (GrappaBox) adjustedGraph.getAttributeValue("bb");

        // The dot output of the bb is given in DPI. The actual width
        // and height of the representing element has to be scaled
        // back to normal
        Double width = bb.getWidth() / SoftViz3dConstants.DPI_DOT_SCALE;
        Double height = bb.getHeight() / SoftViz3dConstants.DPI_DOT_SCALE;

        double buildingHeight = 2;

        return new LayeredLayoutElement(LayeredLayoutElement.Type.NODE,
                snapshot.getId(), "dir_" + snapshot.getId(), width, height,
                buildingHeight, snapshot.getName());
    }

    @Override
    public LayeredLayoutElement visitFile(SonarSnapshot snapshot) {
        LOGGER.debug("LayoutVisitor.visitNode " + snapshot.getId() + " " + snapshot.getName());

        double sideLength = calcSideLength(snapshot.getFootprintMetricValue());

        double buildingHeight = calcBuildingHeight(snapshot
                .getHeightMetricValue());

        return new LayeredLayoutElement(Type.LEAF, snapshot.getId(), "file_"
                + snapshot.getId().toString(), sideLength, sideLength,
                buildingHeight, snapshot.getName());
    }

    /**
     * Building height is calculated in percent.
     * 
     * The actual building size is dependent on the size of the biggest layer.
     * This value is not available at this point of the calculation.
     * 
     * @param value
     *            Metric value for the building size
     * @return percent 0-100%
     */
    private double calcBuildingHeight(Double value) {
        double buildingHeight = 0.0;

        if (value != null) {
            // TODO start with 0 percent also in case of starting higher
            Double maxValue = metricHeight.getMaxValue();

            Double valuePercent = 0.0;
            if (maxValue > 0 && value > 0) {
                valuePercent = SoftViz3dConstants.PERCENT_DIVISOR / maxValue
                        * value;
            }

            buildingHeight = valuePercent;
        }

        return buildingHeight;
    }

    private double calcSideLength(Double value) {
        double sideLength = SoftViz3dConstants.MIN_SIDE_LENGTH;

        if (value != null) {
            // TODO start with 0 percent also in case of starting higher
            // Double minValue = metricFootprint.getMinValue();
            Double maxValue = metricFootprint.getMaxValue();

            // create a linear distribution
            Double onePercent = (SoftViz3dConstants.MAX_SIDE_LENGTH - SoftViz3dConstants.MIN_SIDE_LENGTH)
                    / SoftViz3dConstants.PERCENT_DIVISOR;
            Double valuePercent = 0.0;
            if (maxValue > 0 && value > 0) {
                valuePercent = SoftViz3dConstants.PERCENT_DIVISOR / maxValue
                        * value;
            }

            sideLength = SoftViz3dConstants.MIN_SIDE_LENGTH + valuePercent
                    * onePercent;
        }

        return sideLength;
    }

}