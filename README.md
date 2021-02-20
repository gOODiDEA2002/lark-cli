# lark-cli

A tool for developing [lark](https://github.com/cuigh/lark) based applications.

## Usage

    lark -h

- create project

```bash    
lark new project -group groupName -artifact artifactName -port portSuffix projectDirname
```
- create module 

```bash  
lark new moduleType moduleDirName
```
- example
```bash
# new project
lark new project -group groupName -artifact artifactName -port 100 site

cd groupName
# new api
lark new api-contract api-contract
lark new api api

# new admin-api
lark new admin-api-contract admin-api-contract
lark new admin-api admin-api

# new service
lark new service-contract service-contract
lark new service service

# new admin-service
lark new admin-service-contract admin-service-contract
lark new admin-service admin-service

# new msg
lark new msg-contract msg-contract
lark new msg-handler  msg-handler

# new task
lark new task task

```

## Build

You need Go v1.11+ and [packr](https://github.com/gobuffalo/packr) v1 to build **lark-cli**

    packr build
    
    packr install

* for linux & win
```bash
GOOS=darwin GOARCH=amd64 packr build && mv lark release/mac/lark \
  && GOOS=linux GOARCH=amd64 packr build && mv lark release/linux/lark \
  && GOOS=windows GOARCH=386 packr build && mv lark.exe release/win/lark.exe \
  && packr clean
```