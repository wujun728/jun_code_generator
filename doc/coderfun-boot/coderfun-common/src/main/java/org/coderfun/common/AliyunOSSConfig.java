package org.coderfun.common;

import com.aliyun.oss.OSSClient;

public class AliyunOSSConfig {
	private OSSClient ossClient;
	private String bucket;
	private String urlPrefix;

	public OSSClient getOssClient() {
		return ossClient;
	}

	public void setOssClient(OSSClient ossClient) {
		this.ossClient = ossClient;
	}

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public String getUrlPrefix() {
		return urlPrefix;
	}

	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

}
