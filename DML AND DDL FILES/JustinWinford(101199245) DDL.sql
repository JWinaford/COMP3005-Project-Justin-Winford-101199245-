CREATE TABLE LoginInfo (
  Username VARCHAR(50) PRIMARY KEY,
  Password VARCHAR(255) NOT NULL,
  Role VARCHAR(20) NOT NULL
);

CREATE TABLE AdminStaff (
  AdminID VARCHAR(20) PRIMARY KEY,
  Username VARCHAR(50) NOT NULL,
  FOREIGN KEY (Username) REFERENCES LoginInfo(Username),
  Name VARCHAR(255) NOT NULL,
  Phone VARCHAR(20),
  Email VARCHAR(50),
  Address VARCHAR(255) NOT NULL
);

CREATE TABLE Room (
  RoomID VARCHAR(20) PRIMARY KEY,
  Name VARCHAR(255) NOT NULL,
  Capacity INT NOT NULL,
  DayBooked VARCHAR(10) NOT NULL, 
  TimeSlot VARCHAR(10) NOT NULL
);

CREATE TABLE Member (
  MemberID VARCHAR(20)PRIMARY KEY,
  Username VARCHAR(50) NOT NULL,
  FOREIGN KEY (Username) REFERENCES LoginInfo(Username),
  Name VARCHAR(255) NOT NULL,
  Phone VARCHAR(20) NOT NULL,
  Email VARCHAR(50) NOT NULL UNIQUE,  -- Enforce unique emails
  Address VARCHAR(255) NOT NULL,
  WeightGoal DECIMAL(5,2) DEFAULT NULL,
  WeeklyVisitGoal INT DEFAULT NULL,
  Weight DECIMAL(5,2) DEFAULT NULL,
  Height DECIMAL(3,2) DEFAULT NULL,
  ExerciseRoutine TEXT
);

CREATE TABLE Payment (
  PaymentID VARCHAR(20) PRIMARY KEY,
  Payee VARCHAR(20) NOT NULL,
  Amount DECIMAL(10,2) NOT NULL,
  Date DATE NOT NULL,
  FOREIGN KEY (Payee) REFERENCES Member(MemberID)  -- Foreign key to Member table
);

CREATE TABLE Trainer (
  TrainerID VARCHAR(20) PRIMARY KEY,
  Username VARCHAR(50) NOT NULL,
  FOREIGN KEY (Username) REFERENCES LoginInfo(Username),
  Name VARCHAR(255) NOT NULL,
  PhoneNumber VARCHAR(20) NOT NULL,
  Email VARCHAR(50) NOT NULL UNIQUE,  -- Enforce unique emails
  Address VARCHAR(255) NOT NULL,
  Certifications TEXT
);

CREATE TABLE Class (
  ClassID VARCHAR(20) PRIMARY KEY,
  RoomID VARCHAR(20) REFERENCES Room(RoomID),  -- Foreign key to Room table
  InstructorID VARCHAR(20) REFERENCES Trainer(TrainerID),  -- Foreign key to Trainer table
  Name VARCHAR(255) NOT NULL,
  Description TEXT,
  Day VARCHAR(10) NOT NULL,  -- Weekday (Monday, Tuesday, etc.)
  Time VARCHAR(10) NOT NULL,  -- Time (e.g., 09:00-10:00)
  Cost DECIMAL(10,2) NOT NULL,
  Participants TEXT
);

CREATE TABLE PersonalSession (
  SessionID VARCHAR(20) PRIMARY KEY,
  TrainerID VARCHAR(20) REFERENCES Trainer(TrainerID),  -- Foreign key to Trainer table
  MemberID VARCHAR(20) REFERENCES Member(MemberID) DEFAULT NULL,  -- Foreign key to Member table
  Date DATE NOT NULL,
  Time VARCHAR(10) NOT NULL,  -- Time (e.g., 09:00-10:00)
  Duration INT NOT NULL,  -- Duration in minutes
  Cost DECIMAL(10,2) NOT NULL
);

CREATE TABLE Equipment (
  EquipmentID VARCHAR(20) PRIMARY KEY,
  Name VARCHAR(255) NOT NULL,
  MaintenanceLog TEXT  -- Consider a separate table for detailed maintenance logs
);

