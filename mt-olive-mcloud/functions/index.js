const functions = require('firebase-functions');
const express = require('express');
const path = require('path');
const server = express();


exports.httpReq = functions.https.onRequest(server)

server.get('/',(req,res)=>{
    res.sendFile(path.join(__dirname,'/public/index.html'));
})