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
package de.rinderle.softvis3d.preprocessing.tree;

import de.rinderle.softvis3d.domain.sonar.SonarSnapshot;
import de.rinderle.softvis3d.domain.tree.RootTreeNode;
import de.rinderle.softvis3d.domain.tree.TreeNode;
import de.rinderle.softvis3d.domain.tree.TreeNodeType;
import de.rinderle.softvis3d.domain.tree.ValueTreeNode;

import java.util.Map;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to create a tree out of path information.
 */
public class PathWalker {

  private static final Logger LOGGER = LoggerFactory.getLogger(PathWalker.class);

  private final RootTreeNode root;
  private final Pattern pathSeparator = Pattern.compile("/");
  // TODO: different generated id sequence in DependencyExpander and
  // PathWalker.
  private int generatedIdSequence = Integer.MAX_VALUE - 100000;

  public PathWalker(final int id) {
    this.root = new RootTreeNode(id);
  }

  public RootTreeNode getTree() {
    return this.root;
  }

  public void addPath(final SonarSnapshot element) {
    LOGGER.debug("add path " + element.getPath());

    String[] names = this.pathSeparator.split(element.getPath());

    TreeNode currentNode = this.root;

    boolean isLastIndex;
    for (int i = 0; i < names.length; i = i + 1) {
      isLastIndex = (i == (names.length - 1));
      if (isLastIndex) {
        currentNode =
          this.getOrCreateChild(currentNode, element.getId(), names[i], TreeNodeType.TREE,
            element.getFootprintMetricValue(), element.getHeightMetricValue(),
            element.getAuthorCount());
      } else {
        currentNode = this.getOrCreateGeneratedChild(currentNode, names[i]);
      }
    }
  }

  private int getNextSequence() {
    this.generatedIdSequence = this.generatedIdSequence + 1;
    return this.generatedIdSequence;
  }

  private TreeNode getOrCreateChild(final TreeNode node, Integer id, final String name, final TreeNodeType type,
    final double footprintMetricValue, final double heightMetricValue, final int authorCount) {
    final Map<String, TreeNode> children = node.getChildren();
    if (children.containsKey(name)) {
      return children.get(name);
    }

    final TreeNode result =
      new ValueTreeNode(id, node, node.getDepth() + 1, type, name, footprintMetricValue, heightMetricValue,
        authorCount);

    node.addChildrenNode(name, result);

    return result;
  }

  private TreeNode getOrCreateGeneratedChild(final TreeNode node, final String name) {
    return this.getOrCreateChild(node, this.getNextSequence(), name, TreeNodeType.PATH_GENERATED, 0, 0, 0);
  }

}
