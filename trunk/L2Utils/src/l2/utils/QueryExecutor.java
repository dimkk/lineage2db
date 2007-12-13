package l2.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class QueryExecutor {
	
	public static final String SQL_EXP_BY_HP = 	"SELECT * "
												+	"FROM npc "
												+	"WHERE type <> 'L2Minion' AND type <> 'L2RaidBoss' AND type <> 'L2Boss' AND exp <> 0 "
												+	"ORDER BY exp*4/hp DESC;";
	
	public static final String SQL_GET_MOBS= 	"SELECT * "
												+	"FROM npc "
												+	"WHERE type <> 'L2Minion' AND type <> 'L2RaidBoss' AND type <> 'L2Boss' AND exp <> 0 ";

	
	private Connection conn = null;
	
	private PreparedStatement bestExpByHP = null;

	private PreparedStatement getMobs = null;

	public QueryExecutor( String host, String db ) {
		
        this.conn = null;

        try
        {
            String userName = "eduardo";
            String password = "senha";
            String url = "jdbc:mysql://"+host+"/"+db;
            Class.forName ("com.mysql.jdbc.Driver").newInstance ();
            conn = DriverManager.getConnection (url, userName, password);
            System.out.println ("Database connection established");
            this.initStatements();
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            System.err.println ("Cannot connect to database server");
        }
	}
	
	
	
	private void initStatements() throws SQLException {
		
		bestExpByHP = this.conn.prepareStatement(SQL_EXP_BY_HP);
		System.out.println(SQL_GET_MOBS);
		getMobs  = this.conn.prepareStatement(SQL_GET_MOBS);
	}
	
	public List<Mob> getMobs() throws SQLException{
		LinkedList<Mob> mobs = new LinkedList<Mob>();
		
		ResultSet rs = getMobs.executeQuery();
		while(rs.next()){
			String name = rs.getString("name");
			int level = rs.getInt("level");
			int hp = rs.getInt("hp");
			int patk = rs.getInt("patk");
			int pdef = rs.getInt("pdef");
			int exp = rs.getInt("exp");
			Mob mob = new Mob(name, level, hp, patk, pdef, exp);
			mobs.add(mob);
		}
		return mobs;
	}



	public static void main(String[] args) throws Exception {
		
		QueryExecutor qe = new QueryExecutor( "localhost", "l2jdb" );
		List<Mob> mobs = qe.getMobs();
		int level = 65;
		double dmgProportion = 1d;
		double alternativeXP = 0d;
		HigherXpPerHpFirstComparator comparator = new HigherXpPerHpFirstComparator( level, dmgProportion, alternativeXP );
		Collections.sort(mobs, comparator);
		
		for (Mob mob : mobs) {
			int xp = 4*XPUtils.calculateXP(mob.getLevel(), mob.getExp(), level, dmgProportion, alternativeXP);
			double hp_xp = xp*4d/mob.getHp()*1d;
			System.out.printf("%s %12s  adjustedXP %12.3f ratio\n", mob, xp, hp_xp );
		}
	}

}
