<h1 align="center"> Learning Management System</h1>

### About
“**Learning Management System**” for the university.<br>
The application enables the administration of staff, students and university study programs.<br>
Application was implemented as a web application, which simultaneously represents the university's website.<br>
Participants in the system are students, teachers and administrators, but also unregistered users can visit the university page and forum.<br>

<hr>

### Dependencies
💻 **Front end**
- [Angular 10+](https://angular.io)

🚀 **Back end**
- [Java Spring Boot](https://spring.io/projects/spring-boot)
- [MySQL](https://www.mysql.com/)

<hr>

### Functionality overview
- **Unregistered users:**
  - They can review the university page and see basic information.
  - They can review the pages of individual faculties and see basic information.
  - They can review the study programs at the faculties, with a list subject and description of the study course, as well as data on to the head of the department.
  - They can review the forum, subforum, topics as well as posts in the topic.
  
- **Registered users:**
  - They can edit their profile.
  - They can login, as well as logout.
  
- **Students:**
  - They can review data on the subjects they are currently listening to.
  - They can review notifications for the items they are currently listening to.
  - They can review their study history, including previous passed subjects, collected points and grade for each subject.
  
- **Teachers:**
  - They can review course data, edit the syllabus, manage notifications, view the student list and enter grades for subjects they are currently engaged in.
  - They can make a schedule of outcomes by term. Every term has an outcome, that is, the topic for that term.
  - They can define evaluation instruments (project tasks, tests and colloquium assignments).
  
- **Administrators:**
  - They can administer registered users, study programs, and organization.
  - They can are adding teachers, administrative staff and students.
  
<hr>

### Little preview
<p align="center">
<kbd><img src="https://user-images.githubusercontent.com/87083680/193859384-a67977db-2e2d-48c3-9a97-1f5e194ffd9d.png"></kbd><hr>
<kbd><img src="https://user-images.githubusercontent.com/87083680/193859342-00cb6f9e-cd3e-44f3-9f9a-88d48947bf9c.png"></kbd><hr>
<kbd><img src="https://user-images.githubusercontent.com/87083680/193859349-f021b10f-4c95-4ba4-9c54-6594d514dccf.png"></kbd><hr>
<kbd><img src="https://user-images.githubusercontent.com/87083680/193859351-f438913f-b691-424c-8399-4b4d8da4fd0e.png"></kbd><hr>
<kbd><img src="https://user-images.githubusercontent.com/87083680/193859352-cf853274-624c-48bb-9f26-e28a7bac9cdf.png"></kbd><hr>
<kbd><img src="https://user-images.githubusercontent.com/87083680/193859353-7917b6ff-722e-45ac-a463-c0b9bef4e023.png"></kbd><hr>
<kbd><img src="https://user-images.githubusercontent.com/87083680/193859355-695d3084-3847-4f61-ab21-01941b6d1c3f.png"></kbd><hr>
<kbd><img src="https://user-images.githubusercontent.com/87083680/193859365-d1a81a11-ca04-4123-bc72-71ef64b9129e.png"></kbd><hr>
<kbd><img src="https://user-images.githubusercontent.com/87083680/193859381-9b6d1bbd-f344-4e40-b892-591598d118b7.png"></kbd><hr>
<kbd><img src="https://user-images.githubusercontent.com/87083680/193859376-9cffa29f-0ce7-463e-b827-56879eba75d0.png"></kbd><hr>
<kbd><img src="https://user-images.githubusercontent.com/87083680/193859379-aad600a3-52c5-4538-a98c-9cc3b9092cbd.png"></kbd><hr>
<kbd><img src="https://user-images.githubusercontent.com/87083680/193859367-7977d485-a5ad-4a74-bcd9-1e302aedfb30.png"></kbd><hr>
<kbd><img src="https://user-images.githubusercontent.com/87083680/193859374-9165cb51-e562-42c6-93c0-b7b47fa1987f.png"></kbd><hr>
<kbd><img src="https://user-images.githubusercontent.com/87083680/193859373-ed33e427-d5de-4cef-9490-48ad8435e565.png"></kbd><hr>
<kbd><img src="https://user-images.githubusercontent.com/87083680/193859363-80adf75c-e2cd-4aee-93ca-df137a117603.png"></kbd><hr>
<kbd><img src="https://user-images.githubusercontent.com/87083680/193859360-d3346df1-bfd4-44fd-91f6-bcf684e70732.png"></kbd><hr>
<kbd><img src="https://user-images.githubusercontent.com/87083680/193859358-abe4d9ea-98f1-46ab-8d9d-4ebaa70780dc.png"></kbd><hr>
<kbd><img src="https://user-images.githubusercontent.com/87083680/193859383-4e4a213e-3002-471a-bc08-21bb04f94fa1.png"></kbd><hr>
</p>

**This is just a small preview of the app.**

<hr>

### How to build?

**1. Open CMD**
> Windows (WinKey + R) > enter 'cmd' > navigate to the folder where you want to place the project

or

> Hold Shift + Right-click > Open CMD or PowerShell window here

**2. Clone**
> git clone https://github.com/brankomilovanovic/Learning-Management-System

**3. Create Database**
> Open [MySQL Workbench](https://www.mysql.com/products/workbench) > Create Schema > lms-database

**4. Start back end**
> Open [EclipseIDE](https://www.eclipse.org/ide) > File > Import > Maven > Existing Maven Projects > Root Directory (path to the cloned directory) > Finish

> Expand project and find App.java class > Right-Click on App.java > Run As > Java Application

**5. Front end**
> Open [Visual Studio Code](https://code.visualstudio.com) > File > Open Folder > Find the cloned directory, and select the client folder.

> Terminal > New Terminal > Set the path to the client folder, it is set automatically by default. > input into the console **npm install** > input into the console **ng serve -o**

