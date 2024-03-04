package tw.com.remecomic.comic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.remecomic.comic.model.bean.ComicDUserClusteredComics;
import tw.com.remecomic.comic.model.bean.ComicDUserPreferences;
import tw.com.remecomic.comic.model.bean.GenrePreference;
import tw.com.remecomic.comic.model.dao.ComicDDao;
import tw.com.remecomic.comic.model.dao.ComicDRatingComicDao;
import tw.com.remecomic.comic.model.dao.ComicDUserClusteredComicsDao;
import tw.com.remecomic.comic.model.dao.ComicDUserPreferencesDao;
import tw.com.remecomic.util.Calculate;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

@Service
public class ComicDUserPreferencesService {
	private SimpleKMeans kmeans;
	@Autowired 
	ComicDUserPreferencesDao comicDUserPreferenceDao;
	
	@Autowired
	ComicDRatingComicDao comicDRatingComicDao;
	
	@Autowired 
	ComicDUserClusteredComicsDao comicDUserClusteredComicsDao;
	
	@Autowired
	ComicDDao comicDDao;
	
	public void findDataForMyPerference() {
		List<Map<String,Integer>> comicMaps = comicDRatingComicDao.findDataForMyPerference();
		Integer romance = 1; //y-axis genreId 1
		Integer action = -1; //y-axis genreId 2
		Integer comedy = 1; //x-axis genreId 3
		Integer thriller = -1; //x-axis genreId 4
			
		
		List<GenrePreference> userWithScore = new ArrayList<>();
		
		for(Map<String,Integer> map : comicMaps) {
			Integer userId = map.get("userId");
			Integer genreId = map.get("genreId");
			GenrePreference existUserScore = (GenrePreference) userWithScore.stream()
					.filter(score->score.getUserId() == (userId))
					.findFirst()
					.orElse(null);
			GenrePreference scoreToModify;
			if (existUserScore != null) {
			    scoreToModify = existUserScore;
			} else {
			    scoreToModify = new GenrePreference(userId,0.0,0.0,0.0,0.0);
			    // Assuming you need to put the new object back into the map
			    userWithScore.add(scoreToModify);
			}
			
			switch (genreId) {
			    case 1:
			        scoreToModify.setRomance(scoreToModify.getRomance() + 1);
			        break;
			    case 2:
			        scoreToModify.setAction(scoreToModify.getAction() + 1);
			        break;
			    case 3:
			        scoreToModify.setComedy(scoreToModify.getComedy() + 1);
			        break;
			    case 4:
			        scoreToModify.setThriller(scoreToModify.getThriller() + 1);
			        break;
			    // Include more cases as needed
			}
		}
		List<ComicDUserPreferences> userWithScores = new ArrayList<>();
		userWithScore.forEach((user)->{
			ComicDUserPreferences userPref = new ComicDUserPreferences();				
			Double rcSlope = Calculate.slope(user.getRomance(),0,0,user.getComedy());
			Double raSlope = Calculate.slope(user.getRomance(),0,0,user.getAction());
			Double tcSlope = Calculate.slope(user.getThriller(),0,0,user.getComedy());
			Double trSlope = Calculate.slope(user.getThriller(),0,0, user.getRomance());
			Double taSlope = Calculate.slope(user.getThriller(),0,0, user.getAction());
			Double caSlope = Calculate.slope(user.getComedy(),0,0, user.getAction());
			
			Double rcDistance = Calculate.distance(user.getRomance(),0,0,user.getComedy());
			Double raDistance = Calculate.distance(user.getRomance(),0,0,user.getAction());
			Double tcDistance = Calculate.distance(user.getThriller(),0,0,user.getComedy());
			Double trDistance = Calculate.distance(user.getThriller(),0,0, user.getRomance());
			Double taDistance = Calculate.distance(user.getThriller(),0,0, user.getAction());
			Double caDistance = Calculate.distance(user.getComedy(),0,0, user.getAction());			
			
			userPref.setUserId(user.getUserId());
			userPref.setRcSlope(rcSlope);
			userPref.setRaSlope(raSlope);
			userPref.setTcSlope(tcSlope);
			userPref.setTrSlope(trSlope);
			userPref.setTaSlope(taSlope);
			userPref.setCaSlope(caSlope);			
			userPref.setRcDistance(rcDistance);
			userPref.setRaDistance(raDistance);
			userPref.setTcDistance(tcDistance);
			userPref.setTrDistance(trDistance);
			userPref.setTaDistance(taDistance);
			userPref.setCaDistance(caDistance);
			userWithScores.add(userPref);
			
		});
		comicDUserPreferenceDao.saveAll(userWithScores);
		
	}
	
