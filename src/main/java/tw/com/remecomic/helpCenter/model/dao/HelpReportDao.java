package tw.com.remecomic.helpCenter.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.remecomic.helpCenter.model.bean.HelpReport;

public interface HelpReportDao extends JpaRepository<HelpReport, Integer> {

}
