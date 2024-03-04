//package tw.com.remecomic.comic.model.dao;
//
//import org.springframework.stereotype.Repository;
//
//import jakarta.transaction.Transactional;
//
//@Repository
//@Transactional
//public class ComicDMixtureDao {
//	
//	String hql = "SELECT "
//			+ "    c.comicId, "
//			+ "    c.comicTitle, "
//			+ "    c.comicDescription, "
//			+ "    c.updateDay, "
//			+ "    c.publishDate, "
//			+ "    c.editorChoice, "
//			+ "    c.purchasePrice, "
//			+ "    c.rentalPrice, "
//			+ "    c.episodeLikes, "
//			+ "    SUM(ccr.like) AS comicLikes "
//			+ "    SUM(ceb.pageId) AS comments "
//			+ "FROM "
//			+ "    ComicD c"
//			+ "JOIN "
//			+ "    ComicDEpisodeUpdate ceu ON ceu.comicId = c.comicId"
//			+ "JOIN "
//			+ "	  ComicEpisodeBody ceb ON ceb.episodeId = ceu.episdoeId"
//			+ "JOIN "
//			+ "    ComicDComicRating ccr ON ccr.comicId = c.comicId"
//			+ "Right JOIN"
//			+ "		ComicDComment ccc ON ccc.pageId = ceb.PageId"
//			+ "GROUP BY "
//			+ "    c.comicId, "
//			+ "    c.comicTitle, "
//			+ "    c.comicDescription, "
//			+ "    c.updateDay, "
//			+ "    c.publishDate, "
//			+ "    c.editorChoice, "
//			+ "    c.purchasePrice, "
//			+ "    c.rentalPrice, "
//			+ "    c.episodeLikes
//	
//
//}
