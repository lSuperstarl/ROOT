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

Insert into menuElement values (1, "Users", "User management");
Insert into menuElement values (2, "General", "General operations");
Insert into menuElement values (0, "General pages", "General pages");

INSERT INTO webPages (Page, Description, menuID) VALUES ("validationHashing.jsp", "The validation page", 0); 
INSERT INTO webPages (Page, Description, menuID) VALUES ("addUser.jsp", "This page adds users to the system", 1); 
INSERT INTO webPages (Page, Description, menuID) VALUES ("searchUser.jsp", "This page searches for users in the database", 2);
INSERT INTO webPages (Page, Description, menuID) VALUES ("signout.jsp", "The logout page", 2);
INSERT INTO webPages (Page, Description, menuID) VALUES ("uploadProfilePicture.jsp", "User page to upload profile picture upon registration", 1);

INSERT INTO Roles (roleName, description) VALUES ('adminRole', 'A role for administrators');
INSERT INTO Roles (roleName, description) VALUES ('userRole', 'A role for regular users');
INSERT INTO Roles (roleName, description) VALUES ('techRole', 'A role for service technicians or tech support');

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

# Current
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('signup.html', 'homePage.jsp');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('uploadProfilePicture.jsp', 'signUp.jsp');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('login.html', 'signUp.jsp');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('validationHashing.jsp', 'login.html');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('homePage.jsp', 'validationHashing.jsp');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('signout.jsp', 'homePage.jsp');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('searchUser.html', 'homePage.jsp');
INSERT INTO webPageFlow (currentPage, previousPage) VALUES ('searchUser.jsp', 'searchUser.html');
# Current

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

INSERT INTO webPages (Page, Description) VALUES ("validationHashing.jsp", "The validation page"); 
INSERT INTO webPages (Page, Description) VALUES ("addUser.jsp", "This page adds users to the system"); 
INSERT INTO webPages (Page, Description) VALUES ("searchUser.jsp", "This page searches for users in the database");
INSERT INTO webPages (Page, Description) VALUES ("uploadProfilePicture.jsp", "User page to upload profile picture upon registration");
INSERT INTO webPages (Page, Description) VALUES ("signout.jsp", "The logout page");

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