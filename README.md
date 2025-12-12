# Aegis: A Campus Safety Companion
Aegis is a console-based Java application that enhances campus safety for UTA students. It allows users to request walking companions, report non-emergency hazards, and access emergency guidance. The system simulates a real campus-safety workflow using UTA credential verification, role-based dashboard, and backend logic that can later be expanded into a GUI or mobile app.

### Project Description 
Aegis promotes campus safety by giving students a structured way to request help and report hazards. The system features:
a)	Allows students to request a walking companion from a pool of screened volunteers.
b)	Hazard Reporting for non-emergency issues.
c)	Provides emergency guidance and escalation when keywords indicate urgent danger

To achieve these goals, the project is divided into several logical phases:

i.	User Account and Role Management
Users can login or signup as student, companion, or admin.
ii.	Core Student Features
Companion requests, virtual check-ins, hazard reporting, and emergency handling are implemented in the Student class.
iii.	Companion and Admin Workflows
Companions can track availability, training, approval status, and completed walks. Admins are responsible for reviewing applications, viewing hazard reports, and intervening when needed. 
iv.	Matching and Hazard Management
The CentralDatabase: submit and manage SafetyRequest and hazardReport objects by implementing matching algorithm (student requests with companions). Hazard reports are stored and can be reviewed, escalated, or updated in status.

### Project Architecture
The Aegis system follows a modular, layered architecture:
a)	Presentation / Interaction Layer
o	AegisMain: Manages startup and role selection.
o	LogIn: handles account creation, authentication, and file I/O.
b)	Domain / Business Logic Layer
o	User (abstract class): Common parent for all user types, encapsulating core attributes, and abstract methods.
o	Student, Companion, and Admin extend User with role-specific behavior.
c)	Data Management Layer
o	CentralDatabase: Manages in-memory maps for users, hazard reports, and safety requests, plus the companion-matching algorithm.
d)	Supporting Models
o	SafetyRequest, HazardReport, and enums like HazardType and CampusLocations.

### OOP Concepts Used
a)	Classes and Objects: The system is built around well-defined classes such as User, Student, Companion, Admin, CentralDatabase, SafetyRequest, and hazardReport.
b)	Encapsulation: Each class hides its internal data using private fields and exposes only necessary getters, setters, and public methods.
c)	Inheritance: Abstract User class is the parent of Student, Companion, and Admin. Common properties and behaviors (e.g., name, email, MAVID, gender, basic setters) are defined once in User, while each subclass extends with additional fields and role-specific methods.
d)	Abstraction: Abstract methods viewDashboard(), chooseAction(), and EmergencyIntervention() in the User class; each subclass provides its own implementation
e)	Polymorphism: Because Student, Companion, and Admin all extend User, the program can use polymorphism to treat them as User references while still calling overridden methods.
f)	Enums and Type Safety: The HazardType and CampusLocations enums for type-safe input.
g)	Collections and Data Structures: The CentralDatabase class uses Map<String, User>, Map<String, hazardReport>, and Map<String, SafetyRequest> to store and retrieve objects.
h)	File I/O and Exception Handling: The LogIn class: BufferedReader and BufferedWriter to read and write files. I/O operations are wrapped in try–catch blocks to handle IOException.

### Future Enhancements
a)	Real-time notifications could be added so that companions and admins receive alerts instantly. 
b)	Storage of such sensitive user data could be moved from text files to a relational database.
c)	GPS integration, push notifications, and a richer admin dashboard with analytics would make the system more powerful and closer to a production-level campus safety application.
d)	A graphical user interface or a mobile app front-end could replace the console, making the system more user-friendly.

