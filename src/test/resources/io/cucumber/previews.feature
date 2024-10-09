@previews
Feature: Previews

  As a user
  I want to be able to preview my files
  so that i can check the content without using 3rd party apps

  Background: User is logged in
    Given user Alice is logged in

    Scenario Outline: preview of different kind of files
      Given the following items have been created in Alice account
        | <type> | <name> |
      When Alice opens the file <name>
      Then the <type> <name> should be opened and previewed

      Examples:

        | type  | name       |
        | file  | text.txt   |
        | image | blank.jpg  |
        | pdf   | sample.pdf |
        | audio | sound.mp3  |
        | video | video.mp4  |