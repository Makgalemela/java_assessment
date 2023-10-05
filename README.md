# Account Ledger API Assessment

- The application exposes api protected by okta security, this are api's which enables basic account functionalities
## Functionalities
- withdraw
- deposit
- balance query
- statement search
  - statement search transaction has filter name **transaction_type**
  - transaction_type is enum with two values:
    - CREDIT
    - DEBIT
  - transaction_type need to be passed during search, if this is not passed then all transaction will be returned
  - remove transactions 
  - remove account and it's related transaction
  - future date the credit/deposit


## Section where not done

- I did not include any UI and therefore no ability to login, just to explain that I could log in via okta as SSO and redirect it to a desired page, I would have build UI in angular and this would have required state management management.

## Improvement
 - transaction pagination
 - Improve algorithm for generating account number
 - more validation
 
## documentation

- included on the root directory are api documents
  - ledger_api.yaml which postman documentation
  - and swagger documentation can be found here http://localhost:19019/swagger-ui/index.html#/


## Testing

- I have done bare minimum to no unit testing, this due to time constrain

-  for okta I will list details here:
     - **clientId** : 0oa9j5cv18NcJWFd75d7
     - **clientSecret** : yl6g5tuy4i2284schHOMubulyg9fjvYPE2w0Diw9
     - **Api Scope** : application
     - **token url** : https://dev-05562770.okta.com/oauth2/default/v1/token
     - **grant_typ** : client_credentials


- after creating first account take not of the account number returned, this will need to be set on postman collection variable **account_number**, it will allow proper testing

### Testing future date transactions:

 - date format yyyy-mm-dd
 - assign todays date to datedFor field on postman, I have a chron job running every minute to resolve future dated transaction as a demo. 

## Design Thinking

- Critical for me was speed that means, application which mean the application need to perform very well, which where rabbitmq come in, each request is queued and process asynchronously.

- Critical to me is proper logging, this is so critical because we can use logs to build sequence of events and this allow for ease of debugging.

- horizontal scale, we would need a batter algorithm for generating account numbers so that no two or more instance generate same account number.

- reliability, better error handling validation, this means that the application will not through random exception it will always return business friendly reason for failure. 

- robust api, proper validation and a more structured api response; a more uniform api endpoint and proper http status, 

- this build with ability to use the api for 3pp and other third parties integration.


# Running the application

- we need instance of postgres 13
  - password: postgres
  - user: postgres
  - database: accounting
- on startup the application with run flyway migration
- we need local instance of rabbit mq
    - user: guest
    - password: guest
    - host: 127.0.0.1
    - port: 5672
-  require gradle 8
-  require java 11
-  To run the application: run in the root directory of the project **./gradle bootRun** or **gradle bootRun**

- I INTENTIONALLY  omitted  docker, this would make setup little bit longer as this would require mapping rabbitmq and postgres host to external host and properly reference containers running rabbitmq and postgres. 
