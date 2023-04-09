CREATE DATABASE "social_network";

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
  PRIMARY KEY (roleID)
);

CREATE TABLE menuElement (
	menuID numeric,
	title varchar(40) NOT NULL,
	Description mediumtext NOT NULL,
	PRIMARY KEY (menuID) 
);

CREATE TABLE webPages (
	Page varchar(40),
	Description mediumtext NOT NULL,
	menuID numeric,
	PRIMARY KEY (Page),
	FOREIGN KEY (menuID) REFERENCES menuElement (menuID)
);

CREATE TABLE roleForWebPage ( 
	ID INT NOT NULL AUTO_INCREMENT, 
	RoleID INT NOT NULL, 
	Page VARCHAR(40) NOT NULL, 
	PRIMARY KEY (ID), 
	FOREIGN KEY (RoleID) REFERENCES Roles(roleID), 
	FOREIGN KEY (Page) REFERENCES webPages(Page) 
);

CREATE TABLE webPageFlow ( 
	id INT PRIMARY KEY AUTO_INCREMENT, 
	currentPage VARCHAR(255), 
	previousPage VARCHAR(255) 
);

CREATE TABLE RolesForUser (
  ID INT NOT NULL AUTO_INCREMENT,
  UserName VARCHAR(50) NOT NULL,
  roleID INT NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (UserName) REFERENCES UserInformation(UserName),
  FOREIGN KEY (roleID) REFERENCES Roles(roleID)
);

CREATE TABLE picturesForUser (
	PicturePath mediumtext NOT NULL PRIMARY KEY,
	UserName VARCHAR(50) NOT NULL,
	FOREIGN KEY (UserName) REFERENCES UserInformation(UserName)
);
INSERT INTO Roles (roleName, description) VALUES ('adminRole', 'A role for administrators');
INSERT INTO Roles (roleName, description) VALUES ('userRole', 'A role for regular users');
INSERT INTO Roles (roleName, description) VALUES ('techRole', 'A role for service technicians or tech support');

Insert into menuElement values (1, "Users", "User management");
Insert into menuElement values (2, "General", "General operations");
Insert into menuElement values (0, "General pages", "General pages");

INSERT INTO webPages (Page, Description, menuID) VALUES ("validationHashing.jsp", "The validation page", 0); 
INSERT INTO webPages (Page, Description, menuID) VALUES ("addUser.jsp", "This page adds users to the system", 1); 
INSERT INTO webPages (Page, Description, menuID) VALUES ("removeUser.jsp", "This page removes users from the system", 1); 
INSERT INTO webPages (Page, Description, menuID) VALUES ("searchUser.jsp", "This page searches for users in the database", 1); 
INSERT INTO webPages (Page, Description, menuID) VALUES ("uploadProfilePicture.html", "User page to upload profile picture upon registration", 1);
INSERT INTO webPages (Page, Description, menuID) VALUES ("login.html", "The login page",1);
INSERT INTO webPages (Page, Description, menuID) VALUES ("searchUser.html", "The search user page", 2);
INSERT INTO webPages (Page, Description, menuID) VALUES ("signup.html", "The signup page",0);
INSERT INTO webPages (Page, Description, menuID) VALUES ("signout.jsp", "The logout page",1);
INSERT INTO webPages (Page, Description, menuID) VALUES ("homePage.html", "The home page",1);
INSERT INTO webPages (Page, Description, menuID) VALUES ("adminAddUser.html", "Administrator Page to Add User", 1);
INSERT INTO webPages (Page, Description, menuID) VALUES ("modifyUser.html", "This page adds users to the system", 1); 
INSERT INTO webPages (Page, Description, menuID) VALUES ("editProfile.html", "Edit user Profile", 1);

INSERT INTO roleForWebPage (roleID, page) VALUES (1, "removeUser.jsp");
INSERT INTO roleForWebPage (roleID, page) VALUES (1, "modifyUser.html");
INSERT INTO roleForWebPage (roleID, page) VALUES (1, "searchUser.html");
INSERT INTO roleForWebPage (roleID, page) VALUES (1, "searchUser.html");
INSERT INTO roleForWebPage (roleID, page) VALUES (1, "uploadProfilePicture.html");
INSERT INTO roleForWebPage (roleID, page) VALUES (1, "adminAddUser.html");
INSERT INTO roleForWebPage (roleID, page) VALUES (1, "editProfile.html");
INSERT INTO roleForWebPage (roleID, page) VALUES (2, "editProfile.html");
INSERT INTO roleForWebPage (roleID, page) VALUES (2, "signout.jsp");
INSERT INTO roleForWebPage (roleID, page) VALUES (2, "uploadProfilePicture.html");
INSERT INTO roleForWebPage (roleID, page) VALUES (2, "searchUser.html");
INSERT INTO roleForWebPage (roleID, page) VALUES (3, "editProfile.html");
INSERT INTO roleForWebPage (roleID, page) VALUES (3, "signout.jsp");
INSERT INTO roleForWebPage (roleID, page) VALUES (3, "adminAddUser.html");
INSERT INTO roleForWebPage (roleID, page) VALUES (3, "uploadProfilePicture.html");
INSERT INTO roleForWebPage (roleID, page) VALUES (3, "searchUser.html");

INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('uploadProfilePicture.jsp', 'signUp.jsp');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('homePage.jsp', 'validationHashing.jsp');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('signout.jsp', 'homePage.jsp');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('searchUser.jsp', 'homePage.jsp');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('modifyUser.jsp', 'homePage.jsp');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('removeUser.jsp', 'homePage.jsp');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('addUser.jsp', 'homePage.jsp');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('editProfile.jsp', 'homePage.jsp'); 
