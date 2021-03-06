## 报错
1. new SpringApplicationBuilder( App.class).properties("server.port=" + port).run(args);//这里报错
   - 解决：project structure 里面如果没有spring选项，添加一个，然后下面会提示没有spring支持，选择fix。
2. 提示缺少某个包，重新新建一个功能时重新引导选择具体某一个引用版本。或者在setting里面的maven的ignore文件，去掉吊钩。
   - 其实主要：通过spring initial的项目应该没有夫项目这个吧，所以pom需要改。当子项目的版本号没注明时不可用的时候，一般就是没有引用夫项目。
 3. 注册不可用：
 
         <!--这两个完全不一样啊卧槽-->
         <!--<dependency>-->
             <!--<groupId>org.springframework.cloud</groupId>-->
             <!--<artifactId>spring-cloud-netflix-eureka-client</artifactId>-->
         <!--</dependency>-->
         <dependency>
             <groupId>org.springframework.cloud</groupId>
             <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
         </dependency>
4. pom文件需要注意，夫文件有spring-cloud.version，子文件会覆盖：

        <properties>
            <spring-cloud.version>Greenwich.SR1</spring-cloud.version>
        </properties>
5. 把原有的application.properties删掉。只能存在一个
6. 后台传数据没问题，也return了products，但是前台就是显示不出。原因：static跟templates都有products.html，而配置文件写的是templates，即使这样还是删除static下的文件后才能访问。
7. 配置服务器问题：https://blog.csdn.net/u013233360/article/details/86666279 另外，直接访问服务器时 http://localhost:8030/{}/dev，中应该填product-view-service-feign也就是application也算是文件名字。

## 用法：
1. zipkin 服务链路追踪服务器，就是监控微服务之间的依赖关系
   - 启动链路追踪服务器:`java -jar zipkin-server-2.10.1-exec.jar`
   - 配置好pom以及client以及yml, 挨个启动。
   - 然后打开链路追踪服务器 http://localhost:9411/zipkin/dependency/  就可以看到视图微服务调用数据微服务的图形
   - 加入消息队列后，需要增加参数来启动服务`java -jar zipkin-server-2.10.1-exec.jar --zipkin.collector.rabbitmq.addresses=localhost`，不过需要post请求`http://localhost:8012/actuator/bus-refresh`就是当前应用服务post访问该网址
   - 如果按照顺序先执行命令，后运行应用后，在页面没有看到图像，应该是没有依赖关系。或者没有运行某个有依赖关系的页面，导致数据没有发出因而没有捕捉。
2. 总线Bus，就是动态请求已更新的资源。
3. 断路器hystrix，就是当被访问的微服务无法使用的时候，当前服务能够感知这个现象，并且提供一个备用的方案出来。加好断路器后，如果服务变的可用，可以直接自动切换过去。
4. 断路监控器，本身需要监控器不断访问数据服务才能监控。解决：数据服务什么时候可用，什么时候不可用，如何监控这个事情呢。在`http://localhost:8020/hystrix`输入`http://localhost:8012/actuator/hystrix.stream`
5. 为了方便监控集群里的多个实例，springCloud 提供了一个 turbine 项目，它的作用是把一个集群里的多个实例汇聚在一个 turbine里，这个然后再在 断路器监控里查看这个 turbine, 这样就能够在集群层面进行监控啦。输入`http://localhost:8021/turbine.stream`.其中有一个问题：https://blog.csdn.net/Jack__iT/article/details/84073207
6. 网关：一个微服务会有多个实例，即多个端口号、放在不同IP主机上，为此，应该是做一个，记住网关的地址和端口号就行了。比如数据服务，一次性开3个，会自动进行负载均衡。但前提是访问过新加的服务后，才能感受到他的存在。


## 消息队列rabbitmq
1. 概念：
   - server：broker，接受客户端连接、实现AMQP实体服务
   - connection：连接，应用程序与broker的网络连接
   - channel：网络通道。是消息读写的通道，客户端可以建立多个channel，每个channel代表一个会话任务。
   - message：消息。服务器与应用程序之间传送的数据。由properties与body组成，properties对消息修饰，如消息优先级等，body则是消息体的内容。
   - virtual host：虚拟地址，应用于逻辑隔离，一般用于隔离不同的项目。一个virtual host里可以有一些Exchange与Queue，同一个virtual host不能有相同名称的exchange或queue。
   - exchange：交换机，接受消息，根据路由键转发消息-到-绑定的队列。
   - binding：exchange与queue之间的虚拟连接，binding可以包含routing key。
   - routing key：一个路由规则，虚拟机可以用它来确定如何路由一个特定消息
   - queue：消息队列，保存消息并转发给消费者。
2. 为了确保消息永不丢失，RabbitMQ支持 消息确认。消费者发回ack（nowledgement）告诉RabbitMQ已收到，处理了特定消息，RabbitMQ可以自由删除它。如果消费者死亡（其通道关闭，连接关闭或TCP连接丢失）而不发送确认，RabbitMQ将理解消息未完全处理并将重新排队。如果同时有其他在线消费者，则会迅速将其重新发送给其他消费者。这样你就可以确保没有消息丢失，即使工人偶尔会死亡。
