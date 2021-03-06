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
package de.rinderle.softvis3d.cache;

import com.google.inject.Singleton;
import de.rinderle.softvis3d.domain.SnapshotStorageKey;
import de.rinderle.softvis3d.domain.graph.ResultPlatform;

import java.util.Map;

@Singleton
public class LayoutCacheService {

  private final Cache<SnapshotStorageKey, Map<Integer, ResultPlatform>> storage;

  public LayoutCacheService() {
    storage = new Cache<>();
  }

  public void printCacheContents() {
    storage.logKeys();
  }

  public boolean containsKey(final SnapshotStorageKey key) {
    return storage.containsKey(key);
  }

  public Map<Integer, ResultPlatform> getLayoutResult(final SnapshotStorageKey key) {
    return storage.get(key);
  }

  public void save(final SnapshotStorageKey key, final Map<Integer, ResultPlatform> value) {
    storage.put(key, value);
  }

}
