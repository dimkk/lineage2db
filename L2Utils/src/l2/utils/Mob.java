package l2.utils;

public class Mob {
	
	private String name;
	private int level;
	private int hp;
	private int patk;
	private int pdef;
	private int exp;

	public Mob( String name, int level, int hp, int patk, int pdef, int exp ) {
		this.name = name;
		this.level = level;
		this.hp = hp;
		this.patk = patk;
		this.pdef = pdef;
		this.exp = exp;
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public int getHp() {
		return hp;
	}

	public int getPatk() {
		return patk;
	}

	public int getPdef() {
		return pdef;
	}

	public int getExp() {
		return exp;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void setPatk(int patk) {
		this.patk = patk;
	}

	public void setPdef(int pdef) {
		this.pdef = pdef;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}
	
	@Override
	public String toString() {
		return String.format("%32s %8s lvl %8s hp %8s xp", this.name, this.level, this.hp, this.exp);
	}

}
