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
package de.rinderle.softviz3d.sonar;

import java.math.BigInteger;
import java.text.AttributedCharacterIterator;

import de.rinderle.softviz3d.layout.calc.DependencyType;

/**
 * Created by srinderle on 26.09.14.
 */
public class SonarDependency {

    private BigInteger id;
    private Integer fromSnapshotId;
    private Integer fromResouorceId;
    private Integer toSnapshotId;
    private Integer toResourceId;
    private String usage;
    private Integer weight;
    private String fromScope;
    private String toScope;

    public SonarDependency() {
    }

    public void setId(final BigInteger id) {
        this.id = id;
    }

    public void setFromSnapshotId(final Integer fromSnapshotId) {
        this.fromSnapshotId = fromSnapshotId;
    }

    public void setFromResouorceId(final Integer fromResouorceId) {
        this.fromResouorceId = fromResouorceId;
    }

    public void setToSnapshotId(final Integer toSnapshotId) {
        this.toSnapshotId = toSnapshotId;
    }

    public void setToResourceId(final Integer toResourceId) {
        this.toResourceId = toResourceId;
    }

    public void setUsage(final String usage) {
        this.usage = usage;
    }

    public void setWeight(final Integer weight) {
        this.weight = weight;
    }

    public void setFromScope(final String fromScope) {
        this.fromScope = fromScope;
    }

    public void setToScope(final String toScope) {
        this.toScope = toScope;
    }

    public BigInteger getId() {
        return id;
    }

    public Integer getFromSnapshotId() {
        return fromSnapshotId;
    }

    public Integer getFromResouorceId() {
        return fromResouorceId;
    }

    public Integer getToSnapshotId() {
        return toSnapshotId;
    }

    public Integer getToResourceId() {
        return toResourceId;
    }

    public String getUsage() {
        return usage;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getFromScope() {
        return fromScope;
    }

    public String getToScope() {
        return toScope;
    }

    @Override
    public String toString() {
        return "SonarDependency{" +
            "id=" + id +
            ", fromSnapshotId=" + fromSnapshotId +
            ", fromResouorceId=" + fromResouorceId +
            ", toSnapshotId=" + toSnapshotId +
            ", toResourceId=" + toResourceId +
            ", usage='" + usage + '\'' +
            ", weight=" + weight +
            ", fromScope='" + fromScope + '\'' +
            ", toScope='" + toScope + '\'' +
            '}';
    }

    public DependencyType getType() {
        // TODO: calculate which dependency type it is.
        return DependencyType.INPUT_FLAT;
    }
}
