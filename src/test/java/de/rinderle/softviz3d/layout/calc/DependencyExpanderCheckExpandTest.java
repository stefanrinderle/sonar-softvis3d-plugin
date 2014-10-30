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
package de.rinderle.softviz3d.layout.calc;

import de.rinderle.softviz3d.sonar.SonarDependency;
import de.rinderle.softviz3d.tree.ResourceTreeService;
import de.rinderle.softviz3d.tree.TreeNode;
import de.rinderle.softviz3d.tree.TreeNodeType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Checks that the dependency expander creates dependency
 * nodes and solves the shortest path through the tree.
 */
public class DependencyExpanderCheckExpandTest {

  private static final Integer PROJECT_ID = 1;

  @Mock
  private ResourceTreeService treeService;

  @InjectMocks
  private DependencyExpanderImpl underTest = new DependencyExpanderImpl();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testDependenciesEmpty() {
    List<SonarDependency> dependencies = new ArrayList<SonarDependency>();

    underTest.execute(PROJECT_ID, dependencies);

    assertTrue(dependencies.isEmpty());
  }

  /**
   *      A(1)
   *     /   \
   *   B(2)-->C(3)
   *
   **/
  @Test
  public void testDependenciesFlatEdge() {
    List<SonarDependency> dependencies = new ArrayList<SonarDependency>();

    SonarDependency fromAtoB = createDependency(2, 3, DependencyType.INPUT_FLAT);
    dependencies.add(fromAtoB);

    underTest.execute(PROJECT_ID, dependencies);

    assertTrue(dependencies.contains(fromAtoB));
  }

  /**
   *      A(1)
   *     /   \
   *    B(2) D(4)
   *   /      \
   * C(3)---->E(5)
   *
   **/
  @Test
  public void testDependenciesHierarchicalEdge() {
    List<SonarDependency> dependencies = new ArrayList<SonarDependency>();

    SonarDependency fromCtoE = createDependency(3, 5, DependencyType.INPUT_TREE);
    dependencies.add(fromCtoE);

    TreeNode treeNode1 = createTreeNode(1, null, 0);
    TreeNode treeNode2 = createTreeNode(2, treeNode1, 1);
    TreeNode treeNode3 = createTreeNode(3, treeNode2, 2);
    TreeNode treeNode4 = createTreeNode(4, treeNode1, 1);
    TreeNode treeNode5 = createTreeNode(5, treeNode4, 2);

    TreeNode interfaceLeafNode2 = createInterfaceLeafNode(90, treeNode2);
    TreeNode interfaceLeafNode4 = createInterfaceLeafNode(91, treeNode4);

    underTest.execute(PROJECT_ID, dependencies);

    // dependency elevator edge
    assertTrue(treeNode3.getEdges().containsKey("depPath_3"));
    assertTrue(treeNode3.getEdges().get("depPath_3").getSourceId().equals(3));
    assertTrue(treeNode3.getEdges().get("depPath_3").getDestinationId().equals(90));
    // flat parent connecting edge
    assertTrue(treeNode2.getEdges().containsKey("depPath_2"));
    assertTrue(treeNode2.getEdges().get("depPath_2").getSourceId().equals(2));
    assertTrue(treeNode2.getEdges().get("depPath_2").getDestinationId().equals(4));
    // dependency elevator edge
    assertTrue(interfaceLeafNode4.getEdges().containsKey("depPath_5"));
    assertTrue(interfaceLeafNode4.getEdges().get("depPath_5").getSourceId().equals(91));
    assertTrue(interfaceLeafNode4.getEdges().get("depPath_5").getDestinationId().equals(5));

    // no edges at all other nodes
    assertTrue(treeNode1.getEdges().isEmpty());
    assertTrue(treeNode4.getEdges().isEmpty());
    assertTrue(treeNode5.getEdges().isEmpty());
    assertTrue(interfaceLeafNode2.getEdges().isEmpty());
  }

