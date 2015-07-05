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
package de.rinderle.softvis3d.dao;

import com.google.inject.Inject;
import de.rinderle.softvis3d.dao.dto.MetricResultDTO;
import de.rinderle.softvis3d.domain.MinMaxValue;
import de.rinderle.softvis3d.domain.sonar.ModuleInfo;
import de.rinderle.softvis3d.domain.sonar.SonarDependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DaoService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DaoService.class);

  private static final String SCM_AUTHOR_NAME = "authors_by_line";
  // private static final String SCM_DATE_NAME = "last_commit_datetimes_by_line";

  @Inject
  private SonarDao sonarDao;
  @Inject
  private DependencyDao dependencyDao;
  @Inject
  private ScmCalculationService scmCalculationService;

  public MinMaxValue getMinMaxMetricValuesByRootResourceId(int rootResourceId, String metricKey) {
    LOGGER.debug("getMinMaxMetricValuesByRootResourceId " + rootResourceId);
    return this.sonarDao.getMinMaxMetricValuesByRootSnapshotId(rootResourceId, metricKey);
  }

  public boolean hasDependencies(Integer resourceId) {
    LOGGER.debug("hasDependencies" + resourceId);

    final List<SonarDependency> result = getDependencies(resourceId);

    return !result.isEmpty();
  }

  public List<ModuleInfo> getDirectModuleChildrenIds(Integer resourceId) {
    return this.sonarDao.getDirectModuleChildrenIds(resourceId);
  }

  public int getMaxScmInfo(int rootResourceId) {
    int maxAuthorsCount = 0;

    final List<MetricResultDTO<String>> scmCommitter = getScmAuthors(rootResourceId);
    for (MetricResultDTO<String> aScmCommitter : scmCommitter) {
      final int differentAuthors = scmCalculationService.getDifferentAuthors(aScmCommitter.getValue(), "");

      if (differentAuthors > maxAuthorsCount) {
        maxAuthorsCount = differentAuthors;
      }
    }

    LOGGER.info("max authors count " + maxAuthorsCount);

    return maxAuthorsCount;
  }

  public List<SonarDependency> getDependencies(Integer resourceId) {
    LOGGER.debug("getDependencies " + resourceId);

    List<ModuleInfo> modules = getDirectModuleChildrenIds(resourceId);

    List<SonarDependency> result = new ArrayList<SonarDependency>();
    if (modules == null || modules.isEmpty()) {
      result = this.dependencyDao.getDependencies(resourceId);
    } else {
      for (ModuleInfo module : modules) {
        result.addAll(this.dependencyDao.getDependencies(module.getId()));
      }
    }

    return result;
  }

  private List<MetricResultDTO<String>> getScmAuthors(int rootResourceId) {
    final Integer authorMetricId = this.sonarDao.getMetricIdByKey(SCM_AUTHOR_NAME);

    return this.sonarDao.getMetricTextForAllProjectElementsWithMetric(rootResourceId, authorMetricId);
  }

}
