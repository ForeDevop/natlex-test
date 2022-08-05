# Natlex Test Task

<img src="https://img.shields.io/badge/Maven-3.8.6-brightgreen.svg?style=flat"> <img src="https://img.shields.io/badge/MySQL-8.0.30-brightgreen.svg?style=flat"> <img src="https://img.shields.io/badge/Java-18.0.1.1-brightgreen.svg?style=flat"> <img src="https://img.shields.io/badge/Spring-2.7.2-brightgreen.svg?style=flat">

**REST API development as a test task for Natex.**   <br/>
<br/>
Task description available at [Test task for backend developer](https://github.com/ForeDevop/natlex-test/blob/master/Backend%20Test.pdf)
---

# Overview

- [API](#overview)
    - [Sections](#sections)
      - [Get sections by code](#get-sections-by-geological-class-code)
    - [Geological classes](#geological-classes)
    - [Import file](#import-files)
    - [Export file](#export-files)
    
## Connect to MySQL

To acces the database use the following:

```text
[
    url: jdbc:mysql://localhost:3306/natlex
    username: natlex
    password: password
]
```

## Sections

Allowed methods for Sections (response/request data in JSON format):

GET `api/sections` - get all sections with geological classes.

GET `api/sections/{id}` - get section by specified ID with its geological classes.

POST `api/sections` - save new section with its geological classes in DB (if there is a valid request body).

PUT `api/sections/{id}` - update section with its geological classes in DB (if there is a valid request body).

DELETE `api/sections/{id}` - delete section by specified ID with its geological classes from DB.

### Get sections by geological class code

GET `api/sections/by-code?code={code}` - get all sections that contain geological classes by specified code.

## Geological classes

Allowed methods for Geological Classes (response/request data in JSON format):

GET `api/geoclasses` - get list with all geological classes.

GET `api/geoclasses/{id}` - get geological class by specified ID.

POST `api/geoclasses` - save new geological class in DB (if there is a valid request body).

PUT `api/geoclasses/{id}` - update geological class in DB (if there is a valid request body).

DELETE `api/geoclasses/{id}` - delete geological class by specified ID from DB.

## Import files

Allowed methods for import `.xlsx` files:

POST `api/import (file) ` - save attached file in DB (if there is a valid request body).

GET `api/import/{id}` - get result of importing file by specified ID.

## Export files

Allowed methods for export `.xlsx` files:

GET `api/export` - export data from DB to file.

GET `api/export/{id}` - get result of parsed file by specified ID.

GET `api/export/{id}/file` - returns a file by specified ID, if its parsing done.

