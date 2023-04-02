# Database Tables SQL Code
```sql
CREATE DATABASE "social_network";

use social_network;

CREATE TABLE department (
 dept_name varchar(20),
 building varchar(20) NOT NULL,
 budget int(11) DEFAULT NULL,
 PRIMARY KEY (dept_name)
);

CREATE TABLE User (
	UserName varchar(20),
	PasswordHash varchar(20) NOT NULL,
	Name varchar(20) NOT NULL,
	Telephone varchar(20) NOT NULL,
	dob varchar(20) NOT NULL,
	gender varchar(10) NOT NULL,
	email varchar(100) NOT NULL,
	PRIMARY KEY (UserName)
);

CREATE TABLE Role (
	RoleId varchar(20),
	Name varchar(20) NOT NULL,
	Description varchar(20) NOT NULL,
	PRIMARY KEY (RoleId)
);

CREATE TABLE RoleUser (
	UserName varchar(20),
	RoleId varchar(20),
	dateAssign date NOT NULL,
	PRIMARY KEY (UserName, RoleId) 
);

CREATE TABLE UserGood (
	UserName varchar(20), 
	hashing mediumtext NOT NULL, 
	Name varchar(20) NOT NULL, 
	Telephone varchar(20) NOT NULL, 
	PRIMARY KEY (UserName)
);

CREATE TABLE RoleUserGood (
	UserName varchar(20),
	RoleId varchar(20),
	dateAssign date NOT NULL,
	PRIMARY KEY (UserName, RoleId) 
);

CREATE TABLE menuElement (
	menuID numeric,
	title varchar(40) NOT NULL,
	Description mediumtext NOT NULL,
	PRIMARY KEY (menuID) 
);

CREATE TABLE webPageGood (
	pageURL varchar(40),
	pageTitle varchar(40) NOT NULL,
	Description mediumtext NOT NULL,
	menuID numeric,
	PRIMARY KEY (pageURL),
	FOREIGN KEY (menuID) REFERENCES menuElement (menuID) ON DELETE CASCADE
);

CREATE TABLE RoleWebPagegood (
	RoleId varchar(20),
	pageURL varchar(20),
	dateAssign date NOT NULL,
	PRIMARY KEY (RoleId, pageURL),
	FOREIGN KEY (pageURL) REFERENCES webPageGood(pageURL) ON DELETE CASCADE,
	FOREIGN KEY (RoleId) REFERENCES ROLE(RoleId) ON DELETE CASCADE
);

CREATE TABLE webPagePrevious (
	currentpageURL varchar(40),
	previouspageURL varchar(40),
	PRIMARY KEY (currentpageURL, previouspageURL),
	FOREIGN KEY (currentpageURL) REFERENCES webPageGood(pageURL) ON DELETE CASCADE,
	FOREIGN KEY (previouspageURL) REFERENCES webPageGood(pageURL) ON DELETE CASCADE
);

Insert into menuElement values (1, "Users", "User management");
Insert into menuElement values (2, "General", "General operations");
Insert into menuElement values (0, "General pages", "General pages");

Insert into webPageGood values ("validationHashing.jsp", "Validation", " the validation page", 0);
Insert into webPageGood values ("addUser.jsp", "Add User", "This page adds users to the systems ", 1);
Insert into webPageGood values ("removeUser.jsp", "Remove User", "This page removes users to the systems", 1);
Insert into webPageGood values ("searchUser.jsp", "Search User", "This page will search for users in database", 1)
Insert into webPageGood values ("listUser.jsp", "List Users", "This page lists users to the systems", 2);
Insert into webPageGood values ("welcomeMenu.jsp", "Welcome", " the welcome page", 0);

Insert into webPAgeprevious values ("welcomeMenu.jsp", "validationHashing.jsp");
Insert into webPAgeprevious values ("addUser.jsp", "welcomeMenu.jsp");
Insert into webPAgeprevious values ("addUser.jsp", "listUser.jsp");
Insert into webPAgeprevious values ("listUser.jsp", "welcomeMenu.jsp");

INSERT INTO roleusergood value ("user1", "rol1", "2018/10/01");
INSERT INTO roleusergood value ("user2", "rol3", "2018/10/01");
INSERT INTO roleusergood value ("user3", "rol3", "2018/10/01");

Insert into roleWebPagegood values ("rol1", "addUser.jsp", "2018/10/01");
Insert into roleWebPagegood values ("rol1", "listUser.jsp", "2018/10/01");
Insert into roleWebPagegood values ("rol1", "removeUser.jsp", "2018/10/01");
Insert into roleWebPagegood values ("rol2", "listUser.jsp", "2018/10/01");
Insert into roleWebPagegood values ("rol3", "listUser.jsp", "2018/10/01");
Insert into roleWebPagegood values ("rol1", "welcomeMenu.jsp", "2018/10/01");
Insert into roleWebPagegood values ("rol2", "welcomeMenu.jsp", "2018/10/01");
Insert into roleWebPagegood values ("rol3", "welcomeMenu.jsp", "2018/10/01");
```

