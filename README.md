# CPSC 210 Project

## UBC Rate My Libraries

This project is intended for UBC students to share insights about libraries at which they
feel most comfortable to study. The purpose of building this project is to offer freshmen and
new ubc students ideas of different learning styles they could embrace in different areas. 
From what I experienced and what I heard from the first year student, people often struggle to
find a best place that suits their needs for commuting 
and accessing learning tools such as printers,
chargers, or publicly shared equipments like the muted piano located in Irving K Library
second floor, water station and so on. 

The project piqued my interest as I have never seen a website on the internet
that has put together student's feelings and suggestions for libraries. What people often
focus on when talking about library is what books they provide. However, most
students come to the library to study rather than reaching the top of the shelves
for careful research. From what I experienced this year as a first-year student, different
studying space can yield completely different outcomes and concentration levels. Therefore,
I am committed to create this software interface to minimize obstacles students
may have through their transition to the university of british columbia.


## As a *user*, you may do the following:
- check out previous comments I made so far for each library with user's unique key linked to each comment
- add comments with ratings for every library with their current date and time associated with their comments
- remove comments with ratings for each library commented after key/password validation
- view the list of libraries and list of comments inside the libraries
- quit RateMyLibrary entirely
- when I quit the application menu, I want to be reminded whether to save my comments as files inside RateMyLibrary.
- when I start the application, I will be given the option to load my historical comments so everything is up-to-date.


## Instructions for Grader:
- You can generate the first required action related to adding Xs to a Y by clicking on "Add" button on the bottom left
- You can generate the second required action related to adding Xs to a Y by clicking on Remove button on the bottom left
- You can locate my visual component such as Vancouver's gif at the right panel when you load my application
- You can save the state of Rate My Library by closing my application window and clicking on "Save" on a separate pop-up window
- You can load the state of my application by clicking on "Click here to view" and follow the new prompt which asks you to load or not. If chosen not to load, you cannot remove or add comments.
- You can delete your comments by selecting user number with correct password. The most common passwords for them are "owen04" (or "qweqwe").

## Phase 4: Task 2
##### - made sure data-preserving code and GUI are not logged, but key actions (add and remove) are logged no matter if the user saved it or not
### Sample of Events:
Wed Apr 12 09:02:09 PDT 2023

A new comment is added to Asian Library<br><br>



Wed Apr 12 09:02:19 PDT 2023

A new comment is added to Biomedical Branch Library<br><br>

Wed Apr 12 09:02:43 PDT 2023

A comment is removed from Irving K. Barber Learning Centre<br><br>

Wed Apr 12 09:03:12 PDT 2023

A new comment is added to Law Library<br><br>

Wed Apr 12 09:03:40 PDT 2023 

A comment is removed from Asian Library<br><br>

Wed Apr 12 09:03:51 PDT 2023

A comment is removed from Biomedical Branch Library


## Phase 4: Task 3
There are four places that could be refactored better in my project.

Firstly, in the Comments class, the method setLibrary() violates the single responsibility principle , as this class is only for manipulating(adding or removing) comments. Associating a library for these comments explicitly does not make sense. In order to
achieve bi-directional relationship, I should consider passing it into the constructor instead, and a lot of codes in UISetUp and JsonReader would have to be changed. The second change, in my opinion, is that the User class can be removed, since it only performs one operation, which is to view all the comments
under a password by a particular user. Since one user's password can still change, this feature does not make a lot of sense, and it is also why I took it out in my GUI. However, if keeping it still makes sense
to the users, I should declare a method in UISetUp for iterating over all the comments in different libraries there rather than having a separate class to handle this utility function. Optionally, I can also 
consider letting Library implement Iterable<Comments> to write fewer for-loops.

The third improvement I would make is to give an ID to every individual library. The reason is that when I tried to iterate over or search for a library in UI, I often lost my pointer
and did not know which library I was referring to. Therefore, I had to add a Hashmap to map a unique number for every library, which was tedious to do so. If I were to improve it,
I would make a singleton class to generate ID for each library or simply add an ID field to each library. By doing so, I will be able to greatly reduce code in UISetUp class and also
HomeGUI's directToOtherPages() method. Lots of "if" clauses and for loops will be reduced as a result. Lastly, there are lots of duplications in 
AddingWindowGUI and RemovingWindowGUI classes as lots of them share the same features such as setFrame() and setUpWindow(). I can actually
have an abstract parent class for them to reduce some code, and if I want, I can set up similar windows easily in the future by just calling the
letting the new class extend this abstract parent class.