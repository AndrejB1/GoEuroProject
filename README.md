# GoEuroProject
JSON to .csv converter and writer. Made for GoEuro.com

Upon starting, this application takes a city name as a parameter, and uses it to
retreive details about the city from the GoEuro.com API. The information is returned
from the 'JParser' class as a JSONArray. Once back in the main class, the JSONArray
is edited and written to a .csv file.

This is a command line dependent app, requiring the input of a city name from the user.
If you should choose to run it without providing a city name to search for, the program
will retort that it cannot do any work without a city variable, and you will be notified
of your mistake, and prompted to try again. If there is no city of the name you provided
present in the database, you will be notified. This is most likely due to a spelling error.
