package se.tink.repository.cache.helpers;

import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import se.tink.converter.ModelConverter;
import se.tink.core.models.statistic.Statistic;
import se.tink.core.models.statistic.Statistic.Type;
import se.tink.core.models.statistic.StatisticTree;
import se.tink.repository.cache.models.StatisticEntity;

public class StatisticsTreeConverter {

	private static final String balancesByAccountGroupTypeShort = "#1";
	private static final String expensesByCategoryCodeShort = "#2";
	private static final String incomeByCategoryCodeShort = "#3";
	private static final String balancesByAccountIdShort = "#4";
	private static final String leftToSpendShort = "#5";
	private static final String leftToSpendAverageShort = "#6";

	public static StatisticsTreeBuilder builder(List<StatisticEntity> entities,
		ModelConverter modelConverter) {
		return new StatisticsTreeBuilder(entities, modelConverter);
	}

	public static StatisticsTreeBuilder builder(List<StatisticEntity> entities,
		ModelConverter modelConverter, List<Statistic.Type> types) {
		return new StatisticsTreeBuilder(entities, modelConverter, types);
	}

	public static StatisticTreeFlattener flattener(ModelConverter modelConverter) {
		return new StatisticTreeFlattener(modelConverter);
	}

	public static class StatisticTreeFlattener {

		private ModelConverter modelConverter;

		private StatisticTreeFlattener(ModelConverter modelConverter) {
			this.modelConverter = modelConverter;
		}

		public void flattenTree(StatisticTree tree, List<StatisticEntity> statisticEntities) {
			flattenTopLevelMap(tree.getBalancesByAccountGroupType(),
				balancesByAccountGroupTypeShort, statisticEntities);
			flattenTopLevelMap(tree.getExpensesByCategoryCode(), expensesByCategoryCodeShort,
				statisticEntities);
			flattenTopLevelMap(tree.getIncomeByCategoryCode(), incomeByCategoryCodeShort,
				statisticEntities);
			flattenTopLevelMap(tree.getBalancesByAccountId(), balancesByAccountIdShort,
				statisticEntities);
			flattenTopLevelMap(tree.getLeftToSpend(), leftToSpendShort, statisticEntities);
			flattenTopLevelMap(tree.getLeftToSpendAverage(), leftToSpendAverageShort,
				statisticEntities);
		}

		private void flattenTopLevelMap(Map<String, Statistic> statisticMap, String parentId,
			List<StatisticEntity> statisticInsertionList) {
			if (statisticMap != null) {
				for (String key : statisticMap.keySet()) {
					flattenChildrenRecursive(statisticMap.get(key), key, parentId,
						statisticInsertionList);
				}
			}
		}

		private void flattenChildrenRecursive(Statistic statistic, String identifier,
			String parentDatabaseId, List<StatisticEntity> insertionList) {
			StatisticEntity statisticEntity = modelConverter.map(statistic, StatisticEntity.class);

			String databaseId = parentDatabaseId + identifier;

			statisticEntity.setMapIdentifier(identifier);
			statisticEntity.setParentDatabaseId(parentDatabaseId);
			statisticEntity.setDatabaseId(databaseId);

			Map<String, Statistic> children = statistic.getChildren();

			insertionList.add(statisticEntity);

			if (children != null) {
				for (String key : children.keySet()) {
					flattenChildrenRecursive(children.get(key), key, databaseId, insertionList);
				}
			}
		}
	}

	public static class StatisticsTreeBuilder {

		private final ModelConverter modelConverter;
		private final List<StatisticEntity> entities;
		private final List<Type> types;
		private final List<String> typesAsStrings;

		private Map<String, Map<String, Statistic>> statisticsByParent = new HashMap<>();


		public StatisticsTreeBuilder(List<StatisticEntity> entities, ModelConverter modelConverter,
			List<Type> types) {
			this.types = types;
			this.entities = entities;
			this.modelConverter = modelConverter;
			typesAsStrings = getTypesAsStrings(types);
		}

		private StatisticsTreeBuilder(List<StatisticEntity> entities,
			ModelConverter modelConverter) {
			this.entities = entities;
			this.modelConverter = modelConverter;
			this.types = getAllTypes();
			typesAsStrings = getTypesAsStrings(types);
		}


