package tw.com.remecomic.helpCenter.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.remecomic.helpCenter.model.bean.HelpServiceResponse;

public interface HelpServiceResponseDao extends JpaRepository<HelpServiceResponse, Integer> {

}
