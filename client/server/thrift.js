var thrift = require('thrift');
var transport = thrift.TBufferedTransport;
var protocol = thrift.TMultiplexProtocol;
var expire = 1000;

var ThriftService  = function(service){
	this.service = service;
	this.connection = null;
	this.start = new Date().getTime(); 
	
}

ThriftService.addressPool=[];

ThriftService.prototype = {
	"address":function(str){
		ThriftService.addressPool.push(str);
	},
	"clear":function(){
		ThriftService.addressPool=[];
	},
	"getRemote":function(){
		 let _this = this;
		 var promise =  new Promise (function(resolve, reject){
					buildConnect(_this,resolve,reject);		
		 });
		 
		 return promise //return service
	},
	"end":function(){
		let _this = this;
		if(_this.connection)
			_this.connection.end();
	},
	
}

function buildConnect(thriftService,resolve,reject) {
	  console.log("buildConnect length",ThriftService.addressPool.length)
    if(ThriftService.addressPool.length<1){
		
			var nowTime = new Date().getTime(); 
			if((nowTime-thriftService.start)>expire) {
				reject(new Error("get connect expired"));
			}else{
				setTimeout(function(){
					buildConnect(thriftService,resolve,reject);
				},100)
			}
	
		}else{
					
			var address = ThriftService.addressPool.pop();
			console.log(ThriftService.addressPool)
			console.log("buildConnect address",address);
	
			var temp = address.split(":");
			var conn = createConnect(temp[0],temp[1]);
			thriftService.connection = conn
			//console.log(thriftService.service);
			var serv = thrift.createClient(thriftService.service,conn)
			resolve(serv);
			ThriftService.addressPool.unshift(address);
			console.log(ThriftService.addressPool)
			
		}

}

function createConnect(host,port){
	
	var connection = thrift.createConnection(host, port, {
	  transport : transport,
	  protocol : protocol
	});

	connection.on('error', function(err) {
	  console.log(false, err);
	});
	
	return connection;
}

exports.instance = (service)=>{
	return new ThriftService(service);
}

