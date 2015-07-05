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
goog.provide('ThreeViewer.BackendService');

ThreeViewer.BackendService = function ($http) {
  this.http = $http;
};

ThreeViewer.BackendService.prototype.getVisualization = function (resourceId, footprintMetricKey, heightMetricKey, viewType) {
  return this.http.get("../../api/softVis3D/getVisualization"
      + "?resourceId=" + resourceId
      + "&footprintMetricKey=" + footprintMetricKey
      + "&heightMetricKey=" + heightMetricKey
      + "&viewType=" + viewType);
};

ThreeViewer.BackendService.prototype.getConfig = function (resourceId) {
  return this.http.get("../../api/softVis3D/getConfig?resourceId=" + resourceId);
};