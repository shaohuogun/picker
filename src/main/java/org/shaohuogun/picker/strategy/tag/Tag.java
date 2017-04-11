package org.shaohuogun.picker.strategy.tag;

public abstract class Tag {

	public static final String URI_SEPERATOR = " > ";
	
	private Tag parent;

	public Tag(Tag parent) {
		super();
		this.parent = parent;
	}

	public Tag getParent() {
		return parent;
	}
	
	public Tag getRoot() {
		if (this.parent == null) {
			return this;
		}

		Tag root = this.parent;
		while (root.getParent() != null) {
			root = root.getParent();
		}
		
		return root;
	}
	
	public abstract String getName();
	
	protected abstract String getUri();

}
