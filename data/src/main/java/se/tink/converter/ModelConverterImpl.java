package se.tink.converter;

import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import se.tink.converter.account.AccountDTOToAccountConverter;
import se.tink.converter.account.AccountToAccountDTOConverter;
import se.tink.converter.account.UpdateAccountRequestConverter;
import se.tink.converter.account.accountDetails.AccountDetailsDTOtoAccountDetailsConverter;
import se.tink.converter.account.accountDetails.AccountDetailsToAccountDetailsDTOConverter;
import se.tink.converter.account.loan.LoanToLoanDTOConverter;
import se.tink.converter.activity.ActivityHtmlHeadResponseConverter;
import se.tink.converter.activity.ListActivityHtmlResponseToEventsPageConverter;
import se.tink.converter.authentication.AuthenticationResponseConverter;
import se.tink.converter.authentication.AuthenticationStatusConverter;
import se.tink.converter.authentication.InitiateSmsOtpResponseConverter;
import se.tink.converter.authentication.PhoneNumberAndPin6AuthenticationResponseConverter;
import se.tink.converter.authentication.ResetPin6ResponseConverter;
import se.tink.converter.authentication.SmsOtpAndPin6AuthenticationResponseConverter;
import se.tink.converter.authentication.ThirdPartyAppAuthenticationDTOToThirdPartyAppAuthenticationConverter;
import se.tink.converter.authentication.VerifySmsOtpResponseConverter;
import se.tink.converter.budgets.BudgetConverters;
import se.tink.converter.category.CategoryDTOToCategoryConverter;
import se.tink.converter.category.CategoryToCategoryDTOConverter;
import se.tink.converter.category.CategoryTreeDTOToCategoryTreeConverter;
import se.tink.converter.consents.AvailableConsentsResponseDTOToAvailableConsentsConverter;
import se.tink.converter.consents.ConsentActionToConsentActionDTOConverter;
import se.tink.converter.consents.ConsentsDTOToConcentConverter;
import se.tink.converter.consents.ConsentsDetailsResponseConverter;
import se.tink.converter.consents.ConsentsMessageDTOToConsentMessageResponseConverter;
import se.tink.converter.consents.ConsentsMessageLinkDTOToConsentMessageLinkResponseConverter;
import se.tink.converter.consents.GiveConsentRequestToGiveConsentRequestDTOConverter;
import se.tink.converter.credential.CachedCredentialToCredentialsEntity;
import se.tink.converter.credential.CredentialDTOToCredentialConverter;
import se.tink.converter.credential.CredentialsEntityToCachedCredential;
import se.tink.converter.date.DateTimeToDateConverter;
import se.tink.converter.date.DateTimeToTimestampConverter;
import se.tink.converter.date.DateToDateTimeConverter;
import se.tink.converter.date.DateToTimestampConverter;
import se.tink.converter.date.TimestampToDateTimeConverter;
import se.tink.converter.date.TimestampToLongConverter;
import se.tink.converter.device.AuthenticationMethodEnumResponsConverter;
import se.tink.converter.device.DeviceConfigurationMarketResponseConverter;
import se.tink.converter.device.DeviceConfigurationResponseConverter;
import se.tink.converter.device.UpdateI18NSettingsRequestConverter;
import se.tink.converter.device.UpdateI18NSettingsResponseConverter;
import se.tink.converter.follow.ExpensesFollowCriteriaDTOToExpensesFollowCriteriaConverter;
import se.tink.converter.follow.ExpensesFollowCriteriaToExpensesFollowCriteriaDTOConverter;
import se.tink.converter.follow.FollowDataDTOToFollowDataConverter;
import se.tink.converter.follow.FollowDataToFollowDataDTOConverter;
import se.tink.converter.follow.FollowItemDTOToFollowItemConverter;
import se.tink.converter.follow.FollowItemToCreateFollowItemRequestConverter;
import se.tink.converter.follow.FollowItemToFollowItemDTOConverter;
import se.tink.converter.follow.FollowItemToUpdateRequestConverter;
import se.tink.converter.follow.PeriodExactNumberPairDTOToPeriodExactNumberPairConverter;
import se.tink.converter.follow.PeriodExactNumberPairToPeriodExactNumberPairDTOConverter;
import se.tink.converter.follow.SavingsFollowCriteriaDTOToSavingsFollowCriteriaConverter;
import se.tink.converter.follow.SavingsFollowCriteriaToSavingsFollowCriteriaDTOConverter;
import se.tink.converter.follow.SearchFollowCriteriaDTOToSearchFollowCriteriaConverter;
import se.tink.converter.follow.SearchFollowCriteriaToSearchFollowCriteriaDTOConverter;
import se.tink.converter.identity.IdentityAnswerKeyConverter;
import se.tink.converter.identity.IdentityAnswerKeyDTOConverter;
import se.tink.converter.identity.IdentityEventAnswerConverter;
import se.tink.converter.identity.IdentityEventAnswerFromDTOConverter;
import se.tink.converter.identity.IdentityEventConverter;
import se.tink.converter.identity.IdentityEventDocumentationConverter;
import se.tink.converter.identity.IdentityEventSummaryConverter;
import se.tink.converter.identity.IdentityStateAddressConverter;
import se.tink.converter.identity.IdentityStateCompanyConverter;
import se.tink.converter.identity.IdentityStateCompanyEngagementConverter;
import se.tink.converter.identity.IdentityStateConverter;
import se.tink.converter.identity.IdentityStateCreditScoreConverter;
import se.tink.converter.identity.IdentityStateOutstandingDebtConverter;
import se.tink.converter.identity.IdentityStatePropertyConverter;
import se.tink.converter.identity.IdentityStateRecordOfNonPaymentConverter;
import se.tink.converter.identity.IdentityStateRoleConverter;
import se.tink.converter.identity.IdentityStateTaxDeclarationConverter;
import se.tink.converter.kyc.KycAnswersToStoreKycRequestConverter;
import se.tink.converter.misc.AmountDTOToAmountConverter;
import se.tink.converter.misc.AmountToAmountDTOConverter;
import se.tink.converter.misc.ExactNumberDTOToExactNumberConverter;
import se.tink.converter.misc.ExactNumberToExactNumberDTOConverter;
import se.tink.converter.misc.FieldDTOToFieldConverter;
import se.tink.converter.misc.FieldToFieldDTOConverter;
import se.tink.converter.misc.ImagesConverter;
import se.tink.converter.misc.PeriodDTOToPeriodConverter;
import se.tink.converter.misc.PeriodToPeriodDTOConverter;
import se.tink.converter.misc.StringToStringValueConverter;
import se.tink.converter.onboarding.insights.AmountByCategoryCodeDTOToAmountByCategoryConverter;
import se.tink.converter.onboarding.insights.AmountByWeekdayDTOToAmountByWeekdayConverter;
import se.tink.converter.onboarding.insights.HistogramBucketDTOToHistogramBucketConverter;
import se.tink.converter.onboarding.insights.InsightsCategoriesDTOToInsightsCategoriesConverter;
import se.tink.converter.onboarding.insights.InsightsDailySpendDToToInsightsDailySpendConverter;
import se.tink.converter.onboarding.insights.InsightsLeftToSpendDTOToInsightsLeftToSpendConverter;
import se.tink.converter.onboarding.insights.InsightsMortgageDTOToInsightsMortgageConverter;
import se.tink.converter.onboarding.insights.InsightsResponseToInsightsConverter;
import se.tink.converter.onboarding.insights.InsightsSavingsDTOToInsightsSavingsConverter;
import se.tink.converter.onboarding.insights.LeftToSpendByPeriodDTOToLeftToSpendByPeriodConverter;
import se.tink.converter.provider.ProviderDTOToProviderConverter;
import se.tink.converter.provider.ProviderToProviderDTOConverter;
import se.tink.converter.pushnotifications.DeviceFromDTOConverter;
import se.tink.converter.pushnotifications.DeviceToDeviceDTOConverter;
import se.tink.converter.pushnotifications.RegisterPushNotificationTokenRequestToSendConverter;
import se.tink.converter.pushnotifications.ResponseToRegisterPushNotificationTokenResponseConverter;
import se.tink.converter.request.TransactionToUpdateTransactionRequest;
import se.tink.converter.settings.I18NSettingsConverter;
import se.tink.converter.settings.NotificationSettingsConverter;
import se.tink.converter.settings.NotificationSettingsResponseConverter;
import se.tink.converter.settings.NotificationSettingsUpdateRequestConverter;
import se.tink.converter.settings.PeriodSettingsConverter;
import se.tink.converter.settings.PeriodSettingsConverterUpdateRequest;
import se.tink.converter.statistic.StatisticDTOToStatisticConverter;
import se.tink.converter.statistic.StatisticToStatisticDTOConverter;
import se.tink.converter.statistic.StatisticTreeDTOStatisticTreeConverter;
import se.tink.converter.streaming.StringToStreamingResponseTypeConverter;
import se.tink.converter.tracking.TrackingConfigurationResponseDTOToTrackingConfigurationResponseConverter;
import se.tink.converter.tracking.TrackingEventToTrackingDTOConverter;
import se.tink.converter.tracking.TrackingRequestToTrackingRequestDTOConverter;
import se.tink.converter.tracking.TrackingTimingToTrackingTimingDTOConverter;
import se.tink.converter.tracking.TrackingViewToTrackingViewDTOConverter;
import se.tink.converter.transaction.CreatePartAndCounterpartResponseConverter;
import se.tink.converter.transaction.QueryTransactionResponseDTOToSearchResultMetadataConverter;
import se.tink.converter.transaction.SuggestTransactionConverter;
import se.tink.converter.transaction.SuggestTransactionsResponseConverter;
import se.tink.converter.transaction.TagDTOToTagConverter;
import se.tink.converter.transaction.TagToTagDTOConverter;
import se.tink.converter.transaction.TransactionClusterConverter;
import se.tink.converter.transaction.TransactionDTOToTransactionConverter;
import se.tink.converter.transaction.TransactionDetailsDTOToTransactionDetailsConverter;
import se.tink.converter.transaction.TransactionDetailsToTransactionDetailsDTOConverter;
import se.tink.converter.transaction.TransactionToTransactionDTOConverter;
import se.tink.converter.transfer.ClearingLookupResponseToClearingConverter;
import se.tink.converter.transfer.CreateTransferRequestConverter;
import se.tink.converter.transfer.GiroLookupEntityDTOToGiroLookupEntityConverter;
import se.tink.converter.transfer.SignableOperationDTOtoSignableOperationConverter;
import se.tink.converter.transfer.TransferDTOToTransferConverter;
import se.tink.converter.transfer.TransferDestinationDTOToTransferDestinationConverter;
import se.tink.converter.transfer.TransferDestinationPerAccountDTOtoTransferDestinationPerAccount;
import se.tink.converter.transfer.TransferToTransferDTOConverter;
import se.tink.converter.transfer.UpdateTransferRequestConverter;
import se.tink.converter.user.CollectBankIdAuthenticationRequestDTOConverter;
import se.tink.converter.user.DeleteUserReasonConverter;
import se.tink.converter.user.EmailAndPasswordAuthenticationResponseDTOConverter;
import se.tink.converter.user.GetProfileResponseDTOConverter;
import se.tink.converter.user.InitiateBankIdAuthenticationResponseDTOConverter;
import se.tink.converter.user.LoginResponseDTOConverter;
import se.tink.converter.user.RegisterResponseMapper;
import se.tink.converter.user.UpdateEmailResponseDTOConverter;
import se.tink.converter.user.UserConfigurationDTOToUserConfigurationConverter;
import se.tink.converter.user.UserConfigurationToUserConfigurationDTOConverter;
import se.tink.converter.user.UserProfileDTOConverter;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.CacheConverterFactory;

