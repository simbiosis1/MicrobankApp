package org.simbiosis.migrator;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.microbank.CustomerDto;
import org.simbiosis.migrator.lib.Migrator;

public class MigratorCustomer extends Migrator {

	public static void main(String[] args) {
		MigratorCustomer customer = new MigratorCustomer();
		customer.execute();
	}

	public MigratorCustomer() {
		super();
	}

	protected void process() {
		String strQuery = "select customer_id,customer_code,customer_entrydate,person_name,person_nameext,"
				+ "person_mothername,person_sex, person_pob,person_dob,person_idcardtype,person_idcardnr,"
				+ " address_street,address_city,address_postal,address_district,address_region,"
				+ "address_extra,address_province,phone_homenr,phone_cell "
				+ "from tb_customer,tb_person,tb_address,tb_phone "
				+ "where tb_customer.person_id=tb_person.person_id "
				+ "and tb_person.person_addr1=tb_address.address_id "
				+ "and tb_person.person_phone=tb_phone.phone_id "
				+ "and customer_entrydate between'"
				+ getStrDbBeginDate()
				+ "' and '" + getStrDbEndDate() + "'";
		try {

			ResultSet rs = execSQL(strQuery);
			// int i = 1;
			while (rs.next()) {
				String strCif = getCoreClient().sendRawData("getCifIdByRef",
						rs.getString("customer_id"));
				if (strCif.equalsIgnoreCase("0")) {
					CustomerDto cusDto = new CustomerDto();
					cusDto.setBranch(getBranch());
					cusDto.setRegistration(rs.getDate("customer_entrydate"));
					cusDto.setRefId(rs.getLong("customer_id"));
					cusDto.setCode(rs.getString("customer_code"));
					cusDto.setName(rs.getString("person_name"));
					cusDto.setAlias(rs.getString("person_nameext"));
					cusDto.setMotherName(rs.getString("person_mothername"));
					cusDto.setSex(rs.getInt("person_sex"));
					cusDto.setPob(rs.getString("person_pob"));
					cusDto.setDob(rs.getDate("person_dob"));
					cusDto.setIdType(rs.getInt("person_idcardtype"));
					cusDto.setIdCode(rs.getString("person_idcardnr"));
					cusDto.setAddress(rs.getString("address_street"));
					cusDto.setVillage(rs.getString("address_district"));
					cusDto.setDistrict(rs.getString("address_region"));
					String strArea = rs.getString("address_extra");
					if (!strArea.isEmpty()) {
						String[] areas = strArea.split(";");
						if (areas.length > 1) {
							cusDto.setArea1(areas[0]);
							cusDto.setArea2(areas[1]);
						}
					}
					cusDto.setCity(rs.getString("address_city"));
					cusDto.setPostCode(rs.getString("address_postal"));
					cusDto.setProvince(rs.getString("address_province"));
					cusDto.setPhone(rs.getString("phone_homenr"));
					cusDto.setHandphone(rs.getString("phone_cell"));
					cusDto.setTaxable(1);
					//
					// kirim data
					ObjectMapper mapper = new ObjectMapper();
					StringWriter sw = new StringWriter();
					mapper.writeValue(sw, cusDto);
					getCoreClient().sendRawData("saveCif", sw.toString());
					System.out.print(".");
				} else {
					System.out.println("Data nasabah sudah ada : "
							+ rs.getString("customer_id"));
				}
			}
			closeDb();
		} catch (java.sql.SQLException | IOException e) {
			e.printStackTrace();
			System.out.println("Unable to close a connection.");
		}
	}
}
