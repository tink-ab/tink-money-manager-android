package se.tink.core.models;

import java.util.List;
import java.util.Objects;

public class Category implements Comparable<Category> {

	private String code;
	private Category parent;
	private String name;
	private int sortOrder;
	private Type type;
	private List<Category> children;
	private boolean defaultChild;

	@Override
	public int compareTo(Category o) {
		return sortOrder - o.sortOrder;
	}

	public enum Type {
		TYPE_UNKKNOWN,
		TYPE_INCOME,
		TYPE_EXPENSES,
		TYPE_TRANSFER,
	}

	public void setDefaultChild(boolean defaultChild) {
		this.defaultChild = defaultChild;
	}

	public boolean isDefaultChild() {
		return defaultChild;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Category getParent() {
		return parent;
	}

	public String getNameWithDefaultChildFormat(String defaultChildFormat) {
		if (defaultChild && parent.getChildren().size() > 1) {
			return String.format(defaultChildFormat, getName());
		} else {
			return getName();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Category category = (Category) o;
		return Objects.equals(code, category.code);
	}

	@Override
	public int hashCode() {

		return Objects.hash(code);
	}
}
