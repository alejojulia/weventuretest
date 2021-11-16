# How to launch the app
Build the app on your favorite IDE using maven install and then doble click on the .jar file located in /target
(make a maven clean if needed)
You should know be able to use the app on your favorite browser accessing by URL like : http://localhost:8080/display
(please check your local configuration to resolve any port comptability issue).

# How to use it

## Request availables
../display?userId=<user_id>&year=<year>&month=<month>&day=<day>
../add?userId=<user_id>&food=<food_name>&portion=<portion>&year=<year>&month=<month>&day=<day>
../delete?userId=<user_id>&food=<food_name>
