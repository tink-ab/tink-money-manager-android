package com.tink.pfmsdk.analytics

data class AnalyticsScreen private constructor(val name: String) {

    companion object {

        @JvmField
        val ONBOARDING_FORGOT_PASSWORD = AnalyticsScreen("Onboarding.ForgotPassword")
        @JvmField
        val TRANSACTION_CHOOSE_CATEGORY = AnalyticsScreen("Transaction.ChooseCategory")
        @JvmField
        val BUDGET_EDIT = AnalyticsScreen("Budget.Edit")
        @JvmField
        val TRANSACTION_REQUEST_REIMBURSEMENT_AMOUNT = AnalyticsScreen("Transaction.RequestReimbursement.Amount")
        @JvmField
        val ONBOARDING_NO_SUCH_USER = AnalyticsScreen("Onboarding.NoSuchUser")
        @JvmField
        val DEBUG = AnalyticsScreen("Debug")
        @JvmField
        val PROFILE_SETTINGS_ABOUT = AnalyticsScreen("Profile.Settings.About")
        @JvmField
        val INCOME = AnalyticsScreen("Income")
        @JvmField
        val PROFILE_SETTINGS_CHANGE_PASSWORD = AnalyticsScreen("Profile.Settings.ChangePassword")
        @JvmField
        val PROFILE_ABOUT_TERMS_AND_CONDITIONS = AnalyticsScreen("Profile.About.TermsAndConditions")
        @JvmField
        val PAYMENT_DATE = AnalyticsScreen("Payment.Date")
        @JvmField
        val BUDGET_V2_DETAILS_TRANSACTIONS = AnalyticsScreen("Budget.v2.Details.Transactions")
        @JvmField
        val RESIDENCE_VALUATION_DETAILS = AnalyticsScreen("Residence.Valuation.Details")
        @JvmField
        val ONBOARDING_ADD_CREDENTIALS = AnalyticsScreen("Onboarding.AddCredentials")
        @JvmField
        val PROFILE_PROVIDER_SEARCH = AnalyticsScreen("Profile.Provider.Search")
        @JvmField
        val BUDGET_TRANSACTIONS = AnalyticsScreen("Budget.Transactions")
        @JvmField
        val PROFILE_CHOOSE_CREDENTIAL_TYPE = AnalyticsScreen("Profile.ChooseCredentialType")
        @JvmField
        val OVERVIEW_REFRESH_CREDENTIALS_SUPPLEMENTAL_INFORMATION = AnalyticsScreen("Overview.RefreshCredentials.SupplementalInformation")
        @JvmField
        val TRANSFERS_K_Y_C_TAX_COUNTRY = AnalyticsScreen("TransfersKYC.TaxCountry")
        @JvmField
        val RECURRING_TRANSACTIONS_ADD_SELECT_TRANSACTION = AnalyticsScreen("RecurringTransactions.Add.SelectTransaction")
        @JvmField
        val RECURRING_TRANSACTIONS_ADD_SIMILAR_TRANSACTIONS = AnalyticsScreen("RecurringTransactions.Add.SimilarTransactions")
        @JvmField
        val ACCOUNTS_BALANCE_EVERYDAY = AnalyticsScreen("Accounts.Balance.Everyday")
        @JvmField
        val PROFILE_CREDENTIAL_THIRD_PARTY_AUTHENTICATION = AnalyticsScreen("Profile.Credential.ThirdPartyAuthentication")
        @JvmField
        val OVERVIEW_REFRESH_CREDENTIALS_UPDATING = AnalyticsScreen("Overview.RefreshCredentials.Updating")
        @JvmField
        val ONBOARDING_CREDENTIALS_SUPPLEMENTAL_INFORMATION = AnalyticsScreen("Onboarding.CredentialsSupplementalInformation")
        @JvmField
        val RECURRING_TRANSACTIONS = AnalyticsScreen("RecurringTransactions")
        @JvmField
        val BUDGET_CREATE_CHOOSE_CATEGORY = AnalyticsScreen("Budget.Create.ChooseCategory")
        @JvmField
        val PROFILE_CREDENTIAL_CHOOSE_ACCESS_TYPE = AnalyticsScreen("Profile.Credential.ChooseAccessType")
        @JvmField
        val RECURRING_TRANSACTIONS_DETAIL_EDIT = AnalyticsScreen("RecurringTransactions.Detail.Edit")
        @JvmField
        val PROFILE_PUSH_NOTIFICATIONS = AnalyticsScreen("Profile.PushNotifications")
        @JvmField
        val ONBOARDING_CREATE_USER_USERNAME_PASSWORD = AnalyticsScreen("Onboarding.CreateUser.UsernamePassword")
        @JvmField
        val PROFILE_ID_CONTROL_EVENT = AnalyticsScreen("Profile.IdControl.Event")
        @JvmField
        val PROFILE_ABOUT_APP = AnalyticsScreen("Profile.About.App")
        @JvmField
        val ACCOUNT_DETAIL = AnalyticsScreen("Account.Detail")
        @JvmField
        val OVERVIEW = AnalyticsScreen("Overview")
        @JvmField
        val TRANSACTION_EDIT = AnalyticsScreen("Transaction.Edit")
        @JvmField
        val ONBOARDING_SUGGESTED_PROVIDERS = AnalyticsScreen("Onboarding.SuggestedProviders")
        @JvmField
        val ACCOUNTS = AnalyticsScreen("Accounts")
        @JvmField
        val CATEGORY_LIST = AnalyticsScreen("Category.List")
        @JvmField
        val PAYMENT_INVOICE = AnalyticsScreen("Payment.Invoice")
        @JvmField
        val ONBOARDING_ACCEPT_TERMS_PRIVACY = AnalyticsScreen("Onboarding.AcceptTerms.Privacy")
        @JvmField
        val PAYMENT_EDIT = AnalyticsScreen("Payment.Edit")
        @JvmField
        val ONBOARDING_ACCEPT_TERMS = AnalyticsScreen("Onboarding.AcceptTerms")
        @JvmField
        val BUDGET_V2_EDIT = AnalyticsScreen("Budget.v2.Edit")
        @JvmField
        val BUDGET_V2_DETAILS = AnalyticsScreen("Budget.v2.Details")
        @JvmField
        val ONBOARDING_SPLASH = AnalyticsScreen("Onboarding.Splash")
        @JvmField
        val PROFILE_PROVIDER_CHOOSE_ACCESS_TYPE = AnalyticsScreen("Profile.Provider.ChooseAccessType")
        @JvmField
        val ONBOARDING_ENTER_OTP = AnalyticsScreen("Onboarding.EnterOtp")
        @JvmField
        val PROFILE_SETTINGS_REMOVE_ACCOUNT = AnalyticsScreen("Profile.Settings.RemoveAccount")
        @JvmField
        val FINANCIAL_SERVICES_MORTGAGE_TAKEOVER = AnalyticsScreen("FinancialServicesMortgageTakeover")
        @JvmField
        val ONBOARDING_ACCEPT_TERMS_GENERAL_TERMS = AnalyticsScreen("Onboarding.AcceptTerms.GeneralTerms")
        @JvmField
        val PROFILE_ABOUT_PRIVACY = AnalyticsScreen("Profile.About.Privacy")
        @JvmField
        val BUDGET_CREATE_CHOOSE_AMOUNT = AnalyticsScreen("Budget.Create.ChooseAmount")
        @JvmField
        val ONBOARDING_CREATE_USER = AnalyticsScreen("Onboarding.CreateUser")
        @JvmField
        val LEFT_TO_SPEND_TUTORIAL = AnalyticsScreen("LeftToSpend.Tutorial")
        @JvmField
        val ONBOARDING_NEW_OR_EXISTING_USER = AnalyticsScreen("Onboarding.NewOrExistingUser")
        @JvmField
        val ONBOARDING_TERMS_AND_CONDITIONS = AnalyticsScreen("Onboarding.TermsAndConditions")
        @JvmField
        val TRANSACTION = AnalyticsScreen("Transaction")
        @JvmField
        val ONBOARDING_CHOOSE_CREDENTIAL_TYPE = AnalyticsScreen("Onboarding.ChooseCredentialType")
        @JvmField
        val ONBOARDING_PROVIDER_GROUP_LIST = AnalyticsScreen("Onboarding.ProviderGroupList")
        @JvmField
        val PROFILE_PROVIDERS_LIST = AnalyticsScreen("Profile.ProvidersList")
        @JvmField
        val BUDGET = AnalyticsScreen("Budget")
        @JvmField
        val TRANSACTIONS_SIMILAR = AnalyticsScreen("Transactions.Similar")
        @JvmField
        val PROFILE_SETTINGS_OPEN_SOURCE = AnalyticsScreen("Profile.Settings.OpenSource")
        @JvmField
        val TRANSFERS_K_Y_C = AnalyticsScreen("TransfersKYC")
        @JvmField
        val PROFILE_ABOUT_FEEDBACK = AnalyticsScreen("Profile.About.Feedback")
        @JvmField
        val SUGGEST_TRANSACTION = AnalyticsScreen("Suggest.Transaction")
        @JvmField
        val BUDGET_V2_CREATION_FIELDS = AnalyticsScreen("Budget.v2.Creation.Fields")
        @JvmField
        val ONBOARDING_MARKET_SELECTION = AnalyticsScreen("Onboarding.MarketSelection")
        @JvmField
        val PAYMENT_NEW_RECIPIENT = AnalyticsScreen("Payment.NewRecipient")
        @JvmField
        val PAYMENT_SCAN = AnalyticsScreen("Payment.Scan")
        @JvmField
        val ONBOARDING_SLIDESHOW = AnalyticsScreen("Onboarding.Slideshow")
        @JvmField
        val BETA_INFORMATION = AnalyticsScreen("BetaInformation")
        @JvmField
        val PROFILE_PROVIDERS_LIST_GROUP = AnalyticsScreen("Profile.ProvidersList.Group")
        @JvmField
        val PROFILE_SETTINGS_LANGUAGE = AnalyticsScreen("Profile.Settings.Language")
        @JvmField
        val PROFILE_SETTINGS_CHANGE_EMAIL = AnalyticsScreen("Profile.Settings.ChangeEmail")
        @JvmField
        val EVENTS = AnalyticsScreen("Events")
        @JvmField
        val TRANSFERS_K_Y_C_P_E_P = AnalyticsScreen("TransfersKYC.PEP")
        @JvmField
        val SAVINGS_GOAL_CREATE = AnalyticsScreen("SavingsGoal.Create")
        @JvmField
        val PROFILE_MY_INFORMATION = AnalyticsScreen("Profile.MyInformation")
        @JvmField
        val TRANSACTION_COUNTERPART_PICKER_LIST = AnalyticsScreen("Transaction.CounterpartPickerList")
        @JvmField
        val PROFILE_SETTINGS_STYLE = AnalyticsScreen("Profile.Settings.Style")
        @JvmField
        val ONBOARDING_INSIGHTS = AnalyticsScreen("Onboarding.Insights")
        @JvmField
        val BUDGET_V2_CREATION_FILTER_SEARCH = AnalyticsScreen("Budget.v2.Creation.Filter.Search")
        @JvmField
        val TRANSFER_TO_RECIPIENT = AnalyticsScreen("Transfer.ToRecipient")
        @JvmField
        val TRANSFERS_K_Y_C_ON_OWN_BEHALF = AnalyticsScreen("TransfersKYC.OnOwnBehalf")
        @JvmField
        val ONBOARDING_NO_BANK_ID_FOUND = AnalyticsScreen("Onboarding.NoBankIdFound")
        @JvmField
        val ONBOARDING_FINGERPRINT_LOGIN = AnalyticsScreen("Onboarding.FingerprintLogin")
        @JvmField
        val PROFILE_CREDENTIAL_PROGRESS = AnalyticsScreen("Profile.Credential.Progress")
        @JvmField
        val SEARCH = AnalyticsScreen("Search")
        @JvmField
        val ACCOUNT_BALANCE = AnalyticsScreen("Account.Balance")
        @JvmField
        val PROFILE_CREDENTIAL_MIGRATION = AnalyticsScreen("Profile.Credential.Migration")
        @JvmField
        val PROFILE_HELP = AnalyticsScreen("Profile.Help")
        @JvmField
        val TRANSFERS_K_Y_C_COMPLIANT = AnalyticsScreen("TransfersKYC.Compliant")
        @JvmField
        val PROFILE_CREDENTIAL_EDIT = AnalyticsScreen("Profile.Credential.Edit")
        @JvmField
        val PROFILE_ID_CONTROL = AnalyticsScreen("Profile.IdControl")
        @JvmField
        val ACCOUNTS_BALANCE_LOANS = AnalyticsScreen("Accounts.Balance.Loans")
        @JvmField
        val PROFILE_SETTINGS_PERIOD_BREAK = AnalyticsScreen("Profile.Settings.PeriodBreak")
        @JvmField
        val OVERVIEW_REFRESH_CREDENTIALS_CHOOSE_ACCESS_TYPE = AnalyticsScreen("Overview.RefreshCredentials.ChooseAccessType")
        @JvmField
        val BUDGET_EDIT_CHOOSE_AMOUNT = AnalyticsScreen("Budget.Edit.ChooseAmount")
        @JvmField
        val FEED = AnalyticsScreen("Feed")
        @JvmField
        val TRANSACTION_EDIT_PART = AnalyticsScreen("Transaction.Edit.Part")
        @JvmField
        val PROFILE_CREDENTIAL_SUPPLEMENTAL_INFORMATION = AnalyticsScreen("Profile.Credential.SupplementalInformation")
        @JvmField
        val BUDGET_V2_CREATION_FILTER = AnalyticsScreen("Budget.v2.Creation.Filter")
        @JvmField
        val OVERVIEW_REFRESH_CREDENTIALS_THIRD_PARTY_AUTHENTICATION = AnalyticsScreen("Overview.RefreshCredentials.ThirdPartyAuthentication")
        @JvmField
        val TRANSFER_FROM_ACCOUNT = AnalyticsScreen("Transfer.FromAccount")
        @JvmField
        val ONBOARDING_CREDENTIALS_PROGRESS = AnalyticsScreen("Onboarding.CredentialsProgress")
        @JvmField
        val TRANSFER = AnalyticsScreen("Transfer")
        @JvmField
        val ONBOARDING_ADD_PHONE_NUMBER = AnalyticsScreen("Onboarding.AddPhoneNumber")
        @JvmField
        val ONBOARDING_LOGIN_USERNAME_PASSWORD = AnalyticsScreen("Onboarding.Login.UsernamePassword")
        @JvmField
        val OVERVIEW_REFRESH_CREDENTIALS = AnalyticsScreen("Overview.RefreshCredentials")
        @JvmField
        val LEFT_TO_SPEND_DETAIL = AnalyticsScreen("LeftToSpend.Detail")
        @JvmField
        val ACCOUNTS_BALANCE_SAVINGS = AnalyticsScreen("Accounts.Balance.Savings")
        @JvmField
        val RECURRING_TRANSACTIONS_DETAIL = AnalyticsScreen("RecurringTransactions.Detail")
        @JvmField
        val PROFILE = AnalyticsScreen("Profile")
        @JvmField
        val TRANSFERS_K_Y_C_TAX = AnalyticsScreen("TransfersKYC.Tax")
        @JvmField
        val OVERVIEW_REFRESH_CREDENTIALS_OPEN_BANKING_CONSENT = AnalyticsScreen("Overview.RefreshCredentials.OpenBankingConsent")
        @JvmField
        val ACCOUNT_SETTINGS = AnalyticsScreen("Account.Settings")
        @JvmField
        val ONBOARDING_LOGIN = AnalyticsScreen("Onboarding.Login")
        @JvmField
        val ONBOARDING_ADD_PRIMARY_PROVIDER = AnalyticsScreen("Onboarding.AddPrimaryProvider")
        @JvmField
        val ONBOARDING_PROVIDER_LIST = AnalyticsScreen("Onboarding.ProviderList")
        @JvmField
        val PROFILE_ABOUT_APP_MORE = AnalyticsScreen("Profile.About.App.More")
        @JvmField
        val TRACKING_ERROR = AnalyticsScreen("TrackingError")
        @JvmField
        val TRANSFER_NEW_RECIPIENT = AnalyticsScreen("Transfer.NewRecipient")
        @JvmField
        val PAYMENT = AnalyticsScreen("Payment")
        @JvmField
        val RECURRING_TRANSACTIONS_DETAIL_EDIT_DELETE = AnalyticsScreen("RecurringTransactions.Detail.Edit.Delete")
        @JvmField
        val TRANSACTIONS_LIST = AnalyticsScreen("Transactions.List")
        @JvmField
        val PROFILE_ABOUT = AnalyticsScreen("Profile.About")
        @JvmField
        val RESIDENCE = AnalyticsScreen("Residence")
        @JvmField
        val SEARCH_SUGGEST = AnalyticsScreen("Search.Suggest")
        @JvmField
        val PAYMENT_TO_RECIPIENT = AnalyticsScreen("Payment.ToRecipient")
        @JvmField
        val ONBOARDING_CHOOSE_ACCESS_TYPE = AnalyticsScreen("Onboarding.ChooseAccessType")
        @JvmField
        val PAYMENT_FROM_ACCOUNT = AnalyticsScreen("Payment.FromAccount")
        @JvmField
        val BUDGET_CREATE_SEARCH = AnalyticsScreen("Budget.Create.Search")
        @JvmField
        val PROFILE_SETTINGS_TELL_A_FRIEND = AnalyticsScreen("Profile.Settings.TellAFriend")
        @JvmField
        val TRANSACTION_REQUEST_REIMBURSEMENT_PHONE_NUMBER = AnalyticsScreen("Transaction.RequestReimbursement.PhoneNumber")
        @JvmField
        val SAVINGS_GOAL = AnalyticsScreen("SavingsGoal")
        @JvmField
        val PROFILE_SETTINGS = AnalyticsScreen("Profile.Settings")
        @JvmField
        val LEFT_TO_SPEND = AnalyticsScreen("LeftToSpend")
        @JvmField
        val EVENTS_ARCHIVE = AnalyticsScreen("Events.Archive")
        @JvmField
        val PROFILE_CREDENTIAL = AnalyticsScreen("Profile.Credential")
        @JvmField
        val EXPENSES = AnalyticsScreen("Expenses")
        @JvmField
        val ONBOARDING_CREDENTIALS_THIRD_PARTY_AUTHENTICATION = AnalyticsScreen("Onboarding.CredentialsThirdPartyAuthentication")
        @JvmField
        val PROFILE_PROVIDER_ADD = AnalyticsScreen("Profile.Provider.Add")
        @JvmField
        val PAYMENT_RECIPIENT_TYPE = AnalyticsScreen("Payment.RecipientType")
        @JvmField
        val ONBOARDING_CREATE_USER_EMAIL = AnalyticsScreen("Onboarding.CreateUser.Email")

    }
}
