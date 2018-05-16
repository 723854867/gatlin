package org.gatlin.util.algorithm.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TreeUtil {
	
	public static final <KEY, NODE extends TreeNode<KEY>> List<NODE> children(List<NODE> nodes, NODE root) {
		LinkedList<NODE> queue = new LinkedList<NODE>();
		queue.push(root);
		List<NODE> list = new ArrayList<NODE>();
		while (!queue.isEmpty()) {
			NODE parent = queue.poll();
			_search(queue, list, parent, nodes);
		}
		return list;
	}

	public static final <KEY, NODE extends TreeNode<KEY>> Tree<KEY, NODE> build(List<NODE> nodes, NODE root) {
		LinkedList<Tree<KEY, NODE>> queue = new LinkedList<Tree<KEY, NODE>>();
		Tree<KEY, NODE> tree = new Tree<KEY, NODE>(root);
		queue.push(tree);
		while (!queue.isEmpty()) {
			Tree<KEY, NODE> parent = queue.poll();
			search(queue, parent, nodes);
		}
		return tree;
	}
	
	private static final <KEY, NODE extends TreeNode<KEY>> void search(LinkedList<Tree<KEY, NODE>> queue, Tree<KEY, NODE> parent, List<NODE> nodes) {
		Iterator<NODE> iterator = nodes.iterator();
		while (iterator.hasNext()) {
			NODE node = iterator.next();
			if (!node.getParent().equals(parent.element().key()))
				continue;
			iterator.remove();
			Tree<KEY, NODE> tree = parent.addChild(node);
			queue.push(tree);
		}
	}
	
	private static final <KEY, NODE extends TreeNode<KEY>> void _search(LinkedList<NODE> queue, List<NODE> list, NODE parent, List<NODE> nodes) {
		Iterator<NODE> iterator = nodes.iterator();
		while (iterator.hasNext()) {
			NODE node = iterator.next();
			if (!node.getParent().equals(parent.key()))
				continue;
			iterator.remove();
			queue.push(node);
			list.add(node);
		}
	}
}
