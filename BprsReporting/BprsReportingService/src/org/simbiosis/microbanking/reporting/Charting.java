package org.simbiosis.microbanking.reporting;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getCharting")
public class Charting extends ReportServlet {
	private static final long serialVersionUID = 1L;

	public class ChartingDv {
		String field1;
		String field2;
		double field3;
		public String getField1() {
			return field1;
		}
		public void setField1(String field1) {
			this.field1 = field1;
		}
		public String getField2() {
			return field2;
		}
		public void setField2(String field2) {
			this.field2 = field2;
		}
		public double getField3() {
			return field3;
		}
		public void setField3(double field3) {
			this.field3 = field3;
		}

		
	}
	
	public Charting() {
		super("Charting");
	}

	List<ChartingDv> prepareData() throws ParseException {
		List<ChartingDv> result = new ArrayList<ChartingDv>();
		//
		ChartingDv data = new ChartingDv();
		data.setField1("Iwan");
		data.setField2("Jan");
		data.setField3(73);
		result.add(data);
		//
		data = new ChartingDv();
		data.setField1("Wasith");
		data.setField2("Jan");
		data.setField3(75);
		result.add(data);
		//
		data = new ChartingDv();
		data.setField1("Iwan");
		data.setField2("Feb");
		data.setField3(55);
		result.add(data);
		//
		data = new ChartingDv();
		data.setField1("Wasith");
		data.setField2("Feb");
		data.setField3(50);
		result.add(data);
		//
		data = new ChartingDv();
		data.setField1("Iwan");
		data.setField2("Mar");
		data.setField3(70);
		result.add(data);
		//
		data = new ChartingDv();
		data.setField1("Wasith");
		data.setField2("Mar");
		data.setField3(80);
		result.add(data);
		//
		data = new ChartingDv();
		data.setField1("Iwan");
		data.setField2("Apr");
		data.setField3(74);
		result.add(data);
		//
		data = new ChartingDv();
		data.setField1("Wasith");
		data.setField2("Apr");
		data.setField3(75);
		result.add(data);
		//
		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		
			//
			try {
				prepare();
				//
				setBeanCollection(prepareData());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//
	}

}
