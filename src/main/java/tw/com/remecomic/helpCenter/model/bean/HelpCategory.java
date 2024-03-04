package tw.com.remecomic.helpCenter.model.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "HelpCategory")
public class HelpCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "categoryId")
	private Integer categoryId;
	
	@Column(name = "categoryName")
	private String categoryName;
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reportToCategory")
//	@JsonManagedReference(value = "category-report")
//	private List<HelpReport> helpCategoryToReport;
}
