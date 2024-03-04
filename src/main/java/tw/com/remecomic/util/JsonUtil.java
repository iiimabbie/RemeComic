package tw.com.remecomic.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	public static void handleJsonColorFileUpdate(Map<String,Object> newColor) {
		try {
			//"/Users/daisseeey/Documents/Final/RemeComic_VueJs/RemeComic_VueJS/src/assets/comicD/genreColor.json"
			 Path filePath = Paths.get("C:\\Users\\User\\Desktop\\G1\\workspace\\RemeComic_VueJS\\src\\assets\\comicD\\genreColor.json");
		        ObjectMapper objectMapper = new ObjectMapper();
		        String existingData = Files.readString(filePath);

		        // Parse existing data into a List
		        List<Map<String, Object>> jsonList = 
		        		objectMapper.readValue(existingData, new TypeReference<List<Map<String, Object>>>() {});

		        // Add the new data directly to the list
		        jsonList.add(newColor);

		        // Write the updated list back to the file
		        Files.writeString(filePath, objectMapper.writeValueAsString(jsonList));
			
		}catch (IOException e) {
            e.printStackTrace();
            
        }
	}

}
