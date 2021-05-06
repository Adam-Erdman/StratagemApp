# StratagemApp

This repository holds the development of a Warhammer 40,000 stratagem companion tool I am developing. The tool filters down the massive stratagem card base into a more
usable form based on what army you are playing, what phase of the turn is active, and unit keywords selected to give you a more digestible short list of stratagems available to you.
Never again miss out on using that obscure stratagem at just the right moment.

## Architecture 
Army stratagems are stored in XML files with each object containing the following characteristics: Phase, Description, Command Point Cost, unit Keywords.

When the user selects and army an XML parser reads the XML document tied to that army and creates a SQLite database with the information parsed from the XML file.

Depending on which phase the user selects and the unit keyword, a query is made which populates a list view with all the usable stratagems.

On Click the user enters a detail view with the description, cost and an ability to spend the command points.

## Tech Stack
Android Studio

Java

## Initial design
https://docs.google.com/drawings/d/1IJMV7644aq9qUySTAQ9UZNhN9M_CtnErCLAvVpJZVW4/edit?usp=sharing
