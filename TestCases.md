Senior Test Automation Engineer Task
1.Overview and suggestions:
- The Home page is not intuitive, it is clumsy with lots of information mainly in the mouse hover areas like holiday travel, last minute, flight etc. and they are too bright which is not attractive. If the home page design is similar to the getaway website - https://kurzurlaub.ab-in-den-urlaub.de/, it would be even better
- There is no English language option, if it's implemented it would attract more customers worldwide
- Implementing chat bots for unique customer experience
- Providing testimonials or feedback from the customers who already booked in our website will create trust and urge the new customers to make bookings
-	Needs to improve the design of the webpage by making it more interactive and user friendly

2.Automated Test:
- I have developed the test scripts with Java, Selenium, Page Factory, JUnit, Cucumber(BDD) and used Maven(pom.xml) for handling dependencies.
- Please import the dependencies and you can run the script from TestRunner.java class or by using maven -clean -install
- Once the execution is completed, you can view the cucumber reports in output/cucumber-pretty/index.html

3. Other Tests:
Regression Test:
- All the basic functionalities/actions like package, holiday travel, flight, apartment etc. needs to be added to regression scripts
- For each functionality, creating a booking and payment actions are needs to be added
- User registration and Registered User actions like Wishlist, Retrieve, Edit and Cancel booking actions needs to be added

Performance Test:
- Need to check the time taken for the portal to retrieve response for the each actions performed in the portal which also includes response time for page navigation, offers retrieval, applying filters etc.

Security Test:
- Need to check the vulnerabilities related to areas such as payments, unauthorised user access and handling user's pesonal data

Load Test:
- Need to validate the response of the entire application when it is accessed by large volume of consumers at a time which helps to find the bottleneck and it is most essential for publishing online discount sale during Christmas, Black Friday etc.