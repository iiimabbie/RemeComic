package tw.com.remecomic.helpCenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.remecomic.helpCenter.model.bean.HelpReport;
import tw.com.remecomic.helpCenter.model.dao.HelpReportDao;

@Service
public class HelpReportService {

	@Autowired
	private HelpReportDao reportDao;

	//新增
	public HelpReport addReport(HelpReport helpReport) {
		HelpReport save = reportDao.save(helpReport);
		return save;
	}

	//查詢全部
	public List<HelpReport> findAllReport() {
		return reportDao.findAll();
	}

	//ID查詢
	public Optional<HelpReport> findReportById(Integer id) {
		return reportDao.findById(id);
	}

	//ID刪除
	public boolean deleteReportById(Integer id) {
		Optional<HelpReport> findById = reportDao.findById(id);
		if(findById.isPresent()) {
			HelpReport report = findById.get();
			reportDao.delete(report);
			return true;
		}return false;
	}

	//刪除
	public void deleteReport(HelpReport helpReport) {
		reportDao.delete(helpReport);
	}
	
	
}
