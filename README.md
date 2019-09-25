# Serverless exemple with Java and AWS
An exemple for using serverless with aws and java


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
```shell
$ mvn clean install
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