	public void kmeanCalculateCluster() throws Exception {
		List<ComicDUserPreferences> userPerfernce = comicDUserPreferenceDao.findAll();
		Instances data = convertListToInstances(userPerfernce);
		kmeans = new SimpleKMeans();
		kmeans.setNumClusters(5);
		kmeans.buildClusterer(data);
		 
		for (int i = 0; i < data.numInstances(); i++) {
	            int cluster = kmeans.clusterInstance(data.instance(i));
	            System.out.println(cluster);
	            userPerfernce.get(i).setClusterGroup(cluster+1);
	            //comicDUserPreferenceDao.save(userPerfernce.get(i));
	     }
	}
	
	
	public List<Map<String,Object>>  getNewUserCluster(Integer userId) throws Exception {	
		List<Integer> originalComicLikes = comicDRatingComicDao.findComicIdByUserId(userId);
		Optional<ComicDUserPreferences> userPerfernce = comicDUserPreferenceDao.findById(userId);
		int clusterId = 0;
		if(!userPerfernce.isEmpty()) {
			clusterId = userPerfernce.get().getClusterGroup();
			//List<Integer> comicIds = comicDUserClusteredComicsDao.findComicIdsByClusterGroupId(ClusterGroupId);
		}else {
			List<ComicDUserPreferences> newUserPreference= new ArrayList<>();
			newUserPreference.add(convertToUserPerferences(userId));
			Instances newUserInstance = convertListToInstances(newUserPreference);
			((Instance) newUserInstance).setDataset(((Instance) kmeans.getClusterCentroids()).dataset());
			clusterId = kmeans.clusterInstance((Instance) newUserInstance);
			//List<Integer> comicIds = comicDUserClusteredComicsDao.findComicIdsByClusterGroupId(ClusterGroupId);
		}
		System.out.print("clusterId:  "+clusterId);
		List<Integer> recomComicIds = comicDUserClusteredComicsDao.findComicIdsByClusterGroupId(clusterId);
		List<Integer> resRecomComicIds = 
				recomComicIds.stream()
				.filter(comicId -> !originalComicLikes.contains(comicId))
				.collect(Collectors.toList());
		originalComicLikes.forEach(comicLike -> System.out.println("originalLikes"+ comicLike));
		recomComicIds.forEach(comicLike -> System.out.println("recomLikes"+comicLike));
		List<Map<String,Object>> recomComicInfo = comicDDao.findComicInfoForRecom(resRecomComicIds);
		
		return recomComicInfo;

	}
	
	private ComicDUserPreferences convertToUserPerferences (Integer userId) {
		List<Integer> genreIds = comicDRatingComicDao.findGenreIdByUserId(userId);
		GenrePreference user = new GenrePreference(userId,0.0,0.0,0.0,0.0);
		for(Integer genreId : genreIds) {
			switch (genreId) {
		    case 1:
		    	user.setRomance(user.getRomance() + 1);
		        break;
		    case 2:
		    	user.setAction(user.getAction() + 1);
		        break;
		    case 3:
		    	user.setComedy(user.getComedy() + 1);
		        break;
		    case 4:
		    	user.setThriller(user.getThriller() + 1);
		        break;			
			}
		}
		
		ComicDUserPreferences userPref = new ComicDUserPreferences();				
			Double rcSlope = Calculate.slope(user.getRomance(),0,0,user.getComedy());
			Double raSlope = Calculate.slope(user.getRomance(),0,0,user.getAction());
			Double tcSlope = Calculate.slope(user.getThriller(),0,0,user.getComedy());
			Double trSlope = Calculate.slope(user.getThriller(),0,0, user.getRomance());
			Double taSlope = Calculate.slope(user.getThriller(),0,0, user.getAction());
			Double caSlope = Calculate.slope(user.getComedy(),0,0, user.getAction());
			
			Double rcDistance = Calculate.distance(user.getRomance(),0,0,user.getComedy());
			Double raDistance = Calculate.distance(user.getRomance(),0,0,user.getAction());
			Double tcDistance = Calculate.distance(user.getThriller(),0,0,user.getComedy());
			Double trDistance = Calculate.distance(user.getThriller(),0,0, user.getRomance());
			Double taDistance = Calculate.distance(user.getThriller(),0,0, user.getAction());
			Double caDistance = Calculate.distance(user.getComedy(),0,0, user.getAction());			
		
			userPref.setUserId(userId);
			userPref.setRcSlope(rcSlope);
			userPref.setRaSlope(raSlope);
			userPref.setTcSlope(tcSlope);
			userPref.setTrSlope(trSlope);
			userPref.setTaSlope(taSlope);
			userPref.setCaSlope(caSlope);			
			userPref.setRcDistance(rcDistance);
			userPref.setRaDistance(raDistance);
			userPref.setTcDistance(tcDistance);
			userPref.setTrDistance(trDistance);
			userPref.setTaDistance(taDistance);
			userPref.setCaDistance(caDistance);
			return userPref;
	}
	
	
	
	
	
