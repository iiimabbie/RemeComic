//package tw.com.remecomic.util;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//import tw.com.remecomic.comic.model.bean.ComicDMySpecialRecom;
//import weka.core.Attribute;
//import weka.core.DenseInstance;
//import weka.core.Instances;
//
//@Component
//public class Weka {
//	 @Autowired
//	    private JdbcTemplate jdbcTemplate;
//	 
//	 public List<ComicDMySpecialRecom> findMySpecialRecom() {
//		    String queryMySpecialRecom = 
//		    		"SELECT u.birthDate, ccg.genreId, u.gender, crc.comicId, u.userId "
//		    		+ "from ComicDRatingComic crc "
//		    		+ "JOIN ComicDComicConGenres ccg ON crc.comicId = ccg.comicId "
//		    		+ "JOIN UserA u ON u.userId = crc.userId "
//		    		+ "WHERE like = 1";    
//		    
//		    return jdbcTemplate.query(
//		    		queryMySpecialRecom,
//		            (rs, rowNum) -> new ComicDMySpecialRecom(  
//		                    rs.getDate("birthDate"),
//		                    rs.getInt("genreId"),
//		                    rs.getString("gender"),
//		                    rs.getInt("userId"),
//		                    rs.getInt("comicId")
//		             )
//		                    
//		    );
//		}
//	 
//	 public void startWeka () {
//		 
//		 ArrayList<String> genderValues = new ArrayList<>();
//		 genderValues.add("Male");
//		 genderValues.add("Female");
//		 
//		 
//		 ArrayList<Attribute> attributes = new ArrayList<>();
//		 attributes.add(new Attribute("birthDate")); // assuming it's a numeric representation
//		 attributes.add(new Attribute("genreId"));
//		 // For gender, if it's categorical like M/F, you might need to handle it differently
//		 attributes.add(new Attribute("gender", genderValues)); // for string attribute
//		 attributes.add(new Attribute("userId"));
//		 attributes.add(new Attribute("comicId"));
//		 
//
//		 List<ComicDMySpecialRecom> dataList = findMySpecialRecom();
//		 Instances wekaData = new Instances("Dataset", attributes, dataList.size());
//		 for (ComicDMySpecialRecom data : dataList) {
//		     DenseInstance instance = new DenseInstance(attributes.size());
//		     instance.setValue(attributes.get(0), data.getUserBirthDate().getTime()); // convert Date to a numeric value
//		     instance.setValue(attributes.get(1), data.getGenreId());
//		     instance.setValue(attributes.get(2), data.getUserGender());
//		     instance.setValue(attributes.get(3), data.getUserId());
//		     instance.setValue(attributes.get(4), data.getComicId());
//
//		     wekaData.add(instance);
//		 }
//		 
//	 }
//
//}
