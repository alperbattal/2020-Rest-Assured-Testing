package com.automation.pojos;

public class Job {

    private String job_id, job_title;
    private Integer min_salary, max_salary;

    public Job(){

    }

    public Job(String job_id, String job_title, Integer min_salary, Integer max_salary) {
        this.job_id = job_id;
        this.job_title = job_title;
        this.min_salary = min_salary;
        this.max_salary = max_salary;
    }

    public String getJob_id() {
        return job_id;
    }

    public String getJob_title() {
        return job_title;
    }

    public Integer getMin_salary() {
        return min_salary;
    }

    public Integer getMax_salary() {
        return max_salary;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }
}
