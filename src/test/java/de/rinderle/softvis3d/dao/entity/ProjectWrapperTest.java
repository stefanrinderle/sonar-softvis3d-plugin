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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by stefan on 04.07.15.
 */
public class ProjectWrapperTest {

  @Mock
  private ResourceAdapter resourceAdapter;

  @InjectMocks
  private ProjectWrapper underTest;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testInitProjectStructureNoModules() throws Exception {
    Integer id = 1;
    Resource resource = createResourceWithId(id);
    when(resourceAdapter.getResourceById(eq(id))).thenReturn(resource);
    when(resourceAdapter.getModules(eq(id))).thenReturn(Collections.<Resource>emptyList());

    underTest.initProjectStructure(id);
  }

  @Test
  public void testInitProjectStructureModules() throws Exception {
    Integer id = 1;
    Resource resource = createResourceWithId(id);
    when(resourceAdapter.getResourceById(eq(id))).thenReturn(resource);
    List<Resource> modules = new ArrayList<>();
    modules.add(createResourceWithId(2));
    modules.add(createResourceWithId(3));
    modules.add(createResourceWithId(4));
    when(resourceAdapter.getModules(eq(id))).thenReturn(modules);

    underTest.initProjectStructure(id);
  }

  private Resource createResourceWithId(Integer id) {
    Resource resource = new Resource();
    resource.setId(id);
    return resource;
  }

}
