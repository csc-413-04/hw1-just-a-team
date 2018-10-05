const fs = require('fs');
const async = require('async');
const axios = require('axios');

const rand = (max) => {
    return Math.floor(Math.random() * (max - 1 + 1)) + 1;
}

const checkData = () => {
    fs.readFile('./data.json', (err, file) => {
        if (err) throw err;
        const data = JSON.parse(file);
        console.log('/user');
        console.log({
            data: data.users,
            status: "OK",
            entries: data.users.length,
        });
        const userid = 0;
        console.log(`/user?userid=${userid}`);
        console.log({
            data: data.users,
            status: "OK",
            entries: data.users.length,
        });
        const postid = 0;
        console.log(`/posts?postid=${postid}`);
        console.log({
            data: data.users,
            status: "OK",
            entries: data.users.length,
        });
        const maxLength = 3;
        console.log(`/posts?postid=${postid}&maxlength=${maxLength}`);
    });
};

const checkHost = (host, verbose = true) => {
    console.log(`testing endpoints at ${host}`);
    fs.readFile('./data.json', (err, file) => {
        if (err) {
            console.log(err);
            return;
        }
        const data = JSON.parse(file);
        const userMap = {};
        const postMap = {};
        data.users.forEach((user) => {
            userMap[user.userid] = user;
        });
        data.posts.forEach((post) => {
            postMap[post.postid] = post;
        });
        let score = 100;
        async.waterfall([
            (callback) => {
                const endpoint = `http://${host}/user`;
                console.log(`testing ${endpoint}`);
                const answer = {
                    data: data.users,
                    status: "OK",
                    entries: data.users.length,
                };
                if (verbose) {
                    console.log('expecting:');
                    console.log(JSON.stringify(answer, null, 2))
                }
                axios.get(endpoint)
                    .then((response) => {
                        console.log(`received response from endpoint: ${endpoint}`);
                        if (JSON.stringify(answer) === JSON.stringify(response.data)) {
                            console.log(`${endpoint} is correct`);
                        } else {
                            console.warn(`${endpoint} is incorrect`);
                            score -= 15;
                        }
                        callback();
                    })
                    .catch((e) => {
                        console.warn(`failed ${endpoint}`);
                        score -= 15;
                        callback();
                    });
            },
            (callback) => {
                const randUser = rand(data.users.length - 1);
                const endpoint = `http://${host}/user?userid=${randUser}`;
                console.log(`testing ${endpoint}`);
                const answer = {
                    data: [userMap[randUser]],
                    status: "OK",
                    entries: 1,
                };
                if (verbose) {
                    console.log('expecting:');
                    console.log(JSON.stringify(answer, null, 2))
                }
                axios.get(endpoint)
                    .then((response) => {

                        console.log(`received response from endpoint: ${endpoint}`);
                        if (JSON.stringify(answer) === JSON.stringify(response.data)) {
                            console.log(`${endpoint} is correct`);
                        } else {
                            console.warn(`${endpoint} is incorrect`);
                            score -= 15;
                        }
                        callback();
                    })
                    .catch((e) => {
                        console.warn(`failed ${endpoint}`);
                        score -= 15;
                        callback();
                    });
            },
            (callback) => {
                const randPost = rand(data.posts.length - 1);
                const endpoint = `http://${host}/posts?postid=${randPost}`;
                console.log(`testing ${endpoint}`);
                const answer = {
                    data: [postMap[randPost]],
                    status: "OK",
                    entries: 1,
                };
                if (verbose) {
                    console.log('expecting:');
                    console.log(JSON.stringify(answer, null, 2))
                }
                axios.get(endpoint)
                    .then((response) => {
                        console.log(`received response from endpoint: ${endpoint}`);
                        if (JSON.stringify(answer) === JSON.stringify(response.data)) {
                            console.log(`${endpoint} is correct`);
                        } else {
                            console.warn(`${endpoint} is incorrect`);
                            score -= 15;
                        }
                        callback();
                    })
                    .catch((e) => {
                        console.warn(`failed ${endpoint}`);
                        score -= 15;
                        callback();
                    });
            },
            (callback) => {
                const randPost = rand(data.posts.length - 1);
                const maxLength = rand(40);
                const query = [
                    `postid=${randPost}`,
                    `maxlength=${maxLength}`,
                ];
                if (Math.round(Math.random())) query.reverse();
                const endpoint = `http://${host}/posts?${query.join('&')}`;
                console.log(`testing ${endpoint}`);
                const post = postMap[randPost];
                const postData = (post.data.length <= maxLength) ? [post] : [];
                const answer = {
                    data: postData,
                    status: "OK",
                    entries: postData.length,
                };
                if (verbose) {
                    console.log('expecting:');
                    console.log(JSON.stringify(answer, null, 2))
                }
                              
                axios.get(endpoint)
                    .then((response) => {
                        console.log(`received response from endpoint: ${endpoint}`);
                        if (JSON.stringify(answer) === JSON.stringify(response.data)) {
                            console.log(`${endpoint} is correct`);
                        } else {
                            console.warn(`${endpoint} is incorrect`);
                            score -= 15;
                        }
                        callback();
                    })
                    .catch((e) => {
                        console.warn(`failed ${endpoint}`);
                        score -= 15;
                        callback();
                    });
            }
        ],
            () => {
                console.log(`score is ${score}`);
            });
    });
};

module.exports = { checkData, checkHost };
