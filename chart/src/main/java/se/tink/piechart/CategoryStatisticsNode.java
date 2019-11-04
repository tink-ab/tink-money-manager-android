package se.tink.piechart;


import java.util.List;

public class CategoryStatisticsNode extends CategoryStatistics {

	private List<CategoryStatisticsNode> childNodes;

	public List<CategoryStatisticsNode> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<CategoryStatisticsNode> childNodes) {
		this.childNodes = childNodes;
	}

}
