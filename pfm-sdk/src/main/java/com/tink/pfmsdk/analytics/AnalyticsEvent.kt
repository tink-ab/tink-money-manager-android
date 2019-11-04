package com.tink.pfmsdk.analytics

data class AnalyticsEvent private constructor(val category: String, val action: String, val label: String) {

    companion object {

        @JvmField
        val PROFILE_CREDENTIAL_ITEM_CLICKED_ACCOUNT = AnalyticsEvent("Profile.Credential", "Item Clicked", "Account")
        @JvmField
        val OVERVIEW_REFRESHCREDENTIALS_CHOOSEACCESSTYPE_BUTTON_PRESSED_INFO = AnalyticsEvent("Overview.RefreshCredentials.ChooseAccessType", "Button pressed", "Info")
        @JvmField
        val PROFILE_PROVIDER_CHOOSEACCESSTYPE_BUTTON_PRESSED_OTHER_INFO = AnalyticsEvent("Profile.Provider.ChooseAccessType", "Button pressed", "Other Info")
        @JvmField
        val TRANSACTION_COUNTERPARTS_BUTTON_PRESSED_SAVE = AnalyticsEvent("Transaction.Counterparts", "Button pressed", "Save")
        @JvmField
        val PROFILE_CREDENTIAL_BUTTON_PRESSED_EDIT = AnalyticsEvent("Profile.Credential", "Button pressed", "Edit")
        @JvmField
        val OVERVIEW_REFRESHCREDENTIALS_BUTTON_PRESSED_SUPPLEMENTAL_INFORMATION = AnalyticsEvent("Overview.RefreshCredentials", "Button pressed", "Supplemental Information")
        @JvmField
        val PROFILE_PROVIDER_CHOOSEACCESSTYPE_BUTTON_PRESSED_OPEN_BANKING_INFO = AnalyticsEvent("Profile.Provider.ChooseAccessType", "Button pressed", "Open Banking Info")
        @JvmField
        val RECURRINGTRANSACTIONS_DETAIL_EDIT_TOGGLE_SWITCHED_MARK_AS_RECURRING_COST = AnalyticsEvent("RecurringTransactions.Detail.Edit", "Toggle Switched", "Mark As Recurring Cost")
        @JvmField
        val BUDGET_V2_DETAILS_BUTTON_PRESSED_PREVIOUS_PERIOD = AnalyticsEvent("Budget.v2.Details", "Button pressed", "Previous Period")
        @JvmField
        val TRANSACTION_REQUESTREIMBURSEMENT_BUTTON_PRESSED_SAVE = AnalyticsEvent("Transaction.RequestReimbursement", "Button pressed", "Save")
        @JvmField
        val LEFTTOSPEND_SHOW_PAGE_TWELVE_MONTHS = AnalyticsEvent("LeftToSpend", "Show page", "Twelve months")
        @JvmField
        val LEFTTOSPEND_BUTTON_PRESSED_TIME_PERIOD = AnalyticsEvent("LeftToSpend", "Button pressed", "Time period")
        @JvmField
        val OVERVIEW_SELECTED_BUDGET = AnalyticsEvent("Overview", "Selected", "Budget")
        @JvmField
        val ONBOARDING_CHOOSECREDENTIALTYPE_SELECTED_THIRDPARTY_AUTHENTICATION = AnalyticsEvent("Onboarding.ChooseCredentialType", "Selected", "Thirdparty Authentication")
        @JvmField
        val PROFILE_PROVIDER_CHOOSEACCESSTYPE_SELECTED_OPEN_BANKING = AnalyticsEvent("Profile.Provider.ChooseAccessType", "Selected", "Open Banking")
        @JvmField
        val OVERVIEW_REFRESHCREDENTIALS_UPDATING_BUTTON_PRESSED_CLOSE = AnalyticsEvent("Overview.RefreshCredentials.Updating", "Button pressed", "Close")
        @JvmField
        val TRANSACTION_COUNTERPARTS_BUTTON_PRESSED_DELETE = AnalyticsEvent("Transaction.Counterparts", "Button pressed", "Delete")
        @JvmField
        val OVERVIEW_REFRESHCREDENTIALS_CHOOSEACCESSTYPE_BUTTON_PRESSED_UPDATE_OTHER = AnalyticsEvent("Overview.RefreshCredentials.ChooseAccessType", "Button pressed", "Update Other")
        @JvmField
        val ONBOARDING_SLIDESHOW_SHOW_PAGE_SECURITY = AnalyticsEvent("Onboarding.Slideshow", "Show page", "Security")
        @JvmField
        val RECURRINGTRANSACTIONS_ADD_SIMILARTRANSACTIONS_BUTTON_PRESSED_SKIP = AnalyticsEvent("RecurringTransactions.Add.SimilarTransactions", "Button Pressed", "Skip")
        @JvmField
        val BUDGET_V2_CREATION_FILTER_SEARCH_BUTTON_PRESSED_KEYWORD = AnalyticsEvent("Budget.v2.Creation.Filter.Search", "Button pressed", "Keyword")
        @JvmField
        val OVERVIEW_REFRESHCREDENTIALS_SUPPLEMENTALINFORMATION_BUTTON_PRESSED_CANCEL = AnalyticsEvent("Overview.RefreshCredentials.SupplementalInformation", "Button pressed", "Cancel")
        @JvmField
        val OVERVIEW_REFRESHCREDENTIALS_OPENBANKINGCONSENT_BUTTON_PRESSED_NEXT = AnalyticsEvent("Overview.RefreshCredentials.OpenBankingConsent", "Button pressed", "Next")
        @JvmField
        val PROFILE_CREDENTIAL_DELETE_DIALOG_ACTION_DELETE = AnalyticsEvent("Profile.Credential.Delete", "Dialog Action", "Delete")
        @JvmField
        val EXPENSES_SHOW_PAGE_TWELVE_MONTHS = AnalyticsEvent("Expenses", "Show page", "Twelve months")
        @JvmField
        val BUDGET_V2_DETAILS_TRANSACTIONS_ITEM_CLICKED_TRANSACTION = AnalyticsEvent("Budget.v2.Details.Transactions", "Item clicked", "Transaction")
        @JvmField
        val BUDGET_V2_EDIT_BUTTON_PRESSED_DELETE = AnalyticsEvent("Budget.v2.Edit", "Button pressed", "Delete")
        @JvmField
        val TRANSFERSKYC_TAX_BUTTON_PRESSED_YES = AnalyticsEvent("TransfersKYC.Tax", "Button Pressed", "Yes")
        @JvmField
        val INCOME_BUTTON_PRESSED_TIME_PERIOD = AnalyticsEvent("Income", "Button pressed", "Time period")
        @JvmField
        val ONBOARDING_SLIDESHOW_BUTTON_PRESSED_MARKET = AnalyticsEvent("Onboarding.Slideshow", "Button pressed", "Market")
        @JvmField
        val ONBOARDING_SLIDESHOW_SHOW_PAGE_SECURITY_SWEDEN = AnalyticsEvent("Onboarding.Slideshow", "Show page", "Security Sweden")
        @JvmField
        val INCOME_SHOW_PAGE_ONE_MONTH = AnalyticsEvent("Income", "Show page", "One month")
        @JvmField
        val PROFILE_PROVIDER_CHOOSEACCESSTYPE_BUTTON_PRESSED_SKIP = AnalyticsEvent("Profile.Provider.ChooseAccessType", "Button pressed", "Skip")
        @JvmField
        val PAYMENT_QR_SCAN_SUCCESSFUL = AnalyticsEvent("Payment", "QR Scan", "Successful")
        @JvmField
        val PROFILE_CREDENTIAL_BUTTON_PRESSED_TOGGLE_CREDENTIAL_STATUS = AnalyticsEvent("Profile.Credential", "Button pressed", "Toggle Credential Status")
        @JvmField
        val OVERVIEW_SELECTED_RECENT_TRANSACTION = AnalyticsEvent("Overview", "Selected", "Recent transaction")
        @JvmField
        val RECURRINGTRANSACTIONS_DETAIL_SELECTED_TRANSACTION = AnalyticsEvent("RecurringTransactions.Detail", "Selected", "Transaction")
        @JvmField
        val RECURRINGTRANSACTIONS_BUTTON_PRESSED_PERIOD_SELECTION = AnalyticsEvent("RecurringTransactions", "Button Pressed", "Period Selection")
        @JvmField
        val EXPENSES_SHOW_PAGE_OVER_TIME = AnalyticsEvent("Expenses", "Show page", "Over time")
        @JvmField
        val RECURRINGTRANSACTIONS_DETAIL_EDIT_BUTTON_PRESSED_CANCEL = AnalyticsEvent("RecurringTransactions.Detail.Edit", "Button Pressed", "Cancel")
        @JvmField
        val TRANSFERSKYC_PEP_BUTTON_PRESSED_YES = AnalyticsEvent("TransfersKYC.PEP", "Button Pressed", "Yes")
        @JvmField
        val ONBOARDING_CHOOSEACCESSTYPE_SELECTED_SKIP = AnalyticsEvent("Onboarding.ChooseAccessType", "Selected", "Skip")
        @JvmField
        val LEFTTOSPEND_SHOW_PAGE_SIX_MONTHS = AnalyticsEvent("LeftToSpend", "Show page", "Six months")
        @JvmField
        val TRANSFERSKYC_TAXCOUNTRY_SELECTED_CLOSE = AnalyticsEvent("TransfersKYC.TaxCountry", "Selected", "Close")
        @JvmField
        val ONBOARDING_INSIGHTS_SHOW_PAGE_DAILY_SPEND = AnalyticsEvent("Onboarding.Insights", "Show page", "Daily spend")
        @JvmField
        val OVERVIEW_REFRESHCREDENTIALS_SUPPLEMENTALINFORMATION_BUTTON_PRESSED_SUBMIT = AnalyticsEvent("Overview.RefreshCredentials.SupplementalInformation", "Button pressed", "Submit")
        @JvmField
        val ACCOUNTS_SHOW_PAGE_SAVINGS = AnalyticsEvent("Accounts", "Show page", "Savings")
        @JvmField
        val INCOME_SHOW_PAGE_TWELVE_MONTHS = AnalyticsEvent("Income", "Show page", "Twelve months")
        @JvmField
        val TRANSACTION_EDIT_TOGGLE_SWITCHED_RECURRING_TRANSACTION = AnalyticsEvent("Transaction.Edit", "Toggle Switched", "Recurring Transaction")
        @JvmField
        val TRANSFERSKYC_TAXCOUNTRY_SELECTED_COUNTRY = AnalyticsEvent("TransfersKYC.TaxCountry", "Selected", "Country")
        @JvmField
        val BUDGET_V2_DETAILS_BUTTON_PRESSED_EDIT = AnalyticsEvent("Budget.v2.Details", "Button pressed", "Edit")
        @JvmField
        val ONBOARDING_SLIDESHOW_SHOW_PAGE_CATEGORIZATION = AnalyticsEvent("Onboarding.Slideshow", "Show page", "Categorization")
        @JvmField
        val LEFTTOSPEND_SHOW_PAGE_ONE_MONTH = AnalyticsEvent("LeftToSpend", "Show page", "One month")
        @JvmField
        val PAYMENT_REQUEST_APPROVED = AnalyticsEvent("Payment", "Request", "Approved")
        @JvmField
        val PROFILE_CREDENTIAL_BUTTON_PRESSED_DELETE = AnalyticsEvent("Profile.Credential", "Button pressed", "Delete")
        @JvmField
        val EXPENSES_SHOW_PAGE_ONE_MONTH = AnalyticsEvent("Expenses", "Show page", "One month")
        @JvmField
        val BUDGET_V2_DETAILS_TRANSACTIONS_BUTTON_PRESSED_PREVIOUS_PERIOD = AnalyticsEvent("Budget.v2.Details.Transactions", "Button pressed", "Previous Period")
        @JvmField
        val BUDGET_V2_EDIT_BUTTON_PRESSED_SAVE = AnalyticsEvent("Budget.v2.Edit", "Button pressed", "Save")
        @JvmField
        val TRANSFER_BUTTON_PRESSED_TRANSFER = AnalyticsEvent("Transfer", "Button pressed", "Transfer")
        @JvmField
        val OVERVIEW_SHOW_DIALOG_REFRESH_CREDENTIALS = AnalyticsEvent("Overview", "Show dialog", "Refresh Credentials")
        @JvmField
        val INCOME_SHOW_PAGE_OVER_TIME = AnalyticsEvent("Income", "Show page", "Over time")
        @JvmField
        val INCOME_BUTTON_PRESSED_CATEGORY_PICKER = AnalyticsEvent("Income", "Button pressed", "Category picker")
        @JvmField
        val EVENTS_BUTTON_PRESSED_EVENT = AnalyticsEvent("Events", "Button pressed", "Event")
        @JvmField
        val TRANSFERSKYC_TAX_BUTTON_PRESSED_NO = AnalyticsEvent("TransfersKYC.Tax", "Button Pressed", "No")
        @JvmField
        val PROFILE_CREDENTIAL_PROGRESS_PROGRESS_DONE = AnalyticsEvent("Profile.Credential.Progress", "Progress", "Done")
        @JvmField
        val INCOME_SHOW_PAGE_SIX_MONTHS = AnalyticsEvent("Income", "Show page", "Six months")
        @JvmField
        val RECURRINGTRANSACTIONS_DETAIL_EDIT_DELETE_BUTTON_PRESSED_DELETE = AnalyticsEvent("RecurringTransactions.Detail.Edit.Delete", "Button Pressed", "Delete")
        @JvmField
        val RECURRINGTRANSACTIONS_SELECTED_PERIOD_12_MONTHS = AnalyticsEvent("RecurringTransactions", "Selected", "Period 12 Months")
        @JvmField
        val ONBOARDING_CHOOSEACCESSTYPE_BUTTON_PRESSED_MORE_INFORMATION = AnalyticsEvent("Onboarding.ChooseAccessType", "Button pressed", "More Information")
        @JvmField
        val OVERVIEW_BUTTON_PRESSED_SHOW_ALL_TRANSACTIONS = AnalyticsEvent("Overview", "Button pressed", "Show all transactions")
        @JvmField
        val BUDGET_V2_CREATION_FILTER_SEARCH_ITEM_CLICKED_TAG = AnalyticsEvent("Budget.v2.Creation.Filter.Search", "Item clicked", "Tag")
        @JvmField
        val OVERVIEW_REFRESHCREDENTIALS_UPDATING_PROGRESS_FINISHED = AnalyticsEvent("Overview.RefreshCredentials.Updating", "Progress", "Finished")
        @JvmField
        val ONBOARDING_INSIGHTS_SHOW_PAGE_SAVINGS = AnalyticsEvent("Onboarding.Insights", "Show page", "Savings")
        @JvmField
        val ONBOARDING_INSIGHTS_SHOW_PAGE_CATEGORIES = AnalyticsEvent("Onboarding.Insights", "Show page", "Categories")
        @JvmField
        val RECURRINGTRANSACTIONS_SELECTED_MONTH = AnalyticsEvent("RecurringTransactions", "Selected", "Month")
        @JvmField
        val TRANSACTION_COUNTERPARTS_SUGGESTIONS_BUTTON_PRESSED_REQUEST_REIMBURSEMENT = AnalyticsEvent("Transaction.Counterparts.Suggestions", "Button pressed", "Request reimbursement")
        @JvmField
        val PAYMENT_REQUEST_FAILED = AnalyticsEvent("Payment", "Request", "Failed")
        @JvmField
        val RECURRINGTRANSACTIONS_SELECTED_PERIOD_6_MONTHS = AnalyticsEvent("RecurringTransactions", "Selected", "Period 6 Months")
        @JvmField
        val PROFILE_CREDENTIAL_CHOOSEACCESSTYPE_BUTTON_PRESSED_ADD_OPEN_BANKING = AnalyticsEvent("Profile.Credential.ChooseAccessType", "Button pressed", "Add Open Banking")
        @JvmField
        val PROFILE_CREDENTIAL_PROGRESS_PROGRESS_ERROR = AnalyticsEvent("Profile.Credential.Progress", "Progress", "Error")
        @JvmField
        val OVERVIEW_REFRESHCREDENTIALS_BUTTON_PRESSED_CLOSE = AnalyticsEvent("Overview.RefreshCredentials", "Button pressed", "Close")
        @JvmField
        val RECURRINGTRANSACTIONS_BUTTON_PRESSED_NEXT_PERIOD = AnalyticsEvent("RecurringTransactions", "Button Pressed", "Next Period")
        @JvmField
        val RECURRINGTRANSACTIONS_DETAIL_EDIT_BUTTON_PRESSED_SAVE = AnalyticsEvent("RecurringTransactions.Detail.Edit", "Button Pressed", "Save")
        @JvmField
        val PROFILE_SETTINGS_BUTTON_PRESSED_REMOVE_ACCOUNT = AnalyticsEvent("Profile.Settings", "Button pressed", "Remove Account")
        @JvmField
        val BUDGET_V2_DETAILS_BUTTON_PRESSED_NEXT_PERIOD = AnalyticsEvent("Budget.v2.Details", "Button pressed", "Next Period")
        @JvmField
        val OVERVIEW_SELECTED_RECURRING_TRANSACTIONS = AnalyticsEvent("Overview", "Selected", "Recurring Transactions")
        @JvmField
        val TRANSACTION_COUNTERPARTS_SUGGESTIONS_SHOW_SUGGESTIONS_VIEW = AnalyticsEvent("Transaction.Counterparts.Suggestions", "Show", "Suggestions view")
        @JvmField
        val ONBOARDING_PROVIDERGROUPLIST_SELECTED_PROVIDER_AFTER_SEARCH = AnalyticsEvent("Onboarding.ProviderGroupList", "Selected", "Provider After Search")
        @JvmField
        val OVERVIEW_REFRESHCREDENTIALS_CHOOSEACCESSTYPE_BUTTON_PRESSED_UPDATE_ALL = AnalyticsEvent("Overview.RefreshCredentials.ChooseAccessType", "Button pressed", "Update All")
        @JvmField
        val TRANSFER_REQUEST_APPROVED = AnalyticsEvent("Transfer", "Request", "Approved")
        @JvmField
        val EXPENSES_SHOW_PAGE_SIX_MONTHS = AnalyticsEvent("Expenses", "Show page", "Six months")
        @JvmField
        val TRANSFERSKYC_BUTTON_PRESSED_NEXT = AnalyticsEvent("TransfersKYC", "Button Pressed", "Next")
        @JvmField
        val OVERVIEW_REFRESHCREDENTIALS_CHOOSEACCESSTYPE_BUTTON_PRESSED_CLOSE = AnalyticsEvent("Overview.RefreshCredentials.ChooseAccessType", "Button pressed", "Close")
        @JvmField
        val LEFTTOSPEND_SHOW_PAGE_TRANSACTIONS = AnalyticsEvent("LeftToSpend", "Show page", "Transactions")
        @JvmField
        val PROFILE_CREDENTIAL_CHOOSEACCESSTYPE_SELECTED_OTHER = AnalyticsEvent("Profile.Credential.ChooseAccessType", "Selected", "Other")
        @JvmField
        val RECURRINGTRANSACTIONS_ADD_SELECTTRANSACTION_BUTTON_PRESSED_CANCEL = AnalyticsEvent("RecurringTransactions.Add.SelectTransaction", "Button Pressed", "Cancel")
        @JvmField
        val ONBOARDING_INSIGHTS_SHOW_PAGE_MORTGAGE_RATE = AnalyticsEvent("Onboarding.Insights", "Show page", "Mortgage rate")
        @JvmField
        val TRANSFERSKYC_PEP_BUTTON_PRESSED_CLOSE = AnalyticsEvent("TransfersKYC.PEP", "Button Pressed", "Close")
        @JvmField
        val TRANSACTION_BUTTON_PRESSED_RECURRING_TRANSACTION = AnalyticsEvent("Transaction", "Button pressed", "Recurring Transaction")
        @JvmField
        val TRANSACTION_COUNTERPARTS_SUGGESTIONS_BUTTON_PRESSED_SKIP = AnalyticsEvent("Transaction.Counterparts.Suggestions", "Button pressed", "Skip")
        @JvmField
        val TRANSFERSKYC_ONOWNBEHALF_BUTTON_PRESSED_NO = AnalyticsEvent("TransfersKYC.OnOwnBehalf", "Button Pressed", "No")
        @JvmField
        val ONBOARDING_SLIDESHOW_SHOW_PAGE_BANKS = AnalyticsEvent("Onboarding.Slideshow", "Show page", "Banks")
        @JvmField
        val RECURRINGTRANSACTIONS_SELECTED_RECURRING_TRANSACTION = AnalyticsEvent("RecurringTransactions", "Selected", "Recurring Transaction")
        @JvmField
        val OVERVIEW_REFRESHCREDENTIALS_BUTTON_PRESSED_UPDATE = AnalyticsEvent("Overview.RefreshCredentials", "Button pressed", "Update")
        @JvmField
        val OVERVIEW_REFRESHCREDENTIALS_OPENBANKINGCONSENT_BUTTON_PRESSED_CANCEL = AnalyticsEvent("Overview.RefreshCredentials.OpenBankingConsent", "Button pressed", "Cancel")
        @JvmField
        val TRANSFERSKYC_BUTTON_PRESSED_CLOSE = AnalyticsEvent("TransfersKYC", "Button Pressed", "Close")
        @JvmField
        val ONBOARDING_CHOOSECREDENTIALTYPE_SELECTED_MOBILE_BANKID = AnalyticsEvent("Onboarding.ChooseCredentialType", "Selected", "Mobile BankID")
        @JvmField
        val TRANSFERSKYC_COMPLIANT_BUTTON_PRESSED_OK = AnalyticsEvent("TransfersKYC.Compliant", "Button Pressed", "OK")
        @JvmField
        val RECURRINGTRANSACTIONS_SHOW_PAGE_OVER_TIME = AnalyticsEvent("RecurringTransactions", "Show Page", "Over Time")
        @JvmField
        val TRANSACTION_COUNTERPARTS_SUGGESTIONS_BUTTON_PRESSED_SEE_MORE = AnalyticsEvent("Transaction.Counterparts.Suggestions", "Button pressed", "See more")
        @JvmField
        val RECURRINGTRANSACTIONS_DETAIL_BUTTON_PRESSED_EDIT = AnalyticsEvent("RecurringTransactions.Detail", "Button Pressed", "Edit")
        @JvmField
        val PROFILE_CREDENTIAL_SUPPLEMENTALINFORMATION_BUTTON_PRESSED_CANCEL = AnalyticsEvent("Profile.Credential.SupplementalInformation", "Button pressed", "Cancel")
        @JvmField
        val PROFILE_CHOOSECREDENTIALTYPE_SELECTED_PASSWORD = AnalyticsEvent("Profile.ChooseCredentialType", "Selected", "Password")
        @JvmField
        val PAYMENT_BUTTON_PRESSED_PAY = AnalyticsEvent("Payment", "Button pressed", "Pay")
        @JvmField
        val ACCOUNTS_SHOW_PAGE_EVERYDAY = AnalyticsEvent("Accounts", "Show page", "Everyday")
        @JvmField
        val BUDGET_V2_DETAILS_SHOW_PAGE_HISTORY = AnalyticsEvent("Budget.v2.Details", "Show Page", "History")
        @JvmField
        val TRANSFERSKYC_ONOWNBEHALF_BUTTON_PRESSED_CLOSE = AnalyticsEvent("TransfersKYC.OnOwnBehalf", "Button Pressed", "Close")
        @JvmField
        val RECURRINGTRANSACTIONS_BUTTON_PRESSED_ADD_RECURRING_TRANSACTION = AnalyticsEvent("RecurringTransactions", "Button Pressed", "Add Recurring Transaction")
        @JvmField
        val PROFILE_PROVIDER_CHOOSEACCESSTYPE_SELECTED_OTHER = AnalyticsEvent("Profile.Provider.ChooseAccessType", "Selected", "Other")
        @JvmField
        val RECURRINGTRANSACTIONS_ADD_SIMILARTRANSACTIONS_BUTTON_PRESSED_MARK_SELECTED = AnalyticsEvent("RecurringTransactions.Add.SimilarTransactions", "Button Pressed", "Mark Selected")
        @JvmField
        val ONBOARDING_CREDENTIALSSUPPLEMENTALINFORMATION_BUTTON_PRESSED_CANCEL = AnalyticsEvent("Onboarding.CredentialsSupplementalInformation", "Button pressed", "Cancel")
        @JvmField
        val OVERVIEW_REFRESHCREDENTIALS_UPDATING_PROGRESS_ERROR = AnalyticsEvent("Overview.RefreshCredentials.Updating", "Progress", "Error")
        @JvmField
        val ONBOARDING_CREDENTIALSSUPPLEMENTALINFORMATION_BUTTON_PRESSED_SUBMIT = AnalyticsEvent("Onboarding.CredentialsSupplementalInformation", "Button pressed", "Submit")
        @JvmField
        val ONBOARDING_CHOOSEACCESSTYPE_SELECTED_OTHER = AnalyticsEvent("Onboarding.ChooseAccessType", "Selected", "Other")
        @JvmField
        val EXPENSES_BUTTON_PRESSED_CATEGORY_PICKER = AnalyticsEvent("Expenses", "Button pressed", "Category picker")
        @JvmField
        val OVERVIEW_REFRESHCREDENTIALS_CHOOSEACCESSTYPE_BUTTON_PRESSED_UPDATE_OPEN_BANKING = AnalyticsEvent("Overview.RefreshCredentials.ChooseAccessType", "Button pressed", "Update Open Banking")
        @JvmField
        val BUDGET_V2_DETAILS_BUTTON_PRESSED_SHOW_TRANSACTIONS = AnalyticsEvent("Budget.v2.Details", "Button pressed", "Show Transactions")
        @JvmField
        val BUDGET_V2_DELETE_DIALOG_ACTION_CANCEL = AnalyticsEvent("Budget.v2.Delete", "Dialog action", "Cancel")
        @JvmField
        val TRACKING_ERROR_SHOW_PAGE_ERROR = AnalyticsEvent("Tracking error", "Show page", "Error")
        @JvmField
        val PROFILE_CREDENTIAL_CHOOSEACCESSTYPE_BUTTON_PRESSED_ADD_OTHER = AnalyticsEvent("Profile.Credential.ChooseAccessType", "Button pressed", "Add Other")
        @JvmField
        val ACCOUNTS_SHOW_PAGE_LOANS = AnalyticsEvent("Accounts", "Show page", "Loans")
        @JvmField
        val BUDGET_V2_DELETE_DIALOG_ACTION_CONFIRM = AnalyticsEvent("Budget.v2.Delete", "Dialog action", "Confirm")
        @JvmField
        val OVERVIEW_SELECTED_FAVORITE_ACCOUNT = AnalyticsEvent("Overview", "Selected", "Favorite account")
        @JvmField
        val OVERVIEW_SELECTED_SAVINGS = AnalyticsEvent("Overview", "Selected", "Savings")
        @JvmField
        val EXPENSES_SHOW_PAGE_TRANSACTIONS = AnalyticsEvent("Expenses", "Show page", "Transactions")
        @JvmField
        val BUDGET_V2_CREATION_FILTER_BUTTON_PRESSED_CREATE = AnalyticsEvent("Budget.v2.Creation.Filter", "Button pressed", "Create")
        @JvmField
        val TRANSFER_REQUEST_FAILED = AnalyticsEvent("Transfer", "Request", "Failed")
        @JvmField
        val RECURRINGTRANSACTIONS_SELECTED_PERIOD_ALL_TIME = AnalyticsEvent("RecurringTransactions", "Selected", "Period All Time")
        @JvmField
        val TRANSACTION_COUNTERPARTS_SUGGESTIONS_SELECTED_SUGGESTION_ROW = AnalyticsEvent("Transaction.Counterparts.Suggestions", "Selected", "Suggestion row")
        @JvmField
        val RECURRINGTRANSACTIONS_SHOW_PAGE_CURRENT_MONTH = AnalyticsEvent("RecurringTransactions", "Show Page", "Current Month")
        @JvmField
        val TRANSACTION_REQUESTREIMBURSEMENT_BUTTON_PRESSED_SEND = AnalyticsEvent("Transaction.RequestReimbursement", "Button pressed", "Send")
        @JvmField
        val PROFILE_CREDENTIAL_SUPPLEMENTALINFORMATION_BUTTON_PRESSED_SUBMIT = AnalyticsEvent("Profile.Credential.SupplementalInformation", "Button pressed", "Submit")
        @JvmField
        val EXPENSES_BUTTON_PRESSED_TIME_PERIOD = AnalyticsEvent("Expenses", "Button pressed", "Time period")
        @JvmField
        val PROFILE_PROVIDER_ADD_BUTTON_PRESSED_ADD = AnalyticsEvent("Profile.Provider.Add", "Button pressed", "Add")
        @JvmField
        val ONBOARDING_INSIGHTS_SHOW_PAGE_LEFT_TO_SPEND = AnalyticsEvent("Onboarding.Insights", "Show page", "Left to spend")
        @JvmField
        val OVERVIEW_REFRESHCREDENTIALS_CHOOSEACCESSTYPE_BUTTON_PRESSED_SKIP = AnalyticsEvent("Overview.RefreshCredentials.ChooseAccessType", "Button pressed", "Skip")
        @JvmField
        val PROFILE_CREDENTIAL_DELETE_DIALOG_ACTION_CANCEL = AnalyticsEvent("Profile.Credential.Delete", "Dialog Action", "Cancel")
        @JvmField
        val TRANSFERSKYC_PEP_BUTTON_PRESSED_NO = AnalyticsEvent("TransfersKYC.PEP", "Button Pressed", "No")
        @JvmField
        val RECURRINGTRANSACTIONS_ADD_SELECTTRANSACTION_BUTTON_PRESSED_SEARCH = AnalyticsEvent("RecurringTransactions.Add.SelectTransaction", "Button Pressed", "Search")
        @JvmField
        val ONBOARDING_SLIDESHOW_SHOW_PAGE_INTEREST_RATE = AnalyticsEvent("Onboarding.Slideshow", "Show page", "Interest rate")
        @JvmField
        val BUDGET_V2_DETAILS_TRANSACTIONS_BUTTON_PRESSED_NEXT_PERIOD = AnalyticsEvent("Budget.v2.Details.Transactions", "Button pressed", "Next Period")
        @JvmField
        val ONBOARDING_SLIDESHOW_SHOW_PAGE_BETA_WELCOME = AnalyticsEvent("Onboarding.Slideshow", "Show page", "Beta Welcome")
        @JvmField
        val BUDGET_V2_CREATION_FIELDS_BUTTON_PRESSED_CREATE = AnalyticsEvent("Budget.v2.Creation.Fields", "Button pressed", "Create")
        @JvmField
        val ONBOARDING_CREDENTIALSPROGRESS_PROGRESS_DONE = AnalyticsEvent("Onboarding.CredentialsProgress", "Progress", "Done")
        @JvmField
        val RECURRINGTRANSACTIONS_DETAIL_EDIT_DELETE_BUTTON_PRESSED_CANCEL = AnalyticsEvent("RecurringTransactions.Detail.Edit.Delete", "Button Pressed", "Cancel")
        @JvmField
        val TRANSFERSKYC_TAX_BUTTON_PRESSED_CLOSE = AnalyticsEvent("TransfersKYC.Tax", "Button Pressed", "Close")
        @JvmField
        val PROFILE_CREDENTIAL_EDIT_BUTTON_PRESSED_UPDATE = AnalyticsEvent("Profile.Credential.Edit", "Button pressed", "Update")
        @JvmField
        val ONBOARDING_CHOOSEACCESSTYPE_SELECTED_OPEN_BANKING = AnalyticsEvent("Onboarding.ChooseAccessType", "Selected", "Open Banking")
        @JvmField
        val ONBOARDING_CHOOSECREDENTIALTYPE_SELECTED_PASSWORD = AnalyticsEvent("Onboarding.ChooseCredentialType", "Selected", "Password")
        @JvmField
        val PROFILE_CREDENTIAL_MIGRATION_BUTTON_PRESSED_UPDATE = AnalyticsEvent("Profile.Credential.Migration", "Button pressed", "Update")
        @JvmField
        val TRANSFERSKYC_ONOWNBEHALF_BUTTON_PRESSED_YES = AnalyticsEvent("TransfersKYC.OnOwnBehalf", "Button Pressed", "Yes")
        @JvmField
        val INCOME_SHOW_PAGE_TRANSACTIONS = AnalyticsEvent("Income", "Show page", "Transactions")
        @JvmField
        val OVERVIEW_SELECTED_ADD_FAVORITE_ACCOUNT = AnalyticsEvent("Overview", "Selected", "Add Favorite account")
        @JvmField
        val PROFILE_CHOOSECREDENTIALTYPE_SELECTED_MOBILE_BANKID = AnalyticsEvent("Profile.ChooseCredentialType", "Selected", "Mobile BankID")
        @JvmField
        val PROFILE_CHOOSECREDENTIALTYPE_SELECTED_THIRDPARTY_AUTHENTICATION = AnalyticsEvent("Profile.ChooseCredentialType", "Selected", "Thirdparty Authentication")
        @JvmField
        val BUDGET_V2_CREATION_FILTER_ITEM_CLICKED_SEARCH = AnalyticsEvent("Budget.v2.Creation.Filter", "Item clicked", "Search")
        @JvmField
        val PROFILE_CREDENTIAL_CHOOSEACCESSTYPE_SELECTED_OPEN_BANKING = AnalyticsEvent("Profile.Credential.ChooseAccessType", "Selected", "Open Banking")
        @JvmField
        val ONBOARDING_ADDCREDENTIALS_BUTTON_PRESSED_NEXT = AnalyticsEvent("Onboarding.AddCredentials", "Button pressed", "Next")
        @JvmField
        val RECURRINGTRANSACTIONS_BUTTON_PRESSED_PREVIOUS_PERIOD = AnalyticsEvent("RecurringTransactions", "Button Pressed", "Previous Period")
        @JvmField
        val OVERVIEW_REFRESHCREDENTIALS_UPDATING_PROGRESS_AUTHENTICATING = AnalyticsEvent("Overview.RefreshCredentials.Updating", "Progress", "Authenticating")
        @JvmField
        val ONBOARDING_CREDENTIALSPROGRESS_PROGRESS_ERROR = AnalyticsEvent("Onboarding.CredentialsProgress", "Progress", "Error")
        @JvmField
        val FEED_SELECTED_ACTIVITY = AnalyticsEvent("Feed", "Selected", "Activity")
        @JvmField
        val LEFTTOSPEND_SHOW_PAGE_OVER_TIME = AnalyticsEvent("LeftToSpend", "Show page", "Over time")
        @JvmField
        val PROFILE_CREDENTIAL_MIGRATION_BUTTON_PRESSED_CLOSE = AnalyticsEvent("Profile.Credential.Migration", "Button pressed", "Close")
        @JvmField
        val PAYMENT_BUTTON_PRESSED_SCAN_QR = AnalyticsEvent("Payment", "Button pressed", "Scan QR")
        @JvmField
        val BUDGET_V2_DETAILS_SHOW_PAGE_PROGRESS = AnalyticsEvent("Budget.v2.Details", "Show Page", "Progress")

    }
}
