public class CPUScheduling {
	public static void main(String [] args){
		try{
			switch(args.length){
				case (1):{
					OS driver = new OS(args[0]);
					break;
				}
				case (2):{
					OS driver = new OS(args[0],Integer.parseInt(args[1]));
					break;
				}
				case (3):{
					OS driver = new OS(args[0],Integer.parseInt(args[1]),Integer.parseInt(args[2]));
					break;
				}
			}
		}
		catch(Exception e){
			System.out.println("Inavlid arguments or file:\n Error: " + e.getMessage());
		}
	}
}