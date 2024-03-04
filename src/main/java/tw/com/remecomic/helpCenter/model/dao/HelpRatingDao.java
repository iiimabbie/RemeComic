package tw.com.remecomic.helpCenter.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.remecomic.helpCenter.model.bean.HelpRating;

public interface HelpRatingDao extends JpaRepository<HelpRating, Integer> {

}
