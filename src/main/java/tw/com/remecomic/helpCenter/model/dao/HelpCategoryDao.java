package tw.com.remecomic.helpCenter.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.remecomic.helpCenter.model.bean.HelpCategory;

public interface HelpCategoryDao extends JpaRepository<HelpCategory, Integer> {

}
