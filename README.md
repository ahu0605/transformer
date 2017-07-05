# transformer
vue+nodejs+thrift+zookeeper+mybatis

- 随便搭建的分布式系统，第一版本时间紧，都是bug等待修复
- vue+webpack 做前端工程
- express 做前端controller ，好多callback，TODO改成sync方法，thrift是否可以做成pool?
- thrift 做跨语言通信协议
- zookeeper 做集群管理，感觉不够完善，java 服务端shutdown时会报错
- mybatis 访问数据库
