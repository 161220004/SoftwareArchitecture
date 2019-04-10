# Spring Batch

替换 xmlWriter，使 \*.cvs 转换成 \*.json

尝试利用依赖注入，仅依靠修改 job-hello-world.xml 来换 json 序列化库

- 有三种库可供选择：

  ```xml
  <!-- Json Serialize1 (Jackson) -->
  <dependency>
  	<groupId>com.fasterxml.jackson.core</groupId>
   	<artifactId>jackson-databind</artifactId>
  	<version>2.8.11.1</version>
  </dependency>
  		
  <!-- Json Serialize2 (Gson) -->
  <dependency>
  	<groupId>com.google.code.gson</groupId>
  	<artifactId>gson</artifactId>
  	<version>2.8.5</version>
  </dependency>
  		
  <!-- Json Serialize3 (Json-Lib) -->
  <dependency>
  	<groupId>net.sf.json-lib</groupId>
  	<artifactId>json-lib</artifactId>
  	<version>2.4</version>
  	<classifier>jdk15</classifier>
  </dependency>
  ```

- 新增包 wrapper（包含：JsonInterface ，WJackson，，），利用接口类  JsonInterface 统一这三个库中所要使用函数的接口

  ```java
  public interface JsonInterface {
  	public String toJson(List<? extends Report> items);
  }
  ```

  ```java
  public class WJackson extends com.fasterxml.jackson.databind.ObjectMapper implements JsonInterface {
  	@Override
  	public String toJson(List<? extends Report> items) {
  		String json = "";
  		try {
  			json = writeValueAsString(items);
  		} catch (JsonProcessingException e) {
  			e.printStackTrace();
  		}
  		return json;
  	}
  }
  ```

  ```java
  public class WJsonLib extends net.sf.json.JSONSerializer implements JsonInterface {
  	@Override
  	public String toJson(List<? extends Report> items) {
  		return toJSON(items).toString();
  	}
  }
  ```

  ```java
  // com.google.gson.Gson 是Final类，不能继承
  public class WGson implements JsonInterface {
  	private Gson gson;
  	public void setGson(Gson gson) {
  		this.gson = gson;
  	}
  	@Override
  	public String toJson(List<? extends Report> items) {
  		return gson.toJson(items);
  	}
  }
  ```

- 利用上述接口简化 JsonWriter

  ```java
  public class JsonWriter implements ItemWriter<Report> {
  	// file system resource
  	private String resource;
  	public void setResource(String resource) {
  		this.resource = resource;
  	}
  	// Json序列化库相关接口
  	private JsonInterface jsonInterface;
  	public void setJsonInterface(JsonInterface jsonInterface) {
  		this.jsonInterface = jsonInterface;
  	}
  	@Override
  	public void write(List<? extends Report> items) throws Exception {
  		FileWriter writer = new FileWriter(new File(resource));
  		writer.write(jsonInterface.toJson(items)); // 使用新接口
  		writer.close();
  	}
  }
  ```

- 利用依赖注入改 job-hello-world.xml

  ```xml
  ...
      <bean id="JsonWriter" class="cn.edu.nju.sa2017.pipefilter.JsonWriter">
          <property name="resource" value="xml/outputs/report.json"/>
          <property name="jsonInterface">
          	<!-- 以下三个库任择其一 -->
  			<!--ref bean="WJackson"/-->
  			<!--ref bean="WJsonLib"/-->
  			<ref bean="WGson"/>
  		</property>
      </bean>
  
  	<bean id="WJackson" class="cn.edu.nju.sa2017.pipefilter.wrapper.WJackson"></bean>
  	<bean id="WJsonLib" class="cn.edu.nju.sa2017.pipefilter.wrapper.WJsonLib"></bean>
  	<bean id="WGson" class="cn.edu.nju.sa2017.pipefilter.wrapper.WGson">
          <property name="gson">
  			<ref bean="Gson"/>
  		</property>
  	</bean>
  	<bean id="Gson" class="com.google.gson.Gson"></bean>
  ...	
  ```

  