public class ModelConverterImpl implements ModelConverter {

    private final Map<Integer, AbstractConverter> converterMap =
            new HashMap<Integer, AbstractConverter>();

    public ModelConverterImpl() {
        addConverters();
    }

    private <D> AbstractConverter getConverter(Class<?> aClass, Class<D> destinationType) {
        return converterMap.get(getHash(aClass, destinationType));
    }

    private <D> Integer getHash(Class<?> aClass, Class<D> destinationType) {
        int hashCode = 17;
        hashCode = hashCode * 31 + aClass.hashCode();
        hashCode = hashCode * 31 + destinationType.hashCode();
        return hashCode;
    }

    @Override
    public <KR, VR, KI, VI> Map<KR, VR> map(
            Map<KI, VI> source, Class<KR> destinationKeyType, Class<VR> destinationValueType) {
        Map<KR, VR> returnMap = Maps.newHashMap();
        Set<KI> keySet = source.keySet();
        for (KI key : keySet) {
            VI value = source.get(key);
            returnMap.put(map(key, destinationKeyType), map(value, destinationValueType));
        }
        return returnMap;
    }

    @Override
    public <S, D> List<D> map(Collection<S> source, Class<D> destinationType) {
        ArrayList<D> destinationList = new ArrayList<>();
        for (S sourceObject : source) {
            destinationList.add(map(sourceObject, destinationType));
        }
        return destinationList;
    }

