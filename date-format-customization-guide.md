# Date Format Customization

Tink PFM UI allows you to customize the format of dates shown in the finance overview UI by overriding the date formatter string resources that are publicly available in the Tink PFM UI.

Override the date formatter string resource values in your application's `res/values/strings.xml`
```xml
<resources>
  <string name="tink_date_formatter_month_and_day_of_week">EEEE, d MMM</string>
</resources>
```
Here's a list of all the date formatter string resources in the Tink PFM UI that can be overridden:

| Date Formatter String Resource ID | Current format value |
|------|-----|
| `tink_date_format_human_today` | Today |
| `tink_date_format_human_yesterday` | Yesterday |
| `tink_date_format_human_tomorrow` | Tomorrow |
| `tink_date_formatter_monthly_compact` | `MMM` |
| `tink_date_formatter_month_name` | `MMMMM` |
| `tink_date_formatter_daily_monthly` | `dd MMM` |
| `tink_date_formatter_daily_monthly_yearly` | `dd MMM yyyy` |
| `tink_date_formatter_day_of_week_compact` | `EEE` |
| `tink_date_formatter_month_and_day_of_week` | `EEEE MMM d` |
| `tink_date_formatter_month_and_day` | `MMMM d` |
| `tink_date_formatter_month_and_day_and_year` | `MMMM d yyyy` |
| `tink_date_formatter_month_and_year` | `MMMM yyyy` |
| `tink_date_formatter_month_and_year_compact` | `MMM yyyy` |
