package tw.com.remecomic.helpCenter.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.remecomic.helpCenter.model.bean.HelpType;

public interface HelpTypeDao extends JpaRepository<HelpType, Integer> {

}
