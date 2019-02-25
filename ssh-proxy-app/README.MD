## Build 
Build the project
```commandline
mvn clean package
```
## Config
create a config file: config.json
```json
[{
 	"host": "iyatou.vip",
 	"port": 22,
 	"username": "ubuntu",
 	"authType": "PRK",
 	"privateKeyPath": "id_rsa_0",
 	"timeout": 10000,
 	"keepAliveInterval": 10000,
 	"hostProxyInfos": [{
 		"remoteHost": "iyatou.vip",
 		"remotePort": 3306,
 		"localPort": 3309
 	}]
 },{
	"host": "172.20.211.12",
	"port": 22,
	"username": "hadoop",
	"authType": "PRK",
	"privateKeyPath": "id_rsa_1",
	"timeout": 10000,
	"keepAliveInterval": 10000,
	"hostProxyInfos": [{
		"remoteHost": "rds.redis.aliyun.com",
		"remotePort": 6379,
		"localPort": 6380
	},{
		"remoteHost": "rds.mysql.aliyun.com",
		"remotePort": 3306,
		"localPort": 3308
	}]
}]
```
## Run app
Run proxy
```commandline
java -jar ssh-proxy-app.jar config.json
```
