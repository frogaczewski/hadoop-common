package org.apache.hadoop.mapreduce.v2.api.protocolrecords;

import org.apache.hadoop.mapreduce.v2.api.records.JobId;

public interface KillJobRequest {
  public abstract JobId getJobId();
  
  public abstract void setJobId(JobId jobId);
}
