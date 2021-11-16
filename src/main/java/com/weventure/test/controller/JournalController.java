package com.weventure.test.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class JournalController {
	@Controller
	public class DownloadController {

		private String file = "weventure/src/main/resources/static/data.json";

		@GetMapping("/display")
		public Object display(@RequestParam(value = "userId") int userId,
				@RequestParam(value = "year") int year,
				@RequestParam(value = "month") int month,
				@RequestParam(value = "day") int day) throws IOException,
				ParseException {
			Date date = new Date(year, month, day);
			JSONArray contentArr = readFile();
			JSONArray newArr = new JSONArray();
			for (Object o : contentArr) {
				String s = o.toString();
				if (s.contains(date.toString()))
					newArr.add(s);
			}
			return newArr;
		}

		@GetMapping("/delete")
		public void delete(@RequestParam(value = "userId") int userId,
				@RequestParam(value = "food") String foodName)
				throws IOException, ParseException {
			JSONArray contentArr = readFile();
			JSONArray newArr = new JSONArray();
			for (Object o : contentArr) {
				String s = o.toString();
				if (!s.contains(foodName))
					newArr.add(s);
			}
			writeFile(newArr.toJSONString());
		}

		private String writeJson(JSONArray json, int userId, int id,
				String foodName, int foodPortion, Date date) {
			JSONArray food = new JSONArray();
			food.add("id: " + id);
			food.add("name: " + foodName);
			food.add("portion: " + foodPortion);
			food.add("date: " + date);

			JSONObject obj = new JSONObject();
			obj.put("userId", userId);
			obj.put("food", food);

			JSONArray arr = new JSONArray();
			if (json != null)
				arr.add(json.get(0));
			arr.add(obj);

			return arr.toJSONString();
		}

		private void writeFile(String json) throws IOException {
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(json);
			fileWriter.flush();
			fileWriter.close();
		}

		private JSONArray readFile() throws IOException, ParseException {
			JSONParser parser = new JSONParser();
			FileReader fileReader = new FileReader(file);
			Object parsed = parser.parse(fileReader);
			return (JSONArray) parsed;
		}

		@GetMapping("/add")
		public void add(@RequestParam(value = "userId") int userId,
				@RequestParam(value = "food") String foodName,
				@RequestParam(value = "portion") int foodPortion,
				@RequestParam(value = "year") int year,
				@RequestParam(value = "month") int month,
				@RequestParam(value = "day") int day) throws IOException,
				ParseException {
			JSONArray json = null;
			Date date = new Date(year, month, day);

			if (new File(file).exists())
				json = readFile();
			String jsonToWrite = writeJson(json, userId, 1, foodName,
					foodPortion, date);
			writeFile(jsonToWrite);
		}
	}
}
