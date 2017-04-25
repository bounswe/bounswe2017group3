# Events Application REST API

This is a dummy application for our API project. It allows the consumers to do basic CRUD operations on users and events.

# Endpoints

Here are the currently implemented endpoints with their descriptions and usage information. Base URL for the API is https://bounswe2017group3.herokuapp.com/.

## API Root [/]

This resource does not have any attributes. Instead it offers some information about the API itself.

### Retrieve the Entry Point [GET]

+ Response 200 (application/json)

```json
{
  "baseUrl": "https://bounswe2017group3.herokuapp.com/",
  "git": "https://github.com/bounswe/bounswe2017group3.git",
  "version": "1.0.0"
}
```

## Users Collection [/user{?username&email}]

+ Parameters
    + username (optional, string) - The username of the user to retrieve
    + email (optional, string) - The email of the user to retrieve

A User object has the following attributes:

+ id (set automatically)
+ username
+ password
+ email (optional)
+ fullname (optional)

### List All Users [GET]

+ Response 200 (application/json)

```json
[
  {
    "id": 1,
    "username": "hakan123",
    "email": "hakan.koca@gmail.com",
    "fullname": "Hakan Koca"
  },
  {
    "id": 2,
    "username": "kybiss",
    "email": "kybiss@gmail.com",
    "fullname": "Gökçe Uludoğan"
  },
  {
    "id": 3,
    "username": "MertKalaylioglu",
    "email": "m.k@gmail.com",
    "fullname": "Mert Kalaylioglu"
  }
]
```

### Create a New User [POST]

You may create your own user using this action. It takes a JSON object containing username, password, email and fullName; where email and fullName are optional.

+ Parameters
    + username (string) - The username of the user that'll be used to log in and get an authorization token
    + password (string) - The password of the user that'll be used for authentication
    + email (optional, string) - The email of the user
    + fullName (optional, string) - The full name (name and surname) of the user

+ Request (application/json)

```json
{
  "username": "smddzcy",
  "password": "$up3r$3cr3t",
  "fullname": "Samed Düzçay",
  "email": "smddzcy@gmail.com"
}
```

+ Response 200 (application/json)

```json
{
  "id": 1,
  "username": "smddzcy",
  "email": "smddzcy@gmail.com",
  "fullname": "Samed Düzçay"
}
```

### Update a User [PUT]

You may update a user using this action. It takes a JSON object containing the new fields of the user, and a query param to know which user to update.

+ Parameters
    + username (string) - The username of the user that'll be updated, which is received as a query parameter
    + all post parameters - New fields of the user

+ Request (application/json)

```json
{
  "fullname": "Ahmet Büyük",
}
```

+ Response 200 (application/json)

```json
{
  "id": 1,
  "username": "smddzcy",
  "email": "smddzcy@gmail.com",
  "fullname": "Ahmet Büyük"
}
```

# Setting Up the Development Environment

For development, you need Maven and PostgreSQL installed on your computer.

1. Create a database in your local and make sure the Postgres daemon is running on the background.
2. Get the database URL and put it your path variables with the name `JDBC_DATABASE_URL`. This is where our application looks for the database url and it wouldn't work without it.
3. Open the project folder and run `mvn install`.  
 This will install the dependencies and build the project. You should see a success message with the title `BUILD SUCCESS` after a few minutes (or seconds, depending on your environment).
4. To run the application, run `java -Dserver.port=8080 -jar target/*.jar`, again in the project folder.  
 This will run the application in the background, so you should keep the terminal open. Go to http://localhost:8080/ and see if it works, you should see the API root.
