# RatedPower backend challenge

With this challenge we want to evaluate your skills and the quality of the code you write. You are expected to use SOLID principles. You can use any tools or library that you consider relevant.

## Required tools
- Java 17

## Objectives

Fork this repository, then develop an application that manages a single JSON file, kept in local storage. The JSON file represents an addition of two operands (“valueX” and “valueY”).

The application will allow the following requests:

- **POST** http request to upload a .json file, with the format of the `sample.json` provided (on the project root) and saves it.
- **PUT** http request to modify the content of the stored .json file, changing the “valueX” and/or the “valueY” fields.
- **GET** http request to download the stored document, including a “result” field.
- **DELETE** http request to delete the document.

## JSON examples

### Sample POST file

```json
{
  "valueX": 8,
  "valueY": 5
}
```

### Sample GET file

```json
{
  "valueX": 8,
  "valueY": 5,
  "result": 13
} 
```
## Running the Application Locally

To run the application locally (on the project root), you can use the Spring Boot Maven plugin as follows:

```
$  ./mvnw spring-boot:run
```
This command will start the application on your local machine.

## Running Tests

To run the tests for the application, you can use the following command (on the project root):

```
$  ./mvnw test
```
This command will build the tests and execute them, providing you with feedback on the success or failure of each test.



