//package com.example.controller;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import com.example.model.worldcupinfo.Stadiums;
//import com.example.model.worldcupinfo.WorldCupInfo;
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@RestController
//@RequestMapping("/api/stadiums")
//public class StadiumsControler {
//
//	
//	@GetMapping(value = "/stadiums")
//	public List<Stadiums> getStadiums() throws JsonParseException, JsonMappingException, IOException {
//		
//		List<Stadiums> stadiumsList = getInfoWorlCup();
//		
//		return stadiumsList;
//	}
//	
//	
//	
//	private List<Stadiums>  getInfoWorlCup() throws JsonParseException, JsonMappingException, IOException {
//
//		final String uri = "https://raw.githubusercontent.com/lsv/fifa-worldcup-2018/master/data.json";
//		List<Stadiums> listStadiums;
//		
//		RestTemplate restTemplate = new RestTemplate();
//		String result = restTemplate.getForObject(uri, String.class);
//
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		WorldCupInfo value = mapper.readValue(result, WorldCupInfo.class);
//		
//		listStadiums = new ArrayList<>();
//		for(int i = 0; i< value.getStadiums().length;i++) {
//			listStadiums.add(value.getStadiums()[i]);
//		}
//		
//		return listStadiums;
//
//	}
//	
//	
//}
