Feature: Integration tests

    Scenario: Testing an invalid GET endpoint - 404
        Given path 'wrong'
        When method GET
        Then status 404

    Scenario: Testing exact response of account GET endpoint for {accountId: '0123456789'}
        Given path 'v1/accounts/0123456789'
        When method GET
        Then status 200
        And match response.accountId == '0123456789'
        And match response.status == '200'
        And match response.accounts contains expected
        | accountId        | '0123456789' |
        | accountType      | 'debit'      |
        | accountOpenDate  | '01/01/2020' |
        | accountCloseDate | '31/12/2020' |
        
    Scenario: Testing exact response of accounts GET endpoint
        Given path 'v1/accounts'
        When method GET
        Then status 200
        And match response.accountId == null
        And match response.status == '200'
        And match response.accounts contains expected
        | accountId        | '0123456789' | '1234567890' |
        | accountType      | 'debit'      | 'credit'     |
        | accountOpenDate  | '01/01/2020' | '01/01/2020' |
        | accountCloseDate | '31/12/2020' | '31/12/2020' |

    Scenario: Testing service response for missing account
        Given path 'v1/accounts/0123456780'
        When method GET
        Then status 200
        And match response.accountId == '0123456780'
        And match response.status == '200'
        And match response.errorMessage == '{No data found}'

    Scenario: Testing service response for invalid account id parameter
        Given path 'v1/accounts/012345678'
        When method GET
        Then status 400
        And match response.accountId == '012345678'
        And match response.status == '400'
        And match response.errorMessage == '{Invalid accountId}'

    Scenario: Testing exact response
        Given path 'v1/accounts/0123456789'
        When method GET
        Then status 200
        And match response '/data/integrationAccountResponse.json'

    Scenario: Testing exact response
        Given path 'v1/accounts'
        When method GET
        Then status 200
        And match response '/data/integrationAccountsResponse.json'