	private static Instances convertListToInstances(List<ComicDUserPreferences> list) {
        // Define the attributes
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("userId"));
        attributes.add(new Attribute("rcSlope"));
        attributes.add(new Attribute("raSlope"));
        attributes.add(new Attribute("tcSlope"));
        attributes.add(new Attribute("trSlope"));
        attributes.add(new Attribute("taSlope"));
        attributes.add(new Attribute("caSlope"));
        attributes.add(new Attribute("rcDistance"));
        attributes.add(new Attribute("raDistance"));
        attributes.add(new Attribute("tcDistance"));
        attributes.add(new Attribute("trDistance"));
        attributes.add(new Attribute("taDistance"));
        attributes.add(new Attribute("caDistance"));

        // Create an empty Instances object with the attributes
        Instances dataset = new Instances("ComicDUserPreferencesDataset", attributes, list.size());
        
        // Fill the dataset
        for (ComicDUserPreferences item : list) {
            DenseInstance instance = new DenseInstance(attributes.size());
            instance.setValue(attributes.get(0), item.getUserId());
            instance.setValue(attributes.get(1), item.getRcSlope());
            instance.setValue(attributes.get(2), item.getRaSlope());
            instance.setValue(attributes.get(3), item.getTcSlope());
            instance.setValue(attributes.get(4), item.getTrSlope());
            instance.setValue(attributes.get(5), item.getTaSlope());
            instance.setValue(attributes.get(6), item.getCaSlope());
            instance.setValue(attributes.get(7), item.getRcDistance());
            instance.setValue(attributes.get(8), item.getRaDistance());
            instance.setValue(attributes.get(9), item.getTcDistance());
            instance.setValue(attributes.get(10), item.getTrDistance());
            instance.setValue(attributes.get(11), item.getTaDistance());
            instance.setValue(attributes.get(12), item.getCaDistance());
            dataset.add(instance);
        }
        
        return dataset;
    }
	
	public void getRecommandation(){
		List<ComicDUserPreferences> userPerfernces = comicDUserPreferenceDao.findAll();
		Map<Integer, List<Integer>> userGroupByCluster = new HashMap<>();
		
		for(ComicDUserPreferences userPref: userPerfernces) {
			Integer clusterGroup = userPref.getClusterGroup();
			if(userGroupByCluster.containsKey(clusterGroup)) {
				userGroupByCluster.get(clusterGroup).add(userPref.getUserId());
			}else {
				List<Integer> newUserIds = new ArrayList<>();
			    newUserIds.add(userPref.getUserId());
			    userGroupByCluster.put(clusterGroup,newUserIds);
			}
			
		}
		for(Map.Entry<Integer, List<Integer>> entry: userGroupByCluster.entrySet()) {
			Integer clusterGroupId = entry.getKey();
			List<Integer> userIds = entry.getValue();
 			List<Integer> comicIds = 
					comicDRatingComicDao.findComicIdByClusterUser(userIds);
 			List<ComicDUserClusteredComics> temp = new ArrayList<>();
 			comicIds.forEach(comicId -> {
 				ComicDUserClusteredComics clusterComic = new ComicDUserClusteredComics(clusterGroupId, comicId);
 				temp.add(clusterComic);
 			});
 			comicDUserClusteredComicsDao.saveAll(temp);

		}
				
	}
	

}
