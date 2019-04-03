package cn.edu.nju.sa2017.pipefilter.wrapper;

import java.util.List;

import cn.edu.nju.sa2017.pipefilter.Report;

public interface JsonInterface {
	
	public String toJson(List<? extends Report> items);
}
