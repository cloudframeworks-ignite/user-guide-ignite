# [云框架]Ignite v0.1

![](https://img.shields.io/badge/Release-v0.1-green.svg)
[![](https://img.shields.io/badge/Producer-elvis2002-orange.svg)](CONTRIBUTORS.md)
![](https://img.shields.io/badge/License-Apache_2.0-blue.svg)

在大数据时代，特别是在构建高性能、高可用的大型互联网系统时，缓存扮演着至关重要的角色。分布式缓存技术除了我们熟悉的Redis、Memcached，还有Oracle Coherence、Hazelcast，以及本篇云框架主题——[Apache Ignite](https://ignite.apache.org/)，一款“以内存为中心”的内存计算和事务平台。

**Apache Ignite vs.** [Redis](https://db-engines.com/en/system/Ignite%3BRedis) / [Memcached](https://db-engines.com/en/system/Ignite%3BMemcached) / [Oracle Coherence](https://db-engines.com/en/system/Ignite%3BOracle+Coherence) / [Hazelcast](https://db-engines.com/en/system/Hazelcast%3BIgnite)

Ignite具有持久性、一致性、高可用等主要特点，同时具备强大的SQL、键值以及相关API，非常适合大规模的数据集处理，解决了大规模、大量数据、高并发应用所面临的诸多痛点，如：

* 数据缓存
* 数据库负载
* 查询性能

本篇云框架结合实战经验，在[Spring Petclinic](https://github.com/spring-projects/spring-petclinic)（宠物医院，微服务架构应用）基础上进行“Ignite化”改造，并围绕此业务实例介绍Apache Ignite及其落地的最佳实践。

相关云框架：[[云框架]SMACK大数据架构](https://github.com/cloudframeworks-smack/user-guide-smack)

# 内容概览

* [性能对比](#性能对比)
* [快速部署](#快速部署)
    * [一键部署](#一键部署)
    * [本地部署](#本地部署)
* [业务说明](#业务说明)
* [技术说明](#技术说明)
* [如何变成自己的项目](#如何变成自己的项目)
* [更新计划](#更新计划)
* [社群贡献](#社群贡献)

# <a name="性能对比"></a>性能测试

**测试环境**

```
Macbook pro (Retina, 150inch, Mid 2015)
macOS Sierra 版本 10.12.6
处理器：2.5 GHz Intel Core i7
内存：16GB 1600 MHz DDR3
```

**测试结果**

<div align=center><img width="900" height="" src="./image/ignite-local-test.jpeg"/></div>

通过本机测试，我们对于Ignite的性能已经可以初见端倪。可以预见的是，在集群下面对大规模数据时，Ignite对于性能的提升将会非常明显。

# <a name="快速部署"></a>快速部署

## <a name="一键部署"></a>一键部署

[一键部署至好雨云帮](xx)

## <a name="本地部署"></a>本地部署

1. [准备Docker环境](./READMORE/install-docker.md)

2. 克隆完整代码

   ```
   git clone https://github.com/cloudframeworks-ignite/user-guide-ignite
   ```

3. 使用[docker-compose](https://docs.docker.com/compose/install/)运行如下命令（或查看[分步部署](https://github.com/cloudframeworks-ignite/user-guide-ignite/blob/master/READMORE/deploy-by-steps.md)）

    ```
    docker-compose -f docker-compose.yml up -d
    ```

4. 访问

    ```
    http://127.0.0.1:8080
    ```

# <a name="业务说明"></a>业务说明

Spring Petclinic可完成宠物主人（Owner）、宠物（Vet）、到访（Visit）的注册／添加、查询、编辑等。

查看截图：

[主页](https://github.com/cloudframeworks-ignite/user-guide-ignite/blob/master/image/petclinic-homepage.png) ／ [查询所有信息](https://github.com/cloudframeworks-ignite/user-guide-ignite/blob/master/image/all-owners.png) ／ [注册宠物主人](https://github.com/cloudframeworks-ignite/user-guide-ignite/blob/master/image/add-owners.png) ／ [添加宠物](https://github.com/cloudframeworks-ignite/user-guide-ignite/blob/master/image/add-pets.png) ／ [编辑](https://github.com/cloudframeworks-ignite/user-guide-ignite/blob/master/image/pets-visits.png) ／ [查看Veterinarians](https://github.com/cloudframeworks-ignite/user-guide-ignite/blob/master/image/veterinarians.png)

业务架构如下：

<div align=center><img width="900" height="" src="./image/ignite-business-architecture.png"/></div>

* User可对Owners进行操作，包括注册、查看信息、编辑
* 可为Owners添加Pets或修改Pets信息
* 可为Pets增加Visits
* User可查看所有Veterinarians
* Owners与Visits相互依赖

# <a name="技术说明"></a>技术说明

Apache Ignite将分布式SQL数据库功能作为其平台的一部分，水平可扩展性、容错且符合ANSI ANSI 99标准，支持所有SQL和DML命令，包括SELECT、UPDATE、INSERT、MERGE和DELETE查询，同时为与分布式数据库相关的DDL命令的一个子集提供支持。

得益于固化内存架构，数据集和索引可以存储在RAM和磁盘上，意味着我们可以跨越不同的存储层执行分布式的SQL操作，使得在支持将数据固化到磁盘的前提下获得内存级的性能。

Apache Ignite提供语言层面的跨平台连接性，我们与Apche Ignite的交互不仅可以通过面向Java、.NET和C++的原生API，还已通过JDBC或ODBC API。

Ignite 分布式SQL数据库：

<div align=center><img width="900" height="" src="./image/sql_database.png"/></div>

原项目中Customer、Visits、Vets直接连接MySQL，而经过改造后，将通过Ignite进行交互，如下图所示：

<div align=center><img width="900" height="" src="./image/ignite-components-architecture.png"/></div>

图中灰色连线代表服务依赖关系，蓝色连线代表服务调用关系。

（添加改动及代码链接）

# <a name="如何变成自己的项目"></a>如何变成自己的项目

（添加）

# <a name="更新计划"></a>更新计划

* `README` 集群环境下性能测试

点击查看[历史更新](CHANGELOG.md)

# <a name="社群贡献"></a>社群贡献

+ QQ群: 532843906
+ [参与贡献](CONTRIBUTING.md)
+ [联系我们](mailto:info@goodrain.com)

-------

[云框架](ABOUT.md)系列主题，遵循[APACHE LICENSE 2.0](LICENSE.md)协议发布。


