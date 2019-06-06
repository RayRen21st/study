package study.dataformat.json;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BackCompatibleTest {
	
	@JsonIgnoreProperties(ignoreUnknown=true)
	static class Person {
		
		private String firstName;
		
		private String lastName;
		
		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		
	}
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		
		String aPersonString = "{\"firstName\": \"Vincent\", \"lastName\": \"Zhu\", \"Address\": \"GZ\"}";
//		String aPersonString = "{\"firstName\": \"Vincent\", \"lastName\": \"Zhu\"}";
		ObjectMapper mapper = new ObjectMapper();
		Person aPerson = mapper.readValue(aPersonString, Person.class);
		
	}
}
