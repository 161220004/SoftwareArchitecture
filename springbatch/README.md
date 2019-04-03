# Spring Batch

替换 xmlWriter，使 \*.cvs 转换成 \*.json

- 修改类 Report
  清除所有 xml 相关注解：

  ```java
  public class Report {
      private int id;
      private BigDecimal sales;
      private int qty;
      private String staffName;
      private Date date;
      ... // Get/Set
  }
  ```

- 新增类 JsonWriter
  将 Report 类型对象列表按照格式写成 Json文件（保存于xml/outputs/report.json）
  采用 Json 序列化库：Jackson

  ```xml
  <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.5.3</version>
  </dependency>
  ```

  ```java
  public class JsonWriter implements ItemWriter<Report> {
  	// file system resource
  	private String resource;
  	public void setResource(String resource) {
  		this.resource = resource;
  	}
  	@Override
  	public void write(List<? extends Report> items) throws Exception {
          ... // Jackson
  	}
  }
  ```

- 修改 job-hello-world.xml，改造已有系统

  ```xml
  ...
      <batch:job id="helloWorldJob">
          <batch:step id="step1">
              <batch:tasklet>
                  <batch:chunk reader="cvsFileItemReader" writer="JsonWriter" processor="itemProcessor"
                               commit-interval="10">
                  </batch:chunk>
              </batch:tasklet>
          </batch:step>
      </batch:job>
  ...
      <bean id="JsonWriter" class="cn.edu.nju.sa2017.pipefilter.JsonWriter">
          <property name="resource" value="xml/outputs/report.json"/>
      </bean>
  ...
  ```