    @Override
    public <S, D> D map(S source, Class<D> destinationType) {
        if (source.getClass().equals(destinationType)) {
            return (D) source;
        }
        AbstractConverter<S, D> converter = getConverter(source.getClass(), destinationType);
        return converter.convert(source);
    }

    private void addConverters() {
        setupAuthenticationConverters();
        setupAccountConverters();
        setupCredentialConverters();
        setupProviderConverters();
        setupStatisticConverters();
        setupTransactionConverters();
        setupSupportModelConverters();
        setupCategoryConverters();
        setupTransferConverters();
        setupTrackingConverters();
        setupStreamingResponseConverter();
        setupRequestConverters();
        setupFollowItemConverters();
        setupUserConfigurationConverters();
        setupUserConverters();
        setupActivityConverters();
        setupSettingsConverters();
        setupPushNotificationsConverters();
        setupInsightsConverters();
        setupDeviceConfigurationConverters();
        setupConsentConverters();
        setupCacheEntityConverters();
        setupIdentityConverters();
        setupBudgetConverters();
        setupKycConverters();
    }

    private void addConverter(AbstractConverter abstractConverter) {
        converterMap.put(
                getHash(
                        abstractConverter.getSourceClass(),
                        abstractConverter.getDestinationClass()),
                abstractConverter);
    }

