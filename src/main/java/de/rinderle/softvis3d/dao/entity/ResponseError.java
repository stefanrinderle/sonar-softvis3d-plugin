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

/**
 * Representation of an error response of the sonar api.
 *
 * {"err_code":404,"err_msg":"Resource [18725] not found"}
 *
 */
public class ResponseError {

  public String err_code;
  public String err_msg;

  public String getErr_code() {
    return err_code;
  }

  public String getErr_msg() {
    return err_msg;
  }

  public void setErr_code(String err_code) {
    this.err_code = err_code;
  }

  public void setErr_msg(String err_msg) {
    this.err_msg = err_msg;
  }
}
