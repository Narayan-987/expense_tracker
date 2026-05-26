This project requires a few prerequisites before setup. You should have Java 21, Maven, PostgreSQL installed and running on your system, along with a code editor or IDE such as IntelliJ IDEA. Git is also required for version control.

To set up the database, first start the PostgreSQL service and create a database named expense_tracker using the SQL command CREATE DATABASE expense_tracker;. After that, configure your Spring Boot application by updating the application.yml file with the correct database URL, username, and password so that the backend can connect to PostgreSQL successfully.

To run the backend, build the project using mvn clean install, then start the application by running the main class ExpensesApplication.java. Once the backend is running, the frontend can be accessed directly through the browser because this project uses Thymeleaf for server-side rendering. We can open pages like /login, /signup, /expenses, /categories, and /summary using http://localhost:8080.

The base API URL for the application is http://localhost:8080, and all endpoints are built on top of this. For authentication, the main endpoints include /signup for user registration and /login for user login. Expense-related endpoints include /expenses for retrieving all expenses, adding new expenses, updating or deleting expenses using their IDs, and fetching individual expense details. Category management is handled through /categories for listing and adding categories and /categories/{id} for deleting them. A summary endpoint /summary is also available to view monthly summaries.

For initial testing, sample seed data can be inserted into the database. For example, a default user can be created with email admin@gmail.com and password 12345, and sample categories like Food, Transport, Recharge and Shopping can be added. Sample expenses can also be inserted for testing purposes, such as Pizza or Bus Ticket entries linked to their respective categories.

The overall project follows a standard layered architecture where the user interacts with Thymeleaf-based frontend pages, which send HTTP requests to Spring Boot controllers. The controllers pass the requests to the service layer where business logic is handled, and then the repository layer communicates with PostgreSQL to store or retrieve data. The response is then sent back and displayed on the UI, completing the full cycle of the application.



