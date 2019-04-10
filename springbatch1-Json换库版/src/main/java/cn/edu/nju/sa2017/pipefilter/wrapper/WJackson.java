package cn.edu.nju.sa2017.pipefilter.wrapper;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import cn.edu.nju.sa2017.pipefilter.Report;

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
