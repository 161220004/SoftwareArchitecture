package cn.edu.nju.sa2017.mvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringBatchController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {

		model.addAttribute("message", "Spring MVC Hello World");
		return "hello";
	}

	@RequestMapping(value = "/hello/{name:.+}", method = RequestMethod.GET)
	public ModelAndView hello(@PathVariable("name") String name) {

		ModelAndView model = new ModelAndView();
		model.setViewName("hello");
		model.addObject("msg", name);
		return model;
	}

	@RequestMapping(value = "/finish", method = RequestMethod.GET)
	public ModelAndView finish() throws IOException {

		ModelAndView model = new ModelAndView();
		model.setViewName("finish");
		
        String[] springConfig =
                {
                        "spring/batch/jobs/job-hello-world.xml"
                };
        ApplicationContext context =
                new ClassPathXmlApplicationContext(springConfig);

        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("helloWorldJob");
        try {
            JobExecution execution = jobLauncher.run(job, new JobParameters());
            System.out.println("Exit Status : " + execution.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done");

        String inputPath = (new File("")).getCanonicalPath() + "/src/main/resources/cvs/input/report.csv";
        String outputPath = (new File("")).getCanonicalPath() + "/xml/outputs/report.xml";
		model.addObject("input", readFromFile(inputPath));
        //System.out.println(readFromFile(outputPath));
		model.addObject("output", StringEscapeUtils.escapeHtml4(readFromFile(outputPath)));
	
		return model;
	}
	
	private String readFromFile(String inputPath) {
        String encoding = "UTF-8";  
		String input = "";
        File inFile = new File(inputPath);  
        Long inLength = inFile.length();  
        byte[] inBuf = new byte[inLength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(inFile);  
            in.read(inBuf);  
            input = new String(inBuf, encoding);
            in.close();
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return input;
	}
	
}
