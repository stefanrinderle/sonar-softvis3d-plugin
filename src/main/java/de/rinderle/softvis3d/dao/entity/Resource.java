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
package de.rinderle.softvis3d.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author ashraf_sarhan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Resource {

  private long id;
  private String key;
  private String name;

  private String scope;
  private String qualifier;
//  private String date;
  private String creationDate;

  private String lname;
  private String description;

  private String version;

  public long getId() {
    return id;
  }

  public String getKey() {
    return key;
  }

  public String getName() {
    return name;
  }

  public String getVersion() {
    return version;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public void setQualifier(String qualifier) {
    this.qualifier = qualifier;
  }

//  public void setDate(String date) {
//    this.date = date;
//  }

  public void setCreationDate(String creationDate) {
    this.creationDate = creationDate;
  }

  public void setLname(String lname) {
    this.lname = lname;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getScope() {
    return scope;
  }

  public String getQualifier() {
    return qualifier;
  }

//  public String getDate() {
//    return date;
//  }

  public String getCreationDate() {
    return creationDate;
  }

  public String getLname() {
    return lname;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return "Resource{" +
            "version='" + version + '\'' +
            ", name='" + name + '\'' +
            ", key='" + key + '\'' +
            ", id=" + id +
            '}';
  }
}