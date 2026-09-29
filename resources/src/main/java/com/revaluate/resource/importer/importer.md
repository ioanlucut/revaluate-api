# Expense import

[Engineering notes and test links](../../../../../../../../docs/engineering.md#1-importing-expenses-without-imposing-another-apps-categories) · [Resource implementation](ImporterResource.java)

The CSV parser uses source-specific profiles for column names, delimiters and date formats. For the Mint and Spendee preview flow, the API returns parsed expenses and the distinct source categories. The client maps those categories to Revaluate categories or deselects them, then sends the completed import data back.

The application checks that every source category has a mapping entry, filters deselected categories, replaces selected categories and persists the resulting expenses. See the engineering notes for the boundaries and limitations of this client-assisted import design.
