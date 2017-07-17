var zookeeper = require('node-zookeeper-client');
var config = require('../config');
var thrift = require('./thrift');

function listChildren(client, path) {
    client.getChildren(
        path,
        function (event) {
            console.log('Got watcher event: %s', event);
            listChildren(client, path);
        },
        function (error, children, stat) {
            if (error) {
                console.log(
                    'Failed to list children of %s due to: %s.',
                    path,
                    error
                );
                return;
            }
 
            console.log('Children of %s are: %j.', path, children);
            var inst = thrift.instance();
            inst.clear();
       			children.forEach(function(e){
           		inst.address(e); 
            })
      
        }
    );
}

function init(){
	
  var client = zookeeper.createClient(config.setting.zookeeper);
	client.once('connected', function () {
    console.log('Connected to ZooKeeper.');
    listChildren(client, '/thrift');
	});
	client.connect();
	
}

exports.init = init;
