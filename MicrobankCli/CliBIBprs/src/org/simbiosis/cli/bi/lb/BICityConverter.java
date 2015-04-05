package org.simbiosis.cli.bi.lb;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.simbiosis.cli.bi.model.BICity;

public class BICityConverter {

	Map<String, String> cityMap = new HashMap<String, String>();
	Map<String, BICity> counterMap = new HashMap<String, BICity>();

	public BICityConverter(){
		createCityMap();
	}
	
	private void createCityMap() {
		cityMap.put("surabaya", "1291");
		cityMap.put("gresik", "1201");
		cityMap.put("kab.gresik", "1201");
		cityMap.put("mojokerto", "1292");
		cityMap.put("kab.mojokerto", "1292");
		cityMap.put("trenggalek", "1220");
		cityMap.put("kab.trenggalek", "1220");
		cityMap.put("tulungagung", "1219");
		cityMap.put("kab.tulungagung", "1219");
		cityMap.put("ponorogo", "1225");
		cityMap.put("kab.ponorogo", "1225");
		cityMap.put("sidoarjo", "1202");
		cityMap.put("kab.sidoarjo", "1202");
		cityMap.put("tuban", "1228");
		cityMap.put("kab.tuban", "1228");
		cityMap.put("lamongan", "1229");
		cityMap.put("kab.lamongan", "1229");
		cityMap.put("bangkalan", "1208");
		cityMap.put("kab.bangkalan", "1207");
		cityMap.put("sumenep", "1207");
		cityMap.put("kab.sumenep", "1208");
		cityMap.put("pamekasan", "1206");
		cityMap.put("kab.pamekasan", "1206");
		cityMap.put("pasuruan", "1214");
		cityMap.put("kab.pasuruan", "1214");
		cityMap.put("kediri", "1217");
		cityMap.put("kab.kediri", "1217");
		cityMap.put("jombang", "1204");
		cityMap.put("kab.jombang", "1204");
		cityMap.put("semarang", "0991");
		cityMap.put("lumajang", "1216");
		cityMap.put("kab.lumajang", "1216");
		cityMap.put("blitar", "1221");
		cityMap.put("kab.blitar", "1221");
		cityMap.put("malang", "1293");
		cityMap.put("kab.malang", "1293");
		cityMap.put("nganjuk", "1218");
		cityMap.put("kab.nganjuk", "1218");
	}

	public String getCityFromCode(String code) {
		String cityCode = new String("1291");
		String cabang = code.substring(0, 2);
		if (cabang.equalsIgnoreCase("00")) {
			cityCode = "1291";
		} else if (cabang.equalsIgnoreCase("01")) {
			cityCode = "1292";
		} else if (cabang.equalsIgnoreCase("02")) {
			cityCode = "1220";
		}
		return cityCode;
	}

	public String get(String city, String accCode) {
		String cityCode = (city == null) ? null : cityMap.get(city
				.toLowerCase().replaceAll(" ", ""));
		if (cityCode == null) {
			cityCode = getCityFromCode(accCode.substring(0, 2));
		}
		return cityCode;
	}

	public void put(String cityCode, String accCode, double value) {
		BICity biCity = counterMap.get(cityCode);
		if (biCity == null) {
			biCity = new BICity();
			biCity.setCode(cityCode);
			biCity.setRefCode(accCode);
			biCity.setCounter(1);
			biCity.setValue(value);
		} else {
			biCity.setCounter(biCity.getCounter() + 1);
			biCity.setValue(biCity.getValue() + value);
		}
		counterMap.put(cityCode, biCity);
	}
	
	public void clear(){
		counterMap.clear();
	}
	
	public Collection<BICity> values(){
		return counterMap.values();
	}
}
