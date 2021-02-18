FROM image-repo-dev.lark-cloud.com/ci/java-run:1.0.0
COPY ./target/*.jar /opt/app.jar
EXPOSE {{.Port}}
ENTRYPOINT ["/bin/sh", "/opt/entrypoint.sh"]