		private List<String> getTypesAsStrings(List<Type> types) {
			List<String> returnList = Lists.newArrayList();
			if (types.contains(Type.TYPE_LEFT_TO_SPEND)) {
				returnList.add(leftToSpendShort);
			}
			if (types.contains(Type.TYPE_LEFT_TO_SPEND_AVERAGE)) {
				returnList.add(leftToSpendAverageShort);
			}
			if (types.contains(Type.TYPE_BALANCES_BY_ACCOUNT)) {
				returnList.add(balancesByAccountIdShort);
			}
			if (types.contains(Type.TYPE_BALANCES_BY_ACCOUNT_TYPE_GROUP)) {
				returnList.add(balancesByAccountGroupTypeShort);
			}
			if (types.contains(Type.TYPE_BY_CATEGORY)) {
				returnList.add(expensesByCategoryCodeShort);
				returnList.add(incomeByCategoryCodeShort);
			}
			return returnList;
		}

		public StatisticTree build() {

			Statistic[] allStatistics = new Statistic[entities.size()];

			//Create statistic model
			for (int i = 0; i < entities.size(); i++) {
				StatisticEntity entity = entities.get(i);
				if (typesAsStrings.contains(entity.getDatabaseId().substring(0, 2))) {
					allStatistics[i] = modelConverter.map(entities.get(i), Statistic.class);
				}
			}

			for (int i = 0; i < allStatistics.length; i++) {
				Statistic s = allStatistics[i];
				StatisticEntity e = entities.get(i);

				Map<String, Statistic> entry = statisticsByParent.get(e.getParentDatabaseId());
				if (entry == null) {
					entry = new HashMap<String, Statistic>();
					statisticsByParent.put(e.getParentDatabaseId(), entry);
				}
				entry.put(e.getMapIdentifier(), s);
			}

			StatisticTree destination = new StatisticTree();

			if (typesAsStrings.contains(balancesByAccountGroupTypeShort)) {
				destination.setBalancesByAccountGroupType(
					convertChildrenOf(balancesByAccountGroupTypeShort));
			}
			if (typesAsStrings.contains(expensesByCategoryCodeShort)) {
				destination
					.setExpensesByCategoryCode(convertChildrenOf(expensesByCategoryCodeShort));
			}
			if (typesAsStrings.contains(incomeByCategoryCodeShort)) {
				destination.setIncomeByCategoryCode(convertChildrenOf(incomeByCategoryCodeShort));
			}
			if (typesAsStrings.contains(leftToSpendShort)) {
				destination.setLeftToSpend(convertChildrenOf(leftToSpendShort));
			}
			if (typesAsStrings.contains(leftToSpendAverageShort)) {
				destination
					.setLeftToSpendAverage(convertChildrenOf(leftToSpendAverageShort));
			}
			if (typesAsStrings.contains(balancesByAccountIdShort)) {
				destination.setBalancesByAccountId(
					convertChildrenOf(balancesByAccountIdShort));
			}

			return destination;
		}

		private Map<String, Statistic> convertChildrenOf(String id) {
			Map<String, Statistic> statisticEntities = statisticsByParent.get(id);

			if (statisticEntities == null) {
				return new HashMap<>();
			}

			Map<String, Statistic> destination = new HashMap<>();
			for (String key : statisticEntities.keySet()) {
				Statistic statistic = statisticEntities.get(key);
				destination.put(key, statistic);
				statistic.setChildren(convertChildrenOf(id + key));
			}

			return destination;
		}

		private List<Type> getAllTypes() {
			List list = Lists.newLinkedList();
			list.add(Type.TYPE_BALANCES_BY_ACCOUNT);
			list.add(Type.TYPE_BALANCES_BY_ACCOUNT_TYPE_GROUP);
			list.add(Type.TYPE_BY_CATEGORY);
			list.add(Type.TYPE_LEFT_TO_SPEND);
			list.add(Type.TYPE_LEFT_TO_SPEND_AVERAGE);

			return list;
		}
	}
}
