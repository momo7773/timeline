# timeline
 software testing lab2

frontend: implemented in vue framework
backend: implemented in springboot framework
database part: use jpaRepository

to run the project: enter the frontend directory, run 'yarn start' command in terminal
                    use idea import the backend spring project to start.
                    
Testfile: backend\Time-master\src\test 
test tool: mockito + junit + jacoco
mockito & mockMvc: successfully mimic the http request and mock the sequential data needed to response(use mockbean to mock the repository)
the output report of jacoco is in the directory: \backend\Time-master\target. achieved 100% line coverage.
