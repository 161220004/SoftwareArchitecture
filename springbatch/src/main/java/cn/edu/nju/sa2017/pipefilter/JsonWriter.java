package cn.edu.nju.sa2017.pipefilter;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.google.gson.Gson;

public class JsonWriter implements ItemWriter<Report> {
	// file system resource
	private String resource;

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	@Override
	public void write(List<? extends Report> items) throws Exception {
		FileWriter writer = new FileWriter(new File(resource));
		// Gson��Json���л�
		Gson gson = new Gson();
		writer.write(gson.toJson(items));
		/* Jackson��Json���л�
		ObjectMapper mapper = new ObjectMapper();
		writer.write(mapper.writeValueAsString(items));
		*/
		/* �ֶ�Json���л�
		writer.write("{\n\t\"records\": [\n");
		boolean isHead = true;
		for (Report item: items) {
			if (isHead) {
				isHead = false;
			} else {
				writer.write(",\n");
			}
			writer.write("\t{\n");
			writer.write("\t\t\"id\": \"" + item.getId() + "\",\n");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			writer.write("\t\t\"staffdate\": \"" + dateFormat.format(item.getDate()) + "\",\n");
			writer.write("\t\t\"qty\": \"" + item.getQty() + "\",\n");
			writer.write("\t\t\"sales\": \"" + item.getSales() + "\",\n");
			writer.write("\t\t\"staffName\": \"" + item.getStaffName() + "\"\n");
			writer.write("\t}");
		}
		writer.write("\n  ]\n}\n");
		*/
		writer.close();
	}

}
