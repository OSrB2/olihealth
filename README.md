
# Customer Management API

This project is a Customer Management API developed using Java and Spring Boot, following the requirements specified in the [test-dev-backend](https://github.com/olisaude/teste-dev-backend).

## Features

The API provides the following endpoints to manage customer data:

- **Create Customer**: Add a new customer to the system.
- **Edit Customer**: Update an existing customer's details.
- **Get Specific Customer**: Retrieve the details of a particular customer.
- **List Customers**: Retrieve a list of all customers.
- **Top 10 High-Risk Customers**: Get a classification of the 10 customers with the highest health risk.

### Customer Data Structure

Each customer in the system has the following fields:
- **Name**: The full name of the customer.
- **Date of Birth**: The customer's date of birth.
- **Gender**: The customer's gender.
- **Health Issues**: A list of health issues related to the customer.
- **Creation Date**: The date the customer was added to the system.
- **Update Date**: The date the customer's information was last updated.

## Technical Details

- **Frameworks Used**: Java and Spring Boot.
- **Design Principles**: The code was developed following the SOLID principles to ensure scalability and maintainability.
- **Testing**: The project includes both unit tests and integration tests to verify the application's functionality.
- **Responses**: All API responses are formatted in JSON.

## API Documentation

Detailed API documentation is available on Postman: [Postman API Documentation](https://documenter.getpostman.com/view/20651436/2sAXxS7qtA)

## Installation and Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/your-repository.git
   ```

2. Navigate to the project directory:
   ```bash
   cd your-repository
   ```

3. Configure the application properties (e.g., database connection details) if needed.

4. Run docker-compose
   ```bash
   docker-compose up -d
   ```

5. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

| Method | Endpoint                      | Description                         |
|--------|-------------------------------|-------------------------------------|
| POST   | `/customers`                  | Create a new customer.               |
| PUT    | `/customers/{id}`             | Update an existing customer's details.|
| GET    | `/customers/{id}`             | Get a specific customer's details.   |
| GET    | `/customers`                  | List all customers.                  |
| GET    | `/customers/top-risk`         | Get the top 10 customers with the highest health risk.|

## Future Improvements

- Expand test coverage to include more edge cases.
- Implement additional security features.

## License

This project is open-source and available under the [MIT License](LICENSE).
