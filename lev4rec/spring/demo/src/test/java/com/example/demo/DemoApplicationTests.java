package com.example.demo;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lev4rec.business.FeatureHandler;
import com.lev4rec.DemoApplication;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest (classes = DemoApplication.class)
class DemoApplicationTests {

	@Test
	void contextLoads() throws IOException {
		FeatureHandler.compressFolderToZip(new File("/Users/juri/development/git/LEV4REC/lev4rec/bundles/lev4rec.code.template/output"), new File("Claudio.zip"));
	}

}
