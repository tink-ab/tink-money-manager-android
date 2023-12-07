# String Customization

Money Manager SDK allows you to customize the text shown in the finance overview UI by overriding the string resources that are publicly available in the SDK.

Override the string resource values in your application's `res/values/strings.xml`
```xml
<resources>
    <string name="tink_all_categories">Custom All Categories Text</string>
</resources>
```
You can also have translations for different locales by overriding the string resource values in the respective `strings.xml` resource files.
For example, you can add a Swedish translation of the text in your application's `res/values-sv/strings.xml`
```xml
<resources>
    <string name="tink_all_categories">Alla kategorier</string>
</resources>
```
Here's a list of all the public string resources in the SDK that can be overridden:

| String Resource ID | English Text |
|------|-----|
| `tink_accounts_edit_field_favorite_description` | Balance is shown in the overview |
| `tink_accounts_edit_field_favorite_header` | Favorite account |
| `tink_accounts_edit_field_included_description` | This account is shown in your statistics |
| `tink_accounts_edit_field_included_header` | Included |
| `tink_accounts_edit_field_name_description` | Original description: Sturehof |
| `tink_accounts_edit_field_name_header` | Account name |
| `tink_accounts_edit_field_shared_description` | 50% is included in your statistics |
| `tink_accounts_edit_field_shared_header` | Shared account |
| `tink_accounts_edit_field_type_dialog_title` | Choose account type | 
| `tink_accounts_edit_field_type_header` | Account type | 
| `tink_accounts_edit_field_type` | Account type |
| `tink_accounts_edit_save_button` | Save |
| `tink_accounts_edit_title` | Accont details |
| `tink_accounts_edit_unsaved_changes_title` | You have unsaved changes |
| `tink_accounts_edit_unsaved_changes_message` | Want to discard the changes you haven’t saved? |
| `tink_accounts_edit_unsaved_changes_no_save` | No, save |
| `tink_accounts_edit_unsaved_changes_yes_discard` | Yes, discard |
| `tink_accounts_edit` | Edit accont |
| `tink_accounts_error` | No accounts available |
| `tink_accounts_type_checking` | Checking | 
| `tink_accounts_type_credit_card` | Credit card | 
| `tink_accounts_type_external` | External | 
| `tink_accounts_type_investment` | Investment | 
| `tink_accounts_type_loan` | Loan | 
| `tink_accounts_type_mortgage` | Mortgage | 
| `tink_accounts_type_other` | Other | 
| `tink_accounts_type_pension` | Pension | 
| `tink_accounts_type_savings` | Savings |
| `tink_all_categories` | All categories |
| `tink_budget_category_selection_button` | Next |
| `tink_budget_create_all_expenses` | All expenses |
| `tink_budget_create_button` | Create budget |
| `tink_budget_create_field_amount_hint` | Amount |
| `tink_budget_create_field_name_hint` | Name |
| `tink_budget_create_field_period_custom` | Custom |
| `tink_budget_create_field_period_end_hint` | Period End Date |
| `tink_budget_create_field_period_hint` | Budget Period |
| `tink_budget_create_field_period_monthly` | Monthly |
| `tink_budget_create_field_period_start_hint` | Period Start Date |
| `tink_budget_create_field_period_weekly` | Weekly |
| `tink_budget_create_field_period_yearly` | Yearly |
| `tink_budget_create_period_end_invalid_message` | End date must be after start date. |
| `tink_budget_create_title` | Create budget |
| `tink_budget_creation_success_message` | Your budget is now created! |
| `tink_overview_suggested_budgets_title` | Suggested budget |
| `tink_budget_create_with_keyword` | Create with keyword |
| `tink_budget_delete_dialog_confirm_button` | Delete |
| `tink_budget_delete_dialog_message` | Are you sure you want to remove the budget \"%s\"? |
| `tink_budget_delete_error_message` | We are not able to remove your budget right now. |
| `tink_budget_details_amount_left_daily_message` | You can spend %1$s per day and still remain within your budget |
| `tink_budget_details_amount_left_message_plain` | You can spend %1$s and still remain within your budget |
| `tink_budget_details_amount_left_monthly_message` | You can spend %1$s per month and still remain within your budget |
| `tink_budget_details_amount_left_none_message` | You have no remaining budget for this period |
| `tink_budget_details_amount_left_weekly_message` | You can spend %1$s per week and still remain within your budget |
| `tink_budget_details_amount_left_yearly_message` | You can spend %1$s per year and still remain within your budget |
| `tink_budget_details_chart_empty_message` | Here you will see your historical spending on %s. But now you don\'t have any! |
| `tink_budget_details_chart_over_budget_status_message_current_month` | You have gone over your budget this month |
| `tink_budget_details_chart_over_budget_status_message_current_week` | You have gone over your budget this week |
| `tink_budget_details_chart_over_budget_status_message_current_year` | You have gone over your budget this year |
| `tink_budget_details_chart_status_message_current_month` | You have %s left on your budget this month |
| `tink_budget_details_chart_status_message_current_week` | You have %s left on your budget this week |
| `tink_budget_details_chart_status_message_current_year` | You have %s left on your budget this year |
| `tink_budget_details_ended_months_days_ago` | Closed %s ago |
| `tink_budget_details_managed_budget_message` | You managed your budget this period |
| `tink_budget_details_menu_edit_budget_item` | Edit Budget |
| `tink_budget_details_not_started` | Budget hasn\'t started yet |
| `tink_budget_details_over_budget_message` | You went over your budget this period |
| `tink_budget_details_remaining` | %s remaining |
| `tink_budget_details_selector_monthly` | Monthly |
| `tink_budget_details_selector_over_time` | Over time |
| `tink_budget_details_selector_weekly` | Weekly |
| `tink_budget_details_selector_yearly` | Yearly |
| `tink_budget_details_show_transaction_btn_title` | See Transactions |
| `tink_budget_details_starts_in_exact_1_day` | Starts tomorrow |
| `tink_budget_details_starts_in` | Starts in %s |
| `tink_budget_details_starts_in_month_and_day` | Starts in %s and %s |
| `tink_budget_details_remaining_exact_0_day` | Ends today |
| `tink_budget_details_remaining_exact_1_day` | Ends tomorrow |
| `tink_budget_details_remaining` | %s remaining |
| `tink_budget_details_remaining_months_and_days` | %s and %s remaining |
| `tink_budget_details_ended_1_day_ago` | Closed yesterday |
| `tink_budget_details_ended_months_days_ago` | Closed %s ago |
| `tink_budget_details_ended_months_and_days_ago` | Closed %s and %s ago |
| `tink_budget_details_toolbar_title` | Budget |
| `tink_budget_details_total_amount` | left of %s |
| `tink_budget_edit_button` | Save changes |
| `tink_budget_edit_field_amount_average_for_period` | Average %1$s/%2$s |
| `tink_budget_edit_field_category_hint` | Category |
| `tink_budget_edit_menu_delete_budget_item` | Delete Budget |
| `tink_budget_edit_toolbar_title` | Edit budget |
| `tink_budget_period_chart_week_label` | w%d |
| `tink_budget_period_day` | day |
| `tink_budget_period_month` | month |
| `tink_budget_period_week` | week |
| `tink_budget_period_year` | year |
| `tink_budget_specification_toolbar_title` | Budget \"%s\" |
| `tink_budget_transactions_empty_message` | There are no transactions to show for this period |
| `tink_budget_transactions_header_amount_left_of` | %1$s left of %2$s |
| `tink_budget_transactions_header_amount_over` | %1$s over budget of %2$s |
| `tink_budget_transactions_toolbar_title` | Budget |
| `tink_category_default_child_format` | %s - Other |
| `tink_category_list_fragment_button_text` | Select |
| `tink_changes_saved` | Changes saved |
| `tink_choose_category_text` | Choose category |
| `tink_clear_text_description` | Clear text |
| `tink_date_format_human_today` | Today |
| `tink_date_format_human_tomorrow` | Tomorrow |
| `tink_date_format_human_yesterday` | Yesterday |
| `tink_empty_budget_message_in_recommended_budget_list` | You have no budgets. Create a budget based on your top spending categories or create a new budget. |
| `tink_everyday_account` | Everyday accounts |
| `tink_expenses_header_description_average` | %s on average per month |
| `tink_expenses_menu_settings_title` | Transactions |
| `tink_expenses_text` | %s expenses |
| `tink_expenses_title` | Expenses |
| `tink_income_header_description_average` | %s on average per month |
| `tink_income_title` | Income |
| `tink_insights_archive_title` | Archive |
| `tink_insights_archived_empty_state_text` | No archived events |
| `tink_insights_budget_create_suggestion_save_per_year_text` | Save up to %s per year |
| `tink_insights_empty_state_text` | No new events! Check the archive if you want to see your previous ones |
| `tink_insights_expenses_by_day_average_text` | Average |
| `tink_insights_largest_transaction_recipient_text` | To %s (%s) |
| `tink_insights_largest_transaction_text` | %s was your largest expense |
| `tink_insights_monthly_summary_expense_transactions_title` | Here’s a summary of your expense transactions last month |
| `tink_insights_most_common_transaction_text` | Most common was %s (%d) |
| `tink_insights_overview_card_action_text` | View |
| `tink_insights_overview_card_archived_events_title` | Archived events |
| `tink_insights_overview_card_new_events_title` | Events |
| `tink_insights_tab_title` | Events |
| `tink_insights_total_expenses_text` | %s in total expenses |
| `tink_insights_total_monthly_transactions_text` | %d transactions last month |
| `tink_insights_total_weekly_transactions_text` | %d transactions last week |
| `tink_insights_weekly_summary_expense_transactions_title` | Here’s a summary of your expense transactions last week |
| `tink_latest_transactions_toolbar_title` | All transactions |
| `tink_loans_account` | Loan accounts |
| `tink_nav_app_bar_navigate_up_description` | Navigate up |
| `tink_next_month_description` | Next month |
| `tink_other_account` | Other accounts |
| `tink_other` | Other |
| `tink_overview_accounts_error` | No accounts |
| `tink_overview_budget_left_of` | %1$s left of %2$s |
| `tink_overview_budget_over` | %1$s over budget of %2$s |
| `tink_overview_budgets_create_new` | New Budget |
| `tink_overview_budgets_empty_create_new` | Create New Budget |
| `tink_overview_budgets_title` | Budgets |
| `tink_overview_dynamic_budgets_title` | Top spending categories |
| `tink_overview_favorite_accounts_see_all_accounts` | See all accounts |
| `tink_overview_favorite_accounts_see_all` | See all |
| `tink_overview_highlighted_accounts_title` | Favorite accounts |
| `tink_overview_latest_transactions_title` | Latest transactions |
| `tink_overview_latest_transactions_view_all_link` | See all |
| `tink_overview_statistics_error` | No statistics |
| `tink_overview_suggested_budgets_title` | Suggested budget |
| `tink_overview_toolbar_title` | Overview |
| `tink_overview_top_spending_categories_title` | Top spending categories |
| `tink_period_selection_title` | Choose time period |
| `tink_previous_month_description` | Previous month |
| `tink_savings_account` | Savings accounts |
| `tink_search_label` | Search |
| `tink_selector_12_months` | Last 12 months |
| `tink_selector_6_months` | Last 6 months |
| `tink_selector_all_time` | All time |
| `tink_selector_monthly` | Monthly |
| `tink_selector_over_time` | Over time |
| `tink_snackbar_utils_error_default` | An error has occurred. Please try again later. |
| `tink_snackbar_utils_error_status_code_408` | Could not connect to Tink servers. Please try again later. |
| `tink_suggested_budget_description` | You have spent %1$s on average. Track your expense by creating a budget. |
| `tink_transactions_error` | You haven\'t made any transactions yet |
| `tink_transaction_similar_button_accept_text` | Assign %1$d selected |
| `tink_transaction_similar_button_skip_text` | Skip |
| `tink_transaction_similar_description` | Do you also want to assign these transactions to the %1$s category? |
| `tink_transaction_similar_marker_button_mark_text` | Select all |
| `tink_transaction_similar_marker_button_unmark_text` | Unselect all |
| `tink_transaction_similar_title` | Similar transactions |
| `tink_transactions_list_toolbar_title` | Transactions |
| `tink_until_next_date` | Until %s |
| `tink_transaction_pending_uneditable_dialog_title` | Not editable |
| `tink_transaction_pending_uneditable_dialog_body` | Pending transactions are not editable until they are confirmed by the bank. |
| `tink_general_ok` |OK |
| `tink_general_edit` | Edit |
| `tink_general_done` | Done |

