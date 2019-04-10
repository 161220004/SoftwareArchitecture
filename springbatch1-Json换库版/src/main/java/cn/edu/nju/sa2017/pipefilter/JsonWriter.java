package cn.edu.nju.sa2017.pipefilter;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.springframework.batch.item.ItemWriter;

import cn.edu.nju.sa2017.pipefilter.wrapper.JsonInterface;

public class JsonWriter implements ItemWriter<Report> {
	// file system resource
	private String resource;

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	// Json序列化库相关
	private JsonInterface jsonInterface;

	public void setJsonInterface(JsonInterface jsonInterface) {
		this.jsonInterface = jsonInterface;
	}
	
	@Override
	public void write(List<? extends Report> items) throws Exception {
		FileWriter writer = new FileWriter(new File(resource));
		writer.write(jsonInterface.toJson(items));
		writer.close();
	}

}
