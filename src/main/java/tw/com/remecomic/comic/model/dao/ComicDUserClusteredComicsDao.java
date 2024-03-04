package tw.com.remecomic.comic.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.com.remecomic.comic.model.bean.ComicDUserClusteredComics;
import tw.com.remecomic.comic.model.bean.ComicDUserClusteredComicsPK;

@Repository
public interface ComicDUserClusteredComicsDao extends JpaRepository<ComicDUserClusteredComics, ComicDUserClusteredComicsPK>  {

 @Query(value="Select comicId From ComicDUserClusteredComics where clusterGroupId = :clusterGroupId" , nativeQuery=true)
 List<Integer> findComicIdsByClusterGroupId(Integer clusterGroupId);
	


}
