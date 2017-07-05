var thrift = require('thrift');
var transport = thrift.TBufferedTransport;
var protocol = thrift.TMultiplexProtocol;
var addressPool=[];

function connection(service,callback){

	if(addressPool.length<1){
		var serv = service;
		var cb=callback;
		setTimeout(connection,1000,serv,cb); 
	
	}else{

		var address = addressPool.pop();
		var temp = address.split(":");
		var conn = createConn(temp[0],temp[1]);
		var service = thrift.createClient(service,conn);
		callback(service,conn);
		addressPool.unshift(address);
		
	}
}


function createConn(host,port){
	
	var connection = thrift.createConnection(host, port, {
	  transport : transport,
	  protocol : protocol
	});

	connection.on('error', function(err) {
	  console.log(false, err);
	});
	
	return connection;
}

function create(address){

	addressPool.push(address);

}

function close(conn){
	conn.end();
}

function clear(conn){
	addressPool=[];
}

exports.close = close;
exports.create = create;
exports.clear = clear;
exports.connection = connection;
