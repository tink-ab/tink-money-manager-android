package se.tink.repository.service

import se.tink.core.models.product.TinkApplication
import se.tink.core.models.product.TinkApplicationForm
import se.tink.core.models.product.TinkApplicationSummary
import se.tink.core.models.product.TinkApplicationType
import se.tink.core.models.transfer.SignableOperation
import se.tink.repository.MutationHandler


interface ApplicationService {
    fun createApplication(applicationType: TinkApplicationType, handler: MutationHandler<TinkApplication>)
    fun getApplication(applicationId: String, handler: MutationHandler<TinkApplication>)
    fun deleteApplication(applicationId: String, handler: MutationHandler<String>)
    fun getApplicationSummary(applicationId: String, handler: MutationHandler<TinkApplicationSummary>)
    fun getApplicationSummaryList(handler: MutationHandler<List<TinkApplicationSummary>>)
    fun submitApplicationForm(form: TinkApplicationForm, handler: MutationHandler<TinkApplication>)
    fun submitApplication(applicationId: String, handler: MutationHandler<SignableOperation?>)
    fun getEligibleApplicationTypes(handler: MutationHandler<List<TinkApplicationType>>)
}
