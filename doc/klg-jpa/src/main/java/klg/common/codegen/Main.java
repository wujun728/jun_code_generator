package klg.common.codegen;

import java.io.IOException;

import org.junit.Test;


import freemarker.template.TemplateException;

public class Main {
	 
	public static void main(String[] args) throws IOException, TemplateException {
		//FreemarkUtil.writeToFile("dao.ftl", new CodeModel(), "UserDAO.java");
		//System.out.println(Main.class.getPackage());
		
		BatchGen.run();
	}
	@Test
	public void customGen(){
		String[] entityNames={"TemplateFile"};
		BatchGen.gen(entityNames);
	}
	
}
