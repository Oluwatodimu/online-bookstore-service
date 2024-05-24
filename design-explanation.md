## Discussion of what was done in the submission

First I want to certify that I alone worked on the requirements and instructions
to build the solution to the backend test I was given.

I also certify that the main requirements, and the bonus requirements were
completed in this project.

### Summary
In our bookstore application, I implemented role-based access control (RBAC) to 
manage two main actors: the customer (user) and the shop owner (admin).

As a customer or user of the bookstore application, users have the ability to view a 
list of available books and discover new books. They could browse through books in the virtual bookstore, 
exploring the titles available for purchase.

Meanwhile, the shop owner or admin had full control over the bookstore inventory. They can add new books 
to the store's collection, update book details, and delete books from the inventory. 

By implementing role based access control (RBAC), I restricted certain actions to only the shop owner or admin. This means that only 
admins can add new books, update book details, and delete books from the store. This ensured that the 
inventory remains organized and up-to-date, while customers can easily find the books they were 
interested in purchasing.


### What was done in the project
1. I utilized Java with the Spring Boot framework to develop the APIs for the bookstore application. 
These APIs enable users to perform various actions, including viewing a list of available books, adding new books 
to the store, updating book details, and deleting books from the store.

2. For the database integration, I used specifically MySQL, and utilized Spring Data JPA for seamless database interactions. 
By leveraging Spring Data JPA, I was able to implement CRUD operations for the book entity efficiently.

3. In addition to the core CRUD operations for the book entity, I also implemented functionality to manage the 
availability status of books. This feature allows admins to update the availability status of books. To maintain control 
over this critical aspect of inventory management, I enforced role-based access control (RBAC), ensuring that only admins have 
the authority to perform actions related to managing the availability status of books. This safeguarded the 
integrity of the bookstore's inventory and upheld the principle of data security and access control.

4. For testing, I wrote unit tests to ensure the reliability and correctness of 
all functionalities within the project. These unit tests cover a wide range of scenarios, validating each component's 
behavior and interactions. By rigorously testing all aspects of the project, I aimed to deliver code that meets the 
requirements of this project.

5. For the documentation, I provide the `readme.md` file that contains instructions on how to run the code, the
`design-explanation.md` file that discusses how this application was built, and the [postman](https://www.postman.com/lively-firefly-891824/workspace/my-public-workspace/collection/18629385-c00572dc-13f4-435f-82bf-ac0cbd2d85ed?action=share&creator=18629385&active-environment=18629385-2ebf7579-7078-4850-aa98-2d7284008d14) collection for the API
documentation.

6. I ensured that the project handled errors effectively by implementing proper error handling techniques and using
the correct HTTP status codes. Each error scenario, such as invalid input or database issues, are addressed with clear 
error messages and the appropriate HTTP status code. Additionally, I maintained a uniform API response structure across 
all endpoints, providing consistency and simplicity for users interacting with the application.

7. As a bonus feature, I integrated authentication for API endpoints to enhance security and restrict access to 
authorized users only. Using Spring Security, I implemented authentication and authorization mechanisms, ensuring that 
only authenticated users with the necessary privileges can access protected endpoints. I used Jwts to secure the endpoints.

Implementing authentication and authorization with Spring Security and RBAC enhances the overall security posture of 
the application, mitigating risks associated with unauthorized access and data breaches. It provides peace of mind to 
both users and administrators, knowing that their data is protected and access is tightly controlled.