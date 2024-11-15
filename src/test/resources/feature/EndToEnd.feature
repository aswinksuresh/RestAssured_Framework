Feature: Validate End to End Scenario
 Scenario Outline: verify end to end
    Given user wants to call "/booking" endpoint
    And set header "Content-Type" to "application/json"
    And set body from "create_booking.json"
    When user performs post call
    Then verify status code 200
    And store "bookingid" at "booking.id"
    And verify response schema with "create_booking_schema.json"
    And verify response body has same data as request for post
    And verify response body has filed "booking.firstname" is "Jim"
    Given user wants to call "/auth" endpoint
    And set header "Content-Type" to "application/json"
    And set body with fields "<username>" and "<password>"
    When user performs post call
    Then verify status code 200
    And store "token" at "token.value"
    Given user wants to call "/booking/@id" endpoint
    And set header "Content-Type" to "application/json"
    And set header "Accept" to "application/json"
    And set header "Cookie" to "token=@token"
    And set body from "create_booking.json" with "firstname" value "Aswin"
    When user performs put call
    Then verify status code 200
    And verify request body same as response for put
   Given user wants to call "/booking/@id" endpoint
   And set header "Content-Type" to "application/json"
   And set header "Accept" to "application/json"
   And set header "Cookie" to "token=@token"
   When user performs delete call
   Then verify status code 200

   Examples:
    |username|password|
    |  admin      |   password123     |
   |       Admin      |       password            |
   |     password        |      aadmin             |

