# Serverless exemple with Java and AWS
An exemple for using serverless with aws and java


## Donations
If you find my work useful, consider donating to support it :)
### <img alt="Image of Ethereum" width="25" height="25" src="https://github.com/joumenharzli/donations/blob/main/Ethereum-icon.png?raw=true"> Ethereum
You can simply scan this QR code to get my Ethereum address

<img alt="My QR Code" width="200" height="200" src="https://github.com/joumenharzli/donations/blob/main/ethereum.png?raw=true">


## Configuration
Start by creating a user with programmatic-access-only in AWS having the following roles

* AmazonS3FullAccess
* CloudWatchLogsFullAccess
* IAMFullAccess
* AWSLambdaFullAccess
* CloudFormationFullAccess
* AmazonAPIGatewayAdministrator

Install serverless
```shell
$ npm install -g serverless
```
Setup credentials with serverless
```shell
$ serverless config credentials --provider aws --key <key> --secret <secret>
```

## Build and deployment
### First build
We need to create a lambda layer that contains the java dependencies
```shell
$ cd serverless
$ mvn clean package
$ cd ../javalibs-layer
$ serverless deploy
```
Then deploy the serverless application
```shell
$ cd serverless
$ serverless deploy
```

## Development
if the dependencies was not changed you can simply build and deploy the project
```shell
$ cd serverless
$ mvn clean package
$ serverless deploy
```  
## Testing
You can test a function using
```shell
$ serverless invoke --function add-transaction --data '{"pathParameters":{"accountId":"123"},"body":"{\"amount\": 1234}"}'
```
To see logs use
```shell
$ serverless logs --function add-transaction 
```
Via Curl
```shell
$ curl -X POST https://xxxx.execute-api.us-east-1.amazonaws.com/dev/accounts/123/transactions --data '{"amount":1234}'
```
