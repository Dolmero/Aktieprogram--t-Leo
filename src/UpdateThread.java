// Names in English in this class and mixed in Main... 

public class UpdateThread extends Thread {
	int count;
	float buyPrice;
	int courtageSelection;
	float sellPrice;
	float profit = 0f;
	public UpdateThread(){
	}
	
	public void run(){
		while (true){
		if (findChanges()) {
			try {
				float preValue = count * buyPrice;
				float curValue = count * sellPrice;
				float[] courtage = getCourtage(courtageSelection, preValue, curValue);
				float x = (preValue + 2 * courtage[0]) / count;
				profit = curValue - preValue - courtage[0] - courtage[1];
				
				StringBuilder sb = new StringBuilder();
				sb.append("Change: " + profit + "\n");
				sb.append("Sell point to break even ~" + x + "\n");
				Main.setText(sb.toString(), profit);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		try {
			Thread.sleep(150); // Limit cycles. Ghetto fix.
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	private boolean findChanges(){
		returnValues values;
		if ((values = Main.getvalues()) != null)
			if (	count != values.count||
					buyPrice != values.buyPrice||
					courtageSelection != values.courtageSelection|| 
					sellPrice != values.sellPrice){
				count = values.count;
				buyPrice = values.buyPrice;
				courtageSelection = values.courtageSelection;
				sellPrice = values.sellPrice;
				return true;
			}
		return false;
	}
	
	//Returns two different courtage values. One[0] for when you buy, and one[1] for when you sell.
		private static float[] getCourtage(int index, float preValue, float curValue){
			float[] returnvalue = {0,0};
			switch(index){
			case 0:
				returnvalue[0] = (float) Math.max(69, preValue*0.00069);
				returnvalue[1] = (float) Math.max(69, curValue*0.00069);
				break;
			case 1:
				returnvalue[0] = (float) Math.max(49, preValue*0.00034);
				returnvalue[1] = (float) Math.max(49, curValue*0.00034);
				break;
			case 2:
				returnvalue[0] = (float) Math.max(39, preValue*0.0015);
				returnvalue[1] = (float) Math.max(39, curValue*0.0015);
				break;
			case 3:
				break;
			case 4:
				returnvalue[0] = (float) Math.max(1, preValue*0.0025);
				returnvalue[1] = (float) Math.max(1, curValue*0.0025);
				break;
			default:
				//Should never happen
				System.out.println(("No courtage selection"));
			}
			return returnvalue;
		}
}
