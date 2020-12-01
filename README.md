# lark-cli

A tool for developing [lark](https://github.com/cuigh/lark) based applications.

## Usage

lark -h

#新建项目
lark new project -group ${groupName} -artifact ${artifactName} ${projectDirname}

#新建模块
lark new ${moduleType} -group ${groupName} -artifact ${artifactName} ${moduleDirName}

#各类型模块端口
Api: 1001 ~ 1999
Admin-Api：2001 ~ 2999
Service：3001 ~ 3999
Admin-Service: 4001 ~ 4999
Msg-Handler: 5001 ~ 3999
Task: 6001 ~ 4999

#eg:

#快递自提点服务平台(express-package-self-pickup-site)

#新建：项目
lark new project -group techwis -artifact epsps-user epsps-user 

#新建：Service & Contract
lark new service -group techwis -artifact epsps-user-service service
lark new service-contract -group techwis -artifact epsps-user-service-contract service-contract
lark new service -group techwis -artifact epsps-user-admin-service admin-service
lark new service-contract -group techwis -artifact epsps-user-admin-service-contract admin-service-contract

#新建：Api & Contract
lark new api -group techwis -artifact epsps-user-api api
lark new api-contract -group techwis -artifact epsps-user-api-contract api-contract
lark new api -group techwis -artifact epsps-user-admin-api api
lark new api-contract -group techwis -artifact epsps-user-admin-api-contract admin-api-contract

#新建：Task
lark new task -group techwis -artifact epsps-user-task task

#新建：Msg Handler & Contract
lark new msg-handler -group techwis -artifact epsps-user-msg-handler msg-handler
lark new msg-contract -group techwis -artifact epsps-user-msg-contract msg-contract



#测试Service
curl --location --request POST 'http://127.0.0.1:3001/test/hello.srv' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 123 
}'

#测试Api
curl -X POST "http://127.0.0.1:1001/test/hello.api" -d "id=123&name=xxx"


## Build

You need Go v1.11+ to build **lark-cli**

依赖：packr v1
构建：packr build
安装：packr install

#多平台构建
GOOS=darwin GOARCH=amd64 packr build && mv lark release/mac/lark \
  && GOOS=linux GOARCH=amd64 packr build && mv lark release/linux/lark \
  && GOOS=windows GOARCH=386 packr build && mv lark.exe release/win/lark.exe \
  && packr clean