# String Customization

Tink PFM UI allows you to customize the text shown in the finance overview UI by overriding the string resources that are publicly available in the Tink PFM UI.

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
Here's a list of all the public string resources in the Tink PFM UI, that can be overridden.

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
| `tink_selector_1_months` | 1 month |
| `tink_selector_12_months` | 12 months |
| `tink_selector_6_months` | 6 months |
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
| `tink_date_format_human_today` | Today |
| `tink_date_format_human_yesterday` | Yesterday |
| `tink_date_format_human_tomorrow` | Tomorrow |
| `tink_insights_tab_title` | Events |
| `tink_insights_archive_title` | Archive |
| `tink_insights_empty_state_title` | You handled all events! |
| `tink_insights_empty_state_subtitle` | Check the archive if you want to see your previous ones |
| `tink_insights_overview_card_new_events_title` | New Events |
| `tink_insights_overview_card_archived_events_title` | Archived Events |
| `tink_insights_overview_card_action_text` | View |
| `tink_insights_expenses_by_day_average_text` | Average |