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
@Table(name = "HelpType")
public class HelpType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "typeId")
	private Integer typeId;
	
	@Column(name = "typeName")
	private String typeName;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reportToType")
	@JsonManagedReference(value = "type-report")
	private List<HelpReport> typeToReport;
}
