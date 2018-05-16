package org.gatlin.util.algorithm.tree;

import org.gatlin.util.bean.Identifiable;

public interface TreeNode<KEY> extends Identifiable<KEY> {

	KEY getParent();
}
