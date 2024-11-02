Scalable ecommerce and payments solution in Spring Boot using microservices architecture. Uses technologies like Kaftka, Spring Cloud Config etc to allow web service discovery, distributed tracing and reactive behaviour.

## Project Overview

- To begin with we have an api gateway exposed to the outside world, which can be invoked from a choice of frontend depending on the development team. The api gateway can be configure for authentication,
load balancing etc; 

- there are 3 routes available to the user which are for performing actions relating to :
1) /customers -> for creating and storing a customer in a mongodb database in the customer microservice.
2) /products -> cretes and stores products to be sold in a postgresql database in the product microservice.
3) /orders -> stores an order in a postgresql database, an oder consists of a list of orderline elements that each establish a relationship between a customer and a product, an order is also related to a payment ment which is another microservice.

- furthermore, the order microservice uses openfeign to send an order confirmation asyncronously to a kafka message broker
- also, the payment microserevice sends a payment confirmation to the kafka message broker
- both notfications are consumed by the notification microservice and stored in a mongodb database and used to send out an email to the user concerned
- the entire process can be traced using zipkin logs
- we use spring cloud config as a separate microservice that contains externalized config for each of the microservices
- there is a eureka discovery server that registers these services as and when we start them up.

I have provided the details of the er diagram on the basis of which the microservice design is selected, will be updating more detailed diagrams as the application development progresses : 

## ER Diagram
![image](https://github.com/user-attachments/assets/f8bf1bc4-8772-4a24-810b-e28f0bd63cf7)

## Flow of the Application

Below is the diagram representing the application architecture complete with all the databases and showing where kafka, zipkin etc; are used : 

![image](https://github.com/user-attachments/assets/4ea8cc2a-7584-4c1e-a135-10ed2c609e62)

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

Here’s an example of how the application’s flow works, from placing an order to receiving notifications:

1. **Customer Registration**: A customer registers with their information and is assigned a unique `Customer ID`. Their address is also stored.
   
2. **Browsing Products**: The customer can view and search products, which are organized by categories. The available quantity is displayed for each product.

3. **Placing an Order**: When the customer decides to purchase products, they create an order. 
   - The application captures each item and its quantity in an `OrderLine`.
   - The total cost and order details are stored in the `Order` object.

4. **Processing Payment**: After placing an order, the customer proceeds to payment.
   - The `Payment` entity is created with details such as the payment amount, status (e.g., `PENDING`, `COMPLETED`), and a unique payment reference.
   - Payment status is tracked in real-time and updated accordingly.

5. **Notifications**: 
   - As the payment status or order status changes (e.g., from `PROCESSING` to `SHIPPED`), a `Notification` is sent to inform the customer.
   - Notifications also include payment confirmations and delivery updates.

6. **Order Fulfillment and Delivery**: Once payment is completed and verified, the order moves to the fulfillment stage, where it’s processed and shipped to the customer. Notifications keep the customer updated throughout.



