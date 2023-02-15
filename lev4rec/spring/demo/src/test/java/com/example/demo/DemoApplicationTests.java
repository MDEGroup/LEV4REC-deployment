package com.example.demo;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.lev4rec.DemoApplication;
import com.lev4rec.business.FeatureHandler;

@SpringBootTest (classes = DemoApplication.class)
class DemoApplicationTests {

	@Test
	void contextLoads() throws IOException {
		FeatureHandler.compressFolderToZip(new File("/Users/juri/development/git/LEV4REC/lev4rec/bundles/lev4rec.code.template/output"), new File("Claudio.zip"));
	}

}
