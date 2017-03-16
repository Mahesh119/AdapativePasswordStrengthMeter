import java.util.HashMap;


public class NGramStructure {

	public NGramStructure(HashMap<String, NGramRecord> ngramProbTable2,
			int recordPopulation) {
		this.ngramProbTable = ngramProbTable2;
		this.ngramPopulation = recordPopulation;
	}
	public HashMap<String, NGramRecord> ngramProbTable;
	public int ngramPopulation = 0;
}
