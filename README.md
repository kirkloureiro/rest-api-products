# rest-api-products

This is an API which perform CRUD operations on a Product resource using Image as a sub-resource of Product.
Some technologies used in this application:

- JSE 8
- Spring
- JAX-RS
- Jersey
- JSON
- JPA(Hibernate)
- H2 Database Engine
- Log4j
- JUnit 4
- EasyMock
- Jetty
- Maven

# Building

Just execute the command: 

```bash
mvn clean install
```

# Running the application

You can run/deploy this application with the maven command:

```bash
mvn jetty:run
```

The application is deployed by default at : http://localhost:8080/products/

###### Database:

The H2 database is automatically deployed by default at: http://localhost:8082/login.do

- Driver Class: org.h2.Driver
- JDBC URL: jdbc:h2:mem:products
- Login: sa
- Password:

Note: The database is created on deploy and droped on stop. If you want
disable this feature, go to applicationContext.xml file and change the property hibernate.hbm2ddl.auto.


# Exposed Enpoints:

See [swagger.yaml](swagger.yaml)
	
# How to run the suite of automated tests

Note: In order to run the integrated suite of automated tests, you need to deploy the application.

Just execute the command:

```bash
mvn jetty:run 
```
and then

```bash
mvn test
```


The suite of automated tests cover the most of important operations:

1) Create, update and delete products
	ProductCRUDRestImplTest.createProductTest
	ProductCRUDRestImplTest.updateProductTest
	ProductCRUDRestImplTest.deleteProductTest

2) Create, update and delete images
	ProductCRUDRestImplTest.createImageTest
	ProductCRUDRestImplTest.updateImageTest
	ProductCRUDRestImplTest.deleteImageTest

3) Get all products excluding relationships (child products, images) 
	ProductCRUDRestImplTest.getAllProductsExcludingRelationshipsTest	
	
4) Get all products including specified relationships (child product and/or images) 
	ProductCRUDRestImplTest.getAllProductsIncludingAllRelationshipsTest

5) Same as 3 using specific product identity 
	ProductCRUDRestImplTest.getAllProductsExcludingRelationshipsByIdTest
	
6) Same as 4 using specific product identity 
	ProductCRUDRestImplTest.getAllProductsIncludingImageRelationshipTest
	
7) Get set of child products for specific product 
	ProductCRUDRestImplTest.getSetChildProductsForSpecificProductTest
	
8) Get set of images for specific product
	ProductCRUDRestImplTest.getSetImagesForSpecificProductTest


