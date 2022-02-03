# Project overview

`/static` is where the static files for the website are located. The webserver can be run by clicking `run.bat` or the `server.jar` file located in the root directory. It is a basic webserver that tries to service API calls if the URI is pointing to an api endpoint, otherwise serves static files.

**TODO:**
- Write `DatabaseManager` that can query and set information in the database
- Implement `DatabaseManager` into `APIManager`
- Finish frontend
- Figure out where points are being stored in the database and ensure point total is queried
- Write `SessionManager` that can generate and verify session IDs
- Implement `SessionManager` into `APIManager`

## Main.java

Contains driver class `Main` and server handler class `ServerHandler`.

- `void handle(HttpExchange ex) throws IOException`
-- Called when a user directs to any URI on the server. Calls `apiHandle` when it determines request is at an API endpoint, otherwise serves the proper file in `/static/` or gives a 404 error.
- `boolean isAPIRequest(String path)`
-- Given a URI, determines if it leads to an API endpoint or not.
- `void apiHandle(HttpExchange ex, String path)`
-- Calls appropriate function in `APIManager` given a URI.

## APIManager.java

Handles all of the API endpoints.

- `void apiLogin(HttpExchange ex)`
-- Takes supplied username and password and sends a JSON representation of `LoginResponse` from the database. **Not implemented**

- `void apiGetCards(HttpExchange ex)`
-- Takes supplied session ID and sends a JSON representation of `GetCardsResponse` from the database. **Not implemented**

- `void apiGetRewards(HttpExchange ex)`
-- Takes supplied session ID and sends a JSON representation of `GetRewardsResponse` from the database. **Not implemented**

- `void apiRedeemReward(HttpExchange ex)`
-- Takes supplied session ID and reward ID and sends a JSON representation of `RedeemReward`. **Not implemented**

## ContentTypeHandler.java

A simple class that handles conversion from file types to MIME content types.

- `String getContentTypeHeader(String filename)`
-- Takes in a file ending with an extension and returns its MIME content type. Gets extension with regex and finds match in `HashMap<String, String> ContentTypeHandler.contentTypes`.

# How to build server.jar

In IntelliJ, go to Build > Build Artifacts > Build and `server.jar` in the root directory should be automatically updated. I'm pretty sure since I included project files in the repo it should automatically have all the bits and bobs to do that correctly.