  /**
   *      A(1)
   *     /   \
   *    B(2) D(4)
   *   /      \
   * C(3)<----E(5)
   **/
  @Test
  public void testDependenciesHierarchicalEdgeOtherWayAround() {
    List<SonarDependency> dependencies = new ArrayList<SonarDependency>();

    SonarDependency fromEtoC = createDependency(5, 3, DependencyType.INPUT_TREE);
    dependencies.add(fromEtoC);

    TreeNode treeNode1 = createTreeNode(1, null, 0);
    TreeNode treeNode2 = createTreeNode(2, treeNode1, 1);
    TreeNode treeNode3 = createTreeNode(3, treeNode2, 2);
    TreeNode treeNode4 = createTreeNode(4, treeNode1, 1);
    TreeNode treeNode5 = createTreeNode(5, treeNode4, 2);

    TreeNode interfaceLeafNode2 = createInterfaceLeafNode(90, treeNode2);
    TreeNode interfaceLeafNode4 = createInterfaceLeafNode(91, treeNode4);

    underTest.execute(PROJECT_ID, dependencies);

    // dependency elevator edge
    assertTrue(treeNode5.getEdges().containsKey("depPath_5"));
    assertTrue(treeNode5.getEdges().get("depPath_5").getSourceId().equals(5));
    assertTrue(treeNode5.getEdges().get("depPath_5").getDestinationId().equals(91));
    // flat parent connecting edge
    assertTrue(treeNode4.getEdges().containsKey("depPath_4"));
    assertTrue(treeNode4.getEdges().get("depPath_4").getSourceId().equals(4));
    assertTrue(treeNode4.getEdges().get("depPath_4").getDestinationId().equals(2));
    // dependency elevator edge
    assertTrue(interfaceLeafNode2.getEdges().containsKey("depPath_3"));
    assertTrue(interfaceLeafNode2.getEdges().get("depPath_3").getSourceId().equals(90));
    assertTrue(interfaceLeafNode2.getEdges().get("depPath_3").getDestinationId().equals(3));

    // no edges at all other nodes
    assertTrue(treeNode1.getEdges().isEmpty());
    assertTrue(treeNode2.getEdges().isEmpty());
    assertTrue(treeNode3.getEdges().isEmpty());
    assertTrue(interfaceLeafNode4.getEdges().isEmpty());
  }

  /**
   *      A(1)
   *     /   \
   *    B(2) D(4)
   *   /      \
   * C(3)<----E(5)
   *     ---->
   **/
  @Test
  public void testDependenciesHierarchicalEdgesBoth() {
    List<SonarDependency> dependencies = new ArrayList<SonarDependency>();

    SonarDependency fromCtoE = createDependency(3, 5, DependencyType.INPUT_TREE);
    dependencies.add(fromCtoE);
    SonarDependency fromEtoC = createDependency(5, 3, DependencyType.INPUT_TREE);
    dependencies.add(fromEtoC);

    TreeNode treeNode1 = createTreeNode(1, null, 0);
    TreeNode treeNode2 = createTreeNode(2, treeNode1, 1);
    TreeNode treeNode3 = createTreeNode(3, treeNode2, 2);
    TreeNode treeNode4 = createTreeNode(4, treeNode1, 1);
    TreeNode treeNode5 = createTreeNode(5, treeNode4, 2);

    TreeNode interfaceLeafNode2 = createInterfaceLeafNode(90, treeNode2);
    TreeNode interfaceLeafNode4 = createInterfaceLeafNode(91, treeNode4);

    underTest.execute(PROJECT_ID, dependencies);

    assertTrue(dependencies.contains(fromEtoC));

    // check from c to e

    // dependency elevator edge
    assertTrue(treeNode3.getEdges().containsKey("depPath_3"));
    assertTrue(treeNode3.getEdges().get("depPath_3").getSourceId().equals(3));
    assertTrue(treeNode3.getEdges().get("depPath_3").getDestinationId().equals(90));
    // flat parent connecting edge
    assertTrue(treeNode2.getEdges().containsKey("depPath_2"));
    assertTrue(treeNode2.getEdges().get("depPath_2").getSourceId().equals(2));
    assertTrue(treeNode2.getEdges().get("depPath_2").getDestinationId().equals(4));
    // dependency elevator edge
    assertTrue(interfaceLeafNode4.getEdges().containsKey("depPath_5"));
    assertTrue(interfaceLeafNode4.getEdges().get("depPath_5").getSourceId().equals(91));
    assertTrue(interfaceLeafNode4.getEdges().get("depPath_5").getDestinationId().equals(5));

    // check from e to c

    // dependency elevator edge
    assertTrue(treeNode5.getEdges().containsKey("depPath_5"));
    assertTrue(treeNode5.getEdges().get("depPath_5").getSourceId().equals(5));
    assertTrue(treeNode5.getEdges().get("depPath_5").getDestinationId().equals(91));
    // flat parent connecting edge
    assertTrue(treeNode4.getEdges().containsKey("depPath_4"));
    assertTrue(treeNode4.getEdges().get("depPath_4").getSourceId().equals(4));
    assertTrue(treeNode4.getEdges().get("depPath_4").getDestinationId().equals(2));
    // dependency elevator edge
    assertTrue(interfaceLeafNode2.getEdges().containsKey("depPath_3"));
    assertTrue(interfaceLeafNode2.getEdges().get("depPath_3").getSourceId().equals(90));
    assertTrue(interfaceLeafNode2.getEdges().get("depPath_3").getDestinationId().equals(3));

    // no edges at all other nodes
    assertTrue(treeNode1.getEdges().isEmpty());
  }

