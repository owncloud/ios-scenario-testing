@previews
Feature: Previews

  As a user
  I want to be able to preview my files
  so that i can check the content without using 3rd party apps

  Background: User is logged in
    Given user Alice is logged in

    Scenario: text preview
      Given the following items have been created in Alice account
        | file | previewText.txt |
      When Alice opens the file previewText.txt
      Then the file previewText.txt should be opened and previewed

    Scenario: image preview
      Given the following items have been created in Alice account
        | image | blank.jpg |
      When Alice opens the file blank.jpg
      Then the image blank.jpg should be opened and previewed

    Scenario: pdf preview
      Given the following items have been created in Alice account
        | pdf | sample.pdf |
      When Alice opens the file sample.pdf
      Then the pdf sample.pdf should be opened and previewed