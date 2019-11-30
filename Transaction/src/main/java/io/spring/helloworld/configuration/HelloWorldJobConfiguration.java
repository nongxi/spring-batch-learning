package io.spring.helloworld.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class HelloWorldJobConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("Step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Hello World!");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("Step2")
                .tasklet((stepContribution, chunkContext) ->  {
                    System.out.println("This is step 2!");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("Step3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("This is step 3!");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step step4() {
        return stepBuilderFactory.get("Step4")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("This is step 4!");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Job helloWorldJob() {
        return jobBuilderFactory.get("HelloWorldJob")
                .start(step1())
                .on("COMPLETED").to(step2())
                //.from(step2()).on("COMPLETED").to(step3())
                //.from(step2()).on("COMPLETED").fail()
                //.from(step2()).on("COMPLETED").stop().next(step3())
                //.from(step2()).on("COMPLETED").stop()
                .from(step2()).on("COMPLETED").stopAndRestart(step3())
                .from(step3()).on("COMPLETED").to(step4())
                .from(step4()).end()
                .build();
    }

}
