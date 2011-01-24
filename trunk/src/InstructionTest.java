
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class InstructionTest  {

	@Before
	public void initialize() {
	
		}
	
		@Test
		public void JSRR(){
		Map<Integer, String> rMap;
		rMap = new HashMap<Integer, String>();
		rMap.put(5, "3040");
		String pc = "3031";
		String bRep = "100101001011";
		Integer[] rChange = new Integer[5];
			
			String answer;
			answer = instructionCases.JSRR(rMap, pc, bRep, rChange);
			
			Assert.assertEquals("Error: JSRR is broken" , "304B", answer);
		
	}
		@Test
		public void JSR(){
		Map<Integer, String> rMap;
		rMap = new HashMap<Integer, String>();
		rMap.put(5, "3040");
		String pc = "3031";
		String bRep = "100101001011";
		Integer[] rChange = new Integer[5];
			
			String answer;
			answer = instructionCases.JSR(rMap, pc, bRep, rChange);
			
			Assert.assertEquals("Error: JSR is broken", "314B", answer);
		
	}
		/*
		@Test
		public void Branch(){
		Map<Character, Boolean> rMap = new HashMap<Character, Boolean>();
		rMap.put('N', true);
		String pc = "3031";
		String bRep = "100101001011";

			
			String answer;
			answer = instructionCases.BRx(rMap, pc, bRep);
			
			Assert.assertEquals("Error: BRx is broken" , "314B", answer);
		
	}*/
		@Test
		public void Load(){
			Map<Integer, String> rMap;
			rMap = new HashMap<Integer, String>();
			Map<Character, Boolean> ccReg = new HashMap<Character, Boolean>();
			rMap.put(4, "3040");
			ccReg.put('N', true);
			String pc = "3031";
			String bRep = "100101001011";
			Integer[] rChange = new Integer[5];
			String[] memArray = new String[65536];
			memArray[12619] = "314B";

			
			String answer;
			instructionCases.LD(rMap, memArray, ccReg,pc, bRep, rChange);
			answer = rMap.get(4);
			
			Assert.assertEquals("Error: LD is broken", "314B", answer);
		
	}
		@Test
		public void LoadI(){
			Map<Integer, String> rMap;
			rMap = new HashMap<Integer, String>();
			Map<Character, Boolean> ccReg = new HashMap<Character, Boolean>();
			rMap.put(4, "3040");
			ccReg.put('N', true);
			String pc = "3031";
			String bRep = "100101001011";
			Integer[] rChange = new Integer[5];
			String[] memArray = new String[65536];
			memArray[12619] = "3030";
			memArray[12336] = "314B";
			

			
			String answer;
			instructionCases.LDI(rMap, memArray, ccReg,pc, bRep, rChange);
			answer = rMap.get(4);
			
			Assert.assertEquals("Error: JDI is broken", "314B", answer);
		
	}
		@Test
		public void LoadR(){
			Map<Integer, String> rMap;
			rMap = new HashMap<Integer, String>();
			Map<Character, Boolean> ccReg = new HashMap<Character, Boolean>();
			rMap.put(4, "3040");
			rMap.put(5, "0000");
			ccReg.put('N', true);
			String bRep = "100101001011";
			Integer[] rChange = new Integer[5];
	
			

			
			String answer;
			instructionCases.LDR(rMap, ccReg, bRep, rChange);
			answer = rMap.get(4);
			
			Assert.assertEquals("Error: LDR is broken", "000B", answer);
		
	}
		@Test
		public void LoadA(){
			Map<Integer, String> rMap;
			rMap = new HashMap<Integer, String>();
			Map<Character, Boolean> ccReg = new HashMap<Character, Boolean>();
			rMap.put(4, "3040");
			rMap.put(5, "0000");
			String pc = "3031";
			ccReg.put('N', true);
			String bRep = "100101001011";
			Integer[] rChange = new Integer[5];
	
			

			
			String answer;
			instructionCases.LEA(rMap, ccReg, pc, bRep, rChange);
			answer = rMap.get(4);
			
			Assert.assertEquals("Error: LEA is broken", "314B", answer);
		
	}
		@Test
		public void Store(){
			Map<Integer, String> rMap;
			rMap = new HashMap<Integer, String>();
			Map<Character, Boolean> ccReg = new HashMap<Character, Boolean>();
			rMap.put(4, "3040");
			ccReg.put('N', true);
			String pc = "3031";
			String bRep = "100101001011";
			String[] mChange = new String[5];
			String[] memArray = new String[65536];
	
			

			
			String answer;
			instructionCases.ST(rMap, memArray, ccReg, pc, bRep, mChange);
			answer = memArray[12619];
			
			Assert.assertEquals("Error: ST is broken", "3040", answer);
		
	}
		@Test
		public void StoreI(){
			Map<Integer, String> rMap;
			rMap = new HashMap<Integer, String>();
			Map<Character, Boolean> ccReg = new HashMap<Character, Boolean>();
			rMap.put(4, "3040");
			ccReg.put('N', true);
			String pc = "3031";
			String bRep = "100101001011";
			String[] mChange = new String[5];
			String[] memArray = new String[65536];
			memArray[12619] = "3040";
			
			

			
			String answer;
			instructionCases.STI(rMap, memArray, ccReg, pc, bRep, mChange);
			answer = memArray[12619];
			
			Assert.assertEquals("Error: STI is broken", "3040", answer);
		
	}
		@Test
		public void StoreR(){
			Map<Integer, String> rMap;
			rMap = new HashMap<Integer, String>();
			Map<Character, Boolean> ccReg = new HashMap<Character, Boolean>();
			rMap.put(4, "3040");
			rMap.put(5, "3040");
			ccReg.put('N', true);
			String bRep = "100101001011";
			String[] mChange = new String[5];
			String[] memArray = new String[65536];
			
			
			

			
			String answer;
			instructionCases.STR(rMap, memArray, ccReg, bRep, mChange);
			answer = memArray[12363];
			
			Assert.assertEquals("Error: STR is broken", "3040", answer);
		
	}
		@Test
		public void Not(){
			Map<Integer, String> rMap;
			rMap = new HashMap<Integer, String>();
			Map<Character, Boolean> ccReg = new HashMap<Character, Boolean>();
			rMap.put(5, "3040");
			ccReg.put('N', true);
			String bRep = "100101001011";
			Integer[] rChange = new Integer[5];
			
			
			
			

			
			String answer;
			instructionCases.NOT(rMap, ccReg, bRep, rChange);
			answer = rMap.get(4);
			
			Assert.assertEquals("Error: STR is broken", "CFBF", answer);
		
	}
		@Test
		public void Return(){
			Map<Integer, String> rMap;
			rMap = new HashMap<Integer, String>();
			rMap.put(7, "3040");

			
			String answer;
			answer =instructionCases.RET(rMap);
		
			Assert.assertEquals("Error: STR is broken", "3040", answer);
		
	}
}


