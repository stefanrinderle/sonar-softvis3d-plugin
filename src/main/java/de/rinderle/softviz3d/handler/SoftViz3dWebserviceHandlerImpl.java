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

public class SoftViz3dWebserviceHandlerImpl implements SoftViz3dWebserviceHandler {

    @Inject
    private ResourceTreeService resourceTreeService;

    @Override
    public void handle(Request request, Response response) {
        Integer id = Integer.valueOf(request.param("snapshotId"));

        TreeNode node = resourceTreeService.findNode(Integer.valueOf(id));

        JsonWriter json = response.newJsonWriter();
        json.beginObject();
        json.prop("id", node.getId() );
        json.prop("name", node.getName());
        json.prop("depth", node.getDepth());
        json.prop("footprintMetricValue", node.getFootprintMetricValue());
        json.prop("heightMetricValue", node.getHeightMetricValue());
        json.endObject().close();

    }

}