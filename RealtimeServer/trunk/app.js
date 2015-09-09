//Author: Di Qi

var app = require('express')()
  , server = require('http').createServer(app)
  , io = require('socket.io').listen(server);
 
server.listen(80);

/*
key: CabinetID
value: client instance
*/
var clients = {};

/*
key: socket instance
value: client instance
*/
var cabinets = {};
 
var gOpenRes = {};

//API for open box used by webserver
/*
1. get open box request with cabinetID and boxID from webserver
2. query the socket instance with cabinetID, if find the instance then goto 3, else goto 4
3. send open box request with boxID to the client via socket instance, send response to webserver when get OpenBoxRes message from client
4. send error response to webserver
*/
app.get('/open', function(req, res) {
  var client = clients[req.query.cabinetid];

  var openResKey = req.query.cabinetid + req.query.boxid;

  if(client == null){
	res.send({errorCode: -1, message: "the cabinet is out of service"});
  } else {
	var date = new Date();
//  	console.log(date + " cabinetID is " + client.cabinetID + " boxID is " + req.query.boxid + " socket id is " + client.socket);
	gOpenRes[openResKey] = res;
  	client.socket.emit('OpenBox', {boxID: req.query.boxid});
  }
});

io.set('heartbeat timeout', 2000); 
io.set('heartbeat interval', 1000); 
 
//socket related logic
io.sockets.on('connection', function (socket) {
	
	//socket.manager.transports[socket.id].socket.setTimeout(1500);
/*
  open box response from client
*/
  socket.on('OpenBoxRes', function (data){
//	console.log("OpenBoxRes data is ", data);

//OpenBoxRes data is  {"cabinetid":"00000000-6206-3efb-0033-c5870033c587","res":1,"boxid":15}

	var jsonRes = JSON.parse(data);
//	console.log("cabinetid is ", jsonRes.cabinetid);
	var cabinetid = jsonRes.cabinetid;
	var boxid = jsonRes.boxid;
	var res = jsonRes.res;

	var key = cabinetid+boxid;

	var openRes = gOpenRes[key];
//	console.log("OpenBoxRes, res key is ", key);	

	if(openRes == null)
		return;	

	if(res == -1) { //open box failure: gpio exception
		openRes.send({errorCode: -1, message: "open box failure: GPIO Exception"});
	} else if (res == 1) {
		openRes.send({errorCode: 0, message: "open box success"});
	}
	
	delete gOpenRes[key];
  });

/*
 register cabinet message will be sent when the socket client is connected to server
*/
  socket.on('RegisterCabinet', function (data) {
	var date = new Date();
	console.log(date + ' recv register cabinet pdu, cabinet id is ', data);
	
	var currClient = {
		cabinetID: 0,
		socket: 0,
	};
	
	currClient.cabinetID = data;
	currClient.socket = socket;
	
	/*
	key: cabinetID
	value: client instance
	*/
//	if(clients[currClient.cabinetID] == null){
		clients[currClient.cabinetID] = currClient;
//	} else {
//		console.log('RegisterCabinet: cabinet is already exist in clients array, cabinet id is ', currClient.cabinetID);
//	}
	
	/*
	key: socket instance
	value: client instance
	*/
///	if(cabinets[currClient.socket] == null) {
		cabinets[currClient.socket] = currClient;
//	}else {
//		console.log('RegisterCabinet: cabinet is already exist in cabinets array, cabinet id is ', currClient.cabinetID);
//	}
 });

/*
 related socktet instance should be removed when the socket client is disconnected to server
*/
  socket.on('disconnect', function () {
//	console.log('recv a disconnect pdu');

//	var client = cabinets[socket];
//	if(client != null)
//	{
//		console.log('socket client disconnect, remove related socket instance, the cabinet id is ', client.cabinetID);
//		delete clients[client.cabinetID];
//		delete cabinets[socket];
//	}
 });

});

