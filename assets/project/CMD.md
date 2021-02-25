# 验证

## API

* 本地启动
```bash
java -jar api/target/{{.ArtifactID}}-api-1.0.0-SNAPSHOT.jar --spring.profiles.active=playground
```

* 本地访问
```bash
curl -X POST "http://127.0.0.1:1{{.Port}}/test/hello.api" -d "id=123&name=xxx"
```

## Service

* 本地启动
```bash
java -jar service/target/{{.ArtifactID}}-service-1.0.0-SNAPSHOT.jar --spring.profiles.active=playground
```
* 本地访问
```bash
curl --location --request POST 'http://127.0.0.1:3{{.Port}}/lark/TestService/hello' \
--header 'Content-Type: application/json' \
--data-raw '{
        "service": "TestService",
	"method": "Hello",
	"args": [
		{
			"type": 150,
			"data": "{\"id\":123,\"type\":\"GOOD\"}"
		}
	]
}'
```

## Task

* 本地启动
```bash
java -jar task/target/{{.ArtifactID}}-task-1.0.0-SNAPSHOT.jar --spring.profiles.active=playground
```

* 本地访问
```bash
url="http://127.0.0.1:6{{.Port}}/run"
token="12345678"
task="TestExecutor"
echo -e "->request: $url $task\n"
curl --location --request POST $url \
--header 'Content-Type: application/json' \
--header 'XXL-JOB-ACCESS-TOKEN:$token' \
--data-raw '{
        "jobId": 1,
        "executorHandler": '\"$task\"',
        "executorParams": "",
        "logId": 1,
        "logDateTime":1586629003729
}'
```

## Msg-Handler

* 本地启动
```bash
java -jar msg-handler/target/{{.ArtifactID}}-msg-handler-1.0.0-SNAPSHOT.jar --spring.profiles.active=playground
```

* 本地访问
```bash
handler="TestExecutor"
url="http://127.0.0.1:5{{.Port}}/run/${handler}"
echo -e "->request: $url $handler\n"
curl --location --request POST $url \
--header 'Content-Type: application/json' \
--data-raw '{
      "orderId":123,
      "userId":456
    }'
```