## Importer
# The importer is responsibile with importing CSV expenses exports into revaluate. 
* So far, we use two predefined imports, as the imports can be really hard to be generified. 
* One example represents the date format which can be really hard to be guessed.

## ImportService
* The main logic, separated from application package, ImporterParserService, is used to parse a Reader into a list of expenses.
* An ExpenseProfileDTO is an object which has all the details of an import type.
* Our current flow of working with backend-frontend is the following
** Frontend uploads a csv file (mint or spendee), we parse it with mint expense profile or spendee expense profile and we sent back the expenses parsed (with categories incomplete)
** After parseAndAnalyse is complete, we return back the ExpensesImportDTO which contains the list of expenses along with categories which are found and are needed to be matched.
** The matching is made in frontend, and client side sends back the whole ExpensesImportDTO object which has to contain for every expected/unknown category a match, or to be unselected.
** ==> Therefore the import is performed.