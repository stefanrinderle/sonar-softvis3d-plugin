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
package de.rinderle.softvis3d.domain;

import de.rinderle.softvis3d.dao.scm.ScmAuthorCountCalculationService;
import de.rinderle.softvis3d.dao.scm.ScmCalculationService;
import de.rinderle.softvis3d.dao.scm.ScmCommitCountCalculationService;

/**
 * Specifies, which type of SCM info should be used for the color of the buildings.
 */
public enum ScmInfoType {

    /**
     * ! enum names used in view !
     */
    NONE("None", null),
    AUTHOR_COUNT("Author count", new ScmAuthorCountCalculationService()),
    COMMIT_COUNT("Commit count", new ScmCommitCountCalculationService());

    private final ScmCalculationService scmCalculationService;
    private final String description;

    ScmInfoType(String description, ScmCalculationService scmCalculationService) {
        this.description = description;
        this.scmCalculationService = scmCalculationService;
    }

    public String getDescription() {
        return description;
    }

    public ScmCalculationService getScmCalculationService() {
        return this.scmCalculationService;
    }
}
