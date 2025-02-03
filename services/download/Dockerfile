FROM openjdk:20-jdk-slim

ARG version=undefined
ENV VERSION=${version} \
    JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8 \
    -Djava.awt.headless=true \
    -Dsun.net.inetaddr.ttl=10 \
    -Djava.net.preferIPv4Stack=true \
    -XX:+AlwaysActAsServerClassMachine \
    -XX:+PerfDisableSharedMem \
    -XX:+UseStringDeduplication \
    -XX:+UseNUMA \
    -XX:+AlwaysPreTouch \
    -Dio.netty.leakDetectionLevel=simple \
    -XX:+ExplicitGCInvokesConcurrent \
    -XX:+ExitOnOutOfMemoryError \
    -Djava.security.egd=file:/dev/./urandom \
    -Xmx2g"

LABEL version=${VERSION}

COPY services/user/build/libs/user-$VERSION.jar /user.jar

EXPOSE 8115
EXPOSE 8081

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /user.jar" ]
