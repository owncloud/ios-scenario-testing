@serversidesearch @nooc10
Feature: Server Side Search

  As a user
  I want to be able to search files for content or name in the server
  so that i can check the remote updates

  Background: User is logged in
    Given user Alice is logged in

  Rule: Search for content in a file

    Scenario Outline: Search for content in a file with one match
      Given the following items have been created in Alice account
        | <type>      | <itemWithMatch> |
        | <typeItem1> | <fakeItem1>     |
        | <typeItem2> | <fakeItem2>     |
        | <typeItem3> | <fakeItem3>     |
      When Alice search by content in the server files containing <pattern>
      Then Alice should see <itemWithMatch> in filelist
      But Alice should not see <fakeItem1> in filelist
      And Alice should not see <fakeItem2> in filelist
      And Alice should not see <fakeItem3> in filelist

      Examples:

        | type  | itemWithMatch  | fakeItem1 | typeItem1 | fakeItem2      | typeItem2 | fakeItem3  | typeItem3 | pattern |
        | pdf   | sample.pdf     | blank.jpg | image     | video.mp4      | video     | search.txt | txt       | small   |
        | txt   | search.txt     | blank.jpg | image     | video.mp4      | video     | sample.pdf | pdf       | two     |
        | docx  | officedoc.docx | blank.jpg | image     | video.mp4      | video     | search.txt | txt       | prueba  |
        | image | cheatsheet.png | blank.jpg | image     | officedoc.docx | docx      | blank.jpg  | image     | onView  |

    Scenario: Search for content with no matches
      Given the following items have been created in Alice account
        | image | blank.jpg |
        | video | video.mp4 |
      When Alice search by content in the server files containing two
      Then Alice should see no matches

  Rule: Search for name+content in a file

    Scenario Outline: Search for name and content using only name in a file with one match
      Given the following items have been created in Alice account
        | <type>      | <itemWithMatch> |
        | <typeItem1> | <fakeItem1>     |
        | <typeItem2> | <fakeItem2>     |
        | <typeItem3> | <fakeItem3>     |
      When Alice search by name and contents in the server files containing <pattern>
      Then Alice should see <itemWithMatch> in filelist
      But Alice should not see <fakeItem1> in filelist
      And Alice should not see <fakeItem2> in filelist
      And Alice should not see <fakeItem3> in filelist

      Examples:

        | type | itemWithMatch | fakeItem1 | typeItem1 | fakeItem2 | typeItem2 | fakeItem3  | typeItem3 | pattern |
        | pdf  | sample.pdf    | blank.jpg | image     | video.mp4 | video     | search.txt | txt       | sample  |
        | txt  | search.txt    | blank.jpg | image     | video.mp4 | video     | sample.pdf | pdf       | search  |

      Scenario Outline: Search for name and content using both in a file with one match of each type
        Given the following items have been created in Alice account
          | <type>      | <itemWithMatch> |
          | <typeItem1> | <fakeItem1>     |
          | <typeItem2> | <fakeItem2>     |
          | <typeItem3> | <fakeItem3>     |
        When Alice search by name and contents in the server files containing <pattern>
        Then Alice should see <itemWithMatch> in filelist
        Then Alice should see <fakeItem3> in filelist
        But Alice should not see <fakeItem1> in filelist
        And Alice should not see <fakeItem2> in filelist

        Examples:

          | type | itemWithMatch | fakeItem1 | typeItem1 | fakeItem2 | typeItem2 | fakeItem3   | typeItem3 | pattern |
          | pdf  | sample.pdf    | blank.jpg | image     | video.mp4 | video     | search2.txt | txt       | samp    |

      Scenario: Search for name and content with no matches
      Given the following items have been created in Alice account
        | image | blank.jpg |
        | video | video.mp4 |
      When Alice search by name and contents in the server files containing nothing
      Then Alice should see no matches
