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

import com.google.inject.Inject;
import de.rinderle.softvis3d.dao.webservice.SonarAccess;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by stefan on 04.07.15.
 */
public class ResourceAdapterTest {

  @InjectMocks
  private ResourceAdapter underTest;

  @Mock
  private SonarAccess sonarAccess;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetResourceById() throws Exception {
    final String testResultString = "[{\"id\":13630,\"key\":\"org.codehaus.sonar-plugins:sonar-softVis3D-plugin\",\"name\":\"SoftVis3D Sonar plugin\",\"scope\":\"PRJ\",\"qualifier\":\"TRK\",\"date\":\"2015-06-30T23:20:18+0200\",\"creationDate\":\"2015-05-11T18:32:38+0200\",\"lname\":\"SoftVis3D Sonar plugin\",\"version\":\"0.3.1-SNAPSHOT\",\"description\":\"Creates a 3d view of the project structure\"}]";
    when(sonarAccess.getUrlAsResultString(anyString())).thenReturn(testResultString);

    Resource result = underTest.getResourceById(13630);

    assertEquals(13630, result.getId());
  }

  @Test
  public void testGetModulesEmpty() throws Exception {
    final String testResultString = "[]";
    when(sonarAccess.getUrlAsResultString(anyString())).thenReturn(testResultString);

    List<Resource> result = underTest.getModules(13630);

    assertEquals(0, result.size());
  }

  @Test
  public void testGetModulesFilled() throws Exception {
    final String testResultString = "[{\"id\":2882,\"key\":\"de.rinderle:multitest:softvisSonarPlugin1\",\"name\":\"softvisSonarPlugin1\",\"scope\":\"PRJ\",\"qualifier\":\"BRC\",\"date\":\"2015-02-11T23:24:30+0100\",\"creationDate\":\"2015-02-11T23:24:30+0100\",\"lname\":\"softvisSonarPlugin1\",\"version\":\"0.0.1\",\"branch\":\"softvisSonarPlugin1\",\"description\":\"\"},{\"id\":2883,\"key\":\"de.rinderle:multitest:softvisSonarPlugin2\",\"name\":\"softvisSonarPlugin2\",\"scope\":\"PRJ\",\"qualifier\":\"BRC\",\"date\":\"2015-02-11T23:24:30+0100\",\"creationDate\":\"2015-02-11T23:24:30+0100\",\"lname\":\"softvisSonarPlugin2\",\"version\":\"0.0.1\",\"branch\":\"softvisSonarPlugin2\",\"description\":\"\"},{\"id\":2884,\"key\":\"de.rinderle:multitest:softvisSonarPlugin3\",\"name\":\"softvisSonarPlugin3\",\"scope\":\"PRJ\",\"qualifier\":\"BRC\",\"date\":\"2015-02-11T23:24:30+0100\",\"creationDate\":\"2015-02-11T23:24:30+0100\",\"lname\":\"softvisSonarPlugin3\",\"version\":\"0.0.1\",\"branch\":\"softvisSonarPlugin3\",\"description\":\"\"}]";
    when(sonarAccess.getUrlAsResultString(anyString())).thenReturn(testResultString);

    List<Resource> result = underTest.getModules(13630);

    assertEquals(3, result.size());
  }

}