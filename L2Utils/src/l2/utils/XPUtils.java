package l2.utils;



public class XPUtils {
	
	
	public static int calculateXP( int mobLvl, int baseXP, int playerLvl, double dmgProportion, double alternativeXP ){
		
		int lvlDiff = Math.max(-5, playerLvl-mobLvl);
		
		double xp = baseXP*dmgProportion;
		
		if( alternativeXP != 0 ){
			
			xp *= Math.pow( 2d, -lvlDiff / alternativeXP );
		}
		
		if( lvlDiff > 5 ){
			
			xp *= Math.pow((double)5/6, lvlDiff-5);
		}
		return (int) Math.max( xp, 0 );
	}
	
	
	
	public static int[] calculatePartyXP( int mobLvl, int baseXP, int[] playerLvl, double dmgProportion, double alternativeXP, double ptBonus  ){

		final double[] BONUS_EXP_SP = {1, 1.30, 1.39, 1.50, 1.54, 1.58, 1.63, 1.67, 1.71};
		double ptXpBonus = 	playerLvl.length <= 1 ? 1
							: BONUS_EXP_SP[Math.min(BONUS_EXP_SP.length, playerLvl.length)] * ptBonus;
		
		// get party lvl!
		int ptLvl = playerLvl[0];
		for (int i = 1; i < playerLvl.length; i++) { 
			Math.max(ptLvl, playerLvl[i]);
		}
		
		//calculate pt xp
		double xp = calculateXP(mobLvl, baseXP, ptLvl, dmgProportion, alternativeXP);
		xp *= dmgProportion; //yes, the proportions is multiplied twice in the l2j, maybe a bug? 
		xp *= ptXpBonus; //party bonus
		
		//xp distribution
		double sqLvlSum = 0d;
		int[] distributedXP = new int[playerLvl.length];
		for (int i = 0; i < distributedXP.length; i++) {
			sqLvlSum += playerLvl[i]*playerLvl[i];
		}
		for (int i = 0; i < distributedXP.length; i++) {
			distributedXP[i] = (int) (sqLvlSum/(playerLvl[i]*playerLvl[i]));//little rounding error =P
		}
		
		return distributedXP;
	}
	
	


}