    private void addConverters(List<AbstractConverter<?, ?>> converters) {
        for (AbstractConverter converter : converters) {
            addConverter(converter);
        }
    }

    private void setupIdentityConverters() {
        addConverter(new IdentityAnswerKeyConverter(this));
        addConverter(new IdentityEventAnswerConverter(this));
        addConverter(new IdentityStateConverter(this));
        addConverter(new IdentityEventSummaryConverter(this));
        addConverter(new IdentityAnswerKeyDTOConverter(this));

        addConverter(new IdentityStateAddressConverter(this));
        addConverter(new IdentityStateCompanyEngagementConverter(this));
        addConverter(new IdentityStateRecordOfNonPaymentConverter(this));
        addConverter(new IdentityStateCreditScoreConverter(this));
        addConverter(new IdentityStateTaxDeclarationConverter(this));
        addConverter(new IdentityStateOutstandingDebtConverter(this));

        addConverter(new IdentityStatePropertyConverter(this));
        addConverter(new IdentityEventConverter(this));

        addConverter(new IdentityStateRoleConverter(this));
        addConverter(new IdentityStateCompanyConverter(this));
        addConverter(new IdentityEventDocumentationConverter(this));
        addConverter(new IdentityEventAnswerFromDTOConverter(this));
    }

    private void setupAuthenticationConverters() {
        addConverter(new AuthenticationStatusConverter());
        addConverter(new VerifySmsOtpResponseConverter(this));
        addConverter(new SmsOtpAndPin6AuthenticationResponseConverter(this));
        addConverter(new InitiateSmsOtpResponseConverter(this));
        addConverter(new AuthenticationResponseConverter(this));
        addConverter(new PhoneNumberAndPin6AuthenticationResponseConverter(this));
        addConverter(
                new ThirdPartyAppAuthenticationDTOToThirdPartyAppAuthenticationConverter());
        addConverter(new ResetPin6ResponseConverter(this));
    }

    private void setupSettingsConverters() {
        addConverter(new PeriodSettingsConverter(this));
        addConverter(new PeriodSettingsConverterUpdateRequest(this));

        addConverter(new NotificationSettingsConverter(this));
        addConverter(new NotificationSettingsUpdateRequestConverter(this));
        addConverter(new NotificationSettingsResponseConverter(this));
    }

    private void setupActivityConverters() {
        addConverter(new ActivityHtmlHeadResponseConverter(this));
        addConverter(new ListActivityHtmlResponseToEventsPageConverter(this));
    }

    private void setupUserConverters() {
        addConverter(new UserProfileDTOConverter());
        addConverter(new LoginResponseDTOConverter(this));
        addConverter(new CollectBankIdAuthenticationRequestDTOConverter(this));
        addConverter(new EmailAndPasswordAuthenticationResponseDTOConverter(this));
        addConverter(new UpdateEmailResponseDTOConverter(this));
        addConverter(new GetProfileResponseDTOConverter(this));
        addConverter(new RegisterResponseMapper(this));
        addConverter(new InitiateBankIdAuthenticationResponseDTOConverter(this));
        addConverter(new DeleteUserReasonConverter(this));
    }

