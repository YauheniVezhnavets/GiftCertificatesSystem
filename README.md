Task HTML/CSS
In this task, you need to create basic pages for your future web application. All pages must be created using plain HTML and CSS. Styles that are similar between the pages might be extracted into separate files in order to avoid copy-paste.

Task js

Task 1
install Node.js
You are able to work in any IDE, you want, but we suggest you to use WebStorm or VS code.
Before implementation create empty project and copy a practical task from this module js_practical_task.js in it (or you can fork this repository). You can choose how to proof, that your solution works by yourself. (For example: to cover methods with unit tests. this item will be scored additionally)
Task 2
Based on a previously created HTML template you need to add some interactive elements to your page:

Infinite items (certificates) scroll
Search by name, description and tags
Scroll back to top button
Feature to return to the last scroll position on the page
Application requirements
Save all data in the local storage
Data is ordered by creation date by default, newly added item should be on the top
Perform search by clicking on the tag or when user stop typing in the input
DOM manipulation should be performed on native js
Use _.debounce() or _.throttle() for scroll event handler to optimize your api requests in the future

Task angular

Task
Angular Application structure and CLI
Download and install Angular CLI
Create basic application structure using ng new [your app name] command
Run application using ng serve command
Install Angular Material package in your project
Module
Declare application modules
Create Shared module for reusable common components and services
Import Angular Material module in your aplication
Component and Directive
Create Page components
Destructure page elements onto separate sub components
Use Structural and Attribute directives (i.e. *ngIf, *ngFor, [ngClass]) in your components
Use component Inputs and Outputs to pass data between components
Use Reactive forms for user input
Use Material components if needed
Service and DI
Create service to access API
Create singleton service
Pipe
Create a custom pipe to format price
Application Routing
Create routing module
Pass Item Id as url parameter for Item Details page (i.e. /product/22/details)
Make some modules Lazy Loaded
Rxjs basics
Learn RxJs basics
Use async pipe in your component template

Task TypeScript
Install npm
Set npm npm install -g typescript
Create folder
Create index.html
Create app.ts
Create tsconfig.json (tsconfig.json)
To connect app.js to index.html (For example, <script src=”app.js”></script>)
Run project in terminal tsc fileName.ts
Open path to index.html in the browser (must run tsc fileName.ts in the terminal after each update)
Create a EuropeCompany project. a. Create the class Employee. This class should consist of: • getCurrentProject - method for getting the name of the current project; • getName – method for getting the name of the employee; b. Create the class Company. This class should consist of: • an array of the Employees added to the company; • add - method to add a new Employee to the company; • getProjectList - method to get list of employee’s projects; • getNameList - method to get the list of names by added Employees. c. Create 2 additional classes (Frontend, Backend) which extend Employee class. d. Create an object of class Company. e. Create several objects Frontend and Backend employees with information about their names and projects and add them to the company, display the result of the getProjectList and getNameList methods in the console.
Create an AmericanCompany project based on the EuropeCompany project. a. Let’s update our project. b. Create IEmployee (with methods getCurrentProject and getName) interface instead of using Employee class. After that, implement this interface in Frontend and Backend classes. c. Create several objects of Frontend and Backend employees with information about their names and projects and add them to the Company, display the result of the getProjectList and getNameList methods in the console.
Create a BritishCompany project based on the EuropeCompany project. a. The Company class must be parameterized by Location — the location of the company’s office. Create ILocation interface with the following methods: • addPerson - method which adds a person; • getPerson - method for getting a person by index; • getCount - method of counting the number of employees; b. Location should implement the ILocation interface. c. Create 2 classes with different locations: • CompanyLocationArray class which implements ILocation interface - for storage in Array ; • CompanyLocationLocalStorage class which implements ILocation interface - for storage in localStorage. d. Update class Company with using Location. e. Remove Frontend and Backend classes. f. Create several Companies with different locations. g. Add several employees to each company. h. Display the results of the getProjectList and getNameList methods in the console. i. Do not create Frontend and Backend employees, just work with the Employee class. j. The Employee class does not use public properties, only public methods.