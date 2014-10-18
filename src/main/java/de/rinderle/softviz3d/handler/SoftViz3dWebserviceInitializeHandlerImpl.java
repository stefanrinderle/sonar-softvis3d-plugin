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
package de.rinderle.softviz3d.handler;

import com.google.inject.Inject;
import de.rinderle.softviz3d.tree.ResourceTreeService;
import de.rinderle.softviz3d.tree.TreeNode;
import org.sonar.api.server.ws.Request;
import org.sonar.api.server.ws.Response;
import org.sonar.api.utils.text.JsonWriter;

import java.util.Map;

public class SoftViz3dWebserviceInitializeHandlerImpl implements SoftViz3dWebserviceInitializeHandler {

    @Inject
    private ResourceTreeService resourceTreeService;

    @Override
    public void handle(Request request, Response response) {
        Integer id = Integer.valueOf(request.param("snapshotId"));
        Integer footprintMetricId = Integer.valueOf(request.param("footprintMetricId"));
        Integer heightMetricId = Integer.valueOf(request.param("heightMetricId"));

        TreeNode tree = resourceTreeService.createTreeStructrue(id, footprintMetricId, heightMetricId);

        JsonWriter jsonWriter = response.newJsonWriter();

        transformTreeToJson(jsonWriter, tree);

        jsonWriter.close();
    }

    private void transformTreeToJson(JsonWriter jsonWriter, TreeNode tree) {
        jsonWriter.beginObject();

        jsonWriter.prop("id", tree.getId());
        jsonWriter.prop("name", tree.getName());
        jsonWriter.prop("heightMetricValue", tree.getHeightMetricValue());
        jsonWriter.prop("footprintMetricValue", tree.getFootprintMetricValue());
        jsonWriter.prop("isNode", tree.isNode());

        transformChildren(jsonWriter, tree.getChildren());

        TreeNode parent = tree.getParent();
        if (parent != null) {
            jsonWriter.name("parentInfo");
            jsonWriter.beginObject();
            jsonWriter.prop("id", parent.getId());
            jsonWriter.prop("name", parent.getName());
            jsonWriter.prop("heightMetricValue", parent.getHeightMetricValue());
            jsonWriter.prop("footprintMetricValue", parent.getFootprintMetricValue());
            jsonWriter.prop("isNode", parent.isNode());
            jsonWriter.endObject();
        }

        jsonWriter.endObject();
    }

    private void transformChildren(JsonWriter jsonWriter, Map<String, TreeNode> children) {
        jsonWriter.name("children");
        jsonWriter.beginArray();

        for (TreeNode child : children.values()) {
            transformTreeToJson(jsonWriter, child);
        }

        jsonWriter.endArray();
    }
}