    private void setupStreamingResponseConverter() {
        addConverter(new StringToStreamingResponseTypeConverter(this));
    }

    private void setupTrackingConverters() {
        addConverter(new TrackingEventToTrackingDTOConverter(this));
        addConverter(new TrackingRequestToTrackingRequestDTOConverter(this));
        addConverter(new TrackingTimingToTrackingTimingDTOConverter(this));
        addConverter(new TrackingViewToTrackingViewDTOConverter(this));
        addConverter(
                new TrackingConfigurationResponseDTOToTrackingConfigurationResponseConverter());
    }

    private void setupTransferConverters() {
        addConverter(new TransferDTOToTransferConverter(this));
        addConverter(new TransferToTransferDTOConverter(this));
        addConverter(new CreateTransferRequestConverter(this));
        addConverter(new UpdateTransferRequestConverter(this));
        addConverter(new TransferDestinationPerAccountDTOtoTransferDestinationPerAccount(this));
        addConverter(new TransferDestinationDTOToTransferDestinationConverter(this));
        addConverter(new SignableOperationDTOtoSignableOperationConverter(this));
        addConverter(new GiroLookupEntityDTOToGiroLookupEntityConverter(this));
        addConverter(new ClearingLookupResponseToClearingConverter(this));
    }

    private void setupCategoryConverters() {
        addConverter(new CategoryDTOToCategoryConverter(this));
        addConverter(new CategoryToCategoryDTOConverter(this));
        addConverter(new CategoryTreeDTOToCategoryTreeConverter(this));
    }

    private void setupTransactionConverters() {
        addConverter(new TransactionDTOToTransactionConverter(this));
        addConverter(new TransactionToTransactionDTOConverter(this));

        addConverter(new TransactionDetailsDTOToTransactionDetailsConverter(this));
        addConverter(new TransactionDetailsToTransactionDetailsDTOConverter(this));

        addConverter(new TagToTagDTOConverter(this));
        addConverter(new TagDTOToTagConverter(this));

        addConverter(new SuggestTransactionConverter(this));
        addConverter(new SuggestTransactionsResponseConverter(this));
        addConverter(new TransactionClusterConverter(this));

        addConverter(new QueryTransactionResponseDTOToSearchResultMetadataConverter(this));
        addConverter(new CreatePartAndCounterpartResponseConverter(this));
    }

    private void setupStatisticConverters() {
        addConverter(new StatisticTreeDTOStatisticTreeConverter(this));
        addConverter(new StatisticToStatisticDTOConverter(this));
        addConverter(new StatisticDTOToStatisticConverter(this));
    }

    private void setupProviderConverters() {
        addConverter(new ProviderDTOToProviderConverter(this));
        addConverter(new ProviderToProviderDTOConverter(this));
    }

    private void setupCredentialConverters() {
        addConverter(new CredentialDTOToCredentialConverter(this));

        addConverter(new CredentialsEntityToCachedCredential());
        addConverter(new CachedCredentialToCredentialsEntity());
    }

    private void setupSupportModelConverters() {
        addConverter(new FieldToFieldDTOConverter(this));
        addConverter(new FieldDTOToFieldConverter(this));

        addConverter(new PeriodDTOToPeriodConverter(this));
        addConverter(new PeriodToPeriodDTOConverter(this));

        addConverter(new ExactNumberDTOToExactNumberConverter());
        addConverter(new ExactNumberToExactNumberDTOConverter(this));

        addConverter(new AmountDTOToAmountConverter());
        addConverter(new AmountToAmountDTOConverter(this));

        addConverter(new DateToDateTimeConverter());
        addConverter(new DateTimeToDateConverter());
        addConverter(new DateTimeToTimestampConverter());
        addConverter(new DateToTimestampConverter());

        addConverter(new TimestampToDateTimeConverter());
        addConverter(new TimestampToLongConverter(this));

        addConverter(new StringToStringValueConverter(this));
        addConverter(new ImagesConverter());
    }

    private void setupAccountConverters() {
        addConverter(new AccountDTOToAccountConverter(this));
        addConverter(new AccountToAccountDTOConverter(this));

        addConverter(new AccountDetailsToAccountDetailsDTOConverter(this));
        addConverter(new AccountDetailsDTOtoAccountDetailsConverter(this));

        addConverter(new UpdateAccountRequestConverter(this));

        addConverter(new LoanToLoanDTOConverter(this));
    }

