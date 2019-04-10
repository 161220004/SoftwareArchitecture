package cn.edu.nju.sa2017.pipefilter.wrapper;

import java.util.List;

import cn.edu.nju.sa2017.pipefilter.Report;

public class WJsonLib extends net.sf.json.JSONSerializer implements JsonInterface {

	@Override
	public String toJson(List<? extends Report> items) {
		return toJSON(items).toString();
	}

}
