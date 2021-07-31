package com.example.PayrollApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@WebAppConfiguration
public abstract class BaseAPITest{
	protected MockMvc mvc;
	
	@Autowired
	WebApplicationContext webAppContext;
	
	protected void initialize()
	{
		mvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
	}
	
	protected String toJson(Object obj) throws JsonProcessingException
	{
		ObjectMapper objMapper = new ObjectMapper();
		return objMapper.writeValueAsString(obj);
	}
	
	protected <T> T fromJsonToArray(String json, String nodeName, Class<T> resultantClass) 
		throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		JsonNode jsonNode = objMapper.readTree(json);
		jsonNode = jsonNode.get("_embedded").get(nodeName);
		return objMapper.treeToValue(jsonNode, resultantClass);
	}
	
	protected <T> T fromJson(String json, Class<T> resultantClass)
		throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		return objMapper.readValue(json, resultantClass);
	}
	
	static protected void assertStatus(int status, MvcResult result)
	{
		assertEquals(status, result.getResponse().getStatus());
	}
}