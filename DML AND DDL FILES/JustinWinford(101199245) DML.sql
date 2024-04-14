-- Insert LoginInfo for a member
INSERT INTO LoginInfo (Username, Password, Role)
VALUES ('m', '1', 'member');

-- Insert LoginInfo for a trainer
INSERT INTO LoginInfo (Username, Password, Role)
VALUES ('j', '1', 'trainer');

-- Insert a Trainer
INSERT INTO Trainer (TrainerID, Username, Name, PhoneNumber, Email, Address, Certifications)
VALUES ('T1','j', 'Jimmy', '123-456-7890', 'trainer@example.com', '123 Main St','Certification 1');

-- Insert a Member
INSERT INTO Member (MemberID, Username, Name, Phone, Email, Address,weightgoal,weeklyvisitgoal,weight,height,exerciseroutine)
VALUES ('M1', 'm', 'John Doe', '123-416-7890', 'john.doe@example.com', '123 Main St',150.00,3,175.00,182.10,'Pull ups 3x10, Deadlift 3x5');

-- Insert a Session
INSERT INTO PersonalSession (SessionID, TrainerID, MemberID, Date, Time, Duration, Cost)
VALUES ('S1','T1', 'M1', '2024-04-15', '10:00:00', 60, 50.00);

-- Insert a Room
INSERT INTO Room (RoomID, Name, Capacity, DayBooked,TimeSlot)
VALUES ('R1','SpinClass', '15', '2024-04-15', 10);

-- Insert a Class
INSERT INTO Class (ClassID, RoomID, InstructorID,Name,Description, Day, Time, Cost, Participants)
VALUES ('C1','R1', 'T1', 'Cycling', 'Ladies only spin class', 'Monday', 10, 50.00, 'j');