# Update Tables for Project

```sql
CREATE TABLE UserInformation (
  UserName VARCHAR(20) PRIMARY KEY,
  PasswordHash MEDIUMTEXT NOT NULL,
  Name VARCHAR(20) NOT NULL,
  Telephone VARCHAR(20) NOT NULL,
  dob VARCHAR(20) NOT NULL,
  gender VARCHAR(10) NOT NULL,
  email VARCHAR(100) NOT NULL
);

CREATE TABLE AddressInformation (
  UserName VARCHAR(20),
  Degree MEDIUMTEXT NOT NULL,
  School MEDIUMTEXT NOT NULL,
  Street MEDIUMTEXT NOT NULL,
  Town MEDIUMTEXT NOT NULL,
  State MEDIUMTEXT NOT NULL,
  Country MEDIUMTEXT NOT NULL,
  PRIMARY KEY (UserName),
  FOREIGN KEY (UserName) REFERENCES UserInformation(UserName)
);

CREATE TABLE Roles (
  roleID INT NOT NULL AUTO_INCREMENT,
  roleName VARCHAR(50) NOT NULL,
  description VARCHAR(255) NOT NULL,
  PRIMARY KEY (roleName)
);

INSERT INTO Roles (roleName, description) VALUES ('adminRole', 'A role for administrators');
INSERT INTO Roles (roleName, description) VALUES ('userRole', 'A role for regular users');
INSERT INTO Roles (roleName, description) VALUES ('techRole', 'A role for service technicians or tech support');


CREATE TABLE webPages (
	PageID INT AUTO_INCREMENT PRIMARY KEY,
	Page varchar(100),
    Description mediumtext
);

INSERT INTO webPages (Page, Description) VALUES ("validationHashing.jsp", "The validation page"); 
INSERT INTO webPages (Page, Description) VALUES ("addUser.jsp", "This page adds users to the system"); 
INSERT INTO webPages (Page, Description) VALUES ("removeUser.jsp", "This page removes users from the system"); 
INSERT INTO webPages (Page, Description) VALUES ("searchUser.jsp", "This page searches for users in the database"); 
INSERT INTO webPages (Page, Description) VALUES ("listUser.jsp", "This page lists users in the system"); 
INSERT INTO webPages (Page, Description) VALUES ("welcomeMenu.jsp", "The welcome page"); 
INSERT INTO webPages (Page, Description) VALUES ("uploadProfilePicture.jsp", "User page to upload profile picture upon registration");
INSERT INTO webPages (Page, Description) VALUES ("login.html", "The login page");
INSERT INTO webPages (Page, Description) VALUES ("searchUser.html", "The search user page");
INSERT INTO webPages (Page, Description) VALUES ("signup.html", "The signup page");
INSERT INTO webPages (Page, Description) VALUES ("editProfile.jsp", "The edit profile page");
INSERT INTO webPages (Page, Description) VALUES ("logout.jsp", "The logout page");
INSERT INTO webPages (Page, Description) VALUES ("homePage.html", "The home page");

CREATE TABLE roleForWebPage ( 
	ID INT NOT NULL AUTO_INCREMENT, 
	RoleID INT NOT NULL, 
	PageID INT NOT NULL, 
	PRIMARY KEY (ID), 
	FOREIGN KEY (RoleID) REFERENCES Roles(roleID), 
	FOREIGN KEY (PageID) REFERENCES webPages(PageID) 
);

INSERT INTO roleForWebPage (roleID, pageID) VALUES (3, 1);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (3, 2);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (3, 4);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (3, 5);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (3, 6);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (3, 7);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (3, 8);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (3, 9);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (3, 10);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (3, 12);


INSERT INTO roleForWebPage (roleID, pageID) VALUES (2, 1);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (2, 2);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (2, 4);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (2, 7);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (2, 8);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (2, 11);


INSERT INTO roleForWebPage (roleID, pageID) VALUES (1, 1);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (1, 2);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (1, 3);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (1, 4);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (1, 5);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (1, 6);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (1, 7);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (1, 8);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (1, 9);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (1, 10);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (1, 11);
INSERT INTO roleForWebPage (roleID, pageID) VALUES (1, 12);

CREATE TABLE webPageFlow ( 
	id INT PRIMARY KEY AUTO_INCREMENT, 
	currentPage VARCHAR(255), 
	previousPage VARCHAR(255) 
);

INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('signUp.jsp', 'welcomePage.jsp');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('uploadProfilePicture.jsp', 'signUp.jsp');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('login.jsp', 'signUp.jsp');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('homePage.html', 'login.jsp');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('logOut.jsp', 'homePage.html');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('searchUser.html', 'homePage.html');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('searchUser.jsp', 'searchUser.html');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('userOperations.html', 'homePage.html');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('editProfile.jsp', 'homePage.html');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('removeUser.jsp', 'userOperations.html');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('addUser.jsp', 'userOperations.html');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('modifyUser.jsp', 'userOperations.html');

CREATE TABLE RolesForUser (
  ID INT NOT NULL AUTO_INCREMENT,
  UserName VARCHAR(50) NOT NULL,
  roleID INT NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (UserName) REFERENCES UserInformation(UserName),
  FOREIGN KEY (roleID) REFERENCES Roles(roleID)
);

INSERT INTO RolesForUser (username, roleID) VALUES ('johndoe', 2);
INSERT INTO RolesForUser (username, roleID) VALUES ('janesmith', 3);
INSERT INTO RolesForUser (username, roleID) VALUES ('bobjohnson', 2);
INSERT INTO RolesForUser (username, roleID) VALUES ('saralee', 1);
INSERT INTO RolesForUser (username, roleID) VALUES ('tomwilson', 1);

```