    /**
     *      A(1)
     *     /   \
     *    B(2) D(4)
     *   /     >
     *  /     /
     * C(3)--/
     *
     **/
    @Test
    public void testUnevenDependencyEdge() {
        List<SonarDependency> dependencies = new ArrayList<SonarDependency>();

        SonarDependency fromCtoE = createDependency(3, 4, DependencyType.INPUT_TREE);
        dependencies.add(fromCtoE);

        TreeNode treeNode1 = createTreeNode(1, null, 0);
        TreeNode treeNode2 = createTreeNode(2, treeNode1, 1);
        TreeNode treeNode3 = createTreeNode(3, treeNode2, 2);
        TreeNode treeNode4 = createTreeNode(4, treeNode1, 1);

        TreeNode interfaceLeafNode2 = createInterfaceLeafNode(90, treeNode2);

        underTest.execute(PROJECT_ID, dependencies);

        // dependency elevator edge
        assertTrue(treeNode3.getEdges().containsKey("depPath_3"));
        assertTrue(treeNode3.getEdges().get("depPath_3").getSourceId().equals(3));
        assertTrue(treeNode3.getEdges().get("depPath_3").getDestinationId().equals(90));
        // flat parent connecting edge
        assertTrue(treeNode2.getEdges().containsKey("depPath_2"));
        assertTrue(treeNode2.getEdges().get("depPath_2").getSourceId().equals(2));
        assertTrue(treeNode2.getEdges().get("depPath_2").getDestinationId().equals(4));

        // no edges at all other nodes
        assertTrue(treeNode1.getEdges().isEmpty());
        assertTrue(treeNode4.getEdges().isEmpty());
        assertTrue(interfaceLeafNode2.getEdges().isEmpty());
    }

  private TreeNode createTreeNode(int id, TreeNode parent, int depth) {
    TreeNode result = new TreeNode(id, parent, depth, TreeNodeType.TREE, id + "", 0, 0);

    when(treeService.findNode(LayoutViewType.DEPENDENCY, PROJECT_ID, id)).thenReturn(result);

    return result;
  }

  // resourceTreeService.findInterfaceLeafNode(LayoutViewType.DEPENDENCY, projectId, intLeafLabel);
  private TreeNode createInterfaceLeafNode(int id, TreeNode parent) {
    TreeNode result = new TreeNode(id, parent, 0, TreeNodeType.DEPENDENCY_GENERATED, id + "", 0, 0);

    String intLeafLabel = DependencyExpanderImpl.INTERFACE_PREFIX + "_" + parent.getId();
    when(treeService.findInterfaceLeafNode(LayoutViewType.DEPENDENCY, PROJECT_ID, intLeafLabel))
      .thenReturn(result);

    return result;
  }

  private SonarDependency createDependency(int from, int to, DependencyType type) {
    SonarDependency result = new SonarDependency();

    result.setFromSnapshotId(from);
    result.setToSnapshotId(to);

    when(treeService.getDependencyType(LayoutViewType.DEPENDENCY, PROJECT_ID, from, to))
      .thenReturn(type);

    return result;
  }

}
