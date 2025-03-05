# Retail Rewards Program

A Spring Boot application that calculates reward points for retail customers based on their purchase transactions.

## Overview

This application implements a rewards program where customers earn points based on their purchase amounts:
- 2 points for every dollar spent over $100 in each transaction
- 1 point for every dollar spent between $50 and $100 in each transaction

For example, a $120 purchase = 2×$20 + 1×$50 = 90 points

## Features

- RESTful API endpoint to retrieve customer rewards
- Points calculation for specified time periods
- Monthly and total points tracking
- H2 in-memory database for data storage
- Comprehensive error handling
- Unit and integration tests

## Technical Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database
- JUnit 5
- Mockito

## API Endpoints

### Get Customer Rewards

### Test Coverage
The project includes:
- Unit tests for service layer
- Integration tests for REST endpoints
- Test coverage for error scenarios
- Sample data testing various transaction amounts

## H2 Database Console

Access the H2 Console to view/query the database:
- URL: http://localhost:8081/h2-console
- JDBC URL: jdbc:h2:mem:rewardsdb
- Username: sa
- Password: (leave empty)

## Sample Data

The application includes sample data for testing different scenarios:
- Customer 1: Mix of transactions across different reward tiers
- Customer 2: Mostly high-value transactions
- Customer 3: Mostly low-value transactions
- Customer 4: Edge cases (amounts near threshold values)

## Error Handling

The application handles various error scenarios:
- Invalid customer ID (negative or zero)
- No transactions found for customer
- Invalid date ranges
- Negative transaction amounts
- Unexpected server errors

## Logging

- Service layer logs transaction processing details
- Debug logs for point calculations
- Error logs for exceptional scenarios
- Performance monitoring logs

## Contributing
https://github.com/rk-vision/reward-mgmt.git
1. Fork the repository
2. Create your feature branch (`git checkout -b rk-vision/reward-mgmt`)
3. Commit your changes (`git commit -m 'Add some reward-mgmt'`)
4. Push to the branch (`git push origin feature/reward-mgmt`)
5. Open a Pull Request

## Future Enhancements

Potential improvements that could be added:
- Customer management API
- Transaction creation endpoints
- Authentication and authorization
- Caching for frequently accessed data
- Pagination for large transaction sets
- Export functionality for reports
- More detailed transaction analytics

## License

This project is licensed under the RK License - see the LICENSE file for details