# Page Flow

| Current Page | Previous Page | Explanation |
| -------------- | ---------------- | ------------- | 
| Sign Up | Welcome Page | Welcome Page tiene dos partes: Un area de Login y un area de Sign Up. |
| Upload Photo | Sign Up | Si el Usuario escoge Sign Up, pasa a la pagina donde se registra y luego sube una foto. |
| Welcome Page | Upload Photo | Despues, pasaria de nuevo a la pagina de Welcome para que haga Login despues de registrarse | 
| Login | Welcome Page | |
| Home Page | Login | Luego de hacer login, el usuario veria lo que seria el home page o pagina de inicio |
| Search Friends | Home Page | Estando el usuario en su home page, puede buscar amigos dependiendo de varios valores |
| Home Page | Search Friends | Luego de buscar amigos, debe ser capaz de voler a la pagina de home page o inicio | 
| Log Out | Home Page | En su home page, debe ser capaz de hacer Log Out |

# Database webPageGood after Page Flow

```sql
INSERT INTO webPagePrevious (currentPage, previousPage) VALUES ('signUp.jsp', 'welcomePage.jsp');
INSERT INTO webPagePrevious (currentPage, previousPage) VALUES ('uploadProfilePicture.jsp', 'signUp.jsp');
INSERT INTO webPagePrevious (currentPage, previousPage) VALUES ('login.jsp', 'signUp.jsp');
INSERT INTO webPagePrevious (currentPage, previousPage) VALUES ('login.jsp', 'uploadProfilePicture.jsp');
INSERT INTO webPagePrevious (currentPage, previousPage) VALUES ('homePage.jsp', 'login.jsp');
INSERT INTO webPagePrevious (currentPage, previousPage) VALUES ('logOut.jsp', 'homePage.jsp');
INSERT INTO webPagePrevious (currentPage, previousPage) VALUES ('searchUser.jsp', 'homePage.jsp');
INSERT INTO webPagePrevious (currentPage, previousPage) VALUES ('editProfile.jsp', 'homePage.jsp');
INSERT INTO webPagePrevious (currentPage, previousPage) VALUES ('removeUser.jsp', 'homePage.jsp');
INSERT INTO webPagePrevious (currentPage, previousPage) VALUES ('addUser.jsp', 'homePage.jsp');
INSERT INTO webPagePrevious (currentPage, previousPage) VALUES ('modifyUser.jsp', 'homePage.jsp');
```