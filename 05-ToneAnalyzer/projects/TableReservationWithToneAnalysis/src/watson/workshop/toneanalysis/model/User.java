package watson.workshop.toneanalysis.model;

public class User {
	Tone tone;
	
	public User(Tone tone) {
		this.tone = tone;
	}

	public Tone getTone() {
		return tone;
	}

	public void setTone(Tone tone) {
		this.tone = tone;
	}
	
	
}
