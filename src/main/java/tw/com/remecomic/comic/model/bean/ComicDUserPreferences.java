package tw.com.remecomic.comic.model.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ComicDUserPreferences")
public class ComicDUserPreferences {
	@Id
	private Integer userId;
	private Double rcSlope;
	private Double raSlope;
	private Double tcSlope;
	private Double trSlope;
	private Double taSlope;
	private Double caSlope;
	private Double rcDistance;
	private Double raDistance;
	private Double tcDistance;
	private Double trDistance;
	private Double taDistance;
	private Double caDistance;
	private Integer clusterGroup;

}
