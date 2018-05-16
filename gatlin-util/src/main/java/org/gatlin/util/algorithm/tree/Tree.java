package org.gatlin.util.algorithm.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tree<KEY, E extends TreeNode<KEY>> implements Serializable {

	private static final long serialVersionUID = -3537533014231788734L;

	private E element;
	private Tree<KEY, E> parent;
	private List<Tree<KEY, E>> children;
	
	public Tree(E element) {
		this.element = element;
	}
	
	public E element() {
		return element;
	}
	
	public Tree<KEY, E> parent() {
		return parent;
	}
	
	public List<Tree<KEY, E>> children() {
		return children;
	}
	
	public Tree<KEY, E> addChild(E element) {
		if (null == children)
			this.children = new ArrayList<Tree<KEY, E>>();
		Tree<KEY, E> child = new Tree<KEY, E>(element);
		child.parent = this;
		this.children.add(child);
		return child;
	}
}