    private void setupRequestConverters() {
        addConverter(new TransactionToUpdateTransactionRequest(this));
    }

    private void setupFollowItemConverters() {
        addConverter(new FollowItemDTOToFollowItemConverter(this));
        addConverter(new FollowItemToFollowItemDTOConverter(this));

        addConverter(new FollowDataDTOToFollowDataConverter(this));
        addConverter(new FollowDataToFollowDataDTOConverter(this));

        addConverter(new SavingsFollowCriteriaDTOToSavingsFollowCriteriaConverter(this));
        addConverter(new SavingsFollowCriteriaToSavingsFollowCriteriaDTOConverter(this));

        addConverter(new ExpensesFollowCriteriaDTOToExpensesFollowCriteriaConverter(this));
        addConverter(new ExpensesFollowCriteriaToExpensesFollowCriteriaDTOConverter(this));

        addConverter(new SearchFollowCriteriaDTOToSearchFollowCriteriaConverter(this));
        addConverter(new SearchFollowCriteriaToSearchFollowCriteriaDTOConverter(this));

        addConverter(new PeriodExactNumberPairDTOToPeriodExactNumberPairConverter(this));
        addConverter(new PeriodExactNumberPairToPeriodExactNumberPairDTOConverter(this));

        addConverter(new FollowItemToCreateFollowItemRequestConverter(this));
        addConverter(new FollowItemToUpdateRequestConverter(this));
    }

    private void setupUserConfigurationConverters() {
        addConverter(new UserConfigurationToUserConfigurationDTOConverter(this));
        addConverter(new UserConfigurationDTOToUserConfigurationConverter(this));
    }

    private void setupPushNotificationsConverters() {
        addConverter(new RegisterPushNotificationTokenRequestToSendConverter(this));
        addConverter(new ResponseToRegisterPushNotificationTokenResponseConverter(this));
    }

    private void setupInsightsConverters() {
        addConverter(new AmountByCategoryCodeDTOToAmountByCategoryConverter(this));
        addConverter(new AmountByWeekdayDTOToAmountByWeekdayConverter(this));
        addConverter(new HistogramBucketDTOToHistogramBucketConverter());
        addConverter(new LeftToSpendByPeriodDTOToLeftToSpendByPeriodConverter());
        addConverter(new InsightsCategoriesDTOToInsightsCategoriesConverter(this));
        addConverter(new InsightsDailySpendDToToInsightsDailySpendConverter(this));
        addConverter(new InsightsLeftToSpendDTOToInsightsLeftToSpendConverter(this));
        addConverter(new InsightsMortgageDTOToInsightsMortgageConverter(this));
        addConverter(new InsightsSavingsDTOToInsightsSavingsConverter(this));
        addConverter(new InsightsResponseToInsightsConverter(this));
    }

    private void setupDeviceConfigurationConverters() {
        addConverter(new DeviceConfigurationMarketResponseConverter(this));
        addConverter(new DeviceConfigurationResponseConverter(this));
        addConverter(new DeviceFromDTOConverter(this));
        addConverter(new DeviceToDeviceDTOConverter(this));

        addConverter(new AuthenticationMethodEnumResponsConverter(this));
        addConverter(new UpdateI18NSettingsRequestConverter(this));
        addConverter(new UpdateI18NSettingsResponseConverter(this));
        addConverter(new I18NSettingsConverter());
    }

    private void setupConsentConverters() {
        addConverter(new ConsentsDetailsResponseConverter(this));
        addConverter(new ConsentsDTOToConcentConverter(this));
        addConverter(new ConsentsMessageDTOToConsentMessageResponseConverter(this));
        addConverter(new ConsentsMessageLinkDTOToConsentMessageLinkResponseConverter(this));
        addConverter(new AvailableConsentsResponseDTOToAvailableConsentsConverter(this));
        addConverter(new ConsentActionToConsentActionDTOConverter());
        addConverter(new GiveConsentRequestToGiveConsentRequestDTOConverter(this));
    }

    private void setupCacheEntityConverters() {
        addConverters(CacheConverterFactory.getCacheConverters(this));
    }

    private void setupBudgetConverters() {
        addConverters(BudgetConverters.forConverter(this));
    }

    private void setupKycConverters() {
        addConverter(new KycAnswersToStoreKycRequestConverter());
    }
}
