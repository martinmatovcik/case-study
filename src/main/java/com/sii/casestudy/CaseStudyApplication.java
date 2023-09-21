package com.sii.casestudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class CaseStudyApplication {

  public static void main(String[] args) {
    SpringApplication.run(CaseStudyApplication.class, args);
  }
}
