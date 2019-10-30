package se.tink.repository.service

import se.tink.core.models.property.Property
import se.tink.repository.MutationHandler


interface PropertyService {

    fun listProperties(mutationHandler: MutationHandler<List<Property>>)
    fun deleteValuation(propertyId: String, mutationHandler: MutationHandler<Property>)
}
