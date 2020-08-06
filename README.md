## 基于ali物联网的服务平台
####  1、aliyun      
该包下使用阿里云client客户端demo，来进行topic的订阅      

#### 2、configure   
2.1 CORSConf 配置前端跨域

2.2 LoginInterrupter和LoginInterrupterConfigurer配置资源拦截和重定向

2.3 StaticConfig 配置静态资源，本程序用来提供app升级的安装包

2.4 TomcatConf 配置tomcat的URL解析，使在使用get方式发送"[]{}"的时候可以解析

2.5 webSocket

#### 3、controller  

3.1 BaseController 基类，所有controller需要继承

3.2 AppDateController 数据接口，提供app所需的数据

3.3 DeviceBindController 设备注册和上线所需的操作

3.4 IotWebController 返回一些管理员所需的页面和信息

3.5 OperationController 注册项目使用的页面

3.6 UserController 用户相关

3.7 WebSocketController webSocket相关

3.8 其他未列出的Controller，实际操作中未使用

#### 4、entity

数据库表的实体封装

#### 5、filter

过滤器，保证在服务重启以后，未登录的客户端不会再继续请求到数据

#### 6、IQuartzJob

6.1 BaseJob 基类，所有新建的定时任务类需要继承

6.2 IQuartzConf 配置类，配置quartz随程序启动

6.3 IQuartzStart 配置scheduler启动了哪些任务

6.4 其余为 BaseJob的子类，为任务具体执行操作

#### 7、listener

监听session的创建，用来完成全网单账户只能登陆一个的功能

#### 8、mapper

和resources文件夹下的mapper.xml文件一起使用，是mybatis的组件

#### 9、service

service接口和实现

#### 10、utils工具类

10.1 AMQP 阿里云服务端订阅的AMQP服务端

​	10.1.1 AMQPMessage 消息类封装pojo

​	10.1.2 AMQPServiceUtils AMQP服务端

10.2 Cache 缓存（未使用）

10.3 Html 存放最初自己开发前端页面时的工具，当前无用

10.4 MsgAnalysis 消息解析

​	10.4.1 Dg3AnalysisGrain 大公3粮情协议

​	10.4.2 Dg4AnalysisN2 大公云服务气调协议

​	10.4.3 Dg4AnalysisOil 大公云服务油情协议

​	10.4.4 Dg4AnalysisSunPower 大公云服务太阳能粮情协议

10.5 MsgBuilder 协议消息生成

​	10.5.1 CommondBuild 消息生成的基类

​	10.5.2 ByteUtil 数据转换和校验工具

​	10.5.3 CRC16 crc校验

​	10.5.4 其他：协议消息生成的实际执行者

10.6 Constant 系统常量

10.7 ContextUtil 系统公用方法

10.8 DeviceUtil 从amqpmsg中解析出device对象的方法

10.9 DownOrderUtils 系统消息下发工具类

10.10 GenerateUUID 生成UUID的类，未使用

10.11 JsonResult 返回前端的数据封装，格式1

10.12 JsonResult2 返回前端的数据封装，格式2 用以满足layui的数据表格所需要的数据格式

10.13 Quartz enum

10.14 SpringUtil 获取spring上下文

#### 11、VO

前端请求数据的封装类