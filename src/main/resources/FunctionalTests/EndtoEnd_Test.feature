Feature: End to End Testing of API


Background: User generates token for Authorization
Given I am an authorized user



Scenario: the Authorized user can Add and Remove a book.
Given A list of books are available
When I add a book to my reading list
Then the book is added
When I remove a book from my reading list
Then the book is removed