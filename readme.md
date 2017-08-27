# Offer service to manage offers #

This is a **Offer Service** written in **Java 8** and using the **Spring-Boot** framework to develop the **REST API** that allows any client to connect to the service and manage and view the offers available.

The build tool used is **Gradle**. In the **build.gradle** file, there are all the dependencies needed for the project.

**Mockito** has been used to help with the unit testing.

# Introduction #

The service REST API allows: offer creation, offer update, offer delete, offer viewing.

For this solution, the data will not persist beyond the lifespan of the server.
An assumption was made that name of an offer of a merchant should be unique.

# Prerequisites #

Have your prefferred IDE installed.
Have JDK installed.

# Functionality #

Using basic **curl** syntax or a tool like **Postman** for example, the service supports following requests:

POST request to create an offer:

http://{hostname}:{port}/offer-service/offers?merchant={merchant}

#### Request Body example
	{
		"name": "Great Offer 1"
		"description": "Amazing description 1",
		"priceCurrency": "GBP",
		"priceAmount": 20
	}

If the currency is not an ISO 4217 currency code, the server will return an exception and the offer will not be created.

#### Response Body example
	Successfully created offer {name} for {merchant}


GET request to retrieve a list of the created offers by merchant:

http://{hostname}:{port}/offer-service/offers?merchant={merchant}

#### Response Body example
	[
		{
			"name": "Great Offer 1",
			"description": "Amazing description 1",
			"priceCurrency": "GBP",
			"priceAmount": 20
		},
		{
			"name": "Great Offer 2",
			"description": "Amazing description 2",
			"priceCurrency": "EUR",
			"priceAmount": 40
		}
		...
	]

GET request to retrieve a list of all the created offers in the system:

http://{hostname}:{port}/offer-service/alloffers

#### Response Body example
	{
		"merchant2": [
			{
				"name": "Great Offer 2",
				"description": "Amazing description 2",
				"priceCurrency": "GBP",
				"priceAmount": 1000
			}
		],
		"merchant1": [
			{
				"name": "Great Offer 1",
				"description": "Amazing description 1",
				"priceCurrency": "GBP",
				"priceAmount": 20
			},
			{
				"name": "Great Offer 2",
				"description": "Amazing description 2",
				"priceCurrency": "EUR",
				"priceAmount": 40
			}
		]
		...
	}


PUT request to update a previously created offer of a merchant:

http://{hostname}:{port}/offer-service/offers?merchant={merchant}&offerName={offerName}

#### Request Body example
	{
		"description": "Updated description",
		"priceCurrency": "GBP",
		"priceAmount": 500
	}

#### Response Body example
 Successfully updated offer {offerName}


DELETE request to delete a previously created offer of a merchant

http://{hostname}:{port}/offer-service/offers?merchant={merchant}&offerName={offerName}

#### Response Body example
Successfully deleted offer {offerName}


## Running the service

Make sure you have an IDE installed.
Make sure you have JDK installed.
Clone this project.

You can import it into your IDE and then get the Gradle dependecies and then run the project. Spring Boot will start up Tomcat and will listen by default on the 8080 port. The resources folder contains an **application.properties** file that stores the default port, context path and application name.

The project can also be built as a jar file and then started from the command line using the following command:

	java -jar offer-service-1.0.0.jar


## Tests

The tests can be run from the IDE or from the command line using the following command:

	gradle test
