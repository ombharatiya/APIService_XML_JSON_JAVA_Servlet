# APIService_XML_JSON_JAVA_Servlet
Implementation of an API Service to query on user data which is stored in xml file and mapped to the APIServlet GET method and returning a JSON Array 

## API 

**Application Programming Interface** or API is a software intermediary that allows two applications to 'talk' to each other. 
When you use an application on your mobile phone, this is what happens: 
- The application connects to the Internet and sends data to a server. 
- The server then retrieves that data, interprets it, and performs the necessary actions and sends it back to your phone. 
- The application then interprets that data and presents the information that you wanted in a readable format. 
All this is done through an API. 

## Task details 

Your task is to implement an API service to query on user data which is stored in **_com.he.api.data.xml_** in the following format. 

```
    <dataset id_auto_increment="3"› 
      <user id="1" firstName="John" lastName="Dawson"/> 
      <user id="2" firstName="John" lastName="Doe"/> 
    </dataset>
```
- ``` <dataset> ```  tag is the root element in the XML document. It has a single attribute **_id_auto_increment_**. The value of Its ID increases as follows: value of the user's ID that was added last + 1 
- ``` <user /> ``` tag represents a user record in the dataset. It has following attributes: 
  
  - id: Primary key of the user data. It is an integer value. 
  - firstName: First name of the user. It is a string value that contains only lowercase and uppercase letters of the English alphabet. 
  - IastName: Last name of the user. It is a string value that contains only lowercase and uppercase letters of the English alphabet. 

The call to server would be made by 
```scheme://domain:port/API?action=#&id=#&firstName=#&lastName=#```, where 'string' after '?' denotes the query string. 

You have to implement an API service that is mapped to the **_APIServlet_** (com.he.api.APIServlet.java) **_GET Method_** (at URL scheme://domain:port/API). 
The query string (read more about query strings here) may have following parameters: 

- action: Defines what operations need to be performed 
- id 
- firstName 
- IastName

## Types of queries

The queries can be of the following types: 
- **action=searchByld&id=#**: Search by id in the dataset in the data.xml file. Return a JSON array that contains the selected user as a response. 

Example URL: 
```scheme://domain:port/API?action=searchById&id=1```

Response: 
```
  [{ 
    "id": 1, 
    "firstName": "John", 
    "lastName": "Dawson" 
  }l 
```
- **action=searchByFirstName&firstName=#**: Search by firstName in the dataset in the data.xml file. Return a JSON array that contains the selected user(s) as a response. 

Example URL:
```scheme://domain:port/API?action=searchByFirstName&firstName=John ```

Response: 
```
  [{ 
    "id": 1, 
    "firstName": "John", 
    "lastName": "Dawson" 
  }, { 
    "id": 2, 
    "firstName": "John", 
    "lastName": "Doe" 
  }] 
```
- **action=searchByFirstName&IastName=#**: Search by lastName in the dataset in data.xml. Return a JSON array that contains the selected user(s) as a response. 

Example URL:
```scheme://domain:port/API?action=searchByLastName&lastName=Doe ```

Response: 
```
  [{ 
    "id": 2, 
    "firstName": "John", 
    "lastName": "Doe" 
  }] 
```
- **action=searchByldRange&low=#&high=#**: Search by id in the dataset in the data.xml file. Return a JSON array that contains the selected user(s) whose id is in the range [low,high] as a response. 

Example URL: 
```scheme://domain:port/API?action=searchByIdRange&low=1&high=2 ```

Response: 
```
  [{ 
    "id": 1, 
    "firstName": "John", 
    "lastName": "Dawson" 
  }, { 
    "id": 2, 
    "firstName": "John", 
    "lastName": "Doe" 
  }] 
```
- **action=updateUser&id=#&firstName=#&lastName=#**: Update the user in the data.xml file, which is given by id, and set the firstName and IastName to given values. If firstName or IastName parameter are not present then it's value is left unchanged. It may be possible that only firstName is present and lastName is null, then only firstName has to be updated. 

Example URL:
```scheme://domain:port/API?action=updateUser&id=1&firstName=James&lastName=Jackson ```

```
  <dataset id auto increment="3"> 
    <user id="1" firstName="James" lastName="Jackson"/> 
    <user id="2" firstName="John" lastName="Doe"/> 
  </dataset> 
```

- **action=insertUser&firstName=#&IastName=#**: Insert a user record in the data.xml file by using the following parameters: 
  - firstName 
  - IastName 
  
The ID of the user is given by the id_auto_increment parameter of the root dataset tag. After the value is inserted, the id_auto_increment parameter is incremented by 1. 

Example URL: 
```scheme://domain:port/API?action=insertUser&firstName=James&lastName=Jackson ```

```
  <dataset id auto increment="4"> 
    <user id="1" firstName="John" lastName="Dawson"/> 
    <user id="2" firstName="John" lastName="Doe"/>
    <user id="3" firstName="James" lastName="Jackson"/>
  </dataset> 
```

## Testing
Test your API implementation by comparing the mapping of your JSON array and XML DOM. 

