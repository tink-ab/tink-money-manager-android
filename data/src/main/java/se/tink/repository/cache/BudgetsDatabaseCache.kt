package se.tink.repository.cache

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.Transformations
//import se.tink.converter.budgets.toCoreModel
//import se.tink.converter.budgets.toEntity
//import com.tink.model.budget.BudgetSummary
//import se.tink.repository.cache.database.CacheDatabase
//import se.tink.repository.cache.models.budgets.BudgetEntity
//
//class BudgetsDatabaseCache(
//    val cacheDatabase: CacheDatabase
//) : LiveDataCache<List<BudgetSummary>> {
//
//    private val dao get() = cacheDatabase.budgetsDao()
//
//    override fun read(): LiveData<List<BudgetSummary>> =
//        Transformations.map(dao.getAll()) { it.toCoreModel() }
//
//    override fun clearAndInsert(item: List<BudgetSummary>) = dao.clearAndInsert(item.toEntity())
//    override fun insert(item: List<BudgetSummary>) = dao.insert(item.toEntity())
//    override fun update(item: List<BudgetSummary>) = dao.update(item.toEntity())
//    override fun delete(item: List<BudgetSummary>) = dao.delete(item.toEntity())
//    override fun clear() = dao.clear()
//}
//
//private fun List<BudgetSummary>.toEntity() = map { it.toEntity() }
//private fun List<BudgetEntity>.toCoreModel() = map { it.toCoreModel() }