## Deploy by Steps

1. 安装mysql

    ```
    docker run -d -e MYSQL_ROOT_PASSWORD=root --name=mysql mysql:5.7
    ```

2. 导入[数据](https://github.com/cloudframeworks-ignite/user-guide-ignite/tree/master/db)

3. 部署iginite-config

    ```
    docker run -d --name=config goodraincloudframeworks/iginite-config
    ```

4. 部署ignite-registry

    ```
    docker run -d --name=registry --link config:config  goodraincloudframeworks/iginite-registry
    ```

5. 部署ignite-visits

    ```
    docker run -d --link config:config --link registry:registry --link mysql:mysql goodraincloudframeworks/iginite-visits
    ```

6. 部署ignite-vets

    ```
    docker run -d --link config:config --link registry:registry --link mysql:mysql goodraincloudframeworks/iginite-vets
    ```

7. 部署ignite-customers
 
    ```
    docker run -d --link config:config --link registry:registry --link mysql:mysql goodraincloudframeworks/iginite-customers
    ```

8. 部署ignite-gateway

    ```
    docker run -d --link config:config --link registry:registry -p 8080:8080 goodraincloudframeworks/iginite-gateway
    ```

9. 访问

    ```
    http://127.0.0.1:8080
    ```

