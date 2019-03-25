# 基于HyberLedger fabric 的区块链溯源系统

 

## 部署节点说明

> 由于机器，和ip有限以及集群部署的复杂度，将采用单机部署，为节约资源采用docker容器代替虚拟机，每个端口即是一个docker容器服务。



节点端口

 - orderder :7050
 - 商家组织
    - 节点0：
       - 发布 7051
       - 监听 7053
       - 数据库  5984
    - 节点1：
       - 发布 10051
      - 监听 10053
      - 数据库  7984

### 区块操作

> 区块操作即是通过sdk 发布提案（公钥证书，私钥签名），通知Orderer节点准备投票
>
> 各节点收到区块信息-> 验证 -> 提交 （即投票）

- 添加奶粉信息
- 加工奶粉
- 检测奶粉
- 删除奶粉
- 修改奶粉数据

### 流程演示

1. 区块链信息

   >总共23 个区块，可看到最新区块hash和前一区块hash

   ![1552751522097](C:\Users\huanxi\AppData\Roaming\Typora\typora-user-images\1552751522097.png)

2. 添加奶粉

> 添加一个所有者为haunxi，id为2 ，重量为500g的奶粉，可看到交易id

![1552751693290](C:\Users\huanxi\AppData\Roaming\Typora\typora-user-images\1552751693290.png)

**组织1的peer0 日志**

![1552751817529](C:\Users\huanxi\AppData\Roaming\Typora\typora-user-images\1552751817529.png)

**组织1的peer1 日志**

![1552751901138](C:\Users\huanxi\AppData\Roaming\Typora\typora-user-images\1552751901138.png)

**组织2的peer0 日志**

![1552751953623](C:\Users\huanxi\AppData\Roaming\Typora\typora-user-images\1552751953623.png)

**组织2的peer1日志**

![1552751936685](C:\Users\huanxi\AppData\Roaming\Typora\typora-user-images\1552751936685.png)

> 可以看到4个节点都收到了区块，并验证，然后在提交

**查看数据库情况**

> 可以看到4个数据库都插入了相同的数据

![1552752113637](C:\Users\huanxi\AppData\Roaming\Typora\typora-user-images\1552752113637.png)

![1552752129842](C:\Users\huanxi\AppData\Roaming\Typora\typora-user-images\1552752129842.png)

![1552752144453](C:\Users\huanxi\AppData\Roaming\Typora\typora-user-images\1552752144453.png)

![1552752163204](C:\Users\huanxi\AppData\Roaming\Typora\typora-user-images\1552752163204.png)

**再看区块链信息**

> 对比上面可以看到多了一个区块，并且现在的previoushash等于上一次的currenthash

![1552752253958](C:\Users\huanxi\AppData\Roaming\Typora\typora-user-images\1552752253958.png)

**查看23 区块最新信息**

> 可以看到该区块有组织一创建，能看到组织1的公钥，ws是该区块的所有写操作，可以看到该区块对key为2的值写了后面的值（区块链采用的couchdb，键值对数据库）

![1552752376946](C:\Users\huanxi\AppData\Roaming\Typora\typora-user-images\1552752376946.png)

**对刚才的奶粉加工**

> ​	和添加是同样的流程，不再截图

![1552752684445](C:\Users\huanxi\AppData\Roaming\Typora\typora-user-images\1552752684445.png)

> 看新增的24个区块，可以看到ws中对2 写了新的process_info

![1552752816957](C:\Users\huanxi\AppData\Roaming\Typora\typora-user-images\1552752816957.png)



**检测和加工一样就不再演示了**



### 结束语

> ​	通过流程可以看到，我们将组织对数据库的操作写在区块链中。由于区块链具有不可修改性，有区块链维护的数据库具有公开，不可修改性。可以遍历整个区块链得到一个couchdb数据库。