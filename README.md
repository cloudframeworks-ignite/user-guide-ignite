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

本篇云框架结合实战经验，在[Spring Petclinic](https://github.com/spring-projects/spring-petclinic)（宠物医院）基础上进行“微服务架构”和“Ignite化”改造，并围绕此业务实例介绍Apache Ignite及其落地的最佳实践。

相关云框架：[[云框架]SMACK大数据架构](https://github.com/cloudframeworks-smack/user-guide-smack)

# 内容概览

* [性能对比](#性能对比)
* [快速部署](#快速部署)
    * [一键部署](#一键部署)
    * [本地部署](#本地部署)
* [框架说明-业务](#框架说明-业务)
* [框架说明-组件](#框架说明-组件)
   * [MySQL](#mysql)
   * [Ignite Config](#ignite-config)
   * [Ignite Registry](#ignite-registry)
   * [Ignite Visits](#ignite-visits)
   * [Ignite Vets](#ignite-vets)
   * [Ignite Customers](#ignite-customers)
   * [Ignite Gateway](#ignite-gateway)
* [如何变成自己的项目](#如何变成自己的项目)
* [更新计划](#更新计划)
* [社群贡献](#社群贡献)

# <a name="性能对比"></a>性能测试



# <a name="快速部署"></a>快速部署

## <a name="一键部署"></a>一键部署

[一键部署至好雨云帮](xx)

## <a name="本地部署"></a>本地部署

1. [准备Docker环境](./READMORE/install-docker.md)

2. 克隆完整代码

   ```
   git clone xx
   ```

3. 安装mysql

# <a name="框架说明-业务"></a>框架说明-业务

Spring Petclinic可完成宠物主人（Owner）、宠物（Vet）、到访（Visit）的注册／添加、查询、编辑等。

查看截图：

[主页](https://github.com/cloudframeworks-ignite/user-guide-ignite/blob/master/image/petclinic-homepage.png) ／ [查询所有信息](https://github.com/cloudframeworks-ignite/user-guide-ignite/blob/master/image/all-owners.png) ／ [注册宠物主人](https://github.com/cloudframeworks-ignite/user-guide-ignite/blob/master/image/add-owners.png) ／ [添加宠物](https://github.com/cloudframeworks-ignite/user-guide-ignite/blob/master/image/add-pets.png) ／ [编辑](https://github.com/cloudframeworks-ignite/user-guide-ignite/blob/master/image/pets-visits.png)

业务架构如下：



# <a name="框架说明-组件"></a>框架说明-组件



## <a name="mysql"></a>MySQL 



## <a name="ignite-config"></a>Ignite Config 



## <a name="ignite-registry"></a>Ignite Registry



## <a name="ignite-visits"></a>Ignite Visits



## <a name="ignite-vets"></a>Ignite Vets



## <a name="ignite-customers"></a>Ignite Customers



## <a name="ignite-gateway"></a>Ignite Gateway



# <a name="如何变成自己的项目"></a>如何变成自己的项目 



# <a name="更新计划"></a>更新计划



点击查看[历史更新](CHANGELOG.md)

# <a name="社群贡献"></a>社群贡献

+ QQ群: 
+ [参与贡献](CONTRIBUTING.md)
+ [联系我们](mailto:info@goodrain.com)

-------

[云框架](ABOUT.md)系列主题，遵循[APACHE LICENSE 2.0](LICENSE.md)协议发布。


