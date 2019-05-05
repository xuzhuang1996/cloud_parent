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
4. pom文件需要注意，夫文件有spring-cloud.version，子文件绝对不能有：

        <properties>
            <spring-cloud.version>Greenwich.SR1</spring-cloud.version>
        </properties>
5. 把原有的application.properties删掉。然后一定要执行一下  maven -X clean install
6. 后台传数据没问题，也return了products，但是前台就是显示不出。原因：static跟templates都有products.html，而配置文件写的是templates，即使这样还是删除static下的文件后才能访问。

## 用法：
1. zipkin 服务链路追踪服务器，就是监控微服务之间的依赖关系
   - 启动链路追踪服务器:`java -jar zipkin-server-2.10.1-exec.jar`
   - 配置好pom以及client以及yml, 挨个启动。
   - 然后打开链路追踪服务器 http://localhost:9411/zipkin/dependency/  就可以看到视图微服务调用数据微服务的图形
2. 