# Client-BookApp 📚

This repository contains two Spring Boot applications: the BookApp (a book management API) and the Client (a client application that interacts with the BookApp).

## Prerequisites

Ensure you have the following installed on your machine:

- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) or later
- [Gradle](https://gradle.org/)
- [Docker](https://www.docker.com/) (for running the applications in container)

## Setup

### To run it with Docker:

<em> If you run the application for the first time on your machine in order not to have any issues regarding Docker Daemon, please make sure that you have Docker Desktop application opened. </em>

1. **Clone the repository:**

   ```sh
   git clone https://github.com/SITE-ADA/as3-testing-and-aspect-oriented-programming-mammadmammadov.git
2. **Navigate to the project directory**
   
   ```sh
   cd as3-testing-and-aspect-oriented-programming-mammadmammadov

3. **Build the BookApp Application**
   ```sh
   cd BookApp
   gradle build
4. **Build image for BookApp Application**
   ```sh
   docker build -t bookapp .
5. **Build the Client Application**
   ```sh
   cd ..
   cd Client
   gradle build
6. **Build image for Client Application**
   ```sh
   docker build -t client .
7. **Navigate to root folder holding both projects and run docker-compose file**
    ```sh
    cd ..
   docker-compose up --build


### To run it manually as two separate applications from IDE (e.g. from IntelliJ IDEA):

<strong><em>First, make sure that in BookService.java file in your Client Application you change "bookapp" to "localhost" on Line 27. (comments are also provided in the source code). It is because in the previous Docker setup, there is networking between those two applications and Client depends on the BookApp while this is not very much the case when you run them separately from IDE.</em></strong>

1. **Clone the repository:**

   ```sh
   git clone https://github.com/SITE-ADA/as3-testing-and-aspect-oriented-programming-mammadmammadov.git
2. **Navigate to the BookApp Application**
   
   ```sh
   cd as3-testing-and-aspect-oriented-programming-mammadmammadov/BookApp
2. **Build the BookApp application**
   ```sh
   .\gradlew build
3. **Run the BookApp application**
   ```sh
   .\gradlew bootRun
4. **Build the Client application**
   ```sh
   cd ..
   cd Client
   .\gradlew build
5. **Run the Client application**
   ```sh
   .\gradlew bootRun

## Usage

### Interacting with the BookApp 

You can use tools like Postman to interact with the API endpoints provided by BookApp. It runs on [http://localhost:8080](http://localhost:8080).

### Interacting with the Client Application

The Client application provides an interface to interact with the BookApp. The Client runs on [http://localhost:9090](http://localhost:9090).

## API Endpoints

### BookApp Endpoints

- **GET /books**: Retrieve all books
- **GET /books/{id}**: Retrieve a book by ID
- **POST /books**: Create a new book
- **PUT /books/{id}**: Update an existing book
- **PATCH /books/{id}**: Partially update a book
- **DELETE /books/{id}**: Delete a book by ID

### Client Endpoints

- **GET /client/books**: Retrieve all books
- **GET /client/books/{id}**: Retrieve a book by ID
- **POST /client/books**: Create a new book
- **PUT /client/books/{id}**: Update an existing book
- **PATCH /client/books/{id}**: Partially update a book
- **DELETE /client/books/{id}**: Delete a book by ID



##  &#8595; Project Requirements &#8595;
<em>
Dear students, <br />

In this assignment, you are required to create a very simple RESTful application using Spring similar to the previous
Assignment 2. (One entity and CRUD operations on that entity is enough - use GET, POST, DELETE,  PUT, PATCH methods). 

<h4> Task related: (20%)</h4>
<ul>
    <li> Create a CRUD for a single entity</li>
    <li> Perform a validation for data entry in create and update</li>
    <li> Use DTO pattern instead of exposing your entities to the client</li>
    <li> Perform dynamic and automatic mapping using MapStruct instead of manual mapping</li>
</ul>

<h4> Task related: (20%)</h4>
<ul>
    <li> Write unit tests to test methods of Service class(es). Try to design test scenarios as rich as possible</li>
    <li> Write some integration tests for Controller methods</li>
</ul>

<h4> Task related: (20%)</h4>
<ul>
    <li> Log a message before each method is called in the Service class(es) with the information about the input parameters</li>
    <li> Log a message after each method is executed in the Service class(es) with the information about the return value</li>
    <li> Log a message after each method is executed in the Service class(es) with the information about the execution time</li>
</ul>

<h4> Task related: (30%)</h4>
<ul>
    <li> Develop another app which will use the endpoints exposed by the first app listening to different port. </li>
    <li> Develop endpoints on the new app to send requests to the former one and redirect the responses back to the user. </li>
    <li> Apply logging again for the new app. </li>
</ul>

<h4> Bonus: (20%)</h4>
<ul>
    <li> There are two applications now that are running on your machine. </li>
    <li> Dockerize the applications: 
        <ul>
            <li> Create Dockerfile for both applications and build the docker image for both. </li>
            <li> Run both images to create containers and check both applications.</li>
            <li> Running multiple images manually is not very practical. Create docker-compose file to run your containers in an easier way.  </li>
        </ul>
    </li>
</ul>

<h4> Submission related: (10%) </h4>
<ul>
    <li> Make sure you configured your git client (i.e., username and email is set). Use firstname_lastname as the
        username. </li>
    <li> Please, also ensure that you regularly check, add and commit your changes to the remote repo so that we can
        see
        your progress. </li>
    <li>Things to be submitted to the BB grader in a .zip format:
        <ol>
            <li>This README.md file updated to have
                <ul>
                    <li>the instructions on how to start and use the application(s)</li>
                    <li>link to the video recording</li>
                </ul>
            </li>
            <li>Application source after <em>./gradlew clean</em></li>
        </ol>
    </li>
    <li> <strong> Submissions without a video submission will not be graded.</strong> </li>
</ul>

<p><b>Note:</b> Please work every day; this is definitely not a task you can complete on the last day!
    To keep track of the progress, create a git repository, commit, and push each change (ideally at least every time
    you complete a feature) to the repository.
    Ensure that you have at least <strong>1 commit a day</strong> STARTING from <strong>May 9<sup>th</sup></strong>
    otherwise you will lose points.
</p>
<p>For <strong>late submissions</strong> please refer to the SYLLABUS.</p>


This assignment will give you <strong>10%</strong> of the total. <br />
<em> Good luck! </em> <br />
<em> NS </em>
</em>
