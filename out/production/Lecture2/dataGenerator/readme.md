# How to run grader
1. Install node.js then run `npm install` from directory
2. From directory `node main.js -g <size>`
    * defaults to 50
    * Generates new dummy data file
3. Either copy the new data or change the directory in your project to point to new file 
4. Run `node main.js -c <host>` to check endpoints
    * Defaults to localhost:1299
    * Pass new host:port combo 
    * will tell you what answer it is looking for
### Notes
* Order shouldn't mater in answers
* Order doesn't matter in endpoints
* We will pass it one garbage endpoint to check and see if it can handle errors
* Message me on slack if you need help running it, or you think the answer is correct and it is marking it as wrong. Also let me know if you need any improvments in the grader.