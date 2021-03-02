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
| `tink_all_categories` | All categories |
| `tink_category_list_fragment_button_text` | Select |
| `tink_choose_category_text` | Choose category |
| `tink_expenses_header_description_average` | %s on average per month |
| `tink_expenses_menu_settings_title` | Transactions |
| `tink_expenses_title` | Expenses |
| `tink_income_header_description_average` | %s on average per month |
| `tink_income_title` | Income |
| `tink_other` | Other |
| `tink_category_default_child_format` | %s - Other |
| `tink_overview_latest_transactions_title` | Latest transactions |
| `tink_overview_latest_transactions_view_all_link` | See all |
| `tink_selector_12_months` | Last 12 months |
| `tink_selector_6_months` | Last 6 months |
| `tink_snackbar_utils_error_default` | An error has occurred. Please try again later. |
| `tink_snackbar_utils_error_status_code_408` | Could not connect to Tink servers. Please try again later. |
| `tink_transaction_similar_button_accept_text` | Yes, change selected |
| `tink_transaction_similar_button_skip_text` | Skip |
| `tink_transaction_similar_description` | Do you want to categorize these transactions as well? |
| `tink_transaction_similar_marker_button_mark_text` | Select all |
| `tink_transaction_similar_marker_button_unmark_text` | Unselect all |
| `tink_transaction_similar_title` | Similar transactions |
| `tink_transactions_list_toolbar_title` | Transactions |
| `tink_until_next_date` | Until %s |
| `tink_insights_tab_title` | Events |
| `tink_insights_archive_title` | Archive |
| `tink_insights_empty_state_title` | You handled all events! |
| `tink_insights_empty_state_subtitle` | Check the archive if you want to see your previous ones |
| `tink_insights_overview_card_new_events_title` | New Events |
| `tink_insights_overview_card_archived_events_title` | Archived Events |
| `tink_insights_overview_card_action_text` | View |
| `tink_insights_expenses_by_day_average_text` | Average |
| `tink_insights_budget_create_suggestion_save_per_year_text` | Save up to %s per year |
| `tink_budget_create_title` | Create budget |
| `tink_budget_create_button` | Create budget |
| `tink_budget_create_button_category` | Create budget for %s |
| `tink_budget_specification_toolbar_title` | Budget \"%s\" |
| `tink_budget_create_field_amount_hint` | Monthly budget |
| `tink_budget_create_field_amount_description` | Average %s/month |
| `tink_budget_create_field_period_hint` | Budget Period |
| `tink_budget_create_field_period_start_hint` | Period Start Date |
| `tink_budget_create_field_period_end_hint` | Period End Date |
| `tink_budget_create_field_period_weekly` | Weekly |
| `tink_budget_create_field_period_monthly` | Monthly |
| `tink_budget_create_field_period_yearly` | Yearly |
| `tink_budget_create_field_period_custom` | Custom |
| `tink_budget_create_period_end_invalid_message` | End date must be after start date. |
| `tink_budget_edit_menu_delete_budget_item` | Delete Budget |
| `tink_budget_edit_title` | Edit budget |
| `tink_budget_edit_button` | Save changes |
| `tink_budget_edit_field_amount_average_for_period` | Average %1$s/%2$s |
| `tink_budget_delete_error_message` | We are not able to remove your budget right now. |
| `tink_budget_delete_dialog_message` | Are you sure you want to remove the budget \"%s\"? |
| `tink_budget_delete_dialog_confirm_button` | Delete |
| `tink_budget_details_show_transaction_btn_title` | See Transactions |
| `tink_budget_details_toolbar_title` | Budget |
| `tink_budget_details_menu_edit_budget_item` | Edit Budget |
| `tink_budget_details_selector_monthly` | Monthly |
| `tink_budget_details_selector_over_time` | Over time |
| `tink_budget_details_amount_spent` | %s spent |
| `tink_budget_details_amount_left_message` | You can spend %1$s per %2$s and manage your budget |
| `tink_budget_details_amount_left_message_plain` | You can spend %1$s and manage your budget |
| `tink_budget_details_over_budget_message` | You have gone over your budget this period |
| `tink_budget_details_managed_budget_message` | You managed your budget for this period |
| `tink_budget_details_not_started` | Budget hasn\'t started yet |
| `tink_budget_details_total_amount` | left of %s |
| `tink_budget_period_month` | month |
| `tink_budget_period_week` | week |
| `tink_budget_period_day` | day |
| `tink_budget_period_year` | year |
| `tink_budget_details_spent_average` | %1$s/%2$s spent on average |
| `tink_budget_period_chart_week_label` | w%d |
| `tink_budget_details_chart_empty_message` | Here you will see your historical spending on %s. But now you don\'t have any! |
| `tink_budget_details_chart_status_message_last_year` | You made your budget %s of the time over the last year |
| `tink_budget_details_chart_status_message_since` | You made your budget %1$s of the time since %2$s |
| `tink_budget_transactions_toolbar_title` | Budget |
| `tink_budget_transactions_header_amount_left_of` | %1$s left of %2$s |
| `tink_budget_transactions_header_amount_over` | %1$s over budget of %2$s |
| `tink_budget_transactions_empty_message` | There are no transactions to show for this period |
| `tink_overview_budgets_title` | Budgets |
| `tink_overview_budgets_create_new` | New Budget |
| `tink_overview_budgets_empty_create_new` | Create New Budget |
| `tink_overview_budget_over` | %1$s over budget of %2$s |
| `tink_overview_budget_left_of` | %1$s left of %2$s |
| `tink_budget_transactions_toolbar_title` | Budget |

In addition to the above, here's a list of plurals used in the Tink Money Manager UI that can be overridden as well:
```xml
<resources>
  <plurals name="tink_budget_details_days_left">
    <item quantity="one">%s day left</item>
    <item quantity="other">%s days left</item>
  </plurals>
  <plurals name="tink_budget_details_ended_days_ago">
      <item quantity="one">Ended %s day ago</item>
      <item quantity="other">Ended %s days ago</item>
    </plurals>
</resources>
```


