package cn.edu.nju.sa2017.pipefilter.wrapper;

import java.util.List;

import com.google.gson.Gson;

import cn.edu.nju.sa2017.pipefilter.Report;

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
