var express = require('express');
var log4js = require('log4js');
var config = require('./config');
var path = require('path');
var favicon = require('serve-favicon');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var traceLogger = config.traceLogger;
var app = express();
var user = require('./router/user');
var zookeeper = require('./zookeeper');

//配置node服务器端日志
app.use(log4js.connectLogger(traceLogger, {level: log4js.levels.DEBUG}));

//cookie设置，服务器端可以直接读前端本地cookie
//请求接收json请求
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
//url编码
app.use(cookieParser());

app.use(config.setting.context+'/static', express.static(__dirname+'/static'));
app.use(config.setting.context+'/user', user);

app.get(config.setting.context+'/', function (req, res) {
  res.sendFile( __dirname + '/index.html');
});

zookeeper.init();	

/*function errorHandler(err, req, res, next) {
  if (res.headersSent) {
    return next(err);
  }
  res.status(500);
  res.render('error', { error: err });
}

app.use(errorHandler);*/

app.listen(config.setting.port);