package demo.hello.Main;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import basetest.BaseTest;
import demo.jpa.service.UserService;
import klg.common.utils.MyPrinter;

public class ServiceTest extends BaseTest {
	@Autowired
	UserService userService;

	@Test
	public void testService() {
		MyPrinter.printJson(userService.getById(1));

	}
}
