package nariis.mcsas.management.fault.web.viewmodel;

import java.util.ArrayList;
import java.util.HashMap;

public class StatusNumviewModel {
	private ArrayList<HashMap<String, Object>> list  ;
	private String successful;
	public ArrayList<HashMap<String, Object>> getList() {
		return list;
	}
	public void setList(ArrayList<HashMap<String, Object>> list) {
		this.list = list;
	}
	public String getSuccessful() {
		return successful;
	}
	public void setSuccessful(String successful) {
		this.successful = successful;
	}
}
