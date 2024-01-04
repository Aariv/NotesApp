# NotesApp
![](https://img.shields.io/badge/build-success-brightgreen.svg)

# Stack

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

# File structure

<img width="530" alt="Screenshot 2024-01-04 at 9 55 42 AM" src="https://github.com/Aariv/NotesApp/assets/11393142/7556bc89-9ebc-4af0-bccf-a74c724666d8">

# How to use this code?

1. Make sure you have [Java 17 and above](https://www.java.com/download/) and [Maven](https://maven.apache.org) installed

2. Fork this repository and clone it
  
```
$ git clone https://github.com/Aariv/NotesApp
```

3. Navigate into the folder  

```
$ cd NotesApp
```

4. Install dependencies

```
$ mvn clean install
```

5. Run the project

```
$ mvn spring-boot:run
```

6. Navigate to `http://localhost:8080/swagger-ui.html` in your browser to check everything is working correctly. You can change the default port in the `application.yml` file

```yml
server:
  port: 8080
```
# Swagger UI
<img width="1674" alt="Screenshot 2024-01-04 at 9 53 24 AM" src="https://github.com/Aariv/NotesApp/assets/11393142/0282bcf4-b3f2-4c72-8909-5c4302628ecf">
