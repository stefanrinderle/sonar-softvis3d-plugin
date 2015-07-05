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
goog.provide('ThreeViewer.FileLoaderController');

/**
 * Service which initiates the THREE.js scene and
 *  provides methods to interact with that scene
 *
 * @param {angular.$scope} $scope
 * @param {ThreeViewer.MessageBus} MessageBus
 * @param {ThreeViewer.ViewerService} ViewerService
 * @param {ThreeViewer.BackendService} BackendService
 *
 * @constructor
 * @export
 * @ngInject
 */
ThreeViewer.FileLoaderController = function ($scope, MessageBus, ViewerService, BackendService, TreeService) {
  this.scope = $scope;
  this.MessageBus = MessageBus;
  this.ViewerService = ViewerService;
  this.BackendService = BackendService;
  this.TreeService = TreeService;

  /**
   * @type {{city: boolean, dependency: boolean, custom: boolean, info: boolean}}
   */
  this.state = {
    'city': true, 'dependency': false, 'custom': false, 'info': false
  };

  this.cityInnerState = "complexity";
  this.infoInnerState = "idle";
  this.customViewType = "city";

  this.exceptionMessage;

  this.settings = {
    'metric1': null, 'metric2': null
  };

  this.availableMetrics = [];

  this.configLoaded = false;

  this.BASE_PATH = RESOURCES_BASE_PATH;

  this.init();
};

/**
 * Executes anything after construction.
 */
ThreeViewer.FileLoaderController.prototype.init = function () {
  this.listeners();

  var me = this;

  this.waitFor(500, 0, function () {
    me.BackendService.getConfig(ThreeViewer.RESOURCE_ID).then(function (response) {
      me.settings = response.data.settings;
      me.availableMetrics = response.data.metricsForSnapshot;
      me.hasDependencies = response.data.hasDependencies;
      me.configLoaded = true;
    }, function (response) {
      me.infoInnerState = "error";
      me.exceptionMessage = response.data.errors[0].msg;
      me.showTab("info");
    });
  });
};

ThreeViewer.FileLoaderController.prototype.waitFor = function(msec, count, callback) {
  var me = this;
  // Check if condition met. If not, re-check later (msec).
  if (ThreeViewer.RESOURCE_ID === undefined) {
    count++;
    setTimeout(function () {
      me.waitFor(msec, count, callback);
    }, msec);
    return;
  } else {
    // Condition finally met. callback() can be executed.
    callback();
  }
};
ThreeViewer.FileLoaderController.prototype.listeners = function () {
  this.scope.$on('appReady', function () {
    console.log("app ready");
  }.bind(this));
};

/**
 * Toggles the selected tab
 * @export
 * @param {!string} tab
 */
ThreeViewer.FileLoaderController.prototype.showTab = function (tab) {
  this.state.city = false;
  this.state.dependency = false;
  this.state.custom = false;
  this.state.info = false;
  this.state[tab] = true;
};

ThreeViewer.FileLoaderController.prototype.submitCityForm = function () {
  var cityType = "city";

  var linesKey = "lines";
  var complexityKey = "complexity";
  var issuesKey = "violations";
  var functionsKey = "functions";

  if (this.cityInnerState === "complexity") {
    this.loadVisualisation(complexityKey, linesKey, cityType);
  } else if (this.cityInnerState === "issues") {
    this.loadVisualisation(issuesKey, linesKey, cityType);
  } else if (this.cityInnerState === "functions") {
    this.loadVisualisation(functionsKey, linesKey, cityType);
  } else {
    console.log("invalid option selected.");
  }
};

/**
 * @export
 *
 */
ThreeViewer.FileLoaderController.prototype.loadDependencyView = function () {
  var linesKey = "lines";
  var complexityKey = "complexity";

  this.loadVisualisation(complexityKey, linesKey, "dependency");
};

ThreeViewer.FileLoaderController.prototype.loadCustomView = function () {
  this.loadVisualisation(this.settings.metric1, this.settings.metric2, this.customViewType);
};

ThreeViewer.FileLoaderController.prototype.loadDirectLink = function (metric1Id, metric2Id, viewType) {
  this.loadVisualisation(metric1Id, metric2Id, viewType);
};

ThreeViewer.FileLoaderController.prototype.loadVisualisation = function (metric1Key, metric2Key, viewType) {
  var me = this;

  this.infoInnerState = "loading";
  this.showTab("info");
  this.BackendService.getVisualization(ThreeViewer.RESOURCE_ID, metric1Key, metric2Key, viewType).then(function (response) {
    var treeResult = response.data.resultObject[0].treeResult;
    var visualizationResult = response.data.resultObject[1].visualizationResult;

    me.ViewerService.loadSoftVis3d(visualizationResult);
    me.TreeService.setTree(treeResult);

    var eventObject = {};
    eventObject.softVis3dId = ThreeViewer.RESOURCE_ID;
    eventObject.metric1Name = me.getNameForMetricKey(metric1Key);
    eventObject.metric2Name = me.getNameForMetricKey(metric2Key);

    me.MessageBus.trigger('visualizationReady', eventObject);

    me.infoInnerState = "idle";
    me.showTab("city");

    me.MessageBus.trigger('hideLoader');
  }, function (response) {
    me.infoInnerState = "error";
    me.exceptionMessage = response.data.errors[0].msg;
    me.showTab("info");
  });
};

ThreeViewer.FileLoaderController.prototype.getNameForMetricKey = function (metricKey) {
  for (var index = 0; index < this.availableMetrics.length; index++) {
    if (this.availableMetrics[index].key === metricKey) {
      return this.availableMetrics[index].name;
    }
  }

  return "no name found";
};