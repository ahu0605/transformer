var setting={
    "port": 8080,
    "debugLogFilePath": "./log/debug.log",
    "traceLogFilePath": "./log/trace.log",
    "errorLogFilePath": "./log/error.log",
    "cpuNum":1,
    "debug":true,
    "zookeeper":"",
    "context":"/public"
}
var log4js = require('log4js');
log4js.configure({
    appenders: [
        {type: 'console'},
        {type: 'dateFile', filename: setting.debugLogFilePath, 'pattern': '-yyyy-MM', category: 'debug'},
        {type: 'dateFile', filename: setting.traceLogFilePath, 'pattern': '-yyyy-MM-dd', category: 'trace'},
        {type: 'file', filename: setting.errorLogFilePath, maxLogSize: 1024000, backups: 10, category: 'error'}
    ],
    replaceConsole: true
});
exports.debugLogger = log4js.getLogger('debug');
exports.traceLogger = log4js.getLogger('trace');
exports.errorLogger = log4js.getLogger('error');
exports.setting = setting;