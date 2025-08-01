# 使用官方的OpenJDK 17作为基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制Maven构建的jar文件到容器中
COPY target/springboot-jenkins-docker-demo-0.0.1-SNAPSHOT.jar app.jar

# 暴露应用程序端口
EXPOSE 8097

# 设置JVM参数
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# 运行应用程序
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

# 健康检查
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8097/api/health || exit 1