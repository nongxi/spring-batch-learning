package io.spring.batch.nestedJob.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChildJobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step step1a(){
        return stepBuilderFactory.get("step1a")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("\t this is step 1a");

                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Job childJob(){
        return jobBuilderFactory.get("childJob")
                .start(step1a())
                .build();
    }
}
