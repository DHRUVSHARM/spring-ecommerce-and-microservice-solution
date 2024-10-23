Scalable ecommerce and payments solution in Spring Boot using microservices architecture. Uses technologies like Kaftka, Spring Cloud Config etc to allow web service discovery, distributed tracing and reactive behaviour.

I have provided the details of the er diagram on the basis of which the microservice design is selected, will be updating more detailed diagrams as the application development progresses : 

## ER Diagram
![image](https://github.com/user-attachments/assets/f8bf1bc4-8772-4a24-810b-e28f0bd63cf7)

# Detailed explanation

This project models an **e-commerce system** with a domain-driven design approach. 
The system is divided into several distinct domains, each responsible for handling specific parts of the data and business logic. 
Below is an explanation of the various domains and their relationships.

### 1. **Customer Domain**:
   - **Customer**: Stores customer details such as `id`, `firstname`, `lastname`, and `email`. Each customer is linked to an **Address**.
   - **Address**: Holds address details like `street`, `houseNumber`, and `zipCode`, with a one-to-one relationship to the customer.

### 2. **Order Domain**:
   - **Order**: Represents customer orders, containing fields like `orderDate` and a `reference`. A customer can place multiple orders.
   - **OrderLine**: Links products to orders and specifies the `quantity` of each product in an order. An order can include multiple products.

### 3. **Product Domain**:
   - **Product**: Represents products for sale, with attributes like `name`, `description`, `availableQuantity`, and `price`. Each product belongs to a **Category**.
   - **Category**: Groups products into categories, such as electronics, clothing, etc.

### 4. **Payment Domain**:
   - **Payment**: Tracks payments for orders, storing information like `reference`, `amount`, and `status`. Each payment is linked to an order.

### 5. **Notification Domain**:
   - **Notification**: Captures system notifications related to orders and payments, with fields for `sender`, `recipient`, `content`, and `date`.

### Key Relationships:
   - A **Customer** can place multiple **Orders**.
   - Each **Order** can have multiple **OrderLines**, linking products to the order.
   - A **Payment** is linked to a single **Order**.
   - **Notifications** are linked to **Orders** and represent updates to the customer.