| `tink_transaction_detail_error` | No transaction details available |
| `tink_transaction_detail_change` | CHANGE |
| `tink_transaction_detail_description` | Description |
| `tink_transaction_detail_original_description` | Original description: |
| `tink_transaction_detail_date` | Date |

| `tink_transaction_edit_error_title` | Error saving changes |
| `tink_transaction_edit_error_msg` | There was an unexpected error when saving the changes. |
| `tink_transaction_edit_unsaved_title` | You have unsaved changes |
| `tink_transaction_edit_unsaved_msg` | Want to discard the changes you haven’t saved? |

Alert Dialog button text & common title/message

| `tink_alert_no_save` | No, Save |
| `tink_alert_yes_discard` | Yes, Discard |
| `tink_alert_cancel` | Cancel |
| `tink_alert_try_again` | Try Again |

In addition to the above, here's a list of plurals used in the SDK that can be overridden as well:
```xml
<resources>
    <plurals name="tink_budget_days_plural">
        <item quantity="one">%s day</item>
        <item quantity="other">%s days</item>
    </plurals>
    <plurals name="tink_budget_months_plural">
        <item quantity="one">%s month</item>
        <item quantity="other">%s months</item>
    </plurals>
    <plurals name="tink_budget_details_chart_status_message_last_year">
        <item quantity="one">You made your budget %d%% of the time since you created the budget</item>
        <item quantity="other">You made your budget %d%% of the time since you created the budget</item>
    </plurals>
</resources>
```


