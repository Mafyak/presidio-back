# Presidio Test Task Back-end

Back-end uses in-memory H2 db, which starts empty, gets filled with 2 users via sql script and then some fake data is being generated via Faker library.\

Front-end is based on React JS. I used one of the themes I had from my somewhere, hopefully that's ok.

##### User Login: `user@me.com`, pass: `1` 
##### Admin Login: `admin@me.com`, pass: `1` 

UI for local env: [http://localhost:3000](http://localhost:3000).\
REST for local env: [http://localhost:9000](http://localhost:9000).


### To start back-end
1) mvn clean install
2) java -jar service\target\service-0.0.1-SNAPSHOT.jar

### To start front-end
1) npm install
2